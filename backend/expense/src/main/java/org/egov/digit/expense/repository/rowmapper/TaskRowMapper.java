package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.digit.expense.web.models.Task;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskRowMapper implements RowMapper<Task> {

    private final ObjectMapper mapper;

    @Autowired
    public TaskRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

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
            builder.additionalDetails(getadditionalDetail(rs,"additional_details"));
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

    private JsonNode getadditionalDetail(ResultSet rs, String key) throws SQLException {

        JsonNode additionalDetails = null;

        try {

            PGobject obj = (PGobject) rs.getObject(key);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }

        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "The propertyAdditionalDetail json cannot be parsed");
        }

        if(additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;

        return additionalDetails;

    }
}