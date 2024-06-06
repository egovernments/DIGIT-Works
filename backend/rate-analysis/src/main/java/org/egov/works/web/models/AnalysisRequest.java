package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * AnalysisRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalysisRequest {

    @JsonProperty("RequestInfo")
    @NonNull
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("sorDetails")
    @Valid
    private SorDetails sorDetails = null;


}
