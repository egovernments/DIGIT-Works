package org.egov.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.data.query.builder.SelectQueryBuilder;
import org.egov.common.data.repository.GenericRepository;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.producer.Producer;
import org.egov.repository.querybuilder.AttendanceLogQueryBuilder;
import org.egov.repository.rowmapper.AttendanceLogRowMapper;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceLog;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import static org.egov.Constants.INVALID_TENANT_ID;

@Repository
@Slf4j
public class AttendanceLogRepository extends GenericRepository<AttendanceLog> {
    private final AttendanceLogRowMapper rowMapper;
    private final AttendanceLogQueryBuilder queryBuilder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    protected AttendanceLogRepository(
            Producer producer,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            RedisTemplate<String, Object> redisTemplate,
            SelectQueryBuilder selectQueryBuilder,
            AttendanceLogRowMapper rowMapper,
            JdbcTemplate jdbcTemplate,
            AttendanceLogQueryBuilder queryBuilder) {
        super(producer, namedParameterJdbcTemplate, redisTemplate, selectQueryBuilder, null, Optional.of("abc"));
        this.rowMapper = rowMapper;
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * This method fetches the list of attendance logs based on the search criteria provided.
     *
     * @param searchCriteria The criteria used to filter the attendance logs.
     * @return List<AttendanceLog> A list of AttendanceLog objects representing the attendance logs.
     */
    public List<AttendanceLog> getAttendanceLogs(AttendanceLogSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        if(!StringUtils.isBlank(searchCriteria.getRegisterId())) log.info("Fetching Attendance Log list. RegisterId ["+searchCriteria.getRegisterId()+"]");
        if(!CollectionUtils.isEmpty(searchCriteria.getClientReferenceId())) log.info("Fetching Attendance Log list. ClientReferenceIds "+searchCriteria.getClientReferenceId());
        String tenantId = searchCriteria.getTenantId();
        String query = null;
        // Wrap query construction in try-catch to handle invalid tenant scenarios gracefully
        try {
            query = queryBuilder.getAttendanceLogSearchQuery(tenantId, searchCriteria, preparedStmtList);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID, e.getMessage());
        }
        log.info("Query build successfully");
        List<AttendanceLog> attendanceLogList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched Attendance Log list");
        return attendanceLogList;
    }
}
