package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.Configuration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.egov.works.config.ServiceConstants.RATES_SCHEMA_CODE;
import static org.egov.works.config.ServiceConstants.VALID_TO_KEY;

@Service
@Slf4j
public class MdmsService {

    private final ObjectMapper mapper;
    private final ServiceRequestRepository restRepo;
    private final Configuration configs;
    private final MdmsUtil mdmsUtil;

    public MdmsService(ObjectMapper mapper, ServiceRequestRepository restRepo, Configuration configs, MdmsUtil mdmsUtil) {

        this.mapper = mapper;
        this.restRepo = restRepo;
        this.configs = configs;
        this.mdmsUtil = mdmsUtil;
    }

    public void createRevisedRates(List<Rates> revisedRates, Map<String, Rates> oldRatesMap, RequestInfo requestInfo) {
        List<Mdms> mdmsData = fetchOldMdms(oldRatesMap, requestInfo, revisedRates.get(0).getTenantId());
        Map<String, List<Mdms>> mdmsMap = mdmsData.stream()
                .collect(Collectors.groupingBy(mdms -> mdms.getUniqueIdentifier().split("\\.")[0]));
        for (Rates rates : revisedRates) {
            createRates(rates, requestInfo);
            if (oldRatesMap.containsKey(rates.getSorId()) && oldRatesMap.get(rates.getSorId()) != null) {
                Mdms mdms = mdmsMap.get(rates.getSorId()).get(0);
                ObjectNode objectNode = (ObjectNode) mdms.getData();
                if (objectNode.has(VALID_TO_KEY)) {
                    objectNode.replace(VALID_TO_KEY, mapper.valueToTree(rates.getValidFrom()));
                } else {
                    objectNode.put(VALID_TO_KEY, mapper.valueToTree(rates.getValidFrom()));
                }
                mdms.setData(objectNode);
                updateRates(mdms, requestInfo);
                log.info("Previous rates updated succesfully");

            }
        }
    }

    private void createRates(Rates rates, RequestInfo requestInfo) {
        log.info("Creating rates for sorId " + rates.getSorId());

        Mdms createMdmsData = Mdms.builder()
                .tenantId(rates.getTenantId())
                .schemaCode(RATES_SCHEMA_CODE)
                .isActive(true)
                .data(mapper.convertValue(rates, JsonNode.class)).build();
        MdmsRequest mdmsRequest = MdmsRequest.builder()
                .requestInfo(requestInfo).mdms(createMdmsData).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getCreateEndPoint()).append(RATES_SCHEMA_CODE);
        Object response = restRepo.fetchResult(uri, mdmsRequest);
        MdmsResponseV2 mdmmsresponse = mapper.convertValue(response, MdmsResponseV2.class);
        rates.setId(mdmmsresponse.getMdms().get(0).getId());
    }

    List<Mdms> fetchOldMdms(Map<String, Rates> oldRatesMap, RequestInfo requestInfo, String tenantId) {
        Set<String> uniqueIdentifiers = oldRatesMap.values().stream().filter(rates -> rates != null).map(rates -> rates.getSorId()+"."+rates.getValidFrom())
                .collect(java.util.stream.Collectors.toSet());
        if (CollectionUtils.isEmpty(uniqueIdentifiers))
            return new ArrayList<>();
        MdmsCriteriaV2 mdmsCriteriaV2 = MdmsCriteriaV2.builder()
                .schemaCode(RATES_SCHEMA_CODE).tenantId(tenantId).uniqueIdentifiers(uniqueIdentifiers).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getMdmsV2EndPoint());
        MdmsCriteriaReqV2 mdmsCriteriaReqV2 = MdmsCriteriaReqV2.builder()
                .mdmsCriteria(mdmsCriteriaV2).requestInfo(requestInfo).build();
        Object response = restRepo.fetchResult(uri, mdmsCriteriaReqV2);
        MdmsResponseV2 mdmsResponseV2 = mapper.convertValue(response, MdmsResponseV2.class);


        return mdmsResponseV2.getMdms();
    }
    private void updateRates(Mdms mdms, RequestInfo requestInfo) {
        MdmsRequest mdmsRequest = MdmsRequest.builder()
                .mdms(mdms).requestInfo(requestInfo).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getUpdateEndPoint()).append(RATES_SCHEMA_CODE);

        Object response = restRepo.fetchResult(uri, mdmsRequest);
    }


    public void updateMdmsDataForRatesAndComposition(MdmsRequest mdmsRequest, Mdms previousRates) {
        log.info("RateAnalysisService: updateMdmsDataForRatesAndComposition");
        Mdms currentMdms = mdmsRequest.getMdms();
        String schemaCode = currentMdms.getSchemaCode();
        JsonNode currentData = currentMdms.getData();
        JsonNode previousData = previousRates.getData();
        Long currentValidFrom = currentData.get(schemaCode.equals(configs.getRatesSchemaCode())? "validFrom": "effectiveFrom").asLong();
        Long previousValidFrom = previousData.get(schemaCode.equals(configs.getRatesSchemaCode())? "validFrom": "effectiveFrom").asLong();
        if(previousValidFrom.equals(currentValidFrom) || currentValidFrom < previousValidFrom){
            previousRates.setIsActive(false);
        }else{
            LocalDateTime currentDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(currentValidFrom), ZoneId.systemDefault());
            LocalDateTime endOfPreviousDay = currentDateTime.toLocalDate().minusDays(1).atTime(LocalTime.of(23, 59, 59));
            long endOfPreviousDayEpochTime = endOfPreviousDay.toEpochSecond(ZoneOffset.UTC);
            previousData = ((ObjectNode) previousData).put(schemaCode.equals(configs.getRatesSchemaCode())? "validTo": "effectiveTo", Long.toString(endOfPreviousDayEpochTime));
            previousRates.setData(previousData);
        }
        MdmsRequest updateMdmsRequest = MdmsRequest.builder()
                .requestInfo(mdmsRequest.getRequestInfo())
                .mdms(previousRates)
                .build();
        mdmsUtil.updateMdmsData(updateMdmsRequest);
    }
}