package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.web.models.AttendeeCreateRequest;
import org.egov.web.models.AttendeeCreateResponse;
import org.egov.web.models.AttendeeDeleteRequest;
import org.egov.web.models.AttendeeDeleteResponse;
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

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendeeCreateResponse> attendanceAttendeeV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeCreateRequest body) {
        return new ResponseEntity<AttendeeCreateResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_delete", method = RequestMethod.POST)
    public ResponseEntity<AttendeeDeleteResponse> attendanceAttendeeV1DeletePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeDeleteRequest body) {
        return new ResponseEntity<AttendeeDeleteResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
