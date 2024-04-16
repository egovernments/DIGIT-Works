package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.validation.constraints.Max;


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
