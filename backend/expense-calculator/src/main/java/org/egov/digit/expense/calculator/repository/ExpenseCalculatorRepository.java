package org.egov.digit.expense.calculator.repository;

import org.egov.digit.expense.calculator.repository.querybuilder.ExpenseCalculatorQueryBuilder;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseCalculatorRepository {

    @Autowired
    private ExpenseCalculatorRowMapper rowMapper;

    @Autowired
    private ExpenseCalculatorQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Fetch the record from DB based on the search criteria
     * @param contractId
     * @return
     */
    public List<String> getMusterRoll(String contractId, String billType, List<String> billIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterRollsOfContract(contractId, billType, billIds, preparedStmtList);
        List<String> musterrollIds = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return musterrollIds;
    }
}
