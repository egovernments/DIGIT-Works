package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.service.EstimateService;
import org.egov.util.ResponseHeaderCreator;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateResponse;
import org.egov.web.models.ResponseHeader;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-07-27T14:01:35.043+05:30")

@Controller
@RequestMapping("/estimate/v1")
public class EstimateApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private ResponseHeaderCreator responseHeaderCreator;

    private EstimateService estimateService;

    @Autowired
    public EstimateApiController(ObjectMapper objectMapper, HttpServletRequest request
            , ResponseHeaderCreator responseHeaderCreator
            , EstimateService estimateService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.estimateService = estimateService;
        this.responseHeaderCreator = responseHeaderCreator;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1CreatePost(@ApiParam(value = "Request object to create estimate in the system", required = true) @Valid @RequestBody EstimateRequest body) {
        EstimateRequest enrichedRequest = estimateService.createEstimate(body);
        ResponseHeader responseHeader = responseHeaderCreator.createResponseHeaderFromRequestHeader(body.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseHeader).estimates(Collections.singletonList(enrichedRequest.getEstimate())).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1SearchPost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "formatted unique identifier of the estimateDetail") @Valid @RequestParam(value = "estiamteDetailNumber", required = false) String estiamteDetailNumber, @ApiParam(value = "formmated unqiue identier of the sanction of Estimate Proposal") @Valid @RequestParam(value = "adminSanctionNumber", required = false) String adminSanctionNumber, @ApiParam(value = "Search by list of UUID") @Valid @RequestParam(value = "ids", required = false) List<String> ids, @ApiParam(value = "Search by Application Number i.e Estiamte Number") @Valid @RequestParam(value = "applicationNumber", required = false) String applicationNumber, @ApiParam(value = "Search by Application Status i.e by status of the estimate") @Valid @RequestParam(value = "applicationStatus", required = false) String applicationStatus, @ApiParam(value = "search by proposal date between the from date and todate") @Valid @RequestParam(value = "fromProposalDate", required = false) BigDecimal fromProposalDate, @ApiParam(value = "search by proposal date between the from date and todate") @Valid @RequestParam(value = "toProposalDate", required = false) BigDecimal toProposalDate, @ApiParam(value = "Search by EstimateType") @Valid @RequestParam(value = "estimateType", required = false) String estimateType, @ApiParam(value = "Search by department") @Valid @RequestParam(value = "department", required = false) String department, @ApiParam(value = "Search by type of work") @Valid @RequestParam(value = "tyepOfWork", required = false) String tyepOfWork, @ApiParam(value = "sort the search results by fields", allowableValues = "totalAmount, typeOfWork, department, proposalDate, applicationStatus, createdTime") @Valid @RequestParam(value = "sortBy", required = false) String sortBy, @ApiParam(value = "sorting order of the search resulsts", allowableValues = "asc, desc") @Valid @RequestParam(value = "sortOrder", required = false) String sortOrder, @ApiParam(value = "limit on the resulsts") @Valid @RequestParam(value = "limit", required = false) BigDecimal limit, @ApiParam(value = "offset index of the overall search resulsts") @Valid @RequestParam(value = "offset", required = false) BigDecimal offset) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("")) {
            try {
                return new ResponseEntity<EstimateResponse>(objectMapper.readValue("", EstimateResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<EstimateResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EstimateResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1UpdatePost(@ApiParam(value = "Request object to update estimate in the system", required = true) @Valid @RequestBody EstimateRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("")) {
            try {
                return new ResponseEntity<EstimateResponse>(objectMapper.readValue("", EstimateResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<EstimateResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EstimateResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
