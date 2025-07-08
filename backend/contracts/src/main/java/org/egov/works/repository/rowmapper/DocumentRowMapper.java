package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Document;
import org.egov.works.web.models.Status;
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
public class DocumentRowMapper implements ResultSetExtractor<List<Document>> {

    private final ObjectMapper mapper;

    @Autowired
    public DocumentRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Document> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, Document> documentMap = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("id");
            String fileStoreId = rs.getString("filestoreId");
            String documentType = rs.getString("documentType");
            String documentUid = rs.getString("documentUid");
            String status = rs.getString("status");
            String contractId = rs.getString("contractIid");
            String createdby = rs.getString("createdby");
            String lastmodifiedby = rs.getString("lastModifiedBy");
            Long createdtime = rs.getLong("createdTime");
            Long lastmodifiedtime = rs.getLong("lastModifiedTime");

            AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
                    .lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
                    .build();

            JsonNode additionalDetails = getAdditionalDetail("additionalDetails", rs);

            Document document = Document.builder()
                    .id(id)
                    .fileStore(fileStoreId)
                    .documentType(documentType)
                    .documentUid(documentUid)
                    .status(Status.fromValue(status))
                    .contractId(contractId)
                    .additionalDetails(additionalDetails)
                    .auditDetails(auditDetails)
                    .build();

            if (!documentMap.containsKey(id)) {
                documentMap.put(id, document);
            }
        }
        return new ArrayList<>(documentMap.values());
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
        if (additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
