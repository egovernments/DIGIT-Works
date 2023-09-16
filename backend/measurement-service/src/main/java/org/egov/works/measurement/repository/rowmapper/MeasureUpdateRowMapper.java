package org.egov.works.measurement.repository.rowmapper;

import org.egov.works.measurement.web.models.AuditDetails;
import org.egov.works.measurement.web.models.Measure;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MeasureUpdateRowMapper implements RowMapper<Measure> {

    @Override
    public Measure mapRow(ResultSet rs, int rowNum) throws SQLException {
        Measure measure = new Measure();
        measure.setId(UUID.fromString(rs.getString("id")));
        measure.setLength(rs.getBigDecimal("length"));
        measure.setBreadth(rs.getBigDecimal("breadth"));
        measure.setHeight(rs.getBigDecimal("height"));
        measure.setNumItems(rs.getBigDecimal("numOfItems"));
        measure.setCurrentValue(rs.getBigDecimal("totalValue"));

        // Set AuditDetails (if applicable in your database schema)
        // Note: You might need to adjust this part based on your actual schema.
        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(rs.getString("createdby"));
        auditDetails.setCreatedTime(rs.getLong("createdtime"));
        auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
        auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
        measure.setAuditDetails(auditDetails);

        return measure;
    }
}

