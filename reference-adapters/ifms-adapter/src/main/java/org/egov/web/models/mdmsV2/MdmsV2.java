package org.egov.web.models.mdmsV2;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MdmsV2 {

    @JsonProperty("id")
    @Size(min = 2, max = 64)
    private String id;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 128)
    private String tenantId = null;

    @JsonProperty("schemaCode")
    @NotNull
    @Size(min = 2, max = 128)
    private String schemaCode = null;

    @JsonProperty("uniqueIdentifier")
    @Size(min = 2, max = 128)
    private String uniqueIdentifier = null;

    @JsonProperty("data")
    @NotNull
    private JsonNode data = null;

    @JsonProperty("isActive")
    private Boolean isActive = true;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

}