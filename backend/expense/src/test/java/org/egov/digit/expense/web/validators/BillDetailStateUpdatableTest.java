package org.egov.digit.expense.web.validators;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.egov.digit.expense.config.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link BillValidator#validateBillDetailStateUpdatable(BillDetail, Bill)}.
 * Covers: always-locked states, EC-7 bill-driven locks, and allowed states.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailStateUpdatableTest {

    @Mock private MdmsUtil mdmsUtil;
    @Mock private Configuration configs;
    @Mock private BillRepository billRepository;

    private BillValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new BillValidator(mdmsUtil, configs, billRepository);
    }

    // ── Always-locked states ──────────────────────────────────────────────────

    @Test
    public void verificationInProgress_alwaysLocked() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, java.util.List.of(detail));

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, bill));
        assertEquals("BILL_DETAIL_STATE_LOCKED", ex.getCode());
    }

    @Test
    public void paymentInProgress_alwaysLocked() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAYMENT_IN_PROGRESS);
        Bill bill = buildBillWithDetails(Status.PAYMENT_IN_PROGRESS, java.util.List.of(detail));

        assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    @Test
    public void paid_alwaysLocked() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PAID);
        Bill bill = buildBillWithDetails(Status.FULLY_PAID, java.util.List.of(detail));

        assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    // ── EC-7: bill-driven locks ───────────────────────────────────────────────

    @Test
    public void verifiedDetail_billSendingForReview_locked() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, java.util.List.of(detail));

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, bill));
        assertEquals(ERR_BILL_DETAIL_LOCKED_SENDING_FOR_REVIEW, ex.getCode());
    }

    @Test
    public void underReviewDetail_billReviewInProgress_locked() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.REVIEW_IN_PROGRESS, java.util.List.of(detail));

        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, bill));
        assertEquals(ERR_BILL_DETAIL_LOCKED_REVIEW_IN_PROGRESS, ex.getCode());
    }

    // ── EC-7: lock is bill-state-specific — same detail status, different bill state = allowed ─

    @Test
    public void verifiedDetail_billFullyVerified_allowed() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFIED);
        Bill bill = buildBillWithDetails(Status.FULLY_VERIFIED, java.util.List.of(detail));

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    @Test
    public void underReviewDetail_billSendingForReview_allowed() {
        // UNDER_REVIEW detail is locked only when bill is REVIEW_IN_PROGRESS, not SENDING_FOR_REVIEW
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.UNDER_REVIEW);
        Bill bill = buildBillWithDetails(Status.SENDING_FOR_REVIEW, java.util.List.of(detail));

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    // ── Allowed states ────────────────────────────────────────────────────────

    @Test
    public void pendingVerification_allowed() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, java.util.List.of(detail));

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    @Test
    public void verificationFailed_allowed() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_FAILED);
        Bill bill = buildBillWithDetails(Status.VERIFICATION_IN_PROGRESS, java.util.List.of(detail));

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    @Test
    public void reviewed_allowed_whenBillNotInIntermediateState() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.REVIEWED);
        Bill bill = buildBillWithDetails(Status.REVIEWED, java.util.List.of(detail));

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, bill));
    }

    @Test
    public void nullBill_doesNotThrowForUnlockedDetail() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.PENDING_VERIFICATION);

        assertDoesNotThrow(() -> validator.validateBillDetailStateUpdatable(detail, null));
    }

    @Test
    public void nullBill_stillThrowsForAlwaysLockedDetail() {
        BillDetail detail = buildDetail(DETAIL_ID_1, Status.VERIFICATION_IN_PROGRESS);

        assertThrows(CustomException.class,
                () -> validator.validateBillDetailStateUpdatable(detail, null));
    }
}
