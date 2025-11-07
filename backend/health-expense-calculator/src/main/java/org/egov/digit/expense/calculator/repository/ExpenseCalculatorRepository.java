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
                " WHERE referenceid = :referenceId ";

        Map<String, Object> params = new HashMap<>();
        params.put("referenceId", referenceId);

        List<Map<String, Object>> objectMap = namedParameterJdbcTemplate.queryForList(sql, params);

        List<BillStatus> billStatusList = new ArrayList<>(); //BillStatus
        for (Map<String, Object> row : objectMap) {
            BillStatus billStatus = BillStatus.builder()
                    .id((String) row.get("id"))
                    .referenceId((String) row.get("referenceid"))
                    .tenantId((String) row.get("tenantid"))
                    .status((String) row.get("status")).build();

            billStatusList.add(billStatus);
        }

        return billStatusList;
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

    // ==================== V2 Methods for Intermediate Billing ====================

    /**
     * Get bill status by period ID
     * Used for duplicate bill prevention in V2 intermediate billing
     *
     * @param periodId Billing period ID
     * @return List of bill statuses for the period
     */
    public List<BillStatus> getBillStatusByPeriodId(String periodId) {
        String sql = "SELECT * FROM eg_expense_bill_gen_status WHERE period_id = :periodId";

        Map<String, Object> params = new HashMap<>();
        params.put("periodId", periodId);

        List<Map<String, Object>> objectMap = namedParameterJdbcTemplate.queryForList(sql, params);

        List<BillStatus> billStatusList = new ArrayList<>();
        for (Map<String, Object> row : objectMap) {
            BillStatus billStatus = BillStatus.builder()
                    .id((String) row.get("id"))
                    .referenceId((String) row.get("referenceid"))
                    .tenantId((String) row.get("tenantid"))
                    .status((String) row.get("status"))
                    .error((String) row.get("error"))
                    .build();

            billStatusList.add(billStatus);
        }

        return billStatusList;
    }

    /**
     * Create bill status with V2 fields
     * Includes period information and processing time tracking
     *
     * @param id Bill status ID
     * @param tenantId Tenant ID
     * @param referenceId Reference ID (project ID)
     * @param billingType REGULAR, INTERMEDIATE, or FINAL_AGGREGATE
     * @param periodId Billing period ID
     * @param periodNumber Period number (1, 2, 3, etc.)
     * @param status Status (INITIATED, SUCCESSFUL, FAILED)
     * @param error Error message (if any)
     * @param processingStartTime Processing start timestamp
     * @param registerCount Number of registers in this period
     */
    public void createBillStatusV2(String id, String tenantId, String referenceId,
                                   String billingType, String periodId, Integer periodNumber,
                                   String status, String error, Long processingStartTime,
                                   Integer registerCount) {
        String sql = "INSERT INTO eg_expense_bill_gen_status " +
                "(id, tenantid, referenceid, billing_type, period_id, period_number, " +
                "status, error, processing_start_time, register_count) " +
                "VALUES (:id, :tenantId, :referenceId, :billingType, :periodId, :periodNumber, " +
                ":status, :error, :processingStartTime, :registerCount)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("tenantId", tenantId);
        params.put("referenceId", referenceId);
        params.put("billingType", billingType);
        params.put("periodId", periodId);
        params.put("periodNumber", periodNumber);
        params.put("status", status);
        params.put("error", error);
        params.put("processingStartTime", processingStartTime);
        params.put("registerCount", registerCount);

        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Update bill status with V2 fields
     * Updates status, error message, and processing end time
     *
     * @param id Bill status ID
     * @param status Updated status
     * @param error Error message (if any)
     * @param processingEndTime Processing end timestamp
     */
    public void updateBillStatusV2(String id, String status, String error, Long processingEndTime) {
        String sql = "UPDATE eg_expense_bill_gen_status " +
                "SET status = :status, error = :error, processing_end_time = :processingEndTime " +
                "WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", status);
        params.put("error", error);
        params.put("processingEndTime", processingEndTime);

        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Check if bill already generated for specific project+period combination
     * Critical for duplicate prevention in V2 intermediate billing
     *
     * @param projectId Project reference ID
     * @param periodId Billing period ID
     * @return true if bill exists with SUCCESSFUL status, false otherwise
     */
    public boolean isBillGeneratedForProjectPeriod(String projectId, String periodId) {
        String sql = "SELECT COUNT(*) FROM eg_expense_bill_gen_status " +
                "WHERE referenceid = :projectId " +
                "AND period_id = :periodId " +
                "AND billing_type = 'INTERMEDIATE' " +
                "AND status = 'SUCCESSFUL'";

        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("periodId", periodId);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    /**
     * Get bill status for specific project+period combination
     * Returns the bill status record if it exists
     *
     * @param projectId Project reference ID
     * @param periodId Billing period ID
     * @return BillStatus if found, null otherwise
     */
    public BillStatus getBillStatusByProjectAndPeriod(String projectId, String periodId) {
        String sql = "SELECT * FROM eg_expense_bill_gen_status " +
                "WHERE referenceid = :projectId " +
                "AND period_id = :periodId " +
                "AND billing_type = 'INTERMEDIATE' " +
                "ORDER BY processing_start_time DESC " +
                "LIMIT 1";

        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("periodId", periodId);

        List<Map<String, Object>> objectMap = namedParameterJdbcTemplate.queryForList(sql, params);

        if (objectMap.isEmpty()) {
            return null;
        }

        Map<String, Object> row = objectMap.get(0);
        return BillStatus.builder()
                .id((String) row.get("id"))
                .referenceId((String) row.get("referenceid"))
                .tenantId((String) row.get("tenantid"))
                .status((String) row.get("status"))
                .error((String) row.get("error"))
                .build();
    }

    /**
     * Update bill status with additional details (including bill ID)
     * Used after successful bill submission to store bill metadata
     *
     * @param billStatusId Bill status ID to update
     * @param status Updated status
     * @param error Error message (if any)
     * @param processingEndTime Processing end timestamp
     * @param billId Expense service bill ID
     * @param billNumber Expense service bill number
     * @param totalAmount Total bill amount
     * @param musterRollCount Number of muster rolls included
     */
    public void updateBillStatusWithDetails(String billStatusId, String status, String error,
                                           Long processingEndTime, String billId, String billNumber,
                                           String totalAmount, Integer musterRollCount) {
        String sql = "UPDATE eg_expense_bill_gen_status " +
                "SET status = :status, " +
                "    error = :error, " +
                "    processing_end_time = :processingEndTime, " +
                "    additional_details = jsonb_build_object(" +
                "        'billId', :billId::text, " +
                "        'billNumber', :billNumber::text, " +
                "        'totalAmount', :totalAmount::text, " +
                "        'musterRollCount', :musterRollCount::int" +
                "    ) " +
                "WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", billStatusId);
        params.put("status", status);
        params.put("error", error);
        params.put("processingEndTime", processingEndTime);
        params.put("billId", billId);
        params.put("billNumber", billNumber);
        params.put("totalAmount", totalAmount);
        params.put("musterRollCount", musterRollCount);

        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Check if a specific period is billed for a project
     * Used for sequential billing validation
     *
     * @param projectId Project reference ID
     * @param periodId Period ID to check
     * @return true if period is billed (SUCCESSFUL status), false otherwise
     */
    public boolean isPeriodBilledForProject(String projectId, String periodId) {
        String sql = "SELECT COUNT(*) FROM eg_expense_bill_gen_status " +
                "WHERE referenceid = :projectId " +
                "AND period_id = :periodId " +
                "AND billing_type = 'INTERMEDIATE' " +
                "AND status = 'SUCCESSFUL'";

        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("periodId", periodId);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    /**
     * Get all successfully billed period numbers for a project
     * Used to check billing sequence
     *
     * @param projectId Project reference ID
     * @param tenantId Tenant ID
     * @return List of period numbers that have been successfully billed
     */
    public List<Integer> getBilledPeriodNumbersForProject(String projectId, String tenantId) {
        String sql = "SELECT DISTINCT period_number FROM eg_expense_bill_gen_status " +
                "WHERE referenceid = :projectId " +
                "AND tenantid = :tenantId " +
                "AND billing_type = 'INTERMEDIATE' " +
                "AND status = 'SUCCESSFUL' " +
                "AND period_number IS NOT NULL " +
                "ORDER BY period_number";

        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("tenantId", tenantId);

        return namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);
    }
}
