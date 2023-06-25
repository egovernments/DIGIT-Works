package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SanctionDetailsSearchCriteria {
    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("hoaCode")
    private String hoaCode = null;

    @JsonProperty("ddoCode")
    private String ddoCode = null;

    @JsonProperty("masterAllotmentId")
    private String masterAllotmentId = null;
}
