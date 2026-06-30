package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.common.producer.Producer;
import org.egov.service.FaceAuthEventService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/face-auth/v1")
public class FaceAuthEventApiController {

    private final FaceAuthEventService faceAuthEventService;
    private final ResponseInfoFactory responseInfoFactory;
    private final Producer producer;
    private final AttendanceServiceConfiguration config;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public FaceAuthEventApiController(FaceAuthEventService faceAuthEventService,
                                      ResponseInfoFactory responseInfoFactory,
                                      Producer producer,
                                      AttendanceServiceConfiguration config,
                                      HttpServletRequest httpServletRequest) {
        this.faceAuthEventService = faceAuthEventService;
        this.responseInfoFactory = responseInfoFactory;
        this.producer = producer;
        this.config = config;
        this.httpServletRequest = httpServletRequest;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<FaceAuthEventResponse> faceAuthEventCreatePost(
            @ApiParam(value = "", allowableValues = "application/json")
            @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody FaceAuthEventRequest request) {
        FaceAuthEventResponse response = faceAuthEventService.createFaceAuthEvents(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/bulk/_create", method = RequestMethod.POST)
    public ResponseEntity<ResponseInfo> faceAuthEventBulkCreatePost(
            @ApiParam(value = "", allowableValues = "application/json")
            @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody FaceAuthEventRequest request) {
        request.getRequestInfo().setApiId(httpServletRequest.getRequestURI());
        String tenantId = Optional.ofNullable(request).map(req -> req.getRequestInfo().getUserInfo().getTenantId())
                .get();
        producer.push(tenantId, config.getBulkCreateFaceAuthEventTopic(), request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true));
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<FaceAuthEventResponse> faceAuthEventSearchPost(
            @Valid @ModelAttribute FaceAuthEventSearchCriteria searchCriteria,
            @ApiParam(value = "") @Valid @RequestBody FaceAuthEventSearchRequest searchRequest) {
        FaceAuthEventResponse response = faceAuthEventService.searchFaceAuthEvents(
                RequestInfoWrapper.builder()
                        .requestInfo(searchRequest.getRequestInfo())
                        .build(),
                searchRequest.getFaceAuthEventSearchCriteria() != null
                        ? searchRequest.getFaceAuthEventSearchCriteria()
                        : searchCriteria
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
