package org.egov.works.measurement.web.models;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Sorting order
 */
public enum Order {
    ASC("asc"), DESC("desc");

    private String value;

    Order(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Order fromValue(String text) {
        for (Order b : Order.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
