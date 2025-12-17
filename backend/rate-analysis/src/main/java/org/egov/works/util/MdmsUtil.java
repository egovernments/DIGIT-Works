package org.egov.works.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.mdms.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.Configuration;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.config.ErrorConfiguration.*;
import static org.egov.works.config.ServiceConstants.*;

@Slf4j
@Component
public class MdmsUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Configuration configs;
    @Autowired
    private CommonUtil commonUtil;

    public void updateMdmsData(MdmsRequest mdmsRequest){
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getUpdateEndPoint()).append(mdmsRequest.getMdms().getSchemaCode());
        Object response = new HashMap<>();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsRequest, Map.class);
        } catch (Exception e) {
            log.error(ERROR_WHILE_UPDATING_MDMS, e);
        }
    }

    public MdmsResponseV2 fetchSorsFromMdms(MdmsSearchCriteriaV2 mdmsSearchCriteria) {
        MdmsResponseV2 mdmsResponse = new MdmsResponseV2();
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsV2EndPoint());
        Object response = new HashMap<>();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsSearchCriteria, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponseV2.class);
        } catch (Exception e) {
            log.error(ERROR_WHILE_FETCHING_FROM_MDMS, e);
        }
        return mdmsResponse;
    }
    public Map<String, SorComposition> fetchSorComposition(AnalysisRequest analysisRequest) {
        String filter = getfilter(analysisRequest.getSorDetails().getSorCodes(), false);
        Map<String, Map<String, JSONArray>> sorComposition = fetchMdmsData(analysisRequest.getRequestInfo(),
                analysisRequest.getSorDetails().getTenantId(), WORKS_SOR_KEY,
                Collections.singletonList(COMPOSITION_KEY), filter);


        JSONArray jsonArray;
        List<SorComposition> sorCompositions = null;
        try {
            jsonArray = sorComposition.get(WORKS_SOR_KEY).get(COMPOSITION_KEY);
            sorCompositions = mapper.readValue(
                    jsonArray.toString(),
                    mapper.getTypeFactory().constructCollectionType(List.class, SorComposition.class)
            );
        } catch (Exception e) {
            throw new CustomException(MDMS_PARSE_EXCEPTION_KEY, MDMS_PARSE_EXCEPTION_VALUE);
        }
//        List<SorComposition> sorCompositions = mapper.convertValue(sorComposition.get("WORKS-SOR").get("Composition"), List.class);
        Map<String, List<SorComposition>> sorIdToCompositionMap = sorCompositions.stream().collect(Collectors.groupingBy(e -> e.getSorId()));
        Map<String, SorComposition> sorIdToCompositionMap1 = new HashMap<>();
        for (Map.Entry<String, List<SorComposition>> entry : sorIdToCompositionMap.entrySet()) {
            sorIdToCompositionMap1.put(entry.getKey(), commonUtil.getApplicableSorComposition(entry.getValue(), Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom())));
        }
//        Map<String, Object> sorIdToCompositionMap =  jsonArray.stream().collect(Collectors
//                .toMap(e -> JsonPath.read(e, "$.sorId"), e -> e) );
        if (sorIdToCompositionMap.size() != analysisRequest.getSorDetails().getSorCodes().size()) {
            analysisRequest.getSorDetails().getSorCodes().remove(sorIdToCompositionMap.keySet());
            throw new CustomException(SOR_COMPOSITION_NOT_FOUND_KEY, SOR_COMPOSITION_NOT_FOUND_VALUE + analysisRequest.getSorDetails().getSorCodes());
        }
        return sorIdToCompositionMap1;

    }

    public Map<String, List<Rates>> fetchBasicRates(AnalysisRequest analysisRequest, Map<String, SorComposition> sorIdToCompositionMap) {
        Set<String> basicSorIds = new HashSet<>();

        for (SorComposition sorComposition : sorIdToCompositionMap.values()) {
            basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(BasicSorDetail::getSorId).toList());
        }
        String filter = getfilter(basicSorIds.stream().toList(), false);
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(analysisRequest.getRequestInfo(),
                analysisRequest.getSorDetails().getTenantId(), WORKS_SOR_KEY,
                Collections.singletonList(RATES_KEY), filter);
        List<Rates> ratesList = new ArrayList<>();
        if (sorRates.containsKey(WORKS_SOR_KEY) && sorRates.get(WORKS_SOR_KEY).containsKey(RATES_KEY)) {
            for(Object object : sorRates.get(WORKS_SOR_KEY).get(RATES_KEY)) {
                Rates  rates = mapper.convertValue(object, Rates.class);
                ratesList.add(rates);
            }
        }

        Map<String, List<Rates>> ratesMap = ratesList.stream().collect(Collectors.groupingBy(Rates::getSorId));
