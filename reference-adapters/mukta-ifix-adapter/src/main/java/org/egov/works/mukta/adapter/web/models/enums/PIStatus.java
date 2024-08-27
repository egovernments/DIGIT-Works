package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum PIStatus {

    FAILED("FAILED"),
    INITIATED("INITIATED"),
    PARTIAL("PARTIAL"),
    APPROVED("APPROVED"),
    IN_PROCESS("IN PROCESS"),
    COMPLETED("COMPLETED"),
    SUCCESSFUL("SUCCESSFUL");

    private String value;

    PIStatus(String value) {
        this.value = value;
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

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
