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

    /**
     * This method fetches the list of attendees based on the search criteria provided.
     *
     * @param tenantId        The tenant ID for which the search is being performed.
     * @param searchCriteria   The criteria used to filter the attendees.
     * @return List<IndividualEntry> A list of IndividualEntry objects representing the attendees.
     */
    public List<IndividualEntry> getAttendees(String tenantId, AttendeeSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = null;
        // Wrapped query construction in try-catch to handle tenant ID validation failure
        try {
            query = queryBuilder.getAttendanceAttendeeSearchQuery(tenantId, searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, e.getMessage());
        }
        List<IndividualEntry> attendanceStaffList = jdbcTemplate.query(query, attendeeRowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
