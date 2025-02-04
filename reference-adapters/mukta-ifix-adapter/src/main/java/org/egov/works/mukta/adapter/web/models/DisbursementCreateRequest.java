package org.egov.works.mukta.adapter.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.Valid;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisbursementCreateRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;
    @JsonProperty
    @Valid
    private Disbursement disbursement;
}
