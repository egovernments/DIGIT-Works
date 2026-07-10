package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.MTNUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillDetailRequest;
import org.egov.digit.expense.web.models.ErrorDetails;
import org.egov.digit.expense.web.models.TaskRequest;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.POLL_PHASE_VERIFICATION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MTNServiceVerifyTest {

    @Mock private ExpenseProducer expenseProducer;
    @Mock private Configuration config;
    @Mock private BillValidator validator;
    @Mock private WorkflowUtil workflowUtil;
    @Mock private BillRepository billRepository;
    @Mock private EnrichmentUtil enrichmentUtil;
    @Mock private ResponseInfoFactory responseInfoFactory;
    @Mock private TaskRepository taskRepository;
    @Mock private MTNUtil mtnUtil;
    @Mock private SchedulerJobRepository schedulerJobRepository;
    @Mock private SchedulerJobRegistry schedulerJobRegistry;
    @Mock private BillAggregationService billAggregationService;
    @Mock private BillCacheService billCacheService;
    @Mock private BillDetailService billDetailService;

    private MTNService mtnService;

    @BeforeEach
    public void setup() {
        mtnService = new MTNService(expenseProducer, config, validator, workflowUtil, billRepository,
                enrichmentUtil, responseInfoFactory, taskRepository, mtnUtil,
                schedulerJobRepository, schedulerJobRegistry, new ObjectMapper(),
                billAggregationService, billCacheService, billDetailService);
        when(config.getBillDetailBusinessService()).thenReturn("PAYMENTS.BILLDETAILS");
        when(config.getTaskUpdateTopic()).thenReturn("task-update");
        when(config.getBillUpdateTopic()).thenReturn("bill-update");
        when(validator.isWorkflowActiveForBusinessService(any())).thenReturn(true);
    }

    private TaskRequest setupVerifyTask(Status detailStatus, String provider) {
        BillDetail detail = buildDetailWithPayee(DETAIL_ID_1, detailStatus, "0770000001", provider);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, List.of(detail));
        when(billRepository.search(any(), eq(true))).thenReturn(List.of(bill));
        return buildVerifyTaskRequest(bill, detail);
    }

    @Test
    public void verify_blankProvider_inProgressDetail_marksVerificationFailed_withErrorDetails() {
        TaskRequest taskRequest = setupVerifyTask(Status.VERIFICATION_IN_PROGRESS, "");
        when(workflowUtil.callWorkFlow(any(), any(BillDetailRequest.class)))
                .thenReturn(buildWfState("VERIFICATION_FAILED"));

        mtnService.verify(taskRequest);

        ArgumentCaptor<BillDetailRequest> wfCaptor = ArgumentCaptor.forClass(BillDetailRequest.class);
        verify(workflowUtil).prepareWorkflowRequestForBillDetail(wfCaptor.capture());
        assertEquals("FAILED", wfCaptor.getValue().getWorkflow().getAction());
        assertEquals("No payment provider configured", wfCaptor.getValue().getWorkflow().getComments());

        BillDetail detail = taskRequest.getBill().getBillDetails().get(0);
        assertEquals(Status.VERIFICATION_FAILED, detail.getStatus());

        ArgumentCaptor<List<BillDetail>> updateCaptor = ArgumentCaptor.forClass(List.class);
        verify(billDetailService).update(updateCaptor.capture(), eq(TENANT_ID));
        BillDetail persisted = updateCaptor.getValue().get(0);
        assertEquals(Status.VERIFICATION_FAILED, persisted.getStatus());
        @SuppressWarnings("unchecked")
        Map<String, Object> additionalDetails = (Map<String, Object>) persisted.getAdditionalDetails();
        ErrorDetails errorDetails = (ErrorDetails) additionalDetails.get("errorDetails");
        assertEquals("No payment provider configured", errorDetails.getReasonForFailure());

        verify(billAggregationService).checkAndAggregateBill(
                eq(BILL_ID), eq(TENANT_ID), eq(POLL_PHASE_VERIFICATION), any());
        verify(mtnUtil, never()).isMsisdnActive(any());
    }

    @Test
    public void verify_invalidProvider_inProgressDetail_recordsProviderInReason() {
        TaskRequest taskRequest = setupVerifyTask(Status.VERIFICATION_IN_PROGRESS, "ORANGE");
        when(workflowUtil.callWorkFlow(any(), any(BillDetailRequest.class)))
                .thenReturn(buildWfState("VERIFICATION_FAILED"));

        mtnService.verify(taskRequest);

        ArgumentCaptor<BillDetailRequest> wfCaptor = ArgumentCaptor.forClass(BillDetailRequest.class);
        verify(workflowUtil).prepareWorkflowRequestForBillDetail(wfCaptor.capture());
        assertEquals("FAILED", wfCaptor.getValue().getWorkflow().getAction());
        assertEquals("Invalid payment provider configured: ORANGE",
                wfCaptor.getValue().getWorkflow().getComments());
        assertEquals(Status.VERIFICATION_FAILED,
                taskRequest.getBill().getBillDetails().get(0).getStatus());
    }

    @Test
    public void verify_invalidProvider_settledDetail_skipsWithoutWfCall() {
        TaskRequest taskRequest = setupVerifyTask(Status.VERIFIED, "ORANGE");

        mtnService.verify(taskRequest);

        verify(workflowUtil, never()).callWorkFlow(any(), any(BillDetailRequest.class));
        assertEquals(Status.VERIFIED, taskRequest.getBill().getBillDetails().get(0).getStatus());
    }

    @Test
    public void verify_blankProvider_pendingDetail_verifiesThenFails() {
        TaskRequest taskRequest = setupVerifyTask(Status.PENDING_VERIFICATION, "");
        when(workflowUtil.callWorkFlow(any(), any(BillDetailRequest.class)))
                .thenReturn(buildWfState("VERIFICATION_IN_PROGRESS"))
                .thenReturn(buildWfState("VERIFICATION_FAILED"));

        mtnService.verify(taskRequest);

        ArgumentCaptor<BillDetailRequest> wfCaptor = ArgumentCaptor.forClass(BillDetailRequest.class);
        verify(workflowUtil, times(2)).prepareWorkflowRequestForBillDetail(wfCaptor.capture());
        List<BillDetailRequest> calls = wfCaptor.getAllValues();
        assertEquals("VERIFY", calls.get(0).getWorkflow().getAction());
        assertEquals("FAILED", calls.get(1).getWorkflow().getAction());
        assertEquals(Status.VERIFICATION_FAILED,
                taskRequest.getBill().getBillDetails().get(0).getStatus());
    }
}
