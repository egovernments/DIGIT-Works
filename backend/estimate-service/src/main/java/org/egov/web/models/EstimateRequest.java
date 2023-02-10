package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.ProcessInstance;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Request for Estimate _create and _update api&#39;s
 */
@ApiModel(description = "Request for Estimate _create and _update api's")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("estimate")
    private Estimate estimate = null;

    @JsonProperty("workflow")
    private Workflow workflow = null;
}

