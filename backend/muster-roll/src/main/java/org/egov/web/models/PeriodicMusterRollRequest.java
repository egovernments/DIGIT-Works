package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.egov.service.PeriodAwareMusterRollService.BillingPeriodDetails;

import java.util.List;

/**
 * PeriodicMusterRollRequest
 *
 * Request model for V2 period-aware muster roll creation.
 * Used by health-expense-calculator service during intermediate billing.
 *
 * @author DIGIT-Works
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodicMusterRollRequest {

    @JsonProperty("RequestInfo")
    @NotNull(message = "RequestInfo is mandatory")
    @Valid
    private RequestInfo requestInfo;

    @JsonProperty("tenantId")
    @NotNull(message = "Tenant ID is mandatory")
    @Size(min = 2, max = 64)
    private String tenantId;

    @JsonProperty("registerIds")
    @NotNull(message = "Register IDs are mandatory")
    @Size(min = 1, message = "At least one register ID is required")
    private List<String> registerIds;

    @JsonProperty("campaignNumber")
    @Size(max = 64)
    private String campaignNumber;

    @JsonProperty("billingPeriod")
    @NotNull(message = "Billing period details are mandatory")
    @Valid
    private BillingPeriodDetails billingPeriod;
}
