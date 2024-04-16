package org.egov.works.services.common.models.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractRequest {
    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("contract")
    @Valid
    private Contract contract = null;

    @JsonProperty("workflow")
    @Valid
    private Workflow workflow = null;
}

