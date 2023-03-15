package org.egov.repository.rowmapper;

import digit.models.coremodels.Document;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JurisdictionRowMapper implements ResultSetExtractor<Map<String, List<String>>> {
    @Override
    public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Map<String, List<String>>> jurisdictionMap = new LinkedHashMap<>();
        Map<String, List<String>> jurisdictionOrgCodeMap = new HashMap<>();

        while (rs.next()) {
            String jurisdiction_Id = rs.getString("jurisdiction_Id");
            String jurisdiction_orgId = rs.getString("jurisdiction_orgId");
            String jurisdiction_code = rs.getString("jurisdiction_code");
            String jurisdiction_additionalDetails = rs.getString("jurisdiction_additionalDetails");

            if (jurisdictionOrgCodeMap.containsKey(jurisdiction_orgId)) {
                jurisdictionOrgCodeMap.get(jurisdiction_orgId).add(jurisdiction_code);
            } else {
                List<String> jurisdictions = new ArrayList<>();
                jurisdictions.add(jurisdiction_code);
                jurisdictionOrgCodeMap.put(jurisdiction_orgId, jurisdictions);
            }

            if (!jurisdictionMap.containsKey(jurisdiction_Id)) {
                jurisdictionMap.put(jurisdiction_Id, jurisdictionOrgCodeMap);
            }
        }
//        if(jurisdictionMap.values() instanceof Map) {
//            return (Map<String, List<String>>) jurisdictionMap.values();
//        }

        return jurisdictionOrgCodeMap;
        //return new ArrayList<>(jurisdictionMap.values());
    }

}
