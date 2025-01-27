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
import java.util.List;

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
        log.info("Query of get register : " + query);
        log.info("preparedStmtList of get register : " + preparedStmtList.toString());
        List<AttendanceRegister> attendanceRegisterList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceRegisterList;
    }

}
