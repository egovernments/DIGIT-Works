package org.egov.digit.expense.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.web.models.SchedulerJob;
import org.egov.digit.expense.web.models.enums.SchedulerJobStatus;
import org.egov.digit.expense.web.models.enums.SchedulerJobType;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class SchedulerJobRowMapper implements RowMapper<SchedulerJob> {

    private final ObjectMapper mapper;

    @Autowired
    public SchedulerJobRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SchedulerJob mapRow(ResultSet rs, int rowNum) throws SQLException {
        SchedulerJob.SchedulerJobBuilder builder = SchedulerJob.builder()
                .id(rs.getString("id"))
                .tenantId(rs.getString("tenant_id"))
                .jobType(SchedulerJobType.valueOf(rs.getString("job_type")))
                .referenceId(rs.getString("reference_id"))
                .schedulerStatus(SchedulerJobStatus.valueOf(rs.getString("scheduler_status")))
                .attemptCount(rs.getInt("attempt_count"))
                .maxAttempts(rs.getInt("max_attempts"))
                .createdAt(rs.getLong("created_at"))
                .updatedAt(rs.getLong("updated_at"));

        long nextCheckAt = rs.getLong("next_check_at");
        builder.nextCheckAt(rs.wasNull() ? null : nextCheckAt);

        builder.context(readJsonb(rs, "context"));

        return builder.build();
    }

    private JsonNode readJsonb(ResultSet rs, String column) throws SQLException {
        try {
            PGobject obj = (PGobject) rs.getObject(column);
            if (obj == null || obj.getValue() == null) return null;
            JsonNode node = mapper.readTree(obj.getValue());
            return (node != null && !node.isEmpty()) ? node : null;
        } catch (IOException e) {
            throw new CustomException("SCHEDULER_JOB_PARSE_ERROR",
                    "Failed to parse scheduler job context JSON: " + e.getMessage());
        }
    }
}
