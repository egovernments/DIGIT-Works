package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum BeneficiaryPaymentStatus {

    PENDING("Payment Pending"),
    INITIATED("Payment Initiated"),

    IN_PROCESS("Payment In Process"),

    SUCCESS("Payment Successful"),

    FAILED("Payment Failed");

    private String value;

    BeneficiaryPaymentStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static BeneficiaryPaymentStatus fromValue(String text) {
        for (BeneficiaryPaymentStatus b : BeneficiaryPaymentStatus.values()) {
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
