package org.egov.works.services.common.models.expense;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.model.CustomException;

import jakarta.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {

    @JsonProperty("limit")
    @Valid
    private Integer limit;

    @JsonProperty("offSet")
    @Valid
    private Integer offSet;

    @JsonProperty("totalCount")
    @Valid
    private Integer totalCount;

    @JsonProperty("sortBy")
    private String sortBy;

    @JsonProperty("order")
    private OrderEnum order;

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
                if (String.valueOf(b.value).equalsIgnoreCase(text)) {
                    return b;
                }
            }
			throw new CustomException("EG_EXPENSE_SEARCH_INVALID_ORDER",
					"The order value provided : " + text + " is wrong. it can only be ASC or DESC");
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

}
