package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;

/**
 * Pagination details
 */
@Schema(description = "Pagination details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Pagination {

    @JsonProperty("limit")
    @Max(100)
    private Integer limit = 10;

    @JsonProperty("offSet")
    private Integer offSet = 0;

    @JsonProperty("totalCount")
    private Integer totalCount = null;

    @JsonProperty("sortBy")
    private String sortBy = null;

    @JsonProperty("order")
    private OrderEnum order = null;

    /**
     * Sorting order
     */
    public enum OrderEnum {
        ASC("ASC"),

        DESC("DESC");

        private String value;

        OrderEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static OrderEnum fromValue(String text) {
            for (OrderEnum b : OrderEnum.values()) {
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

}
