package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.mdms.MdmsCriteriaReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.repository.OrganisationRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.BoundaryUtil;
import org.egov.util.MDMSUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.util.OrganisationConstant.*;

@Component
@Slf4j
public class OrganisationServiceValidator {

    @Autowired
    private MDMSUtil mdmsUtil;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private BoundaryUtil boundaryUtil;

    @Autowired
    private Configuration config;

    /**
     * Validate the simple organisation registry (create) details
     *
     * @param orgRequest
     */
    public void validateCreateOrgRegistryWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationServiceValidator::validateCreateOrgRegistryWithoutWorkFlow");
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        List<Organisation> organisationList = orgRequest.getOrganisations();

        validateRequestInfo(requestInfo, errorMap);
        validateOrganisationDetails(organisationList, errorMap);

        //validate organisation details against MDMS
        validateMDMSData(organisationList, requestInfo, organisationList.get(0).getTenantId(), errorMap);

        //validate location - boundary code(s)
        Map<String, List<String>> boundariesForValidation = getBoundaryForValidation(organisationList);
        validateBoundary(boundariesForValidation, organisationList.get(0).getTenantId(), requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateBoundary(Map<String, List<String>> boundaries, String tenantId, RequestInfo requestInfo, Map<String, String> errorMap) {
        if (!boundaries.isEmpty()) {
            boundaryUtil.validateBoundaryDetails(boundaries, tenantId, requestInfo, config.getLocationHierarchyType());
        }
    }

    private Map<String, List<String>> getBoundaryForValidation(List<Organisation> organisationList) {
        Map<String, List<String>> boundariesMap = new HashMap<>();
        List<Address> orgAddressFinalList = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            orgAddressFinalList.addAll(organisation.getOrgAddress());
        }
        if (!CollectionUtils.isEmpty(orgAddressFinalList)) {
            for (Address orgAddress : orgAddressFinalList) {
                if (orgAddress != null
                        && StringUtils.isNotBlank(orgAddress.getBoundaryType())
                        && StringUtils.isNotBlank(orgAddress.getBoundaryCode())) {

                    String boundaryType = orgAddress.getBoundaryType();
                    String boundaryCode = orgAddress.getBoundaryCode();

                    // If the boundary type already exists in the map, add the boundary code to the existing list
                    if (boundariesMap.containsKey(boundaryType)) {
                        boundariesMap.get(boundaryType).add(boundaryCode);
                    }
                    // If the boundary type does not exist in the map, create a new list and add the boundary code to it
                    else {
                        List<String> boundaries = new ArrayList<>();
                        boundaries.add(boundaryCode);
                        boundariesMap.put(boundaryType, boundaries);
                    }
                }
            }
        }
        return boundariesMap;
    }

