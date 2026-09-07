package org.egov.digit.expense.util;

import java.util.Set;

import static org.egov.digit.expense.config.Constants.*;

/**
 * Which set of fields a user may actually change on a bill, given their roles AND the bill's
 * current status. A user holding both payment roles acts in one mode at a time, decided by the
 * bill's stage — so role alone is not enough to tell what is editable.
 *
 * Precedence mirrors BillValidator.stripAndWarnBlockedFields, which checks the editor branch
 * first and returns; the Excel template and its parser must agree with it, or the sheet offers
 * edits the validator will strip.
 */
public enum BillUpdateMode {

    /** Payee fields only — amount/attendance/lineItem fields are stripped. */
    EDITOR,

    /** Amount fields only — totalAttendance, lineItems, totalAmount. Payee fields are stripped. */
    REVIEWER,

    /** Neither role applies at this bill status. */
    NONE;

    /** billStatus is the Bill.status enum or its string form; null means unknown. */
    public static BillUpdateMode resolve(Set<String> userRoles, Object billStatus) {
        if (userRoles == null || billStatus == null) return NONE;
        String status = billStatus.toString();

        boolean editorStatus = STATUS_PENDING_VERIFICATION.equals(status)
                || STATUS_PARTIALLY_VERIFIED.equals(status);
        boolean reviewerStatus = STATUS_UNDER_REVIEW.equals(status);

        if (userRoles.contains(ROLE_PAYMENT_EDITOR) && editorStatus) return EDITOR;
        if (userRoles.contains(ROLE_PAYMENT_REVIEWER) && reviewerStatus) return REVIEWER;
        return NONE;
    }
}
