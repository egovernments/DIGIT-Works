package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.EstimateService;
import org.egov.works.util.ResponseInfoCreator;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private ResponseInfoCreator responseInfoCreator;

    private EstimateService estimateService;

    @Autowired
    public EstimateApiController(ObjectMapper objectMapper, HttpServletRequest request
            , ResponseInfoCreator responseInfoCreator
            , EstimateService estimateService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.estimateService = estimateService;
        this.responseInfoCreator = responseInfoCreator;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1CreatePost(@ApiParam(value = "Request object to create estimate in the system", required = true) @Valid @RequestBody EstimateRequest body) {
        EstimateRequest enrichedRequest = estimateService.createEstimate(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(Collections.singletonList(enrichedRequest.getEstimate())).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1SearchPost(@Valid @RequestBody RequestInfo requestInfo, @Valid @ModelAttribute EstimateSearchCriteria searchCriteria) {
        List<Estimate> estimateList = estimateService.searchEstimate(requestInfo,searchCriteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfo, true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(estimateList).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
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
