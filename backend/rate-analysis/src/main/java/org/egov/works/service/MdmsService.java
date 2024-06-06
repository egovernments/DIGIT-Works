package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.Configuration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MdmsService {

    private final ObjectMapper mapper;
    private final ServiceRequestRepository restRepo;
    private final Configuration configs;

    public MdmsService(ObjectMapper mapper, ServiceRequestRepository restRepo, Configuration configs) {

        this.mapper = mapper;
        this.restRepo = restRepo;
        this.configs = configs;
    }

    public void createRevisedRates(List<Rates> revisedRates, Map<String, List<Rates>> oldRatesMap, RequestInfo requestInfo) {
        List<Mdms> mdmsData = fetchOldMdms(revisedRates, requestInfo);
        Map<String, List<Mdms>> mdmsMap = mdmsData.stream()
                .collect(Collectors.groupingBy(mdms -> mdms.getUniqueIdentifier().split("\\.")[0]));
        for (Rates rates : revisedRates) {
            createRates(rates, requestInfo);
            if (oldRatesMap.containsKey(rates.getSorId())) {
                Mdms mdms = mdmsMap.get(rates.getSorId()).get(0);
//                if (mdms.getData() instanceof ObjectNode) {
                    ((ObjectNode) mdms.getData()).replace("validTo", mapper.valueToTree(rates.getValidFrom()));
                    updateRates(mdms, requestInfo);
                    log.info("Previous rates updated succesfully");
//                }

            }
        }
    }

    private void createRates(Rates rates, RequestInfo requestInfo) {
        log.info("Creating rates for sorId " + rates.getSorId());

        Mdms createMdmsData = Mdms.builder()
                .tenantId(rates.getTenantId())
                .schemaCode("WORKS-SOR.RATES")
                .data(mapper.valueToTree(rates)).build();
        MdmsRequest mdmsRequest = MdmsRequest.builder()
                .requestInfo(requestInfo).mdms(createMdmsData).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getCreateEndPoint()).append("WORKS-SOR.RATES");
        Object response = restRepo.fetchResult(uri, mdmsRequest);
    }

    List<Mdms> fetchOldMdms(List<Rates> revisedRates, RequestInfo requestInfo) {
        Set<String> uniqueIdentifiers = revisedRates.stream().map(rates -> rates.getSorId()+"."+rates.getValidFrom())
                .collect(java.util.stream.Collectors.toSet());
        MdmsCriteriaV2 mdmsCriteriaV2 = MdmsCriteriaV2.builder()
                .schemaCode("WORKS-SOR.RATES").uniqueIdentifiers(uniqueIdentifiers).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getMdmsV2EndPoint());
        Object response = restRepo.fetchResult(uri, mdmsCriteriaV2);
        MdmsResponseV2 mdmsResponseV2 = mapper.convertValue(response, MdmsResponseV2.class);


        return mdmsResponseV2.getMdms();
    }
    private void updateRates(Mdms mdms, RequestInfo requestInfo) {
        MdmsRequest mdmsRequest = MdmsRequest.builder()
                .mdms(mdms).requestInfo(requestInfo).build();
        StringBuilder uri = new StringBuilder(configs.getMdmsHost()).append(configs.getUpdateEndPoint()).append("WORKS-SOR.RATES");

        Object response = restRepo.fetchResult(uri, mdmsRequest);
    }






}
