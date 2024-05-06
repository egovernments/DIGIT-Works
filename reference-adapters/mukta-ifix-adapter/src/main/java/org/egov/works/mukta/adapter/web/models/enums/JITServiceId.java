package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of the Property
 */
public enum JITServiceId {
    PA("PA");

    private String value;

    JITServiceId(String value) {
        this.value = value;
    }

    @JsonCreator
    public static JITServiceId fromValue(String text) {
        for (JITServiceId b : JITServiceId.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
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
