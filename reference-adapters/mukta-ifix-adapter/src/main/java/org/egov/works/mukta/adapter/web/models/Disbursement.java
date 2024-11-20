package org.egov.works.mukta.adapter.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Disbursement extends ExchangeCode {
    @JsonProperty("id")
    private String id;
    @NotNull
    @JsonProperty("target_id")
    private String targetId;
    @NotNull
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("disbursement_date")
    private Long disbursementDate;
    @JsonProperty("allocation_ids")
    private List<String> allocationIds;
    @JsonProperty("sanction_id")
    private String sanctionId;
    @JsonProperty("children")
    private List<Disbursement> disbursements;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("account_code")
    private String accountCode;
    @NotNull
    @JsonProperty("net_amount")
    private BigDecimal netAmount;
    @NotNull
    @JsonProperty("gross_amount")
    private BigDecimal grossAmount;
    @JsonProperty("individual")
    private Individual individual;
    @JsonProperty("program_code")
    @NotNull
    private String programCode;
    @JsonProperty("additional_details")
    private Object additionalDetails;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
}
