package org.egov.works.mukta.adapter.web.models.jit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.egov.common.data.query.annotations.Exclude;
import org.egov.works.mukta.adapter.web.models.enums.BeneficiaryPaymentStatus;
import org.egov.works.mukta.adapter.web.models.enums.BeneficiaryType;
import org.egov.works.services.common.models.expense.LineItem;

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
    @Exclude
    private String id;

    @JsonProperty("tenantId")
    @Exclude
    private String tenantId;

    @JsonProperty("muktaReferenceId")
    @Exclude
    private String muktaReferenceId;

    @JsonProperty("piId")
    @Exclude
    private String piId;

    @JsonProperty("beneficiaryId")
    @Exclude
    private String beneficiaryId;

    @JsonProperty("beneficiaryType")
    @Exclude
    private BeneficiaryType beneficiaryType;

    @JsonProperty("beneficiaryNumber")
    @Exclude
    private String beneficiaryNumber;

    @JsonProperty("bankAccountId")
    @Exclude
    private String bankAccountId;

    @JsonProperty("amount")
    @Exclude
    private BigDecimal amount;

    @JsonProperty("voucherNumber")
    @Exclude
    private String voucherNumber;

    @JsonProperty("voucherDate")
    @Exclude
    private Long voucherDate;

    @JsonProperty("utrNo")
    @Exclude
    private String utrNo;

    @JsonProperty("utrDate")
    @Exclude
    private String utrDate;

    @JsonProperty("endToEndId")
    @Exclude
    private String endToEndId;

    @JsonProperty("challanNumber")
    @Exclude
    private String challanNumber;

    @JsonProperty("challanDate")
    @Exclude
    private String challanDate;

    @JsonProperty("paymentStatus")
    @Exclude
    private BeneficiaryPaymentStatus paymentStatus;

    @JsonProperty("paymentStatusMessage")
    @Exclude
    private String paymentStatusMessage;

    @JsonProperty("additionalDetails")
    @Exclude
    private Object additionalDetails;

    @JsonProperty("benfLineItems")
    private List<BenfLineItems> benfLineItems;

    @JsonProperty("lineItems")
    private List<LineItem> lineItems;

    @JsonProperty("auditDetails")
    @Exclude
    private AuditDetails auditDetails;

    // PI request fields
    @JsonProperty("benefId")
    @Exclude
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
