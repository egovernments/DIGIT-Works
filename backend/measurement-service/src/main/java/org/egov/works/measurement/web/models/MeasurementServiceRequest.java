package org.egov.works.measurement.web.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.web.models.MeasurementService;
//import org.egov.works.measurement.web.models.RequestInfo;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Encapsulates a measurement book service request. Takes a singleton along with workflow.
 */
@Schema(description = "Encapsulates a measurement book service request. Takes a singleton along with workflow.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-15T11:39:57.604+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementServiceRequest   {
    @JsonProperty("RequestInfo")

    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("measurements")
    @Valid
    private List<MeasurementService> measurements = null;


    public MeasurementServiceRequest addMeasurementsItem(MeasurementService measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }

}