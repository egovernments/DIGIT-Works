package org.egov.digit.expense.service;

import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.BillDetailRequest;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.WorkflowUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaymentWorkflowServiceTest {

    @Mock private WorkflowUtil workflowUtil;
    @Mock private BillRepository billRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private SchedulerJobRepository schedulerJobRepository;
    @Mock private SchedulerJobRegistry schedulerJobRegistry;
    @Mock private Configuration config;
    @Mock private ExpenseProducer expenseProducer;
    @Mock private BillCacheService billCacheService;
    @Mock private BillDetailService billDetailService;
    @Mock private ApplicationEventPublisher eventPublisher;

    private PaymentWorkflowService pws;

    @BeforeEach
    public void setUp() {
        pws = new PaymentWorkflowService(workflowUtil, billRepository, taskRepository,
                schedulerJobRepository, schedulerJobRegistry, eventPublisher, config, expenseProducer,
                billCacheService, billDetailService);
        when(config.getBillDetailBusinessService()).thenReturn("PAYMENTS.BILLDETAILS");
        when(config.getBillUpdateTopic()).thenReturn("expense-bill-update");
        when(workflowUtil.prepareWorkflowRequestForBill(any(BillRequest.class))).thenReturn(null);
        when(workflowUtil.prepareWorkflowRequestForBillDetail(any(BillDetailRequest.class))).thenReturn(null);
    }

    // ── EC-1: fetchBillWithDetails — Redis-first ──────────────────────────────

    @Test
    public void fetchBillWithDetails_redisMiss_returnsDbBill() {
        Bill dbBill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        when(billRepository.search(any(BillSearchRequest.class), eq(false))).thenReturn(List.of(dbBill));
        when(billCacheService.get(TENANT_ID, BILL_ID)).thenReturn(Optional.empty());

        Bill result = pws.fetchBillWithDetails(BILL_ID, TENANT_ID, buildRequestInfo());

        assertSame(dbBill, result);
    }

    @Test
    public void fetchBillWithDetails_redisHit_returnsRedisBill() {
        Bill dbBill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        Bill redisBill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 1);
        when(billRepository.search(any(BillSearchRequest.class), eq(false))).thenReturn(List.of(dbBill));
        when(billCacheService.get(TENANT_ID, BILL_ID)).thenReturn(Optional.of(redisBill));

        Bill result = pws.fetchBillWithDetails(BILL_ID, TENANT_ID, buildRequestInfo());

        assertSame(redisBill, result);  // Redis wins over stale DB
        assertEquals(Status.VERIFICATION_IN_PROGRESS, result.getStatus());
    }

    @Test
    public void fetchBillWithDetails_dbEmpty_returnsNull() {
        when(billRepository.search(any(BillSearchRequest.class), eq(false))).thenReturn(List.of());
        when(billCacheService.get(any(), any())).thenReturn(Optional.empty());

        assertNull(pws.fetchBillWithDetails(BILL_ID, TENANT_ID, buildRequestInfo()));
    }

    @Test
    public void fetchBillWithDetails_bypassCache_ignoresRedisReturnsDb() {
        Bill dbBill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        Bill redisBill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 1);
        when(billRepository.search(any(BillSearchRequest.class), eq(false))).thenReturn(List.of(dbBill));
        when(billCacheService.get(TENANT_ID, BILL_ID)).thenReturn(Optional.of(redisBill));

        Bill result = pws.fetchBillWithDetails(BILL_ID, TENANT_ID, buildRequestInfo(), true);

        assertSame(dbBill, result);  // bypassCache=true always returns DB bill
        verify(billCacheService, never()).get(any(), any());
    }

    // ── EC-2: transitionBillDetail — INVALID_ACTION reconciles ───────────────

    @Test
    public void transitionBillDetail_success_updatesDetailStatus() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        State wfState = buildWfState("VERIFICATION_IN_PROGRESS");
        when(workflowUtil.callWorkFlow(isNull(), any(BillDetailRequest.class))).thenReturn(wfState);

        pws.transitionBillDetail(detail, Actions.VERIFY, buildRequestInfo());

        assertEquals(Status.VERIFICATION_IN_PROGRESS, detail.getStatus());
    }

    @Test
    public void transitionBillDetail_invalidAction_searchesWfAndReconciles() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        RuntimeException invalidAction = new RuntimeException("INVALID ACTION");
        State reconciledState = buildWfState("VERIFICATION_IN_PROGRESS");
        when(workflowUtil.callWorkFlow(isNull(), any(BillDetailRequest.class))).thenThrow(invalidAction);
        when(workflowUtil.isRetryableWfError(invalidAction)).thenReturn(true);
        when(workflowUtil.searchCurrentWfState(eq(DETAIL_ID_1), eq(TENANT_ID), any()))
                .thenReturn(reconciledState);

        pws.transitionBillDetail(detail, Actions.VERIFY, buildRequestInfo());

        // Status reconciled from WF search
        assertEquals(Status.VERIFICATION_IN_PROGRESS, detail.getStatus());
    }

    @Test
    public void transitionBillDetail_invalidAction_wfSearchFails_throwsCustomException() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        RuntimeException invalidAction = new RuntimeException("INVALID ACTION");
        when(workflowUtil.callWorkFlow(isNull(), any(BillDetailRequest.class))).thenThrow(invalidAction);
        when(workflowUtil.isRetryableWfError(invalidAction)).thenReturn(true);
        when(workflowUtil.searchCurrentWfState(eq(DETAIL_ID_1), eq(TENANT_ID), any())).thenReturn(null);

        CustomException ex = assertThrows(CustomException.class,
                () -> pws.transitionBillDetail(detail, Actions.VERIFY, buildRequestInfo()));
        assertNotNull(ex.getCode());
    }

    @Test
    public void transitionBillDetail_nonRetryableError_propagatesException() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        RuntimeException networkError = new RuntimeException("Connection refused");
        when(workflowUtil.callWorkFlow(isNull(), any(BillDetailRequest.class))).thenThrow(networkError);
        when(workflowUtil.isRetryableWfError(networkError)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> pws.transitionBillDetail(detail, Actions.VERIFY, buildRequestInfo()));
        verify(workflowUtil, never()).searchCurrentWfState(any(), any(), any());
    }

    // ── EC-2: transitionBill — same pattern ───────────────────────────────────

    @Test
    public void transitionBill_invalidAction_reconcilesBillStatus() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFIED, 1);
        RuntimeException invalidAction = new RuntimeException("INVALID ACTION");
        State reconciledState = buildWfState("FULLY_VERIFIED");
        when(workflowUtil.callWorkFlow(isNull(), any(BillRequest.class))).thenThrow(invalidAction);
        when(workflowUtil.isRetryableWfError(invalidAction)).thenReturn(true);
        when(workflowUtil.searchCurrentWfState(eq(BILL_NUMBER), eq(TENANT_ID), any()))
                .thenReturn(reconciledState);

        pws.transitionBill(bill, Actions.FULLY_VERIFY, buildRequestInfo());

        assertEquals(Status.FULLY_VERIFIED, bill.getStatus());
    }

    // ── EC-1 Fix B: pushBillUpdate — caches before Kafka ─────────────────────

    @Test
    public void pushBillUpdate_cacheTrueDefault_cachesBeforeKafkaPush() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 1);

        pws.pushBillUpdate(bill, buildRequestInfo());

        InOrder order = inOrder(billCacheService, expenseProducer);
        order.verify(billCacheService).put(bill);
        order.verify(expenseProducer).push(any(), any(), any());
    }

    @Test
    public void pushBillUpdate_cacheFalse_skipsCacheButPushesKafka() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.VERIFICATION_IN_PROGRESS, 1);

        pws.pushBillUpdate(bill, buildRequestInfo(), false);

        verify(billCacheService, never()).put(any());
        verify(expenseProducer).push(any(), any(), any());
    }
}
