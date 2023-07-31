package org.egov.ifms.jit.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.ifms.jit.web.models.enums.JITServiceId;

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
