package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ServiceRequestRepository;
import org.egov.web.models.bill.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

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

}
