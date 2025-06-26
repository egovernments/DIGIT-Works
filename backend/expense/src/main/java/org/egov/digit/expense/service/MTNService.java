package org.egov.digit.expense.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.contract.workflow.State;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.kafka.ExpenseProducer;
import org.egov.digit.expense.repository.BillRepository;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.util.*;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.Actions;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

	private final ExecutorService executorService;

	@Autowired
	public MTNService(ExpenseProducer expenseProducer, Configuration config, BillValidator validator, WorkflowUtil workflowUtil, BillRepository billRepository, EnrichmentUtil enrichmentUtil, ResponseInfoFactory responseInfoFactory, IndividualUtil individualUtil, TaskRepository taskRepository, MTNUtil mtnUtil,ExecutorService executorService) {
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
		this.executorService=executorService;
	}

	private Task fetchOrCreateTask(BillTaskRequest billTaskRequest, Task.Type type){
		Bill bill = billTaskRequest.getBill();
		Task task = Task
				.builder()
					.billId(bill.getId())
					.type(type)
					.status(Status.IN_PROGRESS)
				.build();
		Task taskDb = taskRepository.searchTask(task);
		if (taskDb != null){
			return taskDb;
		}

		BillRequest billRequest = BillRequest.builder()
				.requestInfo(billTaskRequest.getRequestInfo())
				.bill(bill)
				.build();
		List<Bill> billsFromSearch = getBills(billRequest,false);
		enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
		Bill billFromSearch = billsFromSearch.get(0);

		task.setId(UUID.randomUUID().toString());

		task.setAuditDetails(billTaskRequest.getBill().getAuditDetails());


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

		Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Verify);

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


		for (BillDetail billDetail: bill.getBillDetails() ){
			if (billDetail.getStatus() == Status.PENDING_VERIFICATION ||
					billDetail.getStatus() == Status.EDITED	) {
				IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(), bill.getTenantId(), billDetail.getPayee().getIdentifier());
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
				boolean updateBillDetailWorkflow = true;
				Workflow workflow = Workflow.builder().build();
				try {
					String name = mtnUtil.getNameIfActive(individualDetails.getPhoneNumber());

					if (!name.equals(individualDetails.getName())) {
						workflow.setAction(Actions.REFUTE.toString());
						taskDetails.setReasonForFailure("NAME_MISMATCH");
						taskDetails.setResponseMessage("Please check the name");
					} else {
						workflow.setAction(Actions.VERIFY.toString());
					}
				} catch (CustomException e) {
					taskDetails.setResponseMessage(e.getMessage());
					taskDetails.setReasonForFailure(e.getCode());
				}
				taskDetails.setStatus(Status.DONE);
				expenseProducer.push(config.getBillTaskDetailsTopic(), taskDetails);

				ErrorDetails errorDetails = ErrorDetails.builder()
						.reasonForFailure(taskDetails.getReasonForFailure())
						.responseMessage(taskDetails.getResponseMessage())
						.response(taskDetails.getAdditionalDetails()).build();
				billDetail.setAdditionalDetails(errorDetails);
				try {
					setBillDetailStatus(billDetail, workflow, taskRequest.getRequestInfo());
				} catch(HttpClientErrorException e){
					log.error("Error in updating workflow state change for billDetail Id: {}, bill number : {}, from status: {} to action: {}"
							, billDetail.getId(), bill.getBillNumber(), billDetail.getStatus(),workflow.getAction(),e);
				}
			}
		}
		if (bill.getStatus() == Status.PENDING_VERIFICATION
				|| bill.getStatus() == Status.PARTIALLY_VERIFIED
				|| bill.getStatus() == Status.PARTIALLY_PAID
		) {
			List<BillDetail> verifiedBillDetails = bill.getBillDetails()
					.stream()
					.filter(billDetail -> billDetail.getStatus() == Status.VERIFIED
													|| billDetail.getStatus() == Status.PAID)
					.collect(Collectors.toList());
			boolean isWorkflowChange = true;
			Workflow workflow = Workflow.builder()
					.build();
			if (verifiedBillDetails.size() == bill.getBillDetails().size()) {
				workflow.setAction(Actions.VERIFY.toString());
			} else if (!verifiedBillDetails.isEmpty() && bill.getStatus() != Status.PARTIALLY_VERIFIED) {
				workflow.setAction(Actions.PARTIALLY_VERIFY.toString());
			} else {
				log.info("No workflow state change for bill number : {}, task id: {}", bill.getBillNumber(), task.getId());
				isWorkflowChange = false;
			}

			BillRequest billRequest = BillRequest.builder()
					.bill(bill)
					.requestInfo(taskRequest.getRequestInfo())
					.workflow(workflow)
					.build();

			updateBill(billRequest, isWorkflowChange);
		}
		task.setStatus(Status.DONE);
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

		Task taskFromRequest = statusRequest.getTask();
		if (null == taskFromRequest.getId()
				&& (null == taskFromRequest.getBillId()
						|| null == taskFromRequest.getType())){
			throw new CustomException("TASK_SEARCH_ERROR", "either task id or bill id with type is required for search");
		}
		Task taskFromSearch = taskRepository.searchTask(taskFromRequest);


		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(statusRequest.getRequestInfo(),true);

		return StatusResponse
				.builder()
				.responseInfo(responseInfo)
				.task(taskFromSearch)
				.build();

	}

	public BillTaskResponse transfer(@Valid BillTaskRequest billTaskRequest) {
		Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Transfer);

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

		executorService.scheduleTask( () -> {
			updatePaymentTaskStatus(taskRequest);
		}, Integer.valueOf(config.getScheduledTaskDelay()),TimeUnit.SECONDS);


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

	private void updateBill(BillRequest billRequest,boolean isWorkflowChange){
		Bill bill = billRequest.getBill();
		try {
			if (isWorkflowChange &&
					validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
				State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
				bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
			}
		} catch (HttpClientErrorException e){
			log.error("Error in updating workflow state change for bill number : {} from status: {}", bill.getBillNumber(), bill.getStatus(),e);
		}
		expenseProducer.push(config.getBillUpdateTopic(), billRequest);
	}

	private void setBillDetailStatus(BillDetail billDetail, Workflow workflow, RequestInfo requestInfo) {
		if (validator.isWorkflowActiveForBusinessService(config.getBillDetailBusinessService())) {
			BillDetailRequest billDetailRequest = BillDetailRequest.builder()
					.billDetail(billDetail)
					.businessService(config.getBillDetailBusinessService())
					.workflow(workflow)
					.requestInfo(requestInfo)
					.build();
			State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
			billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
		}
	}

	public BillDetailResponse updateBillDetailStatus(BillRequest billRequest){
		Bill billFromRequest = billRequest.getBill();
		BillCriteria billCriteria = BillCriteria.builder()
				.ids(Set.of(billFromRequest.getId()))
				.tenantId(billFromRequest.getTenantId())
				.build();
		BillSearchRequest billSearchRequest = BillSearchRequest.builder()
				.requestInfo(billRequest.getRequestInfo())
				.billCriteria(billCriteria)
				.build();

		List<Bill> billsFromSearch = billRepository.search(billSearchRequest, true);
		Bill billFromSearch = billsFromSearch.get(0);

		Map<String, BillDetail> billDetailsFromRequestById
				= billFromRequest.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId,billDetail -> billDetail));

		Map<String, BillDetail> billDetailsFromSearchById
				= billFromSearch.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId,billDetail -> billDetail));

		Map<String, BillDetail> billDetailsToBeUpdatedById = new HashMap<>();
		for (String id: billDetailsFromRequestById.keySet()){
			billDetailsToBeUpdatedById.put(id,billDetailsFromSearchById.get(id));
		}

		BillDetailRequest billDetailRequest
				= BillDetailRequest
					.builder()
						.requestInfo(billRequest.getRequestInfo())
						.businessService(config.getBillDetailBusinessService())
						.workflow(billRequest.getWorkflow())
					.build();

		List<BillDetail> updatedBillDetails = new ArrayList<>();
		billDetailsToBeUpdatedById
				.values()
					.forEach( billDetail -> {
						try {
							billDetailRequest.setBillDetail(billDetail);
							State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
							billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
							updatedBillDetails.add(billDetail);
						} catch (HttpClientErrorException e){
							log.error("error updating workflow status for bill number: {}, billdetail id: {}, status: {}, action: {}",
									billFromSearch.getBillNumber(), billDetail.getId(),billDetail.getStatus(), billRequest.getWorkflow().getAction(),e);
						}
					}
				);
		BillRequest billUpdateRequest
				= BillRequest
				.builder()
					.requestInfo(billDetailRequest.getRequestInfo())
					.bill(billFromSearch)
				.build();
		updateBill(billUpdateRequest,false);

		ResponseInfo responseInfo = responseInfoFactory.
				createResponseInfoFromRequestInfo(billDetailRequest.getRequestInfo(),true);
		return BillDetailResponse
				.builder()
				.responseInfo(responseInfo)
				.billDetails(updatedBillDetails)
				.build();

	}

	public void updatePaymentTaskStatus(TaskRequest taskRequest){

        Task task = taskRequest.getTask();
		Bill bill = taskRequest.getBill();
		log.info("updating payment status for task {}, bill number {}",task.getId(), bill.getBillNumber());

		Map<String, BillDetail> billDetailsById = bill.getBillDetails().stream()
				.collect(Collectors.toMap(BillDetail::getId,billDetail -> billDetail));

		List<TaskDetails> taskDetails = taskRepository.searchTaskDetailsByTaskId(task.getId());
		for (TaskDetails taskDetail: taskDetails){
			BillDetail billDetail = billDetailsById.get(taskDetail.getBillDetailsId());
			if (taskDetail.getStatus() == Status.IN_PROGRESS && billDetail.getStatus() == Status.VERIFIED) {
				Workflow billDetailWorkflow = Workflow.builder().build();
				try {
					PaymentTransferResponse paymentTransferResponse = mtnUtil.getTransferStatus(taskDetail.getId());
					if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESSFUL.toString())) {
						billDetailWorkflow.setAction(Actions.MAKE_PAYMENT.toString());
					} else if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.FAILED.toString())) {
						billDetailWorkflow.setAction(Actions.FAIL_PAYMENT.toString());
					} else {
						log.info("unknown response status: {} for bill bumber : {}, task id: {}, task detail id: {}", paymentTransferResponse.getStatus(),bill.getBillNumber(), task.getId(), taskDetail.getId());
					}
					taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
					taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
					taskDetail.setStatus(Status.DONE);

				} catch (CustomException e) {
					log.error("error in fetching payment transfer status from mtn for bill number : {}, billDetail: {},task: {}, taskDetail: {}",
							bill.getBillNumber(),billDetail.getId(),task.getId(),taskDetail.getId(),e);
					taskDetail.setReasonForFailure(e.getMessage());
					taskDetail.setResponseMessage(e.getLocalizedMessage());
					taskDetail.setStatus(Status.DONE);
					billDetailWorkflow.setAction(Actions.FAIL_PAYMENT.toString());
				}
				expenseProducer.push(config.getTaskDetailsUpdateTopic(), taskDetail);
				ErrorDetails errorDetails = ErrorDetails.builder()
						.reasonForFailure(taskDetail.getReasonForFailure())
						.responseMessage(taskDetail.getResponseMessage())
						.response(taskDetail.getAdditionalDetails()).build();
				billDetail.setAdditionalDetails(errorDetails);
				try {
					setBillDetailStatus(billDetail, billDetailWorkflow, taskRequest.getRequestInfo());
				} catch(HttpClientErrorException e){
					log.error("Error in updating workflow state change for billDetail Id: {}, bill number : {}, from status: {} to action: {}"
							, billDetail.getId(), bill.getBillNumber(), billDetail.getStatus(),billDetailWorkflow.getAction(),e);
				}
			}
		}
		if (bill.getStatus() == Status.PARTIALLY_VERIFIED || bill.getStatus() == Status.FULLY_VERIFIED || bill.getStatus() == Status.PARTIALLY_PAID) {
			List<BillDetail> paidBillDetails = bill
					.getBillDetails()
					.stream()
					.filter(billDetail -> billDetail.getStatus() == Status.PAID)
					.collect(Collectors.toList());
			Workflow workflow = Workflow.builder()
					.build();
			boolean isWorkflowChange = true;
			if (paidBillDetails.size() == bill.getBillDetails().size()) {
				workflow.setAction(Actions.MAKE_FULL_PAYMENT.toString());
			} else if (!paidBillDetails.isEmpty() && bill.getStatus() != Status.PARTIALLY_PAID) {
				workflow.setAction(Actions.MAKE_PARTIAL_PAYMENT.toString());
			} else {
				log.info("No workflow state change for bill number : {}, task id: {}", bill.getBillNumber(), task.getId());
				isWorkflowChange = false;
			}
			BillRequest billRequest = BillRequest
					.builder()
					.bill(bill)
					.workflow(workflow)
					.requestInfo(taskRequest.getRequestInfo())
					.build();
			updateBill(billRequest, isWorkflowChange);
		}
		task.setStatus(Status.DONE);
		expenseProducer.push(config.getTaskStatusUpdateTopic(),task);
		log.info("finished updating payment status for task {}, bill number {}",task.getId(), bill.getBillNumber());
	}

}
