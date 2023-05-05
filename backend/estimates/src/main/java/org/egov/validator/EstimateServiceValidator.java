package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.repository.EstimateRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MDMSUtils;
import org.egov.util.ProjectUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class EstimateServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ProjectUtil projectUtil;

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

        validateRequestInfo(requestInfo, errorMap);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow, errorMap);

        String rootTenantId = estimate.getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);
        Object mdmsDataForOverHead = mdmsUtils.mDMSCallForOverHeadCategory(request, rootTenantId);

        validateMDMSData(estimate, mdmsData, mdmsDataForOverHead, errorMap, true);
        validateProjectId(request, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateProjectId(EstimateRequest estimateRequest, Map<String, String> errorMap) {
        log.info("EstimateServiceValidator::validateProjectId");
        final String projectJsonPath = "$.Project.*";
        List<Object> projects = null;

        Object projectRes = projectUtil.getProjectDetails(estimateRequest);

        if (ObjectUtils.isNotEmpty(projectRes)) {
            try {
                projects = JsonPath.read(projectRes, projectJsonPath);
            } catch (Exception e) {
                throw new CustomException("JSONPATH_ERROR", "Failed to parse project search response");
            }
        }

        if (projects == null || projects.isEmpty())
            throw new CustomException("PROJECT_ID", "The project id : " + estimateRequest.getEstimate().getProjectId() + " is invalid");
    }

    private void validateWorkFlow(Workflow workflow, Map<String, String> errorMap) {
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
//        if (StringUtils.isBlank(estimate.getReferenceNumber())) {
//            errorMap.put("REFERENCE_NUMBER", "Reference number is mandatory");
//        }
//        if (StringUtils.isBlank(estimate.getDescription())) {
//            errorMap.put("DESCRIPTION", "Description is mandatory");
//        }
        if (StringUtils.isBlank(estimate.getExecutingDepartment())) {
            errorMap.put("EXECUTING_DEPARTMENT", "Executing department is mandatory");
        }
        if (StringUtils.isBlank(estimate.getProjectId())) {
            errorMap.put("PROJECT_ID", "ProjectId is mandatory");
        }
        if (estimate.getAddress() == null) {
            throw new CustomException("ADDRESS", "Address is mandatory");
        }

        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        if (estimateDetails == null || estimateDetails.isEmpty()) {
            errorMap.put("ESTIMATE_DETAILS", "Estimate detail is mandatory");
        } else {
            for (EstimateDetail estimateDetail : estimateDetails) {
                if (StringUtils.isBlank(estimateDetail.getSorId()) && StringUtils.isBlank(estimateDetail.getName())) {
                    errorMap.put("ESTIMATE.DETAIL.NAME.OR.SOR.ID", "Estimate detail's name or sorId is mandatory");
                }
                if (estimateDetail.getAmountDetail() == null || estimateDetail.getAmountDetail().isEmpty()) {
                    errorMap.put("ESTIMATE.DETAIL.AMOUNT.DETAILS", "Amount details are mandatory");
                } else {
                    for (AmountDetail amountDetail : estimateDetail.getAmountDetail()) {
                        if (amountDetail.getAmount() == null) {
                            errorMap.put("ESTIMATE.DETAIL.AMOUNT.DETAILS.AMOUNT", "Estimate amount detail's amount is mandatory");
                        }
                    }
                }

            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomException(errorMap);
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
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

    private void validateMDMSData(Estimate estimate, Object mdmsData, Object mdmsDataForOverHead, Map<String, String> errorMap, boolean isCreate) {
        log.info("EstimateServiceValidator::validateMDMSData");
        List<String> reqSorIds = new ArrayList<>();
        List<String> reqEstimateDetailCategories = new ArrayList<>();
        // List<String> reqEstimateDetailNames = new ArrayList<>();
        Map<String, List<String>> reqEstimateDetailNameMap = new HashMap<>();
        Map<String, List<String>> overheadAmountTypeMap = new HashMap<>();
        if (estimate.getEstimateDetails() != null && !estimate.getEstimateDetails().isEmpty()) {
            reqSorIds = estimate.getEstimateDetails().stream()
                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getSorId()))
                    .map(EstimateDetail::getSorId)
                    .collect(Collectors.toList());
            reqEstimateDetailCategories = estimate.getEstimateDetails().stream()
                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getCategory()))
                    .map(EstimateDetail::getCategory)
                    .collect(Collectors.toList());

//            reqEstimateDetailNames = estimate.getEstimateDetails().stream()
//                    .filter(estimateDetail -> StringUtils.isNotBlank(estimateDetail.getName()))
//                    .map(EstimateDetail::getName)
//                    .collect(Collectors.toList());

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
        final String jsonPathForWorksDepartment = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*";
        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String jsonPathForSorIds = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_SOR_ID + ".*";
        final String jsonPathForCategories = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_CATEGORY + ".*";
        final String jsonPathForOverHead = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_OVERHEAD + ".*";

        List<Object> deptRes = null;
        List<Object> tenantRes = null;
        List<Object> sorIdRes = null;
        List<Object> categoryRes = null;
        List<Object> overHeadRes = null;
        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForWorksDepartment);
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            // sorIdRes = JsonPath.read(mdmsData, jsonPathForSorIds);
            categoryRes = JsonPath.read(mdmsData, jsonPathForCategories);
            overHeadRes = JsonPath.read(mdmsDataForOverHead, jsonPathForOverHead);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(deptRes))
            errorMap.put("INVALID_EXECUTING_DEPARTMENT_CODE", "The executing department code: " + estimate.getExecutingDepartment() + " is not present in MDMS");

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT_ID", "The tenant: " + estimate.getTenantId() + " is not present in MDMS");

        //TODO - Configure sorids in MDMS
