package org.egov.works.measurement.web.controllers;


import org.egov.works.measurement.service.MeasurementService;
import org.egov.works.measurement.web.models.ErrorRes;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.egov.works.measurement.web.models.MeasurementResponse;
import org.egov.works.measurement.web.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-09-14T11:43:34.268+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("/measurement")
public class MeasurementApiController {

    @Autowired
    private MeasurementService measurementService; // Import MeasurementService if not imported already

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final MeasurementService service;

    public MeasurementApiController(ObjectMapper objectMapper, HttpServletRequest request, MeasurementService service) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
    }

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        String accept = request.getHeader("Accept");
        return service.createMeasurement(body);
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<MeasurementResponse>(objectMapper.readValue("{  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"measurements\" : [ {    \"measures\" : [ {      \"comments\" : \"comments\",      \"targetId\" : \"contractlineitemid\",      \"breadth\" : 200,      \"documents\" : [ {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      }, {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      } ],      \"length\" : 200,      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"measurementId\",      \"numItems\" : 1.4658129805029452,      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"currentValue\" : 5.962133916683182,      \"cumulativeValue\" : 5.637376656633329,      \"height\" : 200    }, {      \"comments\" : \"comments\",      \"targetId\" : \"contractlineitemid\",      \"breadth\" : 200,      \"documents\" : [ {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      }, {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      } ],      \"length\" : 200,      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"measurementId\",      \"numItems\" : 1.4658129805029452,      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"currentValue\" : 5.962133916683182,      \"cumulativeValue\" : 5.637376656633329,      \"height\" : 200    } ],    \"entryDate\" : 6.027456183070403,    \"measurementNumber\" : \"measurementNumber\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"physicalRefNumber\" : \"physicalRefNumber\",    \"isActive\" : true,    \"additionalDetails\" : { },    \"referenceId\" : \"Contract number\"  }, {    \"measures\" : [ {      \"comments\" : \"comments\",      \"targetId\" : \"contractlineitemid\",      \"breadth\" : 200,      \"documents\" : [ {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      }, {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      } ],      \"length\" : 200,      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"measurementId\",      \"numItems\" : 1.4658129805029452,      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"currentValue\" : 5.962133916683182,      \"cumulativeValue\" : 5.637376656633329,      \"height\" : 200    }, {      \"comments\" : \"comments\",      \"targetId\" : \"contractlineitemid\",      \"breadth\" : 200,      \"documents\" : [ {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      }, {        \"documentType\" : \"documentType\",        \"documentUid\" : \"documentUid\",        \"fileStore\" : \"fileStore\",        \"id\" : \"id\",        \"additionalDetails\" : { }      } ],      \"length\" : 200,      \"isActive\" : true,      \"additionalDetails\" : { },      \"referenceId\" : \"measurementId\",      \"numItems\" : 1.4658129805029452,      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"currentValue\" : 5.962133916683182,      \"cumulativeValue\" : 5.637376656633329,      \"height\" : 200    } ],    \"entryDate\" : 6.027456183070403,    \"measurementNumber\" : \"measurementNumber\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"physicalRefNumber\" : \"physicalRefNumber\",    \"isActive\" : true,    \"additionalDetails\" : { },    \"referenceId\" : \"Contract number\"  } ]}", MeasurementResponse.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                return new ResponseEntity<MeasurementResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }

//        return new ResponseEntity<MeasurementResponse>(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(value = "/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementRequest body) {
        return service.updateMeasurement(body);
    }

    @RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResponse> measurementsV1SearchPost(
            @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MeasurementSearchRequest body) {
        String accept = request.getHeader("Accept");
        MeasurementCriteria criteria = body.getCriteria();
        MeasurementResponse response = new MeasurementResponse();

        if (criteria != null) {
            List<String> referenceIds = criteria.getReferenceId();

            if (referenceIds != null && !referenceIds.isEmpty()) {
                for (String referenceId : referenceIds) {
                    // Use each referenceId as needed for your search
                    System.out.println(referenceId);
                    List<Measurement> measurements = measurementService.searchMeasurements(criteria);

                    // Create a MeasurementResponse using the search results

                    response.setMeasurements(measurements);

                    // Return a successful response for each referenceId
                    return new ResponseEntity<MeasurementResponse>(response, HttpStatus.OK);
                }
            }


        }
        return new ResponseEntity<MeasurementResponse>(response, HttpStatus.OK);
    }}
