package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.Workflow;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Encapsulates a measurement book service request. Takes a singleton along with workflow.
 */
@Schema(description = "Encapsulates a measurement book service request. Takes a singleton along with workflow.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementServiceRequest {

    @JsonProperty("requestInfo")
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("measurements")
    @Valid
    private List<Measurement> measurements = null;

    @JsonProperty("workflow")
    @Valid
    private Workflow workflow = null;

    public MeasurementServiceRequest addMeasurementsItem(Measurement measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }

}
