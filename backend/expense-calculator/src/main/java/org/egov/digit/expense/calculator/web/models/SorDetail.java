package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SorDetail {

    @JsonProperty("id")
    private String id;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("sorType")
    private String sorType;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("sorSubType")
    private String sorSubType;

    @JsonProperty("sorVariant")
    private String sorVariant;

    @JsonProperty("description")
    private String description;

}
