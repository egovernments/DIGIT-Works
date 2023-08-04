package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.enums.JitRespStatusForPI;
import org.egov.web.models.jit.*;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PIStatusLogsRowMapper implements ResultSetExtractor<List<PIStatusLog>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<PIStatusLog> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, PIStatusLog> piStatusLogMap = new LinkedHashMap<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String piId = rs.getString("piId");
            JITServiceId serviceId = JITServiceId.fromValue(rs.getString("serviceId"));
            JitRespStatusForPI status = JitRespStatusForPI.fromValue(rs.getString("status"));
            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastmodifiedby");
            Long createdtime = rs.getLong("createdtime");
            Long lastmodifiedtime = rs.getLong("lastmodifiedtime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            PIStatusLog piStatusLog = PIStatusLog.builder()
                    .id(id)
                    .piId(piId)
                    .serviceId(serviceId)
                    .status(status)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!piStatusLogMap.containsKey(id)) {
                piStatusLogMap.put(id, piStatusLog);
            }
        }
        return new ArrayList<>(piStatusLogMap.values());
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
