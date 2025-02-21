package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementResponse {
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    private MsgHeader header;
    @JsonProperty("message")
    private Disbursement message;
}
