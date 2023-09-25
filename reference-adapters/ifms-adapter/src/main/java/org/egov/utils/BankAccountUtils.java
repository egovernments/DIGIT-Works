package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.*;
import org.egov.web.models.bankaccount.BankAccount;
import org.egov.web.models.bankaccount.BankAccountResponse;
import org.egov.web.models.bankaccount.BankAccountSearchCriteria;
import org.egov.web.models.bankaccount.BankAccountSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class BankAccountUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IfmsAdapterConfig config;

	public @Valid List<BankAccount> getBankAccountsByIdentifier(RequestInfo requestInfo, List<String> identifiers, String tenantId) throws Exception {
		log.info("Fetching Bank account details");
		List<String> billIds = new ArrayList<>();
		BankAccountSearchCriteria bankAccountSearchCriteria = null;
		if (requestInfo != null && identifiers != null && !identifiers.isEmpty()) {
			Set<String> uniqueIdentifires = new HashSet<>(identifiers);
			bankAccountSearchCriteria = BankAccountSearchCriteria.builder()
					.tenantId(tenantId)
					.referenceId(new ArrayList<>(uniqueIdentifires))
					.build();
		} else {
			throw new Exception("");
		}

		Pagination pagination = Pagination.builder()
				.limit(bankAccountSearchCriteria.getReferenceId().size())
				.offSet(0)
				.order(Pagination.OrderEnum.ASC)
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
