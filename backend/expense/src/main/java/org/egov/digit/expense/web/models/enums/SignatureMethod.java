package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for the method used to capture an approver's signature
 */
public enum SignatureMethod {

    DRAWN("DRAWN"),

    UPLOADED("UPLOADED");

    private String value;

    SignatureMethod(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SignatureMethod fromValue(String text) {
        for (SignatureMethod b : SignatureMethod.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
