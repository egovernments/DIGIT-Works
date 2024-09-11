package org.egov.wms.web.model.Job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchCriteria {
    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("reportNumber")
    private String reportNumber;

    @JsonProperty("status")
    private JobStatus status;
}
