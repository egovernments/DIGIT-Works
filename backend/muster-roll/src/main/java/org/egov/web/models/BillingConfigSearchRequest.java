package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * BillingConfigSearchRequest - Request to search billing config
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingConfigSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("searchCriteria")
    private BillingConfigSearchCriteria searchCriteria;
}
