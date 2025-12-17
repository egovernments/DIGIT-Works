package org.egov.works.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * Request for Estimate _create and _update api&#39;s
 */
@ApiModel(description = "Request for Estimate _create and _update api's")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

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

