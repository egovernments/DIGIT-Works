package org.egov.works.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.repository.EstimateRepository;
import org.egov.works.util.LocationUtil;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.egov.works.util.EstimateServiceConstant.*;

@Component
@Slf4j
public class EstimateServiceValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private LocationUtil locationUtil;

    @Autowired
    private EstimateRepository estimateRepository;

    public void validateCreateEstimate(EstimateRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        Estimate estimate = request.getEstimate();
        RequestInfo requestInfo = request.getRequestInfo();
        EstimateRequestWorkflow workflow = request.getWorkflow();

        validateRequestInfo(requestInfo, errorMap);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow, errorMap);

        String rootTenantId = estimate.getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(estimate, mdmsData, errorMap);
        validateLocation(estimate, requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateLocation(Estimate estimate, RequestInfo requestInfo, Map<String, String> errorMap) {
        final String jsonPathForBoundryLocation = "$.MdmsRes." + MDMS_LOCATION_MODULE_NAME + "." + MASTER_BOUNDARY_LOCATION + ".*";
        String location = estimate.getLocation();

        if (StringUtils.isNotBlank(location)) {
            Object locResult = locationUtil.getLocationFromMDMS(location, requestInfo, errorMap);
            if (locResult != null) {
                List<Object> locRes = JsonPath.read(locResult, jsonPathForBoundryLocation);
                if (CollectionUtils.isEmpty(locRes))
                    errorMap.put("LOCATION_NOT_FOUND", "Location doesn't exist in the system");
            }
        }
    }

    private void validateWorkFlow(EstimateRequestWorkflow workflow, Map<String, String> errorMap) {
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            errorMap.put("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }
    }

    private void validateEstimate(Estimate estimate, Map<String, String> errorMap) {
        if (estimate == null) {
            throw new CustomException("ESTIMATE", "Estimate is mandatory");
        }
        if (StringUtils.isBlank(estimate.getTenantId())) {
            errorMap.put("TENANT_ID", "Tenant is is mandatory");
        }
        if (estimate.getStatus() == null || !EnumUtils.isValidEnum(Estimate.StatusEnum.class, estimate.getStatus().toString())) {
            errorMap.put("STATUS", "Status is mandatory");
        }
        if (StringUtils.isBlank(estimate.getSubject())) {
            errorMap.put("SUBJECT", "Subject is mandatory");
        }
        if (StringUtils.isBlank(estimate.getRequirementNumber())) {
            errorMap.put("REQUIREMENT_NUMBER", "Requirement number is mandatory");
        }
        if (StringUtils.isBlank(estimate.getDescription())) {
            errorMap.put("DESCRIPTION", "Description is mandatory");
        }
        if (StringUtils.isBlank(estimate.getDepartment())) {
            errorMap.put("DEPARTMENT", "Department is mandatory");
        }
        if (StringUtils.isBlank(estimate.getWorkCategory())) {
            errorMap.put("WORK_CATEGORY", "Work category is mandatory");
        }

        if (StringUtils.isBlank(estimate.getBeneficiaryType())) {
            errorMap.put("BENEFICIARY_TYPE", "Beneficiary type is mandatory");
        }
        if (StringUtils.isBlank(estimate.getNatureOfWork())) {
            errorMap.put("NATURE_OF_WORK", "Nature of work is mandatory");
        }
        if (StringUtils.isBlank(estimate.getTypeOfWork())) {
            errorMap.put("TYPE_OF_WORK", "Type of work is mandatory");
        }
        if (StringUtils.isBlank(estimate.getEntrustmentMode())) {
            errorMap.put("ENTRUSTMENT_MODE", "Entrustment mode is mandatory");
        }
        if (StringUtils.isBlank(estimate.getFund())) {
            errorMap.put("FUND", "Fund is mandatory");
        }
        if (StringUtils.isBlank(estimate.getFunction())) {
            errorMap.put("FUNCTION", "Function is mandatory");
        }
        if (StringUtils.isBlank(estimate.getBudgetHead())) {
            errorMap.put("BUDGET_HEAD", "Budget head is mandatory");
        }
        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
        if (estimateDetails == null || estimateDetails.isEmpty()) {
            errorMap.put("ESTIMATE_DETAILS", "Estimate detail is mandatory");
        } else {
            for (EstimateDetail estimateDetail : estimateDetails) {
                if (StringUtils.isBlank(estimateDetail.getName())) {
                    errorMap.put("ESTIMATE.DETAIL.NAME", "Estimate detail's name is mandatory");
                }
                if (estimateDetail.getAmount() == null) {
                    errorMap.put("ESTIMATE.DETAIL.AMOUNT", "Estimate detail's amount is mandatory");
                }
            }
        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
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

    private void validateMDMSData(Estimate estimate, Object mdmsData, Map<String, String> errorMap) {

        final String jsonPathForWorksDepartment = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_DEPARTMENT + ".*";
        final String jsonPathForWorksBeneficiaryType = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_BENEFICIART_TYPE + ".*";
        final String jsonPathForWorksEntrustmentMode = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_ENTRUSTMENTMODE + ".*";
        final String jsonPathForWorksTypeOfWork = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_TYPEOFWORK + ".*";
        final String jsonPathForWorksNatureOfWork = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_NATUREOFWORK + ".*";
        final String jsonPathForWorksSubTypeOfWork = "$.MdmsRes." + MDMS_WORKS_MODULE_NAME + "." + MASTER_TYPEOFWORK + "." + "*.code";


        final String jsonPathForFinanceFund = "$.MdmsRes." + MDMS_FINANCE_MODULE_NAME + "." + MASTER_FUND + ".*";
        final String jsonPathForFinanceFunctions = "$.MdmsRes." + MDMS_FINANCE_MODULE_NAME + "." + MASTER_FUNCTIONS + ".*";
        final String jsonPathForFinanceScheme = "$.MdmsRes." + MDMS_FINANCE_MODULE_NAME + "." + MASTER_SCHEME + ".*";
        final String jsonPathForFinanceSubScheme = "$.MdmsRes." + MDMS_FINANCE_MODULE_NAME + "." + MASTER_SCHEME + "." + "*.code";
        final String jsonPathForFinanceBudgetHead = "$.MdmsRes." + MDMS_FINANCE_MODULE_NAME + "." + MASTER_BUDGET_HEAD + ".*";

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";

        List<Object> deptRes = null;
        List<Object> beneficiaryTypeRes = null;
        List<Object> entrustmentModeRes = null;
        List<Object> typeOfWorkRes = null;
        List<Object> natureOfWorkRes = null;
        List<Object> financeFundRes = null;
        List<Object> financeFunctionRes = null;
        List<Object> financeSchemeRes = null;
        List<Object> financeSubSchemeRes = null;
        List<Object> tenantRes = null;
        List<Object> subTypeOfWorkRes = null;
        List<Object> financeBudgetHeadRes = null;

        try {
            deptRes = JsonPath.read(mdmsData, jsonPathForWorksDepartment);
            beneficiaryTypeRes = JsonPath.read(mdmsData, jsonPathForWorksBeneficiaryType);
            entrustmentModeRes = JsonPath.read(mdmsData, jsonPathForWorksEntrustmentMode);
            typeOfWorkRes = JsonPath.read(mdmsData, jsonPathForWorksTypeOfWork);
            natureOfWorkRes = JsonPath.read(mdmsData, jsonPathForWorksNatureOfWork);
            financeFundRes = JsonPath.read(mdmsData, jsonPathForFinanceFund);
            financeFunctionRes = JsonPath.read(mdmsData, jsonPathForFinanceFunctions);
            financeSchemeRes = JsonPath.read(mdmsData, jsonPathForFinanceScheme);
            financeSubSchemeRes = JsonPath.read(mdmsData, jsonPathForFinanceSubScheme);
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            subTypeOfWorkRes = JsonPath.read(mdmsData, jsonPathForWorksSubTypeOfWork);
            financeBudgetHeadRes = JsonPath.read(mdmsData, jsonPathForFinanceBudgetHead);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(deptRes))
            errorMap.put("INVALID_DEPARTMENT_CODE", "The department code: " + estimate.getDepartment() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(beneficiaryTypeRes))
            errorMap.put("INVALID_BENEFICIARY_TYPE", "The beneficiary Type: " + estimate.getBeneficiaryType() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(entrustmentModeRes))
            errorMap.put("INVALID_ENTRUSTMENT_MODE", "The entrustment mode: " + estimate.getEntrustmentMode() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(typeOfWorkRes))
            errorMap.put("INVALID_TYPE_OF_WORK", "The type Of Work: " + estimate.getTypeOfWork() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(natureOfWorkRes))
            errorMap.put("INVALID_NATURE_OF_WORK", "The nature Of Work : " + estimate.getNatureOfWork() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(subTypeOfWorkRes))
            errorMap.put("INVALID_SUB_TYPE_OF_WORK", "The sub type Of Work : " + estimate.getSubTypeOfWork() + " is not present in MDMS");


        if (CollectionUtils.isEmpty(financeFundRes))
            errorMap.put("INVALID_FINANCE_FUND", "The finance fund: " + estimate.getFund() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(financeFunctionRes))
            errorMap.put("INVALID_FINANCE_FUNCTION", "The finance function: " + estimate.getFunction() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(financeSchemeRes))
            errorMap.put("INVALID_FINANCE_SCHEME", "The finance scheme: " + estimate.getScheme() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(financeSubSchemeRes))
            errorMap.put("INVALID_FINANCE_SUB_SCHEME", "The finance sub scheme: " + estimate.getSubScheme() + " is not present in MDMS");
        if (CollectionUtils.isEmpty(financeBudgetHeadRes))
            errorMap.put("INVALID_FINANCE_BUDGET_HEAD", "The finance budget head: " + estimate.getBudgetHead() + " is not present in MDMS");

        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT", "The tenant: " + estimate.getTenantId() + " is not present in MDMS");
    }

    public void validateSearchEstimate(RequestInfoWrapper requestInfoWrapper, EstimateSearchCriteria searchCriteria) {
        if (searchCriteria == null || requestInfoWrapper == null || requestInfoWrapper.getRequestInfo() == null) {
            throw new CustomException("ESTIMATE_SEARCH_CRITERIA_REQUEST", "Estimate search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "Tenant is mandatory");
        }
    }

    public void validateUpdateEstimate(EstimateRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        Estimate estimate = request.getEstimate();
        RequestInfo requestInfo = request.getRequestInfo();
        EstimateRequestWorkflow workflow = request.getWorkflow();

        validateRequestInfo(requestInfo, errorMap);
        validateEstimate(estimate, errorMap);
        validateWorkFlow(workflow, errorMap);

        UUID id = estimate.getId();
        if (id == null) {
            errorMap.put("ESTIMATE_ID", "Estimate id is mandatory");
        } else {
            List<String> ids = new ArrayList<>();
            ids.add(id.toString());
            EstimateSearchCriteria searchCriteria = EstimateSearchCriteria.builder().ids(ids).tenantId(estimate.getTenantId()).build();
            List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);
            if (CollectionUtils.isEmpty(estimateList)) {
                throw new CustomException("INVALID_ESTIMATE_MODIFY", "The record that you are trying to update does not exists in the system");
            }
        }

        validateRolesForUpdateEstimate(requestInfo, errorMap);

        String rootTenantId = estimate.getTenantId();
        //split the tenantId
        rootTenantId = rootTenantId.split("\\.")[0];

        Object mdmsData = mdmsUtils.mDMSCall(request, rootTenantId);

        validateMDMSData(estimate, mdmsData, errorMap);
        validateLocation(estimate, requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);

    }

    private void validateRolesForUpdateEstimate(RequestInfo requestInfo, Map<String, String> errorMap) {
        User userInfo = requestInfo.getUserInfo();
        if (userInfo.getRoles() == null || userInfo.getRoles().isEmpty()) {
            errorMap.put("USER_ROLE", "User's role is missing");
        } else {
            List<org.egov.common.contract.request.Role> roles = userInfo.getRoles();
            List<String> updateRoles = Arrays.asList(UPDATE_ROLES.split(","));

            boolean rolePresent = roles.stream().anyMatch(role -> {
                return updateRoles.contains(role.getCode());
            });

            if (!rolePresent) {
                errorMap.put("UNAUTHORIZED_ROLE", "User role(s) : " + roles + " are not authorized to update estimate.");
            }
        }
    }
}
