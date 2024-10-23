package org.egov.works.services.common.models.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


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

