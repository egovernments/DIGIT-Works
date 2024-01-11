package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.enrichment.ContractEnrichment;
import org.egov.works.kafka.ContractProducer;
import org.egov.works.repository.ContractRepository;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.validator.ContractServiceValidator;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ContractServiceConstants.*;

@Service
@Slf4j
public class ContractService {

    @Autowired
    private ContractServiceValidator contractServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private ContractServiceConfiguration contractServiceConfiguration;

    @Autowired
    private ContractEnrichment contractEnrichment;

    @Autowired
    private ContractProducer contractProducer;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private LineItemsRepository lineItemsRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private NotificationService notificationService;

    public ContractResponse createContract(ContractRequest contractRequest) {
        log.info("Create contract");
        contractServiceValidator.validateCreateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnCreate(contractRequest);
        workflowService.updateWorkflowStatus(contractRequest);
        contractProducer.push(contractServiceConfiguration.getCreateContractTopic(), contractRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(contractRequest.getRequestInfo(), true);
        ContractResponse contractResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(contractRequest.getContract())).build();
        log.info("Contract created");
        return contractResponse;
    }

    public ContractResponse updateContract(ContractRequest contractRequest) {
        log.info("Update contract ["+contractRequest.getContract().getId()+"]");
        // Validate contract request
        contractServiceValidator.validateUpdateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnUpdate(contractRequest);
        workflowService.updateWorkflowStatus(contractRequest);
        contractEnrichment.enrichPreviousContractLineItems(contractRequest);
        contractProducer.push(contractServiceConfiguration.getUpdateContractTopic(), contractRequest);
        try {
            notificationService.sendNotification(contractRequest);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }


        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(contractRequest.getRequestInfo(), true);
        ContractResponse contractResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(contractRequest.getContract())).build();
        log.info("Contract updated ["+contractRequest.getContract().getId()+"]");
        return contractResponse;
    }


    public List<Contract> searchContracts(ContractCriteria contractCriteria) {
        log.info("Search contracts");

        //Validate the requested parameters
        log.info("Validate the search request parameters");
        contractServiceValidator.validateSearchContractRequest(contractCriteria);

        //Enrich requested search criteria
        log.info("Enrich requested search criteria");
        contractEnrichment.enrichSearchContractRequest(contractCriteria);

        //get contracts from db
        log.info("get enriched contracts list");
        List<Contract> contracts = getContracts(contractCriteria);

        log.info("Contracts searched");
        return contracts;
    }

    public List<Contract> getContracts(ContractCriteria contractCriteria) {

        //get lineItems from db
        log.info("get lineItems from db");
        List<LineItems> lineItems = lineItemsRepository.getLineItems(contractCriteria);

        //collect lineItems for each contract
        Map<String, List<LineItems>> lineItemsMap = lineItems.stream().collect(Collectors.groupingBy(LineItems::getContractId));

        //get contract ids from lineItems
        if (contractCriteria.getIds() == null || contractCriteria.getIds().isEmpty()) {
            contractCriteria.setIds(new ArrayList<>(lineItemsMap.keySet()));
        } else contractCriteria.getIds().addAll(lineItemsMap.keySet());

        //get contracts from db
        log.info("get contracts from db");
        List<Contract> contracts = contractRepository.getContracts(contractCriteria);

        log.info("enrich contract with lineItems");
        //filtering : contracts which have a lineItem from db
        List<Contract> filteredContracts = new ArrayList<>();
        for (Contract contract : contracts) {
            if (lineItemsMap.containsKey(contract.getId())) {
                contract.setLineItems(lineItemsMap.get(contract.getId()));
                filteredContracts.add(contract);
            }
        }

        //set total contracts and  sorting order count in pagination object
        contractCriteria.getPagination().setTotalCount(filteredContracts.size());

        return filteredContracts;
    }

    public void createAndPostRevisedContractRequest(EstimateRequest request){
        log.info("ContractService::createAndPostRevisedContractRequest");
        RequestInfo requestInfo= request.getRequestInfo();
        String tenantId=null;
        List<String> estimateIds= new ArrayList<>();

        if(!request.getWorkflow().getAction().equals("APPROVE")){
            throw new CustomException("REVISED_ESTIMATE_NOT_APPROVED", "Revised Estimate is not Approved");
        }

        if(request.getEstimate()!=null || request.getEstimate().getOldUuid()!=null){
            tenantId=request.getEstimate().getTenantId();
            estimateIds.add(request.getEstimate().getOldUuid());
        }else{
            log.info("Estimate olduuid not present in the revised estimate request");
            throw new CustomException("DATA_NOT_PRESENT", "Data required for the contract " +
                    "search is not present in the request");
        }
        ContractCriteria contractCriteria=ContractCriteria.builder().requestInfo(requestInfo)
                .tenantId(tenantId).estimateIds(estimateIds).build();
        List<Contract> contractList=searchContracts(contractCriteria);
        if (contractList.isEmpty()){
            //throw new CustomException("NO_CONTRACT_FOUND","No Contract found with the given search criteria");
            log.info("NO_CONTRACT_FOUND::: No Contract found with the given search criteria");

        }else{
            createContract(createRevisedContractRequest(contractList,request));
        }
    }

    private ContractRequest createRevisedContractRequest(List<Contract> contractList, EstimateRequest request){
        Contract latestContract = null;
        long latestCreatedTime = Long.MIN_VALUE;


        for (Contract contract : contractList) {
            long currentCreatedTime = contract.getAuditDetails().getCreatedTime();
            if (currentCreatedTime > latestCreatedTime) {
                latestCreatedTime = currentCreatedTime;
                latestContract = contract;
            }
        }


        //TE- Approved
        //WO-Accepted
        if(latestContract.getBusinessService().equals(CONTRACT_BUSINESS_SERVICE)&& !Objects.equals(latestContract.getWfStatus(), "ACCEPTED")) {
            throw new CustomException("WF_STATUS_NOT_ACCEPTED","Contract wfStatus is not Accepted");
        }
        if(latestContract.getBusinessService().equals(CONTRACT_TIME_EXTENSION_BUSINESS_SERVICE)&& !Objects.equals(latestContract.getWfStatus(), "APPROVED")){
            throw new CustomException("WF_STATUS_NOT_APPROVED","Time Extension  wfStatus is not Approved");
        }

        Workflow workflow=Workflow.builder().action("SUBMIT").assignees(new ArrayList<>()).build();

        return ContractRequest.builder()
                .requestInfo(request.getRequestInfo())
                .contract(createContractFromPreviousContractAndEstimateRequest(latestContract,request))
                .workflow(workflow)
                .build();
    }

    private Contract createContractFromPreviousContractAndEstimateRequest(Contract oldContract,EstimateRequest request){
        log.info("ContractService::createContractFromPreviousContractAndEstimateRequest");

        LineItems lineItems=LineItems.builder()
                .estimateId(request.getEstimate().getId())
                .status(request.getEstimate().getStatus())
                .tenantId(request.getEstimate().getTenantId())
                .build();


        return Contract.builder().tenantId(oldContract.getTenantId())
                .wfStatus("APPROVED")
                .executingAuthority(oldContract.getExecutingAuthority())
                .businessService(CONTRACT_REVISION_ESTIMATE)
                .contractNumber(oldContract.getContractNumber())
                .totalContractedAmount(oldContract.getTotalContractedAmount())
                .contractType(oldContract.getContractType())
                .securityDeposit(oldContract.getSecurityDeposit())
                .agreementDate(oldContract.getAgreementDate())
                .defectLiabilityPeriod(oldContract.getDefectLiabilityPeriod())
                .orgId(oldContract.getOrgId())
                .startDate(oldContract.getStartDate())
                .endDate(oldContract.getEndDate())
                .status(Status.valueOf("ACTIVE"))
                .completionPeriod(oldContract.getCompletionPeriod())
                .documents(oldContract.getDocuments())
                .processInstance(oldContract.getProcessInstance())
                .additionalDetails(oldContract.getAdditionalDetails())
                .lineItems(Collections.singletonList(lineItems))
                .build();
    }



}
