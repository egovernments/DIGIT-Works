package org.egov.works.measurement.util;

import digit.models.coremodels.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.config.ErrorConfiguration;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.Error;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ContractUtil {
    private final RestTemplate restTemplate;
    private final Configuration configuration;

    private final ErrorConfiguration errorConfigs;

    @Autowired
    public ContractUtil(RestTemplate restTemplate, Configuration configuration,ErrorConfiguration errorConfigs) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.errorConfigs=errorConfigs;
    }

    /**
     * Fetch contracts for a particular measurement
     * based on ContractNumber
     *
     * @param measurement
     * @param requestInfo
     * @return
     */

    public ContractResponse getContracts(Measurement measurement, RequestInfo requestInfo) {
        ContractCriteria req = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId()).contractNumber(measurement.getReferenceId()).build();
        String searchContractUrl = configuration.getContractHost() + configuration.getContractPath();
        ContractResponse response = restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
//        System.out.println(response.toString());
        return response;
    }

    /**
     * Validate the Contract
     *
     * @param measurement
     * @param requestInfo
     * @return
     */
    public Boolean validContract(Measurement measurement, RequestInfo requestInfo) {
        Map<String, ArrayList<String>> lineItemsToEstimateIdMap = new HashMap<>();
        List<String> lineItemIdsList = new ArrayList<>();
        List<String> estimateIdsList = new ArrayList<>();
        List<String> estimateLineItemIdsList = new ArrayList<>();
        Map<String,ArrayList<BigDecimal>> lineItemsToDimentionsMap = new HashMap<>();
        Set<String> targetIdSet = new HashSet<>();
        ContractResponse response = getContracts(measurement, requestInfo);

        // check if there is a reference id
        boolean isValidContract = !response.getContracts().isEmpty();

        // return if no contract is present
        if (!isValidContract) return false;

        boolean isValidEntryDate = ((measurement.getEntryDate().compareTo(response.getContracts().get(0).getStartDate()) >= 0) && (measurement.getEntryDate().compareTo(response.getContracts().get(0).getEndDate()) <= 0));
        boolean isTargetIdsPresent = true;

        lineItemsToEstimateIdMap = getValidLineItemsId(response); // get set of active line items

        for (Measure measure : measurement.getMeasures()) {

            if(targetIdSet.contains(measure.getTargetId())){
                throw errorConfigs.duplicateTargetIds;
            }
            else targetIdSet.add(measure.getTargetId());  // create a set of received target Ids

            boolean isTargetIdPresent = lineItemsToEstimateIdMap.containsKey(measure.getTargetId());  // checks id of line item

            if (!isTargetIdPresent) {
                isTargetIdsPresent = false;
                throw errorConfigs.noActiveContractId;
            } else {
                lineItemIdsList.add(measure.getTargetId());
                estimateIdsList.add(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(0));
                estimateLineItemIdsList.add(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(1));
                ArrayList<BigDecimal> dimensionList = new ArrayList<>();
                dimensionList.add(measure.getLength()); dimensionList.add(measure.getBreadth()); dimensionList.add(measure.getHeight());  // L B H
                lineItemsToDimentionsMap.put(measure.getTargetId() , dimensionList);
            }

        }

        // check exact match of targetIs in Contract Active line Items
        // isAllTargetIdsPresent(targetIdSet,lineItemsToEstimateIdMap);

        // Estimate Validation
        EstimateResponse estimateResponse = getEstimate(requestInfo, measurement.getTenantId(), estimateIdsList);  // assume a single estimate id for now
        boolean validDimensions = true;
        for(int i=0;i<estimateIdsList.size();i++){
            boolean isValidEstimate = false;
            for (EstimateDetail estimateDetail : estimateResponse.getEstimates().get(0).getEstimateDetails()) {
                if (Objects.equals(estimateDetail.getId(), estimateLineItemIdsList.get(i))) {
                    boolean isValidDimension = validateDimensions(estimateDetail,lineItemsToDimentionsMap.get(lineItemIdsList.get(i)));
                    if(isValidDimension){
                        isValidEstimate = true;
                        break;
                    }
                }
            }
            if(!isValidEstimate){
                validDimensions = false;
                throw errorConfigs.noValidEstimate;
            }
        }
        System.out.println(estimateResponse.getEstimates().get(0).getId());

        return isValidContract && isValidEntryDate && isTargetIdsPresent;
    }

    /**
     * Checks given req for all line items ids
     * in the corresponding contract
     * @param measuresTargetIdSet
     * @param lineItemsToEstimateIdMap
     */
    public void isAllTargetIdsPresent(Set<String> measuresTargetIdSet, Map<String, ArrayList<String>> lineItemsToEstimateIdMap ){

        for (Map.Entry<String, ArrayList<String>> entry : lineItemsToEstimateIdMap.entrySet()) {
            String key = entry.getKey();
            if(!measuresTargetIdSet.contains(key)){
                throw errorConfigs.incompleteMeasures;
            }
        }
    }

    /**
     * Generate a set of ACTIVE line Items from a particular Contract Response
     *
     * @param response
     * @return
     */
    public Map<String, ArrayList<String>> getValidLineItemsId(ContractResponse response) {

        Set<String> lineItemsIdList = new HashSet<>();
        Map<String, ArrayList<String>> lineItemsToEstimateId = new HashMap<>();    // [estimateId , estimateLineItemId]
        response.getContracts().get(0).getLineItems().forEach(
                lineItems -> {
                    if (lineItems.getStatus().toString().equals("ACTIVE")) {
                        lineItemsIdList.add(lineItems.getId());  // id  remove this
                        ArrayList<String> arr = new ArrayList<>();
                        arr.add(lineItems.getEstimateId());
                        arr.add(lineItems.getEstimateLineItemId());
                        lineItemsToEstimateId.put(lineItems.getId(), arr);
                    }
                }
        );
        return lineItemsToEstimateId;
//        return  lineItemsIdList;
    }

    public EstimateResponse getEstimate(RequestInfo requestInfo, String tenantId, List<String> estimateIdsList) {

        String estimateSearchUrl = configuration.getEstimateHost()+configuration.getEstimatePath();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimateSearchUrl);
        builder.queryParam("tenantId",tenantId);
        builder.queryParam("ids",estimateIdsList);
        String preparedUrl = builder.toUriString();

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        // How to search by using multiples ids :?
        EstimateResponse estimateResponse = restTemplate.postForObject(preparedUrl, requestInfoWrapper, EstimateResponse.class);
        return estimateResponse;
    }

    public boolean validateDimensions(EstimateDetail estimateDetail,ArrayList<BigDecimal> dimensions)  {
        try {
            // FIXME: How to access the measurement object ??
//            estimateDetail.getAdditionalDetails().getClass().getField("measurement");
        } catch (Error e) {
            System.out.println();
        }
        return true;
    }
}
