package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementRequest {
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    private MsgCallbackHeader header;
    @JsonProperty("message")
    private Disbursement message;
}
