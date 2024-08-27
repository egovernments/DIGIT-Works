package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

/**
 * enum value for the payment status
 */
public enum PaymentStatus {

    INITIATED("INITIATED"),

    SUCCESSFUL("SUCCESSFUL"),

    FAILED("FAILED"),

    CANCELLED("CANCELLED"),
    PARTIAL("PARTIAL");

    private String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static PaymentStatus fromValue(String text) {
        for (PaymentStatus b : PaymentStatus.values()) {
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
