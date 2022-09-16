package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class LOISearchRequest {

    @JsonProperty("requestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("searchCriteria")
    private LOISearchCriteria searchCriteria = null;
}
