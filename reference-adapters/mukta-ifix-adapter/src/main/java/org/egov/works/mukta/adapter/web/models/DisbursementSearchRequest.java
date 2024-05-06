package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementSearchRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("criteria")
    private DisbursementSearchCriteria criteria;

    @JsonProperty("pagination")
    private Pagination pagination;
}
