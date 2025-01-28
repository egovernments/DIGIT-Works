package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum StatusCode {
    RECEIVED("RECEIVED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    INPROCESS("INPROCESS"),
    INITIATED("INITIATED"),
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED"),
    PARTIAL("PARTIAL"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR"),
    INFO("INFO");

    private String value;

    StatusCode(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static StatusCode fromValue(String text) {
        for (StatusCode b : StatusCode.values()) {
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
