package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.STARTED_CHECK_PHASE_VERIFY;
import static org.egov.digit.expense.config.Constants.STARTED_CHECK_PHASE_PAYMENT;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillStartedCheckHandlerTest {

    @Mock private PaymentWorkflowService pws;
    @Mock private WorkflowUtil workflowUtil;

    private BillStartedCheckHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new BillStartedCheckHandler(pws, workflowUtil, new ObjectMapper());
        when(pws.getSafetyNetDelayMs()).thenReturn(60000L);
    }

    // ── VERIFY phase happy path ───────────────────────────────────────────────

    @Test
    public void handle_verifyPhase_allDetailsInProgress_dispatchesVerifyTasksAndDone() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFICATION_IN_PROGRESS);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.VERIFICATION_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(d1, d2));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).insertBillStatusPollJob(eq(bill), eq(POLL_PHASE_VERIFICATION), any(), anyLong());
        verify(pws, times(2)).pushVerificationVerifyTask(eq(bill), any(BillDetail.class), any());
    }

    @Test
    public void handle_verifyPhase_someDetailsPending_retries() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.VERIFICATION_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(d1, d2));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(pws, never()).insertBillStatusPollJob(any(), any(), any(), anyLong());
        verify(pws, never()).pushVerificationVerifyTask(any(), any(), any());
    }

    @Test
    public void handle_verifyPhase_verificationFailedDetail_returnsDone() {
        // VERIFICATION_FAILED is treated as settled (not pending) — handler proceeds to insert
        // poll job and returns DONE so the scheduler does not spin on a failed detail.
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(d1));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.DONE, result);
    }

    @Test
    public void handle_verifyPhase_alreadyVerified_noTaskDispatch() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(d1));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.DONE, result);
        // Already verified — no task dispatched for this detail
        verify(pws, never()).pushVerificationVerifyTask(any(), any(), any());
    }

    // ── PAYMENT phase happy path ──────────────────────────────────────────────

    @Test
    public void handle_paymentPhase_allDetailsInProgress_dispatchesPaymentTasksAndDone() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(d1, d2));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).insertBillStatusPollJob(eq(bill), eq(POLL_PHASE_PAYMENT), any(), anyLong());
        verify(pws, times(2)).pushPaymentInitiationPayTask(eq(bill), any(BillDetail.class), any());
    }

    @Test
    public void handle_paymentPhase_someDetailsReviewed_retries() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.REVIEWED);
        BillDetail d2 = buildDetail(DETAIL_ID_2, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(d1, d2));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_PAYMENT));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    // ── Missing data ──────────────────────────────────────────────────────────

    @Test
    public void handle_billNotFound_returnsFailed() {
        when(pws.fetchBillWithDetails(any(), any(), any(), anyBoolean())).thenReturn(null);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.FAILED, result);
    }

    @Test
    public void handle_unknownPhase_returnsFailed() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildBillStartedCheckJob(BILL_ID, "UNKNOWN_PHASE"));

        assertEquals(SchedulerJobResult.FAILED, result);
    }

    // ── EC-8: onMaxAttemptsExceeded compensation ──────────────────────────────

    @Test
    public void onMaxAttempts_verifyPhase_billStillInVerification_forcesStuckDetailsFailed() {
        BillDetail stuck = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(stuck));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        verify(pws).transitionBillDetail(eq(stuck), any(), any());
    }

    @Test
    public void onMaxAttempts_verifyPhase_billAlreadyFullyVerified_skipsCompensation() {
        // EC-8: bill already exited verification intermediate state
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    @Test
    public void onMaxAttempts_verifyPhase_billPaid_skipsCompensation() {
        Bill bill = buildBill(Status.FULLY_PAID, Status.PAID, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    @Test
    public void onMaxAttempts_paymentPhase_billStillInPayment_forcesStuckDetailsFailed() {
        BillDetail stuck = buildDetail(DETAIL_ID_1, Status.REVIEWED);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(stuck));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_PAYMENT));

        verify(pws).transitionBillDetail(eq(stuck), any(), any());
    }

    @Test
    public void onMaxAttempts_paymentPhase_billAlreadyFullyPaid_skipsCompensation() {
        Bill bill = buildBill(Status.FULLY_PAID, Status.PAID, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_PAYMENT));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    // ── Infinite loop prevention ──────────────────────────────────────────────

    @Test
    public void handle_verifyPhase_maxRetries_doesNotCauseInfiniteCompensationLoop() {
        // Simulate calling onMaxAttempts multiple times — only one compensation attempt per detail
        BillDetail stuck = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(stuck));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);

        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));
        handler.onMaxAttemptsExceeded(buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        // Each call makes at most one WF attempt per detail (no looping inside)
        verify(pws, atMost(2)).transitionBillDetail(any(), any(), any());
    }
}
