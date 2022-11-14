package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets status3
 */
public enum Status3 {

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    private final String value;

    Status3(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Status3 fromValue(String text) {
        for (Status3 b : Status3.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

