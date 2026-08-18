package org.egov.digit.expense.calculator.web.models.report;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.digit.expense.calculator.web.models.RateFieldConfig;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Object which holds the info about the expense details
 */
@Schema(description = "A Object which holds the info about the expense details")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportBill {

    @JsonProperty("totalAmount")
    @Valid
    @Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonIgnore
    @Default
    private Map<String, BigDecimal> amountBreakup = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, BigDecimal> getAmountBreakup() {
        return amountBreakup;
    }

    @JsonAnySetter
    public void setAmountEntry(String key, Object value) {
        if (value instanceof Number) {
            amountBreakup.put(key, new BigDecimal(value.toString()));
        }
    }

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("reportTitle")
    @Valid
    private String reportTitle;

    @JsonProperty("numberOfIndividuals")
    private Integer numberOfIndividuals;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdTime")
    private Long createdTime;

    @JsonProperty("billingPeriodLabel")
    private String billingPeriodLabel;

    @JsonProperty("billingPeriodDateRange")
    private String billingPeriodDateRange;

    @JsonProperty("billingPeriodStartDate")
    private Long billingPeriodStartDate;

    @JsonProperty("billingPeriodEndDate")
    private Long billingPeriodEndDate;

    @JsonProperty("billDetails")
    @NotNull
    @Valid
    private List<ReportBillDetail> reportBillDetails;

    /**
     * Sign-offs captured as the bill moved through the payments workflow, one per voucher slot.
     * Empty for bills approved before sign-off capture existed — the voucher then renders its
     * signature slots blank, exactly as it did before.
     */
    @JsonProperty("signatures")
    @Valid
    private List<ReportSignature> signatures;

    /*
     * Flattened sign-off fields for the pdf template.
     *
     * The template cannot read these out of the signatures list. pdf-service resolves every
     * variable through getValue(jp.query(...), "NA", path), which returns the literal string "NA"
     * when a path matches nothing — and a bill signed at only some stages, or not at all, would
     * leave those paths unmatched. "NA" reaching an image element makes PDFMake throw, which would
     * break pdf generation for every bill approved before sign-off capture existed.
     *
     * These fields are therefore always populated: a blank 1x1 png and a single space stand in
     * where a slot was not signed, so the path always matches and the slot simply renders empty.
     */
    @JsonProperty("preparedBySignatureName")
    private String preparedBySignatureName;

    @JsonProperty("preparedBySignatureImage")
    private String preparedBySignatureImage;

    @JsonProperty("verifiedBySignatureName")
    private String verifiedBySignatureName;

    @JsonProperty("verifiedBySignatureImage")
    private String verifiedBySignatureImage;

    @JsonProperty("approvedBySignatureName")
    private String approvedBySignatureName;

    @JsonProperty("approvedBySignatureImage")
    private String approvedBySignatureImage;

    @JsonIgnore
    private List<RateFieldConfig> fieldConfigs;

}
