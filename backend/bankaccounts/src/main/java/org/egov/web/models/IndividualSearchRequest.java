package org.egov.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * IndividualSearchRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-27T11:47:19.561+05:30")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualSearchRequest {
    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("Individual")
    @NotNull
    @Valid
    private IndividualSearch individual = null;

}
