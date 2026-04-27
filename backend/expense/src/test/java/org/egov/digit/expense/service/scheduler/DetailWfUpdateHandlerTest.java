package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_IGNORE_ERRORS;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_APPROVAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DetailWfUpdateHandlerTest {

    @Mock
    private PaymentWorkflowService pws;
    @Mock
    private BillAggregationService agg;
    @Mock
    private WorkflowUtil workflowUtil;

    private DetailWfUpdateHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new DetailWfUpdateHandler(pws, agg, workflowUtil, new ObjectMapper());
    }

    // ── Phase happy paths ─────────────────────────────────────────────────────

    @Test
    public void handle_ignoreErrors_transitionsAndAggregates() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED);
        Bill bill = buildBillWithDetails(Status.IGNORING_ERRORS_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_IGNORE_ERRORS));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBillDetail(eq(detail), eq(Actions.IGNORE_ERRORS_AND_VERIFY), any());
        ArgumentCaptor<Bill> captor = ArgumentCaptor.forClass(Bill.class);
        verify(pws).pushBillUpdate(captor.capture(), any(), eq(false));
        assertEquals(1, captor.getValue().getBillDetails().size());
        assertEquals(DETAIL_ID_1, captor.getValue().getBillDetails().get(0).getId());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_IGNORE_ERRORS), any());
    }

    @Test
    public void handle_sendForReview_transitionsAndAggregates() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBillDetail(eq(detail), eq(Actions.SEND_FOR_REVIEW), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_SEND_FOR_REVIEW), any());
    }

    @Test
    public void handle_sendForApproval_transitionsAndAggregates() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.REVIEW_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_APPROVAL));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).transitionBillDetail(eq(detail), eq(Actions.SEND_FOR_APPROVAL), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_SEND_FOR_APPROVAL), any());
    }

    // ── Idempotency: detail already transitioned ──────────────────────────────

    @Test
    public void handle_ignoreErrors_alreadyVerified_skipsTransition() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.IGNORING_ERRORS_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_IGNORE_ERRORS));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws, never()).transitionBillDetail(any(), any(), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), any(), eq(POLL_PHASE_IGNORE_ERRORS), any());
    }

    @Test
    public void handle_sendForReview_alreadyUnderReview_skipsTransition() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    // ── INVALID_ACTION treated as already transitioned ────────────────────────

    @Test
    public void handle_invalidActionFromWf_treatedAsIdempotent_returnsDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        doThrow(new RuntimeException("INVALID ACTION")).when(pws).transitionBillDetail(any(), any(), any());
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(eq(BILL_ID), any(), eq(POLL_PHASE_SEND_FOR_REVIEW), any());
    }

    // ── Non-retryable error → RETRY ──────────────────────────────────────────

    @Test
    public void handle_nonRetryableError_returnsRetry() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        doThrow(new RuntimeException("DB connection failed")).when(pws).transitionBillDetail(any(), any(), any());
        when(workflowUtil.isRetryableWfError(any())).thenReturn(false);

        SchedulerJobResult result = handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    // ── Single-detail push guard ─────────────────────────────────────────────

    @Test
    public void handle_5DetailBill_pushesOnlySingleDetail() {
        List<BillDetail> details = Arrays.asList(
                buildDetail("d1", Status.VERIFIED), buildDetail("d2", Status.VERIFIED),
                buildDetail("d3", Status.VERIFIED), buildDetail("d4", Status.VERIFIED),
                buildDetail("d5", Status.VERIFIED));
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, details);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        handler.handle(buildDetailWfUpdateJob(BILL_ID, "d3", POLL_PHASE_SEND_FOR_REVIEW));

        ArgumentCaptor<Bill> captor = ArgumentCaptor.forClass(Bill.class);
        verify(pws).pushBillUpdate(captor.capture(), any(), eq(false));
        assertEquals(1, captor.getValue().getBillDetails().size());
        assertEquals("d3", captor.getValue().getBillDetails().get(0).getId());
    }

    // ── Missing data ──────────────────────────────────────────────────────────

    @Test
    public void handle_billNotFound_returnsFailed() {
        when(pws.fetchBillWithDetails(any(), any(), any())).thenReturn(null);
        assertEquals(SchedulerJobResult.FAILED,
                handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW)));
    }

    @Test
    public void handle_detailNotInBill_returnsFailed() {
        Bill bill = buildBill(Status.SENDING_FOR_REVIEW, Status.VERIFIED, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        assertEquals(SchedulerJobResult.FAILED,
                handler.handle(buildDetailWfUpdateJob(BILL_ID, "NON_EXISTENT", POLL_PHASE_SEND_FOR_REVIEW)));
    }

    @Test
    public void handle_unknownPhase_returnsFailed() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        assertEquals(SchedulerJobResult.FAILED,
                handler.handle(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, "UNKNOWN")));
    }

    // ── onMaxAttemptsExceeded ────────────────────────────────────────────────

    @Test
    public void onMaxAttempts_nonPaymentPhase_appliesFailCompensation() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail, buildDetail(DETAIL_ID_2, Status.VERIFIED)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        verify(pws).transitionBill(eq(bill), eq(Actions.FAIL), any(RequestInfo.class));
        verify(pws).pushBillUpdate(eq(bill), any());
    }

    @Test
    public void onMaxAttempts_compensationInvalidAction_noException() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail, buildDetail(DETAIL_ID_2, Status.VERIFIED)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        doThrow(new RuntimeException("INVALID ACTION"))
                .when(pws).transitionBill(any(), any(), any(RequestInfo.class));
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);

        handler.onMaxAttemptsExceeded(buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));
        // no exception thrown
    }
}
