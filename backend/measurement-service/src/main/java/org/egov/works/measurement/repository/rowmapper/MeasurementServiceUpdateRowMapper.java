package org.egov.works.measurement.repository.rowmapper;

import org.egov.works.measurement.web.models.AuditDetails;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MeasurementServiceUpdateRowMapper implements RowMapper<Measurement> {

    @Override
    public MeasurementService mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeasurementService measurementService = new MeasurementService();
        measurementService.setId(UUID.fromString(rs.getString("id")));
        measurementService.setTenantId(rs.getString("tenantId"));
        measurementService.setMeasurementNumber(rs.getString("mbNumber"));
        measurementService.setWfStatus(rs.getString("wfStatus"));

        // Map common fields with Measurement
        measurementService.setPhysicalRefNumber(rs.getString("phyRefNumber"));
        measurementService.setReferenceId(rs.getString("referenceId"));
        measurementService.setEntryDate(rs.getBigDecimal("entryDate"));
        measurementService.setIsActive(rs.getBoolean("isActive"));

        // Set AuditDetails
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(rs.getString("createdby"));
        auditDetails.setCreatedTime(rs.getLong("createdtime"));
        auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
        auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
        measurementService.setAuditDetails(auditDetails);

        // Set additionalDetails and other common fields
        measurementService.setAdditionalDetails(rs.getObject("additionalDetails")); // Assuming additionalDetails is a JSONB field
        // Map other common fields as needed

        return measurementService;
    }
}
