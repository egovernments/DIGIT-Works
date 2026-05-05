package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import org.egov.digit.expense.web.models.BillDetailRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Unit tests for BankPaymentService.validateBankFields — verifies alignment
 * with Worker Registry payment provider validation rules.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BankPaymentServiceValidateFieldsTest {

    @Mock private Configuration config;
    @Mock private BillRepository billRepository;
    @Mock private WorkflowUtil workflowUtil;
    @Mock private ExpenseProducer expenseProducer;
    @Mock private BillCacheService billCacheService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private BankPaymentService service;

    @BeforeEach
    void setUp() {
        service = new BankPaymentService(config, billRepository, workflowUtil, expenseProducer, billCacheService, objectMapper);
        when(billRepository.search(any(), any(Boolean.class))).thenReturn(List.of(buildBankBill(Status.PENDING_VERIFICATION)));
        when(config.getBillDetailBusinessService()).thenReturn("PAYMENTS.BILLDETAILS");
        when(config.getBillUpdateTopic()).thenReturn("expense-bill-update");
        when(config.getTaskUpdateTopic()).thenReturn("expense-task-update");
    }

    // ── payeeName ──────────────────────────────────────────────────────────────

    @Test
    void verify_missingPayeeName_setsVerificationFailed() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setPayeeName(null);
        stubWorkflowToInProgress(bill);

        String reason = runVerifyAndGetReason(bill);
        assertEquals(Constants.MISSING_PAYEE_NAME_ERR_CODE, reason);
    }

    @Test
    void verify_blankPayeeName_setsVerificationFailed() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setPayeeName("   ");
        stubWorkflowToInProgress(bill);

        String reason = runVerifyAndGetReason(bill);
        assertEquals(Constants.MISSING_PAYEE_NAME_ERR_CODE, reason);
    }

    // ── bankAccount ────────────────────────────────────────────────────────────

    @Test
    void verify_missingBankAccount_setsVerificationFailed() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankAccount(null);
        stubWorkflowToInProgress(bill);

        String reason = runVerifyAndGetReason(bill);
        assertEquals(Constants.MISSING_BANK_ACCOUNT_ERR_CODE, reason);
    }

    @Test
    void verify_bankAccountTooShort_setsInvalidBankAccount() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankAccount("12345");
        stubWorkflowToInProgress(bill);

        String reason = runVerifyAndGetReason(bill);
        assertEquals(Constants.INVALID_BANK_ACCOUNT_ERR_CODE, reason);
    }

    @Test
    void verify_bankAccountNonDigits_setsInvalidBankAccount() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankAccount("ABC1234567");
        stubWorkflowToInProgress(bill);

        String reason = runVerifyAndGetReason(bill);
        assertEquals(Constants.INVALID_BANK_ACCOUNT_ERR_CODE, reason);
    }

    // ── bankCode ───────────────────────────────────────────────────────────────

    @Test
    void verify_bankCodeNull_setsInvalidBankCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode(null);
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BANK_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_bankCodeEmpty_setsInvalidBankCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode("");
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BANK_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_bankCodeThreeDigits_passes() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode("058");
        stubWorkflowToVerified(bill);

        assertNull(runVerifyAndGetReason(bill));
    }

    @Test
    void verify_bankCodeNineDigits_passes() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode("123456789");
        stubWorkflowToVerified(bill);

        assertNull(runVerifyAndGetReason(bill));
    }

    @Test
    void verify_bankCodeTwoDigits_setsInvalidBankCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode("12");
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BANK_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_bankCodeNonDigits_setsInvalidBankCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBankCode("ABC");
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BANK_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    // ── MTN provider ───────────────────────────────────────────────────────────

    @Test
    void verify_mtnMissingPayeeName_setsVerificationFailed() {
        Bill bill = buildMtnBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setPayeeName(null);
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.MISSING_PAYEE_NAME_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_mtnMissingPhoneNumber_setsVerificationFailed() {
        Bill bill = buildMtnBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setPayeePhoneNumber(null);
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.MISSING_PAYEE_PHONE_NUMBER_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_mtnAllFieldsValid_passes() {
        Bill bill = buildMtnBill(Status.PENDING_VERIFICATION);
        stubWorkflowToVerified(bill);

        assertNull(runVerifyAndGetReason(bill));
    }

    // ── unknown provider ───────────────────────────────────────────────────────

    @Test
    void verify_unknownProvider_skipsValidationAndVerifies() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setPaymentProvider("UNKNOWN");
        stubWorkflowToVerified(bill);

        // unknown provider → validatePaymentFields returns null → VERIFICATION_SUCCESS
        assertNull(runVerifyAndGetReason(bill));
    }

    // ── beneficiaryCode ────────────────────────────────────────────────────────

    @Test
    void verify_missingBeneficiaryCode_setsVerificationFailed() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBeneficiaryCode(null);
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.MISSING_BENEFICIARY_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_beneficiaryCodeNonAlphanumeric_setsInvalidBeneficiaryCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBeneficiaryCode("ABC@123");
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BENEFICIARY_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    @Test
    void verify_beneficiaryCodeTooLong_setsInvalidBeneficiaryCode() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        bill.getBillDetails().get(0).getPayee().setBeneficiaryCode("A".repeat(36));
        stubWorkflowToInProgress(bill);

        assertEquals(Constants.INVALID_BENEFICIARY_CODE_ERR_CODE, runVerifyAndGetReason(bill));
    }

    // ── additionalDetails preservation ────────────────────────────────────────

    @Test
    void verify_validationFailure_preservesExistingAdditionalDetails() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        BillDetail detail = bill.getBillDetails().get(0);
        Map<String, Object> existing = new HashMap<>();
        existing.put("noOfDaysWorked", 6);
        detail.setAdditionalDetails(existing);
        detail.getPayee().setBankAccount(null); // trigger MISSING_BANK_ACCOUNT
        stubWorkflowToInProgress(bill);

        runVerifyAndGetReason(bill);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) detail.getAdditionalDetails();
        assertNotNull(result);
        assertEquals(6, result.get("noOfDaysWorked"), "pre-existing field must be preserved on failure");
        assertNotNull(result.get("errorDetails"), "errorDetails must be set on failure");
    }

    @Test
    void verify_validationFailure_preservesExistingAdditionalDetailsAsJsonNode() {
        // BillRowMapper returns additionalDetails as JsonNode, not Map — this is the real runtime type
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        BillDetail detail = bill.getBillDetails().get(0);
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("noOfDaysWorked", 6);
        detail.setAdditionalDetails(jsonNode);
        detail.getPayee().setBankAccount(null); // trigger MISSING_BANK_ACCOUNT
        stubWorkflowToInProgress(bill);

        runVerifyAndGetReason(bill);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) detail.getAdditionalDetails();
        assertNotNull(result);
        assertEquals(6, result.get("noOfDaysWorked"), "noOfDaysWorked must survive when additionalDetails is a JsonNode");
        assertNotNull(result.get("errorDetails"), "errorDetails must be set on failure");
    }

    @Test
    void verify_validationSuccess_doesNotAddErrorDetails() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        BillDetail detail = bill.getBillDetails().get(0);
        Map<String, Object> existing = new HashMap<>();
        existing.put("noOfDaysWorked", 6);
        detail.setAdditionalDetails(existing);
        stubWorkflowToVerified(bill);

        runVerifyAndGetReason(bill);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) detail.getAdditionalDetails();
        // After success the errorDetails-cleared map has noOfDaysWorked; no errorDetails added
        assertTrue(result == null || !result.containsKey("errorDetails"),
                "errorDetails must not be present after successful verification");
    }

    // ── happy path ─────────────────────────────────────────────────────────────

    @Test
    void verify_allFieldsValid_returnsNull() {
        Bill bill = buildBankBill(Status.PENDING_VERIFICATION);
        stubWorkflowToVerified(bill);

        assertNull(runVerifyAndGetReason(bill));
    }

    // ─── helpers ───────────────────────────────────────────────────────────────

    private Bill buildMtnBill(Status status) {
        Party payee = Party.builder()
                .id("payee-mtn-001")
                .tenantId(TENANT_ID)
                .paymentProvider("MTN")
                .payeeName("Jane Doe")
                .payeePhoneNumber("07000000001")
                .build();

        BillDetail detail = BillDetail.builder()
                .id(DETAIL_ID_1)
                .tenantId(TENANT_ID)
                .payee(payee)
                .status(status)
                .build();

        return Bill.builder()
                .id(BILL_ID)
                .tenantId(TENANT_ID)
                .amountBreakup(new java.util.LinkedHashMap<>())
                .billDetails(new java.util.ArrayList<>(Collections.singletonList(detail)))
                .auditDetails(buildAuditDetails())
                .build();
    }

    private Bill buildBankBill(Status status) {
        Party payee = Party.builder()
                .id("payee-001")
                .tenantId(TENANT_ID)
                .paymentProvider("BANK")
                .payeeName("John Doe")
                .bankAccount("0123456789")
                .bankCode("058")
                .beneficiaryCode("ABC123")
                .build();

        BillDetail detail = BillDetail.builder()
                .id(DETAIL_ID_1)
                .tenantId(TENANT_ID)
                .payee(payee)
                .status(status)
                .build();

        return Bill.builder()
                .id(BILL_ID)
                .tenantId(TENANT_ID)
                .amountBreakup(new java.util.LinkedHashMap<>())
                .billDetails(new java.util.ArrayList<>(Collections.singletonList(detail)))
                .auditDetails(buildAuditDetails())
                .build();
    }

    /** Stubs workflow to transition detail to VERIFICATION_IN_PROGRESS (step 1 of verify). */
    private void stubWorkflowToInProgress(Bill bill) {
        org.egov.common.contract.workflow.State inProgress = new org.egov.common.contract.workflow.State();
        inProgress.setApplicationStatus(Status.VERIFICATION_IN_PROGRESS.toString());

        org.egov.common.contract.workflow.State failed = new org.egov.common.contract.workflow.State();
        failed.setApplicationStatus(Status.VERIFICATION_FAILED.toString());

        when(workflowUtil.callWorkFlow(any(), any(BillDetailRequest.class)))
                .thenReturn(inProgress)   // step 1: VERIFY action
                .thenReturn(failed);      // step 2: FAILED action
        when(workflowUtil.prepareWorkflowRequestForBillDetail(any())).thenReturn(null);
    }

    /** Stubs workflow to transition detail through to VERIFIED. */
    private void stubWorkflowToVerified(Bill bill) {
        org.egov.common.contract.workflow.State inProgress = new org.egov.common.contract.workflow.State();
        inProgress.setApplicationStatus(Status.VERIFICATION_IN_PROGRESS.toString());

        org.egov.common.contract.workflow.State verified = new org.egov.common.contract.workflow.State();
        verified.setApplicationStatus(Status.VERIFIED.toString());

        when(workflowUtil.callWorkFlow(any(), any(BillDetailRequest.class)))
                .thenReturn(inProgress)   // step 1: VERIFY action
                .thenReturn(verified);    // step 2: VERIFICATION_SUCCESS action
        when(workflowUtil.prepareWorkflowRequestForBillDetail(any())).thenReturn(null);
    }

    /**
     * Runs verify() and returns the reasonForFailure from additionalDetails,
     * or null if the detail was VERIFIED (no error).
     */
    @SuppressWarnings("unchecked")
    private String runVerifyAndGetReason(Bill bill) {
        BillRequest billRequest = BillRequest.builder()
                .bill(bill)
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .build();
        service.verify(billRequest);

        BillDetail detail = bill.getBillDetails().get(0);
        if (detail.getStatus() == Status.VERIFIED) return null;
        if (detail.getAdditionalDetails() instanceof Map<?, ?> ad) {
            Object errorDetails = ad.get("errorDetails");
            if (errorDetails instanceof Map<?, ?> ed) {
                return (String) ed.get("reasonForFailure");
            }
        }
        return null;
    }
}
