package org.egov.wms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.hash.HashService;
import org.egov.tracer.model.CustomException;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.repository.ServiceRequestRepository;
import org.egov.wms.repository.builder.WMSSearchQueryBuilder;
import org.egov.wms.validator.ValidatorDefaultImplementation;
import org.egov.wms.util.MDMSUtil;
import org.egov.wms.web.model.WMSSearch;
import org.egov.wms.web.model.WMSSearchRequest;
import org.egov.wms.web.model.WMSSearchResponse;
import org.egov.wms.web.model.V2.SearchQueryConfiguration;
import org.egov.wms.web.model.workflow.BusinessService;
import org.egov.wms.web.model.workflow.ProcessInstance;
import org.egov.wms.web.model.workflow.ProcessInstanceSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static org.egov.wms.util.WMSConstants.*;

@Service
@Slf4j
public class WMSSearchService {

    @Autowired
    private SearchConfiguration config;

    @Autowired
    private WMSSearchQueryBuilder queryBuilder;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private ValidatorDefaultImplementation validator;

    @Autowired
    private MDMSUtil mdmsUtil;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HashService hashService;


    /**
     * @param wmsSearchRequest
     * @param module
     * @return
     */
    public WMSSearchResponse getInboxResponse(WMSSearchRequest wmsSearchRequest, String module){

        validator.validateSearchCriteria(wmsSearchRequest,module);
        SearchQueryConfiguration searchQueryConfiguration = mdmsUtil.getConfigFromMDMS(wmsSearchRequest,module);
        hashParamsWhereverRequiredBasedOnConfiguration(wmsSearchRequest, searchQueryConfiguration);
        List<WMSSearch> items = getInboxItems(wmsSearchRequest, searchQueryConfiguration.getIndex(),module);
        enrichProcessInstanceInInboxItems(items);
        Integer totalCount = /*CollectionUtils.isEmpty(wmsSearchRequest.getInbox().getProcessSearchCriteria().getStatus()) ? 0 :*/ getTotalApplicationCount(wmsSearchRequest, searchQueryConfiguration.getIndex(),module);

        // populate below if processSearchCriteria is passed in the request
        List<HashMap<String, Object>> statusCountMap = null;
        Integer nearingSlaCount = null;
        if (wmsSearchRequest.getInbox().getProcessSearchCriteria() != null) {
            statusCountMap = CollectionUtils.isEmpty(wmsSearchRequest.getInbox().getProcessSearchCriteria().getStatus()) ? new ArrayList<>() : getStatusCountMap(wmsSearchRequest, searchQueryConfiguration.getIndex());
            nearingSlaCount = CollectionUtils.isEmpty(wmsSearchRequest.getInbox().getProcessSearchCriteria().getStatus()) ? 0 : getApplicationsNearingSlaCount(wmsSearchRequest, searchQueryConfiguration.getIndex(),module);
        }


        WMSSearchResponse wmsSearchResponse = WMSSearchResponse.builder().items(items).totalCount(totalCount).statusMap(statusCountMap).nearingSlaCount(nearingSlaCount).build();

        return wmsSearchResponse;
    }

    private void hashParamsWhereverRequiredBasedOnConfiguration(WMSSearchRequest wmsSearchRequest, SearchQueryConfiguration searchQueryConfiguration) {
        Map<String, Object> moduleSearchCriteria = wmsSearchRequest.getInbox().getModuleSearchCriteria();

        searchQueryConfiguration.getAllowedSearchCriteria().forEach(searchParam -> {
            if(!ObjectUtils.isEmpty(searchParam.getIsHashingRequired()) && searchParam.getIsHashingRequired()){
                if(moduleSearchCriteria.containsKey(searchParam.getName())){
                    if(moduleSearchCriteria.get(searchParam.getName()) instanceof List){
                        List<Object> hashedParams = new ArrayList<>();
                        ((List<?>) moduleSearchCriteria.get(searchParam.getName())).forEach(object -> {
                            hashedParams.add(hashService.getHashValue(object));
                        });
                        moduleSearchCriteria.put(searchParam.getName(), hashedParams);
                    }else{
                        Object hashedValue = hashService.getHashValue(moduleSearchCriteria.get(searchParam.getName()));
                        moduleSearchCriteria.put(searchParam.getName(), hashedValue);
                    }
                }
            }
        });
    }

