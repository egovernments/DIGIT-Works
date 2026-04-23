package org.egov.digit.expense.service;

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
import java.util.List;
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

    @Autowired
    public BankPaymentService(Configuration config,
                               BillRepository billRepository,
                               WorkflowUtil workflowUtil,
                               ExpenseProducer expenseProducer) {
        this.config = config;
        this.billRepository = billRepository;
        this.workflowUtil = workflowUtil;
        this.expenseProducer = expenseProducer;
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
        try {
            verify(billRequest);
        } finally {
            task.setStatus(Status.DONE);
            expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
        }
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
            if (billDetail.getStatus() != Status.PENDING_VERIFICATION
                    && billDetail.getStatus() != Status.VERIFICATION_FAILED) {
                continue;
            }
            Party payee = billDetail.getPayee();
            if (payee == null || !Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(payee.getPaymentProvider())) {
                continue;
            }

            // Step 1: VERIFY → VERIFICATION_IN_PROGRESS
            setBillDetailStatus(billDetail, Workflow.builder().action(Actions.VERIFY.toString()).build(), requestInfo);
            if (billDetail.getStatus() != Status.VERIFICATION_IN_PROGRESS) {
                log.error("Bank WF Step 1 (VERIFY) failed for billDetail {} — skipping field validation", billDetail.getId());
                continue;
            }

            // Step 2: validate required bank fields
            boolean validationPassed = validateBankFields(billDetail, payee);

            // Step 3: finalize based on validation
            if (validationPassed) {
                setBillDetailStatus(billDetail,
                        Workflow.builder().action(Actions.VERIFICATION_SUCCESS.toString()).build(), requestInfo);
                log.info("Bank verification succeeded for billDetail {}", billDetail.getId());
            } else {
                setBillDetailStatus(billDetail,
                        Workflow.builder().action(Actions.FAILED.toString()).build(), requestInfo);
                log.info("Bank verification failed for billDetail {}", billDetail.getId());
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
        int maxRetries = config.getWfTransitionRetryMaxAttempts();
        long delayMs = config.getWfTransitionRetryInitialDelayMs();
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                State wfState = workflowUtil.callWorkFlow(
                        workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
                return;
            } catch (Exception e) {
                if (attempt == maxRetries) {
                    log.error("Bank WF retries exhausted for billDetail {}, action={} — forcing FAILED",
                            billDetail.getId(), workflow.getAction(), e);
                    forceBillDetailFailed(billDetail, requestInfo);
                    return;
                }
                log.warn("Bank WF retry {}/{} for billDetail {}, action={}: {}",
                        attempt, maxRetries, billDetail.getId(), workflow.getAction(), e.getMessage());
                try { Thread.sleep(delayMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); return; }
                delayMs *= 2;
            }
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

    private boolean validateBankFields(BillDetail billDetail, Party payee) {
        if (!StringUtils.hasText(payee.getBankAccount())) {
            log.warn("Bank account missing for billDetail {}", billDetail.getId());
            return false;
        }
        if (!StringUtils.hasText(payee.getBeneficiaryCode())) {
            log.warn("Beneficiary code missing for billDetail {}", billDetail.getId());
            return false;
        }
        return true;
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
}
