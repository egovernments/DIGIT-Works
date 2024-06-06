package org.egov.works.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.mdms.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.config.ServiceConstants.ERROR_WHILE_FETCHING_FROM_MDMS;

;

@Slf4j
@Component
public class MdmsUtil {

    private RestTemplate restTemplate;

    private ObjectMapper mapper;


    private StatementConfiguration configs;

    private final ServiceRequestRepository serviceRequestRepository;

    private static final String FILTER_START = "[?(";
    private static final String FILTER_END = "')]";
    private static final String COMPOSITION_FILTER_CODE = "@.sorId=='";
    private static final String OR_ADDITIONAL_FILTER = "'||";
    public static final String MDMS_SOR_MASTER_NAME = "SOR";
    private static final String SOR_FILTER_CODE = "@.id=='%s'";

    @Autowired
    private MdmsUtil(RestTemplate restTemplate,ObjectMapper mapper,StatementConfiguration configs,ServiceRequestRepository serviceRequestRepository){
        this.restTemplate=restTemplate;
        this.configs=configs;
        this.mapper=mapper;
        this.serviceRequestRepository=serviceRequestRepository;

    }


    public MdmsResponseV2 fetchSorsFromMdms(MdmsSearchCriteriaV2 mdmsSearchCriteria) {
        MdmsResponseV2 mdmsResponse = new MdmsResponseV2();
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsV2Host()).append(configs.getMdmsSearchEndPoint());
        Object response = new HashMap<>();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsSearchCriteria, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponseV2.class);
        } catch (Exception e) {
            log.error(ERROR_WHILE_FETCHING_FROM_MDMS, e);
        }
        return mdmsResponse;
    }

    public Map<String, SorComposition> fetchSorComposition(RequestInfo requestInfo,Set<String> sorIdSet, String tenantId) {


        String filter = getfilter(new ArrayList<>(sorIdSet));
        Map<String, Map<String, JSONArray>> sorComposition = fetchMdmsData(requestInfo,
                configs.getStateLevelTenantId(), "WORKS-SOR",
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
        Map<String, SorComposition> sorIdToCompositionMap = sorCompositions.stream().collect(Collectors.toMap(e -> e.getSorId(), e -> e));

        return sorIdToCompositionMap;

    }

    private String getfilter(List<String> sorIds) {
        StringBuilder filterBuilder = new StringBuilder(FILTER_START).append(COMPOSITION_FILTER_CODE).append(sorIds.get(0));
        for(int i = 1; i < sorIds.size(); i++) {
            filterBuilder.append(OR_ADDITIONAL_FILTER).append(COMPOSITION_FILTER_CODE);
            filterBuilder.append(sorIds.get(i));
        }
        filterBuilder.append(FILTER_END);
        return filterBuilder.toString();
    }

    public Map<String, List<Rates>> fetchBasicRates(RequestInfo requestInfo, String tenantId, Map<String, SorComposition> sorIdToCompositionMap) {
        Set<String> basicSorIds = new HashSet<>();

        for (SorComposition sorComposition : sorIdToCompositionMap.values()) {
            basicSorIds.addAll(sorComposition.getBasicSorDetails().stream().map(SorCompositionBasicSorDetail::getSorId).toList());
        }
        String filter = getfilter(basicSorIds.stream().toList());
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(requestInfo,
                tenantId, "WORKS-SOR",
                Collections.singletonList("Rates"), filter);
        List<Rates> ratesList = new ArrayList<>();
        for(Object object : sorRates.get("WORKS-SOR").get("Rates")) {
            Rates  rates = mapper.convertValue(object, Rates.class);
            ratesList.add(rates);
        }
        return ratesList.stream().collect(Collectors.groupingBy(Rates::getSorId));

    }

    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
                                                             List<String> masterNameList, String filter) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId, moduleName, masterNameList, filter);
        Object response = new HashMap<>();

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



 /*   public Object mdmsCallV2ForSorComposition(RequestInfo requestInfo,Set<String> sorIdSet, String tenantId){

        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestV2(requestInfo,tenantId,sorIdSet);
        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(),mdmsCriteriaReq);
    }*/

/*    *//**
     * Returns the url for mdms search v2 endpoint
     *
     * @return url for mdms search v2 endpoint
     *//*
    public StringBuilder getMdmsSearchUrlV2() {
        return new StringBuilder().append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
    }*/
