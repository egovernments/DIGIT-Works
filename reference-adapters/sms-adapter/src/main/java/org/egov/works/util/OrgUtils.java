package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.NotificationServiceConfiguration;
import org.egov.works.models.*;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.Constants.*;

@Component
@Slf4j
public class OrgUtils {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private NotificationServiceConfiguration configs;

    public Object fetchOrg(RequestInfo requestInfo, String tenantId, List<String> ids){
        OrgSearchRequest orgSearchRequest = getOrgSearchRequest(requestInfo,tenantId,ids);
        StringBuilder url = getOrgRequestURL();
        return restRepo.fetchResult(url, orgSearchRequest);
    }

    public Map<String, List<String>> getOrganisationInfo(ContractRequest request) {
        RequestInfo requestInfo=request.getRequestInfo();
        Contract contract=request.getContract();
        String tenantId=request.getContract().getTenantId();

        StringBuilder uri = getOrgRequestURL();

        //build request body
        OrgSearchCriteria orgSearchCriteria= OrgSearchCriteria.builder()
                                                .id(Collections.singletonList(contract.getOrgId()))
                                                .tenantId(tenantId)
                                                .build();

        OrgSearchRequest orgSearchRequest= OrgSearchRequest.builder()
                                            .requestInfo(requestInfo)
                                            .searchCriteria(orgSearchCriteria)
                                            .build();

        Object response = restRepo.fetchResult(uri, orgSearchRequest);

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

    private OrgSearchRequest getOrgSearchRequest(RequestInfo requestInfo, String tenantId, List<String> ids) {
        OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder()
                                                .id(ids)
                                                .tenantId(tenantId)
                                                .applicationStatus("ACTIVE")
                                                .build();

        return OrgSearchRequest.builder()
                .searchCriteria(orgSearchCriteria)
                .requestInfo(requestInfo)
                .build();
    }

    private StringBuilder getOrgRequestURL() {
        return new StringBuilder(configs.getOrganisationServiceHost()).append(configs.getOrganisationServiceEndpoint());

    }
}
