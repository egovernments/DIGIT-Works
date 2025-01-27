package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateDetail {

    @JsonProperty("rate")
    private Double rate = null;

    @JsonProperty("sorId")
    private String sorId = null;

    @JsonProperty("validFrom")
    private String validFrom = null;

    @JsonProperty("validTo")
    private String validTo = null;

}
