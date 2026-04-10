package org.egov.digit.expense.web.models.enums;

/**
 * Extensible job-type registry for the generic scheduler.
 * Add a new constant here and provide a matching {@code SchedulerJobHandler} implementation.
 */
public enum SchedulerJobType {
    /** Polls MTN transfer status for a payment task until it settles. */
    TASK_STATUS_CHECK
}
