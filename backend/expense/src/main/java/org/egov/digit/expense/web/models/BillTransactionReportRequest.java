package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

/**
 * Request object for generating bill transaction report
 */
@Schema(description = "Request object for generating bill transaction report")
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillTransactionReportRequest {

    @JsonProperty("RequestInfo")
    @Valid
    @NotNull
    private RequestInfo requestInfo;

    @JsonProperty("billTransactionReport")
    @Valid
    @NotNull
    private BillTransactionReport billTransactionReport;
}
