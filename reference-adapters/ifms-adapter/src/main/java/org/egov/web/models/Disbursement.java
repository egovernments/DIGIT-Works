package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egov.common.contract.models.AuditDetails;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disbursement extends ExchangeCode {
    @JsonProperty("id")
    private String id;
    @NotNull
    @JsonProperty("target_id")
    private String targetId;
    @JsonProperty("location_code")
    @NotNull
    private String locationCode;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("disbursement_date")
    private Long disbursementDate;
    @JsonProperty("sanction_id")
    @NotNull
    private String sanctionId;
    @JsonProperty("children")
    private List<Disbursement> disbursements;
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
    private String programCode;
    @JsonProperty("additional_details")
    private Object additionalDetails;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
}