package org.egov.web.models.program;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.web.models.MsgCallbackHeader;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramSearchResponse {

    @JsonProperty("header")
    MsgCallbackHeader header;

    @JsonProperty("programs")
    List<Program> programs;

}
