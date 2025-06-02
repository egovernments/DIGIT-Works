package org.egov.digit.expense.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.BillVerificationTaskRepository;
import org.egov.digit.expense.util.*;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BillVerificationService {

	private final ExpenseProducer expenseProducer;

	private final Configuration config;

	private final BillValidator validator;

	private final WorkflowUtil workflowUtil;

	private final BillRepository billRepository;

	private final EnrichmentUtil enrichmentUtil;

	private final ResponseInfoFactory responseInfoFactory;

	private final IndividualUtil individualUtil;

	private final BillVerificationTaskRepository billVerificationTaskRepository;

	private final MTNUtil mtnUtil;

	@Autowired
	public BillVerificationService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator, WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, IndividualUtil individualUtil, BillVerificationTaskRepository billVerificationTaskRepository, MTNUtil mtnUtil) {
		this.expenseProducer = expenseProducer;
		this.config = config;
		this.validator = validator;
		this.workflowUtil = workflowUtil;
		this.billRepository = billRepository;
		this.enrichmentUtil = enrichmentUtil;
		this.responseInfoFactory = responseInfoFactory;
		this.individualUtil = individualUtil;
		this.billVerificationTaskRepository = billVerificationTaskRepository;
		this.mtnUtil = mtnUtil;
	}




	public BillVerificationResponse verify(BillVerificationRequest billverificationRequest){

		Bill bill = billverificationRequest.getBill();

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(billverificationRequest.getRequestInfo())
				.bill(bill)
				.build();
		List<Bill> billsFromSearch = getBillsForVerification(billRequest,false);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
		Bill billFromSearch = billsFromSearch.get(0);

		BillVerificationTask billverificationTask =
				BillVerificationTask.builder()
				.id(UUID.randomUUID().toString())
				.auditDetails(billverificationRequest.getBill().getAuditDetails())
				.status(Status.IN_PROGRESS).build();

		BillVerificationTaskRequest billVerificationTaskRequest =
				BillVerificationTaskRequest.builder()
				.taskId(billverificationTask.getId())
				.bill(billFromSearch)
				.build();

		expenseProducer.push(config.getBillVerificationTopic(),billVerificationTaskRequest);

		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(billverificationRequest.getRequestInfo(),true);

		return BillVerificationResponse.builder()
				.responseInfo(responseInfo)
				.taskId(billverificationTask.getId())
				.build();
	}

	public void verify(BillVerificationTaskRequest billVerificationTaskRequest){

		Bill bill = billVerificationTaskRequest.getBill();
		BillVerificationTask billVerificationTask = billVerificationTaskRepository.search(billVerificationTaskRequest.getTaskId());

		boolean fullyVerified = true;
		for (BillDetail billDetail: bill.getBillDetails() ){
			IndividualDetails individualDetails = individualUtil.getIndividualDetails(billVerificationTaskRequest.getRequestInfo(),bill.getTenantId(),billDetail.getPayee().getIdentifier());

			if (individualDetails.getName() != mtnUtil.getNameIfActive(individualDetails.getPhoneNumber())){
				fullyVerified = false;
				billDetail.setStatus(Status.VERIFICATION_FAILED);
			} else{
				billDetail.setStatus(Status.VERIFIED);
			}

		}
		if (fullyVerified) {
			bill.setStatus(Status.FULLY_VERIFIED);
		} else {
			bill.setStatus(Status.PARTIALLY_VERIFIED);
		}
		billVerificationTask.setStatus(Status.DONE);

		BillRequest billRequest = BillRequest.builder()
				.bill(bill)
				.requestInfo(billVerificationTaskRequest.getRequestInfo())
				.build();
		expenseProducer.push(config.getBillUpdateTopic(), billRequest);
		expenseProducer.push(config.getBillVerificationStatusUpdateTopic(),billVerificationTask);
	}

	private List<Bill> getBillsForVerification(BillRequest billRequest, Boolean isCreate) {

		Bill bill = billRequest.getBill();
		BillCriteria billCriteria = BillCriteria.builder()
				.statusNot(Status.INACTIVE.toString())
				.tenantId(bill.getTenantId())
				.build();

		if (Boolean.TRUE.equals(isCreate)) {

			billCriteria.setReferenceIds(Stream.of(bill.getReferenceId()).collect(Collectors.toSet()));
			billCriteria.setBusinessService(bill.getBusinessService());
		} else {

			billCriteria.setIds(Stream.of(bill.getId()).collect(Collectors.toSet()));
		}


		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.requestInfo(billRequest.getRequestInfo())
				.billCriteria(billCriteria)
				.build();

		return billRepository.search(billSearchRequest, true);
	}

	public BillVerificationStatusResponse getBillVerificationStatus(BillVerificationStatusRequest billVerificationStatusRequest){
		BillVerificationTask billVerificationTask = billVerificationTaskRepository.search(billVerificationStatusRequest.getTaskId());

		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(billVerificationStatusRequest.getRequestInfo(),true);

		return BillVerificationStatusResponse
				.builder()
				.responseInfo(responseInfo)
				.status(billVerificationTask.getStatus())
				.build();

	}
}
