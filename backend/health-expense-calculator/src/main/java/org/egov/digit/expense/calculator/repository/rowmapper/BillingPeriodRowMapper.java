package org.egov.digit.expense.calculator.repository.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BillingPeriodRowMapper
 *
 * Maps database result set to BillingPeriod objects.
 * Follows DIGIT Works row mapper pattern.
 *
 * @author DIGIT-Works
 */
@Repository
@Slf4j
public class BillingPeriodRowMapper implements ResultSetExtractor<List<BillingPeriod>> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Extracts billing period data from result set.
     *
     * @param rs Result set from database
     * @return List of billing periods
     * @throws SQLException if database access error occurs
     * @throws DataAccessException if data access error occurs
     */
    @Override
    public List<BillingPeriod> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, BillingPeriod> billingPeriodMap = new HashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");

            // Check if we've already processed this period
            BillingPeriod billingPeriod = billingPeriodMap.get(id);

            if (billingPeriod == null) {
                String frequencyStr = rs.getString("billing_frequency");
                BillingFrequency frequency = frequencyStr != null ?
                    BillingFrequency.fromValue(frequencyStr) : null;

                billingPeriod = BillingPeriod.builder()
                    .id(id)
                    .tenantId(rs.getString("tenant_id"))
                    .projectId(rs.getString("project_id"))
                    .campaignNumber(rs.getString("campaign_number"))
                    .billingConfigId(rs.getString("billing_config_id"))
                    .periodNumber(rs.getInt("period_number"))
                    .periodStartDate(rs.getLong("period_start_date"))
                    .periodEndDate(rs.getLong("period_end_date"))
                    .billingFrequency(frequency)
                    .periodType(rs.getString("period_type"))
                    .status(rs.getString("status"))
                    .billId(rs.getString("bill_id"))
                    .totalAmount(getBigDecimal(rs, "total_amount"))
                    .beneficiaryCount(getInteger(rs, "beneficiary_count"))
                    .registerCount(getInteger(rs, "register_count"))
                    .musterRollCount(getInteger(rs, "muster_roll_count"))
                    .createdBy(rs.getString("created_by"))
                    .createdTime(rs.getLong("created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(getLong(rs, "last_modified_time"))
                    .isDeprecated(rs.getBoolean("is_deprecated"))
                    .additionalDetails(getAdditionalDetails(rs, "additional_details"))
                    .build();

                billingPeriodMap.put(id, billingPeriod);
            }
        }

        return new ArrayList<>(billingPeriodMap.values());
    }

    /**
     * Safely retrieves Integer value from result set.
     *
     * @param rs Result set
     * @param columnName Column name
     * @return Integer value or null if NULL in database
     * @throws SQLException if column doesn't exist
     */
    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }

    /**
     * Safely retrieves Long value from result set.
     *
     * @param rs Result set
     * @param columnName Column name
     * @return Long value or null if NULL in database
     * @throws SQLException if column doesn't exist
     */
    private Long getLong(ResultSet rs, String columnName) throws SQLException {
        long value = rs.getLong(columnName);
        return rs.wasNull() ? null : value;
    }

    /**
     * Safely retrieves BigDecimal value from result set.
     *
     * @param rs Result set
     * @param columnName Column name
     * @return BigDecimal value or null if NULL in database
     * @throws SQLException if column doesn't exist
     */
    private BigDecimal getBigDecimal(ResultSet rs, String columnName) throws SQLException {
        BigDecimal value = rs.getBigDecimal(columnName);
        return rs.wasNull() ? null : value;
    }

    /**
     * Parses JSON additional details from result set.
     *
     * @param rs Result set
     * @param columnName Column name
     * @return Parsed object or null
     * @throws SQLException if column doesn't exist
     */
    private Object getAdditionalDetails(ResultSet rs, String columnName) throws SQLException {
        String jsonString = rs.getString(columnName);
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing additional details JSON: {}", jsonString, e);
            throw new CustomException("JSON_PARSING_ERROR",
                "Error parsing additional details: " + e.getMessage());
        }
    }
}
