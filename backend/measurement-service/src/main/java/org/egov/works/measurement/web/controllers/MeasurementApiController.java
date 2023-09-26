package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.egov.works.measurement.web.models.MeasurementResponse;
import org.egov.works.measurement.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("/measurement")
public class MeasurementApiController {

    @Autowired
    private MeasurementRegistry measurementRegistry; // Import MeasurementRegistry if not imported already

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final MeasurementRegistry service;

    public MeasurementApiController(ObjectMapper objectMapper, HttpServletRequest request, MeasurementRegistry service) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
    }

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        String accept = request.getHeader("Accept");
        return service.createMeasurement(body);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementsV1SearchPost(
            @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        MeasurementResponse response= measurementRegistry.createSearchResponse(body);
        MeasurementCriteria criteria = body.getCriteria();
        if (criteria != null) {
            List<Measurement> measurements = measurementRegistry.searchMeasurements(criteria, body);
            response.setMeasurements(measurements);
            return new ResponseEntity<MeasurementResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<MeasurementResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        MeasurementResponse measurementResponse= service.updateMeasurement(body);
        return new ResponseEntity<>(measurementResponse, HttpStatus.ACCEPTED);
    }
}
