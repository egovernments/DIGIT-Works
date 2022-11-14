package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of request processing
 */
public enum Status1 {

    COMPLETED("COMPLETED"),

    ACCEPTED("ACCEPTED"),

    FAILED("FAILED");

    private final String value;

    Status1(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Status1 fromValue(String text) {
        for (Status1 b : Status1.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

