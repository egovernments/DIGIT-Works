package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.AttendanceLogConfiguration;
import org.egov.common.producer.Producer;
import org.egov.service.AttendanceLogService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.AttendanceLogRequest;
import org.egov.web.models.AttendanceLogResponse;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.egov.web.models.AttendanceLogSearchRequest;
import org.egov.web.models.RequestInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/log/v1")
public class AttendanceLogApiController {


    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AttendanceLogService attendanceLogService;

    private final ResponseInfoFactory responseInfoFactory;

    private final AttendanceLogConfiguration attendanceLogConfiguration;

    private final HttpServletRequest httpServletRequest;

    private final Producer producer;

    public AttendanceLogApiController(ObjectMapper objectMapper, HttpServletRequest request, AttendanceLogService attendanceLogService, ResponseInfoFactory responseInfoFactory, AttendanceLogConfiguration attendanceLogConfiguration, HttpServletRequest httpServletRequest, Producer producer) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.attendanceLogService = attendanceLogService;
        this.responseInfoFactory = responseInfoFactory;
        this.attendanceLogConfiguration = attendanceLogConfiguration;
        this.httpServletRequest = httpServletRequest;
        this.producer = producer;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest attendanceLogRequest) {
        AttendanceLogResponse attendanceLogResponse = attendanceLogService.createAttendanceLog(attendanceLogRequest);
        return new ResponseEntity<AttendanceLogResponse>(attendanceLogResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/bulk/_create", method = RequestMethod.POST)
    public ResponseEntity<ResponseInfo> attendanceLogV1BulkCreatePost(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest attendanceLogRequest) {
        attendanceLogRequest.getRequestInfo().setApiId(httpServletRequest.getRequestURI());
        attendanceLogService.putInCache(attendanceLogRequest.getAttendance());
        producer.push(attendanceLogConfiguration.getCreateAttendanceLogBulkTopic(), attendanceLogRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseInfoFactory.createResponseInfoFromRequestInfo(attendanceLogRequest.getRequestInfo(), true));
    }
    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1SearchPOST(@Valid @ModelAttribute AttendanceLogSearchCriteria searchCriteria, @ApiParam(value = "") @Valid @RequestBody AttendanceLogSearchRequest attendanceLogSearchRequest) {
        AttendanceLogResponse attendanceLogResponse = attendanceLogService.searchAttendanceLog(
                RequestInfoWrapper.builder()
                        .requestInfo(attendanceLogSearchRequest.getRequestInfo())
                        .build(),
                attendanceLogSearchRequest.getAttendanceLogSearchCriteria() != null
                        ? attendanceLogSearchRequest.getAttendanceLogSearchCriteria()
                        : searchCriteria
        );
        return new ResponseEntity<AttendanceLogResponse>(attendanceLogResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1UpdatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest attendanceLogRequest) {
        AttendanceLogResponse attendanceLogResponse = attendanceLogService.updateAttendanceLog(attendanceLogRequest);
        return new ResponseEntity<AttendanceLogResponse>(attendanceLogResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/bulk/_update", method = RequestMethod.POST)
    public ResponseEntity<ResponseInfo> attendanceLogV1BulkUpdatePost(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest attendanceLogRequest) {
        attendanceLogRequest.getRequestInfo().setApiId(httpServletRequest.getRequestURI());
        attendanceLogService.putInCache(attendanceLogRequest.getAttendance());
        producer.push(attendanceLogConfiguration.getUpdateAttendanceLogBulkTopic(), attendanceLogRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseInfoFactory.createResponseInfoFromRequestInfo(attendanceLogRequest.getRequestInfo(), true));
    }

}
