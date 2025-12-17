package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.contract.ContractCriteria;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ContractUtil {

    private final ServiceRequestRepository restRepo;
    private final StatementConfiguration configs;
    private final ObjectMapper mapper;

    public ContractUtil(ServiceRequestRepository restRepo, StatementConfiguration configs, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public List<Contract> fetchContracts(String contractNumber, String tenantId, RequestInfo requestInfo) {
        ContractCriteria contractCriteria = ContractCriteria.builder()
                .requestInfo(requestInfo)
                .tenantId(tenantId)
                .status("ACTIVE")
                .contractNumber(contractNumber)
                .build();
        StringBuilder url = getContractSearchUrl();
        Object response = restRepo.fetchResult(url, contractCriteria);
        ContractResponse contractResponse = mapper.convertValue(response, ContractResponse.class);

        return contractResponse.getContracts();
    }

    private StringBuilder getContractSearchUrl() {
        return new StringBuilder(configs.getContractHost())
                .append(configs.getContractSearchEndpoint());
    }
}
