package org.egov.repository;

import org.egov.repository.querybuilder.AttendanceAttendeeQueryBuilder;
import org.egov.repository.rowmapper.AttendanceAttendeeRowMapper;
import org.egov.web.models.AttendanceAttendeeSearchCriteria;
import org.egov.web.models.IndividualEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceAttendeeRepository {
    @Autowired
    private AttendanceAttendeeRowMapper rowMapper;

    @Autowired
    private AttendanceAttendeeQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<IndividualEntry> getAttendanceAttendee(AttendanceAttendeeSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceAttendeeSearchQuery(searchCriteria, preparedStmtList);
        List<IndividualEntry> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
