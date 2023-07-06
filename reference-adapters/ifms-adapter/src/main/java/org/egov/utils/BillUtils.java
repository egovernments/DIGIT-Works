package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.Pagination;
import org.egov.web.models.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

import static org.egov.config.Constants.MDMS_EXPENSE_MODULE_NAME;
import static org.egov.config.Constants.MDMS_HEAD_CODES_MASTER;

@Component
@Slf4j
public class BillUtils {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IfmsAdapterConfig config;
	@Autowired
	MdmsUtils mdmsUtils;

	public @Valid List<Bill> fetchBillsFromPayment(PaymentRequest paymentRequest) {
		RequestInfo requestInfo = paymentRequest.getRequestInfo();
		List<String> billIds = new ArrayList<>();
		if (requestInfo != null && paymentRequest.getPayment().getBills() != null && !paymentRequest.getPayment().getBills().isEmpty()) {
			for (PaymentBill bill : paymentRequest.getPayment().getBills()) {
				billIds.add(bill.getBillId());
			}
		}
		String tenantId = paymentRequest.getPayment().getTenantId();
		BillCriteria billCriteria = BillCriteria.builder()
				.ids(new HashSet<>(billIds))
				.tenantId(tenantId)
				.build();

		BillSearchRequest billSearchRequest = BillSearchRequest.builder().requestInfo(requestInfo).billCriteria(billCriteria).build();


		return fetchBillsData(billSearchRequest);
	}

	public @Valid List<Bill> fetchBillsData(Object billRequest) {
		StringBuilder uri = new StringBuilder();
		uri.append(config.getBillHost()).append(config.getBillSearchEndPoint());
		Object response = new HashMap<>();
		BillResponse billResponse = new BillResponse();
		try {
			response = restTemplate.postForObject(uri.toString(), billRequest, Map.class);
			billResponse = mapper.convertValue(response, BillResponse.class);
		} catch (Exception e) {
			log.error("Exception occurred while fetching bill lists from bill service: ", e);
		}

		log.info(billResponse.toString());
		return billResponse.getBills();
	}

	public @Valid List<Payment> updatePaymentsData(Object paymentRequest) {
		StringBuilder uri = new StringBuilder();
		uri.append(config.getBillHost()).append(config.getPaymentUpdateEndPoint());
		Object response = new HashMap<>();
		PaymentResponse paymentResponse = new PaymentResponse();
		try {
			response = restTemplate.postForObject(uri.toString(), paymentRequest, Map.class);
			paymentResponse = mapper.convertValue(response, PaymentResponse.class);
		} catch (Exception e) {
			log.error("Exception occurred while fetching bill lists from bill service: ", e);
		}

		log.info(paymentResponse.toString());
		return paymentResponse.getPayments();
	}

	public @Valid Object fetchBillFromCalculator(PaymentRequest paymentRequest, List<String> billNumbers) {
		RequestInfo requestInfo = paymentRequest.getRequestInfo();
		String tenantId = paymentRequest.getPayment().getTenantId();
		Map<String, Object> searchCriteria = new HashMap<>();
		searchCriteria.put("tenantId", tenantId);
		searchCriteria.put("billNumbers", billNumbers);
		Map<String, Object> requestParams = new HashMap<>();
		requestParams.put("RequestInfo", requestInfo);
		requestParams.put("searchCriteria", searchCriteria);
		Pagination pagination = Pagination.builder().limit(billNumbers.size()).offSet(0).build();
		requestParams.put("pagination", pagination);

		return fetchBillCalculatorData(requestParams);
	}

	public @Valid Object fetchBillCalculatorData(Object billRequest) {
		StringBuilder uri = new StringBuilder();
		uri.append(config.getBillCalculatorHost()).append(config.getBillCalculatorSearchEndPoint());
		Object response = new HashMap<>();
		Object billCalcResponse = new Object();
		try {
			response = restTemplate.postForObject(uri.toString(), billRequest, Map.class);
			billCalcResponse = mapper.convertValue(response, Object.class);
		} catch (Exception e) {
			log.error("Exception occurred while fetching bill lists from bill service: ", e);
		}

		log.info(billCalcResponse.toString());
		return billCalcResponse;
	}



	public JSONArray getHeadCode(RequestInfo requestInfo, String tenantId) {
		String rootTenantId = tenantId.split("\\.")[0];
		List<String> headCodeMasters = new ArrayList<>();
		headCodeMasters.add(MDMS_HEAD_CODES_MASTER);
		Map<String, Map<String, JSONArray>> headCodeResponse = mdmsUtils.fetchMdmsData(requestInfo, rootTenantId, MDMS_EXPENSE_MODULE_NAME, headCodeMasters);
		JSONArray ssuDetailsList = headCodeResponse.get(MDMS_EXPENSE_MODULE_NAME).get(MDMS_HEAD_CODES_MASTER);
		return ssuDetailsList;
	}

}
