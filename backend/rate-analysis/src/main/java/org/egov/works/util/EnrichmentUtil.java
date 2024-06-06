package org.egov.works.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
                .amountDetails(rates.getAmountDetails())
                .additionalDetails(sorDetails)
                .build();
    }
    public void enrichRates(Rates rates, String sorId, String validFrom) {
        rates.setSorId(sorId);
        rates.setValidFrom(String.valueOf(validFrom));

    }

}
