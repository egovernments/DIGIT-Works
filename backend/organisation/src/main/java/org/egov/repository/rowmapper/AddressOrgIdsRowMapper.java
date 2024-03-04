package org.egov.repository.rowmapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AddressOrgIdsRowMapper implements ResultSetExtractor<Set<String>> {

    @Override
    public Set<String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Set<String> orgIds = new HashSet<>();

        while (resultSet.next()) {
            String addressOrgId = resultSet.getString("address_orgId");
            orgIds.add(addressOrgId);
        }

        return orgIds;
    }
}
