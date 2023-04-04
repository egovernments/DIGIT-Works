package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.Identifier;
import org.egov.web.models.Jurisdiction;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JurisdictionRowMapper implements ResultSetExtractor<List<Jurisdiction>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Jurisdiction> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Jurisdiction> jurisdictionMap = new LinkedHashMap<>();

        while (rs.next()) {
            String jurisdiction_Id = rs.getString("jurisdiction_Id");
            String jurisdiction_orgId = rs.getString("jurisdiction_orgId");
            String jurisdiction_code = rs.getString("jurisdiction_code");
            JsonNode jurisdiction_additionalDetails = getAdditionalDetail("jurisdiction_additionalDetails", rs);

            Jurisdiction jurisdiction = Jurisdiction.builder()
                    .id(jurisdiction_Id)
                    .orgId(jurisdiction_orgId)
                    .code(jurisdiction_code)
                    .additionalDetails(jurisdiction_additionalDetails)
                    .build();

            if (!jurisdictionMap.containsKey(jurisdiction_Id)) {
                jurisdictionMap.put(jurisdiction_Id, jurisdiction);
            }
        }

        return new ArrayList<>(jurisdictionMap.values());
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
