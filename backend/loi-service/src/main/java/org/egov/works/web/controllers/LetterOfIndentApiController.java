package org.egov.works.web.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.LetterOfIndentService;
import org.egov.works.util.ResponseInfoCreator;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.egov.works.web.models.LetterOfIndentResponse;
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

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Controller
@RequestMapping("/v1")
public class LetterOfIndentApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ResponseInfoCreator responseInfoCreator;

    @Autowired
    private LetterOfIndentService letterOfIndentService;

    @Autowired
    public LetterOfIndentApiController(ObjectMapper objectMapper, HttpServletRequest request, ResponseInfoCreator responseInfoCreator) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.responseInfoCreator = responseInfoCreator;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1CreatePost(@Valid @RequestBody LetterOfIndentRequest letterOfIndentRequest) throws JsonProcessingException {
        letterOfIndentRequest = letterOfIndentService.createLOI(letterOfIndentRequest);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(letterOfIndentRequest.getRequestInfo(), true);
        LetterOfIndentResponse letterOfIndentResponse = LetterOfIndentResponse.builder().responseInfo(responseInfo).letterOfIndents(Collections.singletonList(letterOfIndentRequest.getLetterOfIndent())).build();
        return new ResponseEntity<>(letterOfIndentResponse, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)

    public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1SearchPost(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper, @ApiParam(value = "Request object to search LOI in the system", required = true) @Valid @ModelAttribute LOISearchCriteria criteria) {
        List<LetterOfIndent> loiList = letterOfIndentService.searchLOI(criteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        LetterOfIndentResponse loiResponse = LetterOfIndentResponse.builder().responseInfo(responseInfo).letterOfIndents(loiList).build();
        return new ResponseEntity<LetterOfIndentResponse>(loiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1UpdatePost(@ApiParam(value = "Request object to update estimate in the system", required = true) @Valid @RequestBody LetterOfIndentRequest request) {
        LetterOfIndentRequest enrichedReq = letterOfIndentService.updateLOI(request);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(request.getRequestInfo(), true);
        LetterOfIndentResponse letterOfIndentResponse = LetterOfIndentResponse.builder().responseInfo(responseInfo).letterOfIndents(Collections.singletonList(request.getLetterOfIndent())).build();
        return new ResponseEntity<>(letterOfIndentResponse, HttpStatus.OK);
    }

}
