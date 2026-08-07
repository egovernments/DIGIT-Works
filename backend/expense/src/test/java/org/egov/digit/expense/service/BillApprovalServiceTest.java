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

    private static final byte[] PNG_BYTES = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x01};
    private static final byte[] JPEG_BYTES = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, 0x00, 0x01};

    @BeforeEach
    void setUp() {
        when(config.getSignatureMaxSizeBytes()).thenReturn(5L * 1024 * 1024);
        when(config.getBillApprovalCreateTopic()).thenReturn("expense-bill-approval-create");
        when(filestoreUtil.downloadFile(any(), any())).thenReturn(PNG_BYTES);
    }

    private ApprovalSignature signatureWith(String fileStoreId) {
        return ApprovalSignature.builder()
                .printedName("Jane Doe")
                .signatureMethod(SignatureMethod.UPLOADED)
                .signatureFileStoreId(fileStoreId)
                .build();
    }

    // ── signature image validation (on the approval path) ─────────────────────
    //
    // The image is uploaded straight to filestore by the client, so these checks run when
    // the approval is submitted — the first point at which the server holds the bytes, and
    // a path that is already authenticated and role-checked.

    @Test
    void validateApproval_emptySignatureImage_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = signatureWith("fs-001");
        when(filestoreUtil.downloadFile("fs-001", TENANT_ID)).thenReturn(new byte[0]);

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_EMPTY", ex.getCode());
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    @Test
    void validateApproval_signatureImageTooLarge_throws() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        byte[] oversized = new byte[6 * 1024 * 1024];
        oversized[0] = (byte) 0x89; oversized[1] = 0x50; oversized[2] = 0x4E; oversized[3] = 0x47;
        ApprovalSignature signature = signatureWith("fs-001");
        when(filestoreUtil.downloadFile("fs-001", TENANT_ID)).thenReturn(oversized);

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_TOO_LARGE", ex.getCode());
    }

    @Test
    void validateApproval_signatureImageNotPngOrJpeg_throws() {
        // A file that is neither PNG nor JPEG (e.g. a PDF or renamed binary). Format is decided
        // by magic bytes, so nothing the client claims about the file can get past this.
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        ApprovalSignature signature = signatureWith("fs-001");
        when(filestoreUtil.downloadFile("fs-001", TENANT_ID)).thenReturn(new byte[]{1, 2, 3, 4, 5});

        CustomException ex = assertThrows(CustomException.class,
                () -> billApprovalService.validateApproval(bill, bill, signature, requestInfo));
        assertEquals("EG_EXPENSE_SIGNATURE_FILE_INVALID_FORMAT", ex.getCode());
        verify(expenseProducer, never()).push(any(), any(), any());
    }

    @Test
    void validateApproval_validJpegSignature_passes() {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1);
        bill.setId("bill-001");
        RequestInfo requestInfo = buildRequestInfo("PAYMENT_APPROVER");
        when(filestoreUtil.downloadFile("fs-002", TENANT_ID)).thenReturn(JPEG_BYTES);

        BillApproval approval = billApprovalService.validateApproval(
                bill, bill, signatureWith("fs-002"), requestInfo);

        assertEquals("fs-002", approval.getSignatureFileStoreId());
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
        // Authorisation must be settled before the filestore is touched, so an unauthorised
        // caller can never make the service fetch a file on their behalf.
        verify(filestoreUtil, never()).downloadFile(any(), any());
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
