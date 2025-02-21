package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum PIType {

    ORIGINAL("ORIGINAL"),
    REVISED("REVISED");

    private String value;

    PIType(String value) {
        this.value = value;
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

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
