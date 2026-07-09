package org.egov.digit.expense.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.BillReport;
import org.egov.digit.expense.web.models.enums.ReportStatus;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BillReportRowMapper implements ResultSetExtractor<List<BillReport>> {

    private final ObjectMapper mapper;

    @Autowired
    public BillReportRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<BillReport> extractData(ResultSet rs) throws SQLException {
        List<BillReport> reports = new ArrayList<>();

        while (rs.next()) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("created_by"))
                    .createdTime(rs.getLong("created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(rs.getLong("last_modified_time"))
                    .build();

            BillReport report = BillReport.builder()
                    .id(rs.getString("id"))
                    .billId(rs.getString("bill_id"))
                    .tenantId(rs.getString("tenant_id"))
                    .type(ReportType.fromValue(rs.getString("type")))
                    .status(ReportStatus.fromValue(rs.getString("status")))
                    .fileStoreId(rs.getString("file_store_id"))
                    .errorDetails(getErrorDetails(rs, "error_details"))
                    .auditDetails(auditDetails)
                    .build();

            reports.add(report);
        }

        return reports;
    }

    private JsonNode getErrorDetails(ResultSet rs, String key) throws SQLException {
        JsonNode errorDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(key);
            if (obj != null) {
                errorDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            throw new CustomException("PARSING_ERROR", "The errorDetails json cannot be parsed");
        }

        if (errorDetails != null && errorDetails.isEmpty()) {
            errorDetails = null;
        }

        return errorDetails;
    }
}
