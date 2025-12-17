package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.ExecutedVALogsQueryBuilder;
import org.egov.repository.rowmapper.ExecutedVALogsRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.ExecutedVALog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.egov.repository.querybuilder.ExecutedVALogsQueryBuilder.EXECUTED_VA_LOGS_INSERT_QUERY;
import static org.egov.repository.querybuilder.ExecutedVALogsQueryBuilder.EXECUTED_VA_LOGS_UPDATE_QUERY;

@Repository
@Slf4j
public class ExecutedVALogsRepository {
    @Autowired
    private ExecutedVALogsRowMapper rowMapper;
    @Autowired
    private ExecutedVALogsQueryBuilder queryBuilder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    HelperUtil util;

    public List<ExecutedVALog> getExecutedVALogs(String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        List<Object> preparedStmtList = new ArrayList<>();
        log.info("Fetching Executed VA Log list. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        String query = queryBuilder.getLastExecutedVABySearchQuery(preparedStmtList, tenantId, hoaCode, ddoCode, granteeCode);
        log.info("Query build successfully. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        List<ExecutedVALog> executedVALogList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched Executed VA Log list. tenantId ["+tenantId+"] hoaCode [" + hoaCode +"] ddoCode [" + ddoCode + "] granteeCode [" + granteeCode + "]" );
        return executedVALogList;
    }

    @Transactional
    public void saveExecutedVALogs(List<ExecutedVALog> executedVALogs) {
        List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForExecutedVALog(executedVALogs);
        namedJdbcTemplate.batchUpdate(EXECUTED_VA_LOGS_INSERT_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    @Transactional
    public void updateExecutedVALogs(List<ExecutedVALog> executedVALogs) {
        List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForExecutedVAUpdate(executedVALogs);
        namedJdbcTemplate.batchUpdate(EXECUTED_VA_LOGS_UPDATE_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    private List<MapSqlParameterSource> getSqlParameterListForExecutedVALog(List<ExecutedVALog> executedVALogs) {

        List<MapSqlParameterSource> executedVaLogParamMapList = new ArrayList<>();
        for (ExecutedVALog executedVALog : executedVALogs) {

            MapSqlParameterSource executedVaLogParamMapListParamMap = new MapSqlParameterSource();
            executedVaLogParamMapListParamMap.addValue("id", executedVALog.getId());
            executedVaLogParamMapListParamMap.addValue("tenantId", executedVALog.getTenantId());
            executedVaLogParamMapListParamMap.addValue("hoaCode", executedVALog.getHoaCode());
            executedVaLogParamMapListParamMap.addValue("ddoCode", executedVALog.getDdoCode());
            executedVaLogParamMapListParamMap.addValue("granteeCode", executedVALog.getGranteeCode());
            executedVaLogParamMapListParamMap.addValue("lastExecuted", executedVALog.getLastExecuted());
            executedVaLogParamMapListParamMap.addValue("additionalDetails", util.getPGObject(executedVALog.getAdditionalDetails()));
            executedVaLogParamMapListParamMap.addValue("createdby", executedVALog.getAuditDetails().getCreatedBy());
            executedVaLogParamMapListParamMap.addValue("createdtime", executedVALog.getAuditDetails().getCreatedTime());
            executedVaLogParamMapListParamMap.addValue("lastmodifiedby", executedVALog.getAuditDetails().getLastModifiedBy());
            executedVaLogParamMapListParamMap.addValue("lastmodifiedtime", executedVALog.getAuditDetails().getLastModifiedTime());
            executedVaLogParamMapList.add(executedVaLogParamMapListParamMap);
        }
        return executedVaLogParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForExecutedVAUpdate(List<ExecutedVALog> executedVALogs) {

        List<MapSqlParameterSource> executedVaLogParamMapListParamMapList = new ArrayList<>();
        for (ExecutedVALog executedVALog : executedVALogs) {

            MapSqlParameterSource executedVaLogParamMapListParamMap = new MapSqlParameterSource();
            executedVaLogParamMapListParamMap.addValue("lastExecuted", executedVALog.getLastExecuted());
            executedVaLogParamMapListParamMap.addValue("additionalDetails", util.getPGObject(executedVALog.getAdditionalDetails()));
            executedVaLogParamMapListParamMap.addValue("lastmodifiedby", executedVALog.getAuditDetails().getLastModifiedBy());
            executedVaLogParamMapListParamMap.addValue("lastmodifiedtime", executedVALog.getAuditDetails().getLastModifiedTime());
            executedVaLogParamMapListParamMap.addValue("id", executedVALog.getId());
            executedVaLogParamMapListParamMapList.add(executedVaLogParamMapListParamMap);
        }
        return executedVaLogParamMapListParamMapList;
    }

}
