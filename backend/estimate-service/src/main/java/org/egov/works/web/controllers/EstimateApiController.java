package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.EstimateService;
import org.egov.works.util.ResponseInfoCreator;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateRequest;
import org.egov.works.web.models.EstimateResponse;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<EstimateResponse> estimateV1SearchPost(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper, @Valid @ModelAttribute EstimateSearchCriteria searchCriteria) {
        List<Estimate> estimateList = estimateService.searchEstimate(requestInfoWrapper, searchCriteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(estimateList).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<EstimateResponse> estimateV1UpdatePost(@ApiParam(value = "Request object to update estimate in the system", required = true) @Valid @RequestBody EstimateRequest body) {
        EstimateRequest enrichedRequest = estimateService.updateEstimate(body);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        EstimateResponse estimateResponse = EstimateResponse.builder().responseInfo(responseInfo).estimates(Collections.singletonList(enrichedRequest.getEstimate())).build();
        return new ResponseEntity<EstimateResponse>(estimateResponse, HttpStatus.OK);
    }

}
