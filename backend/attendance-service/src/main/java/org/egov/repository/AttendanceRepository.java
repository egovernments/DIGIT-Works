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
        List<String> registerIds;
        List<AttendanceRegister> attendeeRegisters = null;

        if (StringUtils.isBlank(searchCriteria.getStaffId())) {
            String attendeeQuery = attendeeQueryBuilder.getAttendeeSearchQuery(searchCriteria, preparedStmtList);
            attendeeRegisters = jdbcTemplate.query(attendeeQuery, attendeeRowMapper, preparedStmtList.toArray());

            //If the search is based on attendeeId , fetch the matched attendance registerIds to pass it to staffQueryBuilder
            if (StringUtils.isNotBlank(searchCriteria.getAttendeeId())) {
                registerIds = attendeeRegisters.stream()
                        .map(register -> register.getId().toString()).collect(Collectors.toList());
                searchCriteria.setIds(registerIds);
            }
        }

        preparedStmtList = new ArrayList<>();
        String staffQuery = staffQueryBuilder.getStaffSearchQuery(searchCriteria, preparedStmtList);
        List<AttendanceRegister> staffRegisters = jdbcTemplate.query(staffQuery, staffRowMapper, preparedStmtList.toArray());

        if (StringUtils.isNotBlank(searchCriteria.getStaffId())) {
            registerIds = staffRegisters.stream()
                    .map(register -> register.getId().toString()).collect(Collectors.toList());
            searchCriteria.setIds(registerIds);
            preparedStmtList = new ArrayList<>();
            String attendeeQuery = attendeeQueryBuilder.getAttendeeSearchQuery(searchCriteria, preparedStmtList);
            attendeeRegisters = jdbcTemplate.query(attendeeQuery, attendeeRowMapper, preparedStmtList.toArray());

        }

        List<AttendanceRegister> attendanceRegisterList = new ArrayList<>();
        for (AttendanceRegister attendeeRegister : attendeeRegisters) {
            AttendanceRegister staff = staffRegisters.stream().filter(staffRegister -> staffRegister.getId().toString().equalsIgnoreCase(attendeeRegister.getId().toString()))
                                             .findFirst().orElse(null);
            if (staff != null) {
                AttendanceRegister attendanceRegister = attendeeRegister;
                attendanceRegister.setStaff(staff.getStaff());
                attendanceRegisterList.add(attendanceRegister);
            }
        }

        return attendanceRegisterList;
    }

}
