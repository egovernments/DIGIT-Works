package org.egov.digit.expense.web.validators;

import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.ERR_UNAUTHORIZED;
import static org.egov.digit.expense.config.Constants.ERR_DETAIL_STATUS_SKIPPED_EDITOR;
import static org.egov.digit.expense.config.Constants.ERR_DETAIL_STATUS_SKIPPED_REVIEWER;
import static org.egov.digit.expense.config.Constants.ERR_ROLE_STATUS_MISMATCH;
import static org.egov.digit.expense.config.Constants.ERR_FIELD_STRIPPED_EDITOR;
import static org.egov.digit.expense.config.Constants.ERR_FIELD_STRIPPED_REVIEWER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillValidatorErrorMessageTest {

    @Mock private MdmsUtil mdmsUtil;
    @Mock private Configuration configs;
    @Mock private BillRepository billRepository;

    @InjectMocks
    private BillValidator validator;

    private static final List<String> INTERNAL_ROLE_NAMES =
            List.of("PAYMENT_EDITOR", "PAYMENT_REVIEWER", "PAYMENT_APPROVER");
    private static final List<String> INTERNAL_STATUS_NAMES =
            List.of("PENDING_VERIFICATION", "VERIFICATION_FAILED", "UNDER_REVIEW",
                    "PARTIALLY_VERIFIED", "REVIEWED", "PAYMENT_IN_PROGRESS");

    private Bill buildEditorBill() {
        return buildBillWithDetails(Status.PENDING_VERIFICATION,
                List.of(buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION)));
    }

    private BillDetailUpdateRequest buildUpdateRequest(Bill bill, String... roleNames) {
        PartialBillDetail partial = PartialBillDetail.builder()
                .id(DETAIL_ID_1)
                .payee(Party.builder().id("p1").tenantId(TENANT_ID)
                        .type("INDIVIDUAL").identifier("IND-001")
                        .paymentProvider("MTN").payeePhoneNumber("0770000001").build())
                .build();
        return BillDetailUpdateRequest.builder()
                .requestInfo(buildRequestInfo(roleNames))
                .billId(BILL_ID)
                .tenantId(TENANT_ID)
                .billDetails(new ArrayList<>(List.of(partial)))
                .build();
    }

    // ── ERR_UNAUTHORIZED ──────────────────────────────────────────────────────

    @Test
    public void unauthorized_updateBillDetails_messageHasNoRoleName() {
        Bill bill = buildEditorBill();
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));
        BillDetailUpdateRequest req = buildUpdateRequest(bill, "SOME_OTHER_ROLE");

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateBillDetailUpdateRequest(req));

        assertEquals(ERR_UNAUTHORIZED, ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── ERR_DETAIL_STATUS_SKIPPED_EDITOR ─────────────────────────────────────

    @Test
    public void editorDetailStatusSkipped_usesEditorCode_messageHasNoRoleName() {
        BillDetail verifiedDetail = buildDetail(DETAIL_ID_1, Status.VERIFIED); // not allowed for editor
        Bill bill = buildBillWithDetails(Status.PENDING_VERIFICATION, List.of(verifiedDetail));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));
        BillDetailUpdateRequest req = buildUpdateRequest(bill, "PAYMENT_EDITOR");

        BillValidator.BillDetailValidationResult result = validator.validateBillDetailUpdateRequest(req);

        assertTrue(result.warnings.stream()
                .anyMatch(w -> ERR_DETAIL_STATUS_SKIPPED_EDITOR.equals(w.getCode())));
        result.warnings.stream()
                .filter(w -> ERR_DETAIL_STATUS_SKIPPED_EDITOR.equals(w.getCode()))
                .forEach(w -> assertNoInternalNames(w.getMessage()));
    }

    // ── ERR_DETAIL_STATUS_SKIPPED_REVIEWER ────────────────────────────────────

    @Test
    public void reviewerDetailStatusSkipped_usesReviewerCode_messageHasNoRoleName() {
        BillDetail verifiedDetail = buildDetail(DETAIL_ID_1, Status.VERIFIED); // not UNDER_REVIEW
        Bill bill = buildBillWithDetails(Status.UNDER_REVIEW, List.of(verifiedDetail));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));
        BillDetailUpdateRequest req = buildUpdateRequest(bill, "PAYMENT_REVIEWER");

        BillValidator.BillDetailValidationResult result = validator.validateBillDetailUpdateRequest(req);

        assertTrue(result.warnings.stream()
                .anyMatch(w -> ERR_DETAIL_STATUS_SKIPPED_REVIEWER.equals(w.getCode())));
        result.warnings.stream()
                .filter(w -> ERR_DETAIL_STATUS_SKIPPED_REVIEWER.equals(w.getCode()))
                .forEach(w -> assertNoInternalNames(w.getMessage()));
    }

    // ── ERR_DETAIL_STATUS_SKIPPED codes are distinct ──────────────────────────

    @Test
    public void editorAndReviewerDetailSkipped_haveDistinctErrorCodes() {
        assertNotEquals(ERR_DETAIL_STATUS_SKIPPED_EDITOR, ERR_DETAIL_STATUS_SKIPPED_REVIEWER);
    }

    // ── ERR_ROLE_STATUS_MISMATCH ──────────────────────────────────────────────

    @Test
    public void roleStatusMismatch_messageHasNoRoleOrStatusName() {
        // Bill is FULLY_VERIFIED — neither editor nor reviewer status match
        Bill bill = buildBillWithDetails(Status.FULLY_VERIFIED,
                List.of(buildDetail(DETAIL_ID_1, Status.VERIFIED)));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));
        BillDetailUpdateRequest req = buildUpdateRequest(bill, "PAYMENT_EDITOR");

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateBillDetailUpdateRequest(req));

        assertEquals(ERR_ROLE_STATUS_MISMATCH, ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── ERR_FIELD_STRIPPED_EDITOR ─────────────────────────────────────────────

    @Test
    public void fieldStrippedEditor_messageHasNoRoleName() {
        BillDetail pendingDetail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.PENDING_VERIFICATION, List.of(pendingDetail));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        // Request contains totalAmount change (blocked for editor)
        PartialBillDetail partial = PartialBillDetail.builder()
                .id(DETAIL_ID_1)
                .totalAmount(BigDecimal.valueOf(9999)) // different from DB
                .payee(Party.builder().id("p1").tenantId(TENANT_ID)
                        .type("INDIVIDUAL").identifier("IND-001")
                        .paymentProvider("MTN").payeePhoneNumber("0770000001").build())
                .build();
        BillDetailUpdateRequest req = BillDetailUpdateRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .billId(BILL_ID).tenantId(TENANT_ID)
                .billDetails(new ArrayList<>(List.of(partial))).build();

        BillValidator.BillDetailValidationResult result = validator.validateBillDetailUpdateRequest(req);

        result.warnings.stream()
                .filter(w -> ERR_FIELD_STRIPPED_EDITOR.equals(w.getCode()))
                .forEach(w -> assertNoInternalNames(w.getMessage()));
    }

    // ── ERR_FIELD_STRIPPED_REVIEWER ───────────────────────────────────────────

    @Test
    public void fieldStrippedReviewer_messageHasNoRoleName() {
        BillDetail underReviewDetail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.UNDER_REVIEW, List.of(underReviewDetail));
        when(billRepository.search(any(), anyBoolean())).thenReturn(List.of(bill));

        // Request changes payeePhoneNumber (blocked for reviewer)
        Party changedPayee = Party.builder().id("p1").tenantId(TENANT_ID)
                .type("INDIVIDUAL").identifier("IND-001")
                .paymentProvider("MTN").payeePhoneNumber("0779999999").build(); // different
        PartialBillDetail partial = PartialBillDetail.builder()
                .id(DETAIL_ID_1).payee(changedPayee).build();
        BillDetailUpdateRequest req = BillDetailUpdateRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_REVIEWER"))
                .billId(BILL_ID).tenantId(TENANT_ID)
                .billDetails(new ArrayList<>(List.of(partial))).build();

        BillValidator.BillDetailValidationResult result = validator.validateBillDetailUpdateRequest(req);

        result.warnings.stream()
                .filter(w -> ERR_FIELD_STRIPPED_REVIEWER.equals(w.getCode()))
                .forEach(w -> assertNoInternalNames(w.getMessage()));
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private void assertNoInternalNames(String message) {
        if (message == null) return;
        for (String name : INTERNAL_ROLE_NAMES) {
            assertFalse(message.contains(name),
                    "Message must not expose role name '" + name + "': " + message);
        }
        for (String status : INTERNAL_STATUS_NAMES) {
            assertFalse(message.contains(status),
                    "Message must not expose internal status '" + status + "': " + message);
        }
    }
}
