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
public class PaymentsConfig {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("lowestLevelBoundary")
    private String lowestLevelBoundary;

    @JsonProperty("enableApprovalAnyTime")
    private Boolean enableApprovalAnyTime;
}
