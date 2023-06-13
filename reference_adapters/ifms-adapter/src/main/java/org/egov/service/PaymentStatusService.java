package org.egov.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.egov.config.IfmsAdapterConfig;
import org.egov.kafka.Producer;
import org.egov.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentStatusService {

    @Autowired
    private Producer producer;
    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    // Only for the purpose of mocking
    public void producePaymentStatusFromBill(BillDemandResponse billDemandResponse) {

        List<PaymentStatus> paymentStatuses = new ArrayList<>();

        for(BillDemand billDemand : billDemandResponse.getBillDemands()) {
            List<BeneficiaryTransferStatus> beneficiaryTransferStatusList = new ArrayList<>();
            for(Beneficiary beneficiary : billDemand.getBeneficiaries()) {
                beneficiaryTransferStatusList.add(
                        BeneficiaryTransferStatus.builder()
                                .accountNumber(beneficiary.getAccountNumber())
                                .ifscCode(beneficiary.getIfscCode())
                                .sequenceDate(billDemand.getBillDate())
                                .rbiSequenceNumber(RandomStringUtils.random(10, false, true))
                                .endToEndId(RandomStringUtils.random(10, false, true))
                                .status("SUCCESS")
                                .build());
            }

            PaymentStatus paymentStatus = PaymentStatus.builder()
                    .billNumber(billDemand.getBillNumber())
                    .billDate(billDemand.getBillDate())
                    .voucherNumber(RandomStringUtils.random(10, false, true))
                    .voucherDate(billDemand.getBillDate())
                    .beneficiaryTransferStatuses(beneficiaryTransferStatusList)
                    .build();

            paymentStatuses.add(paymentStatus);
        }

        PaymentStatusResponse paymentStatusResponse =
                PaymentStatusResponse.builder()
                        .responseInfo(billDemandResponse.getResponseInfo())
                        .paymentStatuses(paymentStatuses).build();

        producer.push(ifmsAdapterConfig.getPaymentStatusTopic(), paymentStatusResponse);
    }

    public void updatePaymentStatus(Object paymentDetailsDTO) {

    }

}
