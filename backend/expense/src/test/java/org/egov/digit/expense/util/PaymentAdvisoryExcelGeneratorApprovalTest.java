package org.egov.digit.expense.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillApproval;
import org.egov.digit.expense.web.models.enums.ApprovalStatus;
import org.egov.digit.expense.web.models.enums.SignatureMethod;
import org.egov.digit.expense.web.models.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.TENANT_ID;
import static org.egov.digit.expense.TestDataBuilder.buildBill;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * The CR requires the approver's printed name, role, status, timestamp and signature to appear
 * wherever approval details are shown — including the generated payment voucher, which is the
 * only export/print artefact this service produces.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentAdvisoryExcelGeneratorApprovalTest {

    private static final byte[] PNG_BYTES =
            {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x01};

    @Mock private LocalizationUtil localizationUtil;
    @Mock private Configuration config;
    @Mock private FilestoreUtil filestoreUtil;

    @InjectMocks private PaymentAdvisoryExcelGenerator generator;

    private BillApproval approval() {
        return BillApproval.builder()
                .approverName("Jane Doe")
                .role("PAYMENT_APPROVER")
                .approvalStatus(ApprovalStatus.APPROVED)
                .signatureMethod(SignatureMethod.DRAWN)
                .signatureFileStoreId("fs-sig-1")
                .approvedTime(1754000000000L)
                .build();
    }

    private String sheetText(XSSFSheet sheet) {
        StringBuilder sb = new StringBuilder();
        for (Row row : sheet) {
            for (Cell cell : row) {
                try {
                    sb.append(cell.getStringCellValue()).append('|');
                } catch (Exception ignored) {
                    // numeric/other cells are irrelevant to the approval block
                }
            }
        }
        return sb.toString();
    }

    @Test
    void generate_rendersApproverNameRoleStatusAndTimestampInVoucher() throws Exception {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 1);
        bill.setApprovals(List.of(approval()));
        when(filestoreUtil.downloadFile("fs-sig-1", TENANT_ID)).thenReturn(PNG_BYTES);

        byte[] out = generator.generate(bill, RequestInfo.builder().build());

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(out))) {
            String text = sheetText(wb.getSheetAt(0));
            assertTrue(text.contains("Jane Doe"), "printed name missing from voucher");
            assertTrue(text.contains("PAYMENT_APPROVER"), "role missing from voucher");
            assertTrue(text.contains("APPROVED"), "approval status missing from voucher");
            assertTrue(text.contains("APPROVAL DATE & TIME"), "approval time label missing");
        }
    }

    @Test
    void generate_embedsSignatureImageInVoucher() throws Exception {
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 1);
        bill.setApprovals(List.of(approval()));
        when(filestoreUtil.downloadFile("fs-sig-1", TENANT_ID)).thenReturn(PNG_BYTES);

        byte[] out = generator.generate(bill, RequestInfo.builder().build());

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(out))) {
            XSSFDrawing drawing = wb.getSheetAt(0).getDrawingPatriarch();
            assertNotNull(drawing, "no drawing created — signature image was not embedded");
            assertEquals(1, drawing.getShapes().size());
        }
    }

    @Test
    void generate_unreadableSignatureImage_stillRendersNameAndDoesNotFail() throws Exception {
        // A missing or unreadable image must not take the whole voucher down — the printed
        // name, role, status and timestamp still carry the approval record.
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 1);
        bill.setApprovals(List.of(approval()));
        when(filestoreUtil.downloadFile("fs-sig-1", TENANT_ID))
                .thenThrow(new RuntimeException("filestore unavailable"));

        byte[] out = generator.generate(bill, RequestInfo.builder().build());

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(out))) {
            assertTrue(sheetText(wb.getSheetAt(0)).contains("Jane Doe"));
        }
    }

    @Test
    void generate_billApprovedBeforeSignatureCapture_omitsBlockAndStillGenerates() throws Exception {
        // Backward compatibility: bills approved before this feature existed have no approvals,
        // and their vouchers must render exactly as they did before.
        Bill bill = buildBill(Status.PAYMENT_IN_PROGRESS, Status.PAYMENT_IN_PROGRESS, 1);
        bill.setApprovals(null);

        byte[] out = generator.generate(bill, RequestInfo.builder().build());

        assertNotNull(out);
        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(out))) {
            String text = sheetText(wb.getSheetAt(0));
            assertFalse(text.contains("APPROVED BY"), "approval block must be omitted");
            assertNull(wb.getSheetAt(0).getDrawingPatriarch());
        }
    }
}
