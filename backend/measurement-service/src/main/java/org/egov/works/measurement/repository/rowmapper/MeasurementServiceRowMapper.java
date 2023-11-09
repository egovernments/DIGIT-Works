package org.egov.works.measurement.repository.rowmapper;

import org.egov.common.contract.models.AuditDetails;
import org.egov.works.measurement.web.models.MeasurementService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MeasurementServiceRowMapper implements RowMapper<MeasurementService> {

    @Override
    public MeasurementService mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeasurementService measurementService = new MeasurementService();
        measurementService.setId(rs.getString("id"));
        measurementService.setTenantId(rs.getString("tenantId"));
        measurementService.setMeasurementNumber(rs.getString("mbNumber"));
        measurementService.setWfStatus(rs.getString("wfStatus"));

        AuditDetails auditDetails = new AuditDetails();
        auditDetails.setCreatedBy(rs.getString("createdby"));
        auditDetails.setCreatedTime(rs.getLong("createdtime"));
        auditDetails.setLastModifiedBy(rs.getString("lastmodifiedby"));
        auditDetails.setLastModifiedTime(rs.getLong("lastmodifiedtime"));
        measurementService.setAuditDetails(auditDetails);

        measurementService.setAdditionalDetails(rs.getObject("additionalDetails"));

        return measurementService;
    }
}
