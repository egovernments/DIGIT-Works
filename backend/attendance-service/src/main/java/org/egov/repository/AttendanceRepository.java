package org.egov.repository;

import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {

    public List<AttendanceRegister> getAttendanceRegister(AttendanceRegisterSearchCriteria searchCriteria) {
        List<AttendanceRegister> attendanceRegisters = new ArrayList<>();
        return attendanceRegisters;
    }
}
