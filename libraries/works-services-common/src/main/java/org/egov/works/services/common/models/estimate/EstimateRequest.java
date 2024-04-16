package org.egov.works.services.common.models.estimate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("estimate")
    @Valid
    private Estimate estimate = null;

    @JsonProperty("workflow")
    private Workflow workflow = null;
}

