package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a measurement service response. Returns an array of measurements with workflow.
 */
@Schema(description = "Encapsulates a measurement service response. Returns an array of measurements with workflow.")
@Validated
@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-15T11:39:57.604+05:30[Asia/Calcutta]")
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

    @JsonProperty("pagination")
    private Pagination pagination = null;


    public MeasurementServiceResponse addMeasurementsItem(MeasurementService measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }
}
