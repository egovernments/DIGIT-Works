package org.egov.digit.expense.service;

import java.util.Arrays;
import java.util.List;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.Producer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.EncrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillResponse;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.validators.BillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private BillValidator validator;
	
	@Autowired
	private EncrichmentUtil enrichmentUtil;
	
	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	/**
	 * Validates the Bill Request and sends to repository for create
	 * 
	 * @param billRequest
	 * @return
	 */
	public BillResponse create(BillRequest billRequest) {

		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;

		//validation  mdms
		enrichmentUtil.encrichBillWithUuidAndAudit(billRequest);
		
		producer.push(config.getBillCreateTopic(), billRequest);
		
		response = BillResponse.builder()
				.bills(Arrays.asList(billRequest.getBill()))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo,true))
				.build();
		
		return response;
	}
	
	/**
	 * 
	 * @param billRequest
	 * @return
	 */
	public BillResponse update(BillRequest billRequest) {

		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;
		
		// validation of update request 
		
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest);
		producer.push(config.getBillUpdateTopic(), billRequest);
		
		response = BillResponse.builder()
				.bills(Arrays.asList(billRequest.getBill()))
				.responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo,true))
				.build();
		
		return response;
	}
	
	/**
	 * method to search bills from DB based on bill search criteria
	 * @param billSearchRequest
	 * @return
	 */
	public BillResponse search(BillSearchRequest billSearchRequest) {
		
		validator.validateSearchRequest(billSearchRequest.getBillcriteria());
		List<Bill> bills = billRepository.search(billSearchRequest);
		ResponseInfo responseInfo = responseInfoFactory.
		createResponseInfoFromRequestInfo(billSearchRequest.getRequestInfo(),true);
		
		BillResponse response = BillResponse.builder()
				.bills(bills)
				.responseInfo(responseInfo)
				.build();
		return response;
	}
	
	
}
