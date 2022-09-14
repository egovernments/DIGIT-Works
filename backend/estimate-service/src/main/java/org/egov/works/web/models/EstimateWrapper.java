package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateWrapper {

    @JsonProperty("estimate")
    private Estimate estimate = null;

    @JsonProperty("workflow")
    private EstimateRequestWorkflow workflow = null;
}
