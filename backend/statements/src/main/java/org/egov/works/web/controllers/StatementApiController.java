package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.AnalysisStatementService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/v1/analysis")
public class StatementApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;


    private AnalysisStatementService analysisStatementService;


    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public StatementApiController(ResponseInfoFactory responseInfoFactory,AnalysisStatementService analysisStatementService){
        this.responseInfoFactory=responseInfoFactory;
        this.analysisStatementService=analysisStatementService;
    }


    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1AnalysisCreatePost( @Valid @RequestBody StatementCreateRequest body) {
        StatementPushRequest statementPushRequest = analysisStatementService.createAnalysisStatement(body,null);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(statementPushRequest.getRequestInfo(), true);
        StatementResponse statementResponse= StatementResponse.builder().responseInfo(responseInfo).statement(Arrays.asList(statementPushRequest.getStatement())).build();
        return new ResponseEntity<>(statementResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1AnalysisSearchPost( @Valid @RequestBody StatementSearchCriteria body) {
        body.getSearchCriteria().setStatementType(Statement.StatementTypeEnum.ANALYSIS);
        List<Statement> statementList= analysisStatementService.searchStatement(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        StatementResponse statementResponse= StatementResponse.builder().responseInfo(responseInfo).statement(statementList).build();
        return new ResponseEntity<StatementResponse>(statementResponse,HttpStatus.OK);
    }




}
