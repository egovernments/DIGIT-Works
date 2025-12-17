package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.StaffService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.StaffPermissionRequest;
import org.egov.web.models.StaffPermissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/staff/v1")
public class StaffApiController {


    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final StaffService staffService;

    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public StaffApiController(ObjectMapper objectMapper, HttpServletRequest request, StaffService staffService, ResponseInfoFactory responseInfoFactory) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.staffService = staffService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<StaffPermissionResponse> createStaff(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody StaffPermissionRequest staffPermissionRequest) {
        StaffPermissionRequest enrichedRequest = staffService.createAttendanceStaff(staffPermissionRequest, false);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(staffPermissionRequest.getRequestInfo(), true);
        StaffPermissionResponse staffPermissionResponse = StaffPermissionResponse.builder().responseInfo(responseInfo)
                .staff(enrichedRequest.getStaff()).build();
        return new ResponseEntity<StaffPermissionResponse>(staffPermissionResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_delete", method = RequestMethod.POST)
    public ResponseEntity<StaffPermissionResponse> deleteStaff(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody StaffPermissionRequest staffPermissionRequest) {
        StaffPermissionRequest enrichedRequest = staffService.deleteAttendanceStaff(staffPermissionRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(staffPermissionRequest.getRequestInfo(), true);
        StaffPermissionResponse staffPermissionResponse = StaffPermissionResponse.builder().responseInfo(responseInfo)
                .staff(enrichedRequest.getStaff()).build();
        return new ResponseEntity<StaffPermissionResponse>(staffPermissionResponse, HttpStatus.OK);
    }

}
