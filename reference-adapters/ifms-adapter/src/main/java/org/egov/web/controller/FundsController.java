package org.egov.web.controller;

import org.egov.service.VirtualAllotmentService;
import org.egov.utils.ResponseInfoFactory;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/funds/v1/")
public class FundsController {

    @Autowired
    ResponseInfoFactory responseInfoFactory;
    @Autowired
    VirtualAllotmentService virtualAllotmentService;


    @RequestMapping(path = "_search", method = RequestMethod.POST)
    public ResponseEntity<Object> request(@RequestBody FundsSearchRequest fundsSearchRequest) {
        try {
            List<SanctionDetail> funds = virtualAllotmentService.searchSanctions(fundsSearchRequest);
            FundsSearchResponse fundsSearchResponse = FundsSearchResponse.builder()
                    .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(fundsSearchRequest.getRequestInfo(), true))
                    .funds(funds).build();
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(fundsSearchResponse, HttpStatus.OK);
            return responseEntity;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
