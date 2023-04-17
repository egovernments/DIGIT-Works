package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.AmountDetail;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateDetail;
import org.egov.web.models.EstimateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CalculationService {


    /**
     * Not required as part of estimate service v1
     * Calculate the estimate amount for estimate details and estimate.
     *
     * @param estimateRequest
     */
    public EstimateRequest calculateEstimate(EstimateRequest estimateRequest) {
//        Estimate estimate = estimateRequest.getEstimate();
//        List<EstimateDetail> estimateDetails = estimate.getEstimateDetails();
//
//        //update each estimate details
//        List<EstimateDetail> updatedEstimateDetails = estimateDetails.stream().map(estimateDetail -> {
//            double totalAmount = estimateDetail.getAmountDetail().stream().mapToDouble(AmountDetail::getAmount).sum();
//            estimateDetail.setTotalAmount(totalAmount);
//            return estimateDetail;
//        }).collect(Collectors.toList());

        //update the final amount for an estimate
//        estimate.setEstimateDetails(updatedEstimateDetails);
//        estimate.setTotalEstimateAmount(estimate.getEstimateDetails().stream().mapToDouble(EstimateDetail::getTotalAmount).sum());

        return estimateRequest;
    }
}
