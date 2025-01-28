package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

/**
 * OrgServiceRequest
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgServiceRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo = null;

    @JsonProperty("organisations")
    private List<Organisation> organisations = null;

    @JsonProperty("workflow")
    @Valid
    private Workflow workflow = null;

}
