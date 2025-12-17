package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * Pagination details
 */
@ApiModel(description = "Pagination details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    @JsonProperty("limit")
    @Valid
//    @DecimalMax("100")
    private Integer limit = null;

    @JsonProperty("offSet")
    @Valid
    private Integer offSet = null;

    @JsonProperty("totalCount")
    @Valid
    private Integer totalCount = null;

    @JsonProperty("sortBy")
    private String sortBy = null;

    @JsonProperty("order")
    private OrderEnum order = null;

    /**
     * Sorting order
     */
    public enum OrderEnum {
        ASC("asc"),

        DESC("desc");

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

