package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.util.EncryptionDecryptionUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EncryptionService {

    private final EncryptionDecryptionUtil encryptionDecryptionUtil;

    @Autowired
    public EncryptionService(EncryptionDecryptionUtil encryptionDecryptionUtil) {
        this.encryptionDecryptionUtil = encryptionDecryptionUtil;
    }

    public BankAccountRequest encrypt(BankAccountRequest request, String key) {
        log.info("EncryptionService::encrypt-request");
        List<BankAccount> bankAccountList = request.getBankAccounts();
        String stateLevelTenantId = bankAccountList.get(0).getTenantId().split("\\.")[0];

        for (BankAccount bankAccount : bankAccountList) {
            if (!CollectionUtils.isEmpty(bankAccount.getBankAccountDetails())) {
                List<BankAccountDetails> encryptedBankAcctDetails = (List<BankAccountDetails>) encryptionDecryptionUtil
                        .encryptObject(bankAccount.getBankAccountDetails(), stateLevelTenantId, key, BankAccountDetails.class);
                bankAccount.setBankAccountDetails(encryptedBankAcctDetails);
            }
        }

        return request;
    }

    public BankAccountSearchRequest encrypt(BankAccountSearchRequest searchRequest, String key) {
        log.info("EncryptionService::encrypt-searchRequest");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            String stateLevelTenantId = searchCriteria.getTenantId().split("\\.")[0];
            BankAccountSearchCriteria encryptedSearchCrteria = (BankAccountSearchCriteria) encryptionDecryptionUtil
                    .encryptObject(searchCriteria, stateLevelTenantId, key, BankAccountSearchCriteria.class);

            searchRequest.setBankAccountDetails(encryptedSearchCrteria);
        }

        return searchRequest;
    }

    public List<BankAccount> decrypt(List<BankAccount> bankAccounts, String key, RequestInfo requestInfo) {
        if (!CollectionUtils.isEmpty(bankAccounts)) {
            for (BankAccount bankAccount : bankAccounts) {
                List<BankAccountDetails> bankAccountDetailsList = bankAccount.getBankAccountDetails();

                List<BankAccountDetails> encryptedBankAccountDetails = filterEncryptedBankAccountDetails(bankAccountDetailsList);
                List<BankAccountDetails> decryptedBankAccountDetails = (List<BankAccountDetails>) encryptionDecryptionUtil
                        .decryptObject(encryptedBankAccountDetails, key, BankAccountDetails.class, requestInfo);
                if (bankAccountDetailsList.size() > decryptedBankAccountDetails.size()) {
                    // add the already decrypted objects to the list
                    List<String> ids = decryptedBankAccountDetails.stream()
                            .map(BankAccountDetails::getId)
                            .collect(Collectors.toList());
                    for (BankAccountDetails bankAccountDetail : bankAccountDetailsList) {
                        if (!ids.contains(bankAccountDetail.getId())) {
                            decryptedBankAccountDetails.add(bankAccountDetail);
                        }
                    }
                }

                bankAccount.setBankAccountDetails(decryptedBankAccountDetails);
            }
        }

        return bankAccounts;
    }

    private List<BankAccountDetails> filterEncryptedBankAccountDetails(List<BankAccountDetails> bankAccountDetails) {
        return bankAccountDetails.stream()
                .filter(bankAccountDetail -> isCipherText(bankAccountDetail.getAccountNumber())
                        || isCipherText(StringUtils.isNotBlank(bankAccountDetail.getAccountHolderName())
                        ? bankAccountDetail.getAccountHolderName()
                        : null))
                .collect(Collectors.toList());
    }

    private boolean isCipherText(String text) {
        //sample encrypted data - 640326|7hsFfY6olwUbet1HdcLxbStR1BSkOye8N3M=
        //Encrypted data will have a prefix followed by '|' and the base64 encoded data
        if ((StringUtils.isNotBlank(text) && text.contains("|"))) {
            String base64Data = text.split("\\|")[1];
            return StringUtils.isNotBlank(base64Data) && (base64Data.length() % 4 == 0 || base64Data.endsWith("="));
        }
        return false;
    }
}
