package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.web.models.enums.PIStatus;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PaymentInstruction {

    // Fields
    @JsonProperty("id")
    
    private String id;

    @JsonProperty("tenantId")
    
    private String tenantId;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("parentPiNumber")
    
    private String parentPiNumber;

    @JsonProperty("muktaReferenceId")
    
    private String muktaReferenceId;

    @JsonProperty("numBeneficiaries")
    
    private Integer numBeneficiaries;

    @JsonProperty("grossAmount")
    
    private BigDecimal grossAmount;

    @JsonProperty("netAmount")
    
    private BigDecimal netAmount;

    @JsonProperty("piStatus")
    
    private PIStatus piStatus;

    @JsonProperty("piSuccessCode")
    
    private String piSuccessCode;

    @JsonProperty("piSuccessDesc")
    
    private String piSuccessDesc;

    @JsonProperty("piApprovedId")
    
    private String piApprovedId;

    @JsonProperty("piApprovalDate")
    
    private String piApprovalDate;

    @JsonProperty("piErrorResp")
    
    private String piErrorResp;

    @JsonProperty("additionalDetails")
    
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    
    private AuditDetails auditDetails;

    @JsonProperty("transactionDetails")
    
    private List<TransactionDetails> transactionDetails;

    @JsonProperty("paDetails")
    
    private List<PADetails> paDetails;

    @JsonProperty("piStatusLogs")
    
    private List<PIStatusLog> piStatusLogs;

    @JsonProperty("isActive")
    
    private Boolean isActive = true;

    // PI request fields
    @JsonProperty("jitBillNo")
    
    private String jitBillNo;

    @JsonProperty("jitBillDate")
    
    private String jitBillDate;

    @JsonProperty("jitBillDdoCode")
    
    private String jitBillDdoCode;

    @JsonProperty("granteeAgCode")
    
    private String granteeAgCode;

    @JsonProperty("schemeCode")
    
    private String schemeCode;

    @JsonProperty("hoa")
    
    private String hoa;

    @JsonProperty("ssuIaId")
    
    private String ssuIaId;

    @JsonProperty("mstAllotmentDistId")
    
    private String mstAllotmentDistId;

    @JsonProperty("ssuAllotmentId")
    
    private String ssuAllotmentId;

    @JsonProperty("allotmentTxnSlNo")
    
    private String allotmentTxnSlNo;

    @JsonProperty("billGrossAmount")
    
    private String billGrossAmount;

    @JsonProperty("billNetAmount")
    
    private String billNetAmount;

    @JsonProperty("billNumberOfBenf")
    
    private String billNumberOfBenf;

    @JsonProperty("purpose")
    
    private String purpose;

    @JsonProperty("beneficiaryDetails")
    private List<Beneficiary> beneficiaryDetails;

}
