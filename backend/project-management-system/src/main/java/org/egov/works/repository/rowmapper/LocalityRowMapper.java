package org.egov.works.repository.rowmapper;

import org.egov.works.web.models.Boundary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocalityRowMapper implements ResultSetExtractor<List<Boundary>> {
    @Override
    public List<Boundary> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Boundary> boundaryMap = new LinkedHashMap<>();

        while (rs.next()) {

            String locality_id = rs.getString("locality_id");
            String locality_addressid = rs.getString("locality_addressid");
            String locality_parentid = rs.getString("locality_parentid");
            String locality_code = rs.getString("locality_code");
            String locality_name = rs.getString("locality_name");
            String locality_latitude = rs.getString("locality_latitude");
            String locality_longitude = rs.getString("locality_longitude");
            String locality_materializedpath = rs.getString("locality_materializedpath");


            Boundary boundary = Boundary.builder()
                    .id(locality_id)
                    .addressid(locality_addressid)
                    .parentid(locality_parentid)
                    .name(locality_name)
                    .code(locality_code)
                    .latitude(locality_latitude)
                    .longitude(locality_longitude)
                    .materializedPath(locality_materializedpath)
                    .build();

            if (!boundaryMap.containsKey(locality_id)) {
                boundaryMap.put(locality_id, boundary);
            }
        }

        return new ArrayList<>(boundaryMap.values());
    }
}
