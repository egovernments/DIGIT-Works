package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.egov.works.web.models.LineItem.TypeEnum.EXTRACHARGES;

@Service
@Slf4j
public class CalculatorService {

    private final EnrichmentUtil enrichmentUtil;

    public CalculatorService(EnrichmentUtil enrichmentUtil) {
        this.enrichmentUtil = enrichmentUtil;
    }

    public List<RateAnalysis> calculateRateAnalysis(AnalysisRequest analysisRequest,
                                                    Map<String, SorComposition> sorIdCompositionMap,
                                                    Map<String, List<Rates>> basicRatesMap, Map<String, JsonNode> sorMap) {
        List<RateAnalysis> rateAnalysisList = new ArrayList<>();
        for(String worksSorId : analysisRequest.getSorDetails().getSorCodes()) {
            SorComposition sorComposition = sorIdCompositionMap.get(worksSorId);
            RateAnalysis rateAnalysis = enrichmentUtil.createRateAnalysis(analysisRequest, worksSorId, sorComposition);
            List<LineItem> lineItems = new ArrayList<>();
            for(BasicSorDetail basicSorDetail : sorComposition.getBasicSorDetails()) {

                List<Rates> ratesList = basicRatesMap.get(basicSorDetail.getSorId());
                //TODO sort and get rates
                Rates rate = ratesList.get(0);

                LineItem lineItem = enrichmentUtil.createLineItem(basicSorDetail, rate, sorMap.get(basicSorDetail.getSorId()));
                for (AmountDetail amountDetail : lineItem.getAmountDetails()) {
                    amountDetail.setAmount(amountDetail.getAmount().multiply(basicSorDetail.getQuantity()));
                }
                lineItems.add(lineItem);
            }
            for (AdditionalCharges additionalCharges : sorComposition.getAdditionalCharges()) {
                LineItem lineItem  = LineItem.builder()
                        .id(UUID.randomUUID().toString())
                        .targetId(additionalCharges.getApplicableOn())
                        .type(EXTRACHARGES)
                        .amountDetails(Collections.singletonList(AmountDetail.builder().type(additionalCharges.getCalculationType())
                                .amount(additionalCharges.getFigure())
                                .build()))
                        .build();
                lineItems.add(lineItem);
            }
            rateAnalysis.setLineItems(lineItems);
            rateAnalysisList.add(rateAnalysis);
        }

        return rateAnalysisList;
    }

}
