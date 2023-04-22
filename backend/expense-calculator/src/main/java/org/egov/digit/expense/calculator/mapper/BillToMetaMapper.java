package org.egov.digit.expense.calculator.mapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.CommonUtil;
import org.egov.digit.expense.calculator.web.models.AuditDetails;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillMeta;
import org.egov.digit.expense.calculator.web.models.BillMetaRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BillToMetaMapper {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExpenseCalculatorConfiguration configs;

    public BillMetaRecords map(List<Bill> bills) {
            if(bills == null || bills.isEmpty()) return null;

            BillMetaRecords records = new BillMetaRecords();
            List<BillMeta> billMetas = new ArrayList<>();
            for(Bill bill :bills){
                String id = bill.getId();
                String tenantId = bill.getTenantId();
                String serviceCode = bill.getBusinessService();
//                String contractId = getValueFromAdditionalDetails(bill,CONTRACT_ID_CONSTANT);
//                String billType = getValueFromAdditionalDetails(bill, BILL_TYPE_CONSTANT);
                String contractId = getReferenceId(bill.getReferenceId());
                String billId = bill.getReferenceId();
                String musterRollId = getMusterRollId(bill,bill.getBusinessService());
                AuditDetails billAuditDetails = bill.getAuditDetails();
                BillMeta billMeta = BillMeta.builder()
                        .id(id)
                        .tenantId(tenantId)
                        .serviceCode(serviceCode)
                        .contractId(contractId)
                        .billType(bill.getBusinessService())
                        .billId(billId)
                        .musterrollId(musterRollId)
                        .auditDetails(billAuditDetails)
                        .isActive(Boolean.TRUE)
                        .billAmount(bill.getNetPayableAmount())
                        .build();
                billMetas.add(billMeta);
            }
        records.setBillMeta(billMetas);

        return records;
    }

    private String getReferenceId(String referenceId) {
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
