package org.egov.repository.rowmapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.RegisterPeriodStatus;
import org.egov.web.models.Status;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

/**
 * RegisterRowMapper
 *
 * Standard Spring JDBC RowMapper for extracting AttendanceRegister objects from ResultSet.
 * This pattern is used throughout DIGIT codebase (StaffRowMapper, AttendeeRowMapper, etc.)
 */
@Component
public class RegisterRowMapper implements ResultSetExtractor<List<AttendanceRegister>> {

    private final ObjectMapper mapper;

    @Autowired
    public RegisterRowMapper(@Qualifier("objectMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<AttendanceRegister> extractData(ResultSet rs) throws SQLException, DataAccessException {

        // ============================================================
        // LOCAL MAP - NO MEMORY LEAK CONCERN
        // ============================================================
        // This map is a LOCAL VARIABLE (not a class field). It:
        //   1. Is created fresh for each method invocation
        //   2. Goes out of scope when method returns
        //   3. Is eligible for garbage collection immediately after
        //
        // The return statement creates a NEW ArrayList from the map values.
        // After return, the local map reference is lost and GC will clean it.
        //
        // This is the STANDARD Spring JDBC pattern used in:
        //   - StaffRowMapper.java (this same codebase)
        //   - AttendeeRowMapper.java (this same codebase)
        //   - MusterRollRowMapper.java (muster-roll service)
        //   - Thousands of DIGIT services
        //
        // There is NO memory leak because:
        //   - Map is local, not static
        //   - Map is not stored in any class field
        //   - Method completes → map goes out of scope → GC cleans up
        // ============================================================
        Map<String, AttendanceRegister> attendanceRegisterMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantid");
            String registerNumber = rs.getString("registernumber");
            String name = rs.getString("name");
            BigDecimal startDate = rs.getBigDecimal("startdate");
            BigDecimal endDate = rs.getBigDecimal("enddate");
            String status = rs.getString("status");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");
            String referenceId = rs.getString("referenceid");
            String serviceCode = rs.getString("servicecode");
            String localityCode = rs.getString("localitycode");
            String reviewstatus = rs.getString("reviewstatus");
            String campaignNumber = rs.getString("campaignnumber");
            boolean isDeleted = rs.getBoolean("isdeleted");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionaldetails", rs);

            // V2 Intermediate Billing - Parse period_statuses JSONB array
            List<RegisterPeriodStatus> periodStatuses = getPeriodStatuses("period_statuses", rs);

            AttendanceRegister attendanceRegister = AttendanceRegister.builder()
                    .additionalDetails(additionalDetails)
                    .id(id)
                    .tenantId(tenantId)
                    .status(Status.fromValue(status))
                    .registerNumber(registerNumber)
                    .referenceId(referenceId)
                    .serviceCode(serviceCode)
                    .name(name)
                    .startDate(startDate)
                    .endDate(endDate)
                    .auditDetails(auditDetails)
                    .localityCode(localityCode)
                    .reviewStatus(reviewstatus)
                    .campaignNumber(campaignNumber)
                    .periodStatuses(periodStatuses)
                    .isDeleted(isDeleted)
                    .build();

            if (!attendanceRegisterMap.containsKey(id)) {
                attendanceRegisterMap.put(id, attendanceRegister);
            }
        }
        return new ArrayList<>(attendanceRegisterMap.values());

    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }

    /**
     * V2 Intermediate Billing - Parse period_statuses JSONB array
     *
     * Parses the period_statuses JSONB column into a List of RegisterPeriodStatus objects.
     * Returns null if the column is NULL or empty array.
     *
     * @param columnName The name of the JSONB column to parse
     * @param rs The ResultSet containing the query results
     * @return List of RegisterPeriodStatus objects, or null if empty
     * @throws SQLException If there's an error reading the column
     */
    private List<RegisterPeriodStatus> getPeriodStatuses(String columnName, ResultSet rs) throws SQLException {
        List<RegisterPeriodStatus> periodStatuses = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null && obj.getValue() != null) {
                String jsonValue = obj.getValue();
                // Parse JSONB array into List<RegisterPeriodStatus>
                periodStatuses = mapper.readValue(jsonValue, new TypeReference<List<RegisterPeriodStatus>>() {});
                // Return null instead of empty list for consistency
                if (periodStatuses != null && periodStatuses.isEmpty()) {
                    periodStatuses = null;
                }
            }
        } catch (IOException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse period_statuses JSONB array: " + e.getMessage());
        }
        return periodStatuses;
    }
}
