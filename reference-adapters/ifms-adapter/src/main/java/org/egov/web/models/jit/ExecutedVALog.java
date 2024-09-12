package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExecutedVALog {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("hoaCode")
    private String hoaCode = null;

    @JsonProperty("ddoCode")
    private String ddoCode = null;

    @JsonProperty("granteeCode")
    private String granteeCode = null;

    @JsonProperty("lastExecuted")
    private Long lastExecuted = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;

}