/*    public MdmsCriteriaReq getMDMSRequestV2(RequestInfo requestInfo , String  tenantId , Set<String> sorIds){
        log.info("MDMSUtils::getMDMSRequestV2");
        ModuleDetail compositionSorIdModuleDetail = getSorCompositonModuleRequestData(sorIds);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(compositionSorIdModuleDetail)).tenantId(tenantId).build();
        return MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
    }*/

 /*   private ModuleDetail getSorCompositonModuleRequestData(Set<String> sorIds) {
        log.info("MDMSUtils::getSorIdModuleRequestData");
        List<MasterDetail> compostionSorIdMasterDetails = new ArrayList<>();
        MasterDetail compositionMasterDetails;
        StringBuilder sorStringBuilder = new StringBuilder();
        Iterator<String> sorIterator = sorIds.iterator();
        while (sorIterator.hasNext()) {
            String sorIdRateFilter = String.format( RATES_FILTER_CODE, sorIterator.next());
            sorStringBuilder.append(sorIdRateFilter);
            if(sorIterator.hasNext()){
                sorStringBuilder.append(OR_ADDITIONAL_FILTER);
            }
        }
        String ratesFilter =  FILTER_START + sorStringBuilder + FILTER_END;
        compositionMasterDetails = MasterDetail.builder().name(MDMS_COMPOSITION_MASTER_NAME)
                .filter(ratesFilter).build();

        compostionSorIdMasterDetails.add(compositionMasterDetails);

        return ModuleDetail.builder().masterDetails(compostionSorIdMasterDetails)
                .moduleName(configs.getSorCompositionSearchModuleName()).build();
    }*/
    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
                                                                                List<String> masterNameList) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsHost()).append(configs.getMdmsEndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId, moduleName, masterNameList);
        Object response = new HashMap<>();
        Integer rate = 0;
        MdmsResponse mdmsResponse = new MdmsResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
        }catch(Exception e) {
            log.error(ERROR_WHILE_FETCHING_FROM_MDMS,e);
        }

        return mdmsResponse.getMdmsRes();
        //log.info(ulbToCategoryListMap.toString());
    }

    private MdmsCriteriaReq getMdmsRequest(RequestInfo requestInfo, String tenantId,
                                           String moduleName, List<String> masterNameList) {
        List<MasterDetail> masterDetailList = new ArrayList<>();
        for(String masterName: masterNameList) {
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.setName(masterName);
            masterDetailList.add(masterDetail);
        }

        ModuleDetail moduleDetail = new ModuleDetail();
        moduleDetail.setMasterDetails(masterDetailList);
        moduleDetail.setModuleName(moduleName);
        List<ModuleDetail> moduleDetailList = new ArrayList<>();
        moduleDetailList.add(moduleDetail);

        MdmsCriteria mdmsCriteria = new MdmsCriteria();
        mdmsCriteria.setTenantId(tenantId.split("\\.")[0]);
        mdmsCriteria.setModuleDetails(moduleDetailList);

        MdmsCriteriaReq mdmsCriteriaReq = new MdmsCriteriaReq();
        mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
        mdmsCriteriaReq.setRequestInfo(requestInfo);

        return mdmsCriteriaReq;
    }



    /**
     * Calls MDMS v2 service to fetch works master data
     *
     * @param requestInfo
     * @param tenantId
     * @return
     */
    public Object mdmsCallV2ForSor(RequestInfo requestInfo, String tenantId, Set<String> sorIds ){
        log.info("MDMSUtils::mDMSCallV2");
        MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestV2(requestInfo,tenantId, sorIds);
        return serviceRequestRepository.fetchResult(getMdmsSearchUrlV2(), mdmsCriteriaReq);
    }


    /**
     * Returns mdms v2 search criteria based on the tenantId and mdms search criteria
     * @return
     */

    public MdmsCriteriaReq getMDMSRequestV2(RequestInfo requestInfo , String  tenantId ,Set<String>sorIds){
        log.info("MDMSUtils::getMDMSRequestV2");
        ModuleDetail estimateSorIdModuleDetail = getSorModuleRequestData(sorIds);
        MdmsCriteria mdmsCriteria = MdmsCriteria.builder().moduleDetails(Collections.singletonList(estimateSorIdModuleDetail)).tenantId(tenantId).build();
        return MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(mdmsCriteria).build();
    }


    /**
     * Method to create SorId module with required filters for SOR and Rates for fetching master data
     * @param sorIds
     * @return
     */
    private ModuleDetail getSorModuleRequestData(Set<String> sorIds) {
        log.info("MDMSUtils::getSorIdModuleRequestData");
        List<MasterDetail> compostionSorIdMasterDetails = new ArrayList<>();
        MasterDetail compositionMasterDetails;
        StringBuilder sorStringBuilder = new StringBuilder();
        Iterator<String> sorIterator = sorIds.iterator();
        while (sorIterator.hasNext()) {
            String sorIdFilter = String.format( SOR_FILTER_CODE, sorIterator.next());
            sorStringBuilder.append(sorIdFilter);
            if(sorIterator.hasNext()){
                sorStringBuilder.append(OR_ADDITIONAL_FILTER);
            }
        }
        String sorFilter =  FILTER_START + sorStringBuilder + FILTER_END;
        compositionMasterDetails = MasterDetail.builder().name(MDMS_SOR_MASTER_NAME)
                .filter(sorFilter).build();

        compostionSorIdMasterDetails.add(compositionMasterDetails);

        return ModuleDetail.builder().masterDetails(compostionSorIdMasterDetails)
                .moduleName(configs.getSorCompositionSearchModuleName()).build();
    }

    public StringBuilder getMdmsSearchUrlV2() {
        return new StringBuilder().append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
    }
}