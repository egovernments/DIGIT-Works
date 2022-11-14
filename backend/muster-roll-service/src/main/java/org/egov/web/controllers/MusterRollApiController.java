package org.egov.web.controllers;


import java.math.BigDecimal;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollResponse;
import org.egov.common.contract.request.RequestInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

import javax.validation.constraints.*;
    import javax.validation.Valid;
    import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Controller
    @RequestMapping("/muster-roll/v1")
    public class MusterRollApiController{

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private HttpServletRequest request;

        @RequestMapping(value="/_create", method = RequestMethod.POST)
        public ResponseEntity<MusterRollResponse> musterRollV1CreatePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody MusterRollRequest musterRollRequest) {
            String accept = request.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                try {
                    return new ResponseEntity<MusterRollResponse>(objectMapper.readValue("{  \"responseInfo\" : {    \"debug\" : \"{}\",    \"signature\" : \"signature\",    \"resMsgId\" : \"resMsgId\",    \"additionalInfo\" : \"{}\",    \"msgId\" : \"msgId\",    \"information\" : \"{}\",    \"error\" : {      \"code\" : \"code\",      \"description\" : \"description\",      \"message\" : \"message\",      \"params\" : [ \"params\", \"params\" ]    },    \"ts\" : 0,    \"status\" : \"COMPLETED\"  },  \"musterRolls\" : [ {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  }, {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  } ]}", MusterRollResponse.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    return new ResponseEntity<MusterRollResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
        }


        @RequestMapping(value="/_search", method = RequestMethod.POST)
        public ResponseEntity<MusterRollResponse> musterRollV1SearchPost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@ApiParam(value = "Parameter to carry Request metadata in the request body"  )  @Valid @RequestBody RequestInfo requestInfo,@ApiParam(value = "Id of the muster roll") @Valid @RequestParam(value = "id", required = false) String id,@ApiParam(value = "(Custom-formatted) Muster roll number") @Valid @RequestParam(value = "musterRollNumber", required = false) String musterRollNumber,@ApiParam(value = "Register Id") @Valid @RequestParam(value = "registerId", required = false) String registerId,@ApiParam(value = "Return registers with any overlap in the given time period") @Valid @RequestParam(value = "fromDate", required = false) BigDecimal fromDate,@ApiParam(value = "Return registers with any overlap in the given time period") @Valid @RequestParam(value = "toDate", required = false) BigDecimal toDate,@ApiParam(value = "Status of the muster roll. This can't be the only query param. It should be paired with some other search param.") @Valid @RequestParam(value = "status", required = false) String status,@ApiParam(value = "Workflow Status of the muster roll. This can't be the only query param. It should be paired with some other search param.") @Valid @RequestParam(value = "musterRollStatus", required = false) String musterRollStatus) {
            String accept = request.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                try {
                    //TO-DO Search API Implementation
                    // returning dummy response
                    return new ResponseEntity<MusterRollResponse>(objectMapper.readValue("{  \"responseInfo\" : {    \"debug\" : \"{}\",    \"signature\" : \"signature\",    \"resMsgId\" : \"resMsgId\",    \"additionalInfo\" : \"{}\",    \"msgId\" : \"msgId\",    \"information\" : \"{}\",    \"error\" : {      \"code\" : \"code\",      \"description\" : \"description\",      \"message\" : \"message\",      \"params\" : [ \"params\", \"params\" ]    },    \"ts\" : 0,    \"status\" : \"COMPLETED\"  },  \"musterRolls\" : [ {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  }, {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  } ]}", MusterRollResponse.class), HttpStatus.OK);
                } catch (IOException e) {
                    return new ResponseEntity<MusterRollResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
        }

        @RequestMapping(value="/_update", method = RequestMethod.POST)
        public ResponseEntity<MusterRollResponse> musterRollV1UpdatePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody MusterRollRequest musterRollRequest) {
            String accept = request.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                try {
                    return new ResponseEntity<MusterRollResponse>(objectMapper.readValue("{  \"responseInfo\" : {    \"debug\" : \"{}\",    \"signature\" : \"signature\",    \"resMsgId\" : \"resMsgId\",    \"additionalInfo\" : \"{}\",    \"msgId\" : \"msgId\",    \"information\" : \"{}\",    \"error\" : {      \"code\" : \"code\",      \"description\" : \"description\",      \"message\" : \"message\",      \"params\" : [ \"params\", \"params\" ]    },    \"ts\" : 0,    \"status\" : \"COMPLETED\"  },  \"musterRolls\" : [ {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  }, {    \"individualEntries\" : [ {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    }, {      \"attendanceEntries\" : [ {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      }, {        \"time\" : 1.46581298050294517310021547018550336360931396484375,        \"attendance\" : 0.0      } ],      \"totalAttendance\" : 6.02745618307040320615897144307382404804229736328125,      \"individualId\" : \"individualId\",      \"additionalDetails\" : \"{}\"    } ],    \"musterRollStatus\" : \"CREATED\",    \"registerId\" : \"registerId\",    \"endDate\" : 1.665497271E12,    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"musterRollNumber\" : \"musterRollNumber\",    \"startDate\" : 1.665497225E12,    \"status\" : \"ACTIVE\"  } ]}", MusterRollResponse.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    return new ResponseEntity<MusterRollResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
        }

    }
