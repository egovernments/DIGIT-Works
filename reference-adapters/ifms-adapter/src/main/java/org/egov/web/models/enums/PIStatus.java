package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;

public enum PIStatus {

    PENDING("Pending"),
    DECLINED("Declined"),
    INITIATED("Initiated"),
    REJECTED("Rejected"),
    APPROVED("Approved"),
    IN_PROCESS("In Process"),
    COMPLETED("Completed");

    private String value;

    PIStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    @NotNull
    public static PIStatus fromValue(String text) {
        for (PIStatus b : PIStatus.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
