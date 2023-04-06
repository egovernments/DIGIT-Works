package org.egov.works.util;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.Contract;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.Organisation.OrgSearchCriteria;
import org.egov.works.web.models.Organisation.OrgSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class OrganisationServiceUtil {

    @Autowired
    private ContractServiceConfiguration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public Map<String, List<String>> getOrganisationInfo(ContractRequest request) {
        RequestInfo requestInfo=request.getRequestInfo();
        Contract contract=request.getContract();
        String tenantId=request.getContract().getTenantId();

        StringBuilder uri = getOrganisationURI(tenantId);

        //build request body
        OrgSearchCriteria orgSearchCriteria=OrgSearchCriteria.builder().id(Collections.singletonList(contract.getOrgId())).tenantId(tenantId).build();

        OrgSearchRequest orgSearchRequest= OrgSearchRequest.builder().requestInfo(requestInfo).searchCriteria(orgSearchCriteria).build();

        Object response = serviceRequestRepository.fetchResult(uri, orgSearchRequest);

        Map<String, List<String>> orgDetails = new HashMap<>();
        List<String> contactPersonNames = null;
        List<String> orgNames = null;
        List<String> mobileNumbers =null;

        try {
            orgNames = JsonPath.read(response, ORGANISATION_NAME_CODE);
            contactPersonNames = JsonPath.read(response, ORGANISATION_PERSON_CODE);
            mobileNumbers=JsonPath.read(response, ORGANISATION_MOBILE_NUMBER_CODE);

        } catch (Exception e) {
            throw new CustomException("PARSING_ERROR", "Failed to parse HRMS response");
        }

        if(orgNames.isEmpty() || contactPersonNames.isEmpty() || mobileNumbers.isEmpty()){
            throw new CustomException("SEARCH_ERROR", "Organisation data not found");
        }

        orgDetails.put("orgName", Collections.singletonList(orgNames.get(0)));
        orgDetails.put("contactPersonNames",contactPersonNames);
        orgDetails.put("mobileNumbers",mobileNumbers);

        return orgDetails;
    }

    private StringBuilder getOrganisationURI(String tenantId) {

        StringBuilder builder = new StringBuilder(config.getOrganisationHost());
        builder.append(config.getOrganisationContextPath()).append(config.getOrganisationEndpoint());
        return builder;
    }
}
