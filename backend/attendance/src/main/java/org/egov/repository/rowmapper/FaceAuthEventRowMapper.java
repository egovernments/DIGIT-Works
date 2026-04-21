package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.FaceAuthEvent;
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
import java.util.*;

@Component
@Slf4j
public class FaceAuthEventRowMapper implements ResultSetExtractor<List<FaceAuthEvent>> {

    private final ObjectMapper mapper;

    @Autowired
    public FaceAuthEventRowMapper(@Qualifier("objectMapper") ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<FaceAuthEvent> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, FaceAuthEvent> eventMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("faeId");

            if (eventMap.containsKey(id)) {
                continue;
            }

            AuditDetails auditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("faeCreatedBy"))
                    .createdTime(rs.getLong("faeCreatedTime"))
                    .lastModifiedBy(rs.getString("faeLastModifiedBy"))
                    .lastModifiedTime(rs.getLong("faeLastModifiedTime"))
                    .build();

            AuditDetails clientAuditDetails = AuditDetails.builder()
                    .createdBy(rs.getString("faeClientCreatedBy"))
                    .createdTime(rs.getLong("faeClientCreatedTime"))
                    .lastModifiedBy(rs.getString("faeClientLastModifiedBy"))
                    .lastModifiedTime(rs.getLong("faeClientLastModifiedTime"))
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("faeAdditionalDetails", rs);

            Integer failedAttemptCount = rs.getInt("faeFailedAttemptCount");
            if (rs.wasNull()) failedAttemptCount = null;

            FaceAuthEvent event = FaceAuthEvent.builder()
                    .id(id)
                    .clientReferenceId(rs.getString("faeClientReferenceId"))
                    .tenantId(rs.getString("faeTenantId"))
                    .individualId(rs.getString("faeIndividualId"))
                    .deviceId(rs.getString("faeDeviceId"))
                    .eventType(rs.getString("faeEventType"))
                    .outcome(rs.getString("faeOutcome"))
                    .confidence(rs.getBigDecimal("faeConfidence"))
                    .latitude(rs.getBigDecimal("faeLatitude"))
                    .longitude(rs.getBigDecimal("faeLongitude"))
                    .locationAccuracy(rs.getBigDecimal("faeLocationAccuracy"))
                    .timestamp(rs.getBigDecimal("faeTimestamp"))
                    .failedAttemptCount(failedAttemptCount)
                    .popupTime(rs.getBigDecimal("faePopupTime"))
                    .responseTime(rs.getBigDecimal("faeResponseTime"))
                    .responseType(rs.getString("faeResponseType"))
                    .faceImage(rs.getString("faeFaceImage"))
                    .anomalyFlags(rs.getString("faeAnomalyFlags"))
                    .projectId(rs.getString("faeProjectId"))
                    .boundaryCode(rs.getString("faeBoundaryCode"))
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .clientAuditDetails(clientAuditDetails)
                    .build();

            eventMap.put(id, event);
        }
        return new ArrayList<>(eventMap.values());
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
