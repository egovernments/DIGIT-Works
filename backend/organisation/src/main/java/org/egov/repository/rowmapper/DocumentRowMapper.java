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

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Document> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Document> documentMap = new LinkedHashMap<>();

        while (rs.next()) {

            String document_Id = rs.getString("document_Id");
            String document_orgId = rs.getString("document_orgId");
            String document_orgFuncId = rs.getString("document_orgFuncId");
            String document_documentType = rs.getString("document_documentType");
            String document_fileStore = rs.getString("document_fileStore");
            String document_documentUid = rs.getString("document_documentUid");
            boolean document_active = rs.getBoolean("document_active");
            JsonNode document_additionalDetails = getAdditionalDetail("document_additionalDetails", rs);

            Document document = Document.builder()
                    .id(document_Id)
                    .orgId(document_orgId)
                    .orgFunctionId(document_orgFuncId)
                    .documentType(document_documentType)
                    .fileStore(document_fileStore)
                    .documentUid(document_documentUid)
                    .isActive(document_active)
                    .additionalDetails(document_additionalDetails)
                    .build();

            if (!documentMap.containsKey(document_Id)) {
                documentMap.put(document_Id, document);
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
            throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails != null && additionalDetails.isEmpty())
            additionalDetails = null;
        return additionalDetails;
    }
}
