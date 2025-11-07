package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * BillingPeriodSearchRequest
 *
 * Request object for searching billing periods from expense-calculator service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPeriodSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("searchCriteria")
    private BillingPeriodSearchCriteria searchCriteria;
}
