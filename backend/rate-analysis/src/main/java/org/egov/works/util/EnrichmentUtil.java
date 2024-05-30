package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class EnrichmentUtil {

    public RateAnalysis createRateAnalysis(AnalysisRequest analysisRequest,
                                           String sorCode, SorComposition sorComposition) {
        return RateAnalysis.builder()
                .id(UUID.randomUUID().toString())
                .sorCode(sorCode)
                .sorType(sorComposition.getSorType())
                .analysisQuantity(sorComposition.getQuantity())
                .tenantId(analysisRequest.getSorDetails().getTenantId())
                .effectiveFrom(analysisRequest.getSorDetails().getEffectiveFrom())
                .build();
    }

    public LineItem createLineItem(BasicSorDetail basicSorDetail, Rates rates) {
        //TODO add sor details in rates
        return LineItem.builder()
                .id(UUID.randomUUID().toString())
                .targetId(basicSorDetail.getSorId())
                .amountDetails(rates.getAmountDetails())
                .build();
    }

}
