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
        String query = queryBuilder.getAttendanceRegisterSearchQuery(searchCriteria, preparedStmtList);
        query = queryBuilder.addPaginationWrapper(query.toString(), preparedStmtList, searchCriteria);

        log.info("Query of get register : " + query);
        log.info("preparedStmtList of get register : " + preparedStmtList.toString());

        List<AttendanceRegister> attendanceRegisterList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());

        return attendanceRegisterList;
    }

    public Long[] getRegisterCounts(AttendanceRegisterSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceRegisterSearchQuery(searchCriteria, preparedStmtList);

        log.info("Query of get register : " + query);
        log.info("preparedStmtList of get register : " + preparedStmtList.toString());

//        Create a query to get the total count without limit and offset
        String cteQuery = "WITH result_cte AS (" + query + "), totalCount_cte AS (SELECT COUNT(*) AS totalRows, SUM(CASE WHEN reg.paymentstatus = 'APPROVED' THEN 1 ELSE 0 END) AS approvedCount, SUM(CASE WHEN reg.paymentstatus = 'PENDINGFORAPPROVAL' THEN 1 ELSE 0 END) AS pendingCount FROM result_cte reg) SELECT * FROM totalCount_cte";

// Execute the query and extract the total count, approved count, and pending count
        Map<String, Long> counts = jdbcTemplate.queryForObject(cteQuery, (resultSet, rowNum) -> {
            long totalRows = resultSet.getLong("totalRows");
            long approvedCount = resultSet.getLong("approvedCount");
            long pendingCount = resultSet.getLong("pendingCount");

            Map<String, Long> result = new HashMap<>();
            result.put("totalRows", totalRows);
            result.put("approvedCount", approvedCount);
            result.put("pendingCount", pendingCount);

            return result;
        }, preparedStmtList.toArray());

        long totalCount = counts.get("totalRows");
        long approvedCount = counts.get("approvedCount");
        long pendingCount = counts.get("pendingCount");

        return new Long[]{totalCount,approvedCount,pendingCount};
    }
}
