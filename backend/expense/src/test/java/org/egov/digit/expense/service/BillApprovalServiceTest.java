package org.egov.digit.expense.service;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillApprovalRepository;
import org.egov.digit.expense.util.FilestoreUtil;
import org.egov.digit.expense.web.models.ApprovalSignature;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillApproval;
import org.egov.digit.expense.web.models.BillApprovalRequest;
import org.egov.digit.expense.web.models.enums.SignatureMethod;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BillApprovalServiceTest {

    @Mock private BillApprovalRepository billApprovalRepository;
    @Mock private FilestoreUtil filestoreUtil;
    @Mock private ExpenseProducer expenseProducer;
    @Mock private Configuration config;

    @InjectMocks
    private BillApprovalService billApprovalService;

    @BeforeEach
    void setUp() {
        when(config.getSignatureMaxSizeBytes()).thenReturn(5L * 1024 * 1024);
        when(config.getBillApprovalCreateTopic()).thenReturn("expense-bill-approval-create");
    }

    // ── uploadSignature ──────────────────────────────────────────────────────

    @Test
    void uploadSignature_emptyFile_throws() {
        MockMultipartFile file = new MockMultipartFile("file", "sig.png", "image/png", new byte[0]);

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.uploadSignature(file, TENANT_ID));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_EMPTY", ex.getCode());
        verify(filestoreUtil, never()).upload(any(), any(), any());
    }

    @Test
    void uploadSignature_tooLarge_throws() {
        byte[] bytes = new byte[6 * 1024 * 1024];
        MockMultipartFile file = new MockMultipartFile("file", "sig.png", "image/png", bytes);

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.uploadSignature(file, TENANT_ID));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_TOO_LARGE", ex.getCode());
    }

    @Test
    void uploadSignature_invalidFormat_throws() {
        MockMultipartFile file = new MockMultipartFile("file", "sig.pdf", "application/pdf", new byte[]{1, 2, 3});

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.uploadSignature(file, TENANT_ID));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_INVALID_FORMAT", ex.getCode());
    }

    @Test
    void uploadSignature_validPng_uploadsAndReturnsFileStoreId() {
        byte[] pngBytes = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x01};
        MockMultipartFile file = new MockMultipartFile("file", "sig.png", "image/png", pngBytes);
        when(filestoreUtil.upload(any(), eq(TENANT_ID), eq("sig.png"))).thenReturn("fs-001");

        String fileStoreId = billApprovalService.uploadSignature(file, TENANT_ID);

        assertEquals("fs-001", fileStoreId);
        verify(filestoreUtil).upload(any(), eq(TENANT_ID), eq("sig.png"));
    }

    @Test
    void uploadSignature_validJpeg_uploadsAndReturnsFileStoreId() {
        byte[] jpegBytes = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, 0x00, 0x01};
        MockMultipartFile file = new MockMultipartFile("file", "sig.jpg", "image/jpeg", jpegBytes);
        when(filestoreUtil.upload(any(), eq(TENANT_ID), eq("sig.jpg"))).thenReturn("fs-002");

        String fileStoreId = billApprovalService.uploadSignature(file, TENANT_ID);

        assertEquals("fs-002", fileStoreId);
    }

    @Test
    void uploadSignature_contentTypeSpoofed_magicBytesMismatch_throws() {
        // Content-Type header claims PNG, but the actual bytes are not a PNG (e.g. a renamed
        // executable or arbitrary binary) — the magic-byte check must catch what the spoofable
        // Content-Type header alone would miss.
        MockMultipartFile file = new MockMultipartFile("file", "sig.png", "image/png", new byte[]{1, 2, 3, 4, 5});

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.uploadSignature(file, TENANT_ID));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_CONTENT_MISMATCH", ex.getCode());
        verify(filestoreUtil, never()).upload(any(), any(), any());
    }

    // ── validateApproval / recordApproval ────────────────────────────────────

    @Test
    void validateApproval_nullSignature_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, null, requestInfo));
        assertEquals("EG_EXPENSE_APPROVAL_SIGNATURE_REQUIRED", ex.getCode());
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    @Test
    void validateApproval_blankPrintedName_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("   ")
                .signatureMethod(SignatureMethod.DRAWN)
                .signatureFileStoreId("fs-001")
                .build();

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_APPROVAL_PRINTED_NAME_REQUIRED", ex.getCode());
    }

    @Test
    void validateApproval_missingSignatureFile_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("Jane Doe")
                .signatureMethod(SignatureMethod.UPLOADED)
                .signatureFileStoreId(null)
                .build();

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_APPROVAL_SIGNATURE_FILE_REQUIRED", ex.getCode());
    }

    @Test
    void validateApproval_signatureFileNotFoundInTenant_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("Jane Doe")
                .signatureMethod(SignatureMethod.UPLOADED)
                .signatureFileStoreId("foreign-fs-id")
                .build();
        // Simulates a caller submitting a fileStoreId that was never uploaded for this tenant
        // (e.g. belongs to another tenant/module, or was never uploaded at all).
        doThrow(new CustomException("FILESTORE_DOWNLOAD_FAILED", "not found"))
                .when(filestoreUtil).downloadFile("foreign-fs-id", TENANT_ID);

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_APPROVAL_SIGNATURE_FILE_NOT_FOUND", ex.getCode());
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    @Test
    void validateApproval_notPaymentApprover_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_REVIEWER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("Jane Doe")
                .signatureMethod(SignatureMethod.DRAWN)
                .signatureFileStoreId("fs-001")
                .build();

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_APPROVAL_UNAUTHORIZED", ex.getCode());
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    @Test
    void validateApproval_doesNotPushUntilRecordApprovalIsCalled() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        bill.setId("bill-001");
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("  Jane Doe  ")
                .signatureMethod(SignatureMethod.DRAWN)
                .signatureFileStoreId("fs-001")
                .build();

        BillApproval approval = billApprovalService.validateApproval(bill, bill, signature, requestInfo);

        // Validation alone must never push the audit record — only a subsequent, explicit
        // recordApproval() call (made after payment initiation succeeds) may do that.
        verify(expenseProducer, never()).push(any(), any(), any());
        verify(filestoreUtil).downloadFile("fs-001", TENANT_ID);

        assertEquals("bill-001", approval.getBillId());
        assertEquals("Jane Doe", approval.getApproverName());
        assertEquals(USER_UUID, approval.getApproverUuid());
        assertEquals("PAYMENT_APPROVER", approval.getRole());
        assertEquals(SignatureMethod.DRAWN, approval.getSignatureMethod());
        assertEquals("fs-001", approval.getSignatureFileStoreId());
        assertNotNull(approval.getId());
        assertNotNull(approval.getApprovedTime());
    }

    @Test
    void recordApproval_pushesBillApprovalRequest() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        bill.setId("bill-001");
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = ApprovalSignature.builder()
                .printedName("Jane Doe")
                .signatureMethod(SignatureMethod.DRAWN)
                .signatureFileStoreId("fs-001")
                .build();
        BillApproval approval = billApprovalService.validateApproval(bill, bill, signature, requestInfo);

        billApprovalService.recordApproval(approval, requestInfo);

        org.mockito.ArgumentCaptor<Object> captor = org.mockito.ArgumentCaptor.forClass(Object.class);
        verify(expenseProducer).push(eq(TENANT_ID), eq("expense-bill-approval-create"), captor.capture());

        BillApprovalRequest pushed = (BillApprovalRequest) captor.getValue();
        assertEquals(approval, pushed.getBillApproval());
    }

    // ── enrichBillsWithApprovals ─────────────────────────────────────────────

    @Test
    void enrichBillsWithApprovals_groupsByBillId() {
        Bill bill1 = buildBill(Status.REVIEWED, Status.REVIEWED, 1); bill1.setId("bill-1");
        Bill bill2 = buildBill(Status.REVIEWED, Status.REVIEWED, 1); bill2.setId("bill-2");

        BillApproval approvalForBill1 = BillApproval.builder().id("a1").billId("bill-1").build();
        when(billApprovalRepository.search(any())).thenReturn(List.of(approvalForBill1));

        billApprovalService.enrichBillsWithApprovals(List.of(bill1, bill2), TENANT_ID);

        assertEquals(1, bill1.getApprovals().size());
        assertEquals("a1", bill1.getApprovals().get(0).getId());
        assertTrue(bill2.getApprovals().isEmpty());
    }
}
