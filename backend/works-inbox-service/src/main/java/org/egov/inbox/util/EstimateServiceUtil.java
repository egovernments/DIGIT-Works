package org.egov.inbox.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.inbox.config.InboxConfiguration;
import org.egov.inbox.repository.ServiceRequestRepository;
import org.egov.inbox.web.model.InboxSearchCriteria;
import org.egov.inbox.web.model.RequestInfoWrapper;
import org.egov.inbox.web.model.workflow.ProcessInstanceSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.inbox.util.BSConstants.MOBILE_NUMBER_PARAM;
import static org.egov.inbox.util.BSConstants.SORT_ORDER_PARAM;

@Component
@Slf4j
public class EstimateServiceUtil {

    @Autowired
    private InboxConfiguration inboxConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public List<String> fetchEstimateNumbersFromEstimateService(InboxSearchCriteria criteria, HashMap<String, String> StatusIdNameMap, RequestInfo requestInfo) {
        List<String> estimateNumbers = new ArrayList<>();
        HashMap moduleSearchCriteria = criteria.getModuleSearchCriteria();
        ProcessInstanceSearchCriteria processCriteria = criteria.getProcessSearchCriteria();
        Boolean isSearchResultEmpty = false;
        Boolean isMobileNumberPresent = false;
        List<String> userUUIDs = new ArrayList<>();
        if (moduleSearchCriteria.containsKey(MOBILE_NUMBER_PARAM)) {
            isMobileNumberPresent = true;
        }
        if (isMobileNumberPresent) {
            String tenantId = criteria.getTenantId();
            String mobileNumber = String.valueOf(moduleSearchCriteria.get(MOBILE_NUMBER_PARAM));
            userUUIDs = fetchUserUUID(mobileNumber, requestInfo, tenantId);
            Boolean isUserPresentForGivenMobileNumber = CollectionUtils.isEmpty(userUUIDs) ? false : true;
            isSearchResultEmpty = !isMobileNumberPresent || !isUserPresentForGivenMobileNumber;
            if (isSearchResultEmpty) {
                return new ArrayList<>();
            }
        }

        if (!isSearchResultEmpty) {
            Object result = null;

            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(inboxConfiguration.getEstimateServiceHost())
                    .append(inboxConfiguration.getEstimateServiceSearchPath());

            uriBuilder.append("?tenantId=").append(criteria.getTenantId());
            if (moduleSearchCriteria.containsKey(EstimateConstant.ESTIMATE_IDS)) {
                uriBuilder.append("&ids=").append(moduleSearchCriteria.get(EstimateConstant.ESTIMATE_IDS));
            }
            if (moduleSearchCriteria.containsKey(EstimateConstant.ESTIMATE_IDS)) {
                uriBuilder.append("&ids=").append(moduleSearchCriteria.get(EstimateConstant.ESTIMATE_IDS));
            }
            if (!ObjectUtils.isEmpty(processCriteria.getStatus())) {
                uriBuilder.append("&estimateStatus=").append(processCriteria.getStatus());
            } else {
                if (StatusIdNameMap.values().size() > 0) {
                    if (CollectionUtils.isEmpty(processCriteria.getStatus())) {
                        uriBuilder.append("&estimateStatus=").append(StatusIdNameMap.keySet());
                    }
                }
            }
            uriBuilder.append("&offset=").append(criteria.getOffset());
            uriBuilder.append("&limit=").append(criteria.getLimit());
            if (moduleSearchCriteria.containsKey(SORT_ORDER_PARAM)) {
                uriBuilder.append("&sortOrder=").append(moduleSearchCriteria.get(SORT_ORDER_PARAM));
            }

            RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

            result = restTemplate.postForObject(uriBuilder.toString(), requestInfoWrapper, Map.class);

            estimateNumbers = JsonPath.read(result, "$.estimates.*.estimateNumber");

        }
        return estimateNumbers;
    }


    private List<String> fetchUserUUID(String mobileNumber, RequestInfo requestInfo, String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(inboxConfiguration.getUserHost()).append(inboxConfiguration.getUserSearchEndpoint());
        Map<String, Object> userSearchRequest = new HashMap<>();
        userSearchRequest.put("RequestInfo", requestInfo);
        userSearchRequest.put("tenantId", tenantId);
        userSearchRequest.put("userType", "CITIZEN");
        userSearchRequest.put("mobileNumber", mobileNumber);
        List<String> userUuids = new ArrayList<>();
        try {
            Object user = serviceRequestRepository.fetchResult(uri, userSearchRequest);
            if (null != user) {
                //log.info(user.toString());
                userUuids = JsonPath.read(user, "$.user.*.uuid");
            } else {
                log.error("Service returned null while fetching user for mobile number - " + mobileNumber);
            }
        } catch (Exception e) {
            log.error("Exception while fetching user for mobile number - " + mobileNumber);
            log.error("Exception trace: ", e);
        }
        return userUuids;
    }
}
