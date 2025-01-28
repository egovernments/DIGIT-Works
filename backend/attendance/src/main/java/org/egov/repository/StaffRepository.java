package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.StaffQueryBuilder;
import org.egov.repository.rowmapper.StaffRowMapper;
import org.egov.web.models.StaffSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class StaffRepository {
    private final StaffRowMapper rowMapper;

    private final StaffQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StaffRepository(StaffRowMapper rowMapper, StaffQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StaffPermission> getActiveStaff(StaffSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getActiveAttendanceStaffSearchQuery(searchCriteria, preparedStmtList);
        List<StaffPermission> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }

    public List<StaffPermission> getAllStaff(StaffSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getAttendanceStaffSearchQuery(searchCriteria, preparedStmtList);
        List<StaffPermission> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }

    /**
     * Retrieves a list of staff permissions based on the provided search criteria.
     *
     * @param searchCriteria The criteria to use for searching staff permissions
     * @return A list of staff permissions
     */
    public List<StaffPermission> getFirstStaff(StaffSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.appendOrderLimit(queryBuilder.getAttendanceStaffSearchQuery(searchCriteria, preparedStmtList));
        List<StaffPermission> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
