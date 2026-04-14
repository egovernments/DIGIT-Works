package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

/**
 * Trigger published to report-generation-trigger Kafka topic when a bill is updated.
 * JSON shape is intentionally compatible with health-expense-calculator's ReportGenerationTrigger.
 * forceRegenerate=true signals the calculator to bypass Redis dedup cache and regenerate reports.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRegenerationTrigger {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("billId")
    private String billId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("createdTime")
    private Long createdTime;

    @JsonProperty("numberOfBillDetails")
    private Integer numberOfBillDetails;

    @JsonProperty("forceRegenerate")
    private Boolean forceRegenerate;
}
