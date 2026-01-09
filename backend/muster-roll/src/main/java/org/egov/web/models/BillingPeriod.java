package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillingPeriod model for V2 period-aware muster roll creation.
 * Simplified version containing only fields needed by muster-roll service.
 * Full model exists in health-expense-calculator service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPeriod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("billingConfigId")
    private String billingConfigId;

    @JsonProperty("campaignNumber")
    private String campaignNumber;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("periodNumber")
    private Integer periodNumber;

    @JsonProperty("periodStartDate")
    private Long periodStartDate;

    @JsonProperty("periodEndDate")
    private Long periodEndDate;

    @JsonProperty("billingFrequency")
    private String billingFrequency;

    @JsonProperty("status")
    private String status;

    @JsonProperty("billId")
    private String billId;
}
