package org.egov.works.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Target
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Target {

    @JsonProperty("id")
    private String id = null;

    @JsonIgnore
    private String projectid = null;

    @JsonProperty("beneficiaryType")
    private String beneficiaryType = null;

    @JsonProperty("totalNo")
    private Integer totalNo = null;

    @JsonProperty("targetNo")
    private Integer targetNo = null;

    @JsonProperty("isDeleted")
    private Boolean isDeleted = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;
}
