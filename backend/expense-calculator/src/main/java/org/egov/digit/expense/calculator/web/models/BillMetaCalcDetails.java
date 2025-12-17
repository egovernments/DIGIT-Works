package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import digit.models.coremodels.AuditDetails;

import jakarta.validation.Valid;

@Schema(description = "A Object which holds the meta about the bill")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillMetaCalcDetails {
    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("calculationId")
    private String calculationId = null;

    @JsonProperty("payeeId")
    private String payeeId = null;

    @JsonProperty("billingSlabCode")
    private String billingSlabCode = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

}
