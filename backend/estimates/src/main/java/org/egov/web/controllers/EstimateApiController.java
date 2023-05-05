package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.EstimateService;
import org.egov.util.ResponseInfoCreator;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Controller
@RequestMapping("/v1")
public class EstimateApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ResponseInfoCreator responseInfoCreator;

    @Autowired
    private EstimateService estimateService;

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> createEstimate(@ApiParam(value = "Request object to create estimate in the system", required = true) @Valid @RequestBody EstimateRequest body, @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType) {
        EstimateRequest enrichedRequest = estimateService.createEstimate(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(Collections.singletonList(enrichedRequest.getEstimate())).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> fetchEstimates(@Valid @ModelAttribute EstimateSearchCriteria searchCriteria, @Valid @RequestBody RequestInfoWrapper requestInfoWrapper) {
        List<Estimate> estimateList = estimateService.searchEstimate(requestInfoWrapper, searchCriteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        Integer count = estimateService.countAllEstimateApplications(searchCriteria);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(estimateList).totalCount(count).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> updateEstimate(@ApiParam(value = "Request object to update estimate in the system", required = true) @Valid @RequestBody EstimateRequest body, @ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType) {
        EstimateRequest enrichedRequest = estimateService.updateEstimate(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(Collections.singletonList(enrichedRequest.getEstimate())).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

}
