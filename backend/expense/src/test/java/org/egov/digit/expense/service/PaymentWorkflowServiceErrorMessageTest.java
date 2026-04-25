package org.egov.digit.expense.service;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.SchedulerJobRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.service.scheduler.SchedulerJobRegistry;
import org.egov.digit.expense.util.WorkflowUtil;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaymentWorkflowServiceErrorMessageTest {

    @Mock private WorkflowUtil workflowUtil;
    @Mock private BillRepository billRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private SchedulerJobRepository schedulerJobRepository;
    @Mock private SchedulerJobRegistry schedulerJobRegistry;
    @Mock private Configuration config;
    @Mock private ExpenseProducer expenseProducer;
    @Mock private WorkflowEmailNotificationService workflowEmailNotificationService;

    private PaymentWorkflowService service;

    private static final List<String> INTERNAL_STATUS_NAMES =
            List.of("PENDING_VERIFICATION", "VERIFICATION_FAILED", "UNDER_REVIEW",
                    "PARTIALLY_VERIFIED", "REVIEWED", "PAYMENT_IN_PROGRESS",
                    "FULLY_VERIFIED", "IGNORING_ERRORS_IN_PROGRESS");

    @BeforeEach
    public void setUp() {
        service = new PaymentWorkflowService(workflowUtil, billRepository, taskRepository,
                schedulerJobRepository, schedulerJobRegistry, config, expenseProducer,
                workflowEmailNotificationService);
    }

    // ── IGNORE_ERRORS_VALIDATION ──────────────────────────────────────────────

    @Test
    public void ignoreErrors_pendingDetailsPresent_messageHasNoPendingVerificationStatus() {
        // Bill with one detail still in PENDING_VERIFICATION → triggers IGNORE_ERRORS_VALIDATION
        Bill bill = buildBillWithMixedDetails(Status.PARTIALLY_VERIFIED,
                Status.VERIFICATION_FAILED, Status.PENDING_VERIFICATION);
        BillRequest req = buildBillRequest(bill);

        CustomException ex = assertThrows(CustomException.class,
                () -> service.ignoreErrorsAndVerify(req));

        assertEquals("IGNORE_ERRORS_VALIDATION", ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── INVALID_BILL_STATE ────────────────────────────────────────────────────

    @Test
    public void verifyBill_wrongState_messageHasNoInternalStatus() {
        // FULLY_VERIFIED is not a valid state for verifyBill
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 1);
        BillRequest req = buildBillRequest(bill);

        CustomException ex = assertThrows(CustomException.class,
                () -> service.verifyBill(req));

        assertEquals("INVALID_BILL_STATE", ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    @Test
    public void sendForReview_wrongState_messageHasNoInternalStatus() {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1);
        BillRequest req = buildBillRequest(bill);

        CustomException ex = assertThrows(CustomException.class,
                () -> service.sendForReview(req));

        assertEquals("INVALID_BILL_STATE", ex.getCode());
        assertNoInternalNames(ex.getMessage());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private void assertNoInternalNames(String message) {
        if (message == null) return;
        for (String status : INTERNAL_STATUS_NAMES) {
            assertFalse(message.contains(status),
                    "Message must not expose internal status '" + status + "': " + message);
        }
    }
}
