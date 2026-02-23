package org.egov.web.models.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportGenerationRequest {

    @JsonProperty("requestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("musterRollId")
    private String musterRollId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("reportType")
    private String reportType;  // e.g., "ATTENDANCE_REPORT"

    @JsonProperty("reportFormat")
    private String reportFormat;  // e.g., "EXCEL"

    @JsonProperty("timestamp")
    private Long timestamp;
}
