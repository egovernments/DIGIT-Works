package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.Document;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
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

            String documentId = rs.getString("document_Id");
            String documentOrgId = rs.getString("document_orgId");
            String documentOrgFuncId = rs.getString("document_orgFuncId");
            String documentDocumentType = rs.getString("document_documentType");
            String documentFileStore = rs.getString("document_fileStore");
            String documentDocumentUid = rs.getString("document_documentUid");
            boolean documentActive = rs.getBoolean("document_active");
            JsonNode documentAdditionalDetails = getAdditionalDetail("document_additionalDetails", rs);

            Document document = Document.builder()
                    .id(documentId)
                    .orgId(documentOrgId)
                    .orgFunctionId(documentOrgFuncId)
                    .documentType(documentDocumentType)
                    .fileStore(documentFileStore)
                    .documentUid(documentDocumentUid)
                    .isActive(documentActive)
                    .additionalDetails(documentAdditionalDetails)
                    .build();


            documentMap.computeIfAbsent(documentId, k -> document);
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
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
