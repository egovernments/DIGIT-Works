package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RateAnalysisService {

    private final MdmsUtil mdmsUtil;
    private final CalculatorService calculatorService;

    public RateAnalysisService(MdmsUtil mdmsUtil, CalculatorService calculatorService) {
        this.mdmsUtil = mdmsUtil;
        this.calculatorService = calculatorService;
    }

    public RateAnalysisResponse calculateRate(AnalysisRequest analysisRequest) {
        Map<String, SorComposition> sorIdCompositionMap = mdmsUtil.fetchSorComposition(analysisRequest);
        Map<String, List<Rates>> basicRatesMap = mdmsUtil.fetchBasicRates(analysisRequest, sorIdCompositionMap);
        List<RateAnalysis> rateAnalysis = calculatorService.calculateRateAnalysis(analysisRequest, sorIdCompositionMap, basicRatesMap);
        RateAnalysisResponse rateAnalysisResponse = RateAnalysisResponse.builder()
                .rateAnalysis(rateAnalysis).responseInfo(new ResponseInfo()).build();
        return rateAnalysisResponse;
    }

}
