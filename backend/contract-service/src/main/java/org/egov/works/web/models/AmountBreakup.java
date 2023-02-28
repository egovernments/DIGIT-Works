package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * AmountBreakup
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

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

