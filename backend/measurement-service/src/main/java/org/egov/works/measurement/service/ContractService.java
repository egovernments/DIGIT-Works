package org.egov.works.measurement.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.web.models.ContractCriteria;
import org.egov.works.measurement.web.models.ContractResponse;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ContractService {

    private final RestTemplate restTemplate;

    @Autowired
    public ContractService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ContractResponse getContracts(Measurement measurement , RequestInfo requestInfo){
        ContractCriteria req  = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId()).contractNumber(measurement.getReferenceId()).build();
        String searchContractUrl = "https://unified-dev.digit.org/contract/v1/_search";
        return restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
    }
}
