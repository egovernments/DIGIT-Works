package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * A representation of Individual Registration to a Project.
 */
@ApiModel(description = "A representation of Individual Registration to a Project.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectBeneficiary {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("projectId")
    private String projectId = null;

    @JsonProperty("beneficiaryid")
    private String beneficiaryid = null;

    @JsonProperty("dateOfRegistration")
    private Integer dateOfRegistration = null;

    @JsonProperty("additionalFields")
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


}

