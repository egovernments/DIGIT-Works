package org.egov.works.services.common.models.estimate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.egov.common.contract.models.Address;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.workflow.ProcessInstance;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estimate {

    @JsonProperty("id")
    @Size(min = 2,max = 64)
    private String id = null;

    @JsonProperty("tenantId")
    @Size(min = 2,max = 64)
    private String tenantId = null;

    @JsonProperty("estimateNumber")
    @Size(min = 2,max = 64)
    private String estimateNumber = null;

    @JsonProperty("revisionNumber")
    @Size(min = 2,max = 64)
    private String revisionNumber = null;

    @JsonProperty("businessService")
    @Size(min = 2,max = 64)
    private String businessService = null;

    @JsonProperty("versionNumber")
    private BigDecimal versionNumber = null;

    @JsonProperty("oldUuid")
    @Size(min = 2,max = 64)
    private String oldUuid = null;

    @JsonProperty("projectId")
    @Size(min = 2,max = 64)
    private String projectId = null;

    @JsonProperty("proposalDate")
    private BigDecimal proposalDate = null;

    @JsonProperty("status")
    private Status status = null;
    //private String status = null;

    @JsonProperty("wfStatus")
    @Size(min = 2,max = 64)
    private String wfStatus = null;

    @JsonProperty("name")
    @Size(min = 2,max = 140)
    private String name = null;

    @JsonProperty("referenceNumber")
    @Size(min = 2,max = 140)
    private String referenceNumber = null;

    @JsonProperty("description")
    @Size(min = 2,max = 240)
    private String description = null;

    @JsonProperty("executingDepartment")
    @Size(min = 2,max = 64)
    private String executingDepartment = null;

    @JsonProperty("address")
    private Address address = null;

//    @JsonProperty("totalEstimateAmount")
//    private Double totalEstimateAmount = null;

    @JsonProperty("estimateDetails")
    @Valid
    private List<EstimateDetail> estimateDetails = new ArrayList<>();

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    @JsonProperty("ProcessInstances")
    @Valid
    private ProcessInstance processInstances = null;


    public Estimate addEstimateDetailsItem(EstimateDetail estimateDetailsItem) {
        this.estimateDetails.add(estimateDetailsItem);
        return this;
    }
}

