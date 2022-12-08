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
import org.egov.web.models.TaskResource;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

/**
 * Task
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task   {
        @JsonProperty("id")
        private String id = null;

        @JsonProperty("tenantId")
        private String tenantId = null;

        @JsonProperty("projectId")
        private String projectId = null;

        @JsonProperty("projectBeneficiaryId")
        private String projectBeneficiaryId = null;

        @JsonProperty("resources")
        @Valid
        private List<TaskResource> resources = null;

        @JsonProperty("plannedStartDate")
        private Long plannedStartDate = null;

        @JsonProperty("plannedEndDate")
        private Long plannedEndDate = null;

        @JsonProperty("actualStartDate")
        private Long actualStartDate = null;

        @JsonProperty("actualEndDate")
        private Long actualEndDate = null;

        @JsonProperty("createdBy")
        private String createdBy = null;

        @JsonProperty("createdDate")
        private Long createdDate = null;

        @JsonProperty("address")
        private Address address = null;

        @JsonProperty("additionalFields")
        private AdditionalFields additionalFields = null;

        @JsonProperty("isDeleted")
        private Boolean isDeleted = null;

        @JsonProperty("rowVersion")
        private Integer rowVersion = null;

        @JsonProperty("auditDetails")
        private AuditDetails auditDetails = null;

        @JsonProperty("status")
        private String status = null;


        public Task addResourcesItem(TaskResource resourcesItem) {
            if (this.resources == null) {
            this.resources = new ArrayList<>();
            }
        this.resources.add(resourcesItem);
        return this;
        }

}

