package org.egov.digit.expense.web.models.enums;

/**
 * Notification type for PAYMENTS.BILL workflow transitions.
 * Each type carries the role code of the users who should receive the email.
 */
public enum WorkflowNotificationType {

    /** Bill transitions to UNDER_REVIEW — notify PAYMENT_REVIEWERs. */
    REVIEW("bill-review-notification", "PAYMENT_REVIEWER"),

    /** Bill transitions to REVIEWED — notify PAYMENT_APPROVERs. */
    APPROVAL("bill-approval-notification", "PAYMENT_APPROVER");

    private final String mdmsIdentifier;
    private final String targetRole;

    WorkflowNotificationType(String mdmsIdentifier, String targetRole) {
        this.mdmsIdentifier = mdmsIdentifier;
        this.targetRole = targetRole;
    }

    public String getMdmsIdentifier() {
        return mdmsIdentifier;
    }

    public String getTargetRole() {
        return targetRole;
    }
}
