package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContractService {

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
        if(Boolean.TRUE.equals(isCacheSearchRequired(contractCriteria))) {
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
