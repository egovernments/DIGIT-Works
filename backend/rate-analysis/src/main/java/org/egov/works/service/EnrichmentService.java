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
import java.util.*;

@Service
@Slf4j
public class EnrichmentService {

    private final Configuration configs;
    private final EnrichmentUtil enrichmentUtil;

    public EnrichmentService(Configuration configs, EnrichmentUtil enrichmentUtil) {
        this.configs = configs;
        this.enrichmentUtil = enrichmentUtil;
    }

    List<Rates> enrichRates(List<RateAnalysis> rateAnalysisList) {
        List<Rates> ratesList = new ArrayList<>();
        for (RateAnalysis rateAnalysis : rateAnalysisList) {
            Map<String, List<AmountDetail>> amountDetailMap = new HashMap<>();
            for (LineItem lineItem : rateAnalysis.getLineItems()) {
                for (AmountDetail amountDetail : lineItem.getAmountDetails()) {
                    if (amountDetailMap.containsKey(amountDetail.getHeads())) {
                        amountDetailMap.get(amountDetail.getHeads()).add(amountDetail);
                    } else {
                        List<AmountDetail> amountDetails = new ArrayList<>();
                        amountDetails.add(amountDetail);
                        amountDetailMap.put(amountDetail.getHeads(), amountDetails);
                    }
                }
            }
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
            BigDecimal amountForLabourCess = BigDecimal.valueOf(0.0);
            for (AmountDetail amountDetail : finalAmountDetails) {
                if (!amountDetail.getHeads().equalsIgnoreCase(configs.getLabourCessHeadCode()))
                    amountForLabourCess = amountForLabourCess.add(amountDetail.getAmount());
            }
            amountForLabourCess = amountForLabourCess.multiply(configs.getLabourCessRate().divide(BigDecimal.valueOf(100)));
            AmountDetail labourCessAmountDetail = null;
            for (AmountDetail amountDetail : finalAmountDetails) {
                if (amountDetail.getHeads().equalsIgnoreCase(configs.getLabourCessHeadCode())) {
                    labourCessAmountDetail = amountDetail;
                    break;
                }
            }
            if (labourCessAmountDetail == null) {
                labourCessAmountDetail = AmountDetail.builder().amount(amountForLabourCess).type(AmountDetail.TypeEnum.PERCENTAGE).heads(configs.getLabourCessHeadCode()).build();
            } else {
                labourCessAmountDetail.setAmount(labourCessAmountDetail.getAmount().add(amountForLabourCess));
            }

            finalAmountDetails.add(labourCessAmountDetail);
            Rates rates = Rates.builder().sorCode(rateAnalysis.getSorCode()).sorId(rateAnalysis.getSorId())
                    .amountDetails(finalAmountDetails).build();
            BigDecimal rate = BigDecimal.valueOf(0.0);
            for (AmountDetail amountDetail : finalAmountDetails) {
                rate = rate.add(amountDetail.getAmount());
            }
            rates.setRate(rate);
            enrichmentUtil.enrichRates(rates, rateAnalysis, rateAnalysis.getEffectiveFrom());
            ratesList.add(rates);
        }
        return ratesList;
    }

}
