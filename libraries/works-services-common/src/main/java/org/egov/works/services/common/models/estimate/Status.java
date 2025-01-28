package org.egov.works.services.common.models.estimate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    DRAFT("DRAFT"),

    INWORKFLOW("INWORKFLOW"),

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    private String value;

    Status(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Status fromValue(String text) {
        for (Status b : Status.values()) {
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
