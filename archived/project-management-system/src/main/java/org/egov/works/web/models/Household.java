package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * A representation of Household.
 */
@ApiModel(description = "A representation of Household.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Household {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("clientReferenceId")
    private String clientReferenceId = null;

    @JsonProperty("noOfMembers")
    private Integer noOfMembers = null;

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


}

