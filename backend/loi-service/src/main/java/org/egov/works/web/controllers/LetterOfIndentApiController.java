package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.LetterOfIndentService;
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

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Controller
@RequestMapping("/loi-service/v1")
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
    public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1CreatePost(@ApiParam(value = "Request object to create estimate in the system", required = true) @Valid @RequestBody LetterOfIndentRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("")) {
            try {
                LetterOfIndentRequest letterOfIndentRequest = letterOfIndentService.createLOI(body);
                ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
                LetterOfIndentResponse letterOfIndentResponse = LetterOfIndentResponse.builder().responseInfo(responseInfo).letterOfIndents(Collections.singletonList(letterOfIndentRequest.getLetterOfIndent())).build();
                return new ResponseEntity<LetterOfIndentResponse>(objectMapper.readValue("", LetterOfIndentResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)

        public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1SearchPost(@Valid @RequestBody RequestInfo requestInfo, @ApiParam(value = "Request object to search LOI in the system", required = true) @Valid @ModelAttribute LOISearchCriteria criteria) {
        List<LetterOfIndent> loiList = letterOfIndentService.searchLOI(criteria);
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfo, true);
        LetterOfIndentResponse loiResponse = LetterOfIndentResponse.builder().responseInfo(responseInfo).letterOfIndents(loiList).build();
        return new ResponseEntity<LetterOfIndentResponse>(loiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1UpdatePost(@ApiParam(value = "Request object to update estimate in the system", required = true) @Valid @RequestBody LetterOfIndentRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("")) {
            try {
                return new ResponseEntity<LetterOfIndentResponse>(objectMapper.readValue("", LetterOfIndentResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
