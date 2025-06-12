package org.egov.digit.expense.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.TaskDetails;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskDetailsRowMapper implements RowMapper<TaskDetails> {

    @Override
    public TaskDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskDetails.TaskDetailsBuilder builder = TaskDetails.builder()
                .id(rs.getString("id"))
                .tenantId(rs.getString("tenant_id"))
                .billId(rs.getString("bill_id"))
                .billDetailsId(rs.getString("bill_details_id"))
                .payeeId(rs.getString("payee_id"))
                .taskId(rs.getString("task_id"))
                .referenceId(rs.getString("reference_id"))
                .responseMessage(rs.getString("response_message"))
                .reasonForFailure(rs.getString("reason_for_failure"));

        // Handle Status enum
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            builder.status(Status.valueOf(statusStr));
        }

        // Handle AuditDetails if present in result set
        if (columnExists(rs, "createdby")) {
            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("created_by"))
                    .createdTime(rs.getLong("created_time"))
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .lastModifiedTime(rs.getLong("last_modified_time"))
                    .build();
            builder.auditDetails(auditDetails);
        }

        // Handle additionalDetails (assuming it's stored as JSON string)
        if (columnExists(rs, "additional_details")) {
            String additionalDetailsJson = rs.getString("additional_details");
            if (additionalDetailsJson != null && !additionalDetailsJson.isEmpty()) {
                Object additionalDetails = new ObjectMapper().convertValue(additionalDetailsJson, Object.class);
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