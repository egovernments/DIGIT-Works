package org.egov.works.mukta.adapter.web.controllers;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.util.ResponseInfoFactory;
import org.egov.works.mukta.adapter.web.models.Disbursement;
import org.egov.works.mukta.adapter.web.models.DisbursementResponse;
import org.egov.works.mukta.adapter.web.models.bill.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/mukta-adapter/v1")
public class MuktaAdapterController {
    private final PaymentInstructionService paymentInstructionService;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public MuktaAdapterController(PaymentInstructionService paymentInstructionService, ResponseInfoFactory responseInfoFactory) {
        this.paymentInstructionService = paymentInstructionService;
        this.responseInfoFactory = responseInfoFactory;
    }


    @RequestMapping(path = "/disburse/create", method = RequestMethod.POST)
    public ResponseEntity<Object> disburseCreate(@RequestBody @Valid PaymentRequest paymentRequest) {
        Disbursement disbursement = paymentInstructionService.processDisbursementCreate(paymentRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true);
        DisbursementResponse disbursementResponse = DisbursementResponse.builder().disbursement(disbursement).responseInfo(responseInfo).build();
        return ResponseEntity.ok(disbursementResponse);
    }
}
