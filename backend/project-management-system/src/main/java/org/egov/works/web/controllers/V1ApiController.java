package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.web.models.DeathRegistrationRequest;
import org.egov.works.web.models.DeathRegistrationResponse;
import org.egov.works.web.models.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-10-05T11:05:03.529+05:30")

@Controller
@RequestMapping("/death-services")
public class V1ApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/v1/registration/_create", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationResponse> v1RegistrationCreatePost(@ApiParam(value = "Details for the new Death Registration Application(s) + RequestInfo meta data.", required = true) @Valid @RequestBody DeathRegistrationRequest deathRegistrationRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DeathRegistrationResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"DeathRegistrationApplications\" : [ {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  }, {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  } ]}", DeathRegistrationResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/registration/_search", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationRequest> v1RegistrationSearchPost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "Parameter to carry Request metadata in the request body") @Valid @RequestBody RequestInfo requestInfo, @ApiParam(value = "Search based on status.") @Valid @RequestParam(value = "status", required = false) String status, @Size(max = 50) @ApiParam(value = "Unique identifier of death registration application") @Valid @RequestParam(value = "ids", required = false) List<Long> ids, @Size(min = 2, max = 64) @ApiParam(value = "Unique application number for the Death Registration Application") @Valid @RequestParam(value = "applicationNumber", required = false) String applicationNumber) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DeathRegistrationRequest>(objectMapper.readValue("{  \"RequestInfo\" : {    \"userInfo\" : {      \"password\" : \"password\",      \"additionalroles\" : [ {        \"roles\" : [ {          \"code\" : \"code\",          \"createdDate\" : \"2000-01-23\",          \"createdBy\" : 1,          \"lastModifiedDate\" : \"2000-01-23\",          \"lastModifiedBy\" : 5,          \"name\" : \"name\",          \"tenantId\" : \"tenantId\",          \"description\" : \"description\",          \"id\" : 6        }, {          \"code\" : \"code\",          \"createdDate\" : \"2000-01-23\",          \"createdBy\" : 1,          \"lastModifiedDate\" : \"2000-01-23\",          \"lastModifiedBy\" : 5,          \"name\" : \"name\",          \"tenantId\" : \"tenantId\",          \"description\" : \"description\",          \"id\" : 6        } ],        \"tenantId\" : \"tenantId\"      }, {        \"roles\" : [ {          \"code\" : \"code\",          \"createdDate\" : \"2000-01-23\",          \"createdBy\" : 1,          \"lastModifiedDate\" : \"2000-01-23\",          \"lastModifiedBy\" : 5,          \"name\" : \"name\",          \"tenantId\" : \"tenantId\",          \"description\" : \"description\",          \"id\" : 6        }, {          \"code\" : \"code\",          \"createdDate\" : \"2000-01-23\",          \"createdBy\" : 1,          \"lastModifiedDate\" : \"2000-01-23\",          \"lastModifiedBy\" : 5,          \"name\" : \"name\",          \"tenantId\" : \"tenantId\",          \"description\" : \"description\",          \"id\" : 6        } ],        \"tenantId\" : \"tenantId\"      } ],      \"tenantId\" : \"tenantId\",      \"idToken\" : \"idToken\",      \"mobile\" : \"mobile\",      \"userName\" : \"userName\",      \"uuid\" : \"uuid\",      \"email\" : \"email\",      \"primaryrole\" : [ {        \"code\" : \"code\",        \"createdDate\" : \"2000-01-23\",        \"createdBy\" : 1,        \"lastModifiedDate\" : \"2000-01-23\",        \"lastModifiedBy\" : 5,        \"name\" : \"name\",        \"tenantId\" : \"tenantId\",        \"description\" : \"description\",        \"id\" : 6      }, {        \"code\" : \"code\",        \"createdDate\" : \"2000-01-23\",        \"createdBy\" : 1,        \"lastModifiedDate\" : \"2000-01-23\",        \"lastModifiedBy\" : 5,        \"name\" : \"name\",        \"tenantId\" : \"tenantId\",        \"description\" : \"description\",        \"id\" : 6      } ]    },    \"ver\" : \"ver\",    \"requesterId\" : \"requesterId\",    \"authToken\" : \"authToken\",    \"action\" : \"action\",    \"msgId\" : \"msgId\",    \"correlationId\" : \"correlationId\",    \"apiId\" : \"apiId\",    \"did\" : \"did\",    \"key\" : \"key\",    \"ts\" : 0  },  \"DeathRegistrationApplications\" : [ {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  }, {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  } ]}", DeathRegistrationRequest.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<DeathRegistrationRequest>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DeathRegistrationRequest>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/registration/_update", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationResponse> v1RegistrationUpdatePost(@ApiParam(value = "Details for the new (s) + RequestInfo meta data.", required = true) @Valid @RequestBody DeathRegistrationRequest deathRegistrationRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<DeathRegistrationResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"DeathRegistrationApplications\" : [ {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  }, {    \"timeOfDeath\" : 5,    \"addressOfDeceased\" : {      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 2.3021358869347655,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"addressId\" : \"addressId\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantId\",      \"addressNumber\" : \"addressNumber\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"doorNo\" : \"doorNo\",      \"detail\" : \"detail\",      \"landmark\" : \"landmark\",      \"longitude\" : 7.061401241503109    },    \"applicationNumber\" : \"applicationNumber\",    \"auditDetails\" : {      \"lastModifiedTime\" : 3,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 9    },    \"tenantId\" : \"tenantId\",    \"deceasedLastName\" : \"deceasedLastName\",    \"id\" : \"id\",    \"deceasedFirstName\" : \"deceasedFirstName\",    \"placeOfDeath\" : \"placeOfDeath\",    \"applicant\" : \"\"  } ]}", DeathRegistrationResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
