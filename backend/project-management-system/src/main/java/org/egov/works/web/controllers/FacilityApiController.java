package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.web.models.FacilityRequest;
import org.egov.works.web.models.FacilityResponse;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-08T16:20:57.141+05:30")

@Controller
@RequestMapping("/rushang.dhanesha/Project-Service/1.0.0")
public class FacilityApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public FacilityApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/facility/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<FacilityResponse> facilityV1CreatePost(@ApiParam(value = "Capture details of Facility.", required = true) @Valid @RequestBody FacilityRequest facility) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<FacilityResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"Facility\" : [ {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  }, {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  } ]}", FacilityResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<FacilityResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<FacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/facility/v1/_search", method = RequestMethod.POST)
    public ResponseEntity<FacilityResponse> facilityV1SearchPost(@ApiParam(value = "Details for existing facility.", required = true) @Valid @RequestBody FacilityRequest facility, @NotNull @Min(0) @Max(1000) @ApiParam(value = "Pagination - limit records in response", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Min(0) @ApiParam(value = "Pagination - offset from which records should be returned in response", required = true) @Valid @RequestParam(value = "offset", required = true) Integer offset, @NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "epoch of the time since when the changes on the object should be picked up. Search results from this parameter should include both newly created objects since this time as well as any modified objects since this time. This criterion is included to help polling clients to get the changes in system since a last time they synchronized with the platform. ") @Valid @RequestParam(value = "lastChangedSince", required = false) Long lastChangedSince, @ApiParam(value = "Used in search APIs to specify if (soft) deleted records should be included in search results.", defaultValue = "false") @Valid @RequestParam(value = "includeDeleted", required = false, defaultValue = "false") Boolean includeDeleted) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<FacilityResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"Facility\" : [ {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  }, {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  } ]}", FacilityResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<FacilityResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<FacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/facility/v1/_update", method = RequestMethod.POST)
    public ResponseEntity<FacilityResponse> facilityV1UpdatePost(@ApiParam(value = "Details for existing facility.", required = true) @Valid @RequestBody FacilityRequest facility) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<FacilityResponse>(objectMapper.readValue("{  \"ResponseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"Facility\" : [ {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  }, {    \"isPermanent\" : true,    \"address\" : {      \"locationAccuracy\" : 5962.133916683182,      \"pincode\" : \"pincode\",      \"city\" : \"city\",      \"latitude\" : 18.494211295267263,      \"locality\" : {        \"code\" : \"code\",        \"materializedPath\" : \"materializedPath\",        \"children\" : [ null, null ],        \"latitude\" : \"latitude\",        \"name\" : \"name\",        \"label\" : \"label\",        \"longitude\" : \"longitude\"      },      \"type\" : \"type\",      \"buildingName\" : \"buildingName\",      \"street\" : \"street\",      \"tenantId\" : \"tenantA\",      \"addressLine1\" : \"addressLine1\",      \"addressLine2\" : \"addressLine2\",      \"id\" : \"id\",      \"doorNo\" : \"doorNo\",      \"landmark\" : \"landmark\",      \"longitude\" : -127.23073270189397    },    \"additionalFields\" : {      \"schema\" : \"HOUSEHOLD\",      \"fields\" : [ {        \"value\" : \"180\",        \"key\" : \"height\"      }, {        \"value\" : \"180\",        \"key\" : \"height\"      } ],      \"version\" : 2    },    \"isDeleted\" : { },    \"rowVersion\" : { },    \"usage\" : \"STORAGE_WAREHOUSE\",    \"auditDetails\" : {      \"lastModifiedTime\" : 2,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 3    },    \"storageCapacity\" : 0,    \"id\" : \"id\"  } ]}", FacilityResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<FacilityResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<FacilityResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
