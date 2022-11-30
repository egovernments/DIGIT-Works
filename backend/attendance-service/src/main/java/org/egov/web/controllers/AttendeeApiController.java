package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.service.AttendeeService;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/attendee/v1")
public class AttendeeApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AttendeeService attendeeService;

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendeeCreateResponse> attendanceAttendeeV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeCreateRequest attendeeCreateRequest) {
        AttendeeCreateResponse attendeeCreateResponse = attendeeService.createAttendee(attendeeCreateRequest);
        return new ResponseEntity<AttendeeCreateResponse>(attendeeCreateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_delete", method = RequestMethod.POST)
    public ResponseEntity<AttendeeDeleteResponse> attendanceAttendeeV1DeletePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeDeleteRequest attendeeDeleteRequest) {
        AttendeeDeleteResponse attendeeDeleteResponse = attendeeService.deleteAttendee(attendeeDeleteRequest);
        return new ResponseEntity<AttendeeDeleteResponse>(attendeeDeleteResponse, HttpStatus.OK);
    }

}
