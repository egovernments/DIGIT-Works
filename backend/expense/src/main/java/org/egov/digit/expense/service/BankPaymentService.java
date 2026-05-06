package org.egov.digit.expense.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles bank-based payment verification and transfer.
 * Mirrors the lifecycle management of {@link MTNService} for BANK payment provider.
 *
 * TODO: Integrate with actual bank transfer API when available.
 */
@Service
@Slf4j
public class BankPaymentService implements PaymentProviderService {

    private final Configuration config;
    private final BillRepository billRepository;
    private final WorkflowUtil workflowUtil;
    private final ExpenseProducer expenseProducer;
    private final BillCacheService billCacheService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BankPaymentService(Configuration config,
                               BillRepository billRepository,
                               WorkflowUtil workflowUtil,
                               ExpenseProducer expenseProducer,
                               BillCacheService billCacheService,
                               ObjectMapper objectMapper) {
        this.config = config;
        this.billRepository = billRepository;
        this.workflowUtil = workflowUtil;
        this.expenseProducer = expenseProducer;
        this.billCacheService = billCacheService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(String paymentProvider) {
        return Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(paymentProvider);
    }

    @Override
    public void executeTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        if (task.getType() == Task.Type.Verify) {
            verifyFromTask(taskRequest);
        } else if (task.getType() == Task.Type.Transfer) {
            transferFromTask(taskRequest);
        }
    }

