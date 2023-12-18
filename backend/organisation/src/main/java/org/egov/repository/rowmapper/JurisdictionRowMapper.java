package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.tracer.model.CustomException;
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

    private final ObjectMapper mapper;

    @Autowired
    public JurisdictionRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Jurisdiction> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Jurisdiction> jurisdictionMap = new LinkedHashMap<>();

        while (rs.next()) {
            String jurisdictionId = rs.getString("jurisdiction_Id");
            String jurisdictionOrgId = rs.getString("jurisdiction_orgId");
            String jurisdictionCode = rs.getString("jurisdiction_code");
            JsonNode jurisdictionAdditionalDetails = getAdditionalDetail("jurisdiction_additionalDetails", rs);

            Jurisdiction jurisdiction = Jurisdiction.builder()
                    .id(jurisdictionId)
                    .orgId(jurisdictionOrgId)
                    .code(jurisdictionCode)
                    .additionalDetails(jurisdictionAdditionalDetails)
                    .build();

            jurisdictionMap.computeIfAbsent(jurisdictionId, k -> jurisdiction);
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
