package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * UpdateEstimate
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEstimate {

    @JsonProperty("requestInfo")
    private RequestHeader requestInfo = null;

    @JsonProperty("estimate")
    private Estimate estimate = null;

    @JsonProperty("workflow")
    private EstimateRequestWorkflow workflow = null;
}

