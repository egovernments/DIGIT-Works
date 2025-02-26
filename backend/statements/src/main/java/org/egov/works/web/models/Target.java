package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

/**
 * Target
 */
@Validated

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
