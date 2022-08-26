package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.service.LetterOfIndentService;
import org.egov.works.util.ResponseInfo;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.egov.works.web.models.LetterOfIndentResponse;
import org.egov.works.web.models.ResponseHeader;
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
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-08-04T15:05:28.525+05:30")

@Controller
    @RequestMapping("/eGovTrial/Letter-Of-Indent-Service/1.0.0")
    public class LetterOfIndentApiController{

        private final ObjectMapper objectMapper;

        private final HttpServletRequest request;

        @Autowired
        private  ResponseInfo responseInfo;

        @Autowired
        private  LetterOfIndentService letterOfIndentService;

        @Autowired
        public LetterOfIndentApiController(ObjectMapper objectMapper, HttpServletRequest request, ResponseInfo responseInfo) {
        this.objectMapper = objectMapper;
        this.request = request;
            this.responseInfo = responseInfo;
        }

                @RequestMapping(value="/letter-of-indent/v1/_create", method = RequestMethod.POST)
                public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1CreatePost(@ApiParam(value = "Request object to create estimate in the system" ,required=true )  @Valid @RequestBody LetterOfIndentRequest body) {
                        String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("")) {
                            try {
                                LetterOfIndentRequest letterOfIndentRequest = letterOfIndentService.createLOI(body);
                                ResponseHeader responseHeader = responseInfo.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
                                LetterOfIndentResponse letterOfIndentResponse = LetterOfIndentResponse.builder().responseInfo(responseHeader).letterOfIndents(Collections.singletonList(letterOfIndentRequest.getLetterOfIndent())).build();
                            return new ResponseEntity<LetterOfIndentResponse>(objectMapper.readValue("", LetterOfIndentResponse.class), HttpStatus.NOT_IMPLEMENTED);
                            } catch (IOException e) {
                            return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            }

                        return new ResponseEntity<LetterOfIndentResponse>(HttpStatus.NOT_IMPLEMENTED);
                }

                @RequestMapping(value="/letter-of-indent/v1/_search", method = RequestMethod.POST)
                public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1SearchPost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId,@ApiParam(value = "Search by list of UUID") @Valid @RequestParam(value = "ids", required = false) List<String> ids,@ApiParam(value = "Search by LetterOfIndent Number") @Valid @RequestParam(value = "letterOfIndentNumber", required = false) String letterOfIndentNumber,@ApiParam(value = "The unique identifier of the Work Package the custom formatting,to be used to fetch the LetterOfIndent") @Valid @RequestParam(value = "workPackageNumber", required = false) String workPackageNumber,@ApiParam(value = "Search by Letter Status i.e by status of the LetterOfIndent") @Valid @RequestParam(value = "letterStatus", required = false) String letterStatus,@ApiParam(value = "search by contractId") @Valid @RequestParam(value = "contractorid", required = false) String contractorid,@ApiParam(value = "search by agreement date between the from date and todate") @Valid @RequestParam(value = "fromAgreementDate", required = false) BigDecimal fromAgreementDate,@ApiParam(value = "search by agreement date between the from date and todate") @Valid @RequestParam(value = "toAgreementDate", required = false) BigDecimal toAgreementDate,@ApiParam(value = "sort the search results by fields", allowableValues = "defectLiabilityPeriod, contractPeriod, emdAmount, agreementDate, letterStatus, createdTime") @Valid @RequestParam(value = "sortBy", required = false) String sortBy,@ApiParam(value = "sorting order of the search resulsts", allowableValues = "asc, desc") @Valid @RequestParam(value = "sortOrder", required = false) String sortOrder,@ApiParam(value = "limit on the resulsts") @Valid @RequestParam(value = "limit", required = false) BigDecimal limit,@ApiParam(value = "offset index of the overall search resulsts") @Valid @RequestParam(value = "offset", required = false) BigDecimal offset) {
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

                @RequestMapping(value="/letter-of-indent/v1/_update", method = RequestMethod.POST)
                public ResponseEntity<LetterOfIndentResponse> letterOfIndentV1UpdatePost(@ApiParam(value = "Request object to update estimate in the system" ,required=true )  @Valid @RequestBody LetterOfIndentRequest body) {
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
