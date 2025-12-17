package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.util.MdmsUtil;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.validator.RateAnalysisValidator;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RateAnalysisService {

    private final MdmsUtil mdmsUtil;
    private final CalculatorService calculatorService;
    private final EnrichmentService enrichmentService;
    private final RateAnalysisValidator  rateAnalysisValidator;
    private final ResponseInfoFactory responseInfoFactory;
    private final EnrichmentUtil enrichmentUtil;
    private final MdmsService mdmsService;


    public RateAnalysisService(MdmsUtil mdmsUtil, CalculatorService calculatorService, EnrichmentService enrichmentService, RateAnalysisValidator rateAnalysisValidator, ResponseInfoFactory responseInfoFactory, EnrichmentUtil enrichmentUtil, MdmsService mdmsService) {
        this.mdmsUtil = mdmsUtil;
        this.calculatorService = calculatorService;
        this.enrichmentService = enrichmentService;
        this.rateAnalysisValidator = rateAnalysisValidator;
        this.responseInfoFactory = responseInfoFactory;
        this.enrichmentUtil = enrichmentUtil;
        this.mdmsService = mdmsService;
    }

    public RateAnalysisResponse calculateRate(AnalysisRequest analysisRequest) {
        log.info("Calculate rate request");
        //Removed tenant id validation done from mdms v2 for mukta
        //rateAnalysisValidator.validateTenantId(analysisRequest.getSorDetails().getTenantId(), analysisRequest.getRequestInfo());
        Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(analysisRequest);
        Map<String, List<Rates>> basicRatesMap = mdmsUtil.fetchBasicRates(analysisRequest, sorIdCompositionMap);
        Map<String, JsonNode> sorMap = mdmsUtil.fetchSor(analysisRequest, sorIdCompositionMap);
        List<RateAnalysis> rateAnalysis = calculatorService.calculateRateAnalysis(analysisRequest, sorIdCompositionMap,
                basicRatesMap, sorMap, false);
        enrichmentUtil.enrichRateAnalysis(rateAnalysis, sorMap, sorIdCompositionMap);
        RateAnalysisResponse rateAnalysisResponse = RateAnalysisResponse.builder()
                .rateAnalysis(rateAnalysis)
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(analysisRequest.getRequestInfo(), true))
                .build();
        log.info("Calculate rate request finished");
        return rateAnalysisResponse;
    }

    public List<Rates> createRateAnalysis(AnalysisRequest analysisRequest) {
        log.info("Create rate request");
        //rateAnalysisValidator.validateTenantId(analysisRequest.getSorDetails().getTenantId(), analysisRequest.getRequestInfo());
        Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(analysisRequest);
        Map<String, List<Rates>> basicRatesMap = mdmsUtil.fetchBasicRates(analysisRequest, sorIdCompositionMap);
        Map<String, JsonNode> sorMap = mdmsUtil.fetchSor(analysisRequest, sorIdCompositionMap);
        rateAnalysisValidator.validateRevisionOfRates(analysisRequest, sorIdCompositionMap, basicRatesMap, sorMap);
        List<RateAnalysis> rateAnalysis = calculatorService.calculateRateAnalysis(analysisRequest, sorIdCompositionMap,
                basicRatesMap, sorMap, true);

        List<Rates> calculatedRates = enrichmentService.enrichRates(rateAnalysis);
        Map<String, Rates> worksRatesMap = mdmsUtil.fetchWorksRates(analysisRequest);
        rateAnalysisValidator.validateNewRates(worksRatesMap, calculatedRates);
        mdmsService.createRevisedRates(calculatedRates, worksRatesMap, analysisRequest.getRequestInfo());
        log.info("Create rate request finished");
        return calculatedRates;
    }

    public void updateMdmsDataForRatesAndComposition(MdmsRequest mdmsRequest) {
        log.info("RateAnalysisService: updateMdmsDataForRatesAndComposition");
        Mdms previousRates = fetchPreviousSorRates(mdmsRequest);
        if(previousRates != null){
            mdmsService.updateMdmsDataForRatesAndComposition(mdmsRequest, previousRates);
        }
    }

    public Mdms fetchPreviousSorRates(MdmsRequest mdmsRequest){
        log.info("RateAnalysisService: fetchPreviousSorRates");
        Mdms currentMdms = mdmsRequest.getMdms();
        JsonNode data = currentMdms.getData();
        String sorId = data.get("sorId").asText();
        Map<String, String> sorIdFilterMap = new HashMap<>();
        sorIdFilterMap.put("sorId", sorId);
        MdmsCriteriaV2 mdmsCriteriaV2 = MdmsCriteriaV2.builder()
                .schemaCode(currentMdms.getSchemaCode())
                .tenantId(currentMdms.getTenantId())
                .isActive(true)
                .filterMap(sorIdFilterMap)
                .build();
        MdmsSearchCriteriaV2 mdmsSearchCriteriaV2 = MdmsSearchCriteriaV2.builder()
                .mdmsCriteria(mdmsCriteriaV2)
                .requestInfo(mdmsRequest.getRequestInfo())
                .build();
        MdmsResponseV2 mdmsResponseV2 = mdmsUtil.fetchSorsFromMdms(mdmsSearchCriteriaV2);
        List<Mdms> mdmsList = mdmsResponseV2.getMdms();
        if(mdmsList != null && !mdmsList.isEmpty()){
            for(Mdms mdms1: mdmsList){
                if(Objects.equals(mdms1.getId(), currentMdms.getId())){
                    continue;
                }else{
                    return mdms1;
                }
            }
        }
        return null;
    }
}