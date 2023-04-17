package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * A representation of a household member (already registered as an individual)
 */
@ApiModel(description = "A representation of a household member (already registered as an individual)")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdMember {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("householdId")
    private String householdId = null;

    @JsonProperty("householdClientReferenceId")
    private String householdClientReferenceId = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("individualClientReferenceId")
    private String individualClientReferenceId = null;

    @JsonProperty("isHeadOfHousehold")
    private Boolean isHeadOfHousehold = false;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("additionalFields")
    private AdditionalFields additionalFields = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("rowVersion")
    private Integer rowVersion = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


}

