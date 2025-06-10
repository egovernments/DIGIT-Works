package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.PIStatusLogsQueryBuilder;
import org.egov.repository.rowmapper.PIStatusLogsRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.PIStatusLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PIStatusLogsRepository {
    @Autowired
    private PIStatusLogsRowMapper rowMapper;
    @Autowired
    private PIStatusLogsQueryBuilder queryBuilder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    HelperUtil util;

    public List<PIStatusLog> getPIStatusLogs(List<String> piIds) {
        List<Object> preparedStmtList = new ArrayList<>();
        log.info("Fetching pi status Log list. piIds ["+piIds.toString()+"]");
        String query = queryBuilder.getPIStatusLogsBySearchQuery(preparedStmtList, piIds);
        log.info("Query build successfully. piIds ["+piIds.toString()+"]");
        List<PIStatusLog> piStatusLogs = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched pi status logs list. piIds ["+piIds.toString()+"] " );
        return piStatusLogs;
    }

    @Transactional
    public void savePIStatusLogs(List<PIStatusLog> piStatusLogs) {
        List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForPiStatusLogs(piStatusLogs);
        namedJdbcTemplate.batchUpdate(PIStatusLogsQueryBuilder.PI_STATUS_LOGS_INSERT_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    private List<MapSqlParameterSource> getSqlParameterListForPiStatusLogs(List<PIStatusLog> piStatusLogs) {

        List<MapSqlParameterSource> executedVaLogParamMapList = new ArrayList<>();
        for (PIStatusLog piStatusLog : piStatusLogs) {

            MapSqlParameterSource piStatusLogParamMapListParamMap = new MapSqlParameterSource();
            piStatusLogParamMapListParamMap.addValue("id", piStatusLog.getId());
            piStatusLogParamMapListParamMap.addValue("piId", piStatusLog.getPiId());
            piStatusLogParamMapListParamMap.addValue("serviceId", piStatusLog.getServiceId().toString());
            piStatusLogParamMapListParamMap.addValue("status", piStatusLog.getStatus().toString());
            piStatusLogParamMapListParamMap.addValue("additionalDetails", util.getPGObject(piStatusLog.getAdditionalDetails()));
            piStatusLogParamMapListParamMap.addValue("createdby", piStatusLog.getAuditDetails().getCreatedBy());
            piStatusLogParamMapListParamMap.addValue("createdtime", piStatusLog.getAuditDetails().getCreatedTime());
            piStatusLogParamMapListParamMap.addValue("lastmodifiedby", piStatusLog.getAuditDetails().getLastModifiedBy());
            piStatusLogParamMapListParamMap.addValue("lastmodifiedtime", piStatusLog.getAuditDetails().getLastModifiedTime());
            executedVaLogParamMapList.add(piStatusLogParamMapListParamMap);
        }
        return executedVaLogParamMapList;
    }

}
