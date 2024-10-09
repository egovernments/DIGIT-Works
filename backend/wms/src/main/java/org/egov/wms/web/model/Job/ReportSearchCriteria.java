package org.egov.wms.web.model.Job;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 2, max = 64)
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
