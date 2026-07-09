package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.service.WorkflowEmailNotificationService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_IGNORE_ERRORS;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_APPROVAL;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillStatusPollHandlerTest {

    @Mock
    private PaymentWorkflowService pws;
    @Mock
    private WorkflowUtil workflowUtil;
    @Mock
    private WorkflowEmailNotificationService workflowEmailNotificationService;

    private BillStatusPollHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new BillStatusPollHandler(pws, workflowUtil, new ObjectMapper(), workflowEmailNotificationService);
    }

    // ── VERIFICATION ──────────────────────────────────────────────────────────

    @Test
    public void verification_pendingAndInProgress_dispatchesRetryJobs_returnsRetry() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.PENDING_VERIFICATION, Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        SchedulerJob job = buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION);

        SchedulerJobResult result = handler.handle(job);

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(pws, times(2)).pushVerificationVerifyTask(eq(bill), any(BillDetail.class), any());
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void verification_allVerified_fullyVerify_returnsDone() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED, 3);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill)
                .thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.FULLY_VERIFY), any(RequestInfo.class));
        verify(pws).pushBillUpdate(any(), any());
    }

    @Test
    public void verification_allFailed_failedAction_returnsDone() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_FAILED, 3);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.FAILED), any(RequestInfo.class));
    }

    @Test
    public void verification_mixedVerifiedAndFailed_partiallyVerify_returnsDone() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFIED, Status.VERIFICATION_FAILED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.PARTIALLY_VERIFY), any(RequestInfo.class));
    }

    // ── IGNORE_ERRORS ─────────────────────────────────────────────────────────

    @Test
    public void ignoreErrors_hasVerificationFailed_dispatchesRetryJobs_returnsRetry() {
        Bill bill = buildBillWithMixedDetails(Status.IGNORING_ERRORS_IN_PROGRESS,
                Status.VERIFICATION_FAILED, Status.VERIFIED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_IGNORE_ERRORS));

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(pws, times(1)).insertDetailWfUpdateRetryJob(
                argThat(d -> d.getStatus() == Status.VERIFICATION_FAILED), any(),
                eq(POLL_PHASE_IGNORE_ERRORS), any());
    }

    @Test
    public void ignoreErrors_allVerified_complete_returnsDone() {
        Bill bill = buildBill(Status.IGNORING_ERRORS_IN_PROGRESS, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_IGNORE_ERRORS));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    // ── SEND_FOR_REVIEW ──────────────────────────────────────────────────────

    @Test
    public void sendForReview_hasVerified_dispatchesRetryJobs_returnsRetry() {
        Bill bill = buildBillWithMixedDetails(Status.SENDING_FOR_REVIEW,
                Status.VERIFIED, Status.UNDER_REVIEW);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(pws, times(1)).insertDetailWfUpdateRetryJob(
                argThat(d -> d.getStatus() == Status.VERIFIED), any(),
                eq(POLL_PHASE_SEND_FOR_REVIEW), any());
    }

    @Test
    public void sendForReview_allUnderReviewOrBeyond_complete_returnsDone() {
        Bill bill = buildBillWithMixedDetails(Status.SENDING_FOR_REVIEW,
                Status.UNDER_REVIEW, Status.REVIEWED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    // ── REVIEW ───────────────────────────────────────────────────────────────

    @Test
    public void review_hasUnderReview_dispatchesSendForApprovalRetryJob_returnsRetry() {
        Bill bill = buildBillWithMixedDetails(Status.REVIEW_IN_PROGRESS,
                Status.UNDER_REVIEW, Status.REVIEWED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_REVIEW));

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(pws).insertDetailWfUpdateRetryJob(
                argThat(d -> d.getStatus() == Status.UNDER_REVIEW), any(),
                eq(POLL_PHASE_SEND_FOR_APPROVAL), any());
    }

    @Test
    public void review_allReviewed_complete_returnsDone() {
        Bill bill = buildBill(Status.REVIEW_IN_PROGRESS, Status.REVIEWED, 3);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_REVIEW));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.COMPLETE), any(RequestInfo.class));
    }

    // ── PAYMENT ──────────────────────────────────────────────────────────────

    @Test
    public void payment_billAlreadyExited_returnsDone_noTransition() {
        Bill bill = buildBill(Status.FULLY_PAID, Status.PAID, 3);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void payment_allInProgress_returnsRetry() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    @Test
    public void payment_allPaid_fullyPay_returnsDone() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAID, 3);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.FULLY_PAY), any(RequestInfo.class));
    }

    @Test
    public void payment_mixedPaidAndFailed_partiallyPay_returnsDone() {
        Bill bill = buildBillWithMixedDetails(Status.PAYMENT_IN_PROGRESS,
                Status.PAID, Status.PAYMENT_FAILED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.PARTIALLY_PAY), any(RequestInfo.class));
    }

    @Test
    public void payment_allFailed_failedAction_returnsDone() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_FAILED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBill(any(), eq(Actions.FAILED), any(RequestInfo.class));
    }

    @Test
    public void payment_refetchShowsBillExited_concurrentTransition_returnsDone() {
        Bill stale = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAID, 2);
        Bill fresh = buildBill(Status.FULLY_PAID, Status.PAID, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(stale)
                .thenReturn(fresh);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── Infrastructure / edge cases ──────────────────────────────────────────

    @Test
    public void billNotFound_returnsFailed() {
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(null);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        assertEquals(SchedulerJobResult.FAILED, result);
    }

    @Test
    public void unknownPhase_returnsFailed() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStatusPollJob(BILL_ID, "UNKNOWN_PHASE"));

        assertEquals(SchedulerJobResult.FAILED, result);
    }

    // ── onMaxAttemptsExceeded ────────────────────────────────────────────────

    @Test
    public void onMaxAttempts_verification_appliesFailedAction() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        verify(pws).transitionBill(any(), eq(Actions.FAILED), any(RequestInfo.class));
        verify(pws).pushBillUpdate(any(), any());
    }

    @Test
    public void onMaxAttempts_ignoreErrors_appliesFailAction() {
        Bill bill = buildBill(Status.IGNORING_ERRORS_IN_PROGRESS, Status.VERIFICATION_FAILED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_IGNORE_ERRORS));

        verify(pws).transitionBill(any(), eq(Actions.FAIL), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_sendForReview_appliesFailAction() {
        Bill bill = buildBill(Status.SENDING_FOR_REVIEW, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_SEND_FOR_REVIEW));

        verify(pws).transitionBill(any(), eq(Actions.FAIL), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_payment_appliesFailedAction() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        verify(pws).transitionBill(any(), eq(Actions.FAILED), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_invalidAction_noException() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        doThrow(new RuntimeException("INVALID ACTION"))
                .when(pws).transitionBill(any(), any(), any(RequestInfo.class));
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));
        // no exception thrown
    }

    @Test
    public void onMaxAttempts_billNotFound_silentReturn() {
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(null);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── EC-8: compensation skipped when bill already past intermediate state ──

    @Test
    public void onMaxAttempts_verification_billAlreadyFullyVerified_skipsCompensation() {
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));

        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_ignoreErrors_billAlreadySentForReview_skipsCompensation() {
        Bill bill = buildBill(Status.SENDING_FOR_REVIEW, Status.VERIFIED, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_IGNORE_ERRORS));

        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_sendForReview_billAlreadyUnderReview_skipsCompensation() {
        Bill bill = buildBill(Status.UNDER_REVIEW, Status.UNDER_REVIEW, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_SEND_FOR_REVIEW));

        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    @Test
    public void onMaxAttempts_payment_billAlreadyFullyPaid_skipsCompensation() {
        Bill bill = buildBill(Status.FULLY_PAID, Status.PAID, 2);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStatusPollJob(BILL_ID, POLL_PHASE_PAYMENT));

        verify(pws, never()).transitionBill(any(), any(), any(RequestInfo.class));
    }

    // ── Infinite loop prevention ──────────────────────────────────────────────

    @Test
    public void handle_verification_retryCount_oneAttemptPerHandleCall_noInternalLoop() {
        Bill bill = buildBillWithMixedDetails(Status.VERIFICATION_IN_PROGRESS,
                Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        // Call handle 3 times — each should fetch bill exactly once
        for (int i = 0; i < 3; i++) {
            handler.handle(buildBillStatusPollJob(BILL_ID, POLL_PHASE_VERIFICATION));
        }
        // 3 handle() calls × 1 fetch each = 3 total, no internal retry loop
        verify(pws, times(3)).fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean());
    }
}
