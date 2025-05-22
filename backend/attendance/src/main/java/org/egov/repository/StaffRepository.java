package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.repository.querybuilder.StaffQueryBuilder;
import org.egov.repository.rowmapper.StaffRowMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.StaffSearchCriteria;
import org.egov.web.models.StaffPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.egov.Constants.INVALID_TENANT_ID;
import static org.egov.Constants.INVALID_TENANT_ID_MSG;

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

    /**
     * Retrieves a list of active staff permissions based on the provided search criteria.
     *
     * @param searchCriteria The criteria to use for searching staff permissions
     * @return A list of active staff permissions
     */
    public List<StaffPermission> getActiveStaff(StaffSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String tenantId = searchCriteria.getTenantId();
        String query = null;
        // Wrap query construction in try-catch to handle invalid tenant scenarios gracefully
        try {
            query = queryBuilder.getActiveAttendanceStaffSearchQuery( searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, INVALID_TENANT_ID_MSG);
        }
        List<StaffPermission> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }

    /**
     * Retrieves a list of all staff permissions based on the provided search criteria.
     *
     * @param searchCriteria The criteria to use for searching staff permissions
     * @return A list of all staff permissions
     */
    public List<StaffPermission> getAllStaff(StaffSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String tenantId = searchCriteria.getTenantId();
        String query = null;
        // Wrap query construction in try-catch to handle invalid tenant scenarios gracefully
        try {
            query = queryBuilder.getAttendanceStaffSearchQuery( searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, INVALID_TENANT_ID_MSG);
        }
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
        String tenantId = searchCriteria.getTenantId();
        String query = null;
        try {
            query = queryBuilder.appendOrderLimit(queryBuilder.getAttendanceStaffSearchQuery( searchCriteria, preparedStmtList));
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, INVALID_TENANT_ID_MSG);
        }
        List<StaffPermission> attendanceStaffList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return attendanceStaffList;
    }
}
