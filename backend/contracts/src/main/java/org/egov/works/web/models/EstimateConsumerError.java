package org.egov.works.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateConsumerError {

    @JsonProperty("exception")
    private Exception exception;

    @JsonProperty("estimateRequest")
    private EstimateRequest estimateRequest;
}
