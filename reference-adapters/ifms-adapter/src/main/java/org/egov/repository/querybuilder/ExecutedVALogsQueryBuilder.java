package org.egov.repository.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ExecutedVALogsQueryBuilder {


    private static final String EXECUTED_VA_LOGS_SELECT_QUERY = " SELECT execVaLogs.id, " +
            "execVaLogs.tenantId, " +
            "execVaLogs.hoaCode, " +
            "execVaLogs.ddoCode, " +
            "execVaLogs.granteeCode , " +
            "execVaLogs.lastExecuted, " +
            "execVaLogs.additionalDetails, " +
            "execVaLogs.createdby, " +
            "execVaLogs.lastmodifiedby, " +
            "execVaLogs.createdtime, " +
            "execVaLogs.lastmodifiedtime " +
            "FROM jit_executed_va_logs execVaLogs ";

    public static final String EXECUTED_VA_LOGS_INSERT_QUERY = "INSERT INTO jit_executed_va_logs (id, tenantId, hoaCode,"
            + " ddoCode, granteeCode, lastExecuted, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :hoaCode, :ddoCode, :granteeCode, :lastExecuted, :additionalDetails,"
            + " :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public static final String EXECUTED_VA_LOGS_UPDATE_QUERY = "UPDATE jit_executed_va_logs SET lastExecuted=:lastExecuted,"
            + "additionalDetails=:additionalDetails, lastmodifiedby=:lastmodifiedby,lastmodifiedtime=:lastmodifiedtime "
            + " WHERE id=:id";

    public String getLastExecutedVABySearchQuery(List<Object> preparedStmtList, String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        StringBuilder query = new StringBuilder(getLastExecutedVASearchQuery(preparedStmtList, tenantId, hoaCode, ddoCode, granteeCode));
        query.append(" ORDER BY execVaLogs.lastmodifiedtime DESC ");
        return query.toString();
    }

    private String getLastExecutedVASearchQuery(List<Object> preparedStmtList, String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        StringBuilder query = new StringBuilder(EXECUTED_VA_LOGS_SELECT_QUERY);

        if (tenantId != null && tenantId != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" execVaLogs.tenantId IN (").append(createQuery(Collections.singletonList(tenantId))).append(")");
            preparedStmtList.add(tenantId);
        }

        if (hoaCode != null && hoaCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" execVaLogs.hoaCode IN (").append(createQuery(Collections.singletonList(hoaCode))).append(")");
            preparedStmtList.add(hoaCode);
        }

        if (ddoCode != null && ddoCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" execVaLogs.ddoCode IN (").append(createQuery(Collections.singletonList(ddoCode))).append(")");
            preparedStmtList.add(ddoCode);
        }

        if (granteeCode != null && granteeCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" execVaLogs.granteeCode IN (").append(createQuery(Collections.singletonList(granteeCode))).append(")");
            preparedStmtList.add(granteeCode);
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
