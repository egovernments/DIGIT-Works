package org.egov.works.measurement.util;

import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.web.models.ContractCriteria;
import org.egov.works.measurement.web.models.ContractResponse;
import org.egov.works.measurement.web.models.Measure;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Component
public class ContractUtil {
    private final RestTemplate restTemplate;

    @Autowired
    public ContractUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetch contracts for a particular measurement
     * based on ContractNumber
     * @param measurement
     * @param requestInfo
     * @return
     */
    public ContractResponse getContracts(Measurement measurement , RequestInfo requestInfo){
        ContractCriteria req  = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId()).contractNumber(measurement.getReferenceId()).build();
        String searchContractUrl = "https://unified-dev.digit.org/contract/v1/_search";
        return restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
    }

    /**
     * Validate the Contract
     * @param measurement
     * @param requestInfo
     * @return
     */
    public Boolean validContract(Measurement measurement, RequestInfo requestInfo){
        Set<String> lineItemsIdList = new HashSet<>();
        ContractResponse response = getContracts(measurement,requestInfo);
        boolean isValidContract = !response.getContracts().isEmpty();         // check if there is a reference id

        // return if no contract is present
        if(!isValidContract) return false;

        boolean isValidEntryDate =  ((measurement.getEntryDate().compareTo(response.getContracts().get(0).getStartDate())>=0) && (measurement.getEntryDate().compareTo(response.getContracts().get(0).getEndDate()) <=0));
        boolean isTargetIdsPresent = true;

        lineItemsIdList = getValidLineItemsId(response); // get set of active line items

        // check for each measure targetId in LineItemsList
        // set to false even if it fails for one measure in measurements

        for (Measure measure : measurement.getMeasures()) {
            boolean isTargetIdPresent = lineItemsIdList.contains(measure.getTargetId());
            if(!isTargetIdPresent){
                isTargetIdsPresent = false;
            }

        }
        return isValidContract && isValidEntryDate && isTargetIdsPresent;
    }

    /**
     * Generate a set of ACTIVE line Items from a particular Contract Response
     * @param response
     * @return
     */
    public Set<String> getValidLineItemsId(ContractResponse response){

        Set<String> lineItemsIdList = new HashSet<>();
        response.getContracts().get(0).getLineItems().forEach(
                lineItems -> {
                    System.out.println(lineItems.getStatus().toString());
                    String estimateId = lineItems.getEstimateId();
                    String estimateLineItemId = lineItems.getEstimateLineItemId();
                    if(lineItems.getStatus().toString().equals("ACTIVE")){
                        System.out.println(lineItems.getId());
                        lineItemsIdList.add(lineItems.getId());
                    }
                }
        );
        return  lineItemsIdList;
    }

}
