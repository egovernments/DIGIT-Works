package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.Configuration;
import org.egov.kafka.Producer;
import org.egov.repository.BankAccountRepository;
import org.egov.validator.BankAccountValidator;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BankAccountService {

    public static final String BANK_ACCOUNT_ENCRYPT_KEY = "BankAccountEncrypt";
    public static final String BANK_ACCOUNT_NUMBER_ENCRYPT_KEY = "BankAccountNumberEncrypt";
    public static final String BANK_ACCOUNT_HOLDER_NAME_ENCRYPT_KEY = "BankAccountHolderNameEncrypt";
    public static final String BANK_ACCOUNT_DECRYPT_KEY = "BankAccountDecrypt";

    private final BankAccountValidator bankAccountValidator;

    private final EnrichmentService enrichmentService;

    private final BankAccountRepository bankAccountRepository;

    private final Producer bankAccountProducer;

    private final Configuration configuration;

    private final EncryptionService encryptionService;

    @Autowired
    public BankAccountService(BankAccountValidator bankAccountValidator, EnrichmentService enrichmentService, BankAccountRepository bankAccountRepository, Producer bankAccountProducer, Configuration configuration, EncryptionService encryptionService) {
        this.bankAccountValidator = bankAccountValidator;
        this.enrichmentService = enrichmentService;
        this.bankAccountRepository = bankAccountRepository;
        this.bankAccountProducer = bankAccountProducer;
        this.configuration = configuration;
        this.encryptionService = encryptionService;
    }

    public BankAccountRequest createBankAccount(BankAccountRequest bankAccountRequest) {
        log.info("BankAccountService::createBankAccount");
        bankAccountValidator.validateBankAccountOnCreate(bankAccountRequest);
        enrichmentService.enrichBankAccountOnCreate(bankAccountRequest);

        encryptionService.encrypt(bankAccountRequest, BANK_ACCOUNT_ENCRYPT_KEY);

        bankAccountProducer.push(configuration.getSaveBankAccountTopic(), bankAccountRequest);

        return bankAccountRequest;
    }

    /**
     * @param searchRequest
     * @return
     */
    public List<BankAccount> searchBankAccount(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountService::searchBankAccount");
        bankAccountValidator.validateBankAccountOnSearch(searchRequest);
        enrichmentService.enrichBankAccountOnSearch(searchRequest);

        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        if (!CollectionUtils.isEmpty(searchCriteria.getAccountNumber())) {
            encryptionService.encrypt(searchRequest, BANK_ACCOUNT_NUMBER_ENCRYPT_KEY);
        }
        if (StringUtils.isNotBlank(searchCriteria.getAccountHolderName())) {
            encryptionService.encrypt(searchRequest, BANK_ACCOUNT_HOLDER_NAME_ENCRYPT_KEY);
        }

        List<BankAccount> encryptedBankAccountList = bankAccountRepository.getBankAccount(searchRequest);


        if (!CollectionUtils.isEmpty(encryptedBankAccountList)) {
            encryptedBankAccountList = encryptionService.decrypt(encryptedBankAccountList,
                    BANK_ACCOUNT_DECRYPT_KEY, searchRequest.getRequestInfo());
            return encryptedBankAccountList;
        }

        return Collections.emptyList();
    }

    public Pagination countAllBankAccounts(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountService::countAllBankAccounts");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        Pagination pagination = searchRequest.getPagination();
        if (pagination == null) {
            pagination = new Pagination();
            searchRequest.setPagination(pagination);
        }

        searchCriteria.setIsCountNeeded(Boolean.TRUE);

        Integer count = bankAccountRepository.getBankAccountCount(searchRequest);
        pagination.setTotalCount(Double.valueOf(count));

        return pagination;
    }

    //TODO
    public BankAccountRequest updateBankAccount(BankAccountRequest bankAccountRequest) {
        log.info("BankAccountService::updateBankAccount");
        bankAccountValidator.validateBankAccountOnUpdate(bankAccountRequest);
        enrichmentService.enrichBankAccountOnUpdate(bankAccountRequest);

        encryptionService.encrypt(bankAccountRequest, BANK_ACCOUNT_ENCRYPT_KEY);

        bankAccountProducer.push(configuration.getUpdateBankAccountTopic(), bankAccountRequest);

        return bankAccountRequest;
    }
}
