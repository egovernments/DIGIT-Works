package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class RateAnalysisApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public RateAnalysisApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/rate-analysis/v1/_calculate", method = RequestMethod.POST)
    public ResponseEntity<RateAnalysisResponse> rateAnalysisV1CalculatePost(@Valid @RequestBody AnalysisRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<RateAnalysisResponse>(objectMapper.readValue("{  \"rateAnalysis\" : [ {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  }, {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RateAnalysisResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<RateAnalysisResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<RateAnalysisResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/rate-analysis/v1/_create", method = RequestMethod.POST)
    public ResponseEntity<RatesResponse> rateAnalysisV1CreatePost(@Valid @RequestBody AnalysisRequest body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<RatesResponse>(objectMapper.readValue("{  \"rates\" : {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"amountDetails\" : [ {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    }, {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"rate\" : 650,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RatesResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<RatesResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<RatesResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
}
