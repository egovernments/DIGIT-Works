package org.egov.digit.expense.web.models.enums;

/**
 * Extensible job-type registry for the generic scheduler.
 * Add a new constant here and provide a matching {@code SchedulerJobHandler} implementation.
 */
public enum SchedulerJobType {
    /** Polls MTN transfer status for a payment task until it settles. */
    TASK_STATUS_CHECK,

    /** Safety-net aggregator: checks all bill details settled and drives bill-level WF transition. */
    BILL_STATUS_POLL,

    /** (Legacy) Transitions ALL bill detail WFs for a bill in a single batch job. Replaced by DETAIL_WF_UPDATE. */
    BILL_DETAIL_WF_UPDATE,

    /** Crash/restart recovery for a Verify task — re-runs verification for any stuck bill details. */
    TASK_VERIFY_CHECK,

    // ── Per-detail job types (Phase B) ───────────────────────────────────────
    // Each BillDetail gets its own job; after every detail settles the handler
    // calls BillAggregationService to drive the bill-level transition exactly once.

    /** Runs MTN/Bank verification for a single BillDetail. Replaces Verify Task + TaskConsumer for verify. */
    DETAIL_VERIFY,

    /** Transitions a single BillDetail WF (non-external phases: IGNORE_ERRORS, SEND_FOR_REVIEW, SEND_FOR_APPROVAL, PAYMENT_INITIATION). */
    DETAIL_WF_UPDATE,

    /** Coordinator: fires ONE batch email after all BILL_STATUS_POLL jobs in a bulk request settle. */
    BILL_BATCH_EMAIL
}
