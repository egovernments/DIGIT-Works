package org.egov.works.mukta.adapter.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.NotNull;

public enum Action {
    CREATE("create"),
    UPDATE("update"),
    SEARCH("search");

    private String value;
    Action(String value) {
        this.value = value;
    }

    @JsonCreator
    @NotNull
    public static Action fromValue(String text) {
        for (Action b : Action.values()) {
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
