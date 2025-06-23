package org.egov.digit.expense.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task.TaskBuilder builder = Task.builder()
                .id(rs.getString("id"))
                .billId(rs.getString("bill_id"))
                .status(Status.valueOf(rs.getString("status")))
                .type(Task.Type.valueOf(rs.getString("type")));

        // Handle AuditDetails if present in result set
        if (columnExists(rs, "created_by")) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("created_by"))
                    .createdTime(rs.getLong("created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(rs.getLong("last_modified_time"))
                    .build();
            builder.auditDetails(auditDetails);
        }

        if (columnExists(rs, "additional_details")) {
            String additionalDetailsJson = rs.getString("additional_details");
            if (additionalDetailsJson != null && !additionalDetailsJson.isEmpty()) {
                 Object additionalDetails = new ObjectMapper().convertValue(additionalDetailsJson,Object.class);
                 builder.additionalDetails(additionalDetails);
            }
        }

        return builder.build();
    }

    private boolean columnExists(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}