package org.egov.works.mukta.adapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.mukta.adapter.config.MuktaAdaptorConfig;
import org.egov.works.mukta.adapter.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.contract.ContractCriteria;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContractUtils {
    private final ServiceRequestRepository restRepo;

    private final MuktaAdaptorConfig configs;

    private final ObjectMapper mapper;

    @Autowired
    public ContractUtils(ServiceRequestRepository restRepo, MuktaAdaptorConfig configs, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public ContractResponse fetchContract (RequestInfo requestInfo, String tenantId, String contractNumber) {
        StringBuilder url = getContractSearchURI();
        ContractCriteria contractCriteria = ContractCriteria.builder()
                                                            .requestInfo(requestInfo)
                                                            .tenantId(tenantId)
                                                            .contractNumber(contractNumber.trim())
                                                            .build();

        Object responseObj = restRepo.fetchResult(url, contractCriteria);
        return mapper.convertValue(responseObj, ContractResponse.class);
    }

    private StringBuilder getContractSearchURI() {
        StringBuilder builder = new StringBuilder(configs.getContractHost());
        builder.append(configs.getContractSearchEndPoint());
        return builder;
    }
}
