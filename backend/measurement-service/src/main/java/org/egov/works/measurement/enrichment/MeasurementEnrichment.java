package org.egov.works.measurement.enrichment;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.web.models.Document;
import org.egov.works.measurement.web.models.MeasurementService;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MeasurementEnrichment {

    @Autowired
    private ObjectMapper objectMapper;

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

    public void enrichMeasurementWithActiveDocuments(List<MeasurementService> measurementServiceList){
        for(MeasurementService measurementService : measurementServiceList){
            List<Document> documents = measurementService.getDocuments();
            if(documents != null && !documents.isEmpty()){
                Iterator<Document> iterator = documents.iterator();
                while(iterator.hasNext()){
                    Document document = iterator.next();
                    Map<String, Object> additionalDetailsMap = objectMapper.convertValue(document.getAdditionalDetails(), Map.class);
                    if(additionalDetailsMap.containsKey("isActive")) {
                        boolean isActive = (boolean) additionalDetailsMap.get("isActive");
                        if (!isActive) {
                            // Removing the inActive document
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }
}
