package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;

import java.math.BigDecimal;

/**
 * BillingPeriod
 *
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * Represents an individual billing period generated from billing configuration.
 * Each project can have multiple billing periods based on the configured frequency.
 *
 * WHY BILLING PERIODS EXIST:
 * --------------------------
 * V1: One project → One bill (at campaign end)
 * V2: One project → MULTIPLE bills (per period) + One aggregate bill (at end)
 *
 * Billing periods enable:
 *   1. Periodic payments to workers during campaign (not just at end)
 *   2. Better cash flow management for implementing agencies
 *   3. Audit trail per period (each period has own status, bill, amounts)
 *
 * LIFECYCLE STATES:
 * -----------------
 * PENDING → PROCESSING → COMPLETED/BILLED
 *
 *   - PENDING: Period created but no billing action taken yet
 *   - PROCESSING: Bill generation in progress (async)
 *   - COMPLETED: Muster rolls approved but bill not yet generated
 *   - BILLED: Intermediate bill successfully generated
 *
 * RELATIONSHIP TO OTHER ENTITIES:
 * --------------------------------
 *   BillingConfig 1 ──→ N BillingPeriod
 *   BillingPeriod 1 ──→ N MusterRoll (via billingPeriodId)
 *   BillingPeriod 1 ──→ 1 Bill (via billId after generation)
 *
 * DATA FLOW:
 * ----------
 * 1. BillingConfig created → PeriodGenerationService creates BillingPeriods
 * 2. Proximity supervisor creates MusterRoll with billingPeriodId
 * 3. District supervisor triggers bill generation for a period
 * 4. IntermediateBillingService searches muster rolls by billingPeriodId
 * 5. Bill generated → BillingPeriod updated with billId and status=BILLED
 *
 * ================================================================================
 *
 * @author DIGIT-Works
 */
@Schema(description = "Individual billing period for a project")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingPeriod {

    @JsonProperty("id")
    @Size(min = 1, max = 64)
    @Schema(description = "Unique identifier for the billing period", example = "bp-123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    @Schema(description = "Tenant identifier", example = "mz.meghalaya", required = true)
    private String tenantId;

    @JsonProperty("projectId")
    @NotNull
    @Size(min = 2, max = 256)
    @Schema(description = "Project identifier - inherited from billing configuration", example = "PROJECT-2025-001", required = true)
    private String projectId;

    @JsonProperty("campaignNumber")
    @NotNull
    @Size(min = 2, max = 256)
    @Schema(description = "Campaign number/ID - inherited from billing configuration", example = "CMP-2025-10-14-007097", required = true)
    private String campaignNumber;

    @JsonProperty("billingConfigId")
    @NotNull
    @Size(min = 2, max = 64)
    @Schema(description = "Billing configuration identifier", example = "bc-123e4567", required = true)
    private String billingConfigId;

    @JsonProperty("periodNumber")
    @NotNull
    @Schema(description = "Sequential period number (1, 2, 3, ...)", example = "1", required = true)
    private Integer periodNumber;

    @JsonProperty("periodStartDate")
    @NotNull
    @Schema(description = "Period start date in epoch milliseconds", example = "1704067200000", required = true)
    private Long periodStartDate;

    @JsonProperty("periodEndDate")
    @NotNull
    @Schema(description = "Period end date in epoch milliseconds", example = "1704672000000", required = true)
    private Long periodEndDate;

    @JsonProperty("billingFrequency")
    @NotNull
    @Schema(description = "Billing frequency for this period - inherited from parent BillingConfig",
            example = "WEEKLY",
            required = true,
            allowableValues = {"WEEKLY", "BI_WEEKLY", "MONTHLY", "CUSTOM", "END_OF_CAMPAIGN"})
    private BillingFrequency billingFrequency;

    @JsonProperty("periodType")
    @Size(min = 2, max = 32)
    @Schema(description = "Type of billing period",
            example = "INTERMEDIATE",
            allowableValues = {"INTERMEDIATE", "FINAL_AGGREGATE"})
    private String periodType;

    @JsonProperty("status")
    @Size(min = 2, max = 32)
    @Schema(description = "Status of the billing period",
            example = "PENDING",
            allowableValues = {"PENDING", "PROCESSING", "COMPLETED", "BILLED"})
    private String status;

    @JsonProperty("billId")
    @Size(min = 1, max = 64)
    @Schema(description = "Generated bill identifier (populated after bill generation)", example = "BILL-2025-001")
    private String billId;

    @JsonProperty("totalAmount")
    @Schema(description = "Total amount for this period", example = "50000.00")
    private BigDecimal totalAmount;

    @JsonProperty("beneficiaryCount")
    @Schema(description = "Number of beneficiaries in this period", example = "100")
    private Integer beneficiaryCount;

    @JsonProperty("registerCount")
    @Schema(description = "Number of attendance registers in this period", example = "5")
    private Integer registerCount;

    @JsonProperty("musterRollCount")
    @Schema(description = "Number of muster rolls generated for this period", example = "5")
    private Integer musterRollCount;

    @JsonProperty("createdBy")
    @Size(min = 1, max = 64)
    @Schema(description = "User who created the period", example = "user-uuid-123")
    private String createdBy;

    @JsonProperty("createdTime")
    @Schema(description = "Creation timestamp in epoch milliseconds", example = "1704067200000")
    private Long createdTime;

    @JsonProperty("lastModifiedBy")
    @Size(min = 1, max = 64)
    @Schema(description = "User who last modified the period", example = "user-uuid-456")
    private String lastModifiedBy;

    @JsonProperty("lastModifiedTime")
    @Schema(description = "Last modification timestamp in epoch milliseconds", example = "1704153600000")
    private Long lastModifiedTime;

    @JsonProperty("isDeprecated")
    @Schema(description = "Flag indicating if this period is deprecated due to billing config update", example = "false", defaultValue = "false")
    private Boolean isDeprecated;

    @JsonProperty("additionalDetails")
    @Schema(description = "Additional details as JSON object")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    @Valid
    @Schema(description = "Audit details for the period")
    private AuditDetails auditDetails;

    /**
     * Get the duration of the period in milliseconds.
     *
     * @return duration in milliseconds
     */
    public long getPeriodDuration() {
        return periodEndDate - periodStartDate + 1;
    }

    /**
     * Get the duration of the period in days.
     *
     * @return duration in days
     */
    public int getPeriodDurationInDays() {
        return (int) ((periodEndDate - periodStartDate) / (24 * 60 * 60 * 1000)) + 1;
    }

    /**
     * Check if the period is pending.
     *
     * @return true if status is PENDING
     */
    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(this.status);
    }

    /**
     * Check if the period is processing.
     *
     * @return true if status is PROCESSING
     */
    public boolean isProcessing() {
        return "PROCESSING".equalsIgnoreCase(this.status);
    }

    /**
     * Check if the period is completed.
     *
     * @return true if status is COMPLETED
     */
    public boolean isCompleted() {
        return "COMPLETED".equalsIgnoreCase(this.status);
    }

    /**
     * Check if the period is billed.
     *
     * @return true if status is BILLED
     */
    public boolean isBilled() {
        return "BILLED".equalsIgnoreCase(this.status);
    }

    /**
     * Check if bill has been generated for this period.
     *
     * @return true if billId is not null
     */
    public boolean hasBill() {
        return billId != null && !billId.isEmpty();
    }
}