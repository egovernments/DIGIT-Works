package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;

/**
 * BillingConfigResponse - Response containing billing config
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingConfigResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("BillingConfig")
    private BillingConfig billingConfig;

    @JsonProperty("periods")
    private List<BillingPeriod> periods;

    @JsonProperty("totalPeriods")
    private Integer totalPeriods;
}
