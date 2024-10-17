package org.egov.works.services.common.models.measurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class MeasurementServiceResponse   {
    @JsonProperty("ResponseInfo")

    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("measurements")
    @Valid
    private List<MeasurementService> measurements = null;


    public MeasurementServiceResponse addMeasurementsItem(MeasurementService measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }
}