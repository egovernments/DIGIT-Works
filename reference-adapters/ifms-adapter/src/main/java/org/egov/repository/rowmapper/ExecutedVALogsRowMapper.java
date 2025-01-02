package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.jit.ExecutedVALog;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Component
@Slf4j
public class ExecutedVALogsRowMapper implements ResultSetExtractor<List<ExecutedVALog>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<ExecutedVALog> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, ExecutedVALog> attendanceStaffMap = new LinkedHashMap<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantId");
            String hoaCode = rs.getString("hoaCode");
            String ddoCode = rs.getString("ddoCode");
            String granteeCode = rs.getString("granteeCode");
            Long lastExecuted = rs.getLong("lastExecuted");
            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            ExecutedVALog executedVALog = ExecutedVALog.builder()
                    .id(id)
                    .tenantId(tenantId)
                    .hoaCode(hoaCode)
                    .ddoCode(ddoCode)
                    .granteeCode(granteeCode)
                    .lastExecuted(lastExecuted)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!attendanceStaffMap.containsKey(id)) {
                attendanceStaffMap.put(id, executedVALog);
            }
        }
        return new ArrayList<>(attendanceStaffMap.values());
    }


    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = objectMapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails == null) {
            JsonNode emptyObject = objectMapper.createObjectNode();
            additionalDetails = emptyObject;
        }
        return additionalDetails;
    }
}
