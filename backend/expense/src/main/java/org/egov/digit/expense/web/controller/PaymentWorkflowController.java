package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillResponse;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * REST endpoints for PAYMENTS workflow phase transitions.
 *
 * <p>Each endpoint loads the fresh bill from DB (to get current status + details),
 * delegates to {@link PaymentWorkflowService}, then returns the updated bill.
 */
@Controller
@RequestMapping("/v1/payments/")
@Slf4j
public class PaymentWorkflowController {

    private final PaymentWorkflowService paymentWorkflowService;
    private final BillRepository billRepository;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public PaymentWorkflowController(PaymentWorkflowService paymentWorkflowService,
                                      BillRepository billRepository,
                                      ResponseInfoFactory responseInfoFactory) {
        this.paymentWorkflowService = paymentWorkflowService;
        this.billRepository = billRepository;
        this.responseInfoFactory = responseInfoFactory;
    }

    /**
     * POST /v1/payments/bill/_verify
     * Actor: PAYMENT_EDITOR
     * Pre-condition: Bill in PENDING_VERIFICATION or PARTIALLY_VERIFIED
     */
    @PostMapping(value = "bill/_verify")
    public ResponseEntity<BillResponse> verifyBill(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.verifyBill(billRequest);
        return buildResponse(billRequest, bill);
    }

    /**
     * POST /v1/payments/bill/_ignoreErrors
     * Actor: PAYMENT_EDITOR
     * Pre-condition: Bill in PARTIALLY_VERIFIED; no detail in PENDING_VERIFICATION
     */
    @PostMapping(value = "bill/_ignoreErrors")
    public ResponseEntity<BillResponse> ignoreErrors(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.ignoreErrorsAndVerify(billRequest);
        return buildResponse(billRequest, bill);
    }

    /**
     * POST /v1/payments/bill/_sendForReview
     * Actor: PAYMENT_EDITOR
     * Pre-condition: Bill in FULLY_VERIFIED
     */
    @PostMapping(value = "bill/_sendForReview")
    public ResponseEntity<BillResponse> sendForReview(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.sendForReview(billRequest);
        return buildResponse(billRequest, bill);
    }

    /**
     * POST /v1/payments/bill/_sendForApproval
     * Actor: PAYMENT_REVIEWER
     * Pre-condition: Bill in UNDER_REVIEW
     */
    @PostMapping(value = "bill/_sendForApproval")
    public ResponseEntity<BillResponse> sendForApproval(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.sendForApproval(billRequest);
        return buildResponse(billRequest, bill);
    }

    /**
     * POST /v1/payments/bill/_initiatePayment
     * Actor: PAYMENT_APPROVER
     * Pre-condition: Bill in REVIEWED
     */
    @PostMapping(value = "bill/_initiatePayment")
    public ResponseEntity<BillResponse> initiatePayment(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.initiatePayment(billRequest);
        return buildResponse(billRequest, bill);
    }

    /**
     * POST /v1/payments/bill/_retryPayment
     * Actor: PAYMENT_APPROVER
     * Pre-condition: Bill in PAYMENT_FAILED or PARTIALLY_PAID
     */
    @PostMapping(value = "bill/_retryPayment")
    public ResponseEntity<BillResponse> retryPayment(@Valid @RequestBody BillRequest billRequest) {
        Bill bill = fetchBill(billRequest);
        billRequest.setBill(bill);
        paymentWorkflowService.retryPayment(billRequest);
        return buildResponse(billRequest, bill);
    }

    // ─────────────────────────────────────────────────────────────────────────

    /** Fetches the latest bill (with details) from DB using the bill ID in the request. */
    private Bill fetchBill(BillRequest billRequest) {
        String billId = billRequest.getBill() != null ? billRequest.getBill().getId() : null;
        if (billId == null) {
            throw new CustomException("BILL_ID_REQUIRED", "bill.id is required");
        }
        String tenantId = billRequest.getBill().getTenantId();
        if (tenantId == null) {
            throw new CustomException("TENANT_ID_REQUIRED", "bill.tenantId is required");
        }

        Bill bill = paymentWorkflowService.fetchBillWithDetails(billId, tenantId,
                billRequest.getRequestInfo());
        if (bill == null) {
            throw new CustomException("BILL_NOT_FOUND", "Bill not found for id: " + billId);
        }
        return bill;
    }

    private ResponseEntity<BillResponse> buildResponse(BillRequest billRequest, Bill bill) {
        BillResponse response = BillResponse.builder()
                .bills(Collections.singletonList(bill))
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(
                        billRequest.getRequestInfo(), true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
