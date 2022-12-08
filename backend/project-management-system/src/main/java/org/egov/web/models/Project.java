package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.web.models.AdditionalFields;
import org.egov.web.models.Address;
import org.egov.web.models.AuditDetails;
import org.egov.web.models.Document;
import org.egov.web.models.Target;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * The purpose of this object to define the Project for a geography and period
 */
@ApiModel(description = "The purpose of this object to define the Project for a geography and period")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project   {
        @JsonProperty("id")
        private String id = null;

        @JsonProperty("tenantId")
        private String tenantId = null;

        @JsonProperty("projectType")
        private String projectType = null;

        @JsonProperty("subprojectType")
        private String subprojectType = null;

        @JsonProperty("department")
        private String department = null;

        @JsonProperty("description")
        private String description = null;

        @JsonProperty("referenceID")
        private String referenceID = null;

        @JsonProperty("documents")
        @Valid
        private List<Document> documents = null;

        @JsonProperty("address")
        private Address address = null;

        @JsonProperty("startDate")
        private Long startDate = null;

        @JsonProperty("endDate")
        private Long endDate = null;

        @JsonProperty("isTaskEnabled")
        private Boolean isTaskEnabled = false;

        @JsonProperty("parent")
        private String parent = null;

        @JsonProperty("targets")
        @Valid
        private List<Target> targets = null;

        @JsonProperty("additionalFields")
        private AdditionalFields additionalFields = null;

        @JsonProperty("isDeleted")
        private Boolean isDeleted = null;

        @JsonProperty("rowVersion")
        private Integer rowVersion = null;

        @JsonProperty("auditDetails")
        private AuditDetails auditDetails = null;


        public Project addDocumentsItem(Document documentsItem) {
            if (this.documents == null) {
            this.documents = new ArrayList<>();
            }
        this.documents.add(documentsItem);
        return this;
        }

        public Project addTargetsItem(Target targetsItem) {
            if (this.targets == null) {
            this.targets = new ArrayList<>();
            }
        this.targets.add(targetsItem);
        return this;
        }

}

