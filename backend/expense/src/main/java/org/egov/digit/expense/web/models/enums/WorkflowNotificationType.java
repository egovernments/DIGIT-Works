package org.egov.digit.expense.web.models.enums;

/**
 * Notification type for PAYMENTS.BILL workflow transitions.
 * Add a new constant here and provide a matching MDMS entry to support additional notification types.
 */
public enum WorkflowNotificationType {

    /** Bill sent to PAYMENT_REVIEWER for review. */
    REVIEW("bill-review-notification"),

    /** Bill sent to PAYMENT_APPROVER for approval. */
    APPROVAL("bill-approval-notification");

    private final String mdmsIdentifier;

    WorkflowNotificationType(String mdmsIdentifier) {
        this.mdmsIdentifier = mdmsIdentifier;
    }

    public String getMdmsIdentifier() {
        return mdmsIdentifier;
    }
}
