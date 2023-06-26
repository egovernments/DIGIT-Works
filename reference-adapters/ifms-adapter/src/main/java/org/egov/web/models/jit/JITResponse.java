package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.enums.JITServiceId;

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

    @JsonProperty("data")
    private List<Object> data;
}
