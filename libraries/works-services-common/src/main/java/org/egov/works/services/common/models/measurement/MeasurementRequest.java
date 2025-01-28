package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.common.contract.request.RequestInfo;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;


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
