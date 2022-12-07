package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Request for Estimate _create and _update api&#39;s
 */
@ApiModel(description = "Request for Estimate _create and _update api's")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateRequest {

    @JsonProperty("RequestInfo")
    @NotNull(message = "Request info is mandatory")
    private RequestInfo requestInfo = null;

    @JsonProperty("estimate")
    @NotNull(message = "Estimate is mandatory")
    private Estimate estimate = null;

    @JsonProperty("workflow")
    private EstimateRequestWorkflow workflow = null;
}

