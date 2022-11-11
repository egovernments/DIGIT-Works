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

    public List<String> fetchEstimateNumbersFromEstimateSearcher(InboxSearchCriteria criteria, HashMap<String, String> StatusIdNameMap, RequestInfo requestInfo) {
        List<String> acknowledgementNumbers = new ArrayList<>();
        HashMap moduleSearchCriteria = criteria.getModuleSearchCriteria();
        ProcessInstanceSearchCriteria processCriteria = criteria.getProcessSearchCriteria();
        Boolean isSearchResultEmpty = false;

        if (!isSearchResultEmpty) {
            Object result = null;

            Map<String, Object> searcherRequest = new HashMap<>();
            Map<String, Object> searchCriteria = new HashMap<>();

            searchCriteria.put(TENANT_ID_PARAM, criteria.getTenantId());
            searchCriteria.put(BUSINESS_SERVICE_PARAM, processCriteria.getBusinessService());

            // Accommodating module search criteria in searcher request
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

    public Integer fetchEstimateNumbersCountFromEstimateSearcher(InboxSearchCriteria criteria, HashMap<String, String> StatusIdNameMap, RequestInfo requestInfo) {
        Integer totalCount = 0;
        HashMap moduleSearchCriteria = criteria.getModuleSearchCriteria();
        ProcessInstanceSearchCriteria processCriteria = criteria.getProcessSearchCriteria();
        Boolean isSearchResultEmpty = false;

        if (!isSearchResultEmpty) {
            Object result = null;

            Map<String, Object> searcherRequest = new HashMap<>();
            Map<String, Object> searchCriteria = new HashMap<>();

            searchCriteria.put(TENANT_ID_PARAM, criteria.getTenantId());
            searchCriteria.put(BUSINESS_SERVICE_PARAM, processCriteria.getBusinessService());

            // Accommodating module search criteria in searcher request
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
            // Paginating searcher results
            searchCriteria.put(OFFSET_PARAM, criteria.getOffset());
            searchCriteria.put(NO_OF_RECORDS_PARAM, criteria.getLimit());
            moduleSearchCriteria.put(LIMIT_PARAM, criteria.getLimit());

            searcherRequest.put(REQUESTINFO_PARAM, requestInfo);
            searcherRequest.put(SEARCH_CRITERIA_PARAM, searchCriteria);

            StringBuilder uri = new StringBuilder();
            uri.append(config.getSearcherHost()).append(config.getEstimateInboxSearcherCountEndpoint());


            result = restTemplate.postForObject(uri.toString(), searcherRequest, Map.class);

            double count = JsonPath.read(result, "$.TotalCount[0].count");
            totalCount = new Integer((int) count);

        }
        return totalCount;
    }
}