    private void validateMDMSData(List<Organisation> organisationList, RequestInfo requestInfo, String tenantId, Map<String, String> errorMap) {
        log.info("OrganisationServiceValidator::validateMDMSData");
        String rootTenantId = tenantId.split("\\.")[0];

        //get the organisation related MDMS data
        //tenant -MDMS data
        MdmsCriteriaReq mdmsCriteriaReqForTenant = mdmsUtil.getTenantMDMSRequest(requestInfo, rootTenantId, organisationList);
        Object tenantMdmsData = mdmsUtil.mDMSCall(mdmsCriteriaReqForTenant, rootTenantId);

        //Org type -MDMS data
        MdmsCriteriaReq mdmsCriteriaReqForOrgType = mdmsUtil.getOrgTypeMDMSRequest(requestInfo, rootTenantId, organisationList);
        Object orgTypeMdmsData = mdmsUtil.mDMSCall(mdmsCriteriaReqForOrgType, rootTenantId);

        //Org Fun Category -MDMS data
        MdmsCriteriaReq mdmsCriteriaReqForOrgFunCategory = mdmsUtil.getOrgFunCategoryMDMSRequest(requestInfo, rootTenantId, organisationList);
        Object orgFunCategoryMdmsData = mdmsUtil.mDMSCall(mdmsCriteriaReqForOrgFunCategory, rootTenantId);

        //Org Tax Identifier -MDMS data
        MdmsCriteriaReq mdmsCriteriaReqForOrgTaxIdentifier = mdmsUtil.getOrgTaxIdentifierMDMSRequest(requestInfo, rootTenantId, organisationList);
        Object orgTaxIdentifierMdmsData = mdmsUtil.mDMSCall(mdmsCriteriaReqForOrgTaxIdentifier, rootTenantId);


        //Org Function Class -MDMS data
        MdmsCriteriaReq mdmsCriteriaReqForOrgFunctionClass = mdmsUtil.getOrgFunctionMDMSRequest(requestInfo, rootTenantId, organisationList);
        Object orgFunctionClassMdmsData = mdmsUtil.mDMSCall(mdmsCriteriaReqForOrgFunctionClass, rootTenantId);


        Set<String> orgTypeReqSet = new HashSet<>();
        Set<String> orgFuncCategoryReqSet = new HashSet<>();
        Set<String> orgFuncClassReqSet = new HashSet<>();
        Set<String> orgIdentifierReqSet = new HashSet<>();

        for (Organisation organisation : organisationList) {
            if (!CollectionUtils.isEmpty(organisation.getFunctions())) {
                for (Function function : organisation.getFunctions()) {
                    if (StringUtils.isNotBlank(function.getType())) {
                        orgTypeReqSet.add(function.getType());
                    }
                    if (StringUtils.isNotBlank(function.getCategory())) {
                        orgFuncCategoryReqSet.add(function.getCategory());
                    }
                    if (StringUtils.isNotBlank(function.getPropertyClass())) {
                        orgFuncClassReqSet.add(function.getPropertyClass());
                    }
                }
            }
            if (!CollectionUtils.isEmpty(organisation.getIdentifiers())) {
                for (Identifier identifier : organisation.getIdentifiers()) {
                    if (StringUtils.isNotBlank(identifier.getType())) {
                        orgIdentifierReqSet.add(identifier.getType());
                    }
                }
            }
        }


        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        final String jsonPathForOrgType = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_ORG_TYPE + ".*";
        final String jsonPathForOrgFuncCategory = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_ORG_FUNC_CATEGORY + ".*";
        final String jsonPathForOrgFuncClass = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_ORG_FUNC_CLASS + ".*";
        final String jsonPathForOrgIdentifier = "$.MdmsRes." + MDMS_COMMON_MASTERS_MODULE_NAME + "." + MASTER_ORG_TAX_IDENTIFIER + ".*";

        List<Object> tenantRes = null;
        List<Object> orgTypeRes = null;
        List<Object> orgFuncCategoryRes = null;
        List<Object> orgFuncClassRes = null;
        List<Object> orgIdentifierRes = null;
        try {
            tenantRes = JsonPath.read(tenantMdmsData, jsonPathForTenants);
            orgTypeRes = JsonPath.read(orgTypeMdmsData, jsonPathForOrgType);
            orgFuncCategoryRes = JsonPath.read(orgFunCategoryMdmsData, jsonPathForOrgFuncCategory);
            orgFuncClassRes = JsonPath.read(orgFunctionClassMdmsData, jsonPathForOrgFuncClass);
            orgIdentifierRes = JsonPath.read(orgTaxIdentifierMdmsData, jsonPathForOrgIdentifier);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        //tenant
        if (CollectionUtils.isEmpty(tenantRes))
            errorMap.put("INVALID_TENANT_ID", "The tenant: " + tenantId + " is not present in MDMS");

        //org type
        if (CollectionUtils.isEmpty(orgTypeRes)) {
            errorMap.put("INVALID_ORG_TYPE", "The org type is not configured in MDMS");
        } else {
            if (!CollectionUtils.isEmpty(orgTypeReqSet)) {
                orgTypeReqSet.removeAll(orgTypeRes);
                if (!CollectionUtils.isEmpty(orgTypeReqSet)) {
                    errorMap.put("INVALID_ORG_TYPE", "The org types: " + orgTypeReqSet + " is not present in MDMS");
                }
            }
        }

        //org function category
        if (CollectionUtils.isEmpty(orgFuncCategoryRes)) {
            errorMap.put("INVALID_ORG.FUNCTION_CATEGORY", "The org function category is not configured in MDMS");
        } else {
            if (!CollectionUtils.isEmpty(orgFuncCategoryReqSet)) {
                orgFuncCategoryReqSet.removeAll(orgFuncCategoryRes);
                if (!CollectionUtils.isEmpty(orgFuncCategoryReqSet)) {
                    errorMap.put("INVALID_ORG.FUNCTION_CATEGORY", "The org function category: " + orgFuncCategoryReqSet + " is not present in MDMS");
                }
            }
        }

        //org function class
        if (CollectionUtils.isEmpty(orgFuncClassRes)) {
            errorMap.put("INVALID_ORG.FUNCTION_CLASS", "The org function class is not configured in MDMS");
        } else {
            if (!CollectionUtils.isEmpty(orgFuncClassReqSet)) {
                orgFuncClassReqSet.removeAll(orgFuncClassRes);
                if (!CollectionUtils.isEmpty(orgFuncClassReqSet)) {
                    errorMap.put("INVALID_ORG.FUNCTION_CLASS", "The org function class: " + orgFuncClassReqSet + " is not present in MDMS");
                }
            }
        }


        //org identifier type
        if (CollectionUtils.isEmpty(orgIdentifierRes)) {
            errorMap.put("INVALID_ORG.IDENTIFIER_TYPE", "The org identifier type is not configured in MDMS");
        } else {
            if (!CollectionUtils.isEmpty(orgIdentifierReqSet)) {
                orgIdentifierReqSet.removeAll(orgIdentifierRes);
                if (!CollectionUtils.isEmpty(orgIdentifierReqSet)) {
                    errorMap.put("INVALID_ORG.IDENTIFIER_TYPE", "The org identifier type: " + orgIdentifierReqSet + " is not present in MDMS");
                }
            }
        }

    }

    private void validateOrganisationDetails(List<Organisation> organisationList, Map<String, String> errorMap) {
        log.info("OrganisationServiceValidator::validateOrganisationDetails");
        if (organisationList == null || organisationList.isEmpty()) {
            throw new CustomException("ORGANISATION_DETAILS", "At least one organisation detail is required");
        }
        for (Organisation organisation : organisationList) {
            if (StringUtils.isBlank(organisation.getTenantId())) {
                throw new CustomException("TENANT_ID", "Tenant id is mandatory");
            }
            if (StringUtils.isBlank(organisation.getName())) {
                throw new CustomException("ORG_NAME", "Organisation name is mandatory");
            }
            List<Address> addressList = organisation.getOrgAddress();
            if (addressList != null && !addressList.isEmpty()) {
                for (Address address : addressList) {
                    if (StringUtils.isBlank(address.getTenantId())) {
                        throw new CustomException("ADDRESS.TENANT_ID", "Tenant id is mandatory");
                    }
                    if (StringUtils.isBlank(address.getBoundaryCode())) {
                        throw new CustomException("ADDRESS.BOUNDARY_CODE", "Address's boundary code is mandatory");
                    }
                    if (StringUtils.isBlank(address.getBoundaryType())) {
                        throw new CustomException("ADDRESS.BOUNDARY_TYPE", "Address's boundary type is mandatory");
                    }

                }
            }

        }
    }

    /* Validates Request Info and User Info */
    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        log.info("OrganisationServiceValidator::validateRequestInfo");
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory in RequestInfo");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory in UserInfo");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    public void validateUpdateOrgRegistryWithoutWorkFlow(OrgRequest orgRequest) {
        log.info("OrganisationServiceValidator::validateUpdateOrgRegistryWithoutWorkFlow");
        Map<String, String> errorMap = new HashMap<>();
        RequestInfo requestInfo = orgRequest.getRequestInfo();
        List<Organisation> organisationList = orgRequest.getOrganisations();

        validateRequestInfo(requestInfo, errorMap);
        validateOrganisationDetails(organisationList, errorMap);

        validateOrgIdsExistInSystem(requestInfo, organisationList);

        //validate organisation details against MDMS
        validateMDMSData(organisationList, requestInfo, organisationList.get(0).getTenantId(), errorMap);

        //validate location - boundary code(s)
        Map<String, List<String>> boundariesForValidation = getBoundaryForValidation(organisationList);
        validateBoundary(boundariesForValidation, organisationList.get(0).getTenantId(), requestInfo, errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateOrgIdsExistInSystem(RequestInfo requestInfo, List<Organisation> organisationList) {
        //validate if anyone organisation is not having org id in request
        List<String> orgIds = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (StringUtils.isBlank(organisation.getId())) {
                throw new CustomException("ORGANISATION_ID", "Organisation id is missing");
            }
            orgIds.add(organisation.getId());
        }


        //check the org id exist in the system or not
        OrgSearchCriteria searchCriteria = OrgSearchCriteria.builder()
                .id(orgIds)
                .tenantId(organisationList.get(0).getTenantId())
                .includeDeleted(Boolean.FALSE)
                .build();

        OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder()
                .requestInfo(requestInfo)
                .searchCriteria(searchCriteria)
                .build();

        List<Organisation> organisationListFromDB = organisationRepository.getOrganisations(orgSearchRequest);
        if (CollectionUtils.isEmpty(organisationListFromDB)) {
            throw new CustomException("INVALID_ORG_ID", "Given org id(s) : " + orgIds + " don't exist in the system.");
        } else {
            List<String> orgIdsFromDB = organisationListFromDB.stream().map(Organisation::getId).collect(Collectors.toList());
            orgIds.removeAll(orgIdsFromDB);
            if (!CollectionUtils.isEmpty(orgIds)) {
                throw new CustomException("INVALID_ORG_ID", "Given org id(s) : " + orgIds + " don't exist in the system.");
            }
        }
    }

    public void validateSearchOrganisationRequest(OrgSearchRequest orgSearchRequest) {
        Map<String, String> errorMap = new HashMap<>();
        //Verify if RequestInfo and UserInfo is present
        log.info("Organisation search request Info data validation");
        validateRequestInfo(orgSearchRequest.getRequestInfo(), errorMap);
        //Verify the search criteria
        log.info("Organisation search criteria validation");
        validateSearchCriteria(orgSearchRequest.getSearchCriteria(), errorMap);

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    private void validateSearchCriteria(OrgSearchCriteria searchCriteria, Map<String, String> errorMap) {

        if (searchCriteria == null) {
            log.error("Search criteria is mandatory");
            throw new CustomException("ORGANISATION", "Search criteria is mandatory");
        }

        if (searchCriteria.getContactMobileNumber()== null && StringUtils.isBlank(searchCriteria.getTenantId())) {
            log.error("Tenant ID is mandatory in Organisation request body if mobile number is not passed");
            errorMap.put("TENANT_ID", "Tenant ID is mandatory");
        }

        if ((searchCriteria.getFunctions() != null && searchCriteria.getFunctions().getValidFrom() != null && searchCriteria.getFunctions().getValidTo() != null) &&
                (searchCriteria.getFunctions().getValidFrom().compareTo(searchCriteria.getFunctions().getValidTo()) > 0)) {
            log.error("Valid From in search parameters should be less than Valid To");
            throw new CustomException("INVALID_DATE", "Valid From in search parameters should be less than Valid To");
        }

        if (searchCriteria.getCreatedTo() != null && searchCriteria.getCreatedFrom() == null) {
            log.error("Valid From in search parameters should be less than Valid To");
            throw new CustomException("INVALID_ORG_SEARCH_DATE", "Created From date in search parameters is required when created to date is passed");
        }

        if (searchCriteria.getCreatedFrom() != null && searchCriteria.getCreatedTo() == null) {
            long currentDate = System.currentTimeMillis();
            if (searchCriteria.getCreatedFrom() > currentDate) {
                log.error("Invalid created from date");
                throw new CustomException("INVALID_ORG_SEARCH_DATE", "invalid created from date");
            } else {
                searchCriteria.setCreatedTo(currentDate);
            }
        }

        if (searchCriteria.getCreatedFrom() != null && searchCriteria.getCreatedTo() != null
                && Long.compare(searchCriteria.getCreatedFrom(), searchCriteria.getCreatedTo()) > 0) {
            log.error("Created from date is greater than created to date");
            throw new CustomException("INVALID_ORG_SEARCH_DATE", "Created from date is greater than created to date");

        }
    }
}
