package org.egov.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.OrgService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.OrgSearchRequest;
import org.egov.web.models.OrgServiceRequest;
import org.egov.web.models.OrgServiceResponse;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Controller
@RequestMapping("/v1")
public class OrgServicesApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private OrgService orgService;


    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<OrgServiceResponse> orgServicesV1CreatePOST(
            @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody OrgServiceRequest body) {

        OrgServiceRequest orgServiceRequest = orgService.createOrganisationWithWorkFlow(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        OrgServiceResponse orgServiceResponse = OrgServiceResponse.builder().responseInfo(responseInfo).organisations(orgServiceRequest.getOrganisations()).build();
        return new ResponseEntity<OrgServiceResponse>(orgServiceResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<OrgServiceResponse> orgServicesV1SearchPOST(
            @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody OrgSearchRequest body) {

        List<Organisation> organisations = Collections.emptyList();
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        Integer count = 0;
        OrgServiceResponse orgServiceResponse = OrgServiceResponse.builder().responseInfo(responseInfo).organisations(organisations).totalCount(count).build();
        return new ResponseEntity<OrgServiceResponse>(orgServiceResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<OrgServiceResponse> orgServicesV1UpdatePOST(
            @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
            @ApiParam(value = "") @Valid @RequestBody OrgServiceRequest body) {

        OrgServiceRequest orgServiceRequest = orgService.updateOrganisationWithWorkFlow(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        OrgServiceResponse orgServiceResponse = OrgServiceResponse.builder().responseInfo(responseInfo).organisations(orgServiceRequest.getOrganisations()).build();
        return new ResponseEntity<OrgServiceResponse>(orgServiceResponse, HttpStatus.OK);
    }

}
