package org.egov.digit.expense.calculator.mapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillMeta;
import org.egov.digit.expense.calculator.web.models.BillMetaRecords;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class BillToMetaMapper {
    public BillMetaRecords map(List<Bill> bills) {
            if(bills == null || bills.isEmpty()) return null;

            BillMetaRecords records = new BillMetaRecords();
            List<BillMeta> billMeta = new ArrayList<>();
            for(Bill bill :bills){

                //TODO
            }


        return records;
    }
}
