package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;

public enum PIType {

    ORIGINAL("ORIGINAL"),
    REVISED("REVISED");

    private String value;

    PIType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    @NotNull
    public static PIType fromValue(String text) {
        for (PIType b : PIType.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
