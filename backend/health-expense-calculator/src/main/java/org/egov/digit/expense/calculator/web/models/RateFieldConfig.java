package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateFieldConfig {

    @JsonProperty("fieldKey")
    private String fieldKey;

    @JsonProperty("valueType")
    private String valueType;

    @JsonProperty("paymentType")
    private String paymentType;

    @JsonProperty("components")
    private List<String> components;

    @JsonProperty("percentageKey")
    private String percentageKey;

    @JsonProperty("billAmountKey")
    private String billAmountKey;

    @JsonProperty("reportDetailKey")
    private String reportDetailKey;

    @JsonProperty("isPayable")
    private Boolean isPayable;

    @JsonProperty("order")
    private Integer order;

    @JsonProperty("columnLabelKey")
    private String columnLabelKey;

    @JsonProperty("totalColumnLabelKey")
    private String totalColumnLabelKey;
}
