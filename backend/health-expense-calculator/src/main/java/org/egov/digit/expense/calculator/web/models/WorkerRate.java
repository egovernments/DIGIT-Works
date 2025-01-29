package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerRate {

    @JsonProperty("skillCode")
    private String skillCode;

    @JsonProperty("rateBreakup")
    private Map<String, BigDecimal> rateBreakup;

}