    private void enrichProcessInstanceInInboxItems(List<WMSSearch> items) {
        /*
          As part of the new inbox, having currentProcessInstance as part of the index is mandated. This has been
          done to avoid having redundant network calls which could hog the performance.
        */
        items.forEach(item -> {
            if(item.getBusinessObject().containsKey(CURRENT_PROCESS_INSTANCE_CONSTANT)) {
                // Set process instance object in the native process instance field declared in the model inbox class.
                ProcessInstance processInstance = mapper.convertValue(item.getBusinessObject().get(CURRENT_PROCESS_INSTANCE_CONSTANT), ProcessInstance.class);
                item.setProcessInstance(processInstance);

                // Remove current process instance from business object in order to avoid having redundant data in response.
                item.getBusinessObject().remove(CURRENT_PROCESS_INSTANCE_CONSTANT);
            }
        });
    }

    private List<WMSSearch> getInboxItems(WMSSearchRequest wmsSearchRequest, String indexName, String module){

        List<BusinessService> businessServices = null;
        if (wmsSearchRequest.getInbox().getProcessSearchCriteria() != null) {
            businessServices = workflowService.getBusinessServices(wmsSearchRequest);
            enrichActionableStatusesFromRole(wmsSearchRequest, businessServices);
        }
        if(wmsSearchRequest.getInbox().getProcessSearchCriteria() != null && CollectionUtils.isEmpty(wmsSearchRequest.getInbox().getProcessSearchCriteria().getStatus())){
            return new ArrayList<>();
        }
        Map<String, Object> finalQueryBody = queryBuilder.getESQuery(wmsSearchRequest, Boolean.TRUE, module);
        try {
            String q = mapper.writeValueAsString(finalQueryBody);
            log.info("Query: "+q);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        StringBuilder uri = getURI(indexName, SEARCH_PATH);
        Object result = serviceRequestRepository.fetchResult(uri, finalQueryBody);
        List<WMSSearch> wmsSearchItemsList = parseInboxItemsFromSearchResponse(result, businessServices);
        log.info(result.toString());
        return wmsSearchItemsList;
    }

    private void enrichActionableStatusesFromRole(WMSSearchRequest wmsSearchRequest, List<BusinessService> businessServices) {
        ProcessInstanceSearchCriteria processCriteria = wmsSearchRequest.getInbox().getProcessSearchCriteria();
        String tenantId = wmsSearchRequest.getInbox().getTenantId();
        processCriteria.setTenantId(tenantId);

        HashMap<String, String> StatusIdNameMap = workflowService.getActionableStatusesForRole(wmsSearchRequest.getRequestInfo(), businessServices,
                wmsSearchRequest.getInbox().getProcessSearchCriteria());
        log.info(StatusIdNameMap.toString());
        List<String> actionableStatusUuid = new ArrayList<>();
        if (StatusIdNameMap.values().size() > 0) {
            if (!CollectionUtils.isEmpty(processCriteria.getStatus())) {
                processCriteria.getStatus().forEach(statusUuid -> {
                    if(StatusIdNameMap.keySet().contains(statusUuid)){
                        actionableStatusUuid.add(statusUuid);
                    }
                });
                wmsSearchRequest.getInbox().getProcessSearchCriteria().setStatus(actionableStatusUuid);
            } else {
                wmsSearchRequest.getInbox().getProcessSearchCriteria().setStatus(new ArrayList<>(StatusIdNameMap.keySet()));
            }
        }else{
            wmsSearchRequest.getInbox().getProcessSearchCriteria().setStatus(new ArrayList<>());
        }
    }

    public Integer getTotalApplicationCount(WMSSearchRequest wmsSearchRequest, String indexName, String module){

        Map<String, Object> finalQueryBody = queryBuilder.getESQuery(wmsSearchRequest, Boolean.FALSE, module);
        StringBuilder uri = getURI(indexName, COUNT_PATH);
        Map<String, Object> response = (Map<String, Object>) serviceRequestRepository.fetchResult(uri, finalQueryBody);
        Integer totalCount = 0;
        if(response.containsKey(COUNT_CONSTANT)){
            totalCount = (Integer) response.get(COUNT_CONSTANT);
        }else{
            throw new CustomException("INBOX_COUNT_ERR", "Error occurred while executing ES count query");
        }
        return totalCount;
    }

    public List<HashMap<String, Object>> getStatusCountMap(WMSSearchRequest wmsSearchRequest, String indexName){
        Map<String, Object> finalQueryBody = queryBuilder.getStatusCountQuery(wmsSearchRequest);
        StringBuilder uri = getURI(indexName, SEARCH_PATH);
        Map<String, Object> response = (Map<String, Object>) serviceRequestRepository.fetchResult(uri, finalQueryBody);
        HashMap<String, Object> statusCountMap = parseStatusCountMapFromAggregationResponse(response);
        List<HashMap<String, Object>> transformedStatusMap = transformStatusMap(wmsSearchRequest, statusCountMap);
        return transformedStatusMap;
    }

    private Long getApplicationServiceSla(Map<String, Long> businessServiceSlaMap, Object data) {

        Long currentDate = System.currentTimeMillis(); //current time
        Map<String, Object> auditDetails = (Map<String, Object>) ((Map<String, Object>) data).get(AUDIT_DETAILS_KEY);
        if (!ObjectUtils.isEmpty(auditDetails.get(CREATED_TIME_KEY))) {
            Long createdTime = ((Number) auditDetails.get(CREATED_TIME_KEY)).longValue();
            String businessService = JsonPath.read(data, BUSINESS_SERVICE_PATH);
            Long businessServiceSLA = businessServiceSlaMap.get(businessService);

            return Long.valueOf(Math.round((businessServiceSLA - (currentDate - createdTime)) / ((double) (24 * 60 * 60 * 1000))));
        }
        return null;
    }

    private List<HashMap<String,Object>> transformStatusMap(WMSSearchRequest request, HashMap<String, Object> statusCountMap) {

        if(CollectionUtils.isEmpty(statusCountMap))
            return null;

        List<BusinessService> businessServices = workflowService.getBusinessServices(request);

        Map<String,String> statusIdToBusinessServiceMap = workflowService.getStatusIdToBusinessServiceMap(businessServices);
        Map<String, String> statusIdToApplicationStatusMap = workflowService.getApplicationStatusIdToStatusMap(businessServices);

        List<HashMap<String,Object>> statusCountMapTransformed = new ArrayList<>();

        for(Map.Entry<String, Object> entry : statusCountMap.entrySet()){
            String statusId = entry.getKey();
            Integer count = (Integer) entry.getValue();
            HashMap<String, Object> map = new HashMap<>();
            map.put(COUNT_CONSTANT, count);
            map.put(APPLICATION_STATUS_KEY,statusIdToApplicationStatusMap.get(statusId));
            map.put(BUSINESSSERVICE_KEY,statusIdToBusinessServiceMap.get(statusId));
            map.put(STATUSID_KEY, statusId);
            statusCountMapTransformed.add(map);
        }
        return statusCountMapTransformed;
    }

    private HashMap<String, Object> parseStatusCountMapFromAggregationResponse(Map<String, Object> response) {
        List<HashMap<String, Object>> statusCountResponse = new ArrayList<>();
        if(!CollectionUtils.isEmpty((Map<String, Object>) response.get(AGGREGATIONS_KEY))){
            List<Map<String, Object>> statusCountBuckets = JsonPath.read(response, STATUS_COUNT_AGGREGATIONS_BUCKETS_PATH);
            HashMap<String, Object> statusCountMap = new HashMap<>();
            statusCountBuckets.forEach(bucket -> {
                statusCountMap.put((String)bucket.get(KEY), bucket.get(DOC_COUNT_KEY));
            });
            statusCountResponse.add(statusCountMap);
        }
        if(CollectionUtils.isEmpty(statusCountResponse))
            return null;

        return statusCountResponse.get(0);
    }

    private List<WMSSearch> parseInboxItemsFromSearchResponse(Object result, List<BusinessService> businessServices) {
        Map<String, Object> hits = (Map<String, Object>)((Map<String, Object>) result).get(HITS);
        List<Map<String, Object>> nestedHits = (List<Map<String, Object>>) hits.get(HITS);
        if(CollectionUtils.isEmpty(nestedHits)){
            return new ArrayList<>();
        }

        Map<String, Long> businessServiceSlaMap = new HashMap<>();
        if (businessServices != null && !CollectionUtils.isEmpty(businessServices)) {
            businessServices.forEach(businessService -> {
                businessServiceSlaMap.put(businessService.getBusinessService(),businessService.getBusinessServiceSla());
            });
        }


        List<WMSSearch> wmsSearchItemList = new ArrayList<>();
        nestedHits.forEach(hit ->{
            WMSSearch wmsSearch = new WMSSearch();
            Map<String, Object> businessObject = (Map<String, Object>) hit.get(SOURCE_KEY);
            wmsSearch.setBusinessObject((Map<String, Object>)businessObject.get(DATA_KEY));
            if (!CollectionUtils.isEmpty(businessServiceSlaMap)) {
                Long serviceSla = getApplicationServiceSla(businessServiceSlaMap, wmsSearch.getBusinessObject());
                wmsSearch.getBusinessObject().put(SERVICESLA_KEY, serviceSla);
            }

            wmsSearchItemList.add(wmsSearch);
        });
        return wmsSearchItemList;
    }

    public Integer getApplicationsNearingSlaCount(WMSSearchRequest wmsSearchRequest, String indexName, String module) {
        List<BusinessService> businessServicesObjs = workflowService.getBusinessServices(wmsSearchRequest);
        Map<String, Long> businessServiceSlaMap = new HashMap<>();
        Map<String, HashSet<String>> businessServiceVsStateUuids = new HashMap<>();
        businessServicesObjs.forEach(businessService -> {
            List<String> listOfUuids = new ArrayList<>();
            businessService.getStates().forEach(state -> {
                listOfUuids.add(state.getUuid());
            });
            businessServiceVsStateUuids.put(businessService.getBusinessService(), new HashSet<>(listOfUuids));
            businessServiceSlaMap.put(businessService.getBusinessService(),businessService.getBusinessServiceSla());
        });

        List<String> uuidsInSearchCriteria = wmsSearchRequest.getInbox().getProcessSearchCriteria().getStatus();

        Map<String, List<String>> businessServiceVsUuidsBasedOnSearchCriteria = new HashMap<>();

        // If status uuids are being passed in process search criteria, segregating them based on their business service
        if(!CollectionUtils.isEmpty(uuidsInSearchCriteria)) {
            uuidsInSearchCriteria.forEach(uuid -> {
                businessServiceVsStateUuids.keySet().forEach(businessService -> {
                    HashSet<String> setOfUuids = businessServiceVsStateUuids.get(businessService);
                    if (setOfUuids.contains(uuid)) {
                        if (businessServiceVsUuidsBasedOnSearchCriteria.containsKey(businessService)) {
                            businessServiceVsUuidsBasedOnSearchCriteria.get(businessService).add(uuid);
                        } else {
                            businessServiceVsUuidsBasedOnSearchCriteria.put(businessService, new ArrayList<>(Collections.singletonList(uuid)));
                        }
                    }
                });

            });
        }else{
            businessServiceVsStateUuids.keySet().forEach(businessService -> {
                HashSet<String> setOfUuids = businessServiceVsStateUuids.get(businessService);
                businessServiceVsUuidsBasedOnSearchCriteria.put(businessService, new ArrayList<>(setOfUuids));
            });
        }



        List<String> businessServices = new ArrayList<>(businessServiceVsUuidsBasedOnSearchCriteria.keySet());
        Integer totalCount = 0;
        // Fetch slot percentage only once here !!!!!!!!!!


        for(int i = 0; i < businessServices.size(); i++){
            String businessService = businessServices.get(i);
            Long businessServiceSla = businessServiceSlaMap.get(businessService);
            wmsSearchRequest.getInbox().getProcessSearchCriteria().setStatus(businessServiceVsUuidsBasedOnSearchCriteria.get(businessService));
            Map<String, Object> finalQueryBody = queryBuilder.getNearingSlaCountQuery(wmsSearchRequest, businessServiceSla, module);
            StringBuilder uri = getURI(indexName, COUNT_PATH);
            Map<String, Object> response = (Map<String, Object>) serviceRequestRepository.fetchResult(uri, finalQueryBody);
            Integer currentCount = 0;
            if(response.containsKey(COUNT_CONSTANT)){
                currentCount = (Integer) response.get(COUNT_CONSTANT);
            }else{
                throw new CustomException("INBOX_COUNT_ERR", "Error occurred while executing ES count query");
            }
            totalCount += currentCount;
        }

        return totalCount;

    }


    private StringBuilder getURI(String indexName, String endpoint){
        StringBuilder uri = new StringBuilder(config.getIndexServiceHost());
        uri.append(indexName);
        uri.append(endpoint);
        return uri;
    }

}
