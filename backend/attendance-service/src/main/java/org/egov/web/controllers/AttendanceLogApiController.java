package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.web.models.AttendanceLogRequest;
import org.egov.web.models.AttendanceLogResponse;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.egov.web.models.RequestInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/log/v1")
public class AttendanceLogApiController {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest body) {
        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1SearchPOST(@Valid @ModelAttribute AttendanceLogSearchCriteria searchCriteria, @ApiParam(value = "") @Valid @RequestBody RequestInfoWrapper body) {
        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1UpdatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest body) {
        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
