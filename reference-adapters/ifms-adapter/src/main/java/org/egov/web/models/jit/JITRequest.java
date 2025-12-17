package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.enums.JITServiceId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JITRequest {
    @JsonProperty("serviceId")
    private JITServiceId serviceId;

    @JsonProperty("params")
    private Object params;
}
