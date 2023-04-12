package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Estimate
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estimate {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("projectId")
    private String projectId = null;

    @JsonProperty("estimateDetails")
    @Valid
    private List<EstimateDetail> estimateDetails = new ArrayList<>();

    public Estimate addEstimateDetailsItem(EstimateDetail estimateDetailsItem) {
        this.estimateDetails.add(estimateDetailsItem);
        return this;
    }

}

