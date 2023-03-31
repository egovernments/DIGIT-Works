package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * OrgSearchRequest
 */
@Validated

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("SearchCriteria")
    private OrgSearchCriteria searchCriteria = null;

    @JsonProperty("Pagination")
    private Pagination pagination = null;
}
