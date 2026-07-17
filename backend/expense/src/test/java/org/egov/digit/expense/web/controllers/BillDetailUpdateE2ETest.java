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
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
@Import(TestConfiguration.class)
public class BillDetailUpdateE2ETest {

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

    // ── Happy path → 202 Accepted ─────────────────────────────────────────────

    @Test
    public void e2e_updatePayeePhone_returns202() throws Exception {
        BillDetail detail = buildDetailWithPayee(DETAIL_ID_1, Status.PENDING_VERIFICATION, "0771111111", "MTN");
        when(billService.partialUpdateBillDetails(any())).thenReturn(
                BillDetailUpdateResponse.builder()
                        .billDetails(List.of(detail))
                        .errors(Collections.emptyList())
                        .warnings(Collections.emptyList())
                        .build());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.billDetails").isArray());

        verify(billService).partialUpdateBillDetails(any(BillDetailUpdateRequest.class));
    }

    // ── CustomException → 400 via tracer ExceptionAdvise ─────────────────────

    @Test
    public void e2e_billStateLocked_returns400() throws Exception {
        doThrow(new CustomException("BILL_STATE_LOCKED", "locked"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void e2e_unauthorizedRole_returns400() throws Exception {
        doThrow(new CustomException("ERR_UNAUTHORIZED", "Access denied"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void e2e_billNotFound_returns400() throws Exception {
        doThrow(new CustomException("EG_EXPENSE_INVALID_BILL", "not found"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void e2e_invalidDetailIds_returns400() throws Exception {
        doThrow(new CustomException("ERR_INVALID_BILL_DETAIL_IDS", "invalid detail IDs"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void e2e_roleStatusMismatch_returns400() throws Exception {
        doThrow(new CustomException("ERR_ROLE_STATUS_MISMATCH", "mismatch"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void e2e_paymentFieldNotAllowed_returns400() throws Exception {
        doThrow(new CustomException("ERR_PAYMENT_FIELD_UPDATE_NOT_ALLOWED", "not allowed"))
                .when(billService).partialUpdateBillDetails(any());

        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(buildDetailUpdateRequest(BILL_ID))))
                .andExpect(status().isBadRequest());
    }

    // ── Missing required fields → 400 from Spring validation ─────────────────

    @Test
    public void e2e_missingRequestInfo_returns400() throws Exception {
        mockMvc.perform(post("/bill/v1/billdetails/_update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tenantId\":\"ng.test\",\"billId\":\"b1\"}"))
                .andExpect(status().isBadRequest());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    private BillDetailUpdateRequest buildDetailUpdateRequest(String billId) {
        Party payee = Party.builder()
                .id("payee-001")
                .tenantId(TENANT_ID)
                .type("INDIVIDUAL")
                .identifier("IND-001")
                .paymentProvider("MTN")
                .payeePhoneNumber("0770000001")
                .build();
        PartialBillDetail partial = PartialBillDetail.builder()
                .id(DETAIL_ID_1)
                .payee(payee)
                .build();
        return BillDetailUpdateRequest.builder()
                .requestInfo(buildRequestInfo("PAYMENT_EDITOR"))
                .billId(billId)
                .tenantId(TENANT_ID)
                .billDetails(List.of(partial))
                .build();
    }
}
