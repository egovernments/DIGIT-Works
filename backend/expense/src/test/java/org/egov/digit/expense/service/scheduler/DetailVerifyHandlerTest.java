package org.egov.digit.expense.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.service.BillAggregationService;
import org.egov.digit.expense.service.PaymentProviderService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.TaskRequest;
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
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DetailVerifyHandlerTest {

    @Mock
    private PaymentWorkflowService pws;
    @Mock
    private BillAggregationService agg;
    @Mock
    private WorkflowUtil workflowUtil;
    @Mock
    private PaymentProviderService mtnService;

    private DetailVerifyHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new DetailVerifyHandler(pws, agg, workflowUtil, List.of(mtnService), new ObjectMapper());
        when(mtnService.supports(any())).thenReturn(true);
    }

    @Test
    public void handle_mtnActive_detailVerified_callsAggregation() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        Bill refreshed = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS,
                List.of(buildDetail(DETAIL_ID_1, Status.VERIFIED)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any()))
                .thenReturn(bill).thenReturn(refreshed);

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(mtnService).executeTask(any(TaskRequest.class));
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_VERIFICATION), any());
    }

    @Test
    public void handle_mtnActive_detailFailed_callsAggregation() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        Bill refreshed = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS,
                List.of(buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any()))
                .thenReturn(bill).thenReturn(refreshed);

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(agg).checkAndAggregateBill(eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_VERIFICATION), any());
    }

    @Test
    public void handle_alreadyVerified_idempotent_noExecuteTask() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(mtnService, never()).executeTask(any());
        verify(agg).checkAndAggregateBill(eq(BILL_ID), any(), eq(POLL_PHASE_VERIFICATION), any());
    }

    @Test
    public void handle_alreadyVerificationFailed_idempotent_callsAggregation() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.DONE, result);
        verify(mtnService, never()).executeTask(any());
        verify(agg).checkAndAggregateBill(any(), any(), eq(POLL_PHASE_VERIFICATION), any());
    }

    @Test
    public void handle_stillInProgressAfterExecute_returnsRetry() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_IN_PROGRESS);
        Bill billSame = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any()))
                .thenReturn(billSame).thenReturn(billSame);

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    @Test
    public void handle_executeTaskThrows_returnsRetry() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        doThrow(new RuntimeException("MTN timeout")).when(mtnService).executeTask(any());

        SchedulerJobResult result = handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1));

        assertEquals(SchedulerJobResult.RETRY, result);
    }

    @Test
    public void handle_billNotFound_returnsFailed() {
        when(pws.fetchBillWithDetails(any(), any(), any())).thenReturn(null);
        assertEquals(SchedulerJobResult.FAILED, handler.handle(buildDetailVerifyJob(BILL_ID, DETAIL_ID_1)));
    }

    @Test
    public void handle_detailNotInBill_returnsFailed() {
        Bill bill = buildBill(Status.VERIFICATION_IN_PROGRESS, Status.PENDING_VERIFICATION, 1);
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any())).thenReturn(bill);
        assertEquals(SchedulerJobResult.FAILED, handler.handle(buildDetailVerifyJob(BILL_ID, "NON_EXISTENT")));
    }

    @Test
    public void handle_passesOnlySingleDetailToProvider() {
        List<BillDetail> details = Arrays.asList(
                buildDetail("d1", Status.PENDING_VERIFICATION),
                buildDetail("d2", Status.PENDING_VERIFICATION));
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, details);
        Bill refreshed = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS,
                List.of(buildDetail("d1", Status.VERIFIED), buildDetail("d2", Status.PENDING_VERIFICATION)));
        when(pws.fetchBillWithDetails(eq(BILL_ID), eq(TENANT_ID), any()))
                .thenReturn(bill).thenReturn(refreshed);

        handler.handle(buildDetailVerifyJob(BILL_ID, "d1"));

        ArgumentCaptor<TaskRequest> captor = ArgumentCaptor.forClass(TaskRequest.class);
        verify(mtnService).executeTask(captor.capture());
        assertEquals(1, captor.getValue().getBill().getBillDetails().size());
        assertEquals("d1", captor.getValue().getBill().getBillDetails().get(0).getId());
    }
}