//        if (ratesMap.keySet().size() != basicSorIds.size()) {
//            basicSorIds.remove(ratesMap.keySet());
//            throw new CustomException("RATES_NOT_FOUND", "Rates not found for the following SOR ids :: " + basicSorIds);
//        }
        return ratesMap;

    }

    public Map<String, JsonNode> fetchSor(AnalysisRequest analysisRequest, Map<String, SorComposition> sorIdToCompositionMap) {
        Set<String> basicSorIds = new HashSet<>();

        for (SorComposition sorComposition : sorIdToCompositionMap.values()) {
            basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(BasicSorDetail::getSorId).toList());
        }
        basicSorIds.addAll(analysisRequest.getSorDetails().getSorCodes());
        String filter = getfilter(basicSorIds.stream().toList(), true);
        Map<String, Map<String, JSONArray>> sor = fetchMdmsData(analysisRequest.getRequestInfo(),
                analysisRequest.getSorDetails().getTenantId(), WORKS_SOR_KEY,
                Collections.singletonList(SOR_KEY), filter);
        JSONArray jsonArray = sor.get(WORKS_SOR_KEY).get(SOR_KEY);
        Map<String, JsonNode> sorMap = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonNode jsonNode = mapper.convertValue(jsonArray.get(i), JsonNode.class);
            String id = jsonNode.get("id").asText();
            sorMap.put(id, jsonNode);
        }
        if (sorMap.keySet().size() != basicSorIds.size()) {
            basicSorIds.removeAll(sorMap.keySet());
            throw new CustomException(SOR_NOT_FOUND_KEY, SOR_NOT_FOUND_VALUE + basicSorIds);
        }
        return sorMap;
    }

    public Map<String, Rates> fetchWorksRates(AnalysisRequest analysisRequest) {
        String filter = getfilter(analysisRequest.getSorDetails().getSorCodes(), false);
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(analysisRequest.getRequestInfo(),
                analysisRequest.getSorDetails().getTenantId(), WORKS_SOR_KEY,
                Collections.singletonList(RATES_KEY), filter);
        List<Rates> ratesList = new ArrayList<>();
        for(Object object : sorRates.get(WORKS_SOR_KEY).get(RATES_KEY)) {
            Rates  rates = mapper.convertValue(object, Rates.class);
            ratesList.add(rates);
        }
        Map<String, List<Rates>> ratesMap = ratesList.stream().collect(Collectors.groupingBy(Rates::getSorId));
        Map<String, Rates> rateMap = new HashMap<>();
        for (Map.Entry<String, List<Rates>> entry : ratesMap.entrySet()) {
            rateMap.put(entry.getKey(), commonUtil.getApplicatbleRate(entry.getValue(), Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom())));
        }
        return rateMap;
    }

    private String getfilter(List<String> sorIds, boolean isSOR) {
        String filterBy = isSOR ? ID_FILTER : SOR_ID_FILTER;
        StringBuilder filterBuilder = new StringBuilder(FILTER_START).append(filterBy).append(sorIds.get(0));
        for(int i = 1; i < sorIds.size(); i++) {
            filterBuilder.append(FILTER_OR_CONSTANT).append(filterBy);
            filterBuilder.append(sorIds.get(i));
        }
        filterBuilder.append(FILTER_END);
        return filterBuilder.toString();
    }

    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
                                                             List<String> masterNameList, String filter) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsV1EndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId, moduleName, masterNameList, filter);
        Object response = new HashMap<>();
        Integer rate = 0;
        MdmsResponse mdmsResponse = new MdmsResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
        } catch (Exception e) {
            log.error(ERROR_WHILE_FETCHING_FROM_MDMS, e);
        }

        return mdmsResponse.getMdmsRes();
    }

    private MdmsCriteriaReq getMdmsRequest(RequestInfo requestInfo, String tenantId,
                                           String moduleName, List<String> masterNameList, String filter) {
        List<MasterDetail> masterDetailList = new ArrayList<>();
        for (String masterName : masterNameList) {
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.setName(masterName);
            masterDetail.setFilter(filter);
            masterDetailList.add(masterDetail);
        }

        ModuleDetail moduleDetail = new ModuleDetail();
        moduleDetail.setMasterDetails(masterDetailList);
        moduleDetail.setModuleName(moduleName);
        List<ModuleDetail> moduleDetailList = new ArrayList<>();
        moduleDetailList.add(moduleDetail);

        MdmsCriteria mdmsCriteria = new MdmsCriteria();
        mdmsCriteria.setTenantId(tenantId);
        mdmsCriteria.setModuleDetails(moduleDetailList);

        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
        mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
        mdmsCriteriaReq.setRequestInfo(requestInfo);

        return mdmsCriteriaReq;
    }
}