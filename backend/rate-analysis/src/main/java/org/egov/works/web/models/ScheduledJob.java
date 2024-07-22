package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * ScheduledJob
 */
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduledJob {
    @JsonProperty("id")

    @Valid
    private String id = null;

    @JsonProperty("tenantId")

    private String tenantId = null;

    @JsonProperty("jobId")

    private String jobId = null;

    @JsonProperty("rateEffectiveFrom")

    private Long rateEffectiveFrom = null;

    @JsonProperty("noOfSorScheduled")

    @Valid
    private Integer noOfSorScheduled = null;

    @JsonProperty("auditDetails")

    @Valid
    private AuditDetails auditDetails = null;
    @JsonProperty("status")

    private StatusEnum status = null;
    @JsonProperty("sorDetails")
    @Valid
    private List<SorDetail> sorDetails = null;

    public ScheduledJob addSorDetailsItem(SorDetail sorDetailsItem) {
        if (this.sorDetails == null) {
            this.sorDetails = new ArrayList<>();
        }
        this.sorDetails.add(sorDetailsItem);
        return this;
    }
}
