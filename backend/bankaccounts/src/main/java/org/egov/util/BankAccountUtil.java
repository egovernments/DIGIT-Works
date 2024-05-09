package org.egov.util;


import org.egov.common.contract.models.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.BankAccount;
import org.egov.web.models.BankAccountDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BankAccountUtil {

    /**
     * Method to set auditDetails for create/update flow of Bank Account
     *
     * @param by
     * @param isCreate
     * @return
     */
    public void setAuditDetailsForBankAccount(String by, List<BankAccount> bankAccountList, Boolean isCreate) {
        log.info("BankAccountUtil::setAuditDetailsForBankAccount");
        Long time = System.currentTimeMillis();
        for (BankAccount bankAccount : bankAccountList) {
            if (isCreate) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                bankAccount.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
                bankAccount.setAuditDetails(auditDetailsForUpdate);
            }
        }
    }

    /**
     * Method to set auditDetails for create/update flow of bank account details
     *
     * @param by
     * @param isCreate
     * @return
     */
    public void setAuditDetailsForBankAccountDetail(String by, List<BankAccountDetails> bankAccountDetailsList, Boolean isCreate) {
        log.info("BankAccountUtil::setAuditDetailsForBankAccountDetail");
        Long time = System.currentTimeMillis();
        for (BankAccountDetails bankAccountDetails : bankAccountDetailsList) {
            if (isCreate) {
                AuditDetails auditDetailsForCreate = AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
                bankAccountDetails.setAuditDetails(auditDetailsForCreate);
            } else {
                AuditDetails auditDetailsForUpdate = AuditDetails.builder().lastModifiedBy(by).lastModifiedTime(time).build();
                bankAccountDetails.setAuditDetails(auditDetailsForUpdate);
            }
        }
    }
}
