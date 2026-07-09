package org.egov.digit.expense.service.scheduler;

public enum SchedulerJobResult {
    /** Job completed — mark DONE. */
    DONE,
    /** Job still in progress — reschedule with backoff. */
    RETRY,
    /** Job permanently failed — mark FAILED. */
    FAILED
}
