package org.egov.works.measurement.web.models;

import java.util.ArrayList;
import java.util.List;

public class Ids {
    private List<String> measurementIds;
    private List<String> measureIds;

    public Ids() {
        measurementIds = new ArrayList<>();
        measureIds = new ArrayList<>();
    }

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
