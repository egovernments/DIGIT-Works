package org.egov.digit.expense.calculator.mapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Component
@Slf4j
public class BillToMetaMapper {


    private final ExpenseCalculatorConfiguration configs;

    @Autowired
    public BillToMetaMapper(ExpenseCalculatorConfiguration configs) {
        this.configs = configs;
    }

    public BillMetaRecords map(List<Bill> bills, Map<String, String> metaInfo) {
        if(bills == null || bills.isEmpty()) return null;

        log.info("Create meta records");
        BillMetaRecords records = new BillMetaRecords();
        List<BillMetaCalculation> billMetaCalculations = new ArrayList<>();
        for(Bill bill :bills){
            String contractId = getContractId(bill.getReferenceId());
            String projectId = metaInfo.get(PROJECT_ID_OF_CONSTANT+contractId);
            String uuid = UUID.randomUUID().toString();

            BillMetaCalculation billMetaCalculation = BillMetaCalculation.builder()
                    .id(uuid)
                    .tenantId(bill.getTenantId())
                    .businessService(bill.getBusinessService())
                    .billId(bill.getId())
                    .billNumber(bill.getBillNumber())
                    .billReference(bill.getReferenceId())
                    //.contractId(contractId)
                    .contractNumber(contractId)
                   // .musterrollId(getMusterRollId(bill,bill.getBusinessService()))
                    .orgId(metaInfo.get(ORG_ID_CONSTANT))
                    .musterrollNumber(getMusterRollId(bill,bill.getBusinessService()))
                    .projectNumber(projectId)
                    .auditDetails(bill.getAuditDetails())
                    .additionalDetails(bill.getAdditionalDetails())
                    .isActive(Boolean.TRUE)
                    .billMetaCalcDetails(getBillMetaCalcDetails(bill,uuid,metaInfo))
                    .build();
            billMetaCalculations.add(billMetaCalculation);
        }
        records.setBillMetaCalculation(billMetaCalculations);

        log.info("Meta records created successfully");
        return records;
    }

    private List<BillMetaCalcDetails> getBillMetaCalcDetails(Bill bill, String uuid, Map<String, String> infoMeta) {
        List<BillMetaCalcDetails> calcDetails = new ArrayList<>();
        List<BillDetail> billDetails = bill.getBillDetails();
        for(BillDetail billDetail : billDetails) {
            Party payee = billDetail.getPayee();
            BillMetaCalcDetails billMetaCalcDetails = BillMetaCalcDetails.builder()
                    .id(UUID.randomUUID().toString())
                    .tenantId(payee.getTenantId())
                    .calculationId(uuid)
                    .payeeId(payee.getIdentifier())
                    .isActive(Boolean.TRUE)
                    .billingSlabCode(infoMeta.get(payee.getIdentifier()))
                    .auditDetails(billDetail.getAuditDetails())
                    .additionalDetails(billDetail.getAdditionalDetails())
                    .build();
            calcDetails.add(billMetaCalcDetails);
        }

        return calcDetails;
    }

    private String getContractId(String referenceId) {
        final String[] split = referenceId.split(CONCAT_CHAR_CONSTANT);
        return split[0];
    }

    private String getMusterRollId(Bill bill, String billType) {
        if(configs.getWageBusinessService().equalsIgnoreCase(billType)) {
            String[] split  = bill.getReferenceId().split(CONCAT_CHAR_CONSTANT);
            if(split.length > 0)
                return split[1];
            else
                return null;
        }
        return null;
    }

}