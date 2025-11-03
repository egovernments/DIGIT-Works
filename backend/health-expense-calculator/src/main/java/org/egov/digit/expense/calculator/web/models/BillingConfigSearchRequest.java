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

import java.util.List;

/**
 * BillingConfigSearchRequest
 *
 * Request object for searching billing configurations and periods.
 *
 * @author DIGIT-Works
 */
@Schema(description = "Request object for searching billing configurations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingConfigSearchRequest {

    @JsonProperty("RequestInfo")
    @NotNull
    @Valid
    @Schema(description = "Request metadata", required = true)
    private RequestInfo requestInfo;

    @JsonProperty("searchCriteria")
    @NotNull
    @Valid
    @Schema(description = "Search criteria for billing configuration", required = true)
    private BillingConfigSearchCriteria searchCriteria;
}
