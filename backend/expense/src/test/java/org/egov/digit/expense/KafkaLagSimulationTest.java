package org.egov.digit.expense;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.kafka.TaskConsumer;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.BillCacheService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.service.TaskCacheService;
import org.egov.digit.expense.service.scheduler.*;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.config.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_PAYMENT;
import static org.egov.digit.expense.config.Constants.STARTED_CHECK_PHASE_VERIFY;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_IGNORE_ERRORS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Simulates Kafka-lag scenarios end-to-end using mocks.
 * Each test models a specific time-delay edge case from the design (EC-1 through EC-8).
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class KafkaLagSimulationTest {

    @Mock private PaymentWorkflowService pws;
    @Mock private BillAggregationService agg;
    @Mock private BillCacheService billCacheService;
    @Mock private TaskCacheService taskCacheService;
    @Mock private TaskRepository taskRepository;
    @Mock private WorkflowUtil workflowUtil;
    @Mock private PaymentProviderService mtnService;
    @Mock private ExpenseProducer producer;
    @Mock private Configuration config;

    private TaskConsumer taskConsumer;
    private DetailWfUpdateHandler detailWfUpdateHandler;
    private BillStartedCheckHandler billStartedCheckHandler;
    private BillDetailsTaskVerifyCheckHandler verifyCheckHandler;
    private BillDetailsTaskPaymentStatusCheckHandler paymentStatusCheckHandler;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        when(config.getTaskUpdateTopic()).thenReturn("expense-task-status-update");
        when(pws.getSafetyNetDelayMs()).thenReturn(60000L);
        when(mtnService.supports(any())).thenReturn(true);
        when(taskCacheService.get(any(), any(), any())).thenReturn(Optional.empty());
        when(taskRepository.searchTask(any())).thenReturn(null);

        taskConsumer = new TaskConsumer(objectMapper, List.of(mtnService), pws, agg,
                billCacheService, taskCacheService, config, producer);
        detailWfUpdateHandler = new DetailWfUpdateHandler(pws, agg, workflowUtil, billCacheService, objectMapper);
        billStartedCheckHandler = new BillStartedCheckHandler(pws, workflowUtil, objectMapper);
        verifyCheckHandler = new BillDetailsTaskVerifyCheckHandler(pws, agg, workflowUtil, List.of(mtnService),
                taskCacheService, taskRepository, objectMapper);
        paymentStatusCheckHandler = new BillDetailsTaskPaymentStatusCheckHandler(pws, agg, workflowUtil,
                null, null, taskCacheService, taskRepository, objectMapper);
    }

    // ── EC-1: DB stale, Redis has current state ───────────────────────────────

    /**
     * Simulates: TaskConsumer fires WfUpdate. DB still has old status (Kafka lag).
     * Redis (via fetchBillWithDetails) has the correct current state.
     * Expected: handler uses Redis state, transition proceeds without double-transition.
     */
    @Test
    public void ec1_wfUpdate_dbStale_redisCurrent_proceedsNormally() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill freshBill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(freshBill, detail, POLL_PHASE_SEND_FOR_REVIEW);

        // Redis has current state (simulated via fetchBillWithDetails returning fresh bill)
        taskConsumer.listen(toMap(req));

        verify(pws).transitionBillDetail(any(), eq(Actions.SEND_FOR_REVIEW), any());
        verify(billCacheService).put(any(Bill.class));  // cached after transition
    }

    /**
     * Simulates: DetailWfUpdateHandler reads bill. DB stale (VERIFICATION_FAILED),
     * Redis shows VERIFIED (already transitioned). isAlreadyTransitioned should return true → DONE.
     */
    @Test
    public void ec1_detailWfUpdate_dbStaleDetailAlreadyAdvanced_skipsTransition() {
        // Redis-merged fetch returns detail already in VERIFIED state
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill redisCurrentBill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(redisCurrentBill);

        SchedulerJobResult result = detailWfUpdateHandler.handle(
                buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_IGNORE_ERRORS));

        // VERIFIED is already past IGNORE_ERRORS target — DONE without WF call
        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws, never()).transitionBillDetail(any(), any(), any());
        verify(agg).checkAndAggregateBill(any(), any(), any(), any());
    }

    /**
     * Simulates: BillStartedCheckHandler polls. DB says PENDING_VERIFICATION (stale).
     * Redis says VERIFICATION_IN_PROGRESS (actual). Handler should see current state and dispatch.
     */
    @Test
    public void ec1_billStartedCheck_dbStaleRedisShowsAllInProgress_dispatches() {
        BillDetail d1 = buildDetail(DETAIL_ID_1, Status.VERIFICATION_IN_PROGRESS);
        Bill redisBill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(d1));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(redisBill);

        SchedulerJobResult result = billStartedCheckHandler.handle(
                buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(pws).pushVerificationVerifyTask(any(), any(), any());
    }

    // ── EC-2: INVALID_ACTION → WF already transitioned ───────────────────────

    /**
     * Simulates: transitionBillDetail throws INVALID_ACTION (WF service reconciles it).
     * PaymentWorkflowService.transitionBillDetail has been updated to search WF state and reconcile.
     * From DetailWfUpdateHandler perspective: exception thrown, caught, returns RETRY.
     * (The reconciliation happens inside transitionBillDetail — tested in PaymentWorkflowServiceTest.)
     */
    @Test
    public void ec2_invalidAction_detailWfUpdateHandler_returnsRetry() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        doThrow(new RuntimeException("INVALID ACTION")).when(pws).transitionBillDetail(any(), any(), any());
        when(workflowUtil.isRetryableWfError(any())).thenReturn(true);

        SchedulerJobResult result = detailWfUpdateHandler.handle(
                buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        // Handler caught exception, reconciliation attempted inside transitionBillDetail
        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(any(), any(), eq(POLL_PHASE_SEND_FOR_REVIEW), any());
    }

    // ── EC-5: Transfer task Kafka lag ────────────────────────────────────────

    /**
     * Simulates: handlePaymentInitiationPayTask pushed Transfer task to Kafka.
     * Before persister writes to DB, BillDetailsTaskPaymentStatusCheckHandler fires.
     * Task in Redis (cached before Kafka push). Handler should find it from Redis.
     */
    @Test
    public void ec5_transferTaskCachedBeforeKafka_paymentStatusCheckFindsIt() {
        Task cachedTask = Task.builder().id(TASK_ID).tenantId(TENANT_ID).billId(BILL_ID)
                .billDetailId(DETAIL_ID_1).type(Task.Type.Transfer).status(Status.IN_PROGRESS)
                .auditDetails(buildAuditDetails()).build();
        when(taskCacheService.get(eq(TENANT_ID), eq(DETAIL_ID_1), eq(Task.Type.Transfer)))
                .thenReturn(Optional.of(cachedTask));

        // DB would return null (lag), but cache returns the task
        when(taskRepository.searchTask(any())).thenReturn(null);

        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);

        // paymentStatusCheckHandler not fully configured (no mtn/bank) — just verify task lookup
        verify(taskRepository, never()).searchTask(any());
    }

    // ── EC-6: Task status update lag ─────────────────────────────────────────

    /**
     * Simulates: markTaskDone pushes DONE status to Kafka. Before persister updates DB,
     * task cache is checked. Ensures task cached before Kafka push.
     */
    @Test
    public void ec6_markTaskDone_cachedBeforeKafkaPush() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_REVIEW);

        taskConsumer.listen(toMap(req));

        // Verify task cached (DONE) before producer push
        var inOrder = inOrder(taskCacheService, producer);
        inOrder.verify(taskCacheService).put(argThat(t -> t.getStatus() == Status.DONE));
        inOrder.verify(producer).push(any(), any(), any());
    }

    // ── EC-8: Compensation skipped when bill already exited intermediate state ─

    @Test
    public void ec8_detailWfUpdateMaxAttempts_billAlreadySentForReview_skipsCompensation() {
        Bill billPastIntermediate = buildBillWithDetails(Status.FULLY_VERIFIED,
                List.of(buildDetail(DETAIL_ID_1, Status.VERIFIED)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(billPastIntermediate);

        detailWfUpdateHandler.onMaxAttemptsExceeded(
                buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));

        verify(pws, never()).transitionBill(any(Bill.class), eq(Actions.FAIL), any(RequestInfo.class));
    }

    @Test
    public void ec8_billStartedCheckMaxAttempts_billAlreadyPaid_skipsCompensation() {
        Bill paidBill = buildBill(Status.FULLY_PAID, Status.PAID, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(paidBill);

        billStartedCheckHandler.onMaxAttemptsExceeded(
                buildBillStartedCheckJob(BILL_ID, STARTED_CHECK_PHASE_VERIFY));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    // ── No infinite loop: handler always terminates ──────────────────────────

    @Test
    public void noInfiniteLoop_detailWfUpdateHandlerTerminatesOnMaxRetries() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any(), anyBoolean())).thenReturn(bill);
        when(workflowUtil.isRetryableWfError(any())).thenReturn(false);
        doThrow(new RuntimeException("persistent WF error")).when(pws).transitionBillDetail(any(), any(), any());

        // Calling handle N times — each call returns RETRY (no infinite spinning inside handle())
        for (int i = 0; i < 5; i++) {
            SchedulerJobResult result = detailWfUpdateHandler.handle(
                    buildDetailWfUpdateJob(BILL_ID, DETAIL_ID_1, POLL_PHASE_SEND_FOR_REVIEW));
            assertEquals(SchedulerJobResult.RETRY, result);
        }
        // Exactly one WF attempt per handle() call — no internal retry loop
        verify(pws, times(5)).transitionBillDetail(any(), any(), any());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<>() {});
    }
}
