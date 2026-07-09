package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.BankPaymentService;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.MTNService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.service.TaskCacheService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailsTaskPaymentStatusCheckHandlerTest {

    @Mock private PaymentWorkflowService pws;
    @Mock private BillAggregationService agg;
    @Mock private WorkflowUtil workflowUtil;
    @Mock private MTNService mtnService;
    @Mock private BankPaymentService bankPaymentService;
    @Mock private TaskCacheService taskCacheService;
    @Mock private TaskRepository taskRepository;

    private BillDetailsTaskPaymentStatusCheckHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new BillDetailsTaskPaymentStatusCheckHandler(pws, agg, workflowUtil, mtnService,
                bankPaymentService, taskCacheService, taskRepository, new ObjectMapper());
        when(taskCacheService.get(any(), any(), any())).thenReturn(Optional.empty());
        when(taskRepository.searchTask(any())).thenReturn(buildTransferTask());
        when(mtnService.updatePaymentTaskStatusAndFinalize(any())).thenReturn(false);
        when(bankPaymentService.updatePaymentTaskStatusAndFinalize(any())).thenReturn(false);
    }

    // ── Happy path: detail settles ────────────────────────────────────────────

    @Test
    public void handle_detailAlreadyPaid_aggregatesAndDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAID);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_PAYMENT), any());
        verify(mtnService, never()).updatePaymentTaskStatusAndFinalize(any());
    }

    @Test
    public void handle_detailAlreadyPaymentFailed_aggregatesAndDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_FAILED);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(any(), any(), any(), any());
    }

    @Test
    public void handle_paymentInProgress_mtnReturnsSettled_aggregatesAndDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        Bill refreshed = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS,
                List.of(buildDetail(DETAIL_ID_1, Status.PAID)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean()))
                .thenReturn(bill).thenReturn(refreshed);
        when(mtnService.updatePaymentTaskStatusAndFinalize(any())).thenReturn(false);

        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_PAYMENT), any());
    }

    @Test
    public void handle_paymentInProgress_mtnStillInProgress_retries() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(mtnService.updatePaymentTaskStatusAndFinalize(any())).thenReturn(true);

        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.RETRY, result);
        verify(agg, never()).checkAndAggregateBill(any(), any(), any(), any());
    }

    // ── EC-5: Transfer task cache-first ──────────────────────────────────────

    @Test
    public void handle_transferTaskFoundInCache_doesNotQueryDb() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        Task cachedTask = buildTransferTaskWithId("cached-task-id");
        when(taskCacheService.get(eq(TENANT_ID), eq(DETAIL_ID_1), eq(Task.Type.Transfer)))
                .thenReturn(Optional.of(cachedTask));

        handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        verify(taskRepository, never()).searchTask(any());
    }

    @Test
    public void handle_transferTaskCacheMiss_fallsBackToDb() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(taskCacheService.get(any(), any(), any())).thenReturn(Optional.empty());
        when(taskRepository.searchTask(any())).thenReturn(buildTransferTask());

        handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        verify(taskRepository).searchTask(any());
    }

    @Test
    public void handle_noTransferTaskFound_retries() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(taskCacheService.get(any(), any(), any())).thenReturn(Optional.empty());
        when(taskRepository.searchTask(any())).thenReturn(null);

        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    // ── Missing data ──────────────────────────────────────────────────────────

    @Test
    public void handle_billNotFound_returnsFailed() {
        when(pws.fetchBillWithDetails(any(), any(), any(), anyBoolean())).thenReturn(null);
        assertEquals(SchedulerJobResult.FAILED, handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1)));
    }

    @Test
    public void handle_detailNotInBill_returnsFailed() {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        assertEquals(SchedulerJobResult.FAILED, handler.handle(buildPaymentStatusCheckJob(BILL_ID, "NON_EXISTENT")));
    }

    // ── onMaxAttemptsExceeded ─────────────────────────────────────────────────

    @Test
    public void onMaxAttempts_detailStillInProgress_forcesPaymentFailed() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        verify(pws).transitionBillDetail(eq(detail), eq(Actions.FAILED), any());
        verify(agg).checkAndAggregateBill(any(), any(), eq(POLL_PHASE_PAYMENT), any());
    }

    @Test
    public void onMaxAttempts_detailAlreadyPaid_skipsTransition() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAID);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        handler.onMaxAttemptsExceeded(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
        verify(agg).checkAndAggregateBill(any(), any(), eq(POLL_PHASE_PAYMENT), any());
    }

    // ── No Thread.sleep: provider failure causes RETRY not blocking ───────────

    @Test
    public void handle_mtnThrows_returnsRetry_noThreadSleep() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(mtnService.updatePaymentTaskStatusAndFinalize(any())).thenThrow(new RuntimeException("MTN timeout"));

        long start = System.currentTimeMillis();
        SchedulerJobResult result = handler.handle(buildPaymentStatusCheckJob(BILL_ID, DETAIL_ID_1));
        long elapsed = System.currentTimeMillis() - start;

        assertEquals(SchedulerJobResult.RETRY, result);
        // Verify no blocking sleep — should complete in well under 1 second
        assert elapsed < 1000 : "handler took " + elapsed + "ms — possible Thread.sleep";
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Task buildTransferTask() {
        return buildTransferTaskWithId(TASK_ID);
    }

    private Task buildTransferTaskWithId(String id) {
        return Task.builder()
                .id(id)
                .tenantId(TENANT_ID)
                .billId(BILL_ID)
                .billDetailId(DETAIL_ID_1)
                .type(Task.Type.Transfer)
                .status(Status.IN_PROGRESS)
                .auditDetails(buildAuditDetails())
                .build();
    }
}
