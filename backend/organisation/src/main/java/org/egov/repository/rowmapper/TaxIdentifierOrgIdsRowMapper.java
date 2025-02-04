package org.egov.repository.rowmapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TaxIdentifierOrgIdsRowMapper implements ResultSetExtractor<Set<String>> {

    @Override
    public Set<String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Set<String> orgIds = new HashSet<>();

        while (resultSet.next()) {
            String taxIdentifierOrgId = resultSet.getString("taxIdentifier_orgId");
            orgIds.add(taxIdentifierOrgId);
        }

        return orgIds;
    }
}
