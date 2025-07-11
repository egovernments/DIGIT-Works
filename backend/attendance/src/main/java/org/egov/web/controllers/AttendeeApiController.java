package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.AttendeeService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/attendee/v1")
public class AttendeeApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AttendeeService attendeeService;

    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public AttendeeApiController(ObjectMapper objectMapper, HttpServletRequest request, AttendeeService attendeeService, ResponseInfoFactory responseInfoFactory) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.attendeeService = attendeeService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendeeCreateResponse> createAttendee(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeCreateRequest attendeeCreateRequest) {
        AttendeeCreateRequest enrichedRequest = attendeeService.createAttendee(attendeeCreateRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendeeCreateRequest.getRequestInfo(), true);
        AttendeeCreateResponse attendeeCreateResponse = AttendeeCreateResponse.builder().responseInfo(responseInfo).attendees(enrichedRequest.getAttendees()).build();
        return new ResponseEntity<AttendeeCreateResponse>(attendeeCreateResponse, HttpStatus.OK);
    }

    @RequestMapping(value= "/_updateTag", method = RequestMethod.POST)
    public ResponseEntity<AttendeeUpdateTagResponse> updateAttendeeTag (
            @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody AttendeeUpdateTagRequest request) {
        AttendeeUpdateTagRequest enrichedRequest = attendeeService.updateAttendeeTag(request);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        AttendeeUpdateTagResponse response = AttendeeUpdateTagResponse.builder()
                .responseInfo(responseInfo)
                .attendees(enrichedRequest.getAttendees())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/_delete", method = RequestMethod.POST)
    public ResponseEntity<AttendeeDeleteResponse> deleteAttendee(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeDeleteRequest attendeeDeleteRequest) {
        AttendeeDeleteRequest enrichedRequest = attendeeService.deleteAttendee(attendeeDeleteRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendeeDeleteRequest.getRequestInfo(), true);
        AttendeeDeleteResponse attendeeDeleteResponse = AttendeeDeleteResponse.builder().responseInfo(responseInfo).attendees(enrichedRequest.getAttendees()).build();
        return new ResponseEntity<AttendeeDeleteResponse>(attendeeDeleteResponse, HttpStatus.OK);
    }

}
