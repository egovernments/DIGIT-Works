package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementRequest {
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    private MsgCallbackHeader header;
    @JsonProperty("message")
    private Disbursement message;
}
