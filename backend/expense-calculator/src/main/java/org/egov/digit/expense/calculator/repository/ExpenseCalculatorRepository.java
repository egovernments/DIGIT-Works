package org.egov.digit.expense.calculator.repository;

import org.egov.digit.expense.calculator.repository.querybuilder.ExpenseCalculatorQueryBuilder;
import org.egov.digit.expense.calculator.repository.rowmapper.BillRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorBillRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorMusterRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorProjectRowMapper;
import org.egov.digit.expense.calculator.web.models.BillMapper;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchCriteria;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExpenseCalculatorRepository {

    @Autowired
    private ExpenseCalculatorMusterRowMapper musterRowMapper;

    @Autowired
    private ExpenseCalculatorBillRowMapper billRowMapper;
    
    @Autowired
    private ExpenseCalculatorProjectRowMapper projectRowMapper;

    @Autowired
    private BillRowMapper billMapper;

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
    
    public List<String> getBillsByProjectNumber(String tenantId, List<String> projectNumbers) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillsByProjectNumbers(tenantId, projectNumbers, preparedStmtList);
        List<String> result = jdbcTemplate.query(query, billRowMapper, preparedStmtList.toArray());
        return result;
    }
    
    public List<String> getUniqueProjectNumbers(String tenantId) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getUniqueProjectNumbersByTenant(tenantId,preparedStmtList);
        List<String> result = jdbcTemplate.query(query, projectRowMapper, preparedStmtList.toArray());
        return result;
    }
    

    /* Fetch the record from DB based on the calculatorSearchCritera
     * @param calculatorSearchRequest
     * @return
     */
    public Map<String,BillMapper> getBillMappers(CalculatorSearchRequest calculatorSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillIds(calculatorSearchRequest,preparedStmtList,false);
        Map<String,BillMapper> billMappers = jdbcTemplate.query(query,billMapper,preparedStmtList.toArray());

        return billMappers;
    }

    public Integer getBillCount(CalculatorSearchRequest calculatorSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(calculatorSearchRequest, preparedStmtList,true);
        if (query == null)
            return 0;
        Integer count = jdbcTemplate.queryForObject(query, preparedStmtList.toArray(), Integer.class);
        return count;
    }
}
