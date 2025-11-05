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

/**
 * BillingConfig
 *
 * Represents the billing configuration for a project.
 * Enables intermediate billing by defining billing frequency and project timeline.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Billing configuration for a project enabling intermediate billing")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingConfig {

    @JsonProperty("id")
    @Size(min = 1, max = 64)
    @Schema(description = "Unique identifier for the billing configuration", example = "bc-123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    @Schema(description = "Tenant identifier", example = "mz.meghalaya", required = true)
    private String tenantId;

    @JsonProperty("campaignNumber")
    @NotNull
    @Size(min = 2, max = 256)
    @Schema(description = "Campaign number/ID for which billing configuration is created. All projects under this campaign will inherit this configuration.",
            example = "CMP-2025-10-14-007097", required = true)
    private String campaignNumber;

    @JsonProperty("billingFrequency")
    @NotNull
    @Schema(description = "Billing frequency - determines how often billing periods are generated",
            example = "WEEKLY",
            required = true,
            allowableValues = {"WEEKLY", "BI_WEEKLY", "MONTHLY", "CUSTOM", "END_OF_CAMPAIGN"})
    private BillingFrequency billingFrequency;

    @JsonProperty("customFrequencyDays")
    @Schema(description = "Number of days for custom frequency (Required when billingFrequency is CUSTOM, minimum 3 days)",
            example = "10",
            minimum = "3")
    private Integer customFrequencyDays;

    @JsonProperty("projectStartDate")
    @NotNull
    @Schema(description = "Campaign start date in epoch milliseconds", example = "1704067200000", required = true)
    private Long projectStartDate;

    @JsonProperty("projectEndDate")
    @NotNull
    @Schema(description = "Campaign end date in epoch milliseconds", example = "1706745600000", required = true)
    private Long projectEndDate;

    @JsonProperty("status")
    @Size(min = 2, max = 32)
    @Schema(description = "Status of the billing configuration",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "COMPLETED"})
    private String status;

    @JsonProperty("createdBy")
    @Size(min = 1, max = 64)
    @Schema(description = "User who created the configuration", example = "user-uuid-123")
    private String createdBy;

    @JsonProperty("createdTime")
    @Schema(description = "Creation timestamp in epoch milliseconds", example = "1704067200000")
    private Long createdTime;

    @JsonProperty("lastModifiedBy")
    @Size(min = 1, max = 64)
    @Schema(description = "User who last modified the configuration", example = "user-uuid-456")
    private String lastModifiedBy;

    @JsonProperty("lastModifiedTime")
    @Schema(description = "Last modification timestamp in epoch milliseconds", example = "1704153600000")
    private Long lastModifiedTime;

    @JsonProperty("additionalDetails")
    @Schema(description = "Additional details as JSON object")
    private Object additionalDetails;

    @JsonProperty("auditDetails")
    @Valid
    @Schema(description = "Audit details for the configuration")
    private AuditDetails auditDetails;

    /**
     * Validates if the billing configuration is active.
     *
     * @return true if status is ACTIVE
     */
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.status);
    }

    /**
     * Validates if custom frequency is required.
     *
     * @return true if billing frequency is CUSTOM
     */
    public boolean requiresCustomFrequency() {
        return billingFrequency != null && billingFrequency.requiresCustomDays();
    }

    /**
     * Get the duration of the project in milliseconds.
     *
     * @return duration in milliseconds
     */
    public long getProjectDuration() {
        return projectEndDate - projectStartDate + 1;
    }

    /**
     * Get the duration of the project in days.
     *
     * @return duration in days
     */
    public int getProjectDurationInDays() {
        return (int) ((projectEndDate - projectStartDate) / (24 * 60 * 60 * 1000)) + 1;
    }
}
