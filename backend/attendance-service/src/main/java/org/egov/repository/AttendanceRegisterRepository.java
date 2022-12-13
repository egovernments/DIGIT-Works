package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.AttendanceRegisterQueryBuilder;
import org.egov.repository.rowmapper.AttendanceRegisterRowMapper;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class AttendanceRegisterRepository {

    @Autowired
    private AttendanceRegisterRowMapper rowMapper;

    @Autowired
    private AttendanceRegisterQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceRegister> getAttendanceRegister(AttendanceRegisterSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceRegisterSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceRegister> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
