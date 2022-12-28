package org.egov.works.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Document;
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

            String document_id = rs.getString("document_id");
            String document_projectid = rs.getString("document_projectid");
            String document_documenttype = rs.getString("document_documenttype");
            String document_filestore_id = rs.getString("document_filestore_id");
            String document_documentuid = rs.getString("document_documentuid");
            JsonNode document_additionaldetails = getAdditionalDetail("document_additionaldetails", rs);
            String document_status = rs.getString("document_status");
            String document_createdby = rs.getString("document_createdby");
            String document_lastmodifiedby = rs.getString("document_lastmodifiedby");
            Long document_createdtime = rs.getLong("document_createdtime");
            Long document_lastmodifiedtime = rs.getLong("document_lastmodifiedtime");

            AuditDetails documenttAuditDetails = AuditDetails.builder().createdBy(document_createdby).createdTime(document_createdtime)
                    .lastModifiedBy(document_lastmodifiedby).lastModifiedTime(document_lastmodifiedtime)
                    .build();

            Document document = Document.builder()
                    .id(document_id)
                    .projectid(document_projectid)
                    .documentType(document_documenttype)
                    .fileStore(document_filestore_id)
                    .documentUid(document_documentuid)
                    .additionalDetails(document_additionaldetails)
                    .status(document_status)
                    .auditDetails(documenttAuditDetails)
                    .build();

            if (!documentMap.containsKey(document_id)) {
                documentMap.put(document_id, document);
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
