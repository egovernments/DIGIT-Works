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
public class StaffRepository {

    @Autowired
    private StaffQueryBuilder staffQueryBuilder;

    @Autowired
    private StaffRowMapper staffRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceRegister> getRegisteredStaffs(AttendanceRegisterSearchCriteria searchCriteria) {

        List<Object> preparedStmtList = new ArrayList<>();
        String staffQuery = staffQueryBuilder.getStaffSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceRegister> registerStaffs = jdbcTemplate.query(staffQuery, staffRowMapper, preparedStmtList.toArray());

        return registerStaffs;
    }
}
