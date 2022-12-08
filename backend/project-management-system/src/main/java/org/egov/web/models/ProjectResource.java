package org.egov.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.egov.web.models.AuditDetails;
import org.egov.web.models.ProjectProductVariant;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * This object defines the mapping of a resource to a project.
 */
@ApiModel(description = "This object defines the mapping of a resource to a project.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResource   {
        @JsonProperty("id")
        private String id = null;

        @JsonProperty("tenantId")
        private String tenantId = null;

        @JsonProperty("projectId")
        private String projectId = null;

        @JsonProperty("resource")
        @Valid
        private List<ProjectProductVariant> resource = null;

        @JsonProperty("isDeleted")
        private Boolean isDeleted = null;

        @JsonProperty("rowVersion")
        private Integer rowVersion = null;

        @JsonProperty("auditDetails")
        private AuditDetails auditDetails = null;


        public ProjectResource addResourceItem(ProjectProductVariant resourceItem) {
            if (this.resource == null) {
            this.resource = new ArrayList<>();
            }
        this.resource.add(resourceItem);
        return this;
        }

}

