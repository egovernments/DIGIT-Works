package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.RegisterQueryBuilder;
import org.egov.repository.rowmapper.RegisterRowMapper;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.AttendanceServiceConstants.STATUS_MAP;

@Repository
@Slf4j
public class RegisterRepository {

    private final RegisterRowMapper rowMapper;

    private final RegisterQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RegisterRepository(RegisterRowMapper rowMapper, RegisterQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AttendanceRegister> getRegister(AttendanceRegisterSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceRegisterSearchQuery(searchCriteria, preparedStmtList, false);
        query = queryBuilder.addPaginationWrapper(query.toString(), preparedStmtList, searchCriteria);

        log.info("Query of get register : " + query);
        log.info("preparedStmtList of get register : " + preparedStmtList.toString());

        List<AttendanceRegister> attendanceRegisterList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());

        return attendanceRegisterList;
    }

    public Map<String, Long> getRegisterCounts(AttendanceRegisterSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceRegisterSearchQuery(searchCriteria, preparedStmtList, true);

        log.info("Query of get register : " + query);
        log.info("preparedStmtList of get register : " + preparedStmtList.toString());

// Dynamically construct the SUM(CASE WHEN ...) parts
        StringBuilder statusCountsQuery = new StringBuilder();
        for (Map.Entry<String, String> entry : STATUS_MAP.entrySet()) {
            String status = entry.getValue();
            String alias = entry.getKey();
            statusCountsQuery.append(", SUM(CASE WHEN reg.paymentstatus = '")
                    .append(status)
                    .append("' THEN 1 ELSE 0 END) AS ")
                    .append(alias);
        }

// Construct the full query
        String cteQuery = "WITH result_cte AS (" + query + "), " +
                "totalCount_cte AS (SELECT COUNT(*) AS totalCount " +
                statusCountsQuery +
                " FROM result_cte reg) SELECT * FROM totalCount_cte";

        log.info("Constructed Query: " + cteQuery);

// Execute the query and extract results
        Map<String, Long> counts = jdbcTemplate.queryForObject(cteQuery, (resultSet, rowNum) -> {
            Map<String, Long> result = new HashMap<>();
            result.put("totalCount", resultSet.getLong("totalCount"));

            // Dynamically extract counts for each status
            for (Map.Entry<String, String> entry : STATUS_MAP.entrySet()) {
                String alias = entry.getKey();
                result.put(alias, resultSet.getLong(alias));
            }

            return result;
        }, preparedStmtList.toArray());

        return counts;
    }
}
