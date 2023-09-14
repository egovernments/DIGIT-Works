package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class MeasurementService {

//    @Autowired
//    private MBvalidator validator;

    @Autowired
    private MeasurementEnrichment enrichmentUtil;

//    @Autowired
//    private UserService userService;

//    @Autowired
//    private WorkflowService workflowService;

    @Autowired
    private Producer producer;

    public Measurement updateMbApplication(MeasurementRequest measurementRegistrationRequest) {
        // Validate whether the application that is being requested for update indeed exists
//        Measurement existingMeasurement = validator.validateApplicationExistence(measurementRegistrationRequest.getMeasurements().get(0));

//        // Enrich registration upon update
//        enrichmentUtil.enrichMeasurementRegistrationUponUpdate();

        // Just like create request, update request will be handled asynchronously by the persister
        producer.push("update-mb-application", measurementRegistrationRequest);

        return measurementRegistrationRequest.getMeasurements().get(0);
    }

}
