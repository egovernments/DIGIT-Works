package org.egov.digit.expense.web.models.enums;

/**
 * Extensible job-type registry for the generic scheduler.
 * Add a new constant here and provide a matching {@code SchedulerJobHandler} implementation.
 */
public enum SchedulerJobType {

    /** Safety-net aggregator: checks all bill details settled and drives bill-level WF transition. */
    BILL_STATUS_POLL,

    /** Transitions a single BillDetail WF (non-external phases: IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL). */
    DETAIL_WF_UPDATE,

    /** Coordinator: fires ONE batch email after all BILL_STATUS_POLL jobs in a bulk request settle. */
    BILL_BATCH_EMAIL,

    /** Coordinator: waits for all eligible bill details to enter the active phase before dispatching verify/pay work. */
    BILL_STARTED_CHECK,

    /** Per-detail: polls payment provider for verification status. */
    BILL_DETAILS_TASK_VERIFY_CHECK,

    /** Per-detail: polls payment provider for payment status. */
    BILL_DETAILS_TASK_PAYMENT_STATUS_CHECK
}
