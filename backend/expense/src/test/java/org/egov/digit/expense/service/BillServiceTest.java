package org.egov.digit.expense.service;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.CalculatorUtil;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillServiceTest {

    @Mock private ExpenseProducer expenseProducer;
    @Mock private Configuration config;
    @Mock private BillValidator validator;
    @Mock private WorkflowUtil workflowUtil;
    @Mock private BillRepository billRepository;
    @Mock private EnrichmentUtil enrichmentUtil;
    @Mock private ResponseInfoFactory responseInfoFactory;
    @Mock private NotificationService notificationService;
    @Mock private CalculatorUtil calculatorUtil;
    @Mock private PaymentWorkflowService paymentWorkflowService;
    @Mock private BillDetailService billDetailService;
    @Mock private BillCacheService billCacheService;
    @Mock private BillDetailCacheService billDetailCacheService;
    @Mock private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    public void setUp() {
        when(config.getBillCreateTopic()).thenReturn("expense-bill-create");
        when(config.getBillUpdateTopic()).thenReturn("expense-bill-update");
        when(config.isBillBreakdownEnabled()).thenReturn(false);
        when(config.getBillBusinessService()).thenReturn(BUSINESS_SVC);
        when(responseInfoFactory.createResponseInfoFromRequestInfo(any(), anyBoolean()))
                .thenReturn(ResponseInfo.builder().build());
        when(validator.isWorkflowActiveForBusinessService(any())).thenReturn(true);
        when(workflowUtil.callWorkFlow(any(), any(BillRequest.class)))
                .thenReturn(buildWfState("PENDING_VERIFICATION"));
        when(validator.validateUpdateRequest(any())).thenReturn(
                List.of(buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1)));
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    public void create_validBill_enrichesAndPushes() {
        Bill bill = buildBill(Status.ACTIVE, Status.ACTIVE, 2);
        BillRequest req = buildBillRequest(bill);

        BillResponse response = billService.create(req);

        verify(validator).validateCreateRequest(req);
        verify(enrichmentUtil).encrichBillForCreate(req);
        verify(expenseProducer).push(eq(TENANT_ID), eq("expense-bill-create"), any());
        assertNotNull(response.getBills());
    }

    @Test
    public void create_validBill_callsCreateBillDetailProcessInstances() {
        Bill bill = buildBill(Status.ACTIVE, Status.ACTIVE, 2);
        BillRequest req = buildBillRequest(bill);

        billService.create(req);

        verify(paymentWorkflowService).createBillDetailProcessInstances(req);
    }

    @Test
    public void create_validatorThrows_noPush() {
        doThrow(new CustomException("ERR_DUPLICATE_BILL", "duplicate"))
                .when(validator).validateCreateRequest(any());

        assertThrows(CustomException.class,
                () -> billService.create(buildBillRequest(buildBill(Status.ACTIVE, Status.ACTIVE, 1))));
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    // ── bulkUpdateStatus ─────────────────────────────────────────────────────

    @Test
    public void bulkUpdate_3ValidBills_allSucceed() {
        Bill b1 = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); b1.setId("b1");
        Bill b2 = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); b2.setId("b2");
        Bill b3 = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); b3.setId("b3");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(b1, b2, b3));
        when(validator.validateUpdateRequest(any())).thenAnswer(inv ->
                List.of(((BillRequest) inv.getArgument(0)).getBill()));

        BulkBillStatusUpdateResponse response = billService.bulkUpdateStatus(
                buildBulkRequest("VERIFICATION_IN_PROGRESS", "VERIFY", "b1", "b2", "b3"));

        assertEquals(3, response.getBills().size());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void bulkUpdate_1NotFound_1SuccessAnd1Error() {
        Bill b1 = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); b1.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(b1));
        when(validator.validateUpdateRequest(any())).thenAnswer(inv ->
                List.of(((BillRequest) inv.getArgument(0)).getBill()));

        BulkBillStatusUpdateResponse response = billService.bulkUpdateStatus(
                buildBulkRequest("VERIFICATION_IN_PROGRESS", "VERIFY", "b1", "b2"));

        assertEquals(1, response.getBills().size());
        assertEquals(1, response.getErrors().size());
        assertEquals("b2", response.getErrors().get(0).getBillId());
    }

    @Test
    public void bulkUpdate_validationErrors_returnsImmediately() {
        BulkUpdateError err = BulkUpdateError.builder().code("ERR_BULK_STATUS_EMPTY").build();
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(List.of(err));

        BulkBillStatusUpdateResponse response = billService.bulkUpdateStatus(
                buildBulkRequest("VERIFIED", "VERIFY"));

        assertFalse(response.getErrors().isEmpty());
        verify(validator, never()).getBillsByIds(any(), any(), any());
    }

    @Test
    public void bulkUpdate_verifyAction_routesToVerifyBill() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); bill.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(bill));
        when(validator.validateUpdateRequest(any())).thenReturn(List.of(bill));

        billService.bulkUpdateStatus(buildBulkRequest("VERIFICATION_IN_PROGRESS", Actions.VERIFY.toString(), "b1"));

        verify(paymentWorkflowService).verifyBill(any(BillRequest.class));
        verify(paymentWorkflowService, never()).sendForReview(any());
    }

    @Test
    public void bulkUpdate_sendForReviewAction_routesToSendForReview() {
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 1); bill.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(bill));
        when(validator.validateUpdateRequest(any())).thenReturn(List.of(bill));

        billService.bulkUpdateStatus(buildBulkRequest("SENDING_FOR_REVIEW", Actions.SEND_FOR_REVIEW.toString(), "b1"));

        verify(paymentWorkflowService).sendForReview(any(BillRequest.class), any());
    }

    @Test
    public void bulkUpdate_paymentInitiation_paymentFailedBill_routesToRetryPayment() {
        Bill bill = buildBill(Status.PAYMENT_FAILED, Status.PAYMENT_FAILED, 1); bill.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(bill));
        when(validator.validateUpdateRequest(any())).thenReturn(List.of(bill));

        billService.bulkUpdateStatus(buildBulkRequest("PAYMENT_IN_PROGRESS", Actions.PAYMENT_INITIATION.toString(), "b1"));

        verify(paymentWorkflowService).retryPayment(any(BillRequest.class));
        verify(paymentWorkflowService, never()).initiatePayment(any());
    }

    @Test
    public void bulkUpdate_paymentInitiation_reviewedBill_routesToInitiatePayment() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1); bill.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(bill));
        when(validator.validateUpdateRequest(any())).thenReturn(List.of(bill));

        billService.bulkUpdateStatus(buildBulkRequest("PAYMENT_IN_PROGRESS", Actions.PAYMENT_INITIATION.toString(), "b1"));

        verify(paymentWorkflowService).initiatePayment(any(BillRequest.class));
        verify(paymentWorkflowService, never()).retryPayment(any());
    }

    @Test
    public void bulkUpdate_serviceThrows_capturedAsError() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); bill.setId("b1");
        when(validator.validateBulkStatusUpdateRequest(any())).thenReturn(Collections.emptyList());
        when(validator.getBillsByIds(any(), any(), any())).thenReturn(List.of(bill));
        when(validator.validateUpdateRequest(any())).thenReturn(List.of(bill));
        doThrow(new RuntimeException("WF error")).when(paymentWorkflowService).verifyBill(any());

        BulkBillStatusUpdateResponse response = billService.bulkUpdateStatus(
                buildBulkRequest("VERIFICATION_IN_PROGRESS", Actions.VERIFY.toString(), "b1"));

        assertEquals(1, response.getErrors().size());
        assertEquals("EG_EXPENSE_BILL_STATUS_UPDATE_FAILED", response.getErrors().get(0).getCode());
    }
}
