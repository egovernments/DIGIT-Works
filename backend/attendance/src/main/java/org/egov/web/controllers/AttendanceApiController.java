package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.repository.RegisterRepository;
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

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/v1")
public class AttendanceApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ResponseInfoFactory responseInfoCreator;

    private final AttendanceRegisterService attendanceRegisterService;
    private final ResponseInfoFactory responseInfoFactory;

    private final RegisterRepository registerRepository;

    @Autowired
    public AttendanceApiController(ObjectMapper objectMapper, HttpServletRequest request, ResponseInfoFactory responseInfoCreator, AttendanceRegisterService attendanceRegisterService, ResponseInfoFactory responseInfoFactory, org.egov.repository.RegisterRepository registerRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.responseInfoCreator = responseInfoCreator;
        this.attendanceRegisterService = attendanceRegisterService;
        this.responseInfoFactory = responseInfoFactory;
        this.registerRepository = registerRepository;
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
        Long[] counts = registerRepository.getRegisterCounts(searchCriteria);
        Map<String, Long> statusCount = new HashMap<>();

        // Initialize the map with default values
        statusCount.put("APPROVED", counts[1]);
        statusCount.put("APPROVAL_PENDING", counts[2]);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(attendanceRegisterList).totalCount(counts[0]).statusCount(statusCount).build();
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
