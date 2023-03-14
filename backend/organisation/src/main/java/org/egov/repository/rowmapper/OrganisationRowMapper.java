package org.egov.repository.rowmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.web.models.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganisationRowMapper implements ResultSetExtractor<List<Organisation>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Organisation> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return null;
    }

}
