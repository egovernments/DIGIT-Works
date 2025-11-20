package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.RegisterPeriodStatus;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PeriodStatusUpdateService
 *
 * V2 Intermediate Billing - Service to manage period_statuses JSONB field updates.
 *
 * This service provides event-driven updates to attendance register period statuses.
 * When muster-roll status changes, this service updates the register's period_statuses
 * array asynchronously via Kafka events.
 *
 * Key benefits:
 * - Eliminates synchronous API calls during search
 * - Scales to millions of registers
 * - Pre-computes status for fast queries
 * - Event-driven, non-blocking architecture
 */
@Service
@Slf4j
public class PeriodStatusUpdateService {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PeriodStatusUpdateService(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Updates the period status for a specific register and billing period.
     *
     * This method:
     * 1. Fetches existing period_statuses array from DB
     * 2. Updates or adds the status for the specified period
     * 3. Persists the modified array back to DB
     *
     * @param registerId The attendance register ID
     * @param periodStatus The new or updated period status
     */
    @Transactional
    public void updatePeriodStatus(String registerId, RegisterPeriodStatus periodStatus) {
        log.info("updatePeriodStatus::Updating period status for register: {} period: {} status: {}",
                registerId, periodStatus.getPeriodId(), periodStatus.getStatus());

        try {
            // 1. Fetch existing period_statuses array
            List<RegisterPeriodStatus> existingStatuses = fetchExistingPeriodStatuses(registerId);

            // 2. Update or add the new status
            List<RegisterPeriodStatus> updatedStatuses = mergeperiodStatus(existingStatuses, periodStatus);

            // 3. Convert to JSONB and persist
            String jsonbValue = objectMapper.writeValueAsString(updatedStatuses);
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(jsonbValue);

            String updateSql = "UPDATE eg_wms_attendance_register SET period_statuses = ? WHERE id = ?";
            int rowsUpdated = jdbcTemplate.update(updateSql, pgObject, registerId);

            if (rowsUpdated == 0) {
                log.warn("updatePeriodStatus::No register found with ID: {}", registerId);
                throw new CustomException("REGISTER_NOT_FOUND", "Attendance register not found: " + registerId);
            }

            log.info("updatePeriodStatus::Successfully updated period status for register: {} - Total periods: {}",
                    registerId, updatedStatuses.size());

        } catch (Exception e) {
            log.error("updatePeriodStatus::Failed to update period status for register: {} - Error: {}",
                    registerId, e.getMessage(), e);
            throw new CustomException("PERIOD_STATUS_UPDATE_FAILED",
                    "Failed to update period status: " + e.getMessage());
        }
    }

    /**
     * Fetches existing period_statuses array from database for a given register.
     *
     * @param registerId The attendance register ID
     * @return List of existing period statuses, or empty list if none
     */
    private List<RegisterPeriodStatus> fetchExistingPeriodStatuses(String registerId) {
        try {
            String query = "SELECT period_statuses FROM eg_wms_attendance_register WHERE id = ?";

            return jdbcTemplate.query(query, ps -> ps.setString(1, registerId), rs -> {
                if (!rs.next()) {
                    log.warn("fetchExistingPeriodStatuses::Register not found: {}", registerId);
                    return new ArrayList<>();
                }

                PGobject pgObject = (PGobject) rs.getObject("period_statuses");
                if (pgObject == null || pgObject.getValue() == null) {
                    log.debug("fetchExistingPeriodStatuses::No existing period statuses for register: {}", registerId);
                    return new ArrayList<>();
                }

                try {
                    String jsonValue = pgObject.getValue();
                    List<RegisterPeriodStatus> statuses = objectMapper.readValue(
                            jsonValue,
                            objectMapper.getTypeFactory().constructCollectionType(List.class, RegisterPeriodStatus.class)
                    );

                    log.debug("fetchExistingPeriodStatuses::Found {} existing period statuses for register: {}",
                            statuses.size(), registerId);
                    return statuses;
                } catch (Exception e) {
                    log.error("fetchExistingPeriodStatuses::Failed to parse JSON for register: {} - Error: {}",
                            registerId, e.getMessage());
                    return new ArrayList<>();
                }
            });

        } catch (Exception e) {
            log.error("fetchExistingPeriodStatuses::Failed to fetch period statuses for register: {} - Error: {}",
                    registerId, e.getMessage(), e);
            // Return empty list on error to allow new status insertion
            return new ArrayList<>();
        }
    }

    /**
     * Merges new period status into existing list.
     * If status for the period already exists, it's updated.
     * If not, it's appended to the list.
     *
     * @param existing List of existing period statuses
     * @param newStatus New period status to merge
     * @return Updated list with merged status
     */
    private List<RegisterPeriodStatus> mergeperiodStatus(
            List<RegisterPeriodStatus> existing,
            RegisterPeriodStatus newStatus) {

        String periodId = newStatus.getPeriodId();

        // Check if status for this period already exists
        boolean periodExists = existing.stream()
                .anyMatch(s -> s.getPeriodId().equals(periodId));

        if (periodExists) {
            // Update existing period status
            log.debug("mergePeriodStatus::Updating existing status for period: {}", periodId);
            return existing.stream()
                    .map(s -> s.getPeriodId().equals(periodId) ? newStatus : s)
                    .collect(Collectors.toList());
        } else {
            // Add new period status
            log.debug("mergePeriodStatus::Adding new status for period: {}", periodId);
            List<RegisterPeriodStatus> updated = new ArrayList<>(existing);
            updated.add(newStatus);
            return updated;
        }
    }

    /**
     * Deletes a period status for a specific register and period.
     * Useful for cleanup or corrections.
     *
     * @param registerId The attendance register ID
     * @param periodId The billing period ID to remove
     */
    @Transactional
    public void deletePeriodStatus(String registerId, String periodId) {
        log.info("deletePeriodStatus::Deleting period status for register: {} period: {}",
                registerId, periodId);

        try {
            List<RegisterPeriodStatus> existingStatuses = fetchExistingPeriodStatuses(registerId);

            // Filter out the specified period
            List<RegisterPeriodStatus> updatedStatuses = existingStatuses.stream()
                    .filter(s -> !s.getPeriodId().equals(periodId))
                    .collect(Collectors.toList());

            if (existingStatuses.size() == updatedStatuses.size()) {
                log.warn("deletePeriodStatus::Period {} not found in register {}", periodId, registerId);
                return; // Period not found, nothing to delete
            }

            // Persist updated array
            String jsonbValue = objectMapper.writeValueAsString(updatedStatuses);
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(jsonbValue);

            String updateSql = "UPDATE eg_wms_attendance_register SET period_statuses = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, pgObject, registerId);

            log.info("deletePeriodStatus::Successfully deleted period status for register: {} period: {}",
                    registerId, periodId);

        } catch (Exception e) {
            log.error("deletePeriodStatus::Failed to delete period status - Error: {}", e.getMessage(), e);
            throw new CustomException("PERIOD_STATUS_DELETE_FAILED",
                    "Failed to delete period status: " + e.getMessage());
        }
    }
}
