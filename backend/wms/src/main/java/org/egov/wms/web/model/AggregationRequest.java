package org.egov.wms.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregationRequest {

    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    private RequestInfo requestInfo;

    @JsonProperty("searchCriteria")
    @NotNull
    @Valid
    private AggregationSearchCriteria aggregationSearchCriteria ;

}
