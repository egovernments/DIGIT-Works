package org.egov.works.measurement.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.response.ResponseInfo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

/**
 * Encapsulates a measurement response
 */
@Schema(description = "Encapsulates a measurement response")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementResponse {

    @JsonProperty("ResponseInfo")
    @Valid
    private ResponseInfo responseInfo = null;

    @JsonProperty("measurements")
    @Valid
    private List<Measurement> measurements = null;


    public MeasurementResponse addMeasurementsItem(Measurement measurementsItem) {
        if (this.measurements == null) {
            this.measurements = new ArrayList<>();
        }
        this.measurements.add(measurementsItem);
        return this;
    }

}
