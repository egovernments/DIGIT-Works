package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.measurement.Measure;
import org.egov.works.util.CommonUtil;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.*;
import  org.egov.works.services.common.models.estimate.EstimateDetail;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static org.egov.works.config.ErrorConfiguration.NO_RATES_FOUND_FOR_BASIC_SOR_KEY;
import static org.egov.works.config.ErrorConfiguration.NO_RATES_FOUND_FOR_BASIC_SOR_MSG;

@Service
@Slf4j
public class CalculatorService {
    private final EnrichmentUtil enrichmentUtil;
    private final CommonUtil commonUtil;

    public CalculatorService(EnrichmentUtil enrichmentUtil, CommonUtil commonUtil) {
        this.enrichmentUtil = enrichmentUtil;
        this.commonUtil = commonUtil;
    }

    public void calculateUtilizationForWorksSor(Map<String, SorComposition>  sorIdToCompositionMap,
                                               EstimateDetail estimateDetail, Map<String, List<Rates>> basicSorRateMap,
                                                Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                                Measure measure, Map<String, Sor> sorIdToSorMap,
                                                Map<String, BigDecimal> sorIdToCummValueMap, Long timeForEstimateSubmission) {
        if (sorIdToCompositionMap.get(estimateDetail.getSorId()) != null) {
            List<SorCompositionBasicSorDetail> sorCompositionBasicSorDetails = sorIdToCompositionMap.get(estimateDetail.getSorId()).getBasicSorDetails();
            for (SorCompositionBasicSorDetail sorCompositionBasicSorDetail : sorCompositionBasicSorDetails) {
                if (basicSorRateMap.containsKey(sorCompositionBasicSorDetail.getSorId())) {

                    Rates rates = commonUtil.getApplicatbleRate(basicSorRateMap.get(sorCompositionBasicSorDetail.getSorId()), timeForEstimateSubmission);
                    BigDecimal quantity = sorIdToCummValueMap.get(estimateDetail.getSorId()).multiply(sorCompositionBasicSorDetail.getQuantity())
                            .divide(sorIdToCompositionMap.get(estimateDetail.getSorId()).getQuantity())
                            .divide(sorIdToSorMap.get(sorCompositionBasicSorDetail.getSorId()).getQuantity());
                    calculateAmount(rates, typeToBasicSorDetailsMap, sorIdToSorMap.get(sorCompositionBasicSorDetail.getSorId()), quantity);
                } else {
                    log.error("No rates found for basicSorId : " + sorCompositionBasicSorDetail.getSorId());
                }
            }
        } else {
            log.error("No SOR Composition found for sorId : " + estimateDetail.getSorId());
        }
    }

    public void calculateUtilizationForBasicSor(EstimateDetail estimateDetail, Map<String, List<Rates>> basicSorRateMap,
                                                Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor, Measure measure,
                                                String sorId, Map<String, BigDecimal> sorIdToCummValueMap, Long timeForEstimateSubmission) {
        if (basicSorRateMap.containsKey(sorId)) {

            Rates rates = commonUtil.getApplicatbleRate(basicSorRateMap.get(sorId), timeForEstimateSubmission);
            BigDecimal quantity = sorIdToCummValueMap.get(sorId).divide(sor.getQuantity());
            calculateAmount(rates, typeToBasicSorDetailsMap, sor, quantity);
        } else {
            log.error("No rates found for basicSorId : " + sorId);
            throw new CustomException(NO_RATES_FOUND_FOR_BASIC_SOR_KEY, NO_RATES_FOUND_FOR_BASIC_SOR_MSG + sorId);
        }

    }

    public void calculateUtilizationForBasicSor1(boolean isDeduction, Rates rates,
                                                 Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                                 BigDecimal quantity) {

        calculateAmount(rates, typeToBasicSorDetailsMap, sor, quantity);
    }

//    private void calculateAmount1(Rates rates, Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,)

    private void calculateAmount(Rates rates, Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                 BigDecimal quantity) {

        quantity.setScale(4, BigDecimal.ROUND_HALF_UP);
        if (typeToBasicSorDetailsMap.containsKey(sor.getSorType())) {
            BigDecimal currentAmount = typeToBasicSorDetailsMap.get(sor.getSorType()).getAmount();
            currentAmount = currentAmount.add(rates.getRate().multiply(quantity)).setScale(2, RoundingMode.HALF_UP);
            typeToBasicSorDetailsMap.get(sor.getSorType()).setAmount(currentAmount);
            typeToBasicSorDetailsMap.get(sor.getSorType()).setQuantity(typeToBasicSorDetailsMap.get(sor.getSorType()).getQuantity().add(quantity));
        } else {
            BasicSorDetails basicSorDetails = enrichmentUtil.getEnrichedBasicSorDetails(sor.getSorType());
            BigDecimal currentAmount = rates.getRate().multiply(quantity).setScale(2, RoundingMode.HALF_UP);
            basicSorDetails.setAmount(currentAmount);
            basicSorDetails.setQuantity(quantity);
            typeToBasicSorDetailsMap.put(sor.getSorType(), basicSorDetails);
        }
    }

}
