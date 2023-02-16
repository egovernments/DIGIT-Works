package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.enrichment.ContractEnrichment;
import org.egov.works.kafka.Producer;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.validator.ContractServiceValidator;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.ContractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.enrichment.ContractEnrichment;
import org.egov.works.kafka.Producer;
import org.egov.works.repository.ContractRepository;
import org.egov.works.repository.LineItemsRepository;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.validator.ContractServiceValidator;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.LineItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private Producer producer;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private LineItemsRepository lineItemsRepository;

    public ContractResponse createContract(ContractRequest contractRequest){
        // Validate contract request
        contractServiceValidator.validateCreateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnCreate(contractRequest);
        producer.push(contractServiceConfiguration.getCreateContractTopic(),contractRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(contractRequest.getRequestInfo(), true);
        ContractResponse attendanceLogResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(contractRequest.getContract())).build();
        return attendanceLogResponse;
    }

    public ContractResponse updateContract(ContractRequest contractRequest){
        // Validate contract request
        contractServiceValidator.validateUpdateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnUpdate(contractRequest);
        producer.push(contractServiceConfiguration.getUpdateContractTopic(),contractRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(contractRequest.getRequestInfo(), true);
        ContractResponse attendanceLogResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(contractRequest.getContract())).build();
        return attendanceLogResponse;
    }


    public List<Contract> searchContracts(RequestInfo requestInfo, ContractCriteria contractCriteria) {

        //Validate the requested parameters
        log.info("Validate the search request parameters");
        contractServiceValidator.validateSearchContractRequest(requestInfo, contractCriteria);

        //Enrich requested search criteria
        log.info("Enrich requested search criteria");
        contractEnrichment.enrichSearchContractRequest(requestInfo, contractCriteria);

        //get contracts from db
        log.info("get enriched contracts list");
        List<Contract> contracts = getContracts(contractCriteria);

        return contracts;
    }

    public List<Contract> getContracts(ContractCriteria contractCriteria) {

        //get lineItems from db
        log.info("get lineItems from db");
        List<LineItems> lineItems = lineItemsRepository.getLineItems(contractCriteria);

        //collect lineItems for each contract
        Map<String, List<LineItems>> lineItemsMap= lineItems.stream().collect(Collectors.groupingBy(LineItems::getContractId));

        contractCriteria.setIds(new ArrayList<>(lineItemsMap.keySet()));

        //get contracts from db
        log.info("get contracts from db");
        List<Contract> contracts = contractRepository.getContracts(contractCriteria);

        //set total contracts and  sorting order count in pagination object
        contractCriteria.getPagination().setTotalCount(contracts.size());

        log.info("enrich contract with lineItems");
        for (Contract contract : contracts) {
                contract.setLineItems(lineItemsMap.get(contract.getId()));
        }
        return contracts;
    }

}
