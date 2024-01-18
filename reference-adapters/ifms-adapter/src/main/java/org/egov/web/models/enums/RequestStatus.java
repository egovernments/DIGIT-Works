package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatus {
    RCVD("rcvd"),
    PDNG("pdng"),
    SUCC("succ"),
    RJCT("rjct");

    private String value;
    RequestStatus(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
