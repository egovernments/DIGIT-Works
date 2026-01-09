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
 * BillingConfigSearchCriteria
 *
 * Search criteria for querying billing configurations and periods.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Search criteria for billing configuration queries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingConfigSearchCriteria {

    /**
     * Default limit for search results
     */
    private static final int DEFAULT_LIMIT = 100;

    /**
     * Maximum limit for search results to prevent resource exhaustion
     * Should match expense.billing.search.max.limit configuration
     */
    private static final int MAX_LIMIT = 200;

    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    @Schema(description = "Tenant identifier", example = "mz.meghalaya", required = true)
    private String tenantId;

    @JsonProperty("ids")
    @Schema(description = "List of billing configuration IDs", example = "[\"bc-123\", \"bc-456\"]")
    private List<String> ids;

    @JsonProperty("projectId")
    @Size(min = 2, max = 256)
    @Schema(description = "Project identifier to filter billing configurations", example = "PROJECT-2025-001")
    private String projectId;

    @JsonProperty("projectIds")
    @Schema(description = "List of project identifiers to filter", example = "[\"PROJECT-001\", \"PROJECT-002\"]")
    private List<String> projectIds;

    @JsonProperty("campaignNumber")
    @Size(min = 2, max = 256)
    @Schema(description = "Campaign number/ID to filter billing configurations", example = "CMP-2025-10-14-007097")
    private String campaignNumber;

    @JsonProperty("campaignNumbers")
    @Schema(description = "List of campaign numbers to filter", example = "[\"CMP-001\", \"CMP-002\"]")
    private List<String> campaignNumbers;

    @JsonProperty("billingFrequency")
    @Schema(description = "Filter by billing frequency",
            example = "WEEKLY",
            allowableValues = {"WEEKLY", "BI_WEEKLY", "MONTHLY", "CUSTOM", "END_OF_CAMPAIGN"})
    private BillingFrequency billingFrequency;

    @JsonProperty("status")
    @Size(min = 2, max = 32)
    @Schema(description = "Filter by status",
            example = "ACTIVE",
            allowableValues = {"ACTIVE", "INACTIVE", "COMPLETED"})
    private String status;

    @JsonProperty("createdFrom")
    @Schema(description = "Filter configurations created after this timestamp", example = "1704067200000")
    private Long createdFrom;

    @JsonProperty("createdTo")
    @Schema(description = "Filter configurations created before this timestamp", example = "1706745600000")
    private Long createdTo;

    @JsonProperty("includePeriods")
    @Schema(description = "Include billing periods in response", example = "true", defaultValue = "false")
    private Boolean includePeriods;

    @JsonProperty("limit")
    @Schema(description = "Maximum number of results to return", example = "10", defaultValue = "100")
    private Integer limit;

    @JsonProperty("offset")
    @Schema(description = "Number of results to skip", example = "0", defaultValue = "0")
    private Integer offset;

    /**
     * Check if periods should be included in search results.
     *
     * @return true if periods should be included
     */
    public boolean shouldIncludePeriods() {
        return includePeriods != null && includePeriods;
    }

    /**
     * Get limit with default value and maximum cap.
     * Returns the requested limit capped at MAX_LIMIT to prevent resource exhaustion.
     *
     * @return limit (capped at MAX_LIMIT) or DEFAULT_LIMIT if not specified or invalid
     */
    public Integer getLimitOrDefault() {
        if (limit == null || limit <= 0) {
            return DEFAULT_LIMIT;
        }
        return Math.min(limit, MAX_LIMIT);
    }

    /**
     * Get offset with default value.
     *
     * @return offset or default 0
     */
    public Integer getOffsetOrDefault() {
        return offset != null && offset >= 0 ? offset : 0;
    }
}
