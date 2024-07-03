package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum {
    IN_PROGRESS("IN_PROGRESS"),

    SCHEDULED("SCHEDULED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),

    SUCCESSFUL("SUCCESSFUL"),
    ;

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
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
