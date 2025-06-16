package org.egov.digit.expense.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of the Property
 */
public enum Actions {

    CREATE ("CREATE"),
    VERIFY ("VERIFY"),
    PARTIALLY_VERIFY ("PARTIALLY_VERIFY"),
    MAKE_PARTIAL_PAYMENT ("MAKE_PARTIAL_PAYMENT"),
    DECLINE ("DECLINE"),
    MAKE_FULL_PAYMENT ("MAKE_FULL_PAYMENT"),

    REFUTE ("REFUTE"),
    SEND_BACK_FOR_EDIT ("SEND_BACK_FOR_EDIT"),
    EDIT ("EDIT"),
    MAKE_PAYMENT ("MAKE_PAYMENT"),
    FAIL_PAYMENT ("FAIL_PAYMENT");

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
