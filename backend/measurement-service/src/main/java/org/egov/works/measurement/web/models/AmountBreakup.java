package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AmountBreakup
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmountBreakup {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("estimateAmountBreakupId")
    @NotNull
    @Valid
    private String estimateAmountBreakupId = null;

    @JsonProperty("amount")
    @NotNull
    @Valid
    private Double amount = null;

    @JsonProperty("status")
    @Valid
    private Status status = null;

    @JsonIgnore
    private String lineItemId = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonIgnore
    private AuditDetails auditDetails;

}

