package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of request processing - to be enhanced in future to include INPROGRESS
 */
public enum ResponseStatus {

    SUCCESSFUL("SUCCESSFUL"),

    FAILED("FAILED");

    private String value;

    ResponseStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ResponseStatus fromValue(String text) {
        for (ResponseStatus b : ResponseStatus.values()) {
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