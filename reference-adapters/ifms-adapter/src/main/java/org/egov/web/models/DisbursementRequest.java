package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisbursementRequest {
    @JsonProperty("signature")
    @NotNull
    private String signature;
    @JsonProperty("header")
    @NotNull
    @Valid
    private MsgCallbackHeader header;
    @JsonProperty("message")
    @NotNull
    @Valid
    private Disbursement message;
}
