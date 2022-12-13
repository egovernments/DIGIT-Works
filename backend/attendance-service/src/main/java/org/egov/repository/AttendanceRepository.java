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
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AttendanceRepository {

    @Autowired
    private AttendeeQueryBuilder attendeeQueryBuilder;

    @Autowired
    private StaffQueryBuilder staffQueryBuilder;

    @Autowired
    private AttendeeRowMapper attendeeRowMapper;

    @Autowired
    private StaffRowMapper staffRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceRegister> getAttendanceRegister(AttendanceRegisterSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        List<String> registerIds = new ArrayList<>();

        String attendeeQuery = attendeeQueryBuilder.getAttendeeSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceRegister> attendanceRegisters = jdbcTemplate.query(attendeeQuery, attendeeRowMapper, preparedStmtList.toArray());

        //If the search is based on attendeeId , fetch the matched attendance registerIds to pass it to staffQueryBuilder
        if (StringUtils.isNotBlank(searchCriteria.getAttendeeId())) {
            registerIds = attendanceRegisters.stream()
                    .map(register -> register.getId().toString()).collect(Collectors.toList());
        }

        preparedStmtList = new ArrayList<>();
        String staffQuery = staffQueryBuilder.getStaffSearchQuery(searchCriteria, preparedStmtList,registerIds);
        List<AttendanceRegister> staffList = jdbcTemplate.query(staffQuery, staffRowMapper, preparedStmtList.toArray());

        //Merge the results
        for (int i=0; i < attendanceRegisters.size(); i++) {
            AttendanceRegister register = attendanceRegisters.get(i);
            AttendanceRegister staff = staffList.get(i);
            if (null != register && null != staff &&
                    register.getId().toString().equalsIgnoreCase(staff.getId().toString())) {
                register.setStaff(staff.getStaff());
            }
        }

        return attendanceRegisters;
    }

}
