package org.egov.web.models.organisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationStatus {
    DEBARRED("DEBARRED"),

    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE");

    private String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ApplicationStatus fromValue(String text) {
        for (ApplicationStatus b : ApplicationStatus.values()) {
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
