package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of the Property
 */
public enum Actions {

    CREATE ("CREATE"),
    FULLY_VERIFY("FULLY_VERIFY"),
    PARTIALLY_VERIFY ("PARTIALLY_VERIFY"),
    PARTIALLY_PAY("PARTIALLY_PAY"),
    DECLINE_PAYMENT("DECLINE_PAYMENT"),
    FULLY_PAY("FULLY_PAY"),

    VERIFY ("VERIFY"),
    REFUTE ("REFUTE"),
    SEND_FOR_EDIT("SEND_FOR_EDIT"),
    EDIT ("EDIT"),
    PAY("PAY"),
    DECLINE ("DECLINE");

    private String value;

    Actions(String value) {
    this.value = value;
  }

    @Override
    @JsonValue
    public String toString() {
    return String.valueOf(value);
    }

    @JsonCreator
    public static Actions fromValue(String text) {
        for (Actions b : Actions.values()) {
            if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
