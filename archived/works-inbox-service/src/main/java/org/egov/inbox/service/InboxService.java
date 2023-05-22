package org.egov.inbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.inbox.config.InboxConfiguration;
import org.egov.inbox.repository.ElasticSearchRepository;
import org.egov.inbox.repository.ServiceRequestRepository;
import org.egov.inbox.util.ErrorConstants;
import org.egov.inbox.web.model.Inbox;
import org.egov.inbox.web.model.InboxResponse;
import org.egov.inbox.web.model.InboxSearchCriteria;
import org.egov.inbox.web.model.RequestInfoWrapper;
import org.egov.inbox.web.model.workflow.BusinessService;
import org.egov.inbox.web.model.workflow.ProcessInstance;
import org.egov.inbox.web.model.workflow.ProcessInstanceResponse;
import org.egov.inbox.web.model.workflow.ProcessInstanceSearchCriteria;
import org.egov.tracer.model.CustomException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.egov.inbox.util.BSConstants.ACKNOWLEDGEMENT_IDS_PARAM;
import static org.egov.inbox.util.BSConstants.LOCALITY_PARAM;
import static org.egov.inbox.util.EstimateConstant.ESTIMATE_SERVICE;
import static org.egov.inbox.util.EstimateConstant.OFFSET_PARAM;

@Slf4j
@Service
public class InboxService {

    private InboxConfiguration config;

    private ServiceRequestRepository serviceRequestRepository;

    private ObjectMapper mapper;

    private WorkflowService workflowService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    @Autowired
    private EstimateInboxFilterService estimateInboxFilterService;

    @Autowired
    public InboxService(InboxConfiguration config, ServiceRequestRepository serviceRequestRepository,
                        ObjectMapper mapper, WorkflowService workflowService) {
        this.config = config;
        this.serviceRequestRepository = serviceRequestRepository;
        this.mapper = mapper;
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        this.workflowService = workflowService;
    }

    public InboxResponse fetchInboxData(InboxSearchCriteria criteria, RequestInfo requestInfo) {
        ProcessInstanceSearchCriteria processCriteria = criteria.getProcessSearchCriteria();
        HashMap moduleSearchCriteria = criteria.getModuleSearchCriteria();
        processCriteria.setTenantId(criteria.getTenantId());
        Integer flag = 0;

        Integer totalCount = workflowService.getProcessCount(criteria.getTenantId(), requestInfo, processCriteria);
        Integer nearingSlaProcessCount = workflowService.getNearingSlaProcessCount(criteria.getTenantId(), requestInfo, processCriteria);
        List<String> inputStatuses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(processCriteria.getStatus()))
            inputStatuses = new ArrayList<>(processCriteria.getStatus());
        StringBuilder assigneeUuid = new StringBuilder();
        //get user -> uuid from request info in case of 'estimate'
        if (!ObjectUtils.isEmpty(processCriteria.getAssignee())) {
            assigneeUuid = assigneeUuid.append(processCriteria.getAssignee());
            processCriteria.setStatus(null);
        }

        //assignee -> workflow service--> estimate number
        //list of estimate numbers + filters --> estimate service
        //response aggregate

        // Since we want the whole status count map regardless of the status filter and assignee filter being passed
        processCriteria.setAssignee(null);
        processCriteria.setStatus(null);

        List<HashMap<String, Object>> bpaCitizenStatusCountMap = new ArrayList<HashMap<String, Object>>();
        List<String> roles = requestInfo.getUserInfo().getRoles().stream().map(Role::getCode).collect(Collectors.toList());

