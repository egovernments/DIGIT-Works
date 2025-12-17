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

import static org.egov.works.config.ErrorConfiguration.NO_RATES_FOUND_KEY;
import static org.egov.works.config.ErrorConfiguration.NO_RATES_FOUND_MSG;
import static org.egov.works.config.ServiceConstants.*;
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

    /**
     * Calculates the rate analysis for a given analysis request.
     *
     * @param analysisRequest The analysis request containing the details of the SOR codes.
     * @param sorIdCompositionMap The map of SOR IDs to their compositions.
     * @param basicRatesMap The map of SOR IDs to their corresponding basic rates.
     * @param sorMap The map of SOR IDs to their details.
     * @param isCreate Flag indicating whether this is a create operation.
     * @return The list of rate analyses.
     * @throws CustomException If no rates are found for a basic SOR detail.
     */
    public List<RateAnalysis> calculateRateAnalysis(AnalysisRequest analysisRequest,
                                                    Map<String, SorComposition> sorIdCompositionMap,
                                                    Map<String, List<Rates>> basicRatesMap,
                                                    Map<String, JsonNode> sorMap,
                                                    boolean isCreate) {
        List<RateAnalysis> rateAnalysisList = new ArrayList<>();
        // Iterate over each SOR code in the analysis request
        for(String worksSorId : analysisRequest.getSorDetails().getSorCodes()) {
            SorComposition sorComposition = sorIdCompositionMap.get(worksSorId);
            // Create a rate analysis for the SOR code
            RateAnalysis rateAnalysis = enrichmentUtil.createRateAnalysis(analysisRequest, worksSorId, sorComposition);
            List<LineItem> lineItems = new ArrayList<>();
            // Iterate over each basic SOR detail in the SOR composition
            for(BasicSorDetail basicSorDetail : sorComposition.getBasicSorDetails()) {
                // Get the rates list for the basic SOR detail
                List<Rates> ratesList = basicRatesMap.get(basicSorDetail.getSorId());
                Rates rate = null;
                LineItem lineItem;
                // Check if the rates list is not empty
                if (!CollectionUtils.isEmpty(ratesList)) {
                    // Get the applicable rate for the analysis request effective from date
                    rate = commonUtil.getApplicatbleRate(ratesList, Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom()));
                }

                if (rate == null) {
                    // Throw an exception if this is a create operation
                    if (isCreate)
                        throw new CustomException(NO_RATES_FOUND_KEY, NO_RATES_FOUND_MSG + basicSorDetail.getSorId());
                    // Create a line item with null rate and additional details
                    lineItem = enrichmentUtil.createLineItem(basicSorDetail, rate, sorMap.get(basicSorDetail.getSorId()));
                    Map<String, Object> additonalDetailsMap = mapper.convertValue(lineItem.getAdditionalDetails(), Map.class);
                    additonalDetailsMap.put(DEFINED_QUANTITY, basicSorDetail.getQuantity());
                    lineItem.setAdditionalDetails(additonalDetailsMap);

                } else {
//                    rate = commonUtil.getApplicatbleRate(ratesList, Long.parseLong(analysisRequest.getSorDetails().getEffectiveFrom()));
                    // Create a line item with the rate and additional details
                    lineItem = enrichmentUtil.createLineItem(basicSorDetail, rate, sorMap.get(basicSorDetail.getSorId()));
                    // Update the amount details based on the quantity and SOR details
                    for (AmountDetail amountDetail : lineItem.getAmountDetails()) {
                        BigDecimal sorQuantity = sorMap.get(basicSorDetail.getSorId()).get(QUANTITY).decimalValue();
                        if (isCreate) {
                            amountDetail.setAmount(amountDetail.getAmount().multiply(basicSorDetail
                                    .getQuantity()).divide(sorComposition.getQuantity(), 2, RoundingMode.HALF_UP)
                                    .divide(sorQuantity, 2, RoundingMode.HALF_UP));
                        } else {
                            amountDetail.setAmount(amountDetail.getAmount().multiply(basicSorDetail.getQuantity())
                                    .divide(sorQuantity, 2, RoundingMode.HALF_UP));
                        }

                    }
                    // Update the additional details with the defined quantity and rate
                    Map<String, Object> additonalDetailsMap = mapper.convertValue(lineItem.getAdditionalDetails(), Map.class);
                    additonalDetailsMap.put(DEFINED_QUANTITY, basicSorDetail.getQuantity());
                    additonalDetailsMap.put(RATE_KEY, rate);

                    lineItem.setAdditionalDetails(additonalDetailsMap);
                }

                lineItems.add(lineItem);
            }
            LineItem additionalChargesLineItem = new LineItem();
            additionalChargesLineItem.setType(EXTRACHARGES);
            additionalChargesLineItem.setAmountDetails(new ArrayList<>());
            Map<String, List<String>> stringListMap = new HashMap<>();
            for (AdditionalCharges additionalCharges : sorComposition.getAdditionalCharges()) {
                BigDecimal divideForCreate = BigDecimal.ONE;
                if (isCreate) {
                    divideForCreate = sorComposition.getQuantity();
                }
                AmountDetail amountDetail = AmountDetail.builder()
                        .type(additionalCharges.getCalculationType())
                        .heads(additionalCharges.getApplicableOn())
                        .amount(additionalCharges.getFigure().divide(divideForCreate, 2, RoundingMode.HALF_UP))
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
