package org.egov.digit.expense.calculator.web.models.boundary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * BoundaryRelation
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoundaryRelation {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("code")
    @NotNull
    private String code = null;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId = null;

    @JsonProperty("hierarchyType")
    @NotNull
    private String hierarchyType = null;

    @JsonProperty("boundaryType")
    @NotNull
    private String boundaryType = null;

    @JsonProperty("parent")
    private String parent = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    @JsonIgnore
    private String ancestralMaterializedPath = "";

}
