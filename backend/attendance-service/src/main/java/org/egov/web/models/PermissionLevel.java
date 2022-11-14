package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets PermissionLevel
 */
public enum PermissionLevel {

    FILL_ATTENDANCE("FILL_ATTENDANCE"),

    MANAGE_PERMISSIONS("MANAGE_PERMISSIONS"),

    VIEW("VIEW");

    private final String value;

    PermissionLevel(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PermissionLevel fromValue(String text) {
        for (PermissionLevel b : PermissionLevel.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

