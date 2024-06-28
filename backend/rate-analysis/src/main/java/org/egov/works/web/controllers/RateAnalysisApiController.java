package org.egov.works.web.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.works.service.RateAnalysisService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.AnalysisRequest;
import org.egov.works.web.models.RateAnalysisResponse;
import org.egov.works.web.models.Rates;
import org.egov.works.web.models.RatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class RateAnalysisApiController {

    private final HttpServletRequest request;

    private final RateAnalysisService rateAnalysisService;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public RateAnalysisApiController(HttpServletRequest request, RateAnalysisService rateAnalysisService, ResponseInfoFactory responseInfoFactory) {
        this.request = request;
        this.rateAnalysisService = rateAnalysisService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @RequestMapping(value = "/v1/_calculate", method = RequestMethod.POST)
    public ResponseEntity<RateAnalysisResponse> rateAnalysisV1CalculatePost(@Valid @RequestBody AnalysisRequest analysisRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<RateAnalysisResponse>(rateAnalysisService.calculateRate(analysisRequest), HttpStatus.OK) ;
//                return new ResponseEntity<RateAnalysisResponse>(objectMapper.readValue("{  \"rateAnalysis\" : [ {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  }, {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RateAnalysisResponse.class), HttpStatus.OK);
        }

        return new ResponseEntity<RateAnalysisResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<RatesResponse> rateAnalysisV1CreatePost(@Valid @RequestBody AnalysisRequest analysisRequest) {
        RatesResponse ratesResponse = RatesResponse.builder()
                .rates(rateAnalysisService.createRateAnalysis(analysisRequest))
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(analysisRequest.getRequestInfo(), true)).build();
            return new ResponseEntity<RatesResponse>(ratesResponse, HttpStatus.OK);
//                return new ResponseEntity<RatesResponse>(objectMapper.readValue("{  \"rates\" : {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"amountDetails\" : [ {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    }, {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"rate\" : 650,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RatesResponse.class), HttpStatus.NOT_IMPLEMENTED);



    }
}
