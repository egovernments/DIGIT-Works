package org.egov.works.measurement.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.repository.ServiceRequestRepository;
import org.egov.works.measurement.web.models.*;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.contract.ContractCriteria;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.egov.works.services.common.models.contract.LineItems;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.estimate.EstimateDetail;
import org.egov.works.services.common.models.estimate.EstimateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.measurement.config.ErrorConfiguration.*;
import static org.egov.works.measurement.config.ServiceConstants.*;

@Component
@Slf4j
public class ContractUtil {
    private final RestTemplate restTemplate;
    private final MBServiceConfiguration mbServiceConfiguration;

    private final MeasurementRegistryUtil measurementRegistryUtil;

    private final ServiceRequestRepository serviceRequestRepository;
    private final JdbcTemplate jdbcTemplate;
    private final MeasurementServiceUtil measurementServiceUtil;

    @Autowired
    public ContractUtil(RestTemplate restTemplate, MBServiceConfiguration mbServiceConfiguration,
                        MeasurementRegistryUtil measurementRegistryUtil, ServiceRequestRepository serviceRequestRepository,
                        JdbcTemplate jdbcTemplate, MeasurementServiceUtil measurementServiceUtil) {
        this.restTemplate = restTemplate;
        this.mbServiceConfiguration = mbServiceConfiguration;
        this.measurementRegistryUtil=measurementRegistryUtil;
        this.serviceRequestRepository = serviceRequestRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.measurementServiceUtil = measurementServiceUtil;
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
        ContractCriteria req = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId())
                .contractNumber(measurement.getReferenceId()).status(ACTIVE_STATUS).build();
        String searchContractUrl = mbServiceConfiguration.getContractHost() + mbServiceConfiguration.getContractPath();
        ContractResponse response = restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
        return response;
    }

    /**
     * Validate the Contract
     *
     * @param measurement
     * @param requestInfo
     * @return
     */
    public Boolean validContract(Measurement measurement, RequestInfo requestInfo, Boolean isUpdate, Workflow workflow) {
        Map<String, ArrayList<String>> lineItemsToEstimateIdMap;
        List<String> lineItemIdsList = new ArrayList<>();
        List<String> estimateIdsList = new ArrayList<>();
        Set<String> estimateIdsSet = new HashSet<>();
        List<String> estimateLineItemIdsList = new ArrayList<>();
        Map<String,ArrayList<BigDecimal>> lineItemsToDimentionsMap = new HashMap<>();
        Set<String> targetIdSet = new HashSet<>();
        ContractResponse contractResponse = getContracts(measurement, requestInfo);

        // check if there is a reference id
        boolean isValidContract = !contractResponse.getContracts().isEmpty();

        // return if no contract is present
        if (!isValidContract) return false;

        if (contractResponse.getContracts().get(0).getBusinessService().equals(CONTRACT_SERVICE)
                && !contractResponse.getContracts().get(0).getWfStatus().equalsIgnoreCase(ACCEPTED_STATUS))
            throw new CustomException(CONTRACT_NOT_ACCEPTED_CODE, CONTRACT_NOT_ACCEPTED_MSG);

        if( contractResponse.getContracts().get(0).getBusinessService().equals(CONTRACT_REVISION_ESTIMATE)
                && !contractResponse.getContracts().get(0).getWfStatus().equalsIgnoreCase(APPROVED_STATUS))
            throw new CustomException(CONTRACT_NOT_APPROVED_CODE, CONTRACT_NOT_APPROVED_MSG);

        if (contractResponse.getContracts().get(0).getBusinessService().equals(BUSINESS_SERVICE_TE_CONTRACT)
                && !contractResponse.getContracts().get(0).getWfStatus().equalsIgnoreCase(APPROVED_STATUS))
            throw new CustomException(CONTRACT_NOT_APPROVED_CODE, CONTRACT_NOT_APPROVED_MSG);



        boolean isValidEntryDate = ((measurement.getEntryDate().compareTo(contractResponse.getContracts().get(0).getStartDate()) >= 0));
//                && (measurement.getEntryDate().compareTo(contractResponse.getContracts().get(0).getEndDate()) <= 0));

        lineItemsToEstimateIdMap = getValidLineItemsId(contractResponse); // get set of active line items

        for (Measure measure : measurement.getMeasures()) {

            if(targetIdSet.contains(measure.getTargetId())){
                throw new CustomException(DUPLICATE_TARGET_IDS_CODE, DUPLICATE_TARGET_IDS_MSG);
            }
            else targetIdSet.add(measure.getTargetId());  // create a set of received target Ids

            boolean isTargetIdPresent = lineItemsToEstimateIdMap.containsKey(measure.getTargetId());  // checks id of line item

            if (!isTargetIdPresent) {
                throw new CustomException(INVALID_TARGET_ID_FOR_CONTRACT_CODE, measure.getTargetId() + INVALID_TARGET_ID_FOR_CONTRACT_MSG + measure.getReferenceId());
            } else {
                lineItemIdsList.add(measure.getTargetId());
                if(!estimateIdsSet.contains(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(0))) estimateIdsList.add(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(0)); // take only unique ids
                estimateIdsSet.add(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(0));     // add estimateId to estimateId set
                estimateLineItemIdsList.add(lineItemsToEstimateIdMap.get(measure.getTargetId()).get(1));
                ArrayList<BigDecimal> dimensionList = new ArrayList<>();
                dimensionList.add(measure.getLength()); dimensionList.add(measure.getBreadth()); dimensionList.add(measure.getHeight());  // L B H
                lineItemsToDimentionsMap.put(measure.getTargetId() , dimensionList);
            }
        }

        // check exact match of targetIs in Contract Active line Items
        List<String> validReqLineItems = filterValidReqLineItems(contractResponse , requestInfo , measurement.getTenantId(),lineItemsToEstimateIdMap);
        isAllRequiredLineItemsPresent(validReqLineItems,targetIdSet);

        // Estimate Validation
        estimateIdsList.add(contractResponse.getContracts().get(0).getLineItems().get(0).getEstimateId());
        EstimateResponse estimateResponse = getEstimate(requestInfo, measurement.getTenantId(), estimateIdsList);  // assume a single estimate id for now
        ResponseEntity<MeasurementResponse> measurementResponse = measurementRegistryUtil.searchMeasurements(MeasurementSearchRequest.builder().requestInfo(requestInfo).criteria(MeasurementCriteria.builder().referenceId(Collections.singletonList(measurement.getReferenceId())).isActive(true).tenantId(measurement.getTenantId()).build()).build());
        Measurement measurementFromDB = null;
        if (!measurementResponse.getBody().getMeasurements().isEmpty()) {
            measurementFromDB = measurementResponse.getBody().getMeasurements().get(0);
        }
        validateDimensions(estimateResponse, measurement, contractResponse, measurementFromDB, isUpdate,workflow);
        log.info(estimateResponse.getEstimates().get(0).getId());

        return isValidEntryDate;
    }
    public void isAllRequiredLineItemsPresent(List<String> reqLineItems,Set<String> receivedLineItems){
        for(String id:reqLineItems){
            if(!receivedLineItems.contains(id)){
                throw new CustomException(LINE_ITEMS_NOT_PROVIDED_CODE, LINE_ITEMS_NOT_PROVIDED_MSG + id);
            }
        }
    }
    public void validateByReferenceId(MeasurementServiceRequest measurementServiceRequest){
        MeasurementSearchRequest measurementSearchRequest=MeasurementSearchRequest.builder().requestInfo(measurementServiceRequest.getRequestInfo()).build();
        MeasurementCriteria criteria=MeasurementCriteria.builder().tenantId(measurementServiceRequest.getMeasurements().get(0).getTenantId()).build();
        Pagination pagination = Pagination.builder().limit(1).offSet(0).sortBy("createdTime").order(Pagination.OrderEnum.DESC).build();
        measurementSearchRequest.setPagination(pagination);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(jdbcTemplate);
        for(MeasurementService measurementService:measurementServiceRequest.getMeasurements()){
            criteria.setReferenceId(Collections.singletonList(measurementService.getReferenceId()));
            measurementSearchRequest.setCriteria(criteria);
            List<Measurement> measurements=measurementRegistryUtil.searchMeasurements(measurementSearchRequest).getBody().getMeasurements();
            if(!measurements.isEmpty()){
                List<MeasurementService> measurementServices=serviceRequestRepository.getMeasurementServicesFromMBSTable(namedParameterJdbcTemplate,Collections.singletonList(measurements.get(0).getMeasurementNumber()));
                if(!measurementServices.isEmpty()&&!(measurementServices.get(0).getWfStatus().equals(REJECTED_STATUS)||measurementServices.get(0).getWfStatus().equals(APPROVED_STATUS))){
                    throw new CustomException(NOT_VALID_REFERENCE_ID_CODE, NOT_VALID_REFERENCE_ID_MSG + measurements.get(0).getReferenceId());
                }
            }
        }
    }
    public List<String> filterValidReqLineItems(ContractResponse contractResponse, RequestInfo requestInfo, String tenantId, Map<String, ArrayList<String>> lineItemsToEstimateIdMap){

        Contract latestContract = contractResponse.getContracts().get(0);
        List<String> estimateIds = new ArrayList<>();
        List<String> idsList = new ArrayList<>();

        // filter unique estimate Ids
        for(int i=0;i<latestContract.getLineItems().size();i++){
            LineItems currLineItem = latestContract.getLineItems().get(i);
            if(!estimateIds.contains(currLineItem.getEstimateId())) estimateIds.add(currLineItem.getEstimateId());
        }

        EstimateResponse estimateResponse = getEstimate(requestInfo,tenantId,estimateIds);
        List<Estimate> estimateList =  estimateResponse.getEstimates();
        Map<String,Map<String,String>> estimateToValidReqLineItemsMap = new HashMap<>(); // think of a better name

        for(int i=0;i<estimateList.size();i++){

            String estimateId = estimateList.get(i).getId();
            List<EstimateDetail> estimateDetails = estimateList.get(i).getEstimateDetails();
            Map<String,String> currMap = new HashMap<>();

            for(int j=0;j<estimateDetails.size();j++){
                String estimateLineItemId = estimateDetails.get(j).getId();
                String estimateCategory = estimateDetails.get(j).getCategory();
                currMap.put(estimateLineItemId,estimateCategory);
            }
            estimateToValidReqLineItemsMap.put(estimateId,currMap);
        }

        List<LineItems> lineItemsList = latestContract.getLineItems();
        for(int i=0;i<lineItemsList.size();i++){
            String Id = lineItemsList.get(i).getContractLineItemRef();
            if(lineItemsToEstimateIdMap.containsKey(Id)){
                String estimateId = lineItemsToEstimateIdMap.get(Id).get(0);
                String estimateLineItemId = lineItemsToEstimateIdMap.get(Id).get(1);
                if(Objects.equals(estimateToValidReqLineItemsMap.get(estimateId).get(estimateLineItemId), SOR_CODE) || Objects.equals(estimateToValidReqLineItemsMap.get(estimateId).get(estimateLineItemId), NON_SOR_CODE)){
                    if(!idsList.contains(Id))idsList.add(Id);
                }
            }
        }

        return idsList;
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
                throw new CustomException(INCOMPLETE_MEASURES_CODE, INCOMPLETE_MEASURES_MSG);
            }
        }
    }

    /**
     * Generate a set of ACTIVE line Items from a particular Contract Response
     * @param response
     * @return
     */
    public Map<String, ArrayList<String>> getValidLineItemsId(ContractResponse response) {

        Set<String> lineItemsIdList = new HashSet<>();
        Map<String, ArrayList<String>> lineItemsToEstimateId = new HashMap<>();    // [estimateId , estimateLineItemId]
        response.getContracts().get(0).getLineItems().forEach(
                lineItems -> {
                    if (lineItems.getStatus().toString().equalsIgnoreCase(ACTIVE_STATUS) ||
                            ((response.getContracts().get(0).getBusinessService().equals(CONTRACT_REVISION_ESTIMATE))||
                                    (response.getContracts().get(0).getBusinessService().equals(BUSINESS_SERVICE_TE_CONTRACT))
                                    &&lineItems.getStatus().toString().equalsIgnoreCase(INWORKFLOW) )) {
                        lineItemsIdList.add(lineItems.getContractLineItemRef());  // id  remove this
                        ArrayList<String> arr = new ArrayList<>();
                        arr.add(lineItems.getEstimateId());
                        arr.add(lineItems.getEstimateLineItemId());
                        lineItemsToEstimateId.put(lineItems.getContractLineItemRef(), arr);
                    }
                }
        );
        return lineItemsToEstimateId;
    }

    public EstimateResponse getEstimate(RequestInfo requestInfo, String tenantId, List<String> estimateIdsList) {

        String estimateSearchUrl = mbServiceConfiguration.getEstimateHost()+ mbServiceConfiguration.getEstimatePath();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(estimateSearchUrl);
        builder.queryParam("tenantId",tenantId);
        builder.queryParam("ids",estimateIdsList);

        String preparedUrl = builder.toUriString();

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        // How to search by using multiples ids :?
        EstimateResponse estimateResponse = restTemplate.postForObject(preparedUrl, requestInfoWrapper, EstimateResponse.class);
        return estimateResponse;
    }

    public void validateDimensions(EstimateResponse estimateResponse, Measurement measurement, ContractResponse contractResponse, Measurement measurementFromDB, Boolean isUpdate,Workflow workflow)  {

        Map<String, String> targetIdToEstimateLineItemRef = contractResponse.getContracts().get(0).getLineItems().stream().collect(Collectors.toMap(LineItems::getContractLineItemRef, LineItems::getEstimateLineItemId));

        Map<String, EstimateDetail> estimateLineItemIdToEstimateDetail = estimateResponse.getEstimates().get(0).getEstimateDetails().stream().collect(Collectors.toMap(EstimateDetail::getId, estimateDetail -> estimateDetail));

        Map<String, BigDecimal> sorIdToCumulativeValueMap = new HashMap<>();
        if (measurementFromDB != null && !measurementFromDB.getMeasures().isEmpty()) {
            for (Measure measure : measurementFromDB.getMeasures()) {
                String lineItemId = targetIdToEstimateLineItemRef.get(measure.getTargetId());
                if (lineItemId == null)
                    throw new CustomException(ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_CODE, ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_MSG + measure.getTargetId());

                // Get the EstimateDetail corresponding to the lineItemId
                EstimateDetail estimateDetail = estimateLineItemIdToEstimateDetail.get(lineItemId);
                if (estimateDetail == null)
                    throw new CustomException(ESTIMATE_DETAILS_NOT_PRESENT_CODE, ESTIMATE_DETAILS_NOT_PRESENT_MSG + lineItemId);
                BigDecimal cumulativeValue = getCummulativeValue(isUpdate, measure, estimateDetail);

                sorIdToCumulativeValueMap.merge(estimateDetail.getSorId(), cumulativeValue, BigDecimal::add);
               // targetIdToCumulativeValue.put(measure.getTargetId(), cumulativeValue);
            }
        }

        // Map to store Measure objects grouped by SOR ID
        Map<String, List<Measure>> sorIdToMeasuresMap = new HashMap<>();
        // Map to store EstimateDetail objects grouped by SOR ID
        Map<String, List<EstimateDetail>> sorIdToEstimateDetailMap = new HashMap<>();

       // Iterate over the measures and populate the maps
        for (Measure measure : measurement.getMeasures()) {
            // Get the lineItemId corresponding to targetId
            String lineItemId = targetIdToEstimateLineItemRef.get(measure.getTargetId());
            if (lineItemId == null)
                throw new CustomException(ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_CODE, ESTIMATE_LINE_ITEM_ID_NOT_PRESENT_MSG + measure.getTargetId());

            // Get the EstimateDetail corresponding to the lineItemId
            EstimateDetail estimateDetail = estimateLineItemIdToEstimateDetail.get(lineItemId);
            if (estimateDetail == null)
                throw new CustomException(ESTIMATE_DETAILS_NOT_PRESENT_CODE, ESTIMATE_DETAILS_NOT_PRESENT_MSG + lineItemId);

            measurementServiceUtil.validateDimensions(measure,isUpdate);

            // Group Measure objects by SOR ID
            sorIdToMeasuresMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(measure);

            // Store EstimateDetail objects by SOR ID
            sorIdToEstimateDetailMap.computeIfAbsent(estimateDetail.getSorId(), k -> new ArrayList<>()).add(estimateDetail);

        }

        if( (SENT_BACK).equals(workflow.getAction())|| (SEND_BACK_TO_ORIGINATOR).equals(workflow.getAction())){
         log.info("For Sent Back or Send Back to Originator we don't need to implement the validation on dimensions");
        }else{
        List<EstimateDetail> estimateDetails=estimateResponse.getEstimates().get(0).getEstimateDetails();
        // Iterate over the EstimateDetail objects
        estimateDetails.forEach(estimateDetail -> {
            if(!estimateDetail.getCategory().equals(OVERHEAD)){
            // Get the SOR ID from the EstimateDetail
            String sorId = estimateDetail.getSorId();

            // Get the list of Measure objects corresponding to the SOR ID from sorIdToMeasuresMap
            List<Measure> measureList = sorIdToMeasuresMap.getOrDefault(sorId, Collections.emptyList());
                BigDecimal totalCurrValue = BigDecimal.ZERO;
                for (Measure measure : measureList) {
                    String lineItemId = targetIdToEstimateLineItemRef.get(measure.getTargetId());
                    EstimateDetail estimateDetailForMeasure = estimateLineItemIdToEstimateDetail.get(lineItemId);
                    BigDecimal totalBreadth = measure.getBreadth();
                    BigDecimal totalHeight = measure.getHeight();
                    BigDecimal totalLength = measure.getLength();
                    BigDecimal totalNumItems = measure.getNumItems();

                    BigDecimal currValue = totalBreadth
                            .multiply(totalHeight)
                            .multiply(totalLength)
                            .multiply(totalNumItems);


                    totalCurrValue = estimateDetailForMeasure.getIsDeduction()?totalCurrValue.subtract(currValue):totalCurrValue.add(currValue);
                }



            BigDecimal totalValue = sorIdToCumulativeValueMap.get(sorId)!=null ?totalCurrValue.add(sorIdToCumulativeValueMap.get(sorId)):totalCurrValue.add(BigDecimal.ZERO);

            // Get the list of EstimateDetail objects corresponding to the SOR ID from sorIdToEstimateDetailMap
            List<EstimateDetail> estimateDetailList = sorIdToEstimateDetailMap.getOrDefault(sorId, Collections.emptyList());


            // Calculate the total noOfUnit for the group of EstimateDetail objects corresponding to the SOR ID
                BigDecimal totalNoOfUnit= BigDecimal.ZERO;
                for(EstimateDetail estimatedDetail:estimateDetailList){
                    if(estimatedDetail.getIsDeduction()){
                        totalNoOfUnit=totalNoOfUnit.subtract(BigDecimal.valueOf(estimatedDetail.getNoOfunit()));
                    }else{
                        totalNoOfUnit=totalNoOfUnit.add(BigDecimal.valueOf(estimatedDetail.getNoOfunit()));
                    }

                }


            if (totalValue.compareTo(totalNoOfUnit) > 0) {
                throw new CustomException(TOTAL_VALUE_GREATER_THAN_ESTIMATE_CODE, String.format(TOTAL_VALUE_GREATER_THAN_ESTIMATE_MSG, sorId,totalValue, totalNoOfUnit));
            }
            // Now you can use currValue as needed for the group of Measure objects corresponding to the SOR ID
        }});
        }




    }

    private static BigDecimal getCummulativeValue(Boolean isUpdate, Measure measure, EstimateDetail estimateDetail) {
        BigDecimal cumulativeValue=measure.getCumulativeValue();
        if (isUpdate)
            cumulativeValue = cumulativeValue.subtract(measure.getCurrentValue());

        return estimateDetail.getIsDeduction()?cumulativeValue.multiply(BigDecimal.valueOf(-1)):cumulativeValue;
    }

}
