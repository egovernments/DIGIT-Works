package org.egov.service;


import lombok.extern.slf4j.Slf4j;
import org.egov.config.Configuration;
import org.egov.web.models.BankAccountSearchCriteria;
import org.egov.web.models.BankAccountSearchRequest;
import org.egov.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnrichmentService {

    @Autowired
    private Configuration config;


    public void enrichBankAccountOnSearch(BankAccountSearchRequest searchRequest) {
        log.info("EnrichmentService::enrichBankAccountOnSearch");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        Pagination pagination = searchRequest.getPagination();
        if (pagination.getLimit() == null)
            pagination.setLimit(Double.valueOf(config.getDefaultLimit()));

        if (pagination.getOffSet() == null)
            pagination.setOffSet(Double.valueOf(config.getDefaultOffset()));

        if (pagination.getLimit() != null && pagination.getLimit() > config.getMaxLimit())
            pagination.setLimit(Double.valueOf(config.getMaxLimit()));
    }
}
