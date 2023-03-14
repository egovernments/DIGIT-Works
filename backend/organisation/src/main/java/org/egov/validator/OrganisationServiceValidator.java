package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.OrgSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class OrganisationServiceValidator {

    public void validateSearchOrganisationRequest(RequestInfo requestInfo, OrgSearchCriteria searchCriteria) {
        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(requestInfo);
        //Verify the search criteria
        validateSearchCriteria(searchCriteria);

    }

    /* Validates Request Info and User Info */
    private void validateRequestInfo(RequestInfo requestInfo) {
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

        if (!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }

}
