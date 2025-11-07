package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillingConfigSearchCriteria - Search criteria for billing config
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingConfigSearchCriteria {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("campaignNumber")
    private String campaignNumber;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("status")
    private String status;
}
