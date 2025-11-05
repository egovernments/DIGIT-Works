package org.egov.digit.expense.calculator.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.repository.querybuilder.BillingConfigQueryBuilder;
import org.egov.digit.expense.calculator.repository.rowmapper.BillingConfigRowMapper;
import org.egov.digit.expense.calculator.repository.rowmapper.BillingPeriodRowMapper;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.egov.digit.expense.calculator.web.models.BillingConfigSearchCriteria;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BillingConfigRepository
 *
 * Data access layer for billing configuration and period operations.
 * Follows DIGIT Works repository pattern with JDBC templates.
 *
 * @author DIGIT-Works
 */
@Repository
@Slf4j
public class BillingConfigRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BillingConfigQueryBuilder queryBuilder;
    private final BillingConfigRowMapper billingConfigRowMapper;
    private final BillingPeriodRowMapper billingPeriodRowMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillingConfigRepository(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   BillingConfigQueryBuilder queryBuilder,
                                   BillingConfigRowMapper billingConfigRowMapper,
                                   BillingPeriodRowMapper billingPeriodRowMapper,
                                   ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.billingConfigRowMapper = billingConfigRowMapper;
        this.billingPeriodRowMapper = billingPeriodRowMapper;
        this.objectMapper = objectMapper;
    }

    /**
     * Saves billing configuration to database.
     *
     * @param config Billing configuration to save
     */
    @Transactional
    public void save(BillingConfig config) {
        log.info("Saving billing configuration: {}", config.getId());

        String sql = "INSERT INTO eg_expense_billing_config " +
            "(id, tenant_id, campaign_number, billing_frequency, custom_frequency_days, " +
            "project_start_date, project_end_date, status, created_by, created_time, " +
            "last_modified_by, last_modified_time, additional_details) " +
            "VALUES (:id, :tenantId, :campaignNumber, :billingFrequency, :customFrequencyDays, " +
            ":projectStartDate, :projectEndDate, :status, :createdBy, :createdTime, " +
            ":lastModifiedBy, :lastModifiedTime, :additionalDetails::jsonb)";

        Map<String, Object> params = new HashMap<>();
        params.put("id", config.getId());
        params.put("tenantId", config.getTenantId());
        params.put("campaignNumber", config.getCampaignNumber());
        params.put("billingFrequency", config.getBillingFrequency() != null ?
            config.getBillingFrequency().getValue() : null);
        params.put("customFrequencyDays", config.getCustomFrequencyDays());
        params.put("projectStartDate", config.getProjectStartDate());
        params.put("projectEndDate", config.getProjectEndDate());
        params.put("status", config.getStatus());
        params.put("createdBy", config.getCreatedBy());
        params.put("createdTime", config.getCreatedTime());
        params.put("lastModifiedBy", config.getLastModifiedBy());
        params.put("lastModifiedTime", config.getLastModifiedTime());
        params.put("additionalDetails", convertAdditionalDetailsToJson(config.getAdditionalDetails()));

        namedParameterJdbcTemplate.update(sql, params);
        log.info("Billing configuration saved successfully: {}", config.getId());
    }

    /**
     * Saves multiple billing periods to database in batch.
     *
     * @param periods List of billing periods to save
     */
    @Transactional
    public void savePeriods(List<BillingPeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            log.warn("No periods to save");
            return;
        }

        log.info("Saving {} billing periods", periods.size());

        String sql = "INSERT INTO eg_wms_billing_period " +
            "(id, tenant_id, campaign_number, billing_config_id, period_number, " +
            "period_start_date, period_end_date, billing_frequency, period_type, " +
            "status, bill_id, total_amount, beneficiary_count, register_count, " +
            "muster_roll_count, created_by, created_time, last_modified_by, " +
            "last_modified_time, additional_details) " +
            "VALUES (:id, :tenantId, :campaignNumber, :billingConfigId, :periodNumber, " +
            ":periodStartDate, :periodEndDate, :billingFrequency, :periodType, " +
            ":status, :billId, :totalAmount, :beneficiaryCount, :registerCount, " +
            ":musterRollCount, :createdBy, :createdTime, :lastModifiedBy, " +
            ":lastModifiedTime, :additionalDetails::jsonb)";

        List<Map<String, Object>> batchParams = new ArrayList<>();
        for (BillingPeriod period : periods) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", period.getId());
            params.put("tenantId", period.getTenantId());
            params.put("campaignNumber", period.getCampaignNumber());
            params.put("billingConfigId", period.getBillingConfigId());
            params.put("periodNumber", period.getPeriodNumber());
            params.put("periodStartDate", period.getPeriodStartDate());
            params.put("periodEndDate", period.getPeriodEndDate());
            params.put("billingFrequency", period.getBillingFrequency() != null ?
                period.getBillingFrequency().getValue() : null);
            params.put("periodType", period.getPeriodType());
            params.put("status", period.getStatus());
            params.put("billId", period.getBillId());
            params.put("totalAmount", period.getTotalAmount());
            params.put("beneficiaryCount", period.getBeneficiaryCount());
            params.put("registerCount", period.getRegisterCount());
            params.put("musterRollCount", period.getMusterRollCount());
            params.put("createdBy", period.getCreatedBy());
            params.put("createdTime", period.getCreatedTime());
            params.put("lastModifiedBy", period.getLastModifiedBy());
            params.put("lastModifiedTime", period.getLastModifiedTime());
            params.put("additionalDetails", convertAdditionalDetailsToJson(period.getAdditionalDetails()));

            batchParams.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(sql, batchParams.toArray(new Map[0]));
        log.info("Successfully saved {} billing periods", periods.size());
    }

    /**
     * Searches for billing configurations based on criteria.
     *
     * @param criteria Search criteria
     * @return List of matching billing configurations
     */
    public List<BillingConfig> search(BillingConfigSearchCriteria criteria) {
        log.info("Searching billing configurations with criteria: {}", criteria);

        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.buildBillingConfigSearchQuery(criteria, preparedStmtList);

        log.debug("Executing query: {} with params: {}", query, preparedStmtList);

        List<BillingConfig> configs = jdbcTemplate.query(
            query,
            billingConfigRowMapper,
            preparedStmtList.toArray()
        );

        log.info("Found {} billing configurations", configs != null ? configs.size() : 0);
        return configs;
    }

    /**
     * Finds billing configuration by campaign number.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return Billing configuration or null if not found
     */
    public BillingConfig findByCampaignNumber(String campaignNumber, String tenantId) {
        log.info("Finding billing configuration for campaign: {} in tenant: {}", campaignNumber, tenantId);

        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.buildFindByCampaignNumberQuery(campaignNumber, tenantId, preparedStmtList);

        log.debug("Executing query: {} with params: {}", query, preparedStmtList);

        List<BillingConfig> configs = jdbcTemplate.query(
            query,
            billingConfigRowMapper,
            preparedStmtList.toArray()
        );

        if (configs != null && !configs.isEmpty()) {
            log.info("Found billing configuration: {}", configs.get(0).getId());
            return configs.get(0);
        }

        log.info("No billing configuration found for campaign: {}", campaignNumber);
        return null;
    }

    /**
     * Finds billing periods by campaign number.
     *
     * @param campaignNumber Campaign identifier
     * @param tenantId Tenant identifier
     * @return List of billing periods ordered by period number
     */
    public List<BillingPeriod> findPeriodsByCampaignNumber(String campaignNumber, String tenantId) {
        log.info("Finding billing periods for campaign: {} in tenant: {}", campaignNumber, tenantId);

        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.buildBillingPeriodSearchQuery(campaignNumber, tenantId, preparedStmtList);

        log.debug("Executing query: {} with params: {}", query, preparedStmtList);

        List<BillingPeriod> periods = jdbcTemplate.query(
            query,
            billingPeriodRowMapper,
            preparedStmtList.toArray()
        );

        log.info("Found {} billing periods", periods != null ? periods.size() : 0);
        return periods;
    }

    /**
     * Finds billing periods by billing configuration ID.
     *
     * @param billingConfigId Billing configuration identifier
     * @return List of billing periods ordered by period number
     */
    public List<BillingPeriod> findPeriodsByConfigId(String billingConfigId) {
        log.info("Finding billing periods for config: {}", billingConfigId);

        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.buildBillingPeriodByConfigIdQuery(billingConfigId, preparedStmtList);

        log.debug("Executing query: {} with params: {}", query, preparedStmtList);

        List<BillingPeriod> periods = jdbcTemplate.query(
            query,
            billingPeriodRowMapper,
            preparedStmtList.toArray()
        );

        log.info("Found {} billing periods", periods != null ? periods.size() : 0);
        return periods;
    }

    /**
     * Updates billing configuration in database.
     *
     * @param config Billing configuration to update
     */
    @Transactional
    public void update(BillingConfig config) {
        log.info("Updating billing configuration: {}", config.getId());

        String sql = "UPDATE eg_expense_billing_config SET " +
            "status = :status, " +
            "last_modified_by = :lastModifiedBy, " +
            "last_modified_time = :lastModifiedTime, " +
            "additional_details = :additionalDetails::jsonb " +
            "WHERE id = :id AND tenant_id = :tenantId";

        Map<String, Object> params = new HashMap<>();
        params.put("id", config.getId());
        params.put("tenantId", config.getTenantId());
        params.put("status", config.getStatus());
        params.put("lastModifiedBy", config.getLastModifiedBy());
        params.put("lastModifiedTime", config.getLastModifiedTime());
        params.put("additionalDetails", convertAdditionalDetailsToJson(config.getAdditionalDetails()));

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);

        if (rowsUpdated == 0) {
            log.warn("No billing configuration found to update with id: {}", config.getId());
        } else {
            log.info("Billing configuration updated successfully: {}", config.getId());
        }
    }

    /**
     * Updates billing period in database.
     *
     * @param period Billing period to update
     */
    @Transactional
    public void updatePeriod(BillingPeriod period) {
        log.info("Updating billing period: {}", period.getId());

        String sql = "UPDATE eg_wms_billing_period SET " +
            "status = :status, " +
            "bill_id = :billId, " +
            "total_amount = :totalAmount, " +
            "beneficiary_count = :beneficiaryCount, " +
            "register_count = :registerCount, " +
            "muster_roll_count = :musterRollCount, " +
            "last_modified_by = :lastModifiedBy, " +
            "last_modified_time = :lastModifiedTime, " +
            "additional_details = :additionalDetails::jsonb " +
            "WHERE id = :id AND tenant_id = :tenantId";

        Map<String, Object> params = new HashMap<>();
        params.put("id", period.getId());
        params.put("tenantId", period.getTenantId());
        params.put("status", period.getStatus());
        params.put("billId", period.getBillId());
        params.put("totalAmount", period.getTotalAmount());
        params.put("beneficiaryCount", period.getBeneficiaryCount());
        params.put("registerCount", period.getRegisterCount());
        params.put("musterRollCount", period.getMusterRollCount());
        params.put("lastModifiedBy", period.getLastModifiedBy());
        params.put("lastModifiedTime", period.getLastModifiedTime());
        params.put("additionalDetails", convertAdditionalDetailsToJson(period.getAdditionalDetails()));

        int rowsUpdated = namedParameterJdbcTemplate.update(sql, params);

        if (rowsUpdated == 0) {
            log.warn("No billing period found to update with id: {}", period.getId());
        } else {
            log.info("Billing period updated successfully: {}", period.getId());
        }
    }

    /**
     * Converts additional details object to JSON string.
     *
     * @param additionalDetails Additional details object
     * @return JSON string or null
     */
    private String convertAdditionalDetailsToJson(Object additionalDetails) {
        if (additionalDetails == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(additionalDetails);
        } catch (JsonProcessingException e) {
            log.error("Error converting additional details to JSON", e);
            throw new CustomException("JSON_CONVERSION_ERROR",
                "Error converting additional details to JSON: " + e.getMessage());
        }
    }
}
