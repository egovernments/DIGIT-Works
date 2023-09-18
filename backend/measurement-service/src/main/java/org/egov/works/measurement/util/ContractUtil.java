package org.egov.works.measurement.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.web.models.ContractCriteria;
import org.egov.works.measurement.web.models.ContractResponse;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContractUtil {
    private final RestTemplate restTemplate;

    @Autowired
    public ContractUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ContractResponse getContracts(Measurement measurement , RequestInfo requestInfo){
        ContractCriteria req  = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId()).contractNumber(measurement.getReferenceId()).build();
        String searchContractUrl = "https://works-qa.digit.org/contract/v1/_search";
        return restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
    }
    public Boolean validContract(Measurement measurement, RequestInfo requestInfo){

        ContractResponse response = getContracts(measurement,requestInfo);
        Boolean isValidContract = !response.getContracts().isEmpty();         // check if there is a reference id
//        Boolean isValidEntryDate =  (measurement.getEntryDate() > response.getContracts().get(0).getStartDate()) && measurement.getEntryDate() < response.getContracts().get(0).getEndDate();
        return isValidContract;
    }
}
