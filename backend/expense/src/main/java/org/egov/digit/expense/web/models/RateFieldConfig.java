package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static org.egov.digit.expense.config.Constants.*;

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

    /** Resolved head code for the line item — set directly by MDMS; derived from headCodeMapping in snapshot path. */
    @JsonProperty("headCode")
    private String headCode;

    @JsonProperty("percentageKey")
    private String percentageKey;

    @JsonProperty("isPayable")
    private Boolean isPayable;

    @JsonProperty("order")
    private Integer order;

    @JsonProperty("columnLabelKey")
    private String columnLabelKey;

    /**
     * Fallback fieldConfig used when MDMS has no fieldConfig defined for the campaign.
     * Mirrors RateFieldConfigConstants.DEFAULT_FIELD_CONFIGS from health-expense-calculator.
     */
    public static final List<RateFieldConfig> DEFAULT_FIELD_CONFIGS = List.of(
            RateFieldConfig.builder()
                    .fieldKey("PER_DAY").headCode("PER_DAY")
                    .valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .columnLabelKey("PDF_STATIC_LABEL_BILL_TABLE_WAGE")
                    .isPayable(true).order(1).build(),
            RateFieldConfig.builder()
                    .fieldKey("FOOD").headCode("FOOD")
                    .valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .columnLabelKey("PDF_STATIC_LABEL_BILL_TABLE_FOOD")
                    .isPayable(true).order(2).build(),
            RateFieldConfig.builder()
                    .fieldKey("TRAVEL").headCode("TRAVEL")
                    .valueType(VALUE_TYPE_FLAT).paymentType(PAYMENT_TYPE_PER_DAY)
                    .columnLabelKey("PDF_STATIC_LABEL_BILL_TABLE_TRANSPORTATION")
                    .isPayable(true).order(3).build()
    );
}
