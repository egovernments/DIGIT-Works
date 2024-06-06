package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.AnalysisStatementService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.StatementCreateRequest;
import org.egov.works.web.models.StatementPushRequest;
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


@Controller
@RequestMapping("/v1/")
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


    @RequestMapping(value = "analysis/_create", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1AnalysisCreatePost( @Valid @RequestBody StatementCreateRequest body) {
        StatementPushRequest statementPushRequest = analysisStatementService.createAnalysisStatement(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(statementPushRequest.getRequestInfo(), true);
        StatementResponse statementResponse= StatementResponse.builder().responseInfo(responseInfo).statement(statementPushRequest.getStatement()).build();
        return new ResponseEntity<>(statementResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "analysis/_search", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1AnalysisSearchPost( @Valid @RequestBody StatementSearchCriteria body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StatementResponse>(objectMapper.readValue("{  \"statement\" : [ {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  }, {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", StatementResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StatementResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StatementResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/utilization/create", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1UtilizationCreatePost( @Valid @RequestBody StatementCreateRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StatementResponse>(objectMapper.readValue("{  \"statement\" : [ {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  }, {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", StatementResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StatementResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StatementResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/utilization/search", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1UtilizationSearchPost( @Valid @RequestBody StatementSearchCriteria body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<StatementResponse>(objectMapper.readValue("{  \"statement\" : [ {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  }, {    \"targetId\" : \"461c51eb-e970-4e01-a99a-70136c47a934\",    \"statementType\" : \"ANALYSIS\",    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"sorDetails\" : [ {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    }, {      \"lineItems\" : [ {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      }, {        \"sorType\" : \"Labour\",        \"sorId\" : \"sorId\",        \"id\" : \"id\",        \"amountDetails\" : [ null, null ]      } ],      \"statementId\" : \"statementId\",      \"sorId\" : \"sorId\",      \"id\" : \"id\",      \"amountDetails\" : [ null, null ]    } ],    \"amountDetails\" : [ {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    }, {      \"amount\" : 1534,      \"quantity\" : 6.027456183070403,      \"rate\" : 1.4658129805029452,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"type\" : \"Labour\"    } ]  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", StatementResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<StatementResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<StatementResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
