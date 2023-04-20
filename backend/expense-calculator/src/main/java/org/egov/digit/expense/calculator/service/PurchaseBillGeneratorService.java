package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseBillGeneratorService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public Bill createPurchaseBill(PurchaseBillRequest purchaseBillRequest){
        Bill bill = purchaseBillRequest.getBill();
        Bill purchaseBill = Bill.builder()
                    .id(bill.getId())
                    .tenantId(bill.getTenantId())
                    .billDate(bill.getBillDate())
                    .dueDate(bill.getDueDate())
                    .netPayableAmount(bill.getNetPayableAmount())
                    .netPaidAmount(bill.getNetPaidAmount())
                    .businessService(bill.getBusinessService())
                    .referenceId(bill.getReferenceId())
                    //.billType(bill.getBillType())
                    .fromPeriod(bill.getFromPeriod())
                    .toPeriod(bill.getToPeriod())
                    .paymentStatus(bill.getPaymentStatus())
                    .status(bill.getStatus())
                    .payer(bill.getPayer())
                    .billDetails(bill.getBillDetails())
                    .additionalDetails(bill.getAdditionalDetails())
                    .build();
        return purchaseBill;
    }
}
