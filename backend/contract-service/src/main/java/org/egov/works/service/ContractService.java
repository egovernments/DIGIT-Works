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

@Service
@Slf4j
public class ContractService {
    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private ContractServiceValidator contractServiceValidator;

    @Autowired
    private ContractEnrichment contractEnrichment;

    @Autowired
    private Producer producer;

    @Autowired
    private ContractServiceConfiguration config;

    public ContractResponse createContract(ContractRequest contractRequest){
        // Validate contract request
        contractServiceValidator.validateCreateContractRequest(contractRequest);
        contractEnrichment.enrichContractOnCreate(contractRequest);
        producer.push(config.getCreateContractTopic(),contractRequest);

        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(contractRequest.getRequestInfo(), true);
        ContractResponse attendanceLogResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(contractRequest.getContract())).build();
        return attendanceLogResponse;
    }
}
