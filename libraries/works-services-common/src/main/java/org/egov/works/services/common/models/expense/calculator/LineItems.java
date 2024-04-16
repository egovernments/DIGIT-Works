package org.egov.works.services.common.models.expense.calculator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.services.common.models.contract.AmountBreakup;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


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

