package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.measurement.Measure;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CalculatorService {
    private final EnrichmentUtil enrichmentUtil;

    public CalculatorService(EnrichmentUtil enrichmentUtil) {
        this.enrichmentUtil = enrichmentUtil;
    }

    public void calculateUtilizationForWorksSor(Map<String, SorComposition>  sorIdToCompositionMap,
                                                    EstimateDetail estimateDetail, Map<String, List<Rates>> basicSorRateMap,
                                                    Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                                    Measure measure, Map<String, Sor> sorIdToSorMap) {
        if (sorIdToCompositionMap.get(estimateDetail.getSorId()) != null) {
            List<SorCompositionBasicSorDetail> sorCompositionBasicSorDetails = sorIdToCompositionMap.get(estimateDetail.getSorId()).getBasicSorDetails();
            for (SorCompositionBasicSorDetail sorCompositionBasicSorDetail : sorCompositionBasicSorDetails) {
                if (basicSorRateMap.containsKey(sorCompositionBasicSorDetail.getSorId())) {
                    Rates rates = basicSorRateMap.get(sorCompositionBasicSorDetail.getSorId()).get(0); //TODO fetch correct rates
                    BigDecimal quantity = measure.getCumulativeValue().multiply(sorCompositionBasicSorDetail.getQuantity())
                            .divide(sorIdToCompositionMap.get(estimateDetail.getSorId()).getQuantity())
                            .divide(sorIdToSorMap.get(sorCompositionBasicSorDetail.getSorId()).getQuantity());
                    calculateAmount(rates, typeToBasicSorDetailsMap, sorIdToSorMap.get(sorCompositionBasicSorDetail.getSorId()), quantity, estimateDetail.getIsDeduction());
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
                                                String sorId) {
        if (basicSorRateMap.containsKey(sorId)) {
            Rates rates = basicSorRateMap.get(sorId).get(0); //TODO fetch correct rates
            BigDecimal quantity = measure.getCumulativeValue().divide(sor.getQuantity());
            calculateAmount(rates, typeToBasicSorDetailsMap, sor, quantity, estimateDetail.getIsDeduction());
        } else {
            log.error("No rates found for basicSorId : " + sorId);
            throw new CustomException("NO_RATES_FOUND_FOR_BASIC_SOR", "No rates found for basicSorId : " + sorId);
        }

    }

    public void calculateUtilizationForBasicSor1(boolean isDeduction, Map<String, List<Rates>> basicSorRateMap,
                                                 Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                                 BigDecimal quantity) {
        if (basicSorRateMap.containsKey(sor.getId())) {
            Rates rates = basicSorRateMap.get(sor.getId()).get(0);
            calculateAmount(rates, typeToBasicSorDetailsMap, sor, quantity, isDeduction);
        }
    }

//    private void calculateAmount1(Rates rates, Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,)

    private void calculateAmount(Rates rates, Map<String, BasicSorDetails> typeToBasicSorDetailsMap, Sor sor,
                                 BigDecimal quantity, boolean isDeduction) {

        if (typeToBasicSorDetailsMap.containsKey(sor.getSorType())) {
            BigDecimal currentAmount = typeToBasicSorDetailsMap.get(sor.getSorType()).getAmount();
            if (isDeduction) {
                currentAmount = currentAmount.subtract(rates.getRate().multiply(quantity));// TODO divide by sor quantity
            } else {
                currentAmount = currentAmount.add(rates.getRate().multiply(quantity));
            }
            typeToBasicSorDetailsMap.get(sor.getSorType()).setAmount(currentAmount);
        } else {
            BasicSorDetails basicSorDetails = enrichmentUtil.getEnrichedBasicSorDetails(sor.getSorType());
            if (isDeduction) {
                basicSorDetails.setAmount(rates.getRate().multiply(quantity).multiply(BigDecimal.valueOf(-1)));
            } else {
                basicSorDetails.setAmount(rates.getRate().multiply(quantity));
            }
            basicSorDetails.setAmount(rates.getRate().multiply(quantity));
            typeToBasicSorDetailsMap.put(sor.getSorType(), basicSorDetails);
        }
    }

}
