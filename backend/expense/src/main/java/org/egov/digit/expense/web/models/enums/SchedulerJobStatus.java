package org.egov.digit.expense.web.models.enums;

public enum SchedulerJobStatus {
    /** Waiting to be claimed by the scheduler. */
    PENDING,
    /** Claimed by a pod — currently being processed. */
    PROCESSING,
    /** Completed successfully — will be cleaned up. */
    DONE,
    /** Exceeded max attempts or max duration — polling stopped. */
    FAILED
}
