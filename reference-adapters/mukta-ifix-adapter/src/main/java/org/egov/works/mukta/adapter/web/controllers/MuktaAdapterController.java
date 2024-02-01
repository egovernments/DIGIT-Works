package org.egov.works.mukta.adapter.web.controllers;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.mukta.adapter.service.DisbursementService;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.util.ResponseInfoFactory;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mukta-adapter/v1")
public class MuktaAdapterController {
    private final PaymentInstructionService paymentInstructionService;
    private final ResponseInfoFactory responseInfoFactory;
    private final DisbursementService disbursementService;

    @Autowired
    public MuktaAdapterController(PaymentInstructionService paymentInstructionService, ResponseInfoFactory responseInfoFactory, DisbursementService disbursementService) {
        this.paymentInstructionService = paymentInstructionService;
        this.responseInfoFactory = responseInfoFactory;
        this.disbursementService = disbursementService;
    }


    @RequestMapping(path = "/disburse/create", method = RequestMethod.POST)
    public ResponseEntity<Object> disburseCreate(@RequestBody @Valid PaymentRequest paymentRequest) {
        Disbursement disbursement = paymentInstructionService.processDisbursementCreate(paymentRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true);
        DisbursementCreateResponse disbursementResponse = DisbursementCreateResponse.builder().disbursement(disbursement).responseInfo(responseInfo).build();
        return ResponseEntity.ok(disbursementResponse);
    }

    @RequestMapping(path = "/disburse/search", method = RequestMethod.POST)
    public ResponseEntity<Object> disburseSearch(@RequestBody @Valid DisbursementSearchRequest disbursementSearchRequest) {
        List<Disbursement> disbursements = paymentInstructionService.processDisbursementSearch(disbursementSearchRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(disbursementSearchRequest.getRequestInfo(), true);
        DisbursementSearchResponse disbursementResponse = DisbursementSearchResponse.builder().disbursements(disbursements).responseInfo(responseInfo).build();
        return ResponseEntity.ok(disbursementResponse);
    }

    @RequestMapping(path = "/on-disburse/_create", method = RequestMethod.POST)
    public ResponseEntity<Object> onDisburseCreate(@RequestBody @Valid DisbursementRequest disbursementRequest){
        DisbursementResponse disbursementResponse = disbursementService.processDisbursement(disbursementRequest);
        return ResponseEntity.ok(disbursementResponse);
    }
}
