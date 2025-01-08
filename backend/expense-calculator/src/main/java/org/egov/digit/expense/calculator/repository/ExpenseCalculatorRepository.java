package org.egov.digit.expense.calculator.repository;

import org.egov.digit.expense.calculator.repository.querybuilder.ExpenseCalculatorQueryBuilder;
import org.egov.digit.expense.calculator.repository.rowmapper.BillRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorBillRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorMusterRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.ExpenseCalculatorProjectRowMapper;
import org.egov.digit.expense.calculator.util.BillStatus;
import org.egov.digit.expense.calculator.web.models.BillMapper;
import org.egov.digit.expense.calculator.web.models.CalculatorSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExpenseCalculatorRepository {

    private final ExpenseCalculatorMusterRowMapper musterRowMapper;

    private final ExpenseCalculatorBillRowMapper billRowMapper;
    
    private final ExpenseCalculatorProjectRowMapper projectRowMapper;

    private final BillRowMapper billMapper;

    private final ExpenseCalculatorQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ExpenseCalculatorRepository(ExpenseCalculatorMusterRowMapper musterRowMapper, ExpenseCalculatorBillRowMapper billRowMapper, ExpenseCalculatorProjectRowMapper projectRowMapper, BillRowMapper billMapper, ExpenseCalculatorQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.musterRowMapper = musterRowMapper;
        this.billRowMapper = billRowMapper;
        this.projectRowMapper = projectRowMapper;
        this.billMapper = billMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void createBillStatus(String id, String tenantId, String referenceId, String status, String error) {
        String sql = "INSERT INTO eg_expense_bill_gen_status (id, tenantid, referenceid, status, error) " +
                "VALUES (:id, :tenantId, :referenceId, :status, :error)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("tenantId", tenantId);
        params.put("referenceId", referenceId);
        params.put("status", status);
        params.put("error", error);

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updateBillStatus(String id, String status, String error) {
        String sql = "UPDATE eg_expense_bill_gen_status " +
                "SET status = :status, error = :error " +
                "WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", status);
        params.put("error", error);

        namedParameterJdbcTemplate.update(sql, params);
    }


    public List<String> searchBillStatusByReferenceId(String referenceId) {
        String sql = "SELECT status FROM eg_expense_bill_gen_status WHERE referenceid = :referenceId LIMIT 1";

        Map<String, Object> params = new HashMap<>();
        params.put("referenceId", referenceId);

        return namedParameterJdbcTemplate.queryForList(sql, params, String.class);
    }

    public List<BillStatus> getBillStatusByReferenceId(String referenceId) {
        String sql = "SELECT * FROM eg_expense_bill_gen_status " +
                "WHERE referenceid = :referenceId";

        Map<String, Object> params = new HashMap<>();
        params.put("referenceId", referenceId);

        return namedParameterJdbcTemplate.queryForList(sql, params, BillStatus.class);
    }



    /**
     * Fetch the record from DB based on the search criteria
     * @param contractId
     * @return
     */
    public List<String> getMusterRoll(String contractId, String billType, String tenantId, List<String> billIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getMusterRollsOfContract(contractId, billType, tenantId, billIds, preparedStmtList);
        return jdbcTemplate.query(query, musterRowMapper, preparedStmtList.toArray());
    }
    
    

    /**
     * Fetch the record from DB based on the search criteria
     * @param contractId
     * @return
     */
    public List<String> getBills(String contractId, String tenantId) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillsOfContract(contractId, tenantId, preparedStmtList);
        return jdbcTemplate.query(query, billRowMapper, preparedStmtList.toArray());
    }
    
    public List<String> getBillsByProjectNumber(String tenantId, List<String> projectNumbers) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillsByProjectNumbers(tenantId, projectNumbers, preparedStmtList);
        return jdbcTemplate.query(query, billRowMapper, preparedStmtList.toArray());
    }
    
    public List<String> getUniqueProjectNumbers(String tenantId) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getUniqueProjectNumbersByTenant(tenantId,preparedStmtList);
        return jdbcTemplate.query(query, projectRowMapper, preparedStmtList.toArray());
    }
    

    /* Fetch the record from DB based on the calculatorSearchCritera
     * @param calculatorSearchRequest
     * @return
     */
    public Map<String,BillMapper> getBillMappers(CalculatorSearchRequest calculatorSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getBillIds(calculatorSearchRequest,preparedStmtList,false);

        return jdbcTemplate.query(query,billMapper,preparedStmtList.toArray());
    }

    public Integer getBillCount(CalculatorSearchRequest calculatorSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getSearchCountQueryString(calculatorSearchRequest, preparedStmtList,true);
        if (query == null)
            return 0;
        return jdbcTemplate.queryForObject(query, preparedStmtList.toArray(), Integer.class);
    }
}
