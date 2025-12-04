package org.egov.digit.expense.calculator.repository.rowmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.egov.digit.expense.calculator.web.models.enums.BillingFrequency;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BillingConfigRowMapper
 *
 * Maps database result set to BillingConfig objects.
 * Follows DIGIT Works row mapper pattern.
 *
 * @author DIGIT-Works
 */
@Repository
@Slf4j
public class BillingConfigRowMapper implements ResultSetExtractor<List<BillingConfig>> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Extracts billing configuration data from result set.
     *
     * @param rs Result set from database
     * @return List of billing configurations
     * @throws SQLException if database access error occurs
     * @throws DataAccessException if data access error occurs
     */
    @Override
    public List<BillingConfig> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, BillingConfig> billingConfigMap = new HashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");

            // Check if we've already processed this config
            BillingConfig billingConfig = billingConfigMap.get(id);

            if (billingConfig == null) {
                String frequencyStr = rs.getString("billing_frequency");
                BillingFrequency frequency = frequencyStr != null ?
                    BillingFrequency.fromValue(frequencyStr) : null;

                billingConfig = BillingConfig.builder()
                    .id(id)
                    .tenantId(rs.getString("tenant_id"))
                    .projectId(rs.getString("project_id"))
                    .campaignNumber(rs.getString("campaign_number"))
                    .billingFrequency(frequency)
                    .customFrequencyDays(getInteger(rs, "custom_frequency_days"))
                    .projectStartDate(getLong(rs, "project_start_date"))
                    .projectEndDate(getLong(rs, "project_end_date"))
                    .status(rs.getString("status"))
                    .createdBy(rs.getString("created_by"))
                    .createdTime(getLong(rs, "created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(getLong(rs, "last_modified_time"))
                    .additionalDetails(getAdditionalDetails(rs, "additional_details"))
                    .build();

                billingConfigMap.put(id, billingConfig);
            }
        }

        return new ArrayList<>(billingConfigMap.values());
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
