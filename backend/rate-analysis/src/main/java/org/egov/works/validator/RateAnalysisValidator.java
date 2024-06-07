package org.egov.works.validator;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.AnalysisRequest;
import org.egov.works.web.models.Rates;
import org.egov.works.web.models.SorComposition;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RateAnalysisValidator {

    public void validateRevisionOfRates(AnalysisRequest analysisRequest, Map<String, SorComposition> sorIdCompositionMap,
                                        Map<String, List<Rates>> basicRatesMap, Map<String, JsonNode> sorMap) {
        Map<String, String> errorMap = new HashMap<>();
//        for (String sorId : sorMap.keySet()) {
//            if (sorMap.get(sorId).get("sorType").asText().equals("W") ) {
//                errorMap.put("BASIC_SOR_ONLY", sorId + " is not basic Sor");
//            }
//        }

        if (!CollectionUtils.isEmpty(errorMap))
            throw new CustomException(errorMap);

    }
    public void validateNewRates(Map<String, Rates> oldRatesMap, List<Rates> newRates) {
//        Map<String, String> errorMap = new HashMap<>();
        for (Rates rates : newRates) {
            if (!oldRatesMap.containsKey(rates.getSorId()) || oldRatesMap.get(rates.getSorId()) == null) {
                log.info("Previous rates not found for sorId " + rates.getSorId());
            } else {
                Rates oldRate = oldRatesMap.get(rates.getSorId());
                if (oldRate.getValidFrom().compareTo(rates.getValidFrom()) > 0) {
                    log.error("Effective from date cannot be less than previous effective from date");
                    newRates.remove(rates);
                    continue;
//                    errorMap.put("EFFECTIVE_FROM_ERROR", "Effective from date cannot be less than previous effective from date for sorId :: " + rates.getSorId());
                }
                if (oldRate.getRate().equals(rates.getRate())) {
                    log.error("Previous rate same as new rate");
                    newRates.remove(rates);
//                    errorMap.put("RATE_SAME", "Previous rate same as new rate for sorId :: " + rates.getSorId());
                }
            }
        }
//        if (!CollectionUtils.isEmpty(errorMap))
//            throw new CustomException(errorMap);
    }



}
