package digit.service;


import digit.config.Configuration;
import digit.web.models.BankAccountSearchCriteria;
import digit.web.models.BankAccountSearchRequest;
import digit.web.models.Pagination;
import lombok.extern.slf4j.Slf4j;
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
