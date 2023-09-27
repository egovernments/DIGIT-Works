package org.egov.works.measurement.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.egov.common.contract.models.AuditDetails;
import org.egov.works.measurement.web.models.MeasurementSvcObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MeasurementServiceRowMapper implements RowMapper<MeasurementSvcObject> {

    @Override
    public MeasurementSvcObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        MeasurementSvcObject measurementService = new MeasurementSvcObject();
        measurementService.setId(UUID.fromString(rs.getString("id")));
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
