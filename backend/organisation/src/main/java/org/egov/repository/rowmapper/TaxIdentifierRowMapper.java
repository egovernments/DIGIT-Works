package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper mapper;

    @Autowired
    public TaxIdentifierRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Identifier> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Identifier> identifierMap = new LinkedHashMap<>();

        while (rs.next()) {

            String taxIdentifierId = rs.getString("taxIdentifier_Id");
            String taxIdentifierOrgId = rs.getString("taxIdentifier_orgId");
            String taxIdentifierType = rs.getString("taxIdentifier_type");
            String taxIdentifierValue = rs.getString("taxIdentifier_value");
            boolean taxIdentifierActive = rs.getBoolean("taxIdentifier_active");
            JsonNode taxIdentifierAdditionalDetails = getAdditionalDetail("taxIdentifier_additionalDetails", rs);

            Identifier identifier = Identifier.builder()
                    .id(taxIdentifierId)
                    .orgId(taxIdentifierOrgId)
                    .type(taxIdentifierType)
                    .value(taxIdentifierValue)
                    .isActive(taxIdentifierActive)
                    .additionalDetails(taxIdentifierAdditionalDetails)
                    .build();


            identifierMap.computeIfAbsent(taxIdentifierId, id -> identifier);
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
