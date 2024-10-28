package org.egov.wms.web.model.Job;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportJob {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("reportNumber")
    private String reportNumber;

    @JsonProperty("reportName")
    private String reportName;

    @JsonProperty("noOfProjects")
    private Integer noOfProjects;

    @JsonProperty("status")
    private JobStatus status;

    @JsonProperty("requestPayload")
    private Object requestPayload;

    @JsonProperty("additionalDetails")
    private Object additionalDetails;

    @JsonProperty("fileStoreId")
    private String fileStoreId;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}
