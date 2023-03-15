package digit.service;


import digit.validator.BankAccountValidator;
import digit.web.models.BankAccount;
import digit.web.models.BankAccountRequest;
import digit.web.models.BankAccountSearchRequest;
import digit.web.models.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BankAccountService {

    @Autowired
    private BankAccountValidator bankAccountValidator;

    @Autowired
    private EnrichmentService enrichmentService;


    public BankAccountRequest createBankAccount(BankAccountRequest bankAccountRequest) {
        log.info("BankAccountService::createBankAccount");

        return bankAccountRequest;
    }

    public List<BankAccount> searchBankAccount(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountService::searchBankAccount");
        bankAccountValidator.validateBankAccountOnSearch(searchRequest);
        enrichmentService.enrichBankAccountOnSearch(searchRequest);


        return Collections.emptyList();
    }

    public Pagination countAllBankAccounts(BankAccountSearchRequest body) {
        log.info("BankAccountService::countAllBankAccounts");
        //Pagination.builder().totalCount(count).build();//TODO- add offset and limit
        return null;
    }

    public BankAccountRequest updateBankAccount(BankAccountRequest bankAccountRequest) {
        log.info("BankAccountService::updateBankAccount");
        return bankAccountRequest;
    }
}
