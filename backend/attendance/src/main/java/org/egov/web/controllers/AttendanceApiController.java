package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.AttendanceRegisterService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterRequest;
import org.egov.web.models.AttendanceRegisterResponse;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Controller
@RequestMapping("/v1")
public class AttendanceApiController {

    private final ResponseInfoFactory responseInfoCreator;

    private final AttendanceRegisterService attendanceRegisterService;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public AttendanceApiController(ResponseInfoFactory responseInfoCreator, AttendanceRegisterService attendanceRegisterService, ResponseInfoFactory responseInfoFactory) {
        this.responseInfoCreator = responseInfoCreator;
        this.attendanceRegisterService = attendanceRegisterService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> createAttendanceRegister(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceRegisterRequest attendanceRegisterRequest) {
        AttendanceRegisterRequest enrichedAttendanceRegisterRequest = attendanceRegisterService.createAttendanceRegister(attendanceRegisterRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(enrichedAttendanceRegisterRequest.getAttendanceRegister()).build();
        return new ResponseEntity<AttendanceRegisterResponse>(attendanceRegisterResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> searchAttendanceRegister(@Valid @ModelAttribute AttendanceRegisterSearchCriteria searchCriteria, @Valid @RequestBody RequestInfoWrapper requestInfoWrapper) {
        List<AttendanceRegister> attendanceRegisterList = attendanceRegisterService.searchAttendanceRegister(requestInfoWrapper, searchCriteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(attendanceRegisterList).build();
        return new ResponseEntity<AttendanceRegisterResponse>(attendanceRegisterResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> updateAttendanceRegister(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceRegisterRequest attendanceRegisterRequest) {
        AttendanceRegisterRequest enrichedAttendanceRegisterRequest = attendanceRegisterService.updateAttendanceRegister(attendanceRegisterRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(enrichedAttendanceRegisterRequest.getAttendanceRegister()).build();
        return new ResponseEntity<AttendanceRegisterResponse>(attendanceRegisterResponse, HttpStatus.OK);
    }


}
