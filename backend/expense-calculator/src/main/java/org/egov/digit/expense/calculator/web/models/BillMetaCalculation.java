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
import java.util.ArrayList;
import java.util.List;

@Schema(description = "A Object which holds the meta about the bill")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillMetaCalculation {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("businessService")
    private String businessService = null;

    @JsonProperty("billId")
    private String billId = null;

    @JsonProperty("billNumber")
    private String billNumber = null;

    @JsonProperty("billReference")
    private String billReference = null;

    @JsonProperty("contractNumber")
    private String contractNumber = null;

    @JsonProperty("musterrollNumber")
    private String musterrollNumber = null;

    @JsonProperty("projectNumber")
    private String projectNumber = null;

    @JsonProperty("orgId")
    private String orgId = null;

    @JsonProperty("isActive")
    private Boolean isActive = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    @Valid
    private AuditDetails auditDetails = null;

    @JsonProperty("billMetaCalcDetails")
    @Valid
    private List<BillMetaCalcDetails> billMetaCalcDetails = null;

    public BillMetaCalculation addBillMetaCalcDetailsItem(BillMetaCalcDetails item) {
        if (this.billMetaCalcDetails == null) {
            this.billMetaCalcDetails = new ArrayList<>();
        }
        this.billMetaCalcDetails.add(item);
        return this;
    }
}
