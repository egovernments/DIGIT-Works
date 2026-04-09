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
    FULLY_PAY("FULLY_PAY"),

    VERIFY ("VERIFY"),
    PAY("PAY"),
    IGNORE_ERRORS_AND_VERIFY("IGNORE_ERRORS_AND_VERIFY"),
    SEND_FOR_REVIEW("SEND_FOR_REVIEW"),
    SEND_FOR_APPROVAL("SEND_FOR_APPROVAL"),
    PAYMENT_INITIATION("PAYMENT_INITIATION"),
    FAILED("FAILED"),
    VERIFICATION_SUCCESS("VERIFICATION_SUCCESS");

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
