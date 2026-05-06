package org.egov.digit.expense.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.digit.expense.repository.querybuilder.SchedulerJobQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.SchedulerJobRowMapper;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
@Slf4j
public class SchedulerJobRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SchedulerJobQueryBuilder queryBuilder;
    private final SchedulerJobRowMapper rowMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public SchedulerJobRepository(JdbcTemplate jdbcTemplate,
                                   SchedulerJobQueryBuilder queryBuilder,
                                   SchedulerJobRowMapper rowMapper,
                                   ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.objectMapper = objectMapper;
    }

    /**
     * Persists a new scheduler job directly to DB (bypasses Kafka/persister — scheduler concern).
     */
    public void insert(SchedulerJob job) {
        try {
            String sql = queryBuilder.insert(job.getTenantId());
            PGobject contextObj = new PGobject();
            contextObj.setType("jsonb");
            contextObj.setValue(job.getContext() != null
                    ? objectMapper.writeValueAsString(job.getContext())
                    : null);
            int rows = jdbcTemplate.update(sql,
                    job.getId(),
                    job.getTenantId(),
                    job.getJobType().name(),
                    job.getReferenceId(),
                    SchedulerJobStatus.PENDING.name(),
                    job.getNextCheckAt(),
                    job.getAttemptCount(),
                    job.getMaxAttempts(),
                    contextObj,
                    job.getCreatedAt(),
                    job.getUpdatedAt());
            if (rows == 0) {
                log.warn("Scheduler job for referenceId={} type={} tenant={} already exists — duplicate insert skipped (ON CONFLICT DO NOTHING)",
                        job.getReferenceId(), job.getJobType(), job.getTenantId());
            }
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        } catch (JsonProcessingException | java.sql.SQLException e) {
            throw new CustomException("SCHEDULER_JOB_SERIALIZE_ERROR",
                    "Failed to serialize scheduler job context: " + e.getMessage());
        }
    }

    /**
     * Atomically claims up to {@code batchSize} PENDING jobs due for processing.
     * Uses a single CTE (UPDATE...RETURNING) — no race condition between SELECT and UPDATE.
     * {@code @Transactional} ensures the CTE executes within a single DB transaction.
     */
    @Transactional
    public List<SchedulerJob> claimBatch(String tenantId, int batchSize, long nowMs) {
        try {
            String sql = queryBuilder.claimBatch(tenantId);
            List<SchedulerJob> claimed = jdbcTemplate.query(sql, rowMapper,
                    System.currentTimeMillis(), nowMs, batchSize);
            if (!claimed.isEmpty()) {
                log.info("Claimed {} scheduler jobs for tenant {}", claimed.size(), tenantId);
            }
            return claimed;
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }

    /**
     * Batch-updates scheduler state for a list of jobs in a single batchUpdate call.
     * Avoids per-row round trips after processing.
     */
    public void batchUpdateStatus(List<SchedulerJob> jobs, String tenantId) {
        if (jobs.isEmpty()) return;
        try {
            String sql = queryBuilder.updateStatus(tenantId);
            long now = System.currentTimeMillis();
            jdbcTemplate.batchUpdate(sql, jobs, jobs.size(), (ps, job) -> {
                ps.setString(1, job.getSchedulerStatus().name());
                if (job.getNextCheckAt() != null) {
                    ps.setLong(2, job.getNextCheckAt());
                } else {
                    ps.setNull(2, java.sql.Types.BIGINT);
                }
                ps.setInt(3, job.getAttemptCount());
                ps.setLong(4, now);
                ps.setString(5, job.getId());
            });
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }

    /**
     * Updates scheduler state for a single job after processing.
     * Called per-job immediately after processJob() to minimise crash recovery window (RC-8).
     */
    public void updateStatus(SchedulerJob job, String tenantId) {
        try {
            String sql = queryBuilder.updateStatus(tenantId);
            long now = System.currentTimeMillis();
            jdbcTemplate.update(sql,
                    job.getSchedulerStatus().name(),
                    job.getNextCheckAt(),
                    job.getAttemptCount(),
                    now,
                    job.getId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }

    /**
     * Resets PROCESSING → PENDING for jobs whose {@code updated_at} is older than {@code stuckThresholdMs}.
     * Called by the recovery job to handle pod crashes mid-processing.
     */
    public int recoverStuck(String tenantId, long stuckThresholdMs) {
        try {
            String sql = queryBuilder.recoverStuck(tenantId);
            long now = System.currentTimeMillis();
            long stuckCutoff = now - stuckThresholdMs;
            long nextCheckAt = now + (stuckThresholdMs / 2);
            int count = jdbcTemplate.update(sql, nextCheckAt, now, stuckCutoff);
            if (count > 0) log.warn("Recovered {} stuck scheduler jobs for tenant {}", count, tenantId);
            return count;
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }

    /**
     * Finds all BILL_STATUS_POLL jobs sharing the given batchId (stored in JSONB context).
     * Used by the BILL_BATCH_EMAIL coordinator to check whether all sibling jobs have settled.
     */
    public List<SchedulerJob> findByBatchId(String tenantId, String batchId) {
        try {
            String sql = queryBuilder.findByBatchId(tenantId);
            return jdbcTemplate.query(sql, rowMapper, batchId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }

    /**
     * Deletes DONE/FAILED jobs older than {@code cleanupAfterMs} to keep the table small.
     */
    public int cleanup(String tenantId, long cleanupAfterMs) {
        try {
            String sql = queryBuilder.cleanup(tenantId);
            long cutoff = System.currentTimeMillis() - cleanupAfterMs;
            int count = jdbcTemplate.update(sql, cutoff);
            if (count > 0) log.info("Cleaned up {} completed scheduler jobs for tenant {}", count, tenantId);
            return count;
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
    }
}
