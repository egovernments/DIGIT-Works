package org.egov.digit.expense.service.scheduler;

import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;

/**
 * Strategy interface for scheduler job handlers.
 * Implement this interface and annotate with {@code @Component} to register
 * a new job type — the {@code SchedulerService} auto-discovers all implementations.
 */
public interface SchedulerJobHandler {

    /** The job type this handler is responsible for. */
    SchedulerJobType getJobType();

    /**
     * Process a single claimed job.
     *
     * @param job the claimed job with {@code context} populated
     * @return {@link SchedulerJobResult#DONE} if resolved,
     *         {@link SchedulerJobResult#RETRY} if still pending,
     *         {@link SchedulerJobResult#FAILED} on permanent failure
     */
    SchedulerJobResult handle(SchedulerJob job);
}
