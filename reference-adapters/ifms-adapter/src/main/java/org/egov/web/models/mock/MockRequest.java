package org.egov.web.models.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.web.models.jit.JITRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MockRequest {
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("jitRequest")
    private JITRequest jitRequest;
}
