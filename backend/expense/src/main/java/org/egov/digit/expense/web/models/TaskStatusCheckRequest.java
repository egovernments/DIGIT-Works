package org.egov.digit.expense.web.models;

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
public class TaskStatusCheckRequest {

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("requestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("retryCount")
    private int retryCount;

    @JsonProperty("maxRetries")
    private int maxRetries;
}
