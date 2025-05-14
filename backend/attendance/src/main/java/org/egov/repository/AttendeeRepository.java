package org.egov.repository;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.repository.querybuilder.AttendeeQueryBuilder;
import org.egov.repository.rowmapper.AttendeeRowMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendeeSearchCriteria;
import org.egov.web.models.IndividualEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.egov.Constants.INVALID_TENANT_ID;
import static org.egov.Constants.INVALID_TENANT_ID_MSG;

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

    public List<IndividualEntry> getAttendees(String tenantId, AttendeeSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = null;
        try {
            query = queryBuilder.getAttendanceAttendeeSearchQuery(tenantId, searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, INVALID_TENANT_ID_MSG);
        }
        List<IndividualEntry> attendanceStaffList = jdbcTemplate.query(query, attendeeRowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
