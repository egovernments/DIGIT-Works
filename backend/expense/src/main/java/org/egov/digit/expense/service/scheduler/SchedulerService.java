package org.egov.digit.expense.service.scheduler;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Adaptive DB-backed scheduler.
 *
 * <ul>
 *   <li>Polls per active tenant using a single-threaded executor (no concurrent runs).</li>
 *   <li>Claims jobs atomically via {@code UPDATE...RETURNING} CTE with {@code FOR UPDATE SKIP LOCKED}.</li>
 *   <li>Dispatches to the correct {@link SchedulerJobHandler} by job type.</li>
 *   <li>Applies exponential backoff for retries and backs off polling when idle.</li>
 *   <li>Recovery and cleanup run on Spring {@code @Scheduled} threads (independent of the poll loop).</li>
 * </ul>
 */
@Service
@Slf4j
public class SchedulerService {

    private final Configuration config;
    private final SchedulerJobRepository jobRepository;
    private final SchedulerJobRegistry registry;
    private final Map<SchedulerJobType, SchedulerJobHandler> handlers;

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "scheduler-poll");
                t.setDaemon(true);
                return t;
            });

    private final AtomicLong currentIntervalMs = new AtomicLong(0);

    @Autowired
    public SchedulerService(Configuration config,
                             SchedulerJobRepository jobRepository,
                             SchedulerJobRegistry registry,
                             List<SchedulerJobHandler> handlerList) {
        this.config = config;
        this.jobRepository = jobRepository;
        this.registry = registry;

        // Build handler map from all SchedulerJobHandler beans (Strategy pattern)
        this.handlers = new EnumMap<>(SchedulerJobType.class);
        for (SchedulerJobHandler h : handlerList) {
            handlers.put(h.getJobType(), h);
            log.info("Registered scheduler handler: {} → {}", h.getJobType(), h.getClass().getSimpleName());
        }
    }

    @PostConstruct
    public void start() {
        // Pre-register configured tenants so PENDING jobs in DB are picked up after a full cluster restart,
        // without waiting for a new transfer request to dynamically re-register the tenant.
        config.getSchedulerBootstrapTenants().forEach(tenantId -> {
            registry.register(tenantId);
            log.info("SchedulerService: bootstrapped tenant {} from configuration", tenantId);
        });
        currentIntervalMs.set(config.getSchedulerMinIntervalMs());
        executor.schedule(this::runCycle, config.getSchedulerInitialDelayMs(), TimeUnit.MILLISECONDS);
        log.info("SchedulerService started — initial delay {}ms, {} bootstrap tenant(s)",
                config.getSchedulerInitialDelayMs(), config.getSchedulerBootstrapTenants().size());
    }

    @PreDestroy
    public void stop() {
        executor.shutdownNow();
        log.info("SchedulerService stopped");
    }

    // ── Main polling loop ─────────────────────────────────────────────────────

    private void runCycle() {
        boolean anyWork = false;
        try {
            for (String tenantId : registry.getActiveTenants()) {
                int count = processForTenant(tenantId);
                if (count > 0) anyWork = true;
            }
        } catch (Exception e) {
            log.error("Unexpected error in scheduler poll cycle", e);
        } finally {
            long nextInterval = anyWork
                    ? config.getSchedulerMinIntervalMs()
                    : Math.min((long) (currentIntervalMs.get() * 1.5), config.getSchedulerMaxIntervalMs());
            currentIntervalMs.set(nextInterval);
            try {
                executor.schedule(this::runCycle, nextInterval, TimeUnit.MILLISECONDS);
            } catch (java.util.concurrent.RejectedExecutionException ignored) {
                log.info("Scheduler executor shut down — stopping poll loop");
            }
        }
    }

    private int processForTenant(String tenantId) {
        List<SchedulerJob> claimed = jobRepository.claimBatch(
                tenantId, config.getSchedulerBatchSize(), System.currentTimeMillis());

        if (claimed.isEmpty()) return 0;

        List<SchedulerJob> toUpdate = new ArrayList<>(claimed.size());
        for (SchedulerJob job : claimed) {
            SchedulerJob updated = processJob(job);
            toUpdate.add(updated);
        }

        jobRepository.batchUpdateStatus(toUpdate, tenantId);
        return claimed.size();
    }

    private SchedulerJob processJob(SchedulerJob job) {
        long nowMs = System.currentTimeMillis();
        int nextAttempt = job.getAttemptCount() + 1;

        // Timeout protection
        if (nowMs - job.getCreatedAt() >= config.getSchedulerMaxDurationMs()) {
            log.warn("Job {} (type={}) timed out after {}ms — marking FAILED",
                    job.getId(), job.getJobType(), config.getSchedulerMaxDurationMs());
            return finalized(job, SchedulerJobStatus.FAILED, nextAttempt, null);
        }

        // Max-attempts protection
        if (nextAttempt > job.getMaxAttempts()) {
            log.warn("Job {} (type={}) exceeded max attempts {} — marking FAILED",
                    job.getId(), job.getJobType(), job.getMaxAttempts());
            SchedulerJobHandler exhaustedHandler = handlers.get(job.getJobType());
            if (exhaustedHandler != null) {
                try { exhaustedHandler.onMaxAttemptsExceeded(job); }
                catch (Exception e) {
                    log.error("Error in onMaxAttemptsExceeded for job {} type={}", job.getId(), job.getJobType(), e);
                }
            }
            return finalized(job, SchedulerJobStatus.FAILED, nextAttempt, null);
        }

        SchedulerJobHandler handler = handlers.get(job.getJobType());
        if (handler == null) {
            log.error("No handler registered for job type {} (jobId={}) — marking FAILED",
                    job.getJobType(), job.getId());
            return finalized(job, SchedulerJobStatus.FAILED, nextAttempt, null);
        }

        SchedulerJobResult result = handler.handle(job);

        return switch (result) {
            case DONE -> {
                log.info("Job {} resolved after {} attempt(s)", job.getId(), nextAttempt);
                yield finalized(job, SchedulerJobStatus.DONE, nextAttempt, null);
            }
            case FAILED -> {
                log.warn("Job {} permanently failed after {} attempt(s)", job.getId(), nextAttempt);
                yield finalized(job, SchedulerJobStatus.FAILED, nextAttempt, null);
            }
            case RETRY -> {
                long backoff = computeBackoffMs(nextAttempt);
                log.info("Job {} still pending — attempt {}/{}, retry in {}ms",
                        job.getId(), nextAttempt, job.getMaxAttempts(), backoff);
                yield retrying(job, nextAttempt, nowMs + backoff);
            }
        };
    }

    /** Exponential backoff: 10 s × 2^(attempt-1), capped at 10 min. */
    private long computeBackoffMs(int attempt) {
        long base = 10_000L;
        long cap = 600_000L;
        return Math.min(base << Math.min(attempt - 1, 6), cap);
    }

    private SchedulerJob finalized(SchedulerJob job, SchedulerJobStatus status,
                                    int attemptCount, Long nextCheckAt) {
        job.setSchedulerStatus(status);
        job.setAttemptCount(attemptCount);
        job.setNextCheckAt(nextCheckAt);
        return job;
    }

    private SchedulerJob retrying(SchedulerJob job, int attemptCount, long nextCheckAt) {
        job.setSchedulerStatus(SchedulerJobStatus.PENDING);
        job.setAttemptCount(attemptCount);
        job.setNextCheckAt(nextCheckAt);
        return job;
    }

    // ── Recovery job (every 2 min) ────────────────────────────────────────────

    @Scheduled(fixedRateString = "${task.scheduler.recovery.interval.ms:120000}",
               initialDelayString = "${task.scheduler.recovery.interval.ms:120000}")
    public void recoverStuckJobs() {
        for (String tenantId : registry.getActiveTenants()) {
            try {
                jobRepository.recoverStuck(tenantId, config.getSchedulerStuckThresholdMs());
            } catch (Exception e) {
                log.error("Error in recovery job for tenant {}", tenantId, e);
            }
        }
    }

    // ── Cleanup job (every 1 hr) ──────────────────────────────────────────────

    @Scheduled(fixedRateString = "${task.scheduler.cleanup.interval.ms:3600000}",
               initialDelayString = "${task.scheduler.cleanup.interval.ms:3600000}")
    public void cleanupCompletedJobs() {
        for (String tenantId : registry.getActiveTenants()) {
            try {
                jobRepository.cleanup(tenantId, config.getSchedulerCleanupAfterMs());
            } catch (Exception e) {
                log.error("Error in cleanup job for tenant {}", tenantId, e);
            }
        }
    }
}
