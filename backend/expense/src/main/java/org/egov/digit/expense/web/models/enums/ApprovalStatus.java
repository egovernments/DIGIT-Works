package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for the status recorded against a bill approval signature record
 */
public enum ApprovalStatus {

    APPROVED("APPROVED");

    private String value;

    ApprovalStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ApprovalStatus fromValue(String text) {
        for (ApprovalStatus b : ApprovalStatus.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
