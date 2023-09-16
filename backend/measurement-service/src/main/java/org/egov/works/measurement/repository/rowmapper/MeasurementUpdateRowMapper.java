package org.egov.works.measurement.repository.rowmapper;

import org.egov.works.measurement.web.models.AuditDetails;
import org.egov.works.measurement.web.models.Measurement;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MeasurementUpdateRowMapper implements RowMapper<Measurement> {

    @Override
    public Measurement mapRow(ResultSet rs, int rowNum) throws SQLException {
        Measurement measurement = new Measurement();
        measurement.setId(UUID.fromString(rs.getString("id")));
        measurement.setTenantId(rs.getString("tenantId"));
        measurement.setMeasurementNumber(rs.getString("mbNumber"));
        measurement.setPhysicalRefNumber(rs.getString("phyRefNumber"));
        measurement.setReferenceId(rs.getString("referenceId"));
        measurement.setEntryDate(rs.getBigDecimal("entryDate"));
        measurement.setIsActive(rs.getBoolean("isActive"));

        // Set AuditDetails
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(rs.getString("createdby"));
        auditDetails.setCreatedTime(rs.getLong("createdtime"));
        auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
        auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
        measurement.setAuditDetails(auditDetails);

        measurement.setAdditionalDetails(rs.getObject("additionalDetails")); // Assuming additionalDetails is a JSONB field
        // Map other fields as needed
        return measurement;
    }
}

