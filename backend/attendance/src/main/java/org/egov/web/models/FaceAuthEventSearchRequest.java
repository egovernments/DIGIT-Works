package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceAuthEventSearchRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("faceAuthEventSearchCriteria")
    private FaceAuthEventSearchCriteria faceAuthEventSearchCriteria;
}
