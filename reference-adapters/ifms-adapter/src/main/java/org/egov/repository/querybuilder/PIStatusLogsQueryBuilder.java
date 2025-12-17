package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class PIStatusLogsQueryBuilder {
    public static final String PI_STATUS_LOGS_INSERT_QUERY = "INSERT INTO jit_pi_status_logs "
            + "(id, piId, serviceId, status, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)"
            + " VALUES (:id, :piId, :serviceId, :status, :additionalDetails, :createdtime, :createdby, :lastmodifiedtime, :lastmodifiedby);";

    private static final String PI_STATUS_LOGS_SELECT_QUERY = " SELECT piStatusLogs.id, " +
            "piStatusLogs.piId, " +
            "piStatusLogs.serviceId, " +
            "piStatusLogs.status, " +
            "piStatusLogs.additionalDetails, " +
            "piStatusLogs.createdby, " +
            "piStatusLogs.lastmodifiedby, " +
            "piStatusLogs.createdtime, " +
            "piStatusLogs.lastmodifiedtime " +
            "FROM jit_pi_status_logs piStatusLogs ";

    public String getPIStatusLogsBySearchQuery(List<Object> preparedStmtList, List piIds) {
        StringBuilder query = new StringBuilder(getPiStatusLogsSearchQuery(preparedStmtList, piIds));
        query.append(" ORDER BY piStatusLogs.createdtime DESC ");
        return query.toString();
    }

    private String getPiStatusLogsSearchQuery(List<Object> preparedStmtList, List<String> piIds) {
        StringBuilder query = new StringBuilder(PI_STATUS_LOGS_SELECT_QUERY);

        if (piIds != null && !piIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" piStatusLogs.piId IN (").append(createQuery(piIds)).append(")");
            preparedStmtList.addAll(piIds);
        }

        return query.toString();
    }


    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }
}
