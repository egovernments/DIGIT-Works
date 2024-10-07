package org.egov.wms.web.model.Job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

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

    @JsonProperty("scheduledFrom")
    private Long scheduledFrom;

    @JsonProperty("scheduledTo")
    private Long scheduledTo;
}
