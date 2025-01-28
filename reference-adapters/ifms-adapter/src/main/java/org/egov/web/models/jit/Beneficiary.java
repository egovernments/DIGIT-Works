package org.egov.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.models.AuditDetails;
import org.egov.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.web.models.enums.BeneficiaryType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Beneficiary {

    // Beneficiary Details fields stored in DB
    @JsonProperty("id")

    private String id;

    @JsonProperty("tenantId")

    private String tenantId;

    @JsonProperty("muktaReferenceId")

    private String muktaReferenceId;

    @JsonProperty("piId")

    private String piId;

    @JsonProperty("beneficiaryId")

    private String beneficiaryId;

    @JsonProperty("beneficiaryType")

    private BeneficiaryType beneficiaryType;

    @JsonProperty("beneficiaryNumber")

    private String beneficiaryNumber;

    @JsonProperty("bankAccountId")

    private String bankAccountId;

    @JsonProperty("amount")

    private BigDecimal amount;

    @JsonProperty("voucherNumber")

    private String voucherNumber;

    @JsonProperty("voucherDate")

    private Long voucherDate;

    @JsonProperty("utrNo")

    private String utrNo;

    @JsonProperty("utrDate")

    private String utrDate;

    @JsonProperty("endToEndId")

    private String endToEndId;

    @JsonProperty("challanNumber")

    private String challanNumber;

    @JsonProperty("challanDate")

    private String challanDate;

    @JsonProperty("paymentStatus")

    private BeneficiaryPaymentStatus paymentStatus;

    @JsonProperty("paymentStatusMessage")

    private String paymentStatusMessage;

    @JsonProperty("additionalDetails")

    private Object additionalDetails;

    @JsonProperty("benfLineItems")
    private List<BenfLineItems> benfLineItems;

    @JsonProperty("auditDetails")

    private AuditDetails auditDetails;

    // PI request fields
    @JsonProperty("benefId")

    private String benefId;

    @JsonProperty("benefName")
    private String benefName;

    @JsonProperty("benfAcctNo")
    private String benfAcctNo;

    @JsonProperty("benfBankIfscCode")
    private String benfBankIfscCode;

    @JsonProperty("benfMobileNo")
    private String benfMobileNo;

    @JsonProperty("benfEmailId")
    private String benfEmailId = "";

    @JsonProperty("benfAddress")
    private String benfAddress;

    @JsonProperty("benfAccountType")
    private String benfAccountType;

    @JsonProperty("benfAmount")
    private String benfAmount;

    @JsonProperty("panNo")
    private String panNo = "";

    @JsonProperty("adhaarNumber")
    private String adhaarNumber = "";

    @JsonProperty("purpose")
    private String purpose;

}
