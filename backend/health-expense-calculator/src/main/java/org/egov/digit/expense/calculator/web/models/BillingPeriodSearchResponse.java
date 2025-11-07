package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * BillingPeriodSearchResponse
 *
 * Response object containing search results for billing periods.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Response object containing billing period search results")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingPeriodSearchResponse {

    @JsonProperty("ResponseInfo")
    @Valid
    @Schema(description = "Response metadata")
    private ResponseInfo responseInfo;

    @JsonProperty("billingPeriods")
    @Valid
    @Schema(description = "List of billing periods matching search criteria")
    private List<BillingPeriod> billingPeriods;

    @JsonProperty("totalCount")
    @Schema(description = "Total number of periods matching criteria (without pagination)", example = "25")
    private Integer totalCount;

    @JsonProperty("message")
    @Schema(description = "Response message", example = "Billing periods retrieved successfully")
    private String message;

    /**
     * Get billing periods with null-safe list.
     *
     * @return billing periods list or empty list
     */
    public List<BillingPeriod> getBillingPeriods() {
        if (billingPeriods == null) {
            billingPeriods = new ArrayList<>();
        }
        return billingPeriods;
    }

    /**
     * Get count of returned periods.
     *
     * @return number of periods in response
     */
    public int getReturnedCount() {
        return getBillingPeriods().size();
    }

    /**
     * Check if search returned any results.
     *
     * @return true if periods found
     */
    public boolean hasResults() {
        return !getBillingPeriods().isEmpty();
    }
}
