package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Overide the negotiated amounts from line items of the estimate
 */
@ApiModel(description = "Overide the negotiated amounts from line items of the estimate")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LineItems {
    @JsonProperty("id")
    @Valid
    private String id = null;

    @JsonProperty("estimateId")
    @NotNull
    @Size(min = 1, max = 64)
    private String estimateId = null;

    @JsonProperty("estimateLineItemId")
    @Size(min = 1, max = 64)
    private String estimateLineItemId = null;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("unitRate")
    @Valid
    private Double unitRate = null;

    @JsonProperty("noOfunit")
    @Valid
    private Double noOfunit = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("status")
    @Valid
    private Status status = null;

    @JsonProperty("amountBreakups")
    @Valid
    private List<AmountBreakup> amountBreakups = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonIgnore
    private String contractId;


    public LineItems addAmountBreakupsItem(AmountBreakup amountBreakupsItem) {
        if (this.amountBreakups == null) {
            this.amountBreakups = new ArrayList<>();
        }
        this.amountBreakups.add(amountBreakupsItem);
        return this;
    }

}

