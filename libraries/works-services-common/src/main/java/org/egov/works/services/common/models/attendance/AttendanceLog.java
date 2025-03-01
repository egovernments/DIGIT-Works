package org.egov.works.services.common.models.attendance;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLog {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("time")
    private BigDecimal time = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("documentIds")
    @Valid
    private List<Document> documentIds = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public AttendanceLog addDocumentIdsItem(Document documentIdsItem) {
        if (this.documentIds == null) {
            this.documentIds = new ArrayList<>();
        }
        this.documentIds.add(documentIdsItem);
        return this;
    }

}