        String moduleName = processCriteria.getModuleName();
        /*
         * SAN-920: Commenting out this code as Module name will now be passed for FSM
         * if(ObjectUtils.isEmpty(processCriteria.getModuleName()) &&
         * !ObjectUtils.isEmpty(processCriteria.getBusinessService()) &&
         * (processCriteria.getBusinessService().contains("FSM") ||
         * processCriteria.getBusinessService().contains("FSM_VEHICLE_TRIP"))){
         * processCriteria.setModuleName(processCriteria.getBusinessService().get(0)); }
         */
        List<HashMap<String, Object>> statusCountMap = workflowService.getProcessStatusCount(requestInfo, processCriteria);
        processCriteria.setModuleName(moduleName);
        processCriteria.setStatus(inputStatuses);
        processCriteria.setAssignee(assigneeUuid.toString());
        List<String> businessServiceName = processCriteria.getBusinessService();
        List<Inbox> inboxes = new ArrayList<>();
        InboxResponse response = new InboxResponse();
        JSONArray businessObjects = null;
        // Map<String,String> srvMap = (Map<String, String>) config.getServiceSearchMapping().get(businessServiceName.get(0));
        Map<String, String> srvMap = fetchAppropriateServiceMap(businessServiceName, moduleName);
        if (CollectionUtils.isEmpty(businessServiceName)) {
            throw new CustomException(ErrorConstants.MODULE_SEARCH_INVLAID, "Business Service is mandatory for module search");
        }

