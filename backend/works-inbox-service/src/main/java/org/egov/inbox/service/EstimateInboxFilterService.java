package org.egov.inbox.service;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.inbox.config.InboxConfiguration;
import org.egov.inbox.repository.ServiceRequestRepository;
import org.egov.inbox.web.model.InboxSearchCriteria;
import org.egov.inbox.web.model.workflow.ProcessInstanceSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.inbox.util.EstimateConstant.*;


@Slf4j
@Service
public class EstimateInboxFilterService {

    @Autowired
    private InboxConfiguration config;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public List<String> fetchEstimatesFromEstimateSearcher(InboxSearchCriteria criteria, HashMap<String, String> StatusIdNameMap, RequestInfo requestInfo) {
        List<String> acknowledgementNumbers = new ArrayList<>();
        HashMap moduleSearchCriteria = criteria.getModuleSearchCriteria();
        ProcessInstanceSearchCriteria processCriteria = criteria.getProcessSearchCriteria();
        Boolean isSearchResultEmpty = false;
//        Boolean isMobileNumberPresent = false;
//        List<String> userUUIDs = new ArrayList<>();
//        if (moduleSearchCriteria.containsKey(MOBILE_NUMBER_PARAM)) {
//            isMobileNumberPresent = true;
//        }
//        if (isMobileNumberPresent) {
//            String tenantId = criteria.getTenantId();
//            String mobileNumber = String.valueOf(moduleSearchCriteria.get(MOBILE_NUMBER_PARAM));
//            userUUIDs = fetchUserUUID(mobileNumber, requestInfo, tenantId);
//            Boolean isUserPresentForGivenMobileNumber = CollectionUtils.isEmpty(userUUIDs) ? false : true;
//            isSearchResultEmpty = !isMobileNumberPresent || !isUserPresentForGivenMobileNumber;
//            if (isSearchResultEmpty) {
//                return new ArrayList<>();
//            }
//        }

        if (!isSearchResultEmpty) {
            Object result = null;

            Map<String, Object> searcherRequest = new HashMap<>();
            Map<String, Object> searchCriteria = new HashMap<>();

            searchCriteria.put(TENANT_ID_PARAM, criteria.getTenantId());
            searchCriteria.put(BUSINESS_SERVICE_PARAM, processCriteria.getBusinessService());

            // Accomodating module search criteria in searcher request
            if (moduleSearchCriteria.containsKey(DEPARTMENT)) {
                searchCriteria.put(DEPARTMENT, moduleSearchCriteria.get(DEPARTMENT));
            }
            if (moduleSearchCriteria.containsKey(TYPE_OF_WORK)) {
                searchCriteria.put(TYPE_OF_WORK, moduleSearchCriteria.get(TYPE_OF_WORK));
            }
            if (moduleSearchCriteria.containsKey(FUND)) {
                searchCriteria.put(FUND, moduleSearchCriteria.get(FUND));
            }
            if (moduleSearchCriteria.containsKey(FUNCTION)) {
                searchCriteria.put(FUNCTION, moduleSearchCriteria.get(FUNCTION));
            }
            if (moduleSearchCriteria.containsKey(BUDGET_HEAD)) {
                searchCriteria.put(BUDGET_HEAD, moduleSearchCriteria.get(BUDGET_HEAD));
            }
            if (moduleSearchCriteria.containsKey(ESTIMATE_ID)) {
                searchCriteria.put(ESTIMATE_ID, moduleSearchCriteria.get(ESTIMATE_ID));
            }
            if (moduleSearchCriteria.containsKey(FROM_PROPOSAL_DATE)) {
                searchCriteria.put(FROM_PROPOSAL_DATE, moduleSearchCriteria.get(FROM_PROPOSAL_DATE));
            }
            if (moduleSearchCriteria.containsKey(TO_PROPOSAL_DATE)) {
                searchCriteria.put(TO_PROPOSAL_DATE, moduleSearchCriteria.get(TO_PROPOSAL_DATE));
            }

            // Accomodating process search criteria in searcher request
//            if (!ObjectUtils.isEmpty(processCriteria.getAssignee())) {
//                searchCriteria.put(ASSIGNEE_PARAM, processCriteria.getAssignee());
//            }
//            if (!ObjectUtils.isEmpty(processCriteria.getStatus())) {
//                searchCriteria.put(STATUS_PARAM, processCriteria.getStatus());
//            } else {
//                if (StatusIdNameMap.values().size() > 0) {
//                    if (CollectionUtils.isEmpty(processCriteria.getStatus())) {
//                        searchCriteria.put(STATUS_PARAM, StatusIdNameMap.keySet());
//                    }
//                }
//            }

            // Paginating searcher results
            searchCriteria.put(OFFSET_PARAM, criteria.getOffset());
            searchCriteria.put(NO_OF_RECORDS_PARAM, criteria.getLimit());
            moduleSearchCriteria.put(LIMIT_PARAM, criteria.getLimit());

            searcherRequest.put(REQUESTINFO_PARAM, requestInfo);
            searcherRequest.put(SEARCH_CRITERIA_PARAM, searchCriteria);

            StringBuilder uri = new StringBuilder();
            uri.append(config.getSearcherHost()).append(config.getEstimateInboxSearcherEndpoint());


            result = restTemplate.postForObject(uri.toString(), searcherRequest, Map.class);

            acknowledgementNumbers = JsonPath.read(result, "$.estimates.*.estimate_number");

        }
        return acknowledgementNumbers;
    }

    private List<String> fetchUserUUID(String mobileNumber, RequestInfo requestInfo, String tenantId) {
//        StringBuilder uri = new StringBuilder();
//        uri.append(userHost).append(userSearchEndpoint);
//        Map<String, Object> userSearchRequest = new HashMap<>();
//        userSearchRequest.put("RequestInfo", requestInfo);
//        userSearchRequest.put("tenantId", tenantId);
//        userSearchRequest.put("userType", "CITIZEN");
//        userSearchRequest.put("mobileNumber", mobileNumber);
//        List<String> userUuids = new ArrayList<>();
//        try {
//            Object user = serviceRequestRepository.fetchResult(uri, userSearchRequest);
//            if (null != user) {
//                //log.info(user.toString());
//                userUuids = JsonPath.read(user, "$.user.*.uuid");
//            } else {
//                log.error("Service returned null while fetching user for mobile number - " + mobileNumber);
//            }
//        } catch (Exception e) {
//            log.error("Exception while fetching user for mobile number - " + mobileNumber);
//            log.error("Exception trace: ", e);
//        }
//        return userUuids;
        return null;
    }
}
