package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;

import jakarta.validation.Valid;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementCreateResponse {
    @JsonProperty("ResponseInfo")
    private ResponseInfo responseInfo;
    @JsonProperty("Disbursement")
    @Valid
    private Disbursement disbursement;
}