    /**
     * Placeholder for bank task-status polling. Bank does not use async status checks;
     * all tasks are considered resolved immediately — returns false (no details in-progress).
     */
    public boolean updatePaymentTaskStatusAndFinalize(TaskRequest taskRequest) {
        log.info("Bank updatePaymentTaskStatusAndFinalize placeholder — task {} treated as resolved",
                taskRequest.getTask().getId());
        return false;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Verify
    // ─────────────────────────────────────────────────────────────────────────

    private void verifyFromTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        Bill billFromSearch = getBillFromSearch(taskRequest.getBill(), taskRequest.getRequestInfo());
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(taskRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();
        // No finally: task DONE is only pushed after the bill update is persisted to Kafka.
        // If verify() throws, the exception propagates cleanly and the scheduler returns RETRY.
        // For the Kafka-consumer path, TaskConsumer.markTaskDone() handles the DONE push.
        verify(billRequest);
        billCacheService.put(billFromSearch);
        expenseProducer.push(billFromSearch.getTenantId(), config.getBillUpdateTopic(), billRequest);
        task.setStatus(Status.DONE);
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
    }

    /**
     * Verifies bank payment details: validates required fields, then transitions each eligible
     * BANK bill detail through VERIFY → VERIFICATION_IN_PROGRESS → VERIFIED / VERIFICATION_FAILED.
     */
    public void verify(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();
        log.info("Starting bank payment verification for bill: {}", bill.getId());

        for (BillDetail billDetail : bill.getBillDetails()) {
            Status currentStatus = billDetail.getStatus();
            if (currentStatus != Status.PENDING_VERIFICATION
                    && currentStatus != Status.VERIFICATION_FAILED
                    && currentStatus != Status.VERIFICATION_IN_PROGRESS) {
                continue;
            }
            Party payee = billDetail.getPayee();
            if (payee == null) {
                continue;
            }

            // Step 1: VERIFY → VERIFICATION_IN_PROGRESS (skip if already there — e.g. re-poll from scheduler)
            if (currentStatus != Status.VERIFICATION_IN_PROGRESS) {
                setBillDetailStatus(billDetail, Workflow.builder().action(Actions.VERIFY.toString()).build(), requestInfo);
                if (billDetail.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                    log.error("Bank WF Step 1 (VERIFY) failed for billDetail {} — skipping field validation", billDetail.getId());
                    continue;
                }
            }

            // Clear any prior errorDetails before validation — must be unconditional so the
            // re-poll path (already VERIFICATION_IN_PROGRESS, Step 1 skipped) also gets a clean slate.
            Map<String, Object> cleaned = toMutableAdditionalDetails(billDetail.getAdditionalDetails());
            cleaned.remove("errorDetails");
            billDetail.setAdditionalDetails(cleaned.isEmpty() ? null : cleaned);

            // Step 2: validate payment fields for the detail's provider
            String failureReason = validatePaymentFields(payee);

            // Step 3: finalize based on validation
            if (failureReason == null) {
                setBillDetailStatus(billDetail,
                        Workflow.builder().action(Actions.VERIFICATION_SUCCESS.toString()).build(), requestInfo);
                log.info("Bank verification succeeded for billDetail {}", billDetail.getId());
            } else {
                Map<String, Object> additionalDetails = toMutableAdditionalDetails(billDetail.getAdditionalDetails());
                additionalDetails.put("errorDetails", Map.of("reasonForFailure", failureReason));
                billDetail.setAdditionalDetails(additionalDetails);
                setBillDetailStatus(billDetail,
                        Workflow.builder().action(Actions.FAILED.toString()).comments(failureReason).build(),
                        requestInfo);
                log.info("Bank verification failed for billDetail {}: {}", billDetail.getId(), failureReason);
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Transfer
    // ─────────────────────────────────────────────────────────────────────────

    private void transferFromTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        Bill billFromSearch = getBillFromSearch(taskRequest.getBill(), taskRequest.getRequestInfo());
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(taskRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();
        try {
            transfer(billRequest);
        } finally {
            task.setStatus(Status.DONE);
            expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
        }
    }

    /**
     * Bank transfer is not yet implemented. Each PAYMENT_IN_PROGRESS BANK detail is
     * marked PAYMENT_FAILED via WF so the bill can settle and is not permanently stuck.
     *
     * TODO: Replace with actual bank transfer API call when available.
     */
    public void transfer(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        RequestInfo requestInfo = billRequest.getRequestInfo();
        log.error("CRITICAL: Bank transfer is not yet implemented for bill {}. Forcing PAYMENT_FAILED on BANK details.",
                bill.getId());

        for (BillDetail billDetail : bill.getBillDetails()) {
            if (billDetail.getStatus() != Status.PAYMENT_IN_PROGRESS) {
                continue;
            }
            Party payee = billDetail.getPayee();
            if (payee == null || !Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(payee.getPaymentProvider())) {
                continue;
            }
            log.warn("Forcing FAILED on BANK PAYMENT_IN_PROGRESS billDetail {}", billDetail.getId());
            forceBillDetailFailed(billDetail, requestInfo);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private void setBillDetailStatus(BillDetail billDetail, Workflow workflow, RequestInfo requestInfo) {
        BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                .billDetail(billDetail)
                .businessService(config.getBillDetailBusinessService())
                .workflow(workflow)
                .requestInfo(requestInfo)
                .build();
        // Single attempt — no Thread.sleep. On failure the scheduler handler returns RETRY
        // and the scheduler's own exponential backoff provides the delay without blocking the thread.
        try {
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
            billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
        } catch (Exception e) {
            // INVALID_ACTION means the WF write-side already applied this transition.
            // Fetch the WF read-side state to reconcile. NOTE: the WF service has no cache
            // and can lag behind its own write-side — if it returns the same (old) status,
            // the read-side hasn't caught up yet; throw so the scheduler retries later.
            if (workflowUtil.isRetryableWfError(e)) {
                State actual = workflowUtil.searchCurrentWfState(
                        billDetail.getId(), billDetail.getTenantId(), requestInfo);
                if (actual != null) {
                    Status wfStatus = Status.fromValue(actual.getApplicationStatus());
                    if (wfStatus != billDetail.getStatus()) {
                        // WF read shows a different (newer) state — reconciliation succeeded.
                        billDetail.setStatus(wfStatus);
                        log.warn("Bank setBillDetailStatus: reconciled billDetail={} to status={} after INVALID_ACTION",
                                billDetail.getId(), billDetail.getStatus());
                        return;
                    }
                    // WF read returned the same old state — likely read lag; let scheduler retry.
                    log.warn("Bank setBillDetailStatus: INVALID_ACTION but WF read still shows {} for billDetail={}" +
                            " — possible WF read lag, will retry", billDetail.getStatus(), billDetail.getId());
                }
            }
            log.error("Bank WF transition failed for billDetail={} action={}: {}",
                    billDetail.getId(), workflow.getAction(), e.getMessage());
            throw e;
        }
    }

    private void forceBillDetailFailed(BillDetail billDetail, RequestInfo requestInfo) {
        try {
            BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                    .billDetail(billDetail)
                    .businessService(config.getBillDetailBusinessService())
                    .workflow(Workflow.builder().action(Actions.FAILED.toString()).build())
                    .requestInfo(requestInfo)
                    .build();
            State wfState = workflowUtil.callWorkFlow(
                    workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
            billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            log.info("Forced BANK billDetail {} to FAILED status via WF", billDetail.getId());
        } catch (Exception ex) {
            log.error("CRITICAL: failed to force BANK billDetail {} to FAILED — manual intervention required",
                    billDetail.getId(), ex);
        }
    }

    private String validatePaymentFields(Party payee) {
        String provider = payee.getPaymentProvider();
        if (Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(provider))
            return validateBankPaymentFields(payee);
        if (Constants.PAYMENT_PROVIDER_MTN.equalsIgnoreCase(provider))
            return validateMtnPaymentFields(payee);
        return null;
    }

    private String validateBankPaymentFields(Party payee) {
        if (!StringUtils.hasText(payee.getPayeeName()))
            return Constants.MISSING_PAYEE_NAME_ERR_CODE;
        if (!StringUtils.hasText(payee.getBankAccount()))
            return Constants.MISSING_BANK_ACCOUNT_ERR_CODE;
        if (!payee.getBankAccount().matches("\\d{10}"))
            return Constants.INVALID_BANK_ACCOUNT_ERR_CODE;
        if (!StringUtils.hasText(payee.getBankCode()))
            return Constants.INVALID_BANK_CODE_ERR_CODE;
        if (!payee.getBankCode().matches("\\d{3}|\\d{9}"))
            return Constants.INVALID_BANK_CODE_ERR_CODE;
        if (!StringUtils.hasText(payee.getBeneficiaryCode()))
            return Constants.MISSING_BENEFICIARY_CODE_ERR_CODE;
        if (payee.getBeneficiaryCode().length() > 35 || !payee.getBeneficiaryCode().matches("[a-zA-Z0-9]+"))
            return Constants.INVALID_BENEFICIARY_CODE_ERR_CODE;
        return null;
    }

    private String validateMtnPaymentFields(Party payee) {
        if (!StringUtils.hasText(payee.getPayeeName()))
            return Constants.MISSING_PAYEE_NAME_ERR_CODE;
        if (!StringUtils.hasText(payee.getPayeePhoneNumber()))
            return Constants.MISSING_PAYEE_PHONE_NUMBER_ERR_CODE;
        return null;
    }

    private Bill getBillFromSearch(Bill billFromRequest, RequestInfo requestInfo) {
        BillCriteria billCriteria = BillCriteria.builder()
                .statusesNot(Collections.singletonList(Status.INACTIVE.toString()))
                .tenantId(billFromRequest.getTenantId())
                .ids(Stream.of(billFromRequest.getId()).collect(Collectors.toSet()))
                .build();
        BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(billCriteria)
                .build();
        List<Bill> bills = billRepository.search(billSearchRequest, false);
        if (bills == null || bills.isEmpty()) {
            throw new org.egov.tracer.model.CustomException(Constants.BILL_NOT_FOUND_ERR_CODE,
                    "Bill not found for id: " + billFromRequest.getId());
        }
        return bills.get(0);
    }

    /**
     * Returns a mutable copy of additionalDetails as a Map<String, Object>,
     * handling both JsonNode (returned by BillRowMapper) and plain Map.
     */
    private Map<String, Object> toMutableAdditionalDetails(Object raw) {
        if (raw instanceof JsonNode jsonNode) {
            return objectMapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {});
        }
        if (raw instanceof Map<?, ?> map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> copy = new HashMap<>((Map<String, Object>) map);
            return copy;
        }
        return new HashMap<>();
    }
}
