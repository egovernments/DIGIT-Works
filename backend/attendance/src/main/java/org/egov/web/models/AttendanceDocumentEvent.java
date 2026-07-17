package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDocumentEvent {

    @JsonProperty("individualId")
    private String individualId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("fileStore")
    private String fileStore;

    @JsonProperty("type")
    private String type;

    @JsonProperty("clientAuditDetails")
    private AuditDetails clientAuditDetails;
}
