package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.config.AttendanceServiceConfiguration;
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

@Repository
@Slf4j
public class RegisterRepository {

    private final RegisterRowMapper rowMapper;

    private final RegisterQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    private final AttendanceServiceConfiguration config;

    @Autowired
    public RegisterRepository(RegisterRowMapper rowMapper, RegisterQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate, AttendanceServiceConfiguration config) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
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
        if(config.getAttendanceRegisterReviewStatusEnabled()){
            for (Map.Entry<String, String> entry : config.getAttendanceRegisterStatusMap().entrySet()) {
                String status = entry.getValue();
                String alias = entry.getKey();
                statusCountsQuery.append(", SUM(CASE WHEN reg.reviewstatus = '")
                  .append(status)
                  .append("' THEN 1 ELSE 0 END) AS ")
                  .append(alias);
            }
        }

        /* Construct the full SQL query using Common Table Expressions (CTE)
        The first CTE (`result_cte`) contains the main search query
        The second CTE (`totalCount_cte`) calculates the total count and counts per status */
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
            if(config.getAttendanceRegisterReviewStatusEnabled()){
                for (Map.Entry<String, String> entry : config.getAttendanceRegisterStatusMap().entrySet()) {
                    String alias = entry.getKey();
                    result.put(alias, resultSet.getLong(alias));
                }
            }

            return result;
        }, preparedStmtList.toArray());

        // If a specific reviewStatus is provided in the search criteria, update the totalCount
        if(searchCriteria.getReviewStatus()!=null) {
            config.getAttendanceRegisterStatusMap().forEach((key, value) ->{
                if(searchCriteria.getReviewStatus().equals(value)) {
                    counts.put("totalCount", counts.get(key));
                }
            });
        }

        return counts;
    }
}
