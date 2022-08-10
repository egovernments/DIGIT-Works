package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Estimate
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estimate {

    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("estimateNumber")
    private String estimateNumber = null;

    @JsonProperty("adminSanctionNumber")
    private String adminSanctionNumber = null;

    @JsonProperty("proposalDate")
    private BigDecimal proposalDate = null;

    /**
     * It stores the status of the estimate.
     */
    public enum StatusEnum {
        DRAFT("DRAFT"),

        ACTIVE("ACTIVE"),

        INACTIVE("INACTIVE");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
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
    }

    @JsonProperty("status")
    private StatusEnum status = null;

    @JsonProperty("applicationStatus")
    private String applicationStatus = null;

    @JsonProperty("subject")
    private String subject = null;

    @JsonProperty("requirementNumber")
    private String requirementNumber = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("department")
    private String department = null;

    @JsonProperty("location")
    private String location = null;

    @JsonProperty("workCategory")
    private String workCategory = null;

    @JsonProperty("beneficiary")
    private String beneficiary = null;

    @JsonProperty("natureOfWork")
    private String natureOfWork = null;

    @JsonProperty("typeOfWork")
    private String typeOfWork = null;

    @JsonProperty("subTypeOfWork")
    private String subTypeOfWork = null;

    @JsonProperty("entrustmentMode")
    private String entrustmentMode = null;

    @JsonProperty("fund")
    private String fund = null;

    @JsonProperty("function")
    private String function = null;

    @JsonProperty("budgetHead")
    private String budgetHead = null;

    @JsonProperty("scheme")
    private String scheme = null;

    @JsonProperty("subScheme")
    private String subScheme = null;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount = null;

    @JsonProperty("estimateDetails")
    @Valid
    private List<EstimateDetail> estimateDetails = null;//sub-estimate

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public Estimate addEstimateDetailsItem(EstimateDetail estimateDetailsItem) {
        if (this.estimateDetails == null) {
            this.estimateDetails = new ArrayList<>();
        }
        this.estimateDetails.add(estimateDetailsItem);
        return this;
    }

}

