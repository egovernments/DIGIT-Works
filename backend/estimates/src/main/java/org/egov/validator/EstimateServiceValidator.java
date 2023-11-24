package org.egov.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.repository.EstimateRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.*;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class EstimateServiceValidator {

    private final MDMSUtils mdmsUtils;

    private final EstimateRepository estimateRepository;

    private final ProjectUtil projectUtil;
    private final EstimateServiceConfiguration config;
    final ObjectMapper mapper;

    private final ContractUtils contractUtils;
    private final MeasurementUtils measurementUtils;
    private final EstimateServiceUtil estimateServiceUtil;

    private static final String JSONPATH_ERROR = "JSONPATH_ERROR";
    private static final String MDMS_RES = "$.MdmsRes.";
    private static final String IS_NOT_PRESENT_IN_MDMS = " is not present in MDMS";
    private static final String FAILED_TO_PARSE_MDMS_RESPONSE = "Failed to parse mdms response";
    private static final String INVALID_ESTIMATE = "INVALID_ESTIMATE";

    @Autowired
    public EstimateServiceValidator(MDMSUtils mdmsUtils, EstimateRepository estimateRepository, ProjectUtil projectUtil, EstimateServiceConfiguration config, ObjectMapper mapper, ContractUtils contractUtils, MeasurementUtils measurementUtils, EstimateServiceUtil estimateServiceUtil) {
        this.mdmsUtils = mdmsUtils;
        this.estimateRepository = estimateRepository;
        this.projectUtil = projectUtil;
        this.config = config;
        this.mapper = mapper;
        this.contractUtils = contractUtils;
        this.measurementUtils = measurementUtils;
        this.estimateServiceUtil = estimateServiceUtil;
    }


    /**
     * validate the create estimate request for all the mandatory
     * and/or
     * invalid attributes
     *
     * @param request
     */
    public void validateEstimateOnCreate(EstimateRequest request) {
        log.info("EstimateServiceValidator::validateEstimateOnCreate");
        Map<String, String> errorMap = new HashMap<>();
        Estimate estimate = request.getEstimate();
        RequestInfo requestInfo = request.getRequestInfo();
        Workflow workflow = request.getWorkflow();
        Estimate previousEstimate = null;

        validateRequestInfo(requestInfo);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow);
        if(Boolean.TRUE.equals(estimateServiceUtil.isRevisionEstimate(request))){
            if(estimate.getEstimateNumber() == null){
                throw new CustomException(INVALID_ESTIMATE, "Estimate number is mandatory for revision estimate");
            }
            EstimateSearchCriteria estimateSearchCriteria = EstimateSearchCriteria.builder().tenantId(estimate.getTenantId()).estimateNumber(estimate.getEstimateNumber()).sortOrder(EstimateSearchCriteria.SortOrder.DESC).sortBy(
                    EstimateSearchCriteria.SortBy.createdTime).build();
            List<Estimate> estimateList = estimateRepository.getEstimate(estimateSearchCriteria);
            if(Estimate.StatusEnum.INWORKFLOW.equals(estimateList.get(0).getStatus())){
                throw new CustomException(INVALID_ESTIMATE, "Estimate is already in workflow");
            }
            for(Estimate estimate1: estimateList){
                if(estimate1.getWfStatus().equals(ESTIMATE_APPROVED_STATUS)){
                    previousEstimate = estimate1;
                    break;
                }
            }
            validatepreviousEstimate(estimate, errorMap, previousEstimate);
        }
        List<EstimateDetail> estimateDetails =estimate.getEstimateDetails();

        validateRequestOnMDMSV1AndV2(request,errorMap, true,previousEstimate);
        validateProjectId(request);
        validateNoOfUnit(estimateDetails);

        if(Boolean.TRUE.equals(config.getRevisionEstimateMeasurementValidation()) && Boolean.TRUE.equals(estimateServiceUtil.isRevisionEstimate(request)) && previousEstimate != null){
            validateContractAndMeasurementBook(request, previousEstimate, errorMap);
        }

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validatepreviousEstimate(Estimate estimate, Map<String, String> errorMap, Estimate previousEstimate) {
        log.info("EstimateServiceValidator::validatepreviousEstimate");
        if(previousEstimate == null){
            errorMap.put(INVALID_ESTIMATE, "Approved Previous estimate not found for revision estimate");
        }
        else{
            log.info("Previous estimate found for the given estimate -> "+ previousEstimate);
            List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
            List<EstimateDetail> prevEstimateDetails = previousEstimate.getEstimateDetails();
            HashMap<String,EstimateDetail> prevEstimateDetailMap = new HashMap<>();
            prevEstimateDetails.forEach(estimateDetail ->
                    prevEstimateDetailMap.put(estimateDetail.getId(),estimateDetail)
            );

            for(EstimateDetail estimateDetail: estimateDetails){
                if(estimateDetail.getPreviousLineItemId() != null && (!prevEstimateDetailMap.containsKey(estimateDetail.getPreviousLineItemId()))){
                        errorMap.put("INVALID_ESTIMATE_DETAIL", "Previous Line Id is invalid for revision estimate");
                }
            }
        }
    }

    private void validateContractAndMeasurementBook(EstimateRequest estimateRequest, Estimate estimateForRevision, Map<String, String> errorMap) {
        log.info("EstimateServiceValidator::validateContractAndMeasurementBook");
        Object contractResponse = contractUtils.getContractDetails(estimateRequest.getRequestInfo(), estimateForRevision);
        final String jsonPathForContractNumber = "$.contracts.*.contractNumber";
        List<Object> contractNumbers = null;
        try {
            contractNumbers = JsonPath.read(contractResponse, jsonPathForContractNumber);
        } catch (Exception e) {
            throw new CustomException(JSONPATH_ERROR, "Failed to parse contract search response");
        }
        if(contractNumbers == null || contractNumbers.isEmpty()){
            log.info("No contract found for the given estimate");
        }
        else{
            log.info("Contract found for the given estimate");
            String contractNumber = contractNumbers.get(0).toString();
            Object measurementResponse = measurementUtils.getMeasurementDetails(estimateRequest, contractNumber);
            validateMeasurement(measurementResponse, estimateRequest,contractResponse, errorMap);
        }
    }

    private void validateMeasurement(Object measurementResponse, EstimateRequest estimateRequest,Object contractResponse, Map<String, String> errorMap) {
        log.info("EstimateServiceValidator::validateMeasurement");
        String jsonPathForContractLineItemRef = "$.contracts[*].lineItems[?(@.estimateLineItemId=='{{yourDynamicValue}}')].contractLineItemRef";
        String jsonPathForMeasurementBook = "$.measurements[*].measures[?(@.targetId=='{{yourDynamicValue}}')].cumulativeValue";
        List<EstimateDetail> estimateDetail = estimateRequest.getEstimate().getEstimateDetails();
        estimateDetail.forEach(estimateDetail1 ->
        {
            if(!estimateDetail1.getCategory().equals(OVERHEAD_CODE)){
                List<String> contractLineItemRef = null;
                try {
                    contractLineItemRef = JsonPath.read(contractResponse, jsonPathForContractLineItemRef.replace("{{yourDynamicValue}}", estimateDetail1.getPreviousLineItemId()));
                } catch (Exception e) {
                    throw new CustomException(JSONPATH_ERROR, "Failed to parse contract search response");
                }
                String contractLineItemRefId = contractLineItemRef.get(0);
                List<Integer> measurementBook = null;
                try {
                    measurementBook = JsonPath.read(measurementResponse, jsonPathForMeasurementBook.replace("{{yourDynamicValue}}", contractLineItemRefId));
                } catch (Exception e) {
                    throw new CustomException(JSONPATH_ERROR, "Failed to parse measurement search response");
                }
                if(measurementBook == null || measurementBook.isEmpty()){
                    log.info("No measurement found for the given estimate");
                }
                else if(estimateDetail1.getNoOfunit() > measurementBook.get(0)){
                    errorMap.put("INVALID_ESTIMATE_DETAIL", "No of Unit should be less than or equal to measurement book cumulative value");
                }
            }
        });
    }

    private void validateMDMSDataForUOM(Estimate estimate, Object mdmsDataForUOM, Map<String, String> errorMap) {
        log.info("EstimateServiceValidator::validateMDMSDataForUOM");
        List<String> reqUom = new ArrayList<>();

        if(estimate.getEstimateDetails()!=null && !estimate.getEstimateDetails().isEmpty()) {
            reqUom = estimate.getEstimateDetails().stream()
                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getUom()))
                    .map(EstimateDetail::getUom)
                    .collect(Collectors.toList());
        }
        final String jsonPathForUom = MDMS_RES + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_UOM + ".*.code";
        List<Object> uomRes = null;
        try {
            uomRes = JsonPath.read(mdmsDataForUOM, jsonPathForUom);
        } catch (Exception e) {
            throw new CustomException(JSONPATH_ERROR, FAILED_TO_PARSE_MDMS_RESPONSE);
        }

        //estimate detail - uom
        if (!CollectionUtils.isEmpty(uomRes) && !CollectionUtils.isEmpty(reqUom)) {
            reqUom.removeAll(uomRes);
            if (!CollectionUtils.isEmpty(reqUom)) {
                errorMap.put("INVALID_UOM", "Invalid UOM");
            }
        }
    }

    /**
     * validate the no of units in estimate details
     */
    public void validateNoOfUnit(List<EstimateDetail> estimateDetails){
        log.info("EstimateServiceValidator::validateNoOfUnit");
        for(int i=0;i<estimateDetails.size();i++){
            EstimateDetail estimateDetail = estimateDetails.get(i);

            if(!estimateDetail.getCategory().equals(OVERHEAD_CODE)){
                if(estimateDetail.getNoOfunit()==null){
                    throw new CustomException("NO_OF_UNIT", "noOfUnit is mandatory");
                }
                BigDecimal total =new BigDecimal(1);
                boolean allNull =true;
                if(estimateDetail.getLength()!=null && estimateDetail.getLength().signum() != 0){
                    total =total.multiply(estimateDetail.getLength());
                    allNull =false;
                }
                if(estimateDetail.getWidth()!=null && estimateDetail.getWidth().signum() != 0){
                    total =total.multiply(estimateDetail.getWidth());
                    allNull = false;
                }
                if(estimateDetail.getHeight()!=null && estimateDetail.getHeight().signum() != 0){
                    total =total.multiply(estimateDetail.getHeight());
                    allNull =false;
                }
                if(estimateDetail.getQuantity()!=null && estimateDetail.getQuantity().signum() != 0){
                    total =total.multiply(estimateDetail.getQuantity());
                    allNull = false;
                }
                double totalNew = total.doubleValue();
                if(totalNew==estimateDetail.getNoOfunit() || allNull){
                    log.info("No of unit is valid");
                }
                else{
                    throw new CustomException("NO_OF_UNIT", "noOfUnit value is not correct");
                }
            }
        }
    }


    private void validateProjectId(EstimateRequest estimateRequest) {
        log.info("EstimateServiceValidator::validateProjectId");
        final String projectJsonPath = "$.Project.*";
        List<Object> projects = null;

        Object projectRes = projectUtil.getProjectDetails(estimateRequest);

        if (ObjectUtils.isNotEmpty(projectRes)) {
            try {
                projects = JsonPath.read(projectRes, projectJsonPath);
            } catch (Exception e) {
                throw new CustomException(JSONPATH_ERROR, "Failed to parse project search response");
            }
        }

        if (projects == null || projects.isEmpty())
            throw new CustomException("PROJECT_ID", "The project id : " + estimateRequest.getEstimate().getProjectId() + " is invalid");
    }

    private void validateWorkFlow(Workflow workflow) {
        log.info("EstimateServiceValidator::validateWorkFlow");
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            throw new CustomException("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }

    }

    private void validateEstimate(Estimate estimate, Map<String, String> errorMap) {
        log.info("EstimateServiceValidator::validateEstimate");
        if (estimate == null) {
            throw new CustomException("ESTIMATE", "Estimate is mandatory");
        }
        if (StringUtils.isBlank(estimate.getTenantId())) {
            errorMap.put("TENANT_ID", "TenantId is mandatory");
        }
        if (estimate.getStatus() == null || !EnumUtils.isValidEnum(Estimate.StatusEnum.class, estimate.getStatus().toString())) {
            errorMap.put("STATUS", "Status is mandatory");
        }
        if (StringUtils.isBlank(estimate.getName())) {
            errorMap.put("NAME", "Name is mandatory");
        }
        if (StringUtils.isBlank(estimate.getExecutingDepartment())) {
            errorMap.put("EXECUTING_DEPARTMENT", "Executing department is mandatory");
        }
        if (StringUtils.isBlank(estimate.getProjectId())) {
            errorMap.put("PROJECT_ID", "ProjectId is mandatory");
        }
        if (estimate.getAddress() == null) {
            errorMap.put("ADDRESS", "Address is mandatory");
        }

        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        if (estimateDetails == null || estimateDetails.isEmpty()) {
            errorMap.put("ESTIMATE_DETAILS", "Estimate detail is mandatory");
        } else {
            validateEstimateDetails(estimateDetails, errorMap);
        }
        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }
    }
    private void validateEstimateDetails(List<EstimateDetail> estimateDetails,Map<String,String> errorMap){
        log.info("EstimateServiceValidator::validateEstimateDetails");
        Boolean isSOROrNonSORPresent = false;
            for (EstimateDetail estimateDetail : estimateDetails) {
                if (StringUtils.isBlank(estimateDetail.getSorId()) && StringUtils.isBlank(estimateDetail.getName())) {
                    errorMap.put("ESTIMATE.DETAIL.NAME.OR.SOR.ID", "Estimate detail's name or sorId is mandatory");
                }

                if((estimateDetail.getCategory().equalsIgnoreCase(SOR_CODE) || estimateDetail.getCategory().equalsIgnoreCase(NON_SOR_CODE)) && (estimateDetail.getUnitRate()==null)){
                    errorMap.put("ESTIMATE.DETAIL.UNIT_RATE", "Selected SOR doesn't have a rate effective for the given period. Please update the rate before adding it to an estimate.");
                }
                if (estimateDetail.getAmountDetail() == null || estimateDetail.getAmountDetail().isEmpty()) {
                    errorMap.put("ESTIMATE.DETAIL.AMOUNT.DETAILS", "Amount details are mandatory");
                } else {
                    for (AmountDetail amountDetail : estimateDetail.getAmountDetail()) {
                        if (amountDetail.getAmount() == null || amountDetail.getAmount().isNaN()) {
                            errorMap.put("ESTIMATE.DETAIL.AMOUNT.DETAILS.AMOUNT", "Estimate amount detail's amount is mandatory");
                        }
                    }
                }
                if(estimateDetail.getCategory().equals(SOR_CODE) || estimateDetail.getCategory().equals(NON_SOR_CODE)){
                    isSOROrNonSORPresent = true;
                }
            }
            if(Boolean.FALSE.equals(isSOROrNonSORPresent)){
                errorMap.put("ESTIMATE.DETAIL.CATEGORY", "Atleast one SOR or Non-SOR should be present");
            }
    }

    private void validateRequestInfo(RequestInfo requestInfo) {
        log.info("EstimateServiceValidator::validateRequestInfo");
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    /**
     * validate the mdms data for sorId in mdmsv2
     */
    private void validateMDMSDataV2ForSor(Estimate estimate ,Object mdmsData, Set<String>uniqueIdentifiers, Map<String, String> errorMap ){
        log.info("EstimateServiceValidator::validateMDMSDataV2");
        int uniqueIdentifiersSizeInput = uniqueIdentifiers.size();

        final  String jsonPathForTestSor = "$.MdmsRes.WORKS-SOR.SOR[*].id";
        final String jsonPathForUom = "$.MdmsRes.WORKS-SOR.SOR[*].uom";
        List<Object> sorIdRes = null;
        List<Object> uomRes = null;
        try {
            sorIdRes = JsonPath.read(mdmsData,jsonPathForTestSor);
            uomRes = JsonPath.read(mdmsData,jsonPathForUom);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(JSONPATH_ERROR, FAILED_TO_PARSE_MDMS_RESPONSE);
        }
        int uniqueIdentifiersSizeRes = sorIdRes.size();
        if(uniqueIdentifiersSizeInput!=uniqueIdentifiersSizeRes){
            errorMap.put("INVALId_SOR_ID", "The sor id is not present in MDMS");
        }

        Map<String,String> sorIdUomMap = new HashMap<>();
        for(int i=0;i<sorIdRes.size();i++){
            sorIdUomMap.put(sorIdRes.get(i).toString(),uomRes.get(i).toString());
        }

        estimate.getEstimateDetails().forEach(estimateDetail -> {
            if(estimateDetail.getCategory().equalsIgnoreCase(MDMS_SOR_MASTER_NAME) && (!estimateDetail.getUom().equals(sorIdUomMap.get(estimateDetail.getSorId())))){
                    errorMap.put("INVALID_UOM", "Invalid UOM");

            }
        });
    }

    private void validateDateAndRates (Estimate estimate, Object mdmsData, Map<String, String> errorMap,Estimate previousEstimate,Boolean isCreate) {
        log.info("Validating Date");
        List<Object> mdmsRates = null;
        Long validatingDate = null;
        HashMap<String,EstimateDetail> previousEstimateDetailMap = new HashMap<>();

        if (estimate.getAuditDetails() == null) {
            validatingDate = System.currentTimeMillis();
        } else {
            validatingDate = estimate.getAuditDetails().getCreatedTime();
        }
        final  String jsonPathForRates = "$.MdmsRes.WORKS-SOR.Rates";
        try {
            mdmsRates = JsonPath.read(mdmsData, jsonPathForRates);
        }catch (Exception e) {
            throw new CustomException(JSONPATH_ERROR, FAILED_TO_PARSE_MDMS_RESPONSE);
        }
        if(estimate.getBusinessService() != null && estimate.getBusinessService().equals(config.getRevisionEstimateBusinessService()) && previousEstimate != null) {
            List<EstimateDetail> previousEstimateDetails = previousEstimate.getEstimateDetails();
            previousEstimateDetails.forEach(estimateDetail1 ->
                    previousEstimateDetailMap.put(estimateDetail1.getId(),estimateDetail1)
            );
        }
        for (EstimateDetail estimateDetail : estimate.getEstimateDetails()) {
            if (!estimateDetail.getCategory().equalsIgnoreCase("SOR"))
                continue;
            if(estimate.getBusinessService() != null && estimate.getBusinessService().equals(config.getRevisionEstimateBusinessService()) && estimateDetail.getPreviousLineItemId() != null){
                validateRateOnPreviousEstimate(estimateDetail, errorMap, previousEstimateDetailMap,isCreate);
            }else{
                List<Object> ratesForGivenSor;
                String jsonPathRatesForGivenSor = "$.[?(@.sorId=='{sorId}')]";
                try {
                    jsonPathRatesForGivenSor = jsonPathRatesForGivenSor.replace("{sorId}", estimateDetail.getSorId());
                    ratesForGivenSor = JsonPath.read(mdmsRates, jsonPathRatesForGivenSor);
                }catch (Exception e) {
                    throw new CustomException(JSONPATH_ERROR, FAILED_TO_PARSE_MDMS_RESPONSE);
                }
                JsonNode filteredRates = mapper.convertValue(ratesForGivenSor, JsonNode.class);
                validateDate(filteredRates, estimateDetail, errorMap, validatingDate);
            }
        }

    }

    private void validateRateOnPreviousEstimate(EstimateDetail estimateDetail, Map<String, String> errorMap, HashMap<String,EstimateDetail> previousEstimateDetailMap,Boolean isCreate) {
        log.info("EstimateServiceValidator::validateRateOnPreviousEstimate");
        if(previousEstimateDetailMap.containsKey(isCreate?estimateDetail.getPreviousLineItemId():estimateDetail.getId())){
            EstimateDetail previousEstimateDetail = previousEstimateDetailMap.get(isCreate?estimateDetail.getPreviousLineItemId():estimateDetail.getId());
            if(!Objects.equals(previousEstimateDetail.getUnitRate(), estimateDetail.getUnitRate())){
                errorMap.put("INVALID_UNIT_RATE", "Unit rate is not matching with previous approved estimate");
            }
        }
        else{
            errorMap.put("INVALID_PREVIOUS_LINE_ITEM_ID", "Previous line item id is invalid");
        }
    }

    private void validateDate (JsonNode filteredRates, EstimateDetail estimateDetail, Map<String, String> errorMap, Long validatingDate) {
        List<JsonNode> jsonList = new ArrayList<>();
        for (JsonNode node : filteredRates) {
            jsonList.add(node);
        }

        Comparator<JsonNode> comparator = (o1, o2) -> {
            String startDate1 = o1.get(VALID_FROM).asText();
            String startDate2 = o2.get(VALID_FROM).asText();
            return startDate1.compareTo(startDate2);
        };

        Collections.sort(jsonList, comparator.reversed());

        JsonNode sortedJsonArray = mapper.valueToTree(jsonList);

        for (int i = 0; i < sortedJsonArray.size(); i++) {
            Long validFrom = null;
            Long validTo = null;
            try {
                String str =  sortedJsonArray.get(i).get(VALID_FROM).asText();
                validFrom = Long.parseLong(str);
            }catch (Exception e) {
                log.error("No start date for this object");
            }
            try {
                String strVT = sortedJsonArray.get(i).get("validTo").asText();
                validTo = Long.parseLong(strVT);
            }catch (Exception e) {
                log.info("No end date for this object");
            }

            if ((validFrom != null && validatingDate < validFrom) || (validTo != null && validatingDate > validTo)) {
                continue;
            }
            if (sortedJsonArray.get(i).get("rate").asDouble() != estimateDetail.getUnitRate()) {
                log.error("Rates provided in request do not match rates in mdms");
                errorMap.put("RATES_MISMATCH", "Rates provided in request do not match rates in mdms");
            }
            return;
        }
        log.error("No Rates found for the given date and time");
        errorMap.put("DATES_MISMATCH", "No Rates found for the given date and time");
    }

    private void validateMDMSData(Estimate estimate, Object mdmsData, Object mdmsDataForOverHead, Map<String, String> errorMap, boolean isCreate) {
        log.info("EstimateServiceValidator::validateMDMSData");
        List<String> reqEstimateDetailCategories = new ArrayList<>();
        List<String> reqEstimateDetailNamesForOverHeads = new ArrayList<>();
        Map<String, List<String>> reqEstimateDetailNameMap = new HashMap<>();
        Map<String, List<String>> overheadAmountTypeMap = new HashMap<>();
        if (estimate.getEstimateDetails() != null && !estimate.getEstimateDetails().isEmpty()) {
            reqEstimateDetailCategories = estimate.getEstimateDetails().stream()
                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getCategory()))
                    .map(EstimateDetail::getCategory)
                    .collect(Collectors.toList());

            reqEstimateDetailNamesForOverHeads = estimate.getEstimateDetails().stream()
                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getName()) && estimateDetail.getCategory().equalsIgnoreCase(OVERHEAD_CODE))
                    .map(EstimateDetail::getName)
                    .collect(Collectors.toList());

            //name map for each category
            for (EstimateDetail estimateDetail : estimate.getEstimateDetails()) {
                if (!isCreate && estimateDetail.isActive()) {
                    if (reqEstimateDetailNameMap.containsKey(estimateDetail.getCategory())) {
                        reqEstimateDetailNameMap.get(estimateDetail.getCategory()).add(estimateDetail.getName());
                    } else {
                        List<String> names = new ArrayList<>();
                        names.add(estimateDetail.getName());
                        reqEstimateDetailNameMap.put(estimateDetail.getCategory(), names);
                    }
                }
            }

            //Amount type
            for (EstimateDetail estimateDetail : estimate.getEstimateDetails()) {
                if (!isCreate && estimateDetail.isActive()) {
                    if (overheadAmountTypeMap.containsKey(estimateDetail.getCategory())) {
                        List<String> amountTypeList = estimateDetail.getAmountDetail().stream()
                                .filter(amountDetail -> StringUtils.isNotBlank(amountDetail.getType())
                                        && amountDetail.isActive())
                                .map(AmountDetail::getType)
                                .collect(Collectors.toList());
                        List<String> existingAmountTypeList = overheadAmountTypeMap.get(estimateDetail.getCategory());
                        existingAmountTypeList.addAll(amountTypeList);
                        overheadAmountTypeMap.put(estimateDetail.getCategory(), existingAmountTypeList);
                    } else {
                        List<String> amountTypeList = estimateDetail.getAmountDetail().stream()
                                .filter(amountDetail -> StringUtils.isNotBlank(amountDetail.getType())
                                        && amountDetail.isActive())
                                .map(AmountDetail::getType)
                                .collect(Collectors.toList());
                        overheadAmountTypeMap.put(estimateDetail.getCategory(), amountTypeList);
                    }
                }
            }
        }
        final String jsonPathForWorksDepartment = MDMS_RES + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*";
        final String jsonPathForTenants = MDMS_RES + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String jsonPathForCategories = MDMS_RES + MDMS_WORKS_MODULE_NAME + "." + MASTER_CATEGORY + ".*";
        final String jsonPathForOverHead = MDMS_RES + MDMS_WORKS_MODULE_NAME + "." + MASTER_OVERHEAD + ".*";

        List<Object> deptRes = null;
        List<Object> tenantRes = null;
        List<Object> categoryRes = null;
        List<Object> overHeadRes = null;
        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForWorksDepartment);
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            categoryRes = JsonPath.read(mdmsData, jsonPathForCategories);
            overHeadRes = JsonPath.read(mdmsDataForOverHead, jsonPathForOverHead);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(JSONPATH_ERROR, FAILED_TO_PARSE_MDMS_RESPONSE);
        }

        if (CollectionUtils.isEmpty(deptRes))
            errorMap.put("INVALID_EXECUTING_DEPARTMENT_CODE", "The executing department code: " + estimate.getExecutingDepartment() + IS_NOT_PRESENT_IN_MDMS);

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT_ID", "The tenant: " + estimate.getTenantId() + IS_NOT_PRESENT_IN_MDMS);

        //estimate detail - category
        if (!CollectionUtils.isEmpty(categoryRes) && !CollectionUtils.isEmpty(reqEstimateDetailCategories)) {
            reqEstimateDetailCategories.removeAll(categoryRes);
            if (!CollectionUtils.isEmpty(reqEstimateDetailCategories)) {
                errorMap.put("ESTIMATE_DETAIL.CATEGORY", "The categories : " + reqEstimateDetailCategories + IS_NOT_PRESENT_IN_MDMS);
            }
        }

        //estimate detail - overHeadName
        if (!CollectionUtils.isEmpty(overHeadRes) && !CollectionUtils.isEmpty(reqEstimateDetailNamesForOverHeads)) {
            reqEstimateDetailNamesForOverHeads.removeAll(overHeadRes);
            if (!CollectionUtils.isEmpty(reqEstimateDetailNamesForOverHeads)) {
                errorMap.put("INVALID_OVERHEAD", "Invalid overhead");
            }
        }


        //estimate detail - name
        if (!CollectionUtils.isEmpty(overHeadRes) && !CollectionUtils.isEmpty(reqEstimateDetailNameMap)) {

            for (Map.Entry<String, List<String>> category : reqEstimateDetailNameMap.entrySet()) {

                if (StringUtils.isNotBlank(category.getKey()) && category.getKey().equalsIgnoreCase(OVERHEAD_CODE)) {
                    List<String> reqEstimateDetailNames = reqEstimateDetailNameMap.get(category.getKey());

                    Map<String, Integer> reqNameMap = new HashMap<>();
                    for (String reqName : reqEstimateDetailNames) {
                        if (reqNameMap.containsKey(reqName)) {
                            reqNameMap.put(reqName, reqNameMap.get(reqName) + 1);
                        } else {
                            reqNameMap.put(reqName, 1);
                        }
                    }

                    List<String> invalidNames = new ArrayList<>();
                    for (Map.Entry<String, Integer> reqName : reqNameMap.entrySet()) {
                        if (overHeadRes.contains(reqName.getKey())) {
                            if (reqNameMap.get(reqName.getKey()) > 1) {
                                errorMap.put("ESTIMATE_DETAIL.DUPLICATE.NAME", "The name : " + reqName.getKey() + " is added more than one time");
                                break;
                            }
                        } else {
                            invalidNames.add(reqName.getKey());
                        }
                    }
                    if (!CollectionUtils.isEmpty(invalidNames)) {
                        errorMap.put("ESTIMATE_DETAIL.NAME", "The names : " + invalidNames + IS_NOT_PRESENT_IN_MDMS);
                    }
                }
            }

        }

        //Overhead -amount type validation
        if (!CollectionUtils.isEmpty(overHeadRes) && !CollectionUtils.isEmpty(overheadAmountTypeMap)) {
            for (Map.Entry<String, List<String>> overheadCategoryKey : overheadAmountTypeMap.entrySet()) {
                if (StringUtils.isNotBlank(overheadCategoryKey.getKey()) && overheadCategoryKey.getKey().equalsIgnoreCase(OVERHEAD_CODE)) {
                    List<String> amountTypes = overheadCategoryKey.getValue();
                    //frequency map
                    Map<String, Integer> reqTypeMap = new HashMap<>();
                    for (String type : amountTypes) {
                        if (reqTypeMap.containsKey(type)) {
                            reqTypeMap.put(type, reqTypeMap.get(type) + 1);
                        } else {
                            reqTypeMap.put(type, 1);
                        }
                    }

                    List<String> invalidList = new ArrayList<>();
                    for (Map.Entry<String, Integer> reqType : reqTypeMap.entrySet()) {
                        if (overHeadRes.contains(reqType.getKey())) {
                            if (reqTypeMap.get(reqType.getKey()) > 1) {
                                errorMap.put("ESTIMATE_DETAIL.AMOUNT_DETAIL.DUPLICATE.TYPE", "The amount type : " + reqType.getKey() + " is added more than one time");
                            }
                        } else {
                            invalidList.add(reqType.getKey());
                        }
                    }

                    if (!CollectionUtils.isEmpty(invalidList)) {
                        errorMap.put("ESTIMATE_DETAIL.AMOUNT_DETAIL.TYPE", "The amount types : " + invalidList + IS_NOT_PRESENT_IN_MDMS);
                    }
                }
            }
        }

    }


    /**
     * validate the search estimate request for all the mandatory attributes
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     */
    public void validateEstimateOnSearch(RequestInfoWrapper requestInfoWrapper, EstimateSearchCriteria searchCriteria) {
        log.info("EstimateServiceValidator::validateEstimateOnSearch");
        if (searchCriteria == null || requestInfoWrapper == null || requestInfoWrapper.getRequestInfo() == null) {
            throw new CustomException("ESTIMATE_SEARCH_CRITERIA_REQUEST", "Estimate search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "TenantId is mandatory");
        }
        if (searchCriteria.getIds() != null && !searchCriteria.getIds().isEmpty() && searchCriteria.getIds().size() > 10) {
            throw new CustomException("IDS", "Ids should be of max 10.");
        }
        if (searchCriteria.getFromProposalDate() != null && searchCriteria.getToProposalDate() != null && searchCriteria.getFromProposalDate().compareTo(searchCriteria.getToProposalDate()) > 0) {
            throw new CustomException("FROM_GREATER_THAN_TO_DATE", "From date is greater than to date");
        }
    }

    /**
     * validate the update estimate request for all the mandatory
     * and/or
     * invalid attributes
     *
     * @param request
     */
    public void validateEstimateOnUpdate(EstimateRequest request) {
        log.info("EstimateServiceValidator::validateEstimateOnUpdate");
        Map<String, String> errorMap = new HashMap<>();
        Estimate estimate = request.getEstimate();
        Estimate estimateForRevision = null;
        RequestInfo requestInfo = request.getRequestInfo();
        Workflow workflow = request.getWorkflow();
        List<EstimateDetail>estimateDetails=estimate.getEstimateDetails();

        validateRequestInfo(requestInfo);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow);

        String id = estimate.getId();
        if (StringUtils.isBlank(id)) {
            errorMap.put("ESTIMATE_ID", "Estimate id is mandatory");
        } else {
            estimateForRevision = validateEstimateFromDBAndFetchPreviousEstimate(request);
        }
        validateRequestOnMDMSV1AndV2(request,errorMap,false,estimateForRevision);
        validateProjectId(request);
        validateNoOfUnit(estimateDetails);

        if(Boolean.TRUE.equals(config.getRevisionEstimateMeasurementValidation()) && Boolean.TRUE.equals(estimateServiceUtil.isRevisionEstimate(request)) && estimateForRevision != null){
            validateContractAndMeasurementBook(request, estimateForRevision, errorMap);
        }

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

    }
    private Estimate validateEstimateFromDBAndFetchPreviousEstimate(EstimateRequest request){
        Estimate estimate = request.getEstimate();
        String id = estimate.getId();
        List<String> ids = new ArrayList<>();
        ids.add(id);
        EstimateSearchCriteria searchCriteria = EstimateSearchCriteria.builder().ids(ids).tenantId(estimate.getTenantId()).build();
        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);
        if (CollectionUtils.isEmpty(estimateList)) {
            throw new CustomException("INVALID_ESTIMATE_MODIFY", "The record that you are trying to update does not exists in the system");
        }
        //check projectId is same or not, if project Id is not same throw validation error
        Estimate estimateFromDB = estimateList.get(0);
        if (!estimateFromDB.getProjectId().equals(estimate.getProjectId())) {
            throw new CustomException("INVALID_PROJECT_ID", "The project id is different than that is linked with given estimate id : " + id);
        }
        if(estimateServiceUtil.isRevisionEstimate(request)){
            if(estimate.getRevisionNumber() == null){
                throw new CustomException("INVALID_REVISION_NUMBER", "Revision number is mandatory for revision estimate");
            }
            if(!estimate.getRevisionNumber().equals(estimateFromDB.getRevisionNumber())){
                throw new CustomException("INVALID_REVISION_NUMBER", "revisionNumber is not valid");
            }
            validatePreviousEstimateForUpdate(estimate, estimateFromDB);
        }
        if (ObjectUtils.isEmpty(estimate.getAuditDetails())) {
            estimate.setAuditDetails(estimateFromDB.getAuditDetails());
        }

        return estimateFromDB;
    }

    private void validatePreviousEstimateForUpdate(Estimate estimate, Estimate estimateFromDB) {
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        List<EstimateDetail> estimateDetailsFromDB = estimateFromDB.getEstimateDetails();
        HashMap<String,EstimateDetail> estimateDetailMap = new HashMap<>();
        estimateDetailsFromDB.forEach(estimateDetail ->
                estimateDetailMap.put(estimateDetail.getId(),estimateDetail)
        );
        estimateDetails.forEach(estimateDetail -> {
            if(estimateDetail.getId() != null && (!estimateDetailMap.containsKey(estimateDetail.getId()))){
                throw new CustomException("INVALID_ESTIMATE_DETAIL", "Line item id is invalid for revision estimate");
            }
            if(estimateDetail.getId() != null && estimateDetailMap.containsKey(estimateDetail.getId())){
                if(estimateDetail.getPreviousLineItemId() == null){
                    throw new CustomException("INVALID_PREVIOUS_LINE_ITEM_ID", "Previous line item id is mandatory for revision estimate");
                }
                if(!estimateDetail.getPreviousLineItemId().equals(estimateDetailMap.get(estimateDetail.getId()).getPreviousLineItemId())){
                    throw new CustomException("INVALID_PREVIOUS_LINE_ITEM_ID", "Previous line item id is invalid for revision estimate");
                }
            }
        });
    }

    private void validateRequestOnMDMSV1AndV2(EstimateRequest request,Map<String, String> errorMap,Boolean isCreate,Estimate previousEstimate){
        log.info("EstimateServiceValidator::validateRequestOnMDMSV1AndV2");
        Estimate estimate = request.getEstimate();
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        String rootTenantId = estimate.getTenantId();

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);
        Object mdmsDataForUOM = mdmsUtils.mdmsCallV2(request, rootTenantId, MASTER_UOM,MDMS_COMMON_MASTERS_MODULE_NAME);
        Object mdmsDataForOverHead = mdmsUtils.mDMSCallForOverHeadCategory(request, rootTenantId);
        validateMDMSData(estimate, mdmsData, mdmsDataForOverHead, errorMap, isCreate);
        validateMDMSDataForUOM(estimate, mdmsDataForUOM, errorMap);

        Set<String> uniqueIdentifiers = new HashSet<>();
        for (EstimateDetail estimateDetail : estimateDetails) {
            if (estimateDetail.getCategory().equalsIgnoreCase(SOR_CODE) && estimateDetail.getSorId() != null) {
                uniqueIdentifiers.add(estimateDetail.getSorId());
            }
        }

        if (!uniqueIdentifiers.isEmpty()) {
            Object mdmsDataV2ForSor = mdmsUtils.mdmsCallV2ForSor(request, rootTenantId, uniqueIdentifiers, false);
            validateMDMSDataV2ForSor(estimate, mdmsDataV2ForSor, uniqueIdentifiers, errorMap);

            Object mdmsDataV2ForRate = mdmsUtils.mdmsCallV2ForSor(request, rootTenantId, uniqueIdentifiers, true);
            validateDateAndRates(estimate, mdmsDataV2ForRate, errorMap,previousEstimate,isCreate);
        }
    }
}