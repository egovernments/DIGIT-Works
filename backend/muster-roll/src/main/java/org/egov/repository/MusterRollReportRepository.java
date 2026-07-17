package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.report.MusterRollReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@Slf4j
@Repository
public class MusterRollReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MultiStateInstanceUtil multiStateInstanceUtil;

    private static final String SELECT_QUERY = "SELECT id, muster_roll_id, tenant_id, report_type, report_format, " +
            "report_status, file_store_id, generated_at, error_message, created_by, last_modified_by, " +
            "created_time, last_modified_time FROM " + SCHEMA_REPLACE_STRING + ".eg_wms_muster_roll_report";

    private static final String INSERT_QUERY = "INSERT INTO " + SCHEMA_REPLACE_STRING + ".eg_wms_muster_roll_report " +
            "(id, muster_roll_id, tenant_id, report_type, report_format, report_status, file_store_id, " +
            "generated_at, error_message, created_by, last_modified_by, created_time, last_modified_time) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE " + SCHEMA_REPLACE_STRING + ".eg_wms_muster_roll_report SET " +
            "report_status=?, file_store_id=?, generated_at=?, error_message=?, " +
            "last_modified_by=?, last_modified_time=? " +
            "WHERE muster_roll_id=? AND report_type=? AND report_format=?";

    private static final String DELETE_QUERY = "DELETE FROM " + SCHEMA_REPLACE_STRING + ".eg_wms_muster_roll_report " +
            "WHERE muster_roll_id=? AND report_type=? AND report_format=?";

    private static final RowMapper<MusterRollReport> rowMapper = new RowMapper<MusterRollReport>() {
        @Override
        public MusterRollReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MusterRollReport.builder()
                    .id(rs.getString("id"))
                    .musterRollId(rs.getString("muster_roll_id"))
                    .tenantId(rs.getString("tenant_id"))
                    .reportType(rs.getString("report_type"))
                    .reportFormat(rs.getString("report_format"))
                    .reportStatus(rs.getString("report_status"))
                    .fileStoreId(rs.getString("file_store_id"))
                    .generatedAt(rs.getLong("generated_at"))
                    .errorMessage(rs.getString("error_message"))
                    .auditDetails(AuditDetails.builder()
                            .createdBy(rs.getString("created_by"))
                            .lastModifiedBy(rs.getString("last_modified_by"))
                            .createdTime(rs.getLong("created_time"))
                            .lastModifiedTime(rs.getLong("last_modified_time"))
                            .build())
                    .build();
        }
    };

    /**
     * Save a new report
     */
    public MusterRollReport save(MusterRollReport report) {
        if (report.getId() == null) {
            report.setId(UUID.randomUUID().toString());
        }
        if (report.getAuditDetails() == null) {
            report.setAuditDetails(new AuditDetails());
        }

        Long now = System.currentTimeMillis();
        report.getAuditDetails().setCreatedTime(now);
        report.getAuditDetails().setLastModifiedTime(now);

        try {
            // Apply schema replacement for multi-tenant support
            String query = multiStateInstanceUtil.replaceSchemaPlaceholder(INSERT_QUERY, report.getTenantId());

            jdbcTemplate.update(query,
                    report.getId(),
                    report.getMusterRollId(),
                    report.getTenantId(),
                    report.getReportType(),
                    report.getReportFormat(),
                    report.getReportStatus(),
                    report.getFileStoreId(),
                    report.getGeneratedAt(),
                    report.getErrorMessage(),
                    report.getAuditDetails().getCreatedBy(),
                    report.getAuditDetails().getLastModifiedBy(),
                    report.getAuditDetails().getCreatedTime(),
                    report.getAuditDetails().getLastModifiedTime()
            );
            log.debug("Report saved: id={}, musterRollId={}, type={}, format={}, tenantId={}",
                    report.getId(), report.getMusterRollId(), report.getReportType(), report.getReportFormat(),
                    report.getTenantId());
        } catch (InvalidTenantIdException e) {
            log.error("Invalid tenant ID for report save: {}", report.getTenantId(), e);
            throw new CustomException("INVALID_TENANT", e.getMessage());
        } catch (Exception e) {
            log.error("Error saving report: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save report", e);
        }

        return report;
    }

    /**
     * Update an existing report
     */
    public MusterRollReport update(MusterRollReport report) {
        Long now = System.currentTimeMillis();
        if (report.getAuditDetails() == null) {
            report.setAuditDetails(new AuditDetails());
        }
        report.getAuditDetails().setLastModifiedTime(now);

        try {
            // Apply schema replacement for multi-tenant support
            String query = multiStateInstanceUtil.replaceSchemaPlaceholder(UPDATE_QUERY, report.getTenantId());

            jdbcTemplate.update(query,
                    report.getReportStatus(),
                    report.getFileStoreId(),
                    report.getGeneratedAt(),
                    report.getErrorMessage(),
                    report.getAuditDetails().getLastModifiedBy(),
                    now,
                    report.getMusterRollId(),
                    report.getReportType(),
                    report.getReportFormat()
            );
            log.debug("Report updated: musterRollId={}, type={}, format={}, status={}, tenantId={}",
                    report.getMusterRollId(), report.getReportType(), report.getReportFormat(),
                    report.getReportStatus(), report.getTenantId());
        } catch (InvalidTenantIdException e) {
            log.error("Invalid tenant ID for report update: {}", report.getTenantId(), e);
            throw new CustomException("INVALID_TENANT", e.getMessage());
        } catch (Exception e) {
            log.error("Error updating report: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update report", e);
        }

        return report;
    }

    /**
     * Find report by muster roll ID, type, and format
     */
    public Optional<MusterRollReport> findByMusterRollAndTypeAndFormat(String musterRollId, String reportType, String reportFormat, String tenantId) {
        try {
            // Apply schema replacement for multi-tenant support
            String query = multiStateInstanceUtil.replaceSchemaPlaceholder(
                    SELECT_QUERY + " WHERE muster_roll_id=? AND report_type=? AND report_format=?",
                    tenantId
            );

            List<MusterRollReport> results = jdbcTemplate.query(
                    query,
                    rowMapper,
                    musterRollId,
                    reportType,
                    reportFormat
            );
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (InvalidTenantIdException e) {
            log.error("Invalid tenant ID for report lookup: tenantId={}", tenantId, e);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error finding report: musterRollId={}, type={}, format={}, tenantId={}",
                    musterRollId, reportType, reportFormat, tenantId, e);
            return Optional.empty();
        }
    }

    /**
     * Find all reports for a muster roll
     */
    public List<MusterRollReport> findByMusterRollId(String musterRollId) {
        try {
            return jdbcTemplate.query(
                    SELECT_QUERY + " WHERE muster_roll_id=? ORDER BY generated_at DESC",
                    rowMapper,
                    musterRollId
            );
        } catch (Exception e) {
            log.error("Error finding reports for muster roll: {}", musterRollId, e);
            return List.of();
        }
    }

    /**
     * Find all reports for a muster roll by tenant
     */
    public List<MusterRollReport> findByMusterRollAndTenant(String musterRollId, String tenantId) {
        try {
            // Apply schema replacement for multi-tenant support
            String query = multiStateInstanceUtil.replaceSchemaPlaceholder(
                    SELECT_QUERY + " WHERE muster_roll_id=? AND tenant_id=? ORDER BY generated_at DESC",
                    tenantId
            );

            return jdbcTemplate.query(
                    query,
                    rowMapper,
                    musterRollId,
                    tenantId
            );
        } catch (InvalidTenantIdException e) {
            log.error("Invalid tenant ID for reports lookup: tenantId={}", tenantId, e);
            return List.of();
        } catch (Exception e) {
            log.error("Error finding reports for muster roll: {}, tenant: {}", musterRollId, tenantId, e);
            return List.of();
        }
    }

    /**
     * Delete a report
     */
    public void delete(String musterRollId, String reportType, String reportFormat) {
        try {
            jdbcTemplate.update(DELETE_QUERY, musterRollId, reportType, reportFormat);
            log.debug("Report deleted: musterRollId={}, type={}, format={}", musterRollId, reportType, reportFormat);
        } catch (Exception e) {
            log.error("Error deleting report: {}", e.getMessage(), e);
        }
    }
}
