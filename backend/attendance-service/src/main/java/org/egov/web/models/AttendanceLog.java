package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.Document;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AttendanceLog
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceLog {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("individualId")
    private UUID individualId = null;

    @JsonProperty("time")
    private Double time = null;

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

