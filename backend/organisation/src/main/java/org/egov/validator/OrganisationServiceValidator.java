package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.OrgSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import org.egov.util.MDMSUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrganisationServiceValidator {

    @Autowired
    private MDMSUtil mdmsUtil;

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

        String rootTenantId = organisationList.get(0).getTenantId();
        rootTenantId = rootTenantId.split("\\.")[0];
        //get the organisation related MDMS data
        Object mdmsData = mdmsUtil.mDMSCall(orgRequest, rootTenantId);

        //validate organisation details against MDMS
        //validateMDMSData(organisationList, mdmsData, errorMap);


        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
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

                    Boundary addressBoundary = address.getBoundary();
                    if (addressBoundary == null) {
                        throw new CustomException("ADDRESS.BOUNDARY", "Address's boundary is mandatory");
                    }
                    if (StringUtils.isBlank(addressBoundary.getCode())) {
                        throw new CustomException("ADDRESS.BOUNDARY.CODE", "Address's boundary code is mandatory");
                    }
                    if (StringUtils.isBlank(addressBoundary.getName())) {
                        throw new CustomException("ADDRESS.BOUNDARY.NAME", "Address's boundary name is mandatory");
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
        //validate if anyone organisation is not having org id in request
        List<String> orgIds = new ArrayList<>();
        for (Organisation organisation : organisationList) {
            if (StringUtils.isBlank(organisation.getId())) {
                throw new CustomException("ORGANISATION_ID", "Organisation id is missing");
            }
            orgIds.add(organisation.getId());
        }

        String rootTenantId = organisationList.get(0).getTenantId();
        rootTenantId = rootTenantId.split("\\.")[0];
        //get the organisation related MDMS data
        Object mdmsData = mdmsUtil.mDMSCall(orgRequest, rootTenantId);

        //TODO - check the org id exist in the system or not

        OrgSearchCriteria searchCriteria = OrgSearchCriteria.builder().id(orgIds).build();

        //validate organisation details against MDMS
        //validateMDMSData(organisationList, mdmsData, errorMap);


        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

    public void validateSearchOrganisationRequest(OrgSearchRequest orgSearchRequest) {
        Map<String, String> errorMap = new HashMap<>();
        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(orgSearchRequest.getRequestInfo(), errorMap);
        //Verify the search criteria
        validateSearchCriteria(orgSearchRequest.getSearchCriteria());

    }

    private void validateSearchCriteria(OrgSearchCriteria searchCriteria) {
        Map<String, String> errorMap = new HashMap<>();

        if (searchCriteria == null) {
            log.error("Organisation is mandatory");
            throw new CustomException("ORGANISATION", "Organisation is mandatory");
        }

        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            log.error("Tenant ID is mandatory in Organisation request body");
            errorMap.put("TENANT_ID", "Tenant ID is mandatory");
        }

        if ((searchCriteria.getFunctions() != null && searchCriteria.getFunctions().getValidFrom() != null && searchCriteria.getFunctions().getValidTo() != null) &&
                (searchCriteria.getFunctions().getValidFrom().compareTo(searchCriteria.getFunctions().getValidTo()) > 0)) {
            log.error("Valid From in search parameters should be less than Valid To");
            throw new CustomException("INVALID_DATE", "Valid From in search parameters should be less than Valid To");
        }
    }
}
