package org.egov.digit.expense.service;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_IGNORE_ERRORS;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT_INITIATION;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_APPROVAL;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillAggregationServiceTest {

    @Mock
    private PaymentWorkflowService pws;
    @Mock
    private WorkflowUtil workflowUtil;

    @InjectMocks
    private BillAggregationService svc;

    private RequestInfo ri;

    @BeforeEach
    public void setUp() {
        ri = buildRequestInfo("PAYMENT_EDITOR");
    }

    // ── VERIFICATION ──────────────────────────────────────────────────────────

    @Test
    public void verification_allVerified_fullyVerify() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED, 3);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.FULLY_VERIFY), any(RequestInfo.class));
        verify(pws).pushBillUpdate(eq(bill), any(RequestInfo.class));
    }

    @Test
    public void verification_mixedVerifiedAndFailed_partiallyVerify() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFIED, Status.VERIFICATION_FAILED);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.PARTIALLY_VERIFY), any(RequestInfo.class));
    }

    @Test
    public void verification_allFailed_failedAction() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFICATION_FAILED, Status.VERIFICATION_FAILED);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.FAILED), any(RequestInfo.class));
    }

    @Test
    public void verification_stillPending_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFIED, Status.PENDING_VERIFICATION);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void verification_stillInProgress_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFIED, Status.VERIFICATION_IN_PROGRESS);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── IGNORE_ERRORS ─────────────────────────────────────────────────────────

    @Test
    public void ignoreErrors_allVerified_complete() {
        Bill bill = buildBill(Status.IGNORING_ERRORS_IN_PROGRESS, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_IGNORE_ERRORS, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    @Test
    public void ignoreErrors_hasVerificationFailed_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.IGNORING_ERRORS_IN_PROGRESS,
                Status.VERIFIED, Status.VERIFICATION_FAILED);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_IGNORE_ERRORS, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── SEND_FOR_REVIEW ──────────────────────────────────────────────────────

    @Test
    public void sendForReview_allUnderReview_complete() {
        Bill bill = buildBill(Status.SENDING_FOR_REVIEW, Status.UNDER_REVIEW, 3);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_SEND_FOR_REVIEW, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    @Test
    public void sendForReview_stillVerified_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.SENDING_FOR_REVIEW,
                Status.UNDER_REVIEW, Status.VERIFIED);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_SEND_FOR_REVIEW, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── SEND_FOR_APPROVAL ────────────────────────────────────────────────────

    @Test
    public void sendForApproval_allReviewed_complete() {
        Bill bill = buildBill(Status.REVIEW_IN_PROGRESS, Status.REVIEWED, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_SEND_FOR_APPROVAL, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    @Test
    public void sendForApproval_stillUnderReview_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.REVIEW_IN_PROGRESS,
                Status.REVIEWED, Status.UNDER_REVIEW);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_SEND_FOR_APPROVAL, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── PAYMENT_INITIATION ───────────────────────────────────────────────────

    @Test
    public void paymentInitiation_allPaymentInProgress_noBillAction() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT_INITIATION, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── PAYMENT ──────────────────────────────────────────────────────────────

    @Test
    public void payment_allPaid_fullyPay() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAID, 3);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.FULLY_PAY), any(RequestInfo.class));
    }

    @Test
    public void payment_allFullyPaid_fullyPay() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.FULLY_PAID, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.FULLY_PAY), any(RequestInfo.class));
    }

    @Test
    public void payment_mixedPaidAndFailed_partiallyPay() {
        Bill bill = buildBillWithMixedDetails(Status.PAYMENT_IN_PROGRESS,
                Status.PAID, Status.PAYMENT_FAILED);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.PARTIALLY_PAY), any(RequestInfo.class));
    }

    @Test
    public void payment_allFailed_failedAction() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_FAILED, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT, ri);
        verify(pws).transitionBill(eq(bill), eq(Actions.FAILED), any(RequestInfo.class));
    }

    @Test
    public void payment_stillInProgress_noTransition() {
        Bill bill = buildBillWithMixedDetails(Status.PAYMENT_IN_PROGRESS,
                Status.PAID, Status.PAYMENT_IN_PROGRESS);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_PAYMENT, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── Error handling ────────────────────────────────────────────────────────

    @Test
    public void invalidAction_treatedAsIdempotent_noPushBillUpdate() {
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        doThrow(new RuntimeException("INVALID ACTION - No valid action"))
                .when(pws).transitionBill(any(), any(), any(RequestInfo.class));
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws, never()).pushBillUpdate(any(), any());
    }

    @Test
    public void nonRetryableError_doesNotRethrow() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        doThrow(new RuntimeException("Network timeout"))
                .when(pws).transitionBill(any(), any(), any(RequestInfo.class));
        when(workflowUtil.isRetryableWfError(any())).thenReturn(false);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        // no exception thrown
    }

    @Test
    public void billNotFound_noTransition() {
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(null);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void billHasNoDetails_noTransition() {
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, Collections.emptyList());
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, POLL_PHASE_VERIFICATION, ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void unknownPhase_noTransition() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 2);
        when(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, ri)).thenReturn(bill);
        svc.checkAndAggregateBill(BILL_ID, TENANT_ID, "UNKNOWN_PHASE", ri);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }
}
