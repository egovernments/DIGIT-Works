package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.workflow.ProcessInstance;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Estimate
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estimate {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("estimateNumber")
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
    private String projectId = null;

    @JsonProperty("proposalDate")
    private BigDecimal proposalDate = null;

    @JsonProperty("status")
    private StatusEnum status = null;
    //private String status = null;

    @JsonProperty("wfStatus")
    private String wfStatus = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("referenceNumber")
    private String referenceNumber = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("executingDepartment")
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

    @JsonProperty("project")
    private Project project = null;

    @JsonProperty("ProcessInstances")
    @Valid
    private ProcessInstance processInstances = null;


    public Estimate addEstimateDetailsItem(EstimateDetail estimateDetailsItem) {
        this.estimateDetails.add(estimateDetailsItem);
        return this;
    }

    /**
     * It stores the status of the estimate.
     */
    public enum StatusEnum {

        DRAFT("DRAFT"),
        
        INWORKFLOW("INWORKFLOW"),

        ACTIVE("ACTIVE"),

        INACTIVE("INACTIVE");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

}

