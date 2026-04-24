package org.egov.digit.expense.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportMetaUpdateRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("billId")
    @NotNull
    private String billId;

    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("reportDetails")
    @NotNull
    private Map<String, Object> reportDetails;
}
