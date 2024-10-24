package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.utils.EncryptionDecryptionUtil;
import org.egov.web.models.bankaccount.*;
import org.egov.works.services.common.models.bankaccounts.BankAccount;
import org.egov.works.services.common.models.bankaccounts.BankAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EncryptionService {

    @Autowired
    private EncryptionDecryptionUtil encryptionDecryptionUtil;

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
