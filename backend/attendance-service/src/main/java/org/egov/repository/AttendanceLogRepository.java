package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.AttendanceLogQueryBuilder;
import org.egov.repository.rowmapper.AttendanceLogRowMapper;
import org.egov.web.models.AttendanceLog;
import org.egov.models.AttendanceLogSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AttendanceLogRepository {
    @Autowired
    private AttendanceLogRowMapper rowMapper;

    @Autowired
    private AttendanceLogQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceLog> getAttendanceLogs(AttendanceLogSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceLogSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceLog> attendanceLogList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceLogList;
    }
}
