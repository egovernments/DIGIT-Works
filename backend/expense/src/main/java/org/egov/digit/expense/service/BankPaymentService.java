package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Skeleton service for bank-based payment verification and transfer.
 * Mirrors the structure of {@link MTNService} but targets BANK payment provider.
 *
 * TODO: Integrate with actual bank transfer API when available.
 */
@Service
@Slf4j
public class BankPaymentService implements PaymentProviderService {

    private final Configuration config;
    private final BillRepository billRepository;

    @Autowired
    public BankPaymentService(Configuration config, BillRepository billRepository) {
        this.config = config;
        this.billRepository = billRepository;
    }

    /**
     * Verifies bank payment details on bill details.
     * Validates that required bank fields (bankAccount, bankCode, beneficiaryCode) are present.
     *
     * @param billRequest the bill request containing bill details to verify
     */
    public void verify(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
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

            validateBankFields(billDetail, payee);

            // TODO: Call bank verification API to validate account details
            log.info("Bank verification placeholder for billDetail: {}, bankAccount: {}, bankCode: {}",
                    billDetail.getId(), payee.getBankAccount(), payee.getBankCode());
        }
    }

    /**
     * Initiates bank transfer for verified bill details.
     *
     * @param billRequest the bill request containing bill details to transfer
     */
    public void transfer(BillRequest billRequest) {
        Bill bill = billRequest.getBill();
        log.info("Starting bank payment transfer for bill: {}", bill.getId());

        for (BillDetail billDetail : bill.getBillDetails()) {
            if (billDetail.getStatus() != Status.PAYMENT_IN_PROGRESS) {
                continue;
            }

            Party payee = billDetail.getPayee();
            if (payee == null || !Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(payee.getPaymentProvider())) {
                continue;
            }

            throw new CustomException(Constants.BANK_TRANSFER_NOT_IMPLEMENTED_ERR_CODE,
                    "Bank transfer is not yet implemented. billDetail: " + billDetail.getId());
        }
    }

    @Override
    public boolean supports(String paymentProvider) {
        return Constants.PAYMENT_PROVIDER_BANK.equalsIgnoreCase(paymentProvider);
    }

    /**
     * Dispatches a Kafka task to the bank verify or transfer flow.
     */
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
     * Placeholder for bank task-status polling. Since bank does not yet use async status checks,
     * all tasks are considered resolved immediately — returns false (no details in-progress).
     */
    public boolean updatePaymentTaskStatusAndFinalize(TaskRequest taskRequest) {
        log.info("Bank updatePaymentTaskStatusAndFinalize placeholder — task {} treated as resolved",
                taskRequest.getTask().getId());
        return false;
    }

    private void verifyFromTask(TaskRequest taskRequest) {
        Bill billFromSearch = getBillFromSearch(taskRequest.getBill(), taskRequest.getRequestInfo());
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(taskRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();
        verify(billRequest);
    }

    private void transferFromTask(TaskRequest taskRequest) {
        Bill billFromSearch = getBillFromSearch(taskRequest.getBill(), taskRequest.getRequestInfo());
        BillRequest billRequest = BillRequest.builder()
                .requestInfo(taskRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();
        transfer(billRequest);
    }

    private Bill getBillFromSearch(Bill billFromRequest, RequestInfo requestInfo) {
        BillCriteria billCriteria = BillCriteria.builder()
                .statusNot(Status.INACTIVE.toString())
                .tenantId(billFromRequest.getTenantId())
                .ids(Stream.of(billFromRequest.getId()).collect(Collectors.toSet()))
                .build();
        BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(billCriteria)
                .build();
        List<Bill> bills = billRepository.search(billSearchRequest, false);
        if (bills == null || bills.isEmpty()) {
            throw new CustomException(Constants.BILL_NOT_FOUND_ERR_CODE,
                    "Bill not found for id: " + billFromRequest.getId());
        }
        return bills.get(0);
    }

    private void validateBankFields(BillDetail billDetail, Party payee) {
        if (!StringUtils.hasText(payee.getBankAccount())) {
            throw new CustomException(Constants.MISSING_BANK_ACCOUNT_ERR_CODE,
                    "Bank account is required for BANK payment on billDetail: " + billDetail.getId());
        }
        if (!StringUtils.hasText(payee.getBeneficiaryCode())) {
            throw new CustomException(Constants.MISSING_BENEFICIARY_CODE_ERR_CODE,
                    "Beneficiary code is required for BANK payment on billDetail: " + billDetail.getId());
        }
    }
}
