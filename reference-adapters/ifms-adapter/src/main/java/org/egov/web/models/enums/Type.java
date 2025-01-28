package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    INITIAL("INITIAL"),
    ADDITIONAL("ADDITIONAL"),
    DEDUCTION("DEDUCTION");

    private String value;
    Type(String value){
        this.value =value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
