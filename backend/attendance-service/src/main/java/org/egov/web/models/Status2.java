package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The attendance log of the cancelled event can be marked as inactive.
 */
public enum Status2 {

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    private final String value;

    Status2(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Status2 fromValue(String text) {
        for (Status2 b : Status2.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

