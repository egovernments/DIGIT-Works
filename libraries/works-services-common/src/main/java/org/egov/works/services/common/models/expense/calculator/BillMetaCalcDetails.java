package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;


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