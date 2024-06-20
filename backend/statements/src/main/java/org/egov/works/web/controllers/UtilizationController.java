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


        StatementResponse statementResponse = StatementResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true))
                .statement(Collections.singletonList(utilizationService.utilizationCreate(body))).build() ;
        return new ResponseEntity<StatementResponse>(statementResponse, HttpStatus.OK);
//        try {
//            return new ResponseEntity<StatementResponse>(objectMapper.readValue("{\n" +
//                    "  \"responseInfo\": {\n" +
//                    "    \"apiId\": \"string\",\n" +
//                    "    \"ver\": \"string\",\n" +
//                    "    \"ts\": 0,\n" +
//                    "    \"resMsgId\": \"string\",\n" +
//                    "    \"msgId\": \"string\",\n" +
//                    "    \"status\": \"SUCCESSFUL\"\n" +
//                    "  },\n" +
//                    "  \"statement\": [\n" +
//                    "    {\n" +
//                    "      \"id\": \"b17bdb3c-f0c4-4507-abaf-285d7afea03e\",\n" +
//                    "      \"tenantId\": \"pg.citya\",\n" +
//                    "      \"targetId\": \"654cca5a-9ff7-4508-bfe5-5fc49956e96a\",\n" +
//                    "      \"statementType\": \"ANALYSIS\",\n" +
//                    "      \"basicSorDetails\": [\n" +
//                    "        {\n" +
//                    "          \"id\": null,\n" +
//                    "          \"amount\": 157.98,\n" +
//                    "          \"type\": \"W\",\n" +
//                    "          \"quantity\": null\n" +
//                    "        },\n" +
//                    "        {\n" +
//                    "          \"id\": null,\n" +
//                    "          \"amount\": 280,\n" +
//                    "          \"type\": \"L\",\n" +
//                    "          \"quantity\": null\n" +
//                    "        }\n" +
//                    "      ],\n" +
//                    "      \"sorDetails\": [\n" +
//                    "        {\n" +
//                    "          \"id\": \"b22e7f9b-817f-4515-9392-d79d093b282d\",\n" +
//                    "          \"statementId\": \"b17bdb3c-f0c4-4507-abaf-285d7afea03e\",\n" +
//                    "          \"sorId\": \"SOR_000002\",\n" +
//                    "          \"basicSorDetails\": [\n" +
//                    "            {\n" +
//                    "              \"id\": \"fae0aa76-bf46-449a-b7fe-964a7fc595c4\",\n" +
//                    "              \"amount\": 157.98,\n" +
//                    "              \"type\": \"W\",\n" +
//                    "              \"quantity\": null\n" +
//                    "            },\n" +
//                    "            {\n" +
//                    "              \"id\": \"ac6ed4b2-3f93-4210-bce5-c6140b0edf24\",\n" +
//                    "              \"amount\": 280,\n" +
//                    "              \"type\": \"L\",\n" +
//                    "              \"quantity\": null\n" +
//                    "            }\n" +
//                    "          ],\n" +
//                    "          \"lineItems\": [\n" +
//                    "            {\n" +
//                    "              \"id\": \"4452034c-7878-424f-8cb6-e243cad53890\",\n" +
//                    "              \"sorId\": \"SOR_000009\",\n" +
//                    "              \"sorType\": \"W\",\n" +
//                    "              \"referenceId\": \"b22e7f9b-817f-4515-9392-d79d093b282d\",\n" +
//                    "              \"basicSorDetails\": [\n" +
//                    "                {\n" +
//                    "                  \"id\": \"0090b821-459f-4453-bbd3-e9ce45b67540\",\n" +
//                    "                  \"amount\": 157.2,\n" +
//                    "                  \"type\": \"W\",\n" +
//                    "                  \"quantity\": 0.4\n" +
//                    "                }\n" +
//                    "              ],\n" +
//                    "              \"additionalDetails\": {\n" +
//                    "                \"rateDetails\": {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"tenantId\": null,\n" +
//                    "                  \"sorCode\": null,\n" +
//                    "                  \"sorId\": \"SOR_000009\",\n" +
//                    "                  \"sorType\": null,\n" +
//                    "                  \"sorSubType\": null,\n" +
//                    "                  \"sorVariant\": null,\n" +
//                    "                  \"isBasicVariant\": null,\n" +
//                    "                  \"uom\": null,\n" +
//                    "                  \"quantity\": null,\n" +
//                    "                  \"description\": null,\n" +
//                    "                  \"rate\": 393,\n" +
//                    "                  \"validFrom\": \"1702857600000\",\n" +
//                    "                  \"validTo\": null,\n" +
//                    "                  \"amountDetails\": [\n" +
//                    "                    {\n" +
//                    "                      \"id\": null,\n" +
//                    "                      \"type\": \"fixed\",\n" +
//                    "                      \"heads\": \"LA.2\",\n" +
//                    "                      \"amount\": 200\n" +
//                    "                    }\n" +
//                    "                  ]\n" +
//                    "                },\n" +
//                    "                \"sorDetails\": {\n" +
//                    "                  \"id\": \"SOR_000009\",\n" +
//                    "                  \"uom\": \"CUM\",\n" +
//                    "                  \"sorType\": \"W\",\n" +
//                    "                  \"quantity\": 1,\n" +
//                    "                  \"sorSubType\": \"CC\",\n" +
//                    "                  \"sorVariant\": \"FF\",\n" +
//                    "                  \"description\": \"C:C: (1:2:4) using 12 mm. size H.G. stone chips including the cost of all materials labour T&P sundries etc complete. (FF)\"\n" +
//                    "                }\n" +
//                    "              }\n" +
//                    "            },\n" +
//                    "            {\n" +
//                    "              \"id\": \"fb1cb28f-7f72-4a01-9880-2e122ac9538a\",\n" +
//                    "              \"sorId\": \"SOR_000003\",\n" +
//                    "              \"sorType\": \"W\",\n" +
//                    "              \"referenceId\": \"b22e7f9b-817f-4515-9392-d79d093b282d\",\n" +
//                    "              \"basicSorDetails\": [\n" +
//                    "                {\n" +
//                    "                  \"id\": \"82bdac99-3407-4ca2-913d-d4a354a7a917\",\n" +
//                    "                  \"amount\": 0.78,\n" +
//                    "                  \"type\": \"W\",\n" +
//                    "                  \"quantity\": 0.0017\n" +
//                    "                }\n" +
//                    "              ],\n" +
//                    "              \"additionalDetails\": {\n" +
//                    "                \"rateDetails\": {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"tenantId\": null,\n" +
//                    "                  \"sorCode\": null,\n" +
//                    "                  \"sorId\": \"SOR_000003\",\n" +
//                    "                  \"sorType\": null,\n" +
//                    "                  \"sorSubType\": null,\n" +
//                    "                  \"sorVariant\": null,\n" +
//                    "                  \"isBasicVariant\": null,\n" +
//                    "                  \"uom\": null,\n" +
//                    "                  \"quantity\": null,\n" +
//                    "                  \"description\": null,\n" +
//                    "                  \"rate\": 456,\n" +
//                    "                  \"validFrom\": \"1702944000000\",\n" +
//                    "                  \"validTo\": null,\n" +
//                    "                  \"amountDetails\": [\n" +
//                    "                    {\n" +
//                    "                      \"id\": null,\n" +
//                    "                      \"type\": \"fixed\",\n" +
//                    "                      \"heads\": \"RA.5\",\n" +
//                    "                      \"amount\": 200\n" +
//                    "                    }\n" +
//                    "                  ]\n" +
//                    "                },\n" +
//                    "                \"sorDetails\": {\n" +
//                    "                  \"id\": \"SOR_000003\",\n" +
//                    "                  \"uom\": \"CUM\",\n" +
//                    "                  \"sorType\": \"W\",\n" +
//                    "                  \"quantity\": 120,\n" +
//                    "                  \"sorSubType\": \"CC\",\n" +
//                    "                  \"sorVariant\": \"GF\",\n" +
//                    "                  \"description\": \"P.C.C. Grade M25  Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum) [First Floor]\"\n" +
//                    "                }\n" +
//                    "              }\n" +
//                    "            },\n" +
//                    "            {\n" +
//                    "              \"id\": \"e99f5df4-eb85-4677-ab86-98781f72e0fe\",\n" +
//                    "              \"sorId\": \"SOR_0000011\",\n" +
//                    "              \"sorType\": \"L\",\n" +
//                    "              \"referenceId\": \"b22e7f9b-817f-4515-9392-d79d093b282d\",\n" +
//                    "              \"basicSorDetails\": [\n" +
//                    "                {\n" +
//                    "                  \"id\": \"71a91a81-8c70-4a57-b9e1-8474c54916d1\",\n" +
//                    "                  \"amount\": 280,\n" +
//                    "                  \"type\": \"L\",\n" +
//                    "                  \"quantity\": 0.4\n" +
//                    "                }\n" +
//                    "              ],\n" +
//                    "              \"additionalDetails\": {\n" +
//                    "                \"rateDetails\": {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"tenantId\": null,\n" +
//                    "                  \"sorCode\": null,\n" +
//                    "                  \"sorId\": \"SOR_0000011\",\n" +
//                    "                  \"sorType\": null,\n" +
//                    "                  \"sorSubType\": null,\n" +
//                    "                  \"sorVariant\": null,\n" +
//                    "                  \"isBasicVariant\": null,\n" +
//                    "                  \"uom\": null,\n" +
//                    "                  \"quantity\": null,\n" +
//                    "                  \"description\": null,\n" +
//                    "                  \"rate\": 700,\n" +
//                    "                  \"validFrom\": \"1712580560000\",\n" +
//                    "                  \"validTo\": \"null\",\n" +
//                    "                  \"amountDetails\": [\n" +
//                    "                    {\n" +
//                    "                      \"id\": \"123\",\n" +
//                    "                      \"type\": \"fixed\",\n" +
//                    "                      \"heads\": \"FH.123\",\n" +
//                    "                      \"amount\": 700\n" +
//                    "                    }\n" +
//                    "                  ]\n" +
//                    "                },\n" +
//                    "                \"sorDetails\": {\n" +
//                    "                  \"id\": \"SOR_0000011\",\n" +
//                    "                  \"uom\": \"NOs\",\n" +
//                    "                  \"sorType\": \"L\",\n" +
//                    "                  \"quantity\": 1,\n" +
//                    "                  \"sorSubType\": \"S\",\n" +
//                    "                  \"sorVariant\": \"NA\",\n" +
//                    "                  \"description\": \"SKILLED FEMALE MULIA.\"\n" +
//                    "                }\n" +
//                    "              }\n" +
//                    "            }\n" +
//                    "          ],\n" +
//                    "          \"tenantId\": \"pg.citya\",\n" +
//                    "          \"isActive\": true,\n" +
//                    "          \"additionalDetails\": {\n" +
//                    "            \"rateDetails\": {\n" +
//                    "              \"id\": \"2\",\n" +
//                    "              \"tenantId\": null,\n" +
//                    "              \"sorCode\": null,\n" +
//                    "              \"sorId\": \"SOR_000002\",\n" +
//                    "              \"sorType\": null,\n" +
//                    "              \"sorSubType\": null,\n" +
//                    "              \"sorVariant\": null,\n" +
//                    "              \"isBasicVariant\": null,\n" +
//                    "              \"uom\": null,\n" +
//                    "              \"quantity\": null,\n" +
//                    "              \"description\": \"Earth Work\",\n" +
//                    "              \"rate\": 439070.35,\n" +
//                    "              \"validFrom\": \"1698796800000\",\n" +
//                    "              \"validTo\": \"1923609600000\",\n" +
//                    "              \"amountDetails\": [\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"LC.6\",\n" +
//                    "                  \"amount\": 4347.23\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"RA.5\",\n" +
//                    "                  \"amount\": 15928\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"CA.4\",\n" +
//                    "                  \"amount\": 32001.01\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"MHA.3\",\n" +
//                    "                  \"amount\": 32001.01\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"LA.2\",\n" +
//                    "                  \"amount\": 7095.84\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                  \"id\": null,\n" +
//                    "                  \"type\": \"fixed\",\n" +
//                    "                  \"heads\": \"MA.1\",\n" +
//                    "                  \"amount\": 329936.45\n" +
//                    "                }\n" +
//                    "              ]\n" +
//                    "            },\n" +
//                    "            \"sorDetails\": {\n" +
//                    "              \"id\": \"SOR_000002\",\n" +
//                    "              \"uom\": \"CUM\",\n" +
//                    "              \"sorType\": \"W\",\n" +
//                    "              \"quantity\": 120,\n" +
//                    "              \"sorSubType\": \"CC\",\n" +
//                    "              \"sorVariant\": \"GF\",\n" +
//                    "              \"description\": \"P.C.C. Grade M25  Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum)\"\n" +
//                    "            }\n" +
//                    "          }\n" +
//                    "        }\n" +
//                    "      ],\n" +
//                    "      \"auditDetails\": {\n" +
//                    "        \"createdBy\": \"45614d29-9a50-4970-aba5-81b380745f48\",\n" +
//                    "        \"lastModifiedBy\": \"45614d29-9a50-4970-aba5-81b380745f48\",\n" +
//                    "        \"createdTime\": 1718713193272,\n" +
//                    "        \"lastModifiedTime\": 1718713193272\n" +
//                    "      },\n" +
//                    "      \"additionalDetails\": {\n" +
//                    "        \"estimateNumber\": \"ES/2024-25/000311\"\n" +
//                    "      }\n" +
//                    "    }\n" +
//                    "  ]\n" +
//                    "}", StatementResponse.class), HttpStatus.OK);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

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
