package org.egov.digit.expense.calculator.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * BillingConfigRequest
 *
 * Request wrapper for billing configuration operations.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Request object for billing configuration operations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingConfigRequest {

    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    @Schema(description = "Request metadata", required = true)
    private RequestInfo requestInfo;

    @JsonProperty("billingConfig")
    @NotNull
    @Valid
    @Schema(description = "Billing configuration details", required = true)
    private BillingConfig billingConfig;
}