//        if (!CollectionUtils.isEmpty(sorIdRes) && !CollectionUtils.isEmpty(reqSorIds)) {
//            reqSorIds.removeAll(sorIdRes);
//            if (!CollectionUtils.isEmpty(reqSorIds)) {
//                errorMap.put("SOR_IDS", "The sorIds: " + reqSorIds + " is not present in MDMS");
//            }
//        }

        //estimate detail - category
        if (!CollectionUtils.isEmpty(categoryRes) && !CollectionUtils.isEmpty(reqEstimateDetailCategories)) {
            reqEstimateDetailCategories.removeAll(categoryRes);
            if (!CollectionUtils.isEmpty(reqEstimateDetailCategories)) {
                errorMap.put("ESTIMATE_DETAIL.CATEGORY", "The categories : " + reqEstimateDetailCategories + " is not present in MDMS");
            }
        }


        //estimate detail - name
        if (!CollectionUtils.isEmpty(overHeadRes) && !CollectionUtils.isEmpty(reqEstimateDetailNameMap)) {

            for (String category : reqEstimateDetailNameMap.keySet()) {

                if (StringUtils.isNotBlank(category) && category.equalsIgnoreCase(OVERHEAD_CODE)) {
                    List<String> reqEstimateDetailNames = reqEstimateDetailNameMap.get(category);

                    Map<String, Integer> reqNameMap = new HashMap<>();
                    for (String reqName : reqEstimateDetailNames) {
                        if (reqNameMap.containsKey(reqName)) {
                            reqNameMap.put(reqName, reqNameMap.get(reqName) + 1);
                        } else {
                            reqNameMap.put(reqName, 1);
                        }
                    }

                    List<String> invalidNames = new ArrayList<>();
                    for (String reqName : reqNameMap.keySet()) {
                        if (overHeadRes.contains(reqName)) {
                            if (reqNameMap.get(reqName) > 1) {
                                errorMap.put("ESTIMATE_DETAIL.DUPLICATE.NAME", "The name : " + reqName + " is added more than one time");
                                break;
                            }
                        } else {
                            invalidNames.add(reqName);
                        }
                    }
                    if (!CollectionUtils.isEmpty(invalidNames)) {
                        errorMap.put("ESTIMATE_DETAIL.NAME", "The names : " + invalidNames + " is not present in MDMS");
                    }
                }
            }

        }

        //Overhead -amount type validation
        if (!CollectionUtils.isEmpty(overHeadRes) && !CollectionUtils.isEmpty(overheadAmountTypeMap)) {
            for (String overheadCategoryKey : overheadAmountTypeMap.keySet()) {
                if (StringUtils.isNotBlank(overheadCategoryKey) && overheadCategoryKey.equalsIgnoreCase(OVERHEAD_CODE)) {
                    List<String> amountTypes = overheadAmountTypeMap.get(overheadCategoryKey);
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
                    for (String reqType : reqTypeMap.keySet()) {
                        if (overHeadRes.contains(reqType)) {
                            if (reqTypeMap.get(reqType) > 1) {
                                errorMap.put("ESTIMATE_DETAIL.AMOUNT_DETAIL.DUPLICATE.TYPE", "The amount type : " + reqType + " is added more than one time");
                            }
                        } else {
                            invalidList.add(reqType);
                        }
                    }

                    if (!CollectionUtils.isEmpty(invalidList)) {
                        errorMap.put("ESTIMATE_DETAIL.AMOUNT_DETAIL.TYPE", "The amount types : " + invalidList + " is not present in MDMS");
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
        RequestInfo requestInfo = request.getRequestInfo();
        Workflow workflow = request.getWorkflow();

        validateRequestInfo(requestInfo, errorMap);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow, errorMap);

        String id = estimate.getId();
        if (StringUtils.isBlank(id)) {
            errorMap.put("ESTIMATE_ID", "Estimate id is mandatory");
        } else {
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
        }
        String rootTenantId = estimate.getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        Object mdmsDataForOverHead = mdmsUtils.mDMSCallForOverHeadCategory(request, rootTenantId);
        validateMDMSData(estimate, mdmsData, mdmsDataForOverHead, errorMap, false);
        validateProjectId(request, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

    }
}
