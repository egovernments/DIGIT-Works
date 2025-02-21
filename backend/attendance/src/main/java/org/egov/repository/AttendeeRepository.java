package org.egov.repository;

import org.egov.repository.querybuilder.AttendeeQueryBuilder;
import org.egov.repository.rowmapper.AttendeeRowMapper;
import org.egov.web.models.AttendeeSearchCriteria;
import org.egov.web.models.IndividualEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendeeRepository {
    private final AttendeeRowMapper attendeeRowMapper;

    private final AttendeeQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttendeeRepository(AttendeeRowMapper attendeeRowMapper, AttendeeQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.attendeeRowMapper = attendeeRowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IndividualEntry> getAttendees(AttendeeSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceAttendeeSearchQuery(searchCriteria, preparedStmtList);
        List<IndividualEntry> attendanceStaffList = jdbcTemplate.query(query, attendeeRowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
