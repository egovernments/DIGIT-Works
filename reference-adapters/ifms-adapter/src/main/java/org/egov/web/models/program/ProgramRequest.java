package org.egov.web.models.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.web.models.MsgCallbackHeader;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRequest {

    private String id;
    @JsonProperty("signature")
    private String signature;

    @JsonProperty("header")
    @NotNull
    @Valid
    private MsgCallbackHeader header;

    @JsonProperty("message")
    @NotNull
    @Valid
    private Program program;

}
