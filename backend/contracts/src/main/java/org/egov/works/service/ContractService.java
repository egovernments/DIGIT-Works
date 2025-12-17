package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.ContractServiceConstants.*;

@Service
@Slf4j
public class ContractService {

    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private ContractServiceValidator contractServiceValidator;

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

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisService redisService;
    private static final String CONTRACT_REDIS_KEY = "CONTRACT_{id}";

    public ContractResponse createContract(ContractRequest contractRequest) {
        log.info("Create contract");
        contractServiceValidator.validateCreateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnCreate(contractRequest);
        workflowService.updateWorkflowStatus(contractRequest);
        if(Boolean.TRUE.equals(contractServiceConfiguration.getIsCachingEnabled())){
            setCacheContract(contractRequest.getContract());
        }
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
        if(Boolean.TRUE.equals(contractServiceConfiguration.getIsCachingEnabled())){
            setCacheContract(contractRequest.getContract());
        }
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
        List<Contract> contracts = new ArrayList<>();
        if(Boolean.TRUE.equals(isCacheSearchRequired(contractCriteria))){
            log.info("get contract from cache");
            try {
                contracts = getContractsFromCache(new HashSet<>(contractCriteria.getIds()));
                if(contracts.size() == contractCriteria.getIds().size()){
                    log.info("Contracts searched");
                    return contracts;
                }else{
                    log.info("Contracts not found in cache");
                    contractCriteria.getIds().removeAll(contracts.stream().map(Contract::getId).collect(Collectors.toList()));
                    if (contractCriteria.getIds().isEmpty())
                        return contracts;
                }
            }catch (Exception e) {
                log.error("Exception while getting cache: {}", e);
            }
        }
        //get contracts from db
        log.info("get enriched contracts list");
        contracts.addAll(getContracts(contractCriteria));

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
      // Need to check the status of the estimate if it is Active or not
        if(request.getEstimate().getStatus().equals(Status.ACTIVE)){

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
        }else {
            log.info("ESTIMATE_NOT_ACTIVE","Revised Estimate is not in Active state");
        }
    }

    private ContractRequest createRevisedContractRequest(List<Contract> contractList, EstimateRequest request){
        Contract latestContract = null;
        long latestCreatedTime = Long.MIN_VALUE;


        for (Contract contract : contractList) {
            if("REJECTED".equals(contract.getWfStatus())){
             continue;
            }
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

        Workflow workflow=Workflow.builder().action(SUBMIT).assignees(new ArrayList<>()).build();

        return ContractRequest.builder()
                .requestInfo(request.getRequestInfo())
                .contract(createContractFromPreviousContractAndEstimateRequest(latestContract,request))
                .workflow(workflow)
                .build();
    }

    private Contract createContractFromPreviousContractAndEstimateRequest(Contract oldContract,EstimateRequest request){
        log.info("ContractService::createContractFromPreviousContractAndEstimateRequest");

        List<LineItems> lineItemsList = new ArrayList<>();
        LineItems lineItems=LineItems.builder()
                .estimateId(request.getEstimate().getId())
                .status(Status.ACTIVE)
                .tenantId(request.getEstimate().getTenantId())
                .build();
        lineItemsList.add(lineItems);
        BigDecimal totalEstimatedAmount = null;

        Object additionalDetailsObject = request.getEstimate().getAdditionalDetails();

        if (additionalDetailsObject instanceof Map) {
            // Convert additionalDetailsObject to Map<String, Object>
            Map<String, Object> additionalDetailsMap = (Map<String, Object>) additionalDetailsObject;

            // Now you can use additionalDetailsMap as a Map<String, Object>
           log.info("Is additionalDetailsMap an instance of Map? " + (additionalDetailsMap instanceof Map));

            // Accessing totalEstimatedAmount
            totalEstimatedAmount = new BigDecimal(additionalDetailsMap.get("totalEstimatedAmount").toString());

        }

        if (totalEstimatedAmount!=null) {
            log.info("Total Estimated Amount: " + totalEstimatedAmount);
        }else{
            log.info("Total Estimate Amount not found");
        }



        return Contract.builder().tenantId(oldContract.getTenantId())
                .wfStatus("APPROVED")
                .executingAuthority(oldContract.getExecutingAuthority())
                .businessService(CONTRACT_REVISION_ESTIMATE)
                .contractNumber(oldContract.getContractNumber())
                .totalContractedAmount(totalEstimatedAmount)
                .contractType(oldContract.getContractType())
                .securityDeposit(oldContract.getSecurityDeposit())
                .agreementDate(oldContract.getAgreementDate())
                .defectLiabilityPeriod(oldContract.getDefectLiabilityPeriod())
                .orgId(oldContract.getOrgId())
                .startDate(oldContract.getStartDate())
                .endDate(oldContract.getEndDate())
                .status(Status.ACTIVE)
                .completionPeriod(oldContract.getCompletionPeriod())
                .documents(oldContract.getDocuments())
                .processInstance(oldContract.getProcessInstance())
                .additionalDetails(oldContract.getAdditionalDetails())
                .lineItems(lineItemsList)
                .build();
    }

    private List<Contract> getContractsFromCache(Set<String> ids) {
        List<Contract> contracts = new ArrayList<>();
        for (String id : ids) {
            String key = getContractRedisKey(id);
            Contract contract = redisService.getCache(key, Contract.class);
            if (contract != null) {
                contracts.add(contract);
            }
        }
        return contracts;
    }

    private void setCacheContract(Contract contract){
        try {
            String key = getContractRedisKey(contract.getId());
            redisService.setCache(key, contract);
        }catch (Exception e){
            log.error("Exception while setting cache: " + e);
        }
    }
    private String getContractRedisKey(String id) {
        return CONTRACT_REDIS_KEY.replace("{id}", id);
    }

    private boolean isCacheSearchRequired(ContractCriteria searchCriteria) {
        return contractServiceConfiguration.getIsCachingEnabled() && !CollectionUtils.isEmpty(searchCriteria.getIds()) &&
                StringUtils.isEmpty(searchCriteria.getContractNumber()) &&
                StringUtils.isEmpty(searchCriteria.getBusinessService()) &&
                StringUtils.isEmpty(searchCriteria.getContractType()) &&
                StringUtils.isEmpty(searchCriteria.getSupplementNumber()) &&
                CollectionUtils.isEmpty(searchCriteria.getEstimateIds()) &&
                CollectionUtils.isEmpty(searchCriteria.getEstimateLineItemIds()) &&
                StringUtils.isEmpty(searchCriteria.getContractType()) &&
                StringUtils.isEmpty(searchCriteria.getStatus()) &&
                StringUtils.isEmpty(searchCriteria.getWfStatus()) &&
                CollectionUtils.isEmpty(searchCriteria.getOrgIds());
    }
}
