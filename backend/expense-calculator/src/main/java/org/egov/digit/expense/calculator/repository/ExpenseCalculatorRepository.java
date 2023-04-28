package org.egov.digit.expense.calculator.repository;

import org.egov.digit.expense.calculator.repository.querybuilder.ExpenseCalculatorQueryBuilder;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorBillRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorMusterRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseCalculatorRepository {

    @Autowired
    private ExpenseCalculatorMusterRowMapper musterRowMapper;

    @Autowired
    private ExpenseCalculatorBillRowMapper billRowMapper;

    @Autowired
    private ExpenseCalculatorQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Fetch the record from DB based on the search criteria
     * @param contractId
     * @return
     */
    public List<String> getMusterRoll(String contractId, String billType, String tenantId, List<String> billIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterRollsOfContract(contractId, billType, tenantId, billIds, preparedStmtList);
        List<String> musterrollIds = jdbcTemplate.query(query, musterRowMapper, preparedStmtList.toArray());
        return musterrollIds;
    }

    /**
     * Fetch the record from DB based on the search criteria
     * @param contractId
     * @return
     */
    public List<String> getBills(String contractId, String tenantId) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillsOfContract(contractId, tenantId, preparedStmtList);
        List<String> billIds = jdbcTemplate.query(query, billRowMapper, preparedStmtList.toArray());
        return billIds;
    }
}
