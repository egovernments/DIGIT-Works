package org.egov.digit.expense.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
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
import org.egov.digit.expense.web.models.TaskStatusCheckRequest;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.digit.expense.web.validators.BillValidator;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.egov.digit.expense.config.Constants.ERROR;
import static org.egov.digit.expense.config.Constants.EXCEPTION;

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

    private Task fetchOrCreateTask(BillTaskRequest billTaskRequest, Task.Type type) {
        Bill billFromRequest = billTaskRequest.getBill();
        Task task = Task
                .builder()
                .billId(billFromRequest.getId())
                .type(type)
                .status(Status.IN_PROGRESS)
                .build();
        Task taskDb = taskRepository.searchTask(task);
        if (taskDb != null) {
            return taskDb;
        }

        task.setId(UUID.randomUUID().toString());
        task.setTenantId(billFromRequest.getTenantId());

        task.setAuditDetails(billTaskRequest.getBill().getAuditDetails());

        TaskRequest taskRequest =
                TaskRequest.builder()
                        .task(task)
                        .bill(billFromRequest)
                        .requestInfo(billTaskRequest.getRequestInfo())
                        .build();

        expenseProducer.push(billFromRequest.getTenantId(), config.getBillTaskTopic(), taskRequest);

        return task;
    }

    private Bill getBillfromSearch(Bill billFromRequest, RequestInfo requestInfo) {
        BillRequest billRequest =
                BillRequest.builder()
                        .requestInfo(requestInfo)
                        .bill(billFromRequest)
                        .build();
        List<Bill> billsFromSearch = getBills(billRequest, false);
        Bill billFromSearch = billsFromSearch.get(0);
        if (null == billFromRequest.getPayer()) {
            billFromRequest.setPayer(billFromSearch.getPayer());
        }
        if (null == billFromRequest.getBillDetails()) {
            billFromRequest.setBillDetails(billFromSearch.getBillDetails());
        }
        enrichmentUtil.encrichBillWithUuidAndAuditForUpdate(billRequest, billsFromSearch);
        return billFromSearch;
    }

    public void executeTask(TaskRequest taskRequest) {
        Task task = taskRequest.getTask();
        if (task.getType() == Task.Type.Verify) {
            verify(taskRequest);
        } else if (task.getType() == Task.Type.Transfer) {
            transfer(taskRequest);
        }
    }

    public BillTaskResponse verify(BillTaskRequest billTaskRequest) {

        Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Verify);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(), true);

        return BillTaskResponse.builder()
                .responseInfo(responseInfo)
                .taskId(task.getId())
                .build();
    }

    public void verify(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());

        Map<String, BillDetail> billDetailsToBeUpdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);


        for (BillDetail billDetail : billDetailsToBeUpdatedById.values()) {
            if (billDetail.getStatus() == Status.PENDING_VERIFICATION ||
                    billDetail.getStatus() == Status.EDITED) {
                IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(), billFromSearch.getTenantId(), billDetail.getPayee().getIdentifier());
                TaskDetails taskDetails = TaskDetails.builder()
                        .id(UUID.randomUUID().toString())
                        .taskId(task.getId())
                        .billId(billFromSearch.getId())
                        .billDetailsId(billDetail.getId())
                        .payeeId(billDetail.getPayee().getId())
                        .status(Status.IN_PROGRESS)
                        .tenantId(billFromSearch.getTenantId())
                        .auditDetails(billFromSearch.getAuditDetails())
                        .referenceId(individualDetails.getPhoneNumber())
                        .build();
                boolean updateBillDetailWorkflow = true;
                Workflow workflow = Workflow.builder().build();
                try {
                    boolean isActive = mtnUtil.isMsisdnActive(individualDetails.getPhoneNumber());

                    if (isActive) {
                        workflow.setAction(Actions.VERIFY.toString());
                    } else {
                        workflow.setAction(Actions.REFUTE.toString());
                        taskDetails.setReasonForFailure("MTN_ACCOUNT_INACTIVE_" + EXCEPTION);
                        taskDetails.setResponseMessage("Account is not active");
                    }

                } catch (CustomException e) {
                    log.error("Exception while verifying MSISDN : {}",
                            individualDetails.getPhoneNumber(), e);
                        workflow.setAction(Actions.REFUTE.toString());

                    taskDetails.setResponseMessage(e.getMessage());
                    taskDetails.setReasonForFailure(e.getCode());
                }
//                try {
//                    String name = mtnUtil.getNameIfActive(individualDetails.getPhoneNumber());
//
//                    if (!name.equalsIgnoreCase(individualDetails.getName())) {
//                        workflow.setAction(Actions.REFUTE.toString());
//                        taskDetails.setReasonForFailure("NAME_MISMATCH");
//                        taskDetails.setResponseMessage("Please check the name");
//                    } else {
//                        workflow.setAction(Actions.VERIFY.toString());
//                    }
//                } catch (CustomException e) {
//                    log.error("Exception while verifying MSISDN : {} ",individualDetails.getPhoneNumber(),e);
//                    if (Objects.equals(e.getCode(), "MTN_SERVICE_" + EXCEPTION)) {
//                        updateBillDetailWorkflow = false;
//                    } else {
//                        workflow.setAction(Actions.REFUTE.toString());
//                    }
//                    taskDetails.setResponseMessage(e.getMessage());
//                    taskDetails.setReasonForFailure(e.getCode());
//                }
                taskDetails.setStatus(Status.DONE);
                expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);

                Object additionalDetailsObj = billDetail.getAdditionalDetails();
                Map<String, Object> additionalDetails;

                try {
                    additionalDetails = new ObjectMapper().convertValue(
                            additionalDetailsObj,
                            new TypeReference<>() {
                            }
                    );
                } catch (IllegalArgumentException e) {
                    additionalDetails = new HashMap<>();
                }

                ErrorDetails errorDetails = ErrorDetails.builder()
                        .reasonForFailure(taskDetails.getReasonForFailure())
                        .responseMessage(taskDetails.getResponseMessage())
                        .response(taskDetails.getAdditionalDetails()).build();
                additionalDetails.put("errorDetails", errorDetails);
                billDetail.setAdditionalDetails(additionalDetails);
                try {
                    setBillDetailStatus(billDetail, workflow, taskRequest.getRequestInfo(), updateBillDetailWorkflow);
                } catch (HttpClientErrorException e) {
                    log.error("Error in updating workflow state change for billDetail Id: {}, bill number : {}, from status: {} to action: {}"
                            , billDetail.getId(), billFromSearch.getBillNumber(), billDetail.getStatus(), workflow.getAction(), e);
                }
            }
        }
        if (billFromSearch.getStatus() == Status.PENDING_VERIFICATION
                || billFromSearch.getStatus() == Status.PARTIALLY_VERIFIED
                || billFromSearch.getStatus() == Status.PARTIALLY_PAID
                || billFromSearch.getStatus() == Status.PAYMENT_FAILED
        ) {
            List<BillDetail> verifiedBillDetails = billFromSearch.getBillDetails()
                    .stream()
                    .filter(billDetail -> billDetail.getStatus() == Status.VERIFIED
                            || billDetail.getStatus() == Status.PAID)
                    .collect(Collectors.toList());
            boolean isWorkflowChange = true;
            Workflow workflow = Workflow.builder()
                    .build();
            if (verifiedBillDetails.size() == billFromSearch.getBillDetails().size()) {
                workflow.setAction(Actions.FULLY_VERIFY.toString());
            } else if (!verifiedBillDetails.isEmpty() &&
                    (billFromSearch.getStatus() == Status.PENDING_VERIFICATION || billFromSearch.getStatus() == Status.PAYMENT_FAILED)) {
                workflow.setAction(Actions.PARTIALLY_VERIFY.toString());
            } else {
                log.info("No workflow state change for bill number : {}, task id: {}", billFromSearch.getBillNumber(), task.getId());
                isWorkflowChange = false;
            }

            BillRequest billRequest = BillRequest.builder()
                    .bill(billFromSearch)
                    .requestInfo(taskRequest.getRequestInfo())
                    .workflow(workflow)
                    .build();

            updateBillWfStatus(billRequest, isWorkflowChange);
        }
        task.setStatus(Status.DONE);
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);
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

    public StatusResponse getTaskStatus(StatusRequest statusRequest) {

        Task taskFromRequest = statusRequest.getTask();
        if (null == taskFromRequest.getId()
                && (null == taskFromRequest.getBillId()
                || null == taskFromRequest.getType())) {
            throw new CustomException("TASK_SEARCH_" + EXCEPTION, "either task id or bill id with type is required for search");
        }
        Task taskFromSearch = taskRepository.searchTask(taskFromRequest);


        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(statusRequest.getRequestInfo(), true);

        return StatusResponse
                .builder()
                .responseInfo(responseInfo)
                .task(taskFromSearch)
                .build();

    }

    public MtnBalanceResponse getMtnBalanceResponse(MtnBalanceRequest mtnBalanceRequest) {
        try {
            MtnBalance mtnBalance = mtnUtil.getTotalAmountBalance();
            ResponseInfo responseInfo = responseInfoFactory.
                    createResponseInfoFromRequestInfo(mtnBalanceRequest.getRequestInfo(), true);

            return MtnBalanceResponse
                    .builder()
                    .responseInfo(responseInfo)
                    .mtnBalance(mtnBalance)
                    .build();
        } catch (CustomException e) {
            log.error("Error while retrieving balance", e);
            throw new CustomException(e.getCode(), e.getMessage());
        } catch (HttpClientErrorException e) {
            log.error("Error from MTN service", e);
            throw new CustomException("MTN_SERVICE_" + EXCEPTION, e.getMessage());
        } catch (Exception e) {
            log.error("Error while retrieving balance", e);
            throw new CustomException("MTN_ACCOUNT_BALANCE_" + EXCEPTION, e.getMessage());
        }

    }

    public BillTaskResponse transfer(@Valid BillTaskRequest billTaskRequest) {
        Task task = fetchOrCreateTask(billTaskRequest, Task.Type.Transfer);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billTaskRequest.getRequestInfo(), true);

        return BillTaskResponse.builder()
                .responseInfo(responseInfo)
                .taskId(task.getId())
                .build();
    }

    public void transfer(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());

        Map<String, BillDetail> billDetailsToBeupdatedById = getBillDetailsToBeUpdated(billFromRequest, billFromSearch);


        for (BillDetail billDetail : billDetailsToBeupdatedById.values()) {
            if (billDetail.getStatus() == Status.VERIFIED || billDetail.getStatus() == Status.PAYMENT_FAILED) {
                IndividualDetails individualDetails = individualUtil.getIndividualDetails(taskRequest.getRequestInfo(), billFromSearch.getTenantId(), billDetail.getPayee().getIdentifier());
                TaskDetails taskDetails = TaskDetails.builder()
                        .id(UUID.randomUUID().toString())
                        .taskId(task.getId())
                        .referenceId(individualDetails.getPhoneNumber())
                        .billId(billFromSearch.getId())
                        .billDetailsId(billDetail.getId())
                        .payeeId(billDetail.getPayee().getId())
                        .status(Status.IN_PROGRESS)
                        .tenantId(billFromSearch.getTenantId())
                        .auditDetails(billFromSearch.getAuditDetails())
                        .build();

                //ZERO Amt check
                if (billDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
                    taskDetails.setResponseMessage("Payment couldn't be processed as total amount is 0.");
                    taskDetails.setReasonForFailure("TOTAL_AMOUNT_ZERO_" + EXCEPTION);
                    expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);
                    log.info("payment couldn't be processed for bill detail id {} as total amount is 0", billDetail.getId());
                } else {
                    PaymentTransferRequest paymentTransferRequest = createPaymentTransferRequest(billDetail, individualDetails.getPhoneNumber());
                    try {
                        mtnUtil.transferIfAccountIsActive(paymentTransferRequest, taskDetails.getId());
                    } catch (CustomException e) {
                        taskDetails.setResponseMessage(e.getMessage());
                        taskDetails.setReasonForFailure(e.getCode());
                    }
                    expenseProducer.push(billFromSearch.getTenantId(), config.getBillTaskDetailsTopic(), taskDetails);
                }
            }
        }
        task.setAdditionalDetails(taskRequest.getRequestInfo());
        AuditDetails auditDetails = task.getAuditDetails();
        auditDetails.setLastModifiedTime(System.currentTimeMillis());
        expenseProducer.push(billFromSearch.getTenantId(), config.getTaskUpdateTopic(), task);

        // Push delayed status check event — consumed after retryDelayMs
        TaskStatusCheckRequest checkRequest = TaskStatusCheckRequest.builder()
                .taskId(task.getId())
                .tenantId(billFromSearch.getTenantId())
                .requestInfo(taskRequest.getRequestInfo())
                .retryCount(0)
                .maxRetries(config.getTaskStatusCheckMaxRetries())
                .build();
        expenseProducer.pushWithDelay(
                billFromSearch.getTenantId(),
                config.getTaskStatusCheckTopic(),
                checkRequest,
                System.currentTimeMillis() + config.getTaskStatusCheckRetryDelayMs()
        );
    }

    private PaymentTransferRequest createPaymentTransferRequest(BillDetail billDetail, String partyId) {

        BigDecimal totalAmount = billDetail.getTotalAmount();

        //additionalAmount
        BigDecimal additionalAmount = totalAmount
                .multiply(config.getAdditionalAmountPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        //finalAmount
        BigDecimal finalAmount = totalAmount.add(additionalAmount);

        return PaymentTransferRequest.builder()
                .amount(finalAmount.setScale(0, RoundingMode.HALF_UP).toPlainString())
                .currency(config.getPaymentCurrency())
                .externalId(billDetail.getId())
                .payee(PaymentTransferRequest.Payee.builder()
                        .partyId(config.getPhoneCodePrefix() + partyId)
                        .partyIdType(config.getPartyIdType())
                        .build())
                .build();


    }

    public TaskDetailsResponse getTaskDetails(TaskDetailsRequest taskDetailsRequest) {
        String tenantId = taskDetailsRequest.getRequestInfo().getUserInfo().getTenantId();
        List<TaskDetails> taskDetails = new ArrayList<>();
        if (taskDetailsRequest.getBillDetailsId() != null) {
            taskDetails.add(taskRepository.searchTaskDetails(taskDetailsRequest));
        } else {
            taskDetails = taskRepository.searchTaskDetailsByTaskId(taskDetailsRequest.getTaskId(), tenantId);
        }
        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(taskDetailsRequest.getRequestInfo(), true);

        return TaskDetailsResponse.builder()
                .responseInfo(responseInfo)
                .taskDetails(taskDetails)
                .build();
    }

    private void updateBillWfStatus(BillRequest billRequest, boolean isWorkflowChange) {
        Bill bill = billRequest.getBill();
        try {
            if (isWorkflowChange &&
                    validator.isWorkflowActiveForBusinessService(bill.getBusinessService())) {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBill(billRequest), billRequest);
                bill.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            }
        } catch (HttpClientErrorException e) {
            log.error("Error in updating workflow state change for bill number : {} from status: {}", bill.getBillNumber(), bill.getStatus(), e);
        }
        expenseProducer.push(bill.getTenantId(), config.getBillUpdateTopic(), billRequest);
    }

    private void setBillDetailStatus(BillDetail billDetail, Workflow workflow, RequestInfo requestInfo, boolean isWorkflowUpdate) {
        if (validator.isWorkflowActiveForBusinessService(config.getBillDetailBusinessService()) && isWorkflowUpdate) {
            BillDetailRequest billDetailRequest = BillDetailRequest.builder()
                    .billDetail(billDetail)
                    .businessService(config.getBillDetailBusinessService())
                    .workflow(workflow)
                    .requestInfo(requestInfo)
                    .build();
            try {
                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
            } catch (HttpClientErrorException e) {
                log.error("Error in updating workflow state change for billDetail Id: {}, from status: {} to action: {}"
                        , billDetail.getId(), billDetail.getStatus(), workflow.getAction(), e);
            }
        }
    }

    public BillDetailResponse updateBillDetailStatus(BillRequest billRequest) {


        Bill billFromRequest = billRequest.getBill();

        Bill billFromSearch = getBillfromSearch(billFromRequest, billRequest.getRequestInfo());

        boolean isBillWorkflowChange = false;
        if (billRequest.getWorkflow().getAction().equals(Actions.CREATE.toString())) {
            billFromSearch.setBusinessService(config.getBillBusinessService());
            billFromSearch.setAdditionalDetails(billFromRequest.getAdditionalDetails());
            isBillWorkflowChange = true;
        }
        BillDetailRequest billDetailRequest
                = BillDetailRequest
                .builder()
                .requestInfo(billRequest.getRequestInfo())
                .businessService(config.getBillDetailBusinessService())
                .workflow(billRequest.getWorkflow())
                .build();

        List<BillDetail> updatedBillDetails = new ArrayList<>();
        getBillDetailsToBeUpdated(billFromRequest, billFromSearch)
                .values()
                .forEach(billDetail -> {
                            try {
                                billDetailRequest.setBillDetail(billDetail);
                                State wfState = workflowUtil.callWorkFlow(workflowUtil.prepareWorkflowRequestForBillDetail(billDetailRequest), billDetailRequest);
                                billDetail.setStatus(Status.fromValue(wfState.getApplicationStatus()));
                                updatedBillDetails.add(billDetail);
                            } catch (HttpClientErrorException e) {
                                log.error("error updating workflow status for bill number: {}, billdetail id: {}, status: {}, action: {}",
                                        billFromSearch.getBillNumber(), billDetail.getId(), billDetail.getStatus(), billRequest.getWorkflow().getAction(), e);
                            }
                        }
                );
        BillRequest billUpdateRequest
                = BillRequest
                .builder()
                .requestInfo(billRequest.getRequestInfo())
                .bill(billFromSearch)
                .build();

        if (isBillWorkflowChange) {
            billUpdateRequest.setWorkflow(billRequest.getWorkflow());
        }
        updateBillWfStatus(billUpdateRequest, isBillWorkflowChange);

        ResponseInfo responseInfo = responseInfoFactory.
                createResponseInfoFromRequestInfo(billDetailRequest.getRequestInfo(), true);
        return BillDetailResponse
                .builder()
                .responseInfo(responseInfo)
                .billDetails(updatedBillDetails)
                .build();

    }

    private Map<String, BillDetail> getBillDetailsToBeUpdated(Bill billFromRequest, Bill billFromSearch) {
        Map<String, BillDetail> billDetailsFromRequestById
                = billFromRequest.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        Map<String, BillDetail> billDetailsFromSearchById
                = billFromSearch.getBillDetails().stream().collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        Map<String, BillDetail> billDetailsToBeUpdatedById = new HashMap<>();
        for (String id : billDetailsFromRequestById.keySet()) {
            billDetailsToBeUpdatedById.put(id, billDetailsFromSearchById.get(id));
        }
        return billDetailsToBeUpdatedById;
    }

    public void updatePaymentTaskStatus(TaskRequest taskRequest) {

        Task task = taskRequest.getTask();
        Bill billFromRequest = taskRequest.getBill();
        Bill billFromSearch = getBillfromSearch(billFromRequest, taskRequest.getRequestInfo());
        log.info("updating payment status for task {}, bill number {}", task.getId(), billFromSearch.getBillNumber());

        Map<String, BillDetail> billDetailsById = billFromSearch.getBillDetails().stream()
                .collect(Collectors.toMap(BillDetail::getId, billDetail -> billDetail));

        List<TaskDetails> taskDetails = taskRepository.searchTaskDetailsByTaskId(task.getId(), billFromSearch.getTenantId());
        for (TaskDetails taskDetail : taskDetails) {
            boolean isUpdateWorkflow = true;
            BillDetail billDetail = billDetailsById.get(taskDetail.getBillDetailsId());
            if (taskDetail.getStatus() == Status.IN_PROGRESS && (billDetail.getStatus() == Status.VERIFIED || billDetail.getStatus() == Status.PAYMENT_FAILED)) {
                Workflow billDetailWorkflow = Workflow.builder().build();
                try {
                    if (billDetail.getTotalAmount().compareTo(BigDecimal.ZERO) == 0) {
                        billDetailWorkflow.setAction(Actions.DECLINE.toString());
                        taskDetail.setStatus(Status.DONE);
                        log.info("Payment couldn't be processed for bill detail id {} as total amount is 0", billDetail.getId());
                    } else if (
                            (taskDetail.getReasonForFailure() != null && !taskDetail.getReasonForFailure().isBlank()) &&
                                    (taskDetail.getReasonForFailure().toLowerCase().contains(EXCEPTION.toLowerCase()) || taskDetail.getReasonForFailure().toLowerCase().contains(ERROR.toLowerCase()))
                    ) {
                        billDetailWorkflow.setAction(Actions.DECLINE.toString());
                        taskDetail.setStatus(Status.DONE);
                        log.info("Payment couldn't be processed for bill detail id {} : {}", billDetail.getId(),
                                taskDetail.getReasonForFailure() + " " + taskDetail.getResponseMessage());
                    } else {
                        PaymentTransferResponse paymentTransferResponse = mtnUtil.getTransferStatus(taskDetail.getId());
                        if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESSFUL.toString())) {
                            billDetailWorkflow.setAction(Actions.PAY.toString());
                        } else if (paymentTransferResponse.getStatus().equalsIgnoreCase(ResponseStatus.FAILED.toString())) {
                            billDetailWorkflow.setAction(Actions.DECLINE.toString());
                        } else {
                            log.info("unknown response status: {} for bill number : {}, task id: {}, task detail id: {}", paymentTransferResponse.getStatus(), billFromSearch.getBillNumber(), task.getId(), taskDetail.getId());
                        }
                        taskDetail.setReasonForFailure(paymentTransferResponse.getReason());
                        taskDetail.setAdditionalDetails((Object) paymentTransferResponse);
                        taskDetail.setStatus(Status.DONE);
                    }
                    if (billDetail.getStatus() == Status.PAYMENT_FAILED && Objects.equals(billDetailWorkflow.getAction(), Actions.DECLINE.toString()))
                        isUpdateWorkflow = false;
                } catch (CustomException e) {
                    log.error("error in fetching payment transfer status from mtn for bill number : {}, billDetail: {},task: {}, taskDetail: {}",
                            billFromSearch.getBillNumber(), billDetail.getId(), task.getId(), taskDetail.getId(), e);
                    taskDetail.setReasonForFailure(e.getCode());
                    taskDetail.setResponseMessage(e.getMessage());
                    isUpdateWorkflow = false;
                }
                expenseProducer.push(billFromSearch.getTenantId(), config.getTaskDetailsUpdateTopic(), taskDetail);

                Object additionalDetailsObj = billDetail.getAdditionalDetails();
                Map<String, Object> additionalDetails;

                try {
                    additionalDetails = new ObjectMapper().convertValue(
                            additionalDetailsObj,
                            new TypeReference<>() {
                            }
                    );
                } catch (IllegalArgumentException e) {
                    additionalDetails = new HashMap<>();
                }

                ErrorDetails errorDetails = ErrorDetails.builder()
                        .reasonForFailure(taskDetail.getReasonForFailure())
                        .responseMessage(taskDetail.getResponseMessage())
                        .response(taskDetail.getAdditionalDetails()).build();
                additionalDetails.put("errorDetails", errorDetails);
                billDetail.setAdditionalDetails(additionalDetails);
                try {
                    setBillDetailStatus(billDetail, billDetailWorkflow, taskRequest.getRequestInfo(), isUpdateWorkflow);
                } catch (HttpClientErrorException e) {
                    log.error("Error in updating workflow state change for billDetail Id: {}, bill number : {}, from status: {} to action: {}"
                            , billDetail.getId(), billFromSearch.getBillNumber(), billDetail.getStatus(), billDetailWorkflow.getAction(), e);
                }
            }
        }
        if (billFromSearch.getStatus() == Status.PARTIALLY_VERIFIED || billFromSearch.getStatus() == Status.FULLY_VERIFIED || billFromSearch.getStatus() == Status.PARTIALLY_PAID) {

            List<BillDetail> paidBillDetails = new ArrayList<>();
            List<BillDetail> declinedBillDetails = new ArrayList<>();
            billFromSearch
                    .getBillDetails().forEach(billDetail -> {
                        if (billDetail.getStatus() == Status.PAID) {
                            paidBillDetails.add(billDetail);
                        } else if (billDetail.getStatus() == Status.PAYMENT_FAILED) {
                            declinedBillDetails.add(billDetail);
                        }
                    });

            Workflow workflow = Workflow.builder()
                    .build();
            boolean isWorkflowChange = true;
            if (paidBillDetails.size() == billFromSearch.getBillDetails().size()) {
                workflow.setAction(Actions.FULLY_PAY.toString());
            } else if (!paidBillDetails.isEmpty() && billFromSearch.getStatus() != Status.PARTIALLY_PAID) {
                workflow.setAction(Actions.PARTIALLY_PAY.toString());
            } else if (declinedBillDetails.size() == billFromSearch.getBillDetails().size()) {
                workflow.setAction(Actions.DECLINE_PAYMENT.toString());
            } else {
                log.info("no workflow state change for bill number : {}, task id: {}", billFromSearch.getBillNumber(), task.getId());
                isWorkflowChange = false;
            }
            BillRequest billRequest = BillRequest
                    .builder()
                    .bill(billFromSearch)
                    .workflow(workflow)
                    .requestInfo(taskRequest.getRequestInfo())
                    .build();
            updateBillWfStatus(billRequest, isWorkflowChange);
        }

        log.info("finished updating payment status for task {}, bill number {}", task.getId(), billFromSearch.getBillNumber());
    }

    /**
     * Called by TaskStatusCheckConsumer. Fetches the task, runs the MTN status check,
     * updates task details + bill workflow, and returns whether any task details
     * are still IN_PROGRESS (i.e. MTN response was still pending).
     *
     * @return true if any task detail is still IN_PROGRESS (retry needed), false if all done
     */
    public boolean checkAndFinalizeTaskStatus(TaskStatusCheckRequest checkRequest) {
        String tenantId = checkRequest.getTenantId();

        Task taskSearch = Task.builder()
                .id(checkRequest.getTaskId())
                .tenantId(tenantId)
                .build();
        Task task = taskRepository.searchTask(taskSearch);

        if (task == null) {
            log.warn("Task not found for id: {}, tenantId: {}", checkRequest.getTaskId(), tenantId);
            return false;
        }

        TaskRequest taskRequest = TaskRequest.builder()
                .task(task)
                .bill(Bill.builder().id(task.getBillId()).tenantId(tenantId).build())
                .requestInfo(checkRequest.getRequestInfo())
                .build();

        updatePaymentTaskStatus(taskRequest);

        // Re-fetch task details to check remaining IN_PROGRESS items
        List<TaskDetails> taskDetails = taskRepository.searchTaskDetailsByTaskId(task.getId(), tenantId);
        boolean anyInProgress = taskDetails.stream()
                .anyMatch(td -> td.getStatus() == Status.IN_PROGRESS);

        // Finalize task status and push update
        AuditDetails auditDetails = task.getAuditDetails();
        if (auditDetails != null) {
            auditDetails.setLastModifiedTime(System.currentTimeMillis());
        }
        if (!anyInProgress) {
            task.setStatus(Status.DONE);
        }
        expenseProducer.push(tenantId, config.getTaskUpdateTopic(), task);

        return anyInProgress;
    }

}
