package org.egov.works.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.mdms.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.Configuration;
import org.egov.works.web.models.AnalysisRequest;
import org.egov.works.web.models.BasicSorDetail;
import org.egov.works.web.models.Rates;
import org.egov.works.web.models.SorComposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, SorComposition> fetchSorComposition(AnalysisRequest analysisRequest) {
            String filter = getfilter(analysisRequest.getSorDetails().getSorCodes());
            Map<String, Map<String, JSONArray>> sorComposition = fetchMdmsData(analysisRequest.getRequestInfo(),
                    analysisRequest.getSorDetails().getTenantId(), "WORKS-SOR",
                    Collections.singletonList("Composition"), filter);


        JSONArray jsonArray = sorComposition.get("WORKS-SOR").get("Composition");
        List<SorComposition> sorCompositions = null;
        try {
            sorCompositions = mapper.readValue(
                    jsonArray.toString(),
                    mapper.getTypeFactory().constructCollectionType(List.class, SorComposition.class)
            );
        } catch (JsonProcessingException e) {
            throw new CustomException("MDMS_PARSE_EXCEPTION", "Error while parsing mdms response");
        }
//        List<SorComposition> sorCompositions = mapper.convertValue(sorComposition.get("WORKS-SOR").get("Composition"), List.class);
        Map<String, SorComposition> sorIdToCompositionMap = sorCompositions.stream().collect(Collectors.toMap(e -> e.getSorId(), e -> e));
//        Map<String, Object> sorIdToCompositionMap =  jsonArray.stream().collect(Collectors
//                .toMap(e -> JsonPath.read(e, "$.sorId"), e -> e) );
        if (sorIdToCompositionMap.size() != analysisRequest.getSorDetails().getSorCodes().size()) {
            analysisRequest.getSorDetails().getSorCodes().remove(sorIdToCompositionMap.keySet());
            throw new CustomException("SOR_COMPOSITION_NOT_FOUND", "Sor composition not found for SOR codes :: " + analysisRequest.getSorDetails().getSorCodes());
        }
        return sorIdToCompositionMap;

    }

    public Map<String, List<Rates>> fetchBasicRates(AnalysisRequest analysisRequest, Map<String, SorComposition> sorIdToCompositionMap) {
        Set<String> basicSorIds = new HashSet<>();

        for (SorComposition sorComposition : sorIdToCompositionMap.values()) {
            basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(BasicSorDetail::getSorId).toList());
        }
        String filter = getfilter(basicSorIds.stream().toList());
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(analysisRequest.getRequestInfo(),
                analysisRequest.getSorDetails().getTenantId(), "WORKS-SOR",
                Collections.singletonList("Rates"), filter);
        List<Rates> ratesList = new ArrayList<>();
        for(Object object : sorRates.get("WORKS-SOR").get("Rates")) {
            Rates  rates = mapper.convertValue(object, Rates.class);
            ratesList.add(rates);
        }
        Map<String, List<Rates>> ratesMap = ratesList.stream().collect(Collectors.groupingBy(Rates::getSorId));
        if (ratesMap.keySet().size() != basicSorIds.size()) {
            basicSorIds.remove(ratesMap.keySet());
            throw new CustomException("RATES_NOT_FOUND", "Rates not found for the following SOR ids :: " + basicSorIds);
        }
        return ratesMap;

    }

    private String getfilter(List<String> sorIds) {
        StringBuilder filterBuilder = new StringBuilder(FILTER_START).append(ID_FILTER).append(sorIds.get(0));
        for(int i = 1; i < sorIds.size(); i++) {
            filterBuilder.append(FILTER_OR_CONSTANT).append(ID_FILTER);
            filterBuilder.append(sorIds.get(i));
        }
        filterBuilder.append(FILTER_END);
        return filterBuilder.toString();
    }

    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
                                                             List<String> masterNameList, String filter) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsEndPoint());
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
        //log.info(ulbToCategoryListMap.toString());
    }

    private MdmsCriteriaReq getMdmsRequest(RequestInfo requestInfo, String tenantId,
                                           String moduleName, List<String> masterNameList, String filter) {
        List<MasterDetail> masterDetailList = new ArrayList<>();
        for (String masterName : masterNameList) {
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.setName(masterName);
            //TODO remove commenting
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