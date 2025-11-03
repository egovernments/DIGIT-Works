package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.response.ResponseInfo;

import java.util.List;

/**
 * BillingConfigResponse
 *
 * Response wrapper for billing configuration operations.
 * Contains the billing configuration and associated billing periods.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Response object for billing configuration operations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingConfigResponse {

    @JsonProperty("responseInfo")
    @Valid
    @Schema(description = "Response metadata")
    private ResponseInfo responseInfo;

    @JsonProperty("billingConfig")
    @Valid
    @Schema(description = "Billing configuration details")
    private BillingConfig billingConfig;

    @JsonProperty("periods")
    @Valid
    @Schema(description = "List of billing periods generated from configuration")
    private List<BillingPeriod> periods;

    @JsonProperty("totalPeriods")
    @Schema(description = "Total number of billing periods", example = "5")
    private Integer totalPeriods;

    /**
     * Helper method to add period count.
     */
    public BillingConfigResponse withTotalPeriods() {
        if (this.periods != null) {
            this.totalPeriods = this.periods.size();
        }
        return this;
    }
}
