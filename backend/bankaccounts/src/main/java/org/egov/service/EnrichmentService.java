package org.egov.service;


import org.egov.common.contract.models.Document;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.util.BankAccountUtil;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class EnrichmentService {

    private final Configuration config;

    private final BankAccountUtil bankAccountUtil;

    @Autowired
    public EnrichmentService(Configuration config, BankAccountUtil bankAccountUtil) {
        this.config = config;
        this.bankAccountUtil = bankAccountUtil;
    }


    /**
     * Enrich the search Bank Account with default pagination
     * if it's not there as part of search request
     *
     * @param searchRequest
     */
    public void enrichBankAccountOnSearch(BankAccountSearchRequest searchRequest) {
        log.info("EnrichmentService::enrichBankAccountOnSearch");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        Pagination pagination = searchRequest.getPagination();
        if (pagination == null) {
            pagination = new Pagination();
            searchRequest.setPagination(pagination);
        }
        if (pagination.getLimit() == null)
            pagination.setLimit(Double.valueOf(config.getDefaultLimit()));

        if (pagination.getOffSet() == null)
            pagination.setOffSet(Double.valueOf(config.getDefaultOffset()));

        if (pagination.getLimit() != null && pagination.getLimit() > config.getMaxLimit())
            pagination.setLimit(Double.valueOf(config.getMaxLimit()));
    }

    /**
     * Enrich create Bank Account with ids, audit details
     *
     * @param bankAccountRequest
     */
    public void enrichBankAccountOnCreate(BankAccountRequest bankAccountRequest) {
        log.info("EnrichmentService::enrichBankAccountOnCreate");
        RequestInfo requestInfo = bankAccountRequest.getRequestInfo();
        List<BankAccount> bankAccountList = bankAccountRequest.getBankAccounts();

        String userUuid = "dmgfUUId";//TODO-remove
        if (requestInfo != null
                && requestInfo.getUserInfo() != null
                && StringUtils.isNotBlank(requestInfo.getUserInfo().getUuid())) {
            userUuid = requestInfo.getUserInfo().getUuid();
        }

        bankAccountUtil.setAuditDetailsForBankAccount(userUuid, bankAccountList, Boolean.TRUE);

        for (BankAccount bankAccount : bankAccountList) {
            //set the Bank Account
            bankAccount.setId(UUID.randomUUID().toString());

            List<BankAccountDetails> bankAccountDetailList = bankAccount.getBankAccountDetails();
            bankAccountUtil.setAuditDetailsForBankAccountDetail(userUuid, bankAccountDetailList, Boolean.TRUE);

            for (BankAccountDetails bankAccountDetails : bankAccountDetailList) {
                //set the Bank Account Details
                bankAccountDetails.setId(UUID.randomUUID().toString());
                //set the Bank Branch Identifier
                bankAccountDetails.getBankBranchIdentifier().setId(UUID.randomUUID().toString());

                List<Document> documents = bankAccountDetails.getDocuments();
                //set the document id
                if (!CollectionUtils.isEmpty(documents)) {
                    for (Document document : documents) {
                        document.setId(UUID.randomUUID().toString());
                    }
                }
            }

        }

    }
    
    /**
     * Enrich update Bank Account with audit details
     *
     * @param bankAccountRequest
     */
    public void enrichBankAccountOnUpdate(BankAccountRequest bankAccountRequest) {
        log.info("EnrichmentService::enrichBankAccountOnUpdate");
        RequestInfo requestInfo = bankAccountRequest.getRequestInfo();
        List<BankAccount> bankAccountList = bankAccountRequest.getBankAccounts();
        String userUuid = "dmgfUUId";//TODO-remove
        if (requestInfo != null
                && requestInfo.getUserInfo() != null
                && StringUtils.isNotBlank(requestInfo.getUserInfo().getUuid())) {
            userUuid = requestInfo.getUserInfo().getUuid();
        }
        //TODO: deactivate existing documents and set new ones from update
        bankAccountUtil.setAuditDetailsForBankAccount(userUuid, bankAccountList, Boolean.TRUE);
        

    }
}
