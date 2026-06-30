package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerMdms {

    private String campaignId;
    private String eventType;
    private String currency;
    private List<WorkerRate> rates;

    @JsonProperty("fieldConfig")
    private List<RateFieldConfig> fieldConfig;

    @JsonProperty("headCodeMapping")
    private Map<String, String> headCodeMapping;
}
