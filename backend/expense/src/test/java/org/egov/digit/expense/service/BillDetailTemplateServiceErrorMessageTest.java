package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.BillDetailExcelGenerator;
import org.egov.digit.expense.util.BillDetailExcelParser;
import org.egov.digit.expense.util.FilestoreUtil;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.*;
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
import static org.egov.digit.expense.config.Constants.ERR_UNAUTHORIZED;
import static org.egov.digit.expense.config.Constants.ERR_INVALID_BILL_STATUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailTemplateServiceErrorMessageTest {

    @Mock private BillRepository billRepository;
    @Mock private BillDetailExcelGenerator excelGenerator;
    @Mock private BillDetailExcelParser excelParser;
    @Mock private BillService billService;
    @Mock private FilestoreUtil filestoreUtil;
    @Mock private MdmsUtil mdmsUtil;
    @Mock private ObjectMapper objectMapper;

    private BillDetailTemplateService service;

    private static final List<String> INTERNAL_NAMES =
            List.of("PAYMENT_EDITOR", "PAYMENT_REVIEWER", "PAYMENT_APPROVER",
                    "PENDING_VERIFICATION", "VERIFICATION_FAILED", "UNDER_REVIEW",
                    "PARTIALLY_VERIFIED", "REVIEWED");

    @BeforeEach
    public void setUp() {
        service = new BillDetailTemplateService(billRepository, excelGenerator, excelParser,
                billService, filestoreUtil, mdmsUtil, objectMapper);
    }

    // ── ERR_UNAUTHORIZED ──────────────────────────────────────────────────────

    @Test
    public void generateTemplate_unauthorized_messageHasNoRoleName() {
        Bill bill = buildBillWithDetails(Status.PENDING_VERIFICATION,
                List.of(buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION)));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        BillTemplateRequest req = BillTemplateRequest.builder()
                .requestInfo(buildRequestInfo("SOME_OTHER_ROLE"))
                .billId(BILL_ID).tenantId(TENANT_ID).build();

        CustomException ex = assertThrows(CustomException.class,
                () -> service.generateTemplateBytes(req));

        assertEquals(ERR_UNAUTHORIZED, ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    @Test
    public void uploadTemplate_unauthorized_messageHasNoRoleName() {
        Bill bill = buildBillWithDetails(Status.PENDING_VERIFICATION,
                List.of(buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION)));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        BillTemplateUploadRequest req = BillTemplateUploadRequest.builder()
                .requestInfo(buildRequestInfo("SOME_OTHER_ROLE"))
                .billId(BILL_ID).tenantId(TENANT_ID).filestoreId("fs-001").build();

        CustomException ex = assertThrows(CustomException.class,
                () -> service.processUpload(req));

        assertEquals(ERR_UNAUTHORIZED, ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── ERR_INVALID_BILL_STATUS ───────────────────────────────────────────────

    @Test
    public void generateTemplate_invalidBillStatus_messageHasNoRoleOrStatusName() {
        // REVIEWED status is not allowed for either editor or reviewer template generation
        Bill bill = buildBillWithDetails(Status.REVIEWED,
                List.of(buildDetail(DETAIL_ID_1, Status.REVIEWED)));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        BillTemplateRequest req = BillTemplateRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .billId(BILL_ID).tenantId(TENANT_ID).build();

        CustomException ex = assertThrows(CustomException.class,
                () -> service.generateTemplateBytes(req));

        assertEquals(ERR_INVALID_BILL_STATUS, ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    @Test
    public void uploadTemplate_invalidBillStatus_messageHasNoRoleOrStatusName() {
        Bill bill = buildBillWithDetails(Status.REVIEWED,
                List.of(buildDetail(DETAIL_ID_1, Status.REVIEWED)));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        BillTemplateUploadRequest req = BillTemplateUploadRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_REVIEWER"))
                .billId(BILL_ID).tenantId(TENANT_ID).filestoreId("fs-001").build();

        CustomException ex = assertThrows(CustomException.class,
                () -> service.processUpload(req));

        assertEquals(ERR_INVALID_BILL_STATUS, ex.getCode());
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
