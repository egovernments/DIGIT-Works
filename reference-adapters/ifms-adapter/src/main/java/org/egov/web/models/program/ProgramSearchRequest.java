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
public class ProgramSearchRequest {

    @JsonProperty("signature")
    private String signature;

    @NotNull
    @JsonProperty("header")
    @Valid
    private MsgCallbackHeader header;

    @JsonProperty("message")
    @NotNull
    @Valid
    private ProgramSearch programSearch;

}
