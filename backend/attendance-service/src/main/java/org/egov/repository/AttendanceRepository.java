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
    AttendeeRepository attendeeRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AttendanceRegister> getAttendanceRegister(AttendanceRegisterSearchCriteria searchCriteria) {

        List<String> registerIds;
        List<AttendanceRegister> registerAttendees = new ArrayList<>();

        if (StringUtils.isBlank(searchCriteria.getStaffId())) {
            registerAttendees = attendeeRepository.getRegisteredAttendees(searchCriteria);
            //Fetch the matched attendance registerIds to pass it to staffQueryBuilder
            registerIds = registerAttendees.stream()
                    .map(register -> register.getId().toString()).collect(Collectors.toList());
            searchCriteria.setIds(registerIds);
        }


        List<AttendanceRegister> registerStaffs = staffRepository.getRegisteredStaffs(searchCriteria);
        //If the search is based on staffId , fetch the matched staff registerIds to pass it to attendeeQueryBuilder
        if (StringUtils.isNotBlank(searchCriteria.getStaffId())) {
            registerIds = registerStaffs.stream()
                    .map(register -> register.getId().toString()).collect(Collectors.toList());
            searchCriteria.setIds(registerIds);
            registerAttendees = attendeeRepository.getRegisteredAttendees(searchCriteria);
        }


        List<AttendanceRegister> attendanceRegisterList = new ArrayList<>();
        for (AttendanceRegister attendeeRegister : registerAttendees) {
            AttendanceRegister staff = registerStaffs.stream().filter(staffRegister -> staffRegister.getId().toString().equalsIgnoreCase(attendeeRegister.getId().toString()))
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
