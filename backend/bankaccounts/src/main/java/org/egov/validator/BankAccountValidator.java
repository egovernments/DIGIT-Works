package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.BankAccountSearchCriteria;
import org.egov.web.models.BankAccountSearchRequest;
import org.springframework.stereotype.Component;

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
}
