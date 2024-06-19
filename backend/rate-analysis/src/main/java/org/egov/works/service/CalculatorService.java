package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.util.CommonUtil;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.works.web.models.LineItem.TypeEnum.EXTRACHARGES;

@Service
@Slf4j
public class CalculatorService {

    private final EnrichmentUtil enrichmentUtil;
    private final ObjectMapper mapper;
    private final CommonUtil commonUtil;

    public CalculatorService(EnrichmentUtil enrichmentUtil, ObjectMapper mapper, CommonUtil commonUtil) {
        this.enrichmentUtil = enrichmentUtil;
        this.mapper = mapper;
        this.commonUtil = commonUtil;
    }

    public List<RateAnalysis> calculateRateAnalysis(AnalysisRequest analysisRequest,
                                                    Map<String, SorComposition> sorIdCompositionMap,
                                                    Map<String, List<Rates>> basicRatesMap,
                                                    Map<String, JsonNode> sorMap,
                                                    boolean isCreate) {
        List<RateAnalysis> rateAnalysisList = new ArrayList<>();
        for(String worksSorId : analysisRequest.getSorDetails().getSorCodes()) {
            SorComposition sorComposition = sorIdCompositionMap.get(worksSorId);
            RateAnalysis rateAnalysis = enrichmentUtil.createRateAnalysis(analysisRequest, worksSorId, sorComposition);
            List<LineItem> lineItems = new ArrayList<>();
            for(BasicSorDetail basicSorDetail : sorComposition.getBasicSorDetails()) {

                List<Rates> ratesList = basicRatesMap.get(basicSorDetail.getSorId());
                Rates rate = null;
                LineItem lineItem;
                if (!CollectionUtils.isEmpty(ratesList))
                    rate = commonUtil.getApplicatbleRate(ratesList, Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom()));

                if (rate == null) {
                    if (isCreate)
                        throw new CustomException("NO_RATES_FOUND", "Rate not found for the sor id :: " + basicSorDetail.getSorId());
                    lineItem = enrichmentUtil.createLineItem(basicSorDetail, rate, sorMap.get(basicSorDetail.getSorId()));
                    Map<String, Object> additonalDetailsMap = mapper.convertValue(lineItem.getAdditionalDetails(), Map.class);
                    additonalDetailsMap.put("definedQuantity", basicSorDetail.getQuantity());
                    lineItem.setAdditionalDetails(additonalDetailsMap);

                } else {
                    rate = commonUtil.getApplicatbleRate(ratesList, Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom()));
                    lineItem = enrichmentUtil.createLineItem(basicSorDetail, rate, sorMap.get(basicSorDetail.getSorId()));
                    for (AmountDetail amountDetail : lineItem.getAmountDetails()) {
                        BigDecimal sorQuantity = sorMap.get(basicSorDetail.getSorId()).get("quantity").decimalValue();
                        if (isCreate) {
                            amountDetail.setAmount(amountDetail.getAmount().multiply(basicSorDetail.getPerUnitQty()));
                        } else {
                            amountDetail.setAmount(amountDetail.getAmount().multiply(basicSorDetail.getQuantity())
                                    .divide(sorQuantity, 4, RoundingMode.HALF_UP));
                        }

                    }
                    Map<String, Object> additonalDetailsMap = mapper.convertValue(lineItem.getAdditionalDetails(), Map.class);
                    additonalDetailsMap.put("definedQuantity", basicSorDetail.getQuantity());
                    additonalDetailsMap.put("rate", rate);

                    lineItem.setAdditionalDetails(additonalDetailsMap);
                }

                lineItems.add(lineItem);
            }
            LineItem additionalChargesLineItem = new LineItem();
            additionalChargesLineItem.setType(EXTRACHARGES);
            additionalChargesLineItem.setAmountDetails(new ArrayList<>());
            Map<String, List<String>> stringListMap = new HashMap<>();
            for (AdditionalCharges additionalCharges : sorComposition.getAdditionalCharges()) {

                AmountDetail amountDetail = AmountDetail.builder()
                        .type(additionalCharges.getCalculationType())
                        .heads(additionalCharges.getApplicableOn())
                        .amount(additionalCharges.getFigure())
                        .build();
                additionalChargesLineItem.getAmountDetails().add(amountDetail);
                if (stringListMap.containsKey(additionalCharges.getApplicableOn())) {
                    stringListMap.get(additionalCharges.getApplicableOn()).add(additionalCharges.getDescription());

                } else {
                    List<String> descriptions = new ArrayList<>();
                    descriptions.add(additionalCharges.getDescription());
                    stringListMap.put(additionalCharges.getApplicableOn(), descriptions);
                }

            }
            additionalChargesLineItem.setAdditionalDetails(stringListMap);

            lineItems.add(additionalChargesLineItem);
            rateAnalysis.setLineItems(lineItems);
            rateAnalysisList.add(rateAnalysis);
        }

        return rateAnalysisList;
    }

}
