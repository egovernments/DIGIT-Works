package org.egov.works.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.egov.works.config.ServiceConstants.*;

@Component
@Slf4j
public class EnrichmentUtil {

    public RateAnalysis createRateAnalysis(AnalysisRequest analysisRequest,
                                           String sorCode, SorComposition sorComposition) {
        return RateAnalysis.builder()
                .id(UUID.randomUUID().toString())
                .compositionId(sorComposition.getCompositionId())
                .sorCode(sorCode)
                .sorType(sorComposition.getSorType())
                .analysisQuantity(sorComposition.getQuantity())
                .tenantId(analysisRequest.getSorDetails().getTenantId())
                .effectiveFrom(analysisRequest.getSorDetails().getEffectiveFrom())
                .build();
    }

    public LineItem createLineItem(BasicSorDetail basicSorDetail, Rates rates, JsonNode sorDetails) {
        return LineItem.builder()
                .id(UUID.randomUUID().toString())
                .type(LineItem.TypeEnum.SOR)
                .targetId(basicSorDetail.getSorId())
                .amountDetails(rates != null ? enrichAmountDetails(rates.getAmountDetails()) : null)
                .additionalDetails(sorDetails)
                .build();
    }
    public void enrichRates(Rates rates, RateAnalysis rateAnalysis, String validFrom) {
        rates.setSorId(rateAnalysis.getSorCode());
        rates.setValidFrom(String.valueOf(validFrom));
        rates.setCompositionId(rateAnalysis.getCompositionId());
//        rates.setValidTo(NAN_KEY);
        rates.setTenantId(rateAnalysis.getTenantId());
    }

    public void enrichRateAnalysis(List<RateAnalysis> rateAnalysisList, Map<String, JsonNode> sorMap) {
        for (RateAnalysis rateAnalysis : rateAnalysisList) {
            JsonNode worksSor = sorMap.get(rateAnalysis.getSorCode());
            rateAnalysis.setSorSubType(worksSor.get(SOR_SUB_TYPE).asText());
            rateAnalysis.setSorVariant(worksSor.get(SOR_VARIANT).asText());
            rateAnalysis.setSorType(worksSor.get(SOR_TYPE).asText());
            rateAnalysis.setUom(worksSor.get(UOM).asText());
            rateAnalysis.setDescription(worksSor.get(DESCRIPTION_KEY).asText());
            rateAnalysis.setQuantity(BigDecimal.valueOf(worksSor.get(QUANTITY).asDouble()));
            rateAnalysis.setStatus(RateAnalysis.StatusEnum.ACTIVE);
        }
    }

    private List<AmountDetail> enrichAmountDetails(List<AmountDetail> amountDetails) {
        List<AmountDetail> finalAmountDetails = new ArrayList<>();
        for (AmountDetail amountDetail : amountDetails) {
            AmountDetail newAmountDetail = AmountDetail.builder()
                    .amount(amountDetail.getAmount())
                    .type(amountDetail.getType())
                    .heads(amountDetail.getHeads())
                    .build();
            finalAmountDetails.add(newAmountDetail);
        }

        return finalAmountDetails;
    }

}
