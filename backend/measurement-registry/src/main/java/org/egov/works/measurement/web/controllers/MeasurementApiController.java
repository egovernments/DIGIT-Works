package org.egov.works.measurement.web.controllers;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.works.measurement.service.MeasurementRegistry;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("")
public class MeasurementApiController {

    @Autowired
    private MeasurementRegistry measurementRegistry;

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        MeasurementResponse measurementResponse = measurementRegistry.createMeasurement(body);
        return new ResponseEntity<>(measurementResponse,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementsV1SearchPost(
            @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        MeasurementResponse response= measurementRegistry.createSearchResponse(body);
        MeasurementCriteria criteria = body.getCriteria();
        if (criteria != null) {
            List<Measurement> measurements = measurementRegistry.searchMeasurements(criteria, body);
            response.setMeasurements(measurements);
            Integer count = measurementRegistry.getMeasurementCount(criteria);
            response.setPagination(body.getPagination());
            response.getPagination().setTotalCount(count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        MeasurementResponse measurementResponse= measurementRegistry.updateMeasurement(body);
        return new ResponseEntity<>(measurementResponse, HttpStatus.ACCEPTED);
    }
}
