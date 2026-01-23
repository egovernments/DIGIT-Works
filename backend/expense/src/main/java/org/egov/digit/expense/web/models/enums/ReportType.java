package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for bill transaction report type
 */
public enum ReportType {

    EXCEL("EXCEL"),

    PDF("PDF");

    private String value;

    ReportType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ReportType fromValue(String text) {
        for (ReportType b : ReportType.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
