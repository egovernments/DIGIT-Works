package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MeasurementService;
import org.egov.works.measurement.web.models.ErrorRes;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.egov.works.measurement.web.models.MeasurementServiceRequest;
import org.egov.works.measurement.web.models.MeasurementServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("")
public class MeasurementserviceApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MeasurementService service;

    @RequestMapping(value = "/measurementservice/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementServiceRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<MeasurementServiceResponse>(objectMapper.readValue("{  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"measurements\" : [ {    \"allOf\" : {      \"measures\" : [ {        \"comments\" : \"comments\",        \"targetId\" : \"contractlineitemid\",        \"breadth\" : 200,        \"documents\" : [ {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        }, {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        } ],        \"length\" : 200,        \"isActive\" : true,        \"additionalDetails\" : { },        \"referenceId\" : \"measurementId\",        \"numItems\" : 1.4658129805029452,        \"auditDetails\" : {          \"lastModifiedTime\" : 7,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 2        },        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"currentValue\" : 5.962133916683182,        \"cumulativeValue\" : 5.637376656633329,        \"height\" : 200      }, {        \"comments\" : \"comments\",        \"targetId\" : \"contractlineitemid\",        \"breadth\" : 200,        \"documents\" : [ {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        }, {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        } ],        \"length\" : 200,        \"isActive\" : true,        \"additionalDetails\" : { },        \"referenceId\" : \"measurementId\",        \"numItems\" : 1.4658129805029452,        \"auditDetails\" : {          \"lastModifiedTime\" : 7,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 2        },        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"currentValue\" : 5.962133916683182,        \"cumulativeValue\" : 5.637376656633329,        \"height\" : 200      } ],      \"entryDate\" : 6.027456183070403,      \"measurementNumber\" : \"measurementNumber\",      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"physicalRefNumber\" : \"physicalRefNumber\",      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"Contract number\"    },    \"workflow\" : {      \"action\" : \"action\",      \"assignees\" : [ \"assignees\", \"assignees\" ],      \"comment\" : \"comment\"    }  }, {    \"allOf\" : {      \"measures\" : [ {        \"comments\" : \"comments\",        \"targetId\" : \"contractlineitemid\",        \"breadth\" : 200,        \"documents\" : [ {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        }, {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        } ],        \"length\" : 200,        \"isActive\" : true,        \"additionalDetails\" : { },        \"referenceId\" : \"measurementId\",        \"numItems\" : 1.4658129805029452,        \"auditDetails\" : {          \"lastModifiedTime\" : 7,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 2        },        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"currentValue\" : 5.962133916683182,        \"cumulativeValue\" : 5.637376656633329,        \"height\" : 200      }, {        \"comments\" : \"comments\",        \"targetId\" : \"contractlineitemid\",        \"breadth\" : 200,        \"documents\" : [ {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        }, {          \"documentType\" : \"documentType\",          \"documentUid\" : \"documentUid\",          \"fileStore\" : \"fileStore\",          \"id\" : \"id\",          \"additionalDetails\" : { }        } ],        \"length\" : 200,        \"isActive\" : true,        \"additionalDetails\" : { },        \"referenceId\" : \"measurementId\",        \"numItems\" : 1.4658129805029452,        \"auditDetails\" : {          \"lastModifiedTime\" : 7,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 2        },        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"currentValue\" : 5.962133916683182,        \"cumulativeValue\" : 5.637376656633329,        \"height\" : 200      } ],      \"entryDate\" : 6.027456183070403,      \"measurementNumber\" : \"measurementNumber\",      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"physicalRefNumber\" : \"physicalRefNumber\",      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"Contract number\"    },    \"workflow\" : {      \"action\" : \"action\",      \"assignees\" : [ \"assignees\", \"assignees\" ],      \"comment\" : \"comment\"    }  } ]}", MeasurementServiceResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<MeasurementServiceResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<MeasurementServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/measurementservice/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        return new ResponseEntity<MeasurementServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    // FIXME: ADD valid annotaion and then fix validation
    @RequestMapping(value = "/measurementservice/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementServiceResponse> measurementserviceV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @RequestBody MeasurementServiceRequest body){
        return service.updateMeasurementService(body);
    }

}
