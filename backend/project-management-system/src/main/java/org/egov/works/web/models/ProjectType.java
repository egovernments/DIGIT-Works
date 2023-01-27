package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the master data to capture the metadata of Project
 */
@ApiModel(description = "This is the master data to capture the metadata of Project")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectType {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("code")
    private String code = null;

    @JsonProperty("group")
    private String group = null;

    /**
     * beneficiary type
     */
    public enum BeneficiaryTypeEnum {
        HOUSEHOLD("HOUSEHOLD"),

        INDIVIDUAL("INDIVIDUAL"),

        STRUCTURE("STRUCTURE");

        private String value;

        BeneficiaryTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static BeneficiaryTypeEnum fromValue(String text) {
            for (BeneficiaryTypeEnum b : BeneficiaryTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("beneficiaryType")
    private BeneficiaryTypeEnum beneficiaryType = null;

    @JsonProperty("eligibilityCriteria")
    @Valid
    private List<String> eligibilityCriteria = null;

    @JsonProperty("taskProcedure")
    @Valid
    private List<String> taskProcedure = null;

    @JsonProperty("resources")
    @Valid
    private List<ProjectProductVariant> resources = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


    public ProjectType addEligibilityCriteriaItem(String eligibilityCriteriaItem) {
        if (this.eligibilityCriteria == null) {
            this.eligibilityCriteria = new ArrayList<>();
        }
        this.eligibilityCriteria.add(eligibilityCriteriaItem);
        return this;
    }

    public ProjectType addTaskProcedureItem(String taskProcedureItem) {
        if (this.taskProcedure == null) {
            this.taskProcedure = new ArrayList<>();
        }
        this.taskProcedure.add(taskProcedureItem);
        return this;
    }

    public ProjectType addResourcesItem(ProjectProductVariant resourcesItem) {
        if (this.resources == null) {
            this.resources = new ArrayList<>();
        }
        this.resources.add(resourcesItem);
        return this;
    }

}