        Map<String, Long> businessServiceSlaMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(moduleSearchCriteria)) {
            moduleSearchCriteria.put("tenantId", criteria.getTenantId());
            moduleSearchCriteria.put("offset", criteria.getOffset());
            moduleSearchCriteria.put("limit", criteria.getLimit());
            List<BusinessService> bussinessSrvs = new ArrayList<BusinessService>();
            for (String businessSrv : businessServiceName) {
                BusinessService businessService = workflowService.getBusinessService(criteria.getTenantId(), requestInfo,
                        businessSrv);
                bussinessSrvs.add(businessService);
                businessServiceSlaMap.put(businessService.getBusinessService(), businessService.getBusinessServiceSla());
            }
            HashMap<String, String> StatusIdNameMap = workflowService.getActionableStatusesForRole(requestInfo, bussinessSrvs,
                    processCriteria);
            String applicationStatusParam = srvMap.get("applsStatusParam");
            String businessIdParam = srvMap.get("businessIdProperty");
            if (StringUtils.isEmpty(applicationStatusParam)) {
                applicationStatusParam = "applicationStatus";
            }
            List<String> crtieriaStatuses = new ArrayList<String>();
            // if(!CollectionUtils.isEmpty((Collection<String>) moduleSearchCriteria.get(applicationStatusParam))) {
            // //crtieriaStatuses = (List<String>) moduleSearchCriteria.get(applicationStatusParam);
            // }else {
            if (StatusIdNameMap.values().size() > 0) {
                if (!CollectionUtils.isEmpty(processCriteria.getStatus())) {
                    List<String> statuses = new ArrayList<String>();
                    processCriteria.getStatus().forEach(status -> {
                        statuses.add(StatusIdNameMap.get(status));
                    });
                    moduleSearchCriteria.put(applicationStatusParam, StringUtils.arrayToDelimitedString(statuses.toArray(), ","));
                } else {
                    moduleSearchCriteria.put(applicationStatusParam,
                            StringUtils.arrayToDelimitedString(StatusIdNameMap.values().toArray(), ","));
                }

            }

            Map<String, List<String>> tenantAndApplnNumbersMap = new HashMap<>();

            /*
             * In the WF statuscount API, locality based fileter is not supported.
             * To support status wise count based on locality, with status and locality API
             * is called and those count will be set in statuscount response.
             */

            // }
            // Redirect request to searcher in case of PT to fetch acknowledgement IDS
            Boolean isSearchResultEmpty = false;
            List<String> businessKeys = new ArrayList<>();
            if (!ObjectUtils.isEmpty(processCriteria.getModuleName()) && processCriteria.getModuleName().equals(ESTIMATE_SERVICE)) {
//                totalCount = estimateInboxFilterService.fetchEstimateNumbersCountFromEstimateSearcher(criteria, StatusIdNameMap,
//                        requestInfo);
                List<String> estimateNumbers = estimateInboxFilterService.fetchEstimateNumbersFromEstimateSearcher(criteria,
                        StatusIdNameMap, requestInfo);

                if (!CollectionUtils.isEmpty(estimateNumbers)) {
                    moduleSearchCriteria.put(ACKNOWLEDGEMENT_IDS_PARAM, estimateNumbers);
                    businessKeys.addAll(estimateNumbers);
                    moduleSearchCriteria.remove(LOCALITY_PARAM);
                    moduleSearchCriteria.remove(OFFSET_PARAM);
                } else {
                    isSearchResultEmpty = true;
                }
            }
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> businessMapWS = new LinkedHashMap<>();

            businessObjects = new JSONArray();
            if (!isSearchResultEmpty) {
                businessObjects = fetchModuleObjects(moduleSearchCriteria, businessServiceName, criteria.getTenantId(),
                        requestInfo, srvMap);
            }
            Map<String, Object> businessMap = StreamSupport.stream(businessObjects.spliterator(), false)
                    .collect(Collectors.toMap(s1 -> ((JSONObject) s1).get(businessIdParam).toString(),
                            s1 -> s1, (e1, e2) -> e1, LinkedHashMap::new));
            ArrayList businessIds = new ArrayList();
            businessIds.addAll(businessMap.keySet());
            processCriteria.setBusinessIds(businessIds);
            // processCriteria.setOffset(criteria.getOffset());
            // processCriteria.setLimit(criteria.getLimit());
            processCriteria.setIsProcessCountCall(false);

            String businessService;
            Map<String, String> srvSearchMap;
            JSONArray serviceSearchObject = new JSONArray();
//            Map<String, Object> serviceSearchMap;
//            serviceSearchMap = StreamSupport.stream(serviceSearchObject.spliterator(), false)
//                    .collect(Collectors.toMap(s1 -> ((JSONObject) s1).get("connectionNo").toString(),
//                            s1 -> s1, (e1, e2) -> e1, LinkedHashMap::new));

            ProcessInstanceResponse processInstanceResponse;
            processInstanceResponse = workflowService.getProcessInstance(processCriteria, requestInfo);


            List<ProcessInstance> processInstances = processInstanceResponse.getProcessInstances();
            Map<String, ProcessInstance> processInstanceMap = processInstances.stream()
                    .collect(Collectors.toMap(ProcessInstance::getBusinessId, Function.identity()));

            if (businessObjects.length() > 0 && processInstances.size() > 0) {
                if (!CollectionUtils.isEmpty(businessKeys)) {
                    businessMap.keySet().forEach(businessKey -> {
                        if (null != processInstanceMap.get(businessKey)) {
                            //For non- Bill Amendment Inbox search
                            Inbox inbox = new Inbox();
                            inbox.setProcessInstance(processInstanceMap.get(businessKey));
                            inbox.setBusinessObject(toMap((JSONObject) businessMap.get(businessKey)));
                            inboxes.add(inbox);
                        }
                    });
                }
            }
        } else {
            processCriteria.setOffset(criteria.getOffset());
            processCriteria.setLimit(criteria.getLimit());

            ProcessInstanceResponse processInstanceResponse = workflowService.getProcessInstance(processCriteria, requestInfo);
            List<ProcessInstance> processInstances = processInstanceResponse.getProcessInstances();
            HashMap<String, List<String>> businessSrvIdsMap = new HashMap<String, List<String>>();
            Map<String, ProcessInstance> processInstanceMap = processInstances.stream()
                    .collect(Collectors.toMap(ProcessInstance::getBusinessId, Function.identity()));
            moduleSearchCriteria = new HashMap<String, String>();
            if (CollectionUtils.isEmpty(srvMap)) {
                throw new CustomException(ErrorConstants.INVALID_MODULE,
                        "config not found for the businessService : " + businessServiceName);
            }
            String businessIdParam = srvMap.get("businessIdProperty");
            moduleSearchCriteria.put(srvMap.get("applNosParam"),
                    StringUtils.arrayToDelimitedString(processInstanceMap.keySet().toArray(), ","));
            moduleSearchCriteria.put("tenantId", criteria.getTenantId());
            // moduleSearchCriteria.put("offset", criteria.getOffset());
            moduleSearchCriteria.put("limit", criteria.getLimit());
            businessObjects = fetchModuleObjects(moduleSearchCriteria, businessServiceName, criteria.getTenantId(), requestInfo,
                    srvMap);
            Map<String, Object> businessMap = StreamSupport.stream(businessObjects.spliterator(), false)
                    .collect(Collectors.toMap(s1 -> ((JSONObject) s1).get(businessIdParam).toString(),
                            s1 -> s1));

            if (businessObjects.length() > 0 && processInstances.size() > 0) {
                processInstanceMap.keySet().forEach(pinstance -> {
                    Inbox inbox = new Inbox();
                    inbox.setProcessInstance(processInstanceMap.get(pinstance));
                    inbox.setBusinessObject(toMap((JSONObject) businessMap.get(pinstance)));
                    inboxes.add(inbox);
                });
            }

        }

        // log.info("businessServiceName.contains(FSM_MODULE) ::: " + businessServiceName.contains(FSM_MODULE));

        log.info("statusCountMap size :::: " + statusCountMap.size());

        response.setTotalCount(totalCount);
        response.setNearingSlaCount(nearingSlaProcessCount);
        response.setStatusMap(statusCountMap);
        response.setItems(inboxes);
        return response;
    }

    /**
     * @param businessServiceSlaMap
     * @param data                  -- application object
     * @return Description : Calculate ServiceSLA for each application for WS and SW
     */
    private Long getApplicationServiceSla(Map<String, Long> businessServiceSlaMap, Object data) {

        Long currentDate = System.currentTimeMillis(); //current time
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> properties = mapper.convertValue(data, Map.class);
        Map<String, Object> additionalDetails = (Map<String, Object>) properties.get("additionalDetails");
        if (!ObjectUtils.isEmpty(additionalDetails.get("appCreatedDate")) || !Objects.isNull(additionalDetails.get("appCreatedDate"))) {
            Long createdTime = ((Number) additionalDetails.get("appCreatedDate")).longValue();
            Map<String, Object> history = (LinkedHashMap<String, Object>) ((ArrayList) properties.get("history")).get(0);
            String businessService = (String) history.get("businessService");
            Long businessServiceSLA = businessServiceSlaMap.get(businessService);

            return Long.valueOf(Math.round((businessServiceSLA - (currentDate - createdTime)) / ((double) (24 * 60 * 60 * 1000))));
        }
        return null;
    }

    private Map<String, String> fetchAppropriateServiceMap(List<String> businessServiceName, String moduleName) {
        StringBuilder appropriateKey = new StringBuilder();
        for (String businessServiceKeys : config.getServiceSearchMapping().keySet()) {
            if (businessServiceKeys.contains(businessServiceName.get(0))) {
                appropriateKey.append(businessServiceKeys);
                break;
            }
        }
        if (ObjectUtils.isEmpty(appropriateKey)) {
            throw new CustomException("EG_INBOX_SEARCH_ERROR",
                    "Inbox service is not configured for the provided business services");
        }
        return config.getServiceSearchMapping().get(appropriateKey.toString());
    }

    private JSONArray fetchModuleObjects(HashMap moduleSearchCriteria, List<String> businessServiceName, String tenantId,
                                         RequestInfo requestInfo, Map<String, String> srvMap) {
        JSONArray resutls = null;

        if (CollectionUtils.isEmpty(srvMap) || StringUtils.isEmpty(srvMap.get("searchPath"))) {
            throw new CustomException(ErrorConstants.INVALID_MODULE_SEARCH_PATH,
                    "search path not configured for the businessService : " + businessServiceName);
        }
        StringBuilder url = new StringBuilder(srvMap.get("searchPath"));
        url.append("?tenantId=").append(tenantId);

        Set<String> searchParams = moduleSearchCriteria.keySet();

        searchParams.forEach((param) -> {

            if (!param.equalsIgnoreCase("tenantId")) {

                if (moduleSearchCriteria.get(param) instanceof Collection) {
                    url.append("&").append(param).append("=");
                    url.append(StringUtils
                            .arrayToDelimitedString(((Collection<?>) moduleSearchCriteria.get(param)).toArray(), ","));
                } else if (param.equalsIgnoreCase("appStatus")) {
                    url.append("&").append("applicationStatus").append("=")
                            .append(moduleSearchCriteria.get(param).toString());
                } else if (param.equalsIgnoreCase("consumerNo")) {
                    url.append("&").append("connectionNumber").append("=")
                            .append(moduleSearchCriteria.get(param).toString());
                } else if (null != moduleSearchCriteria.get(param)) {
                    url.append("&").append(param).append("=").append(moduleSearchCriteria.get(param).toString());
                }
            }
        });

        log.info("\nfetchModuleObjects URL :::: " + url.toString());

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        LinkedHashMap responseMap;
        try {
            responseMap = mapper.convertValue(result, LinkedHashMap.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorConstants.PARSING_ERROR, "Failed to parse response of ProcessInstance Count");
        }


        JSONObject jsonObject = new JSONObject(responseMap);

        try {
            resutls = (JSONArray) jsonObject.getJSONArray(srvMap.get("dataRoot"));
        } catch (Exception e) {
            throw new CustomException(ErrorConstants.INVALID_MODULE_DATA,
                    " search api could not find data in dataroot " + srvMap.get("dataRoot"));
        }


        return resutls;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        if (object == null) {
            return map;
        }
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    private Map<String, String> fetchAppropriateServiceSearchMap(String businessServiceName, String moduleName) {
        StringBuilder appropriateKey = new StringBuilder();
        for (String businessServiceKeys : config.getBsServiceSearchMapping().keySet()) {
            if (businessServiceKeys.contains(businessServiceName)) {
                appropriateKey.append(businessServiceKeys);
                break;
            }
        }
        if (ObjectUtils.isEmpty(appropriateKey)) {
            throw new CustomException("EG_INBOX_SEARCH_ERROR",
                    "Inbox service is not configured for the provided business services");
        }
        return config.getBsServiceSearchMapping().get(appropriateKey.toString());
    }

    private JSONArray fetchModuleSearchObjects(HashMap moduleSearchCriteria, List<String> businessServiceName,
                                               String tenantId, RequestInfo requestInfo, Map<String, String> srvMap) {
        JSONArray results = null;

        if (CollectionUtils.isEmpty(srvMap) || StringUtils.isEmpty(srvMap.get("searchPath"))) {
            throw new CustomException(ErrorConstants.INVALID_MODULE_SEARCH_PATH,
                    "search path not configured for the businessService : " + businessServiceName);
        }
        StringBuilder url = new StringBuilder(srvMap.get("searchPath"));
        url.append("?tenantId=").append(tenantId);

        Set<String> searchParams = moduleSearchCriteria.keySet();

        searchParams.forEach((param) -> {

            if (!param.equalsIgnoreCase("tenantId")) {
                if (param.equalsIgnoreCase("limit"))
                    return;
                if (moduleSearchCriteria.get(param) instanceof Collection) {
                    url.append("&").append(param).append("=");
                    url.append(StringUtils
                            .arrayToDelimitedString(((Collection<?>) moduleSearchCriteria.get(param)).toArray(), ","));
                } else if (null != moduleSearchCriteria.get(param)) {
                    url.append("&").append(param).append("=").append(moduleSearchCriteria.get(param).toString());
                }
            }
        });

        log.info("\nfetchModulSearcheObjects URL :::: " + url.toString());

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
        Object result = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        LinkedHashMap responseMap;
        try {
            responseMap = mapper.convertValue(result, LinkedHashMap.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorConstants.PARSING_ERROR, "Failed to parse response of ProcessInstance Count");
        }

        JSONObject jsonObject = new JSONObject(responseMap);

        try {
            results = (JSONArray) jsonObject.getJSONArray(srvMap.get("dataRoot"));
        } catch (Exception e) {
            throw new CustomException(ErrorConstants.INVALID_MODULE_DATA, " search api could not find data in serviceMap " + srvMap.get("dataRoot"));
        }

        return results;
    }
}
