package org.egov.digit.expense.service;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.workflow.ProcessInstance;
import org.egov.common.contract.workflow.ProcessInstanceResponse;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.util.EnrichmentUtil;
import org.egov.digit.expense.util.ResponseInfoFactory;
import org.egov.digit.expense.util.WorkflowUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BillService {
	
	private final ExpenseProducer expenseProducer;
	
	private final Configuration config;
	
	private final BillValidator validator;
	
	private final WorkflowUtil workflowUtil;
	
	private final BillRepository billRepository;
	
	private final EnrichmentUtil enrichmentUtil;
	
	private final ResponseInfoFactory responseInfoFactory;

	private final NotificationService notificationService;
	private final Configuration configs;

	@Autowired
	public BillService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator, WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, NotificationService notificationService, Configuration configs) {
		this.expenseProducer = expenseProducer;
		this.config = config;
		this.validator = validator;
		this.workflowUtil = workflowUtil;
		this.billRepository = billRepository;
		this.enrichmentUtil = enrichmentUtil;
		this.responseInfoFactory = responseInfoFactory;
		this.notificationService = notificationService;
        this.configs = configs;
    }

	/**
	 * Validates the Bill Request and sends to repository for create
	 * 
	 * @param billRequest
	 * @return
	 */
	public BillResponse create(BillRequest billRequest) {

		Bill bill = billRequest.getBill();
		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;

		validator.validateCreateRequest(billRequest);
		enrichmentUtil.encrichBillForCreate(billRequest);
		
		if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {

			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
			bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
			try {
				if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.SUPERVISION"))
					notificationService.sendNotificationForSupervisionBill(billRequest);
			}catch (Exception e){
				log.error("Exception while sending notification: " + e);
			}
		} else {
			bill.setStatus(Status.ACTIVE);
		}
		if (config.isBillBreakdownEnabled() && bill.getBillDetails().size() > config.getBillBreakdownSize()) {
			produceBillsBatchWise(billRequest);
		} else {
			expenseProducer.push(config.getBillCreateTopic(), billRequest);
		}
		
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

		Bill bill = billRequest.getBill();
		RequestInfo requestInfo = billRequest.getRequestInfo();
		BillResponse response = null;

		List<Bill> billsFromSearch = validator.validateUpdateRequest(billRequest);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
		if (validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {

			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
			bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
		}
		try {
			if (billRequest.getBill().getBusinessService().equalsIgnoreCase("EXPENSE.PURCHASE"))
				notificationService.sendNotificationForPurchaseBill(billRequest);
		}catch (Exception e){
			log.error("Exception while sending notification: " + e);
		}

		if (config.isBillBreakdownEnabled() && bill.getBillDetails().size() > config.getBillBreakdownSize()) {
			List<BillDetail> allBillDetails = new ArrayList<>(bill.getBillDetails());
			for (int i = 0; i < allBillDetails.size(); i += configs.getBillBreakdownSize()) {
				List<BillDetail> currBatchBillDetails = allBillDetails.subList(i, Math.min(i + configs.getBillBreakdownSize(), allBillDetails.size()));
				bill.setBillDetails(currBatchBillDetails);
				expenseProducer.push(configs.getBillUpdateTopic(), billRequest);
			}
		} else {
			expenseProducer.push(config.getBillUpdateTopic(), billRequest);
		}

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
	public BillResponse search(BillSearchRequest billSearchRequest, boolean isWfEncrichRequired) {
		
		BillCriteria billCriteria=billSearchRequest.getBillCriteria();

		log.info("Validate billCriteria Parameters BillCriteria : "+billCriteria);
		validator.validateSearchRequest(billSearchRequest);

		log.info("Enrich billCriteria");
		enrichmentUtil.enrichSearchBillRequest(billSearchRequest);

		log.info("Search repository using billCriteria");
		List<Bill> bills = billRepository.search(billSearchRequest, false);
		Integer totalBills = billRepository.searchCount(billSearchRequest);
		billSearchRequest.getPagination().setTotalCount(totalBills);

		ResponseInfo responseInfo = responseInfoFactory.
		createResponseInfoFromRequestInfo(billSearchRequest.getRequestInfo(),true);

		if (!config.isHealthContextEnabled() && isWfEncrichRequired  && bills != null && !bills.isEmpty())
			enrichWfstatusForBills(bills, billCriteria.getTenantId(), billSearchRequest.getRequestInfo());

		if (config.isHealthContextEnabled() && !StringUtils.isEmpty(billCriteria.getLocalityCode())) {
			bills.stream().forEach(bill -> bill.setLocalityCode(billCriteria.getLocalityCode()));
		}

		return BillResponse.builder()
				.bills(bills)
				.pagination(billSearchRequest.getPagination())
				.responseInfo(responseInfo)
				.build();
	}

	private void enrichWfstatusForBills(List<Bill> bills, String tenantId, RequestInfo requestInfo) {

		List<String> billNumbers = bills.stream().map(Bill::getBillNumber).collect(Collectors.toList());
		ProcessInstanceResponse response = workflowUtil.searchWorkflowForBusinessIds(billNumbers, tenantId,
				requestInfo);

		Map<String, String> busnessIdToWfStatus = response.getProcessInstances().stream().collect(Collectors
				.toMap(ProcessInstance::getBusinessId, processInstance -> processInstance.getState().getState()));
		for (Bill bill : bills) {
			bill.setWfStatus(busnessIdToWfStatus.get(bill.getBillNumber()));
		}
	}
	/**
	 * Breakdown the billDetails into batches and push to kafka. This is needed
	 * to avoid large payload in kafka.
	 *
	 * @param billRequest The bill request object
	 */
	private void produceBillsBatchWise(BillRequest billRequest) {
		Bill bill = billRequest.getBill();
		List<BillDetail> allBillDetails = new ArrayList<>(bill.getBillDetails());
		// Breakdown the billDetails into batches and push to kafka
		for (int i = 0; i < allBillDetails.size(); i += configs.getBillBreakdownSize()) {
			// Breakdown bill details into batches and push to kafka topic
			List<BillDetail> currBatchBillDetails = allBillDetails.subList(i, Math.min(i + configs.getBillBreakdownSize(), allBillDetails.size()));
			bill.setBillDetails(currBatchBillDetails);
			expenseProducer.push(configs.getBillCreateTopic(), billRequest);
		}
		log.info("All bill details pushed to kafka");
		// Implementation specific to generate pdf
		if (config.isHealthContextEnabled()) {
			Map<String, Object> pdfGenerationObject = new HashMap<>();
			pdfGenerationObject.put("id", bill.getId());
			pdfGenerationObject.put("createdTime", System.currentTimeMillis());
			pdfGenerationObject.put("numberOfBillDetails", bill.getBillDetails().size());
			expenseProducer.push(configs.getExpenseExcelPdfGenerateTopic(), pdfGenerationObject);
			log.info("Excel/PDF generation request pushed to kafka");
		}
	}
}
