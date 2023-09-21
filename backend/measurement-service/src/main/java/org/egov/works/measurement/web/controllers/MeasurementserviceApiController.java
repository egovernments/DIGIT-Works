package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MSservice;
import org.egov.works.measurement.service.MeasurementService;
import org.egov.works.measurement.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("")
public class MeasurementserviceApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MeasurementService service;
    @Autowired
    private MSservice msService;

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body) {
        MeasurementServiceResponse measurementServiceResponse = msService.handleCreateMeasurementService(body);
        return new ResponseEntity<MeasurementServiceResponse>(measurementServiceResponse,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        RestTemplate restTemplate = new RestTemplate();
        String measurementServiceUrl = "http://localhost:8080/measurement-service/measurement/v1/_search";
        ResponseEntity<MeasurementResponse> responseEntity = restTemplate.postForEntity(measurementServiceUrl, body, MeasurementResponse.class);
        MeasurementResponse measurementResponse = responseEntity.getBody();
        MeasurementServiceResponse measurementServiceResponse = new MeasurementServiceResponse();
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = changeToMeasurementService(measurementResponse.getMeasurements());
        measurementServiceResponse.setMeasurements(measurementServices);
        return new ResponseEntity<>(measurementServiceResponse, responseEntity.getStatusCode());
    }

    public List<org.egov.works.measurement.web.models.MeasurementService> changeToMeasurementService(List<Measurement> measurements) {
        List<org.egov.works.measurement.web.models.MeasurementService> measurementServices = new ArrayList<>();

        for (Measurement measurement : measurements) {
            org.egov.works.measurement.web.models.MeasurementService measurementService = new org.egov.works.measurement.web.models.MeasurementService();
            measurementService.setId(measurement.getId());
            measurementService.setTenantId(measurement.getTenantId());
            measurementService.setMeasurementNumber(measurement.getMeasurementNumber());
            measurementService.setPhysicalRefNumber(measurement.getPhysicalRefNumber());
            measurementService.setReferenceId(measurement.getReferenceId());
            measurementService.setEntryDate(measurement.getEntryDate());
            measurementService.setMeasures(measurement.getMeasures());
            measurementService.setIsActive(measurement.getIsActive());
            measurementService.setAuditDetails(measurement.getAuditDetails());

            measurementService.setWfStatus(null);
            measurementService.setWorkflow(null);

            measurementServices.add(measurementService);
        }

        return measurementServices;
    }



    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body){
        return msService.updateMeasurementService(body);
    }

}
