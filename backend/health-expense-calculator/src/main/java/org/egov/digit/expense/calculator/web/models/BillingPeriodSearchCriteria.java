package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;

import java.util.List;

/**
 * BillingPeriodSearchCriteria
 *
 * Search criteria for querying billing periods.
 * Supports flexible search by IDs, billing config, campaign, project, status, and period numbers.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Search criteria for billing period queries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingPeriodSearchCriteria {

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    @Schema(description = "Tenant identifier", example = "mz.meghalaya", required = true)
    private String tenantId;

    @JsonProperty("ids")
    @Schema(description = "List of billing period IDs to search", example = "[\"period-123\", \"period-456\"]")
    private List<String> ids;

    @JsonProperty("billingConfigId")
    @Size(min = 2, max = 64)
    @Schema(description = "Billing configuration ID to filter periods", example = "bc-123e4567")
    private String billingConfigId;

    @JsonProperty("billingConfigIds")
    @Schema(description = "List of billing configuration IDs", example = "[\"bc-123\", \"bc-456\"]")
    private List<String> billingConfigIds;

    @JsonProperty("projectId")
    @Size(min = 2, max = 256)
    @Schema(description = "Project identifier to filter periods", example = "PROJECT-2025-001")
    private String projectId;

    @JsonProperty("projectIds")
    @Schema(description = "List of project identifiers", example = "[\"PROJECT-001\", \"PROJECT-002\"]")
    private List<String> projectIds;

    @JsonProperty("campaignNumber")
    @Size(min = 2, max = 256)
    @Schema(description = "Campaign number to filter periods", example = "CMP-2025-10-14-007097")
    private String campaignNumber;

    @JsonProperty("campaignNumbers")
    @Schema(description = "List of campaign numbers", example = "[\"CMP-001\", \"CMP-002\"]")
    private List<String> campaignNumbers;

    @JsonProperty("periodNumber")
    @Schema(description = "Specific period number to search", example = "1")
    private Integer periodNumber;

    @JsonProperty("periodNumbers")
    @Schema(description = "List of period numbers to filter", example = "[1, 2, 3]")
    private List<Integer> periodNumbers;

    @JsonProperty("status")
    @Size(min = 2, max = 32)
    @Schema(description = "Filter by status",
            example = "PENDING",
            allowableValues = {"PENDING", "PROCESSING", "COMPLETED", "BILLED"})
    private String status;

    @JsonProperty("statuses")
    @Schema(description = "List of statuses to filter", example = "[\"PENDING\", \"BILLED\"]")
    private List<String> statuses;

    @JsonProperty("billingFrequency")
    @Schema(description = "Filter by billing frequency",
            example = "WEEKLY",
            allowableValues = {"WEEKLY", "BI_WEEKLY", "MONTHLY", "CUSTOM", "END_OF_CAMPAIGN"})
    private BillingFrequency billingFrequency;

    @JsonProperty("fromDate")
    @Schema(description = "Filter periods starting from this date (epoch ms)", example = "1704067200000")
    private Long fromDate;

    @JsonProperty("toDate")
    @Schema(description = "Filter periods ending before this date (epoch ms)", example = "1706745600000")
    private Long toDate;

    @JsonProperty("hasBill")
    @Schema(description = "Filter periods that have bills generated", example = "true")
    private Boolean hasBill;

    @JsonProperty("limit")
    @Schema(description = "Maximum number of results to return", example = "10", defaultValue = "100")
    private Integer limit;

    @JsonProperty("offset")
    @Schema(description = "Number of results to skip", example = "0", defaultValue = "0")
    private Integer offset;

    @JsonProperty("includeDeprecated")
    @Schema(description = "Include deprecated billing periods in search results", example = "false", defaultValue = "false")
    private Boolean includeDeprecated;

    /**
     * Get limit with default value.
     *
     * @return limit or default 100
     */
    public Integer getLimitOrDefault() {
        return limit != null && limit > 0 ? limit : 100;
    }

    /**
     * Get offset with default value.
     *
     * @return offset or default 0
     */
    public Integer getOffsetOrDefault() {
        return offset != null && offset >= 0 ? offset : 0;
    }

    /**
     * Check if filtering by bill existence.
     *
     * @return true if hasBill filter is specified
     */
    public boolean hasFilterByBill() {
        return hasBill != null;
    }

    /**
     * Determines if deprecated periods should be excluded from search results.
     *
     * @return true if deprecated periods must be filtered out
     */
    public boolean shouldExcludeDeprecated() {
        return includeDeprecated == null || !includeDeprecated;
    }
}
