package org.egov.digit.expense.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.digit.expense.TestConfiguration;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.service.NotificationService;
import org.egov.digit.expense.service.PaymentWorkflowService;
import org.egov.digit.expense.util.CalculatorUtil;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.controller.BillController;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.egov.digit.expense.TestDataBuilder.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
@Import(TestConfiguration.class)
public class BillBulkUpdateStatusE2ETest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private BillService billService;
    @MockBean private BillValidator validator;
    @MockBean private BillRepository billRepository;
    @MockBean private WorkflowUtil workflowUtil;
    @MockBean private EnrichmentUtil enrichmentUtil;
    @MockBean private ExpenseProducer expenseProducer;
    @MockBean private Configuration config;
    @MockBean private ResponseInfoFactory responseInfoFactory;
    @MockBean private NotificationService notificationService;
    @MockBean private CalculatorUtil calculatorUtil;
    @MockBean private PaymentWorkflowService paymentWorkflowService;

    @BeforeEach
    public void setUp() {
        when(responseInfoFactory.createResponseInfoFromRequestInfo(any(), anyBoolean()))
                .thenReturn(org.egov.common.contract.response.ResponseInfo.builder().build());
    }

    // ── Happy path routing ────────────────────────────────────────────────────
    // No errors → 202 Accepted

    @Test
    public void e2e_verifyAction_routes_returns202() throws Exception {
        Bill bill = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); bill.setId("b1");
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder().bills(List.of(bill)).errors(Collections.emptyList()).build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("VERIFICATION_IN_PROGRESS", Actions.VERIFY.toString(), "b1"))))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.bills").isArray());

        verify(billService).bulkUpdateStatus(any(BulkBillStatusUpdateRequest.class));
    }

    @Test
    public void e2e_sendForReviewAction_routes_returns202() throws Exception {
        Bill bill = buildBill(Status.FULLY_VERIFIED, Status.VERIFIED, 1); bill.setId("b1");
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder().bills(List.of(bill)).errors(Collections.emptyList()).build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("SENDING_FOR_REVIEW", Actions.SEND_FOR_REVIEW.toString(), "b1"))))
                .andExpect(status().isAccepted());
    }

    @Test
    public void e2e_sendForApprovalAction_routes_returns202() throws Exception {
        Bill bill = buildBill(Status.UNDER_REVIEW, Status.UNDER_REVIEW, 1); bill.setId("b1");
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder().bills(List.of(bill)).errors(Collections.emptyList()).build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("REVIEW_IN_PROGRESS", Actions.SEND_FOR_APPROVAL.toString(), "b1"))))
                .andExpect(status().isAccepted());
    }

    @Test
    public void e2e_paymentInitiationAction_routes_returns202() throws Exception {
        Bill bill = buildBill(Status.REVIEWED, Status.REVIEWED, 1); bill.setId("b1");
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder().bills(List.of(bill)).errors(Collections.emptyList()).build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("PAYMENT_IN_PROGRESS", Actions.PAYMENT_INITIATION.toString(), "b1"))))
                .andExpect(status().isAccepted());
    }

    // ── All-errors → 422 Unprocessable Entity ────────────────────────────────

    @Test
    public void e2e_validationErrors_returns422() throws Exception {
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder()
                        .bills(Collections.emptyList())
                        .errors(List.of(BulkUpdateError.builder()
                                .code("ERR_BULK_STATUS_EMPTY").message("No bill IDs").build()))
                        .build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("VERIFIED", "VERIFY"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].code").value("ERR_BULK_STATUS_EMPTY"));
    }

    @Test
    public void e2e_billNotFound_returns422() throws Exception {
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder()
                        .bills(Collections.emptyList())
                        .errors(List.of(BulkUpdateError.builder()
                                .billId("unknown-id").code("EG_EXPENSE_INVALID_BILL")
                                .message("Bill not found").build()))
                        .build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("VERIFICATION_IN_PROGRESS", "VERIFY", "unknown-id"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].billId").value("unknown-id"))
                .andExpect(jsonPath("$.errors[0].code").value("EG_EXPENSE_INVALID_BILL"));
    }

    @Test
    public void e2e_serviceThrows_returns422() throws Exception {
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder()
                        .bills(Collections.emptyList())
                        .errors(List.of(BulkUpdateError.builder()
                                .billId("b1").code("EG_EXPENSE_BILL_STATUS_UPDATE_FAILED")
                                .message("WF error").build()))
                        .build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("VERIFICATION_IN_PROGRESS", "VERIFY", "b1"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].code").value("EG_EXPENSE_BILL_STATUS_UPDATE_FAILED"));
    }

    // ── Mixed success + error → 207 Multi-Status ─────────────────────────────

    @Test
    public void e2e_partialSuccess_returns207() throws Exception {
        Bill b1 = buildBill(Status.PENDING_VERIFICATION, Status.PENDING_VERIFICATION, 1); b1.setId("b1");
        when(billService.bulkUpdateStatus(any())).thenReturn(
                BulkBillStatusUpdateResponse.builder()
                        .bills(List.of(b1))
                        .errors(List.of(BulkUpdateError.builder()
                                .billId("b2").code("EG_EXPENSE_INVALID_BILL").build()))
                        .build());

        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildBulkRequest("VERIFICATION_IN_PROGRESS", "VERIFY", "b1", "b2"))))
                .andExpect(status().isMultiStatus())
                .andExpect(jsonPath("$.bills.length()").value(1))
                .andExpect(jsonPath("$.errors.length()").value(1));
    }

    // ── Bad request (missing required fields → 400 from Spring validation) ────

    @Test
    public void e2e_missingRequestInfo_returns400() throws Exception {
        mockMvc.perform(post("/bill/v1/_bulkupdatestatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"billIds\":[\"b1\"],\"status\":\"VERIFIED\"}"))
                .andExpect(status().isBadRequest());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
}
