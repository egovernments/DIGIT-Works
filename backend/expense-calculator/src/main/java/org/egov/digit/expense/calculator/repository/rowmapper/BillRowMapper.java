package org.egov.digit.expense.calculator.repository.rowmapper;


import org.egov.digit.expense.calculator.web.models.BillMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class BillRowMapper implements ResultSetExtractor<Map<String,BillMapper>> {


    @Override
    public Map<String,BillMapper> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, BillMapper> billMap = new LinkedHashMap<>();


        while (rs.next()) {
            String billId = rs.getString("bill_id");
            String contractNumber = rs.getString("contract_number");
            String projectNUmber = rs.getString("project_number");
            String orgId = rs.getString("org_id");
            String musterRollNumber = rs.getString("musterroll_number");



            BillMapper billMapper= BillMapper.builder()
                    .billId(billId)
                    .projectNumber(projectNUmber)
                    .contractNumber(contractNumber)
                    .orgId(orgId)
                    .musterRollNumber(musterRollNumber)
                    .bill(null)
                    .build();


            billMap.computeIfAbsent(billId, k -> billMapper);
        }


        return  billMap;
    }


}
