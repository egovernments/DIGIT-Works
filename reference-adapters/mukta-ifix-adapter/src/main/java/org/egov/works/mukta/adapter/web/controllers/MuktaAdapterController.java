package org.egov.works.mukta.adapter.web.controllers;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.mukta.adapter.service.DisbursementService;
import org.egov.works.mukta.adapter.service.PaymentInstructionService;
import org.egov.works.mukta.adapter.util.ResponseInfoFactory;
import org.egov.works.mukta.adapter.web.models.*;
import org.egov.works.mukta.adapter.web.models.jit.PIResponse;
import org.egov.works.mukta.adapter.web.models.jit.PaymentInstruction;
import org.egov.works.services.common.models.expense.BillSearchRequest;
import org.egov.works.services.common.models.expense.Payment;
import org.egov.works.services.common.models.expense.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
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


    @RequestMapping(path = "/disburse/_create", method = RequestMethod.POST)
    public ResponseEntity<Object> disburseCreate(@RequestBody @Valid PaymentRequest paymentRequest) {
        Disbursement disbursement = paymentInstructionService.processDisbursementCreate(paymentRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(paymentRequest.getRequestInfo(), true);
        DisbursementCreateResponse disbursementResponse = DisbursementCreateResponse.builder().disbursement(disbursement).responseInfo(responseInfo).build();
        return ResponseEntity.ok(disbursementResponse);
    }

    @RequestMapping(path = "/disburse/_search", method = RequestMethod.POST)
    public ResponseEntity<Object> disburseSearch(@RequestBody @Valid DisbursementSearchRequest disbursementSearchRequest) {
        List<Disbursement> disbursements = paymentInstructionService.processDisbursementSearch(disbursementSearchRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(disbursementSearchRequest.getRequestInfo(), true);
        DisbursementSearchResponse disbursementResponse = DisbursementSearchResponse.builder().disbursements(disbursements).responseInfo(responseInfo).build();
        return ResponseEntity.ok(disbursementResponse);
    }

    @RequestMapping(path = "/on-disburse/_create", method = RequestMethod.POST)
    public ResponseEntity<Object> onDisburseCreate(@RequestBody @Valid DisbursementRequest disbursementRequest){
        DisbursementResponse disbursementResponse = disbursementService.processOnDisbursement(disbursementRequest);
        return ResponseEntity.ok(disbursementResponse);
    }
    @RequestMapping(path = "/on-disburse/_update", method = RequestMethod.POST)
    public ResponseEntity<Object> onDisburseUpdate(@RequestBody @Valid DisbursementRequest disbursementRequest){
        DisbursementResponse disbursementResponse = disbursementService.processOnDisbursement(disbursementRequest);
        return ResponseEntity.ok(disbursementResponse);
    }

    @RequestMapping(path = "/pi/_search", method = RequestMethod.POST)
    public ResponseEntity<Object> piSearch(@RequestBody @Valid DisbursementSearchRequest disbursementSearchRequest) {
        List<PaymentInstruction> paymentInstructions = paymentInstructionService.processPaymentInstructionSearch(disbursementSearchRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(disbursementSearchRequest.getRequestInfo(), true);
        PIResponse paymentInstructionResponse = PIResponse.builder().paymentInstructions(paymentInstructions).responseInfo(responseInfo).build();
        return ResponseEntity.ok(paymentInstructionResponse);
    }

    // TODO: Remove this after dev
    @RequestMapping(path = "/manual/payment-create", method = RequestMethod.POST)
    public ResponseEntity<Object> paymentCreate(@RequestBody @Valid BillSearchRequest billSearchRequest){
        List<Payment> payments = paymentInstructionService.processCreatePayment(billSearchRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(billSearchRequest.getRequestInfo(), true);
        PaymentResponse paymentResponse = PaymentResponse.builder().responseInfo(responseInfo).payments(payments).build();
        return ResponseEntity.ok(paymentResponse);
    }
}
