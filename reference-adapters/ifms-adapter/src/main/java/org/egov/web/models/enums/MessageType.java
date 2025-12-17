package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {
    PROGRAM("program"),
    ON_PROGRAM("on-program"),
    SANCTION("sanction"),
    ON_SANCTION("on-sanction"),
    ALLOCATION("allocation"),
    ON_ALLOCATION("on-allocation"),
    DISBURSE("disburse"),
    ON_DISBURSE("on-disburse"),
    DEMAND("demand"),
    ON_DEMAND("on-demand"),
    RECEIPT("receipt"),
    ON_RECEIPT("on-receipt"),
    BILL("bill"),
    ON_BILL("on-bill");

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static MessageType fromValue(String text) {
        for (MessageType b : MessageType.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}