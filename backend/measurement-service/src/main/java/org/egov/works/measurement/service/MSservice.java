package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.egov.works.measurement.web.models.MeasurementServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MSservice {

    @Autowired
    private MeasurementService msService;
    public ResponseEntity<MeasurementServiceResponse> createMeasurementService(MeasurementServiceRequest body){

        MeasurementServiceResponse measurementServiceResponse = new MeasurementServiceResponse();
//        for (int i = 0; i <  body.getMeasurements().size(); i++) {
//
//        }
        return new ResponseEntity<MeasurementServiceResponse>(measurementServiceResponse, HttpStatus.ACCEPTED);

    }

}
