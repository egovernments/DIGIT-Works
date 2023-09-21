package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MSservice;
import org.egov.works.measurement.service.MeasurementService;
import org.egov.works.measurement.web.models.ErrorRes;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.egov.works.measurement.web.models.MeasurementServiceResponse;
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
        return new ResponseEntity<MeasurementServiceResponse>(measurementServiceResponse,HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        return new ResponseEntity<MeasurementServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body){
        return msService.updateMeasurementService(body);
    }

}
