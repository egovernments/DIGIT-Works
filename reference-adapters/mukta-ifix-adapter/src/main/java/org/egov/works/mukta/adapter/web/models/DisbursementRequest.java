package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementRequest {
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    @Valid
    private MsgHeader header;
    @JsonProperty("message")
    @Valid
    private Disbursement message;
}
