package org.egov.works.mukta.adapter.web.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.request.RequestInfo;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisbursementCreateRequest {
    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;
    @JsonProperty
    private Disbursement disbursement;
}
