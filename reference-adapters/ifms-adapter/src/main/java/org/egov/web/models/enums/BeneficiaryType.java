package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;

public enum BeneficiaryType {

    ORG("ORG"),
    IND("IND"),

    DEPT("DEPT");

    private String value;

    BeneficiaryType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
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
}
