package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.validation.constraints.NotNull;

/**
 * MusterRollRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRollRequest {
    @JsonProperty("RequestInfo")
    @NotNull(message = "Request info is mandatory")
    private RequestInfo requestInfo = null;

    @JsonProperty("musterRoll")
    @NotNull(message = "Muster Roll is mandatory")
    private MusterRoll musterRoll = null;

    @JsonProperty("workflow")
    private Workflow workflow = null;


}

