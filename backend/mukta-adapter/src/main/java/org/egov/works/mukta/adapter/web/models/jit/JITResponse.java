package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.works.mukta.adapter.web.models.enums.JITServiceId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JITResponse {
    @JsonProperty("serviceId")
    private JITServiceId serviceId;

    @JsonProperty("errorMsg")
    private String errorMsg;

    @JsonProperty("errorMsgs")
    private List<Object> errorMsgs;

    @JsonProperty("data")
    private List<Object> data;
}
