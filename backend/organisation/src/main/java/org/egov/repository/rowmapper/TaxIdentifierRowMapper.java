package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.Document;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.Identifier;
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
public class TaxIdentifierRowMapper implements ResultSetExtractor<List<Identifier>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Identifier> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Identifier> identifierMap = new LinkedHashMap<>();

        while (rs.next()) {

            String taxIdentifier_Id = rs.getString("taxIdentifier_Id");
            String taxIdentifier_orgId = rs.getString("taxIdentifier_orgId");
            String taxIdentifier_type = rs.getString("taxIdentifier_type");
            String taxIdentifier_value = rs.getString("taxIdentifier_value");
            boolean taxIdentifier_active = rs.getBoolean("taxIdentifier_active");
            JsonNode taxIdentifier_additionalDetails = getAdditionalDetail("taxIdentifier_additionalDetails", rs);

            Identifier identifier = Identifier.builder()
                    .id(taxIdentifier_Id)
                    .orgId(taxIdentifier_orgId)
                    .type(taxIdentifier_type)
                    .value(taxIdentifier_value)
                    .isActive(taxIdentifier_active)
                    .additionalDetails(taxIdentifier_additionalDetails)
                    .build();

            if (!identifierMap.containsKey(taxIdentifier_Id)) {
                identifierMap.put(taxIdentifier_Id, identifier);
            }
        }

        return new ArrayList<>(identifierMap.values());
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
