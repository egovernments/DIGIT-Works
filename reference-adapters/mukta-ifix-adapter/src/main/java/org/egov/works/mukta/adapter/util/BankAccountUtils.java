package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.constants.Error;
import org.egov.works.services.common.models.bankaccounts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class BankAccountUtils {
    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final MuktaAdaptorConfig config;

    @Autowired
    public BankAccountUtils(RestTemplate restTemplate, ObjectMapper mapper, MuktaAdaptorConfig config) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    public @Valid List<BankAccount> getBankAccountsByIdentifier(RequestInfo requestInfo, List<String> identifiers, String tenantId) {
        log.info("Fetching Bank account details");
        BankAccountSearchCriteria bankAccountSearchCriteria = null;
        if (requestInfo != null && identifiers != null && !identifiers.isEmpty()) {
            Set<String> uniqueIdentifires = new HashSet<>(identifiers);
            bankAccountSearchCriteria = BankAccountSearchCriteria.builder()
                    .tenantId(tenantId)
                    .referenceId(new ArrayList<>(uniqueIdentifires))
                    .build();
        } else {
            throw new CustomException(Error.INVALID_BANK_ACCOUNT_SEARCH_CRITERIA, Error.INVALID_BANK_ACCOUNT_SEARCH_CRITERIA_MESSAGE);
        }

        Pagination pagination = Pagination.builder()
                .limit((double) bankAccountSearchCriteria.getReferenceId().size())
                .offSet((double) 0)
                .order(Order.ASC)
                .build();

        BankAccountSearchRequest bankAccountSearchRequest = BankAccountSearchRequest.builder()
                .requestInfo(requestInfo)
                .bankAccountDetails(bankAccountSearchCriteria)
                .pagination(pagination)
                .build();

        return getBankAccounts(bankAccountSearchRequest);
    }

    public @Valid List<BankAccount> getBankAccounts(Object bankAccountRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getBankAccountHost()).append(config.getBankAccountSearchEndPoint());
        Object response = new HashMap<>();
        BankAccountResponse bankAccountResponse = new BankAccountResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), bankAccountRequest, Map.class);
            bankAccountResponse = mapper.convertValue(response, BankAccountResponse.class);
            log.info("Bank account fetched.");
        } catch (Exception e) {
            log.error("Exception occurred while fetching bank account lists from bank account service: ", e);
        }

        return bankAccountResponse.getBankAccounts();
    }

}
