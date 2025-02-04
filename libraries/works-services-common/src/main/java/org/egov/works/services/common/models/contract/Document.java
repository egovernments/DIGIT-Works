package org.egov.works.services.common.models.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
    @JsonProperty("id")
    @Size(max = 64)
    private String id = null;

    @JsonProperty("documentType")
    private String documentType = null;

    @JsonProperty("fileStore")
    private String fileStore = null;

    @JsonProperty("documentUid")
    @Size(max = 64)
    private String documentUid = null;

    @JsonProperty("status")
    @Valid
    private Status status = null;

    @Size(max = 64)
    private String contractId = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonIgnore
    private AuditDetails auditDetails = null;


}

