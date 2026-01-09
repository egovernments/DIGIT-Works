package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BillingPeriodSearchCriteria
 *
 * Search criteria for querying billing periods from expense-calculator service.
 * Simplified version for muster-roll service needs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPeriodSearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("ids")
    private List<String> ids;

    @JsonProperty("billingConfigId")
    private String billingConfigId;

    @JsonProperty("campaignNumber")
    private String campaignNumber;

    @JsonProperty("periodNumber")
    private Integer periodNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;
}
