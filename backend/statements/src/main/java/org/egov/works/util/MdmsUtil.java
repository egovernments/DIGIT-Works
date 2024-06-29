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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private final CommonUtil commonUtil;

    private static final String FILTER_START = "[?(";
    private static final String FILTER_END = ")]";
    private static final String COMPOSITION_RATE_FILTER_CODE = "@.sorId==''";
    private static final String COMPOSITION_SOR_FILTER_CODE = "@.id==''";
    private static final String COMPOSITION_ID_FILTER_CODE="@.compositionId==''";
    private static final String OR_ADDITIONAL_FILTER = "||";
    public static final String MDMS_SOR_MASTER_NAME = "SOR";
    private static final String SOR_FILTER_CODE = "@.id=='%s'";

    @Autowired
    private MdmsUtil(RestTemplate restTemplate, ObjectMapper mapper, StatementConfiguration configs, ServiceRequestRepository serviceRequestRepository, CommonUtil commonUtil){
        this.restTemplate=restTemplate;
        this.configs=configs;
        this.mapper=mapper;
        this.serviceRequestRepository=serviceRequestRepository;

        this.commonUtil = commonUtil;
    }


    //TODO  Need to remove this method as now we are fetching the composition based on compositonId
    public Map<String, SorComposition> fetchSorComposition(RequestInfo requestInfo,Set<String> sorIdSet, String tenantId,Long createdTime) {


        String filter = getfilter(sorIdSet,Boolean.FALSE);
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
        Map<String, List<SorComposition>> sorIdToCompositionMap = sorCompositions.stream().collect(Collectors.groupingBy(e -> e.getSorId()));

        Map<String, SorComposition> sorIdToCompositionMap1 = new HashMap<>();
        for (Map.Entry<String, List<SorComposition>> entry : sorIdToCompositionMap.entrySet()) {
            sorIdToCompositionMap1.put(entry.getKey(), commonUtil.getApplicableSorComposition(entry.getValue(), createdTime));
        }

       /* if (sorIdToCompositionMap.size() != sorIdSet.size()) {
            analysisRequest.getSorDetails().getSorCodes().remove(sorIdToCompositionMap.keySet());
            throw new CustomException("SOR_COMPOSITION_NOT_FOUND", "Sor composition not found for SOR codes :: " + analysisRequest.getSorDetails().getSorCodes());
        }*/
        return sorIdToCompositionMap1;



    }

    /**
     * This method is used to fetch the SorComposition based on the composition Id
     * @param requestInfo
     * @param compositionId
     * @param tenantId
     * @param createdTime
     * @return
     */
    public Map<String, SorComposition> fetchSorCompositionBasedOnCompositionId(RequestInfo requestInfo,Set<String> compositionId, String tenantId,Long createdTime) {


        String filter = getfilterForSorComposition(compositionId);
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
        Map<String, List<SorComposition>> sorIdToCompositionMap = sorCompositions.stream().collect(Collectors.groupingBy(e -> e.getSorId()));

        Map<String, SorComposition> sorIdToCompositionMap1 = new HashMap<>();
        for (Map.Entry<String, List<SorComposition>> entry : sorIdToCompositionMap.entrySet()) {
            sorIdToCompositionMap1.put(entry.getKey(), commonUtil.getApplicableSorComposition(entry.getValue(), createdTime));
        }

       /* if (sorIdToCompositionMap.size() != sorIdSet.size()) {
            analysisRequest.getSorDetails().getSorCodes().remove(sorIdToCompositionMap.keySet());
            throw new CustomException("SOR_COMPOSITION_NOT_FOUND", "Sor composition not found for SOR codes :: " + analysisRequest.getSorDetails().getSorCodes());
        }*/
        return sorIdToCompositionMap1;



    }

    private String getfilter(Set<String> sorIds, Boolean isSorRequest) {
        StringBuilder filterBuilder = new StringBuilder(FILTER_START);
        Iterator<String> sorIdsIterator = sorIds.iterator();
        while (sorIdsIterator.hasNext()) {
            String sorId= sorIdsIterator.next();
            String sorIdOrRateFilter = Boolean.TRUE.equals(isSorRequest)? COMPOSITION_SOR_FILTER_CODE:COMPOSITION_RATE_FILTER_CODE;
            // Replace the placeholder in the template with the actual sorId
            String formattedFilter = sorIdOrRateFilter.replace("''", "'" + sorId + "'");
            filterBuilder.append(formattedFilter);
            if(sorIdsIterator.hasNext()){
                filterBuilder.append(OR_ADDITIONAL_FILTER);
            }
        }
        String ratesFilter =  FILTER_START + filterBuilder + FILTER_END;
        filterBuilder.append(FILTER_END);

        return filterBuilder.toString();
    }

    /**
     * Create filter for fetching Sor Composition
     * @param sorIds
     * @return
     */
    private String getfilterForSorComposition(Set<String> sorIds) {
        StringBuilder filterBuilder = new StringBuilder(FILTER_START);
        Iterator<String> sorIdsIterator = sorIds.iterator();
        while (sorIdsIterator.hasNext()) {
            String sorId= sorIdsIterator.next();
            String sorIdOrRateFilter =  COMPOSITION_ID_FILTER_CODE;
            // Replace the placeholder in the template with the actual sorId
            String formattedFilter = sorIdOrRateFilter.replace("''", "'" + sorId + "'");
            filterBuilder.append(formattedFilter);
            if(sorIdsIterator.hasNext()){
                filterBuilder.append(OR_ADDITIONAL_FILTER);
            }
        }
       // String ratesFilter =  FILTER_START + filterBuilder + FILTER_END;
        filterBuilder.append(FILTER_END);

        return filterBuilder.toString();
    }

    public Map<String, Rates> fetchBasicRates(RequestInfo requestInfo, String tenantId, List<String> sorsList) {

        String filter = getfilter(new HashSet<>(sorsList), Boolean.FALSE);
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(requestInfo,
                tenantId, "WORKS-SOR",
                Collections.singletonList("Rates"), filter);
        List<Rates> ratesList = new ArrayList<>();
        for(Object object : sorRates.get("WORKS-SOR").get("Rates")) {
            Rates  rates = mapper.convertValue(object, Rates.class);
            ratesList.add(rates);
        }

        Map<String ,List<Rates>> sorIdToRatesMap= ratesList.stream().collect(Collectors.groupingBy(Rates::getSorId));
        Long currentEpochTime=  LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)*1000;


        Map<String, Rates> sorIdToRatesMap1 = sorIdToRatesMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Rates> listOfRates = entry.getValue();

                            return commonUtil.getApplicatbleRate(listOfRates, currentEpochTime);
                        }
                ));

        return sorIdToRatesMap1;

    }
    public Map<String, Sor> fetchSorData(RequestInfo requestInfo, String tenantId, List<String> sorsList, Boolean isSorRequest) {

        String filter = getfilter(sorsList.stream().collect(Collectors.toSet()), isSorRequest);
        Map <String, Sor> sorIdToSorMap= new HashMap<>();
        Map<String, Map<String, JSONArray>> sorRates = fetchMdmsData(requestInfo,
                tenantId, "WORKS-SOR",
                Collections.singletonList("SOR"), filter);
       // List<Sor> sorList = new ArrayList<>();
        for(Object object : sorRates.get("WORKS-SOR").get("SOR")) {
            Sor  sor = mapper.convertValue(object, Sor.class);
            sorIdToSorMap.put(sor.getId(),sor);
           // sorList.add(sor);
        }

        return sorIdToSorMap;

    }

    public Map<String, List<Rates>> fetchRateForNonWorksSor(List<String> basicSorIds, RequestInfo requestInfo,String tenantId){

        String filter = getfilter(basicSorIds.stream().collect(Collectors.toSet()), Boolean.FALSE);
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