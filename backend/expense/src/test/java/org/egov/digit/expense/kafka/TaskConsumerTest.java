package org.egov.digit.expense.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.web.models.*;
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
import java.util.Map;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_IGNORE_ERRORS;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_REVIEW;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_SEND_FOR_APPROVAL;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TaskConsumerTest {

    @Mock
    private PaymentWorkflowService pws;
    @Mock
    private BillAggregationService agg;
    @Mock
    private PaymentProviderService mtnService;
    @Mock
    private ExpenseProducer producer;
    @Mock
    private Configuration config;

    private TaskConsumer consumer;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        when(config.getTaskUpdateTopic()).thenReturn("expense-task-status-update");
        consumer = new TaskConsumer(objectMapper, List.of(mtnService), pws, agg, config, producer);
        when(mtnService.supports(any())).thenReturn(true);
    }

    // ── Routing ───────────────────────────────────────────────────────────────

    @Test
    public void listen_verifyTask_routesToProvider() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        TaskRequest req = buildVerifyTaskRequest(bill, detail);

        consumer.listen(toMap(req));

        verify(mtnService).executeTask(any(TaskRequest.class));
        verify(pws, never()).transitionBillDetail(any(), any(), any());
    }

    @Test
    public void listen_nullProvider_routesToMtnService() {
        BillDetail detail = buildDetailWithPayee(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS, "0770000001", null);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, List.of(detail));
        Task task = Task.builder()
                .id(TASK_ID).tenantId(TENANT_ID).billId(BILL_ID).billDetailId(DETAIL_ID_1)
                .type(Task.Type.Transfer).status(Status.IN_PROGRESS)
                .auditDetails(buildAuditDetails()).build();
        Bill singleDetailBill = Bill.builder()
                .id(BILL_ID).tenantId(TENANT_ID).businessService(BUSINESS_SVC)
                .billNumber(BILL_NUMBER).billDate(System.currentTimeMillis())
                .dueDate(System.currentTimeMillis() + 86400000L)
                .totalAmount(java.math.BigDecimal.valueOf(1000))
                .totalWageAmount(java.math.BigDecimal.valueOf(1000))
                .status(Status.PAYMENT_IN_PROGRESS).referenceId("register-001")
                .billDetails(List.of(detail)).auditDetails(buildAuditDetails()).build();
        TaskRequest req = TaskRequest.builder()
                .task(task).bill(singleDetailBill)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR")).build();
        when(mtnService.supports(null)).thenReturn(true);

        consumer.listen(toMap(req));

        verify(mtnService).executeTask(any());
    }

    // ── WfUpdate happy paths ──────────────────────────────────────────────────

    @Test
    public void listen_wfUpdateIgnoreErrors_transitionsAndAggregates_marksTaskDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED);
        Bill bill = buildBillWithDetails(Status.IGNORING_ERRORS_IN_PROGRESS, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_IGNORE_ERRORS);

        consumer.listen(toMap(req));

        verify(pws).transitionBillDetail(
                argThat(d -> d.getId().equals(DETAIL_ID_1)),
                eq(Actions.IGNORE_ERRORS_AND_VERIFY), any());
        verify(pws).pushBillUpdate(argThat(b -> b.getBillDetails().size() == 1), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_IGNORE_ERRORS), any());
        verify(producer).push(eq(TENANT_ID), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    @Test
    public void listen_wfUpdateSendForReview_transitionsAndAggregates() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_REVIEW);

        consumer.listen(toMap(req));

        verify(pws).transitionBillDetail(any(), eq(Actions.SEND_FOR_REVIEW), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_SEND_FOR_REVIEW), any());
        verify(producer).push(any(), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    @Test
    public void listen_wfUpdateSendForApproval_transitionsAndAggregates() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.REVIEW_IN_PROGRESS, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_APPROVAL);

        consumer.listen(toMap(req));

        verify(pws).transitionBillDetail(any(), eq(Actions.SEND_FOR_APPROVAL), any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_SEND_FOR_APPROVAL), any());
    }

    // ── WfUpdate error handling ───────────────────────────────────────────────

    @Test
    public void listen_wfUpdate_invalidAction_aggregatesAndMarksTaskDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_REVIEW);
        doThrow(new RuntimeException("INVALID ACTION")).when(pws).transitionBillDetail(any(), any(), any());

        consumer.listen(toMap(req));

        verify(producer).push(any(), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    @Test
    public void listen_wfUpdate_nonRetryableError_marksTaskDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_REVIEW);
        doThrow(new RuntimeException("DB error")).when(pws).transitionBillDetail(any(), any(), any());

        consumer.listen(toMap(req));

        verify(producer).push(any(), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    @Test
    public void listen_wfUpdateUnknownPhase_noTransition_marksTaskDone() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, "UNKNOWN_PHASE");

        consumer.listen(toMap(req));

        verify(pws, never()).transitionBillDetail(any(), any(), any());
        verify(producer, atLeastOnce()).push(any(), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    @Test
    public void listen_wfUpdate_taskAlwaysMarkedDone_evenOnUnexpectedException() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, List.of(detail));
        TaskRequest req = buildWfUpdateTaskRequest(bill, detail, POLL_PHASE_SEND_FOR_REVIEW);
        doThrow(new RuntimeException("unexpected")).when(pws).transitionBillDetail(any(), any(), any());

        consumer.listen(toMap(req));

        verify(producer).push(any(), eq("expense-task-status-update"),
                argThat(t -> ((Task) t).getStatus() == Status.DONE));
    }

    // ── Provider throws — offset still commits ─────────────────────────────────

    @Test
    public void listen_providerThrows_doesNotBlockOtherProviders() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        TaskRequest req = buildVerifyTaskRequest(bill, detail);
        doThrow(new RuntimeException("MTN timeout")).when(mtnService).executeTask(any());

        // Should not throw — exception is caught per-service
        consumer.listen(toMap(req));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<>() {});
    }
}
