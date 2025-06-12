package org.egov.digit.expense.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.util.*;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MTNService {

	private final ExpenseProducer expenseProducer;

	private final Configuration config;

	private final BillValidator validator;

	private final WorkflowUtil workflowUtil;

	private final BillRepository billRepository;

	private final EnrichmentUtil enrichmentUtil;

	private final ResponseInfoFactory responseInfoFactory;

	private final IndividualUtil individualUtil;

	private final TaskRepository taskRepository;

	private final MTNUtil mtnUtil;

	@Autowired
	public MTNService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator, WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, IndividualUtil individualUtil, TaskRepository taskRepository, MTNUtil mtnUtil) {
		this.expenseProducer = expenseProducer;
		this.config = config;
		this.validator = validator;
		this.workflowUtil = workflowUtil;
		this.billRepository = billRepository;
		this.enrichmentUtil = enrichmentUtil;
		this.responseInfoFactory = responseInfoFactory;
		this.individualUtil = individualUtil;
		this.taskRepository = taskRepository;
		this.mtnUtil = mtnUtil;
	}

	private Task createTask(BillTaskRequest billTaskRequest, Task.Type type){
		Bill bill = billTaskRequest.getBill();

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(billTaskRequest.getRequestInfo())
				.bill(bill)
				.build();
		List<Bill> billsFromSearch = getBills(billRequest,false);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
		Bill billFromSearch = billsFromSearch.get(0);

		Task task =
				Task.builder()
						.id(UUID.randomUUID().toString())
						.type(type)
						.auditDetails(billTaskRequest.getBill().getAuditDetails())
						.status(Status.IN_PROGRESS).build();

		TaskRequest taskRequest =
				TaskRequest.builder()
						.task(task)
						.bill(billFromSearch)
						.requestInfo(billTaskRequest.getRequestInfo())
						.build();

		expenseProducer.push(config.getBillTaskTopic(),taskRequest);

		return task;
	}

	public void executeTask(TaskRequest taskRequest){
		Task task = taskRequest.getTask();
		if (task.getType()==Task.Type.Verify){
			verify(taskRequest);
		} else if (task.getType()== Task.Type.Transfer){
			transfer(taskRequest);
		}
	}

	public BillTaskResponse verify(BillTaskRequest billTaskRequest){

		Task task = createTask(billTaskRequest, Task.Type.Verify);

		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(),true);

		return BillTaskResponse.builder()
				.responseInfo(responseInfo)
				.taskId(task.getId())
				.build();
	}

	public void verify(TaskRequest taskRequest){

		Bill bill = taskRequest.getBill();
		Task task = taskRequest.getTask();

		boolean fullyVerified = true;
		for (BillDetail billDetail: bill.getBillDetails() ){
			IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(),bill.getTenantId(),billDetail.getPayee().getIdentifier());
			TaskDetails taskDetails = TaskDetails.builder()
					.id(UUID.randomUUID().toString())
					.taskId(task.getId())
					.billId(bill.getId())
					.billDetailsId(billDetail.getId())
					.payeeId(billDetail.getPayee().getId())
					.status(Status.IN_PROGRESS)
					.tenantId(bill.getTenantId())
					.auditDetails(bill.getAuditDetails())
					.referenceId(individualDetails.getPhoneNumber())
					.build();
			try {
				String name = mtnUtil.getNameIfActive(individualDetails.getPhoneNumber());

				if (name != individualDetails.getName()){
					fullyVerified = false;
					billDetail.setStatus(Status.VERIFICATION_FAILED);
					taskDetails.setReasonForFailure("NAME_MISMATCH");
					taskDetails.setResponseMessage("Please check the name");
				} else{
					billDetail.setStatus(Status.VERIFIED);
				}
			} catch(CustomException e) {
				taskDetails.setResponseMessage(e.getMessage());
				taskDetails.setReasonForFailure(e.getCode());
			}
			taskDetails.setStatus(Status.DONE);
			expenseProducer.push(config.getBillTaskDetailsTopic(),taskDetails);
		}
		List<BillDetail> verifiedBillDetails = bill.getBillDetails()
				.stream()
				.filter(billDetail -> billDetail.getStatus() == Status.VERIFIED)
				.collect(Collectors.toList());

		if (verifiedBillDetails.size() == bill.getBillDetails().size()) {
			bill.setStatus(Status.FULLY_VERIFIED);
		} else if (!verifiedBillDetails.isEmpty()){
			bill.setStatus(Status.PARTIALLY_VERIFIED);
		} else {
			bill.setStatus(Status.PENDING_EDIT);
		}
		task.setStatus(Status.DONE);

		BillRequest billRequest = BillRequest.builder()
				.bill(bill)
				.requestInfo(taskRequest.getRequestInfo())
				.build();
		expenseProducer.push(config.getBillUpdateTopic(), billRequest);
		expenseProducer.push(config.getTaskStatusUpdateTopic(),task);
	}

	private List<Bill> getBills(BillRequest billRequest, Boolean isCreate) {

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

	public StatusResponse getTaskStatus(StatusRequest statusRequest){
		Task task = taskRepository.searchTask(statusRequest.getTaskId());


		if(task.getType() == Task.Type.Transfer && task.getStatus() == Status.IN_PROGRESS){
			Bill bill = statusRequest.getBill();
			BillRequest billRequest = BillRequest.builder()
					.requestInfo(statusRequest.getRequestInfo())
					.bill(bill)
					.build();
			List<Bill> billsFromSearch = getBills(billRequest,false);
			enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
			Bill billFromSearch = billsFromSearch.get(0);
			Map<String, BillDetail> billDetailsById = new HashMap<>();
			List<BillDetail> billDetailsFromSearch = billFromSearch.getBillDetails();
			billDetailsFromSearch
					.forEach(billDetail -> {
						billDetailsById.put(billDetail.getId(),billDetail);
					});
			List<TaskDetails> taskDetails = taskRepository.searchTaskDetailsByTaskId(task.getId());
			for (TaskDetails taskDetail: taskDetails){
				if (taskDetail.getStatus() == Status.IN_PROGRESS) {
					BillDetail billDetail = billDetailsById.get(taskDetail.getBillDetailsId());
					try {
						PaymentTransferResponse paymentTransferResponse = mtnUtil.getTransferStatus(taskDetail.getId());
						if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESSFUL.toString())) {

							billDetail.setStatus(Status.PAID);
						} else if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.FAILED.toString())) {

							taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
							taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
							billDetail.setStatus(Status.PAYMENT_FAILED);
						} else {
							log.info("unknown response status: {} for task id: {}, task detail id: {}", paymentTransferResponse.getStatus(), task.getId(), taskDetail.getId());
							taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
						}
						taskDetail.setStatus(Status.DONE);
					} catch(Exception e){
						taskDetail.setReasonForFailure(e.getMessage());
						taskDetail.setResponseMessage(e.getLocalizedMessage());
						taskDetail.setStatus(Status.DONE);
						billDetail.setStatus(Status.PAYMENT_FAILED);
					}
					expenseProducer.push(config.getTaskDetailsUpdateTopic(),taskDetail);
				}

			}
			List<TaskDetails> taskDetailsInProgress = taskDetails
					.stream()
					.filter(taskDetail -> taskDetail.getStatus() == Status.IN_PROGRESS)
					.collect(Collectors.toList());
			if (taskDetailsInProgress.isEmpty()){
				task.setStatus(Status.DONE);
			}

			List<BillDetail> billDetailsForFailedPayments = billFromSearch
					.getBillDetails()
					.stream()
					.filter(billDetail -> billDetail.getStatus() == Status.PAYMENT_FAILED)
					.collect(Collectors.toList());
			if (billDetailsForFailedPayments.isEmpty()){
				billFromSearch.setStatus(Status.FULLY_PAID);
				task.setStatus(Status.DONE);
			} else if (billDetailsForFailedPayments.size() != billDetailsFromSearch.size()){
				billFromSearch.setStatus(Status.PARTIALLY_PAID);
			}

			billRequest.setBill(billFromSearch);
			expenseProducer.push(config.getBillUpdateTopic(), billRequest);
			expenseProducer.push(config.getTaskStatusUpdateTopic(),task);
		}
		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(statusRequest.getRequestInfo(),true);

		return StatusResponse
				.builder()
				.responseInfo(responseInfo)
				.status(task.getStatus())
				.build();

	}

	public BillTaskResponse transfer(@Valid BillTaskRequest billTaskRequest) {
		Task task = createTask(billTaskRequest, Task.Type.Transfer);

		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(),true);

		return BillTaskResponse.builder()
				.responseInfo(responseInfo)
				.taskId(task.getId())
				.build();
	}

	public void transfer(TaskRequest taskRequest){

		Bill bill = taskRequest.getBill();
		Task task = taskRequest.getTask();

		boolean fullyVerified = true;
		for (BillDetail billDetail: bill.getBillDetails() ){
			if(billDetail.getStatus()==Status.VERIFIED) {
				IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(),bill.getTenantId(),billDetail.getPayee().getIdentifier());
				TaskDetails taskDetails = TaskDetails.builder()
						.id(UUID.randomUUID().toString())
						.taskId(task.getId())
						.referenceId(individualDetails.getPhoneNumber())
						.billId(bill.getId())
						.billDetailsId(billDetail.getId())
						.payeeId(billDetail.getPayee().getId())
						.status(Status.IN_PROGRESS)
						.tenantId(bill.getTenantId())
						.auditDetails(bill.getAuditDetails())
						.build();

				PaymentTransferRequest paymentTransferRequest = createPaymentTransferRequest(billDetail, individualDetails.getPhoneNumber());
				try {
					mtnUtil.transferIfAccountIsActive(paymentTransferRequest,taskDetails.getId());
				} catch(CustomException e) {
					taskDetails.setResponseMessage(e.getMessage());
					taskDetails.setReasonForFailure(e.getCode());
				}
				expenseProducer.push(config.getBillTaskDetailsTopic(),taskDetails);
			}
		}
	}

	private PaymentTransferRequest createPaymentTransferRequest(BillDetail billDetail, String partyId){
		return PaymentTransferRequest.builder()
				.amount(String.valueOf(billDetail.getTotalAmount()))
				.currency(config.getPaymentCurrency())
				.externalId(billDetail.getId())
				.payee(PaymentTransferRequest.Payee.builder()
						.partyId(partyId)
						.partyIdType("MSISDN")
						.build())
				.build();


	}

	public TaskDetailsResponse getTaskDetails(TaskDetailsRequest taskDetailsRequest){
		List<TaskDetails> taskDetails = new ArrayList<>();
		if (taskDetailsRequest.getBillDetailsId() != null) {
			 taskDetails.add(taskRepository.searchTaskDetails(taskDetailsRequest));
		}else {
			taskDetails = taskRepository.searchTaskDetailsByTaskId(taskDetailsRequest.getTaskId());
		}
		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(taskDetailsRequest.getRequestInfo(),true);

		return TaskDetailsResponse.builder()
				.responseInfo(responseInfo)
				.taskDetails(taskDetails)
				.build();
	}
}
