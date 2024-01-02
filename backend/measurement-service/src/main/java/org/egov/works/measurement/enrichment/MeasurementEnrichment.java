package org.egov.works.measurement.enrichment;


import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.web.models.MeasurementService;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MeasurementEnrichment {
    public void enrichMeasurementServiceUpdate(MeasurementServiceRequest body , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = body.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich the workFlow Status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }

    public void enrichWf(MeasurementServiceRequest measurementServiceRequest , List<String> wfStatusList){
        List<MeasurementService> measurementServiceList = measurementServiceRequest.getMeasurements();
        for(int i=0;i<measurementServiceList.size();i++){
            measurementServiceList.get(i).setWfStatus(wfStatusList.get(i));                              // enrich wf status
            measurementServiceList.get(i).setWorkflow(measurementServiceList.get(i).getWorkflow());      // enrich the Workflow
        }
    }
}
