package org.egov.digit.expense.calculator.mapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.PROJECT_ID_OF_CONSTANT;

@Component
@Slf4j
public class BillToMetaMapper {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public BillMetaRecords map(List<Bill> bills, Map<String, String> context) {
            if(bills == null || bills.isEmpty()) return null;

            BillMetaRecords records = new BillMetaRecords();
            List<BillMetaCalculation> billMetaCalculations = new ArrayList<>();
            for(Bill bill :bills){
                String contractId = getContractId(bill.getReferenceId());
                String projectId = context.get(PROJECT_ID_OF_CONSTANT+contractId);
                String uuid = UUID.randomUUID().toString();

                BillMetaCalculation billMetaCalculation = BillMetaCalculation.builder()
                        .id(uuid)
                        .tenantId(bill.getTenantId())
                        .billType(bill.getBusinessService())
                        .billId(bill.getId())
                        .billNumber(bill.getReferenceId())
                        .billReference(bill.getReferenceId())
                        .contractId(contractId)
                        .musterrollId(getMusterRollId(bill,bill.getBusinessService()))
                        .musterrollNumber(getMusterRollId(bill,bill.getBusinessService()))
                        .projectName(projectId)
                        .auditDetails(bill.getAuditDetails())
                        .additionalDetails(bill.getAdditionalDetails())
                        .isActive(Boolean.TRUE)
                        .billMetaCalcDetails(getBillMetaCalcDetails(bill,uuid))
                        .build();
                billMetaCalculations.add(billMetaCalculation);
            }
        records.setBillMetaCalculation(billMetaCalculations);

        return records;
    }

    private List<BillMetaCalcDetails> getBillMetaCalcDetails(Bill bill, String uuid) {
        List<BillMetaCalcDetails> calcDetails = new ArrayList<>();
        List<BillDetail> billDetails = bill.getBillDetails();
        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            BillMetaCalcDetails billMetaCalcDetails = BillMetaCalcDetails.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(payee.getTenantId())
                    .calculationId(uuid)
                    .payeeId(payee.getIdentifier())
                    .build();
            calcDetails.add(billMetaCalcDetails);
        }

        return calcDetails;
    }

    private String getContractId(String referenceId) {
        final String[] split = referenceId.split("_");
        return split[0];
    }

    private String getMusterRollId(Bill bill, String billType) {
        if(configs.getWageBusinessService().equalsIgnoreCase(billType)) {
            String[] split  = bill.getReferenceId().split("_");
            if(split.length > 0)
                return split[1];
            else
                return null;
        }
        return null;
    }

    private String getValueFromAdditionalDetails(Bill bill, String key) {
         Object additionalDetails = bill.getAdditionalDetails();
         Optional<String> value = commonUtil.findValue(additionalDetails, key);
         if(value.isPresent()) return value.get();

         return null;
    }
}
