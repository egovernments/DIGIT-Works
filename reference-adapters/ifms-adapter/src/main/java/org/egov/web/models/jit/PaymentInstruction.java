package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.egov.common.data.query.annotations.Exclude;
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
    @Exclude
    private String id;

    @JsonProperty("tenantId")
    @Exclude
    private String tenantId;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("parentPiNumber")
    @Exclude
    private String parentPiNumber;

    @JsonProperty("muktaReferenceId")
    @Exclude
    private String muktaReferenceId;

    @JsonProperty("numBeneficiaries")
    @Exclude
    private Integer numBeneficiaries;

    @JsonProperty("grossAmount")
    @Exclude
    private BigDecimal grossAmount;

    @JsonProperty("netAmount")
    @Exclude
    private BigDecimal netAmount;

    @JsonProperty("piStatus")
    @Exclude
    private PIStatus piStatus;

    @JsonProperty("piSuccessCode")
    @Exclude
    private String piSuccessCode;

    @JsonProperty("piSuccessDesc")
    @Exclude
    private String piSuccessDesc;

    @JsonProperty("piApprovedId")
    @Exclude
    private String piApprovedId;

    @JsonProperty("piApprovalDate")
    @Exclude
    private String piApprovalDate;

    @JsonProperty("piErrorResp")
    @Exclude
    private String piErrorResp;

    @JsonProperty("additionalDetails")
    @Exclude
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    @Exclude
    private AuditDetails auditDetails;

    @JsonProperty("transactionDetails")
    @Exclude
    private List<TransactionDetails> transactionDetails;

    @JsonProperty("paDetails")
    @Exclude
    private List<PADetails> paDetails;

    @JsonProperty("piStatusLogs")
    @Exclude
    private List<PIStatusLog> piStatusLogs;

    @JsonProperty("isActive")
    @Exclude
    private Boolean isActive = true;

    // PI request fields
    @JsonProperty("jitBillNo")
    @Exclude
    private String jitBillNo;

    @JsonProperty("jitBillDate")
    @Exclude
    private String jitBillDate;

    @JsonProperty("jitBillDdoCode")
    @Exclude
    private String jitBillDdoCode;

    @JsonProperty("granteeAgCode")
    @Exclude
    private String granteeAgCode;

    @JsonProperty("schemeCode")
    @Exclude
    private String schemeCode;

    @JsonProperty("hoa")
    @Exclude
    private String hoa;

    @JsonProperty("ssuIaId")
    @Exclude
    private String ssuIaId;

    @JsonProperty("mstAllotmentDistId")
    @Exclude
    private String mstAllotmentDistId;

    @JsonProperty("ssuAllotmentId")
    @Exclude
    private String ssuAllotmentId;

    @JsonProperty("allotmentTxnSlNo")
    @Exclude
    private String allotmentTxnSlNo;

    @JsonProperty("billGrossAmount")
    @Exclude
    private String billGrossAmount;

    @JsonProperty("billNetAmount")
    @Exclude
    private String billNetAmount;

    @JsonProperty("billNumberOfBenf")
    @Exclude
    private String billNumberOfBenf;

    @JsonProperty("purpose")
    @Exclude
    private String purpose;

    @JsonProperty("beneficiaryDetails")
    private List<Beneficiary> beneficiaryDetails;

}
