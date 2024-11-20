package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a measurement entry request
 */
@Schema(description = "Encapsulates a measurement entry request")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-25T13:42:37.896+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementRequest {

    @JsonProperty("RequestInfo")
    @Valid
    private RequestInfo requestInfo = null;

    @JsonProperty("measurements")
    @Valid
    @Size(message = "At least one measurement is required" , min = 1)
    private List<Measurement> measurements = null;


    public MeasurementRequest addMeasurementsItem(Measurement measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }

}
