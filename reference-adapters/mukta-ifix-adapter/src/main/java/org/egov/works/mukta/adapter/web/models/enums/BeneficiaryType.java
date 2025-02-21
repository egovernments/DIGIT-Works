package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum BeneficiaryType {

    ORG("ORG"),
    IND("IND"),

    DEPT("DEPT");

    private String value;

    BeneficiaryType(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static BeneficiaryType fromValue(String text) {
        for (BeneficiaryType b : BeneficiaryType.values()) {
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
