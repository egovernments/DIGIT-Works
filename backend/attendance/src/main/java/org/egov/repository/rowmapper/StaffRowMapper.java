package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffType;
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

@Component
public class StaffRowMapper implements ResultSetExtractor<List<StaffPermission>> {

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Override
    public List<StaffPermission> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, StaffPermission> attendanceStaffMap = new LinkedHashMap<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String tenantId = rs.getString("tenantid");
            String individuaId = rs.getString("individual_id");
            String registerId = rs.getString("register_id");
            BigDecimal enrollmentDate = rs.getBigDecimal("enrollment_date");
            BigDecimal deenrollmentDate = rs.getBigDecimal("deenrollment_date");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");
            List<StaffType> staffType = getStaffType(rs);

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionaldetails", rs);

            StaffPermission attendanceStaff = StaffPermission.builder()
                    .additionalDetails(additionalDetails)
                    .id(id)
                    .tenantId(tenantId)
                    .userId(individuaId)
                    .registerId(registerId)
                    .additionalDetails(additionalDetails)
                    .enrollmentDate(enrollmentDate)
                    .denrollmentDate(deenrollmentDate)
                    .auditDetails(auditDetails)
                    .staffType(staffType)
                    .build();

            if (!attendanceStaffMap.containsKey(id)) {
                attendanceStaffMap.put(id, attendanceStaff);
            }
        }
        return new ArrayList<>(attendanceStaffMap.values());
    }

    public List<StaffType> getStaffType(ResultSet rs) {
        JsonNode staffType = null;
        ObjectMapper mapper = new ObjectMapper(); // You can reuse this ObjectMapper if it's already instantiated elsewhere in your code

        try {
            PGobject obj = (PGobject) rs.getObject("stafftype");
            if (obj != null) {
                staffType = mapper.readTree(obj.getValue());
            }
        } catch (IOException | SQLException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }

        if (staffType != null && !staffType.isEmpty()) {
            List<StaffType> staffTypesList = new ArrayList<>();
            for (JsonNode node : staffType) {
                // Convert each string in the array to the corresponding enum
                try {
                    staffTypesList.add(StaffType.valueOf(node.asText()));
                } catch (IllegalArgumentException e) {
                    // Handle invalid enum string (optional)
                    throw new CustomException("INVALID_ENUM", "Invalid staff type value: " + node.asText());
                }
            }
            return staffTypesList;
        } else {
            return null; // or return an empty list if you prefer
        }
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
}
