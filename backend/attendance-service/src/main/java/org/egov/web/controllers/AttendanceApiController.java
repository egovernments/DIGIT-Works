package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T14:44:21.051+05:30")

@Controller
@RequestMapping("/attendnace/v1")
public class AttendanceApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public AttendanceApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/attendance/attendee/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendeeCreateResponse> attendanceAttendeeV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeCreateRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendeeCreateResponse>(objectMapper.readValue("{  \"attendees\" : [ {    \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",    \"denrollmentDate\" : 5.962133916683182,    \"enrollmentDate\" : 1.4658129805029452,    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"  }, {    \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",    \"denrollmentDate\" : 5.962133916683182,    \"enrollmentDate\" : 1.4658129805029452,    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"  } ],  \"responseInfo\" : \"{}\"}", AttendeeCreateResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendeeCreateResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendeeCreateResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/attendee/v1/_delete", method = RequestMethod.POST)
    public ResponseEntity<AttendeeDeleteResponse> attendanceAttendeeV1DeletePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendeeDeleteRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendeeDeleteResponse>(objectMapper.readValue("{  \"attendees\" : [ {    \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",    \"denrollmentDate\" : 5.962133916683182,    \"enrollmentDate\" : 1.4658129805029452,    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"  }, {    \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",    \"denrollmentDate\" : 5.962133916683182,    \"enrollmentDate\" : 1.4658129805029452,    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"  } ],  \"responseInfo\" : \"{}\"}", AttendeeDeleteResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendeeDeleteResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendeeDeleteResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/log/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceLogResponse>(objectMapper.readValue("{  \"responseInfo\" : \"{}\",  \"attendance\" : [ {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  }, {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  } ]}", AttendanceLogResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceLogResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/log/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1SearchPOST(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "Id of the register") @Valid @RequestParam(value = "registerId", required = false) String registerId, @ApiParam(value = "Starting time from which the attendance needs to be searched") @Valid @RequestParam(value = "fromTime", required = false) Double fromTime, @ApiParam(value = "End time till which the attendance needs to be searched") @Valid @RequestParam(value = "toTime", required = false) Double toTime, @ApiParam(value = "List of individual ids") @Valid @RequestParam(value = "individualIds", required = false) List<String> individualIds, @ApiParam(value = "The status of the attendance log.", allowableValues = "ACTIVE, INACTIVE") @Valid @RequestParam(value = "status", required = false) String status, @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody RequestInfoWrapper body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceLogResponse>(objectMapper.readValue("{  \"responseInfo\" : \"{}\",  \"attendance\" : [ {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  }, {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  } ]}", AttendanceLogResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceLogResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/log/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<AttendanceLogResponse> attendanceLogV1UpdatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceLogRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceLogResponse>(objectMapper.readValue("{  \"responseInfo\" : \"{}\",  \"attendance\" : [ {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  }, {    \"registerId\" : \"registerId\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"individualId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"time\" : 0.8008281904610115,    \"type\" : \"type\",    \"additionalDetails\" : \"{}\",    \"status\" : \"{}\",    \"documentIds\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ]  } ]}", AttendanceLogResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceLogResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceLogResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/staff/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<StaffPermissionResponse> attendanceStaffV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody StaffPermissionRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StaffPermissionResponse>(objectMapper.readValue("{  \"staff\" : {    \"permissionLevels\" : [ { }, { } ],    \"registerId\" : \"registerId\",    \"denrollmentDate\" : 6.027456183070403,    \"enrollmentDate\" : 0.8008281904610115,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"userId\" : \"userId\"  },  \"responseInfo\" : \"{}\"}", StaffPermissionResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StaffPermissionResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StaffPermissionResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/staff/v1/_delete", method = RequestMethod.POST)
    public ResponseEntity<StaffPermissionResponse> attendanceStaffV1DeletePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody StaffPermissionRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StaffPermissionResponse>(objectMapper.readValue("{  \"staff\" : {    \"permissionLevels\" : [ { }, { } ],    \"registerId\" : \"registerId\",    \"denrollmentDate\" : 6.027456183070403,    \"enrollmentDate\" : 0.8008281904610115,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"userId\" : \"userId\"  },  \"responseInfo\" : \"{}\"}", StaffPermissionResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StaffPermissionResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StaffPermissionResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> attendanceV1CreatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceRegisterRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceRegisterResponse>(objectMapper.readValue("{  \"attendanceRegister\" : [ {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  }, {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  } ],  \"responseInfo\" : \"{}\"}", AttendanceRegisterResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> attendanceV1SearchPOST(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "Id of the register") @Valid @RequestParam(value = "id", required = false) String id, @ApiParam(value = "Custom formatted Register id") @Valid @RequestParam(value = "registerNumber", required = false) String registerNumber, @ApiParam(value = "Name of the register") @Valid @RequestParam(value = "name", required = false) String name, @ApiParam(value = "Return registers with any overlap in the given time period") @Valid @RequestParam(value = "fromDate", required = false) Double fromDate, @ApiParam(value = "Return registers with any overlap in the given time period") @Valid @RequestParam(value = "toDate", required = false) Double toDate, @ApiParam(value = "Status of the register. This can't be the only query param. It should be paired with some other search param.") @Valid @RequestParam(value = "status", required = false) String status, @ApiParam(value = "Get all the registers where the given Individual was registered.") @Valid @RequestParam(value = "attendeeId", required = false) String attendeeId, @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody RequestInfoWrapper body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceRegisterResponse>(objectMapper.readValue("{  \"attendanceRegister\" : [ {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  }, {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  } ],  \"responseInfo\" : \"{}\"}", AttendanceRegisterResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/attendance/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<AttendanceRegisterResponse> attendanceV1UpdatePOST(@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType, @ApiParam(value = "") @Valid @RequestBody AttendanceRegisterRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendanceRegisterResponse>(objectMapper.readValue("{  \"attendanceRegister\" : [ {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  }, {    \"endDate\" : 1.665497271E12,    \"attendees\" : [ {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    }, {      \"registerId\" : \"32e33343-7b4c-4353-9abf-4de8f5bcd764\",      \"denrollmentDate\" : 5.962133916683182,      \"enrollmentDate\" : 1.4658129805029452,      \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",      \"individualId\" : \"2bafdd8d-5673-4690-b3d0-e13d7ac0cf24\"    } ],    \"auditDetails\" : \"{}\",    \"tenantId\" : \"pb.amritsar\",    \"name\" : \"Class-10-E Physics\",    \"staff\" : [ {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    }, {      \"permissionLevels\" : [ { }, { } ],      \"registerId\" : \"registerId\",      \"denrollmentDate\" : 6.027456183070403,      \"enrollmentDate\" : 0.8008281904610115,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"userId\" : \"userId\"    } ],    \"id\" : \"64e33343-7b4c-4353-9abf-4de8f5bcd732\",    \"registerNumber\" : \"REG/2022-23/001\",    \"additionalDetails\" : \"{}\",    \"startDate\" : 1.665497225E12,    \"status\" : \"{}\"  } ],  \"responseInfo\" : \"{}\"}", AttendanceRegisterResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendanceRegisterResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
