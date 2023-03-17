package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BankAccountValidator {


    /**
     * validate the search bank account
     *
     * @param searchRequest
     */
    public void validateBankAccountOnSearch(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountValidator::validateSearchBankAccount");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        RequestInfo requestInfo = searchRequest.getRequestInfo();
        if (searchCriteria == null) {
            throw new CustomException("BANK_ACCOUNTS_SEARCH_CRITERIA_REQUEST", "Bank accounts search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "TenantId is mandatory");
        }
//        if (searchCriteria.getIds() != null && !searchCriteria.getIds().isEmpty() && searchCriteria.getIds().size() > 10) {
//            throw new CustomException("IDS", "Ids should be of max 10.");
//        }

    }

    /**
     * Validate the create Bank account and its details
     *
     * @param bankAccountRequest
     */
    public void validateBankAccountOnCreate(BankAccountRequest bankAccountRequest) {
        log.info("BankAccountValidator::validateBankAccountOnCreate");
        Map<String, String> errorMap = new HashMap<>();
        List<BankAccount> bankAccountList = bankAccountRequest.getBankAccounts();
        RequestInfo requestInfo = bankAccountRequest.getRequestInfo();

        //validateRequestInfo(requestInfo, errorMap);
        validateBankAccount(bankAccountList, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateBankAccount(List<BankAccount> bankAccountList, Map<String, String> errorMap) {
        log.info("BankAccountValidator::validateBankAccount");
        if (CollectionUtils.isEmpty(bankAccountList)) {
            throw new CustomException("BANK_ACCOUNTS", "Bank account is mandatory");
        }
        for (BankAccount bankAccount : bankAccountList) {
            if (StringUtils.isBlank(bankAccount.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }
            if (StringUtils.isBlank(bankAccount.getReferenceId())) {
                throw new CustomException("REFERENCE_ID", "Reference id is mandatory");
            }
            if (StringUtils.isBlank(bankAccount.getServiceCode())) {
                throw new CustomException("SERVICE_CODE", "Service code is mandatory");
            }

            List<BankAccountDetails> bankAccountDetailsList = bankAccount.getBankAccountDetails();
            if (CollectionUtils.isEmpty(bankAccountDetailsList)) {
                throw new CustomException("BANK_ACCOUNT_DETAILS", "Bank account details are mandatory");
            }

            for (BankAccountDetails bankAccountDetails : bankAccountDetailsList) {
                if (StringUtils.isBlank(bankAccountDetails.getTenantId())) {
                    throw new CustomException("BANK_ACCOUNT_DETAILS.TENANT_ID", "Tenant id is mandatory");
                }
                if (StringUtils.isBlank(bankAccountDetails.getAccountNumber())) {
                    throw new CustomException("BANK_ACCOUNT_DETAILS.ACCOUNT_NUMBER", "Account number is mandatory");
                }

                BankBranchIdentifier bankBranchIdentifier = bankAccountDetails.getBankBranchIdentifier();
                if (bankBranchIdentifier == null) {
                    throw new CustomException("BANK_ACCOUNT_DETAILS.BRANCH_IDENTIFIER", "Bank branch identifier is mandatory");
                }

                if (StringUtils.isBlank(bankBranchIdentifier.getType())) {
                    throw new CustomException("BRANCH_IDENTIFIER.TYPE", "Branch identifier type is mandatory");
                }
                if (StringUtils.isBlank(bankBranchIdentifier.getCode())) {
                    throw new CustomException("BRANCH_IDENTIFIER.CODE", "Branch identifier code is mandatory");
                }
            }
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        log.info("BankAccountValidator::validateRequestInfo");
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }
}
