package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillingConfig - Simplified model for muster-roll service needs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingConfig {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("projectId")
    private String projectId;

    @JsonProperty("campaignNumber")
    private String campaignNumber;

    @JsonProperty("billingFrequency")
    private String billingFrequency;

    @JsonProperty("customFrequencyDays")
    private Integer customFrequencyDays;

    @JsonProperty("projectStartDate")
    private Long projectStartDate;

    @JsonProperty("projectEndDate")
    private Long projectEndDate;

    @JsonProperty("status")
    private String status;
}
