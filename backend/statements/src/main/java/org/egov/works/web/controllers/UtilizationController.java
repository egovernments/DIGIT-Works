package org.egov.works.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.AnalysisStatementService;
import org.egov.works.service.UtilizationService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.Statement;
import org.egov.works.web.models.StatementCreateRequest;
import org.egov.works.web.models.StatementResponse;
import org.egov.works.web.models.StatementSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/v1/utilization")
public class UtilizationController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UtilizationService utilizationService;
    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private AnalysisStatementService analysisStatementService;

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1UtilizationCreatePost(@Valid @RequestBody StatementCreateRequest body) {


        StatementResponse statementResponse = StatementResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true))
                .statement(Collections.singletonList(utilizationService.utilizationCreate(body, null))).build() ;
        return new ResponseEntity<StatementResponse>(statementResponse, HttpStatus.OK);

    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1UtilizationSearchPost( @Valid @RequestBody StatementSearchCriteria body) {
        body.getSearchCriteria().setStatementType(Statement.StatementTypeEnum.UTILIZATION);
        List<Statement> statementList= analysisStatementService.searchStatement(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        StatementResponse statementResponse= StatementResponse.builder().responseInfo(responseInfo).statement(statementList).build();

        return new ResponseEntity<StatementResponse>(statementResponse,HttpStatus.OK);
    }

}
