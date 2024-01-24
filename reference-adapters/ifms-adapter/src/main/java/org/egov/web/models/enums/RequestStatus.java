package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatus {
    RCVD("rcvd"),
    PDNG("pdng"),
    SUCC("succ"),
    RJCT("rjct");

    private final String value;
    RequestStatus(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static RequestStatus fromString(String value) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid RequestStatus value: " + value);
    }
}
