package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

    DEBIT("DEBIT"),

    REVERSAL("REVERSAL");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TransactionType fromValue(String text) {
        for (TransactionType b : TransactionType.values()) {
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
