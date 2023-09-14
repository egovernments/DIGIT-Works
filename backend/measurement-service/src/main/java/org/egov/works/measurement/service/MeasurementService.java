package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.works.measurement.enrichment.MeasurementEnrichment;
import org.egov.works.measurement.kafka.Producer;
import org.egov.works.measurement.util.MdmsUtil;
import org.egov.works.measurement.validator.MeasurementServiceValidator;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MeasurementService {

    @Autowired
    private MeasurementServiceValidator validator;

    @Autowired
    private MeasurementEnrichment enrichmentUtil;

//    @Autowired
//    private UserService userService;

//    @Autowired
//    private WorkflowService workflowService;

    @Autowired
    private MdmsUtil mdmsUtil;

    @Autowired
    private Producer producer;

    public Measurement updateMeasurement(MeasurementRequest measurementRegistrationRequest) {
        // Initialize masterNameList with a single element "uom"
        List<String> masterNameList = Arrays.asList("uom");

        Map<String, Map<String, JSONArray>> mdmsData=mdmsUtil.fetchMdmsData(measurementRegistrationRequest.getRequestInfo(),measurementRegistrationRequest.getMeasurements().get(0).getTenantId(),"common-masters",masterNameList);
        System.out.println(mdmsData);

        return measurementRegistrationRequest.getMeasurements().get(0);
    }

}
