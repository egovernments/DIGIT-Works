package org.egov.digit.expense.web.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.Workflow;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.MdmsUtil;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSignature;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillValidatorSignatureTest {

    @Mock private MdmsUtil mdmsUtil;
    @Mock private Configuration configs;
    @Mock private BillRepository billRepository;

    private BillValidator validator;

    private static final List<String> GATED_ACTIONS =
            List.of("SEND_FOR_REVIEW", "SEND_FOR_APPROVAL", "PAYMENT_INITIATION");

    @BeforeEach
    void setUp() {
        validator = new BillValidator(mdmsUtil, configs, billRepository, new ObjectMapper());
        when(configs.getSignatureRequiredActions()).thenReturn(GATED_ACTIONS);
    }

    private BillRequest request(String action, List<BillSignature> signatures) {
        Bill bill = Bill.builder().billNumber("BILL-001").signatures(signatures).build();
        Workflow workflow = action == null ? null : Workflow.builder().action(action).build();
        return BillRequest.builder().bill(bill).workflow(workflow).build();
    }

    private BillSignature signature(String action, String printedName, String fileStoreId) {
        return BillSignature.builder().action(action).printedName(printedName).fileStoreId(fileStoreId).build();
    }

    @Test
    void passesWhenNoWorkflow() {
        assertDoesNotThrow(() -> validator.validateSignatureForWorkflowAction(request(null, null)));
    }

    @Test
    void passesForNonGatedAction() {
        assertDoesNotThrow(() -> validator.validateSignatureForWorkflowAction(request("VERIFY", null)));
    }

    @Test
    void failsWhenNoSignatureOnGatedAction() {
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateSignatureForWorkflowAction(request("SEND_FOR_REVIEW", null)));
        assertTrue(ex.getCode().contains("EG_EXPENSE_BILL_SIGNATURE_REQUIRED"));
    }

    @Test
    void failsWhenSignatureIsForDifferentAction() {
        List<BillSignature> signatures =
                Collections.singletonList(signature("SEND_FOR_REVIEW", "Jane Doe", "fs-1"));
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateSignatureForWorkflowAction(request("SEND_FOR_APPROVAL", signatures)));
        assertTrue(ex.getCode().contains("EG_EXPENSE_BILL_SIGNATURE_REQUIRED"));
    }

    @Test
    void failsWhenPrintedNameIsBlank() {
        List<BillSignature> signatures =
                Collections.singletonList(signature("SEND_FOR_REVIEW", "   ", "fs-1"));
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateSignatureForWorkflowAction(request("SEND_FOR_REVIEW", signatures)));
        assertTrue(ex.getErrors().containsKey("EG_EXPENSE_BILL_SIGNATURE_PRINTED_NAME_REQUIRED"));
    }

    @Test
    void failsWhenFileStoreIdMissing() {
        List<BillSignature> signatures =
                Collections.singletonList(signature("PAYMENT_INITIATION", "Jane Doe", null));
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateSignatureForWorkflowAction(request("PAYMENT_INITIATION", signatures)));
        assertTrue(ex.getErrors().containsKey("EG_EXPENSE_BILL_SIGNATURE_IMAGE_REQUIRED"));
    }

    @Test
    void passesWithValidSignatureForAction() {
        List<BillSignature> signatures =
                Collections.singletonList(signature("SEND_FOR_APPROVAL", "Jane Doe", "fs-1"));
        assertDoesNotThrow(() -> validator.validateSignatureForWorkflowAction(request("SEND_FOR_APPROVAL", signatures)));
    }

    @Test
    void latestSignatureForActionWins() {
        List<BillSignature> signatures = List.of(
                signature("SEND_FOR_REVIEW", "  ", null),
                signature("SEND_FOR_REVIEW", "Jane Doe", "fs-2"));
        assertDoesNotThrow(() -> validator.validateSignatureForWorkflowAction(request("SEND_FOR_REVIEW", signatures)));
    }
}
