package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this object to define the Project for a geography and period
 */
@Validated

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("projectNumber")
    private String projectNumber = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("projectType")
    private String projectType = null;

    @JsonProperty("projectSubType")
    private String projectSubType = null;

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

    @JsonProperty("projectHierarchy")
    private String projectHierarchy = null;

    @JsonProperty("natureOfWork")
    private String natureOfWork = null;

    @JsonProperty("ancestors")
    private List<Project> ancestors = null;

    @JsonProperty("descendants")
    private List<Project> descendants = null;

    @JsonProperty("targets")
    @Valid
    private List<Target> targets = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = false;

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
