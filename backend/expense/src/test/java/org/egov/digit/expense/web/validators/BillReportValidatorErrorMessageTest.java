package org.egov.digit.expense.web.validators;

import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.BillReportRepository;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class BillReportValidatorErrorMessageTest {

    @Mock private BillReportRepository reportRepository;
    @Mock private BillRepository billRepository;

    private BillReportValidator validator;

    private static final List<String> INTERNAL_NAMES =
            List.of("PAYMENT_EDITOR", "PAYMENT_REVIEWER", "PAYMENT_APPROVER",
                    "PENDING_VERIFICATION", "UNDER_REVIEW", "REVIEWED",
                    "VERIFICATION_FAILED", "PAYMENT_IN_PROGRESS");

    @BeforeEach
    public void setUp() {
        validator = new BillReportValidator(reportRepository, billRepository);
    }

    // ── EG_EXPENSE_REPORT_UNAUTHORIZED ────────────────────────────────────────

    @Test
    public void reportUnauthorized_messageHasNoRoleName() {
        // Non-approver user requesting Payment Advisory Report
        BillReport report = BillReport.builder()
                .billIds(List.of(BILL_ID))
                .tenantId(TENANT_ID)
                .type(ReportType.PAYMENT_ADVISORY_EXCEL)
                .build();
        BillReportRequest req = BillReportRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR")) // not PAYMENT_APPROVER
                .billReport(report)
                .build();
        when(reportRepository.getLatestReport(any(), any(), any())).thenReturn(null);

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateGenerateRequest(req));

        assertEquals("EG_EXPENSE_REPORT_UNAUTHORIZED", ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── EG_EXPENSE_REPORT_BILL_NOT_REVIEWED ───────────────────────────────────

    @Test
    public void billNotReviewed_messageHasNoStatusName() {
        // Bill is in PENDING_VERIFICATION, not REVIEWED
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        bill.setId(BILL_ID);
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));
        when(reportRepository.getLatestReport(any(), any(), any())).thenReturn(null);

        BillReport report = BillReport.builder()
                .billIds(List.of(BILL_ID))
                .tenantId(TENANT_ID)
                .type(ReportType.PAYMENT_ADVISORY_EXCEL)
                .build();
        BillReportRequest req = BillReportRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_APPROVER"))
                .billReport(report)
                .build();

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateGenerateRequest(req));

        assertEquals("EG_EXPENSE_REPORT_BILL_NOT_REVIEWED", ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private void assertNoInternalNames(String message) {
        if (message == null) return;
        for (String name : INTERNAL_NAMES) {
            assertFalse(message.contains(name),
                    "Message must not expose internal name '" + name + "': " + message);
        }
    }
}
