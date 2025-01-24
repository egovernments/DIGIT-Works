package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StaffType {

    EDITOR("EDITOR"),

    APPROVER("APPROVER"),

    OWNER("OWNER");

    private final String value;

    StaffType(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static StaffType fromValue(String text) {
        for (StaffType b : StaffType.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}