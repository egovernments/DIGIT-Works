package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.Configuration;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.web.models.AmountDetail;
import org.egov.works.web.models.LineItem;
import org.egov.works.web.models.RateAnalysis;
import org.egov.works.web.models.Rates;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EnrichmentService {

    private final Configuration configs;
    private final EnrichmentUtil enrichmentUtil;

    public EnrichmentService(Configuration configs, EnrichmentUtil enrichmentUtil) {
        this.configs = configs;
        this.enrichmentUtil = enrichmentUtil;
    }

    /**
     * Enriches the given list of RateAnalysis objects by calculating the total amount for each heads and
     * adding the labour cess amount if not already present.
     *
     * @param rateAnalysisList List of RateAnalysis objects to be enriched
     * @return List of enriched Rates objects
     */
    List<Rates> enrichRates(List<RateAnalysis> rateAnalysisList) {
        List<Rates> ratesList = new ArrayList<>();

        // Iterate over each RateAnalysis object
        for (RateAnalysis rateAnalysis : rateAnalysisList) {
            Map<String, List<AmountDetail>> amountDetailMap = new HashMap<>();
            // Iterate over each LineItem and AmountDetail in the RateAnalysis
            for (LineItem lineItem : rateAnalysis.getLineItems()) {
                for (AmountDetail amountDetail : lineItem.getAmountDetails()) {
                    // If the heads already exists in the map, add the AmountDetail to the existing list
                    if (amountDetailMap.containsKey(amountDetail.getHeads())) {
                        amountDetailMap.get(amountDetail.getHeads()).add(amountDetail);
                    } else {
                        // If the heads does not exist in the map, create a new list and add the AmountDetail
                        List<AmountDetail> amountDetails = new ArrayList<>();
                        amountDetails.add(amountDetail);
                        amountDetailMap.put(amountDetail.getHeads(), amountDetails);
                    }
                }
            }
            // Calculate the total amount for each heads
            List<AmountDetail> finalAmountDetails = new ArrayList<>();
            for (Map.Entry<String, List<AmountDetail>> entry : amountDetailMap.entrySet()) {
                BigDecimal totalAmount = BigDecimal.valueOf(0.0);
                for (AmountDetail amountDetail : entry.getValue()) {
                    totalAmount = totalAmount.add(amountDetail.getAmount());
                }
                AmountDetail amountDetail = AmountDetail.builder()
                        .amount(totalAmount)
                        .type(entry.getValue().get(0).getType())
                        .heads(entry.getKey())
                        .build();
                finalAmountDetails.add(amountDetail);
            }
            // Calculate the amount for labour cess
            BigDecimal amountForLabourCess = BigDecimal.valueOf(0.0);
            for (AmountDetail amountDetail : finalAmountDetails) {
                if (!amountDetail.getHeads().equalsIgnoreCase(configs.getLabourCessHeadCode()))
                    amountForLabourCess = amountForLabourCess.add(amountDetail.getAmount());
            }
            amountForLabourCess = amountForLabourCess.multiply(configs.getLabourCessRate()).divide(BigDecimal.valueOf(100),
                    2, RoundingMode.HALF_UP);
            // Add the labour cess amount if not already present
            AmountDetail labourCessAmountDetail = null;
            for (AmountDetail amountDetail : finalAmountDetails) {
                if (amountDetail.getHeads().equalsIgnoreCase(configs.getLabourCessHeadCode())) {
                    labourCessAmountDetail = amountDetail;
                    break;
                }
            }
            if (labourCessAmountDetail == null) {
                labourCessAmountDetail = AmountDetail.builder().amount(amountForLabourCess).type(AmountDetail.TypeEnum.PERCENTAGE).heads(configs.getLabourCessHeadCode()).build();
                finalAmountDetails.add(labourCessAmountDetail);
            } else {
                labourCessAmountDetail.setAmount(labourCessAmountDetail.getAmount().add(amountForLabourCess));
            }


            // Create the Rates object and enrich it
            Rates rates = Rates.builder().sorId(rateAnalysis.getSorId())
                    .amountDetails(finalAmountDetails).build();
            BigDecimal rate = BigDecimal.valueOf(0.0);
            for (AmountDetail amountDetail : finalAmountDetails) {
                rate = rate.add(amountDetail.getAmount());
            }
            rate = rate.setScale(2, RoundingMode.HALF_UP);
            rates.setRate(rate);
            enrichmentUtil.enrichRates(rates, rateAnalysis, rateAnalysis.getEffectiveFrom());
            ratesList.add(rates);
        }
        return ratesList;
    }

}
