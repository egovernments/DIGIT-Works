package org.egov.wms.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregationResponse {

    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;

    @JsonProperty("aggsResponse")
    private AggsResponse aggsResponse;

}
