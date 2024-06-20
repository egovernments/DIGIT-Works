package org.egov.works.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.works.service.UtilizationService;
import org.egov.works.util.ResponseInfoFactory;
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

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<StatementResponse> statementV1UtilizationCreatePost(@Valid @RequestBody StatementCreateRequest body) {


//        StatementResponse statementResponse = StatementResponse.builder()
//                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true))
//                .statement(Collections.singletonList(utilizationService.utilizationCreate(body))).build() ;
//        return new ResponseEntity<StatementResponse>(statementResponse, HttpStatus.OK);
        try {
            return new ResponseEntity<StatementResponse>(objectMapper.readValue("{\n" +
                    "  \"responseInfo\": {\n" +
                    "    \"apiId\": \"string\",\n" +
                    "    \"ver\": \"string\",\n" +
                    "    \"ts\": 0,\n" +
                    "    \"resMsgId\": \"string\",\n" +
                    "    \"msgId\": \"string\",\n" +
                    "    \"status\": \"SUCCESSFUL\"\n" +
                    "  },\n" +
                    "  \"statement\": [\n" +
                    "    {\n" +
                    "      \"id\": \"251c51eb-e970-4e01-a99a-70136c47a934\",\n" +
                    "      \"tenantId\": \"pb.jalandhar OR dwss\",\n" +
                    "      \"targetId\": \"461c51eb-e970-4e01-a99a-70136c47a934\",\n" +
                    "      \"additionalDetails\": {},\n" +
                    "      \"basicSorDetails\": [\n" +
                    "        {\n" +
                    "          \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                    "          \"amount\": 1534,\n" +
                    "          \"type\": \"Labour\",\n" +
                    "          \"quantity\": 0,\n" +
                    "          \"rate\": 0\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"sorDetails\": [\n" +
                    "        {\n" +
                    "          \"id\": \"string\",\n" +
                    "          \"statementId\": \"string\",\n" +
                    "          \"sorId\": \"SOR-W1\",\n" +
                    "          \"additionalDetails\": {\n" +
                    "            \"sorDetails\": {\n" +
                    "              \"id\": \"SOR_000692\",\n" +
                    "              \"uom\": \"SQM\",\n" +
                    "              \"sorType\": \"W\",\n" +
                    "              \"quantity\": 1.0,\n" +
                    "              \"sorSubType\": \"PA\",\n" +
                    "              \"sorVariant\": \"TF\",\n" +
                    "              \"description\": \"Finishing walls with water proffing cement paint two coats with cost of cement paint\"\n" +
                    "            },\n" +
                    "            \"rateDetails\": {\n" +
                    "              \"rate\": 654,\n" +
                    "              \"sorId\": \"SOR_000692\",\n" +
                    "              \"validTo\": \"1709251200000\",\n" +
                    "              \"validFrom\": \"1709251200000\",\n" +
                    "              \"amountDetails\": [\n" +
                    "                  {\n" +
                    "                      \"type\": \"fixed\",\n" +
                    "                      \"heads\": \"CA.9\",\n" +
                    "                      \"amount\": 98765\n" +
                    "                  }\n" +
                    "              ]\n" +
                    "            }\n" +
                    "          },\n" +
                    "          \"basicSorDetails\": [\n" +
                    "            {\n" +
                    "              \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                    "              \"amount\": 1534,\n" +
                    "              \"type\": \"Labour\",\n" +
                    "              \"quantity\": 0,\n" +
                    "              \"rate\": 0\n" +
                    "            }\n" +
                    "          ],\n" +
                    "          \"lineItems\": [\n" +
                    "            {\n" +
                    "              \"id\": \"string\",\n" +
                    "              \"sorId\": \"SOR-1\",\n" +
                    "              \"sorType\": \"Labour\",\n" +
                    "              \"additionalDetails\": {\n" +
                    "                \"sorDetails\": {\n" +
                    "                  \"id\": \"SOR_000692\",\n" +
                    "                  \"uom\": \"SQM\",\n" +
                    "                  \"sorType\": \"W\",\n" +
                    "                  \"quantity\": 1.0,\n" +
                    "                  \"sorSubType\": \"PA\",\n" +
                    "                  \"sorVariant\": \"TF\",\n" +
                    "                  \"description\": \"Finishing walls with water proffing cement paint two coats with cost of cement paint\"\n" +
                    "                },\n" +
                    "                \"rateDetails\": {\n" +
                    "                  \"rate\": 654,\n" +
                    "                  \"sorId\": \"SOR_000692\",\n" +
                    "                  \"validTo\": \"1709251200000\",\n" +
                    "                  \"validFrom\": \"1709251200000\",\n" +
                    "                  \"amountDetails\": [\n" +
                    "                      {\n" +
                    "                          \"type\": \"fixed\",\n" +
                    "                          \"heads\": \"CA.9\",\n" +
                    "                          \"amount\": 98765\n" +
                    "                      }\n" +
                    "                  ]\n" +
                    "                }\n" +
                    "              },\n" +
                    "              \"basicSorDetails\": [\n" +
                    "                {\n" +
                    "                  \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                    "                  \"amount\": 1534,\n" +
                    "                  \"type\": \"Labour\",\n" +
                    "                  \"quantity\": 0,\n" +
                    "                  \"rate\": 0\n" +
                    "                }\n" +
                    "              ]\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}", StatementResponse.class), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
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
