package org.egov.repository;

import org.apache.commons.lang3.StringUtils;
import org.egov.repository.rowmapper.AttendeeQueryBuilder;
import org.egov.repository.rowmapper.AttendeeRowMapper;
import org.egov.repository.rowmapper.StaffQueryBuilder;
import org.egov.repository.rowmapper.StaffRowMapper;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AttendeeRepository {

    @Autowired
    private AttendeeQueryBuilder attendeeQueryBuilder;

    @Autowired
    private AttendeeRowMapper attendeeRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceRegister> getRegisteredAttendees(AttendanceRegisterSearchCriteria searchCriteria) {

        List<Object> preparedStmtList = new ArrayList<>();
        String attendeeQuery = attendeeQueryBuilder.getAttendeeSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceRegister> registerAttendees = jdbcTemplate.query(attendeeQuery, attendeeRowMapper, preparedStmtList.toArray());

       return registerAttendees;

    }

}
