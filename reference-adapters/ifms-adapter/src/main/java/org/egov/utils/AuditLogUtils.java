package org.egov.utils;

import static org.egov.config.Constants.BANK_ACCOUNT_DECRYPT_KEY;
import static org.egov.config.Constants.BANK_IFSC_TABLE_NAME;
import static org.egov.config.Constants.REQUEST_INFO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.http.client.utils.URIBuilder;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Constants;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.service.EncryptionService;
import org.egov.web.models.bankaccount.AuditLogsResponse;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.works.services.common.models.bankaccounts.BankAccount;
import org.egov.works.services.common.models.bankaccounts.BankAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * This class fetches bank account audit log details, fetches the relevant bank account, decrypts it
 * and provides a response.
 *
 */
@Component
@Slf4j
public class AuditLogUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IfmsAdapterConfig config;
	@Autowired
	EncryptionService encryptionService;

	public @Valid List<Object> fetchAuditLogs(RequestInfo requestInfo, String tenantId, String objectId, int limit, int offset) throws IOException {
		log.info("Fetching data from audit log service.");
		StringBuilder uri = new StringBuilder();
		uri.append(config.getAuditLogHost()).append(config.getAuditLogSearchEndPoint());
		URIBuilder builder = new URIBuilder();
		builder.addParameter("tenantId", tenantId);
		builder.addParameter("offset", String.valueOf(offset));
		builder.addParameter("limit", String.valueOf(limit));
		builder.addParameter("objectId", objectId);
		String query =  builder.toString();
		uri.append(query);
		Map<String, Object> auditLogRequest = new HashMap<>();
		auditLogRequest.put(REQUEST_INFO, requestInfo);
		Object response = new HashMap<>();
		AuditLogsResponse auditLogsResponse = new AuditLogsResponse();
		try {
			response = restTemplate.postForObject(uri.toString(), auditLogRequest, Map.class);
			auditLogsResponse = mapper.convertValue(response, AuditLogsResponse.class);
			log.info("Fetched audit details from audit service.");
		} catch (Exception e) {
			log.error("Exception occurred while fetching bill lists from bill service: ", e);
		}

		log.info(response.toString());
		List<Object> auditLogs = auditLogsResponse.getAuditLogs();
		return auditLogs;
	}

	/**
	 * If bank account table details change, then this method needs to be modified. Audit logs for a particular entity
	 * returns the table names as the entityID. Based on this, details from appropriate entity are returned. This means that if the 
	 * entityID (table name) changes, we will have problems. TODO: Needs to be reviewed and confirmed with core team.
	 * 
	 * @param paymentRequest
	 * @param bankAccount
	 * @param time
	 * @return
	 */
	public Map<String, String> getLastUpdatedBankAccountDetailsFromAuditLogFromTime(PaymentRequest paymentRequest, BankAccount bankAccount, long time) {
		String bankAccountNumber = null;
		String bankIFSCCode = null;
		try {
			List<Object> auditLogResponse = fetchAuditLogs(paymentRequest.getRequestInfo(), paymentRequest.getPayment().getTenantId(), bankAccount.getId(), 1000, 0);
			if (auditLogResponse != null && !auditLogResponse.isEmpty()) {
				// Convert List<Object> to List<JsonNode>
				List<JsonNode> auditLogs = new ArrayList<>();
				for (Object obj : auditLogResponse) {
					String json = mapper.writeValueAsString(obj);
					JsonNode jsonNode = mapper.readTree(json);
					auditLogs.add(jsonNode);
				}
				// Sort the List<JsonNode> based on the "name" key
				Collections.sort(auditLogs, new JsonNodeComparator("changeDate"));
				Collections.reverse(auditLogs);
				log.info(" " + auditLogs);
				for (JsonNode auditLog : auditLogs) {
					long changeDate = auditLog.get("changeDate").asLong();
					if (changeDate > time)
						continue;
					String entityName = auditLog.get("entityName").asText();
					if (bankAccountNumber == null) {
						if (entityName.equalsIgnoreCase(Constants.BANK_ACCOUNT_TABLE_NAME))
							bankAccountNumber = auditLog.at("/keyValueMap/accountNumber").asText();
					}
					if (bankIFSCCode == null) {
						if (entityName.equalsIgnoreCase(BANK_IFSC_TABLE_NAME))
							bankIFSCCode = auditLog.at("/keyValueMap/code").asText();
					}
				}
			}
		} catch (Exception e) {
			log.info("Exception in AuditLogUtils:getLastUpdatedBankAccountDetailsFromAuditLogFromTime" + e);
		}
		// Decrypt bank account number
		if (bankAccountNumber != null) {
			bankAccountNumber = decryptBankAccountNumber(bankAccount, bankAccountNumber, paymentRequest.getRequestInfo());
		}

		Map<String, String> map = new HashMap<>();
		map.put("bankAccountNumber", bankAccountNumber);
		map.put("bankIFSCCode", bankIFSCCode);
		return map;
	}

	private String decryptBankAccountNumber(BankAccount bankAccount, String encryptedBankAccountNumber, RequestInfo requestInfo) {
		String bankAccountNumber = null;
		try {
			BankAccountDetails bankAccountDetails = BankAccountDetails.builder()
					.id(bankAccount.getBankAccountDetails().get(0).getId())
					.accountNumber(encryptedBankAccountNumber)
					.build();
			BankAccount bankAccountNew = BankAccount.builder().bankAccountDetails(Collections.singletonList(bankAccountDetails)).build();
			bankAccountNew.getBankAccountDetails().get(0).setAccountNumber(encryptedBankAccountNumber);
			bankAccountNew.getBankAccountDetails().get(0).setAccountHolderName(null);
			List<BankAccount> decryptedBankAccountList = encryptionService.decrypt(Collections.singletonList(bankAccountNew),
					BANK_ACCOUNT_DECRYPT_KEY, requestInfo);
			if (decryptedBankAccountList != null && !decryptedBankAccountList.isEmpty()) {
				bankAccountNumber = decryptedBankAccountList.get(0).getBankAccountDetails().get(0).getAccountNumber();
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while decrypting bank account number.");
		}
		return bankAccountNumber;
	}

	static class JsonNodeComparator implements Comparator<JsonNode> {
		private String key;

		public JsonNodeComparator(String key) {
			this.key = key;
		}

		@Override
		public int compare(JsonNode node1, JsonNode node2) {
			String value1 = node1.get(key).asText();
			String value2 = node2.get(key).asText();
			return value1.compareTo(value2);
		}
	}

}
