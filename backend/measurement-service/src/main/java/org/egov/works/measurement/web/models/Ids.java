package org.egov.works.measurement.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Ids {
    private List<String> measurementIds;
    private List<String> measureIds;

    public List<String> getMeasurementIds() {
        return measurementIds;
    }

    public void setMeasurementIds(List<String> measurementIds) {
        this.measurementIds = measurementIds;
    }

    public List<String> getMeasureIds() {
        return measureIds;
    }

    public void setMeasureIds(List<String> measureIds) {
        this.measureIds = measureIds;
    }
}
