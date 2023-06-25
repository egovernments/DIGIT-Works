package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.ExecutedVALogsQueryBuilder;
import org.egov.repository.rowmapper.ExecutedVALogsRowMapper;
import org.egov.web.models.jit.ExecutedVALog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ExecutedVALogsRepository {
    @Autowired
    private ExecutedVALogsRowMapper rowMapper;

    @Autowired
    private ExecutedVALogsQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ExecutedVALog> getAttendanceLogs(String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        List<Object> preparedStmtList = new ArrayList<>();
        log.info("Fetching Executed VA Log list. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        String query = queryBuilder.getLastExecutedVABySearchQuery(preparedStmtList, tenantId, hoaCode, ddoCode, granteeCode);
        log.info("Query build successfully. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        List<ExecutedVALog> executedVALogList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched Executed VA Log list. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        return executedVALogList;
    }
}
