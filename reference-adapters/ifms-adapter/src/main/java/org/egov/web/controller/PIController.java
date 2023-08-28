package org.egov.web.controller;

import org.egov.service.IfmsService;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.PaymentInstructionService;
import org.egov.service.VirtualAllotmentService;
import org.egov.utils.AuthenticationUtils;
import org.egov.utils.JitRequestUtils;
import org.egov.utils.ResponseInfoFactory;
import org.egov.utils.ResponseInfoFactory;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.web.models.jit.PIResponse;
import org.egov.web.models.jit.PISearchRequest;
import org.egov.web.models.jit.PIResponse;
import org.egov.web.models.jit.PaymentInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

@Controller
@RequestMapping("/pi/v1/")
public class PIController {

    @Autowired
    IfmsService ifmsService;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    JitRequestUtils jitRequestUtils;

    @Autowired
    PaymentInstructionService paymentInstruction;

    @Autowired
    VirtualAllotmentService virtualAllotmentService;

    @Autowired
    ResponseInfoFactory responseInfoFactory;

    @RequestMapping(path = "_create", method = RequestMethod.POST)
    public ResponseEntity<Object> request(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentInstruction createdPI = paymentInstruction.processPaymentRequest(paymentRequest);
            Map<String, Object> response = new HashMap<>();
            ResponseInfo requestInfo = responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true);
            response.put("ResponseInfo", requestInfo);
            response.put("paymentInstruction", createdPI);
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(path = "_search", method = RequestMethod.POST)
    public ResponseEntity<Object> request(@RequestBody PISearchRequest piSearchRequest) {
        try {
            List<PaymentInstruction> paymentInstructions = paymentInstruction.searchPi(piSearchRequest);
            PIResponse piResponse = PIResponse.builder()
                    .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(piSearchRequest.getRequestInfo(), true))
                    .paymentInstructions(paymentInstructions).build();
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(piResponse, HttpStatus.OK);
            return responseEntity;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
