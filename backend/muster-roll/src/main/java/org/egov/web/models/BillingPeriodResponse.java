package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;

/**
 * BillingPeriodResponse for fetching billing period details from expense-calculator service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPeriodResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("billingPeriods")
    private List<BillingPeriod> billingPeriods;

    @JsonProperty("totalCount")
    private Integer totalCount;
}
