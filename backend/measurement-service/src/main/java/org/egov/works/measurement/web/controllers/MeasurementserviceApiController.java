package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MeasurementService;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("")
public class MeasurementserviceApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MeasurementRegistry service;
    @Autowired
    private MeasurementService msService;

    @Autowired MeasurementApiController measurementApiController;

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body) {
        MeasurementServiceResponse measurementServiceResponse = msService.handleCreateMeasurementService(body);
        return new ResponseEntity<MeasurementServiceResponse>(measurementServiceResponse,HttpStatus.ACCEPTED);
    }



    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body){
        MeasurementServiceResponse measurementServiceResponse= msService.updateMeasurementService(body);
        return new ResponseEntity<>(measurementServiceResponse, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        ResponseEntity<MeasurementResponse> responseEntity=measurementApiController.measurementsV1SearchPost(body);
        MeasurementResponse measurementResponse = responseEntity.getBody();
        MeasurementServiceResponse measurementServiceResponse = service.makeSearchResponse(body);
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = service.changeToMeasurementService(measurementResponse.getMeasurements());
        measurementServiceResponse.setMeasurements(measurementServices);
        return new ResponseEntity<>(measurementServiceResponse, responseEntity.getStatusCode());
    }

}
