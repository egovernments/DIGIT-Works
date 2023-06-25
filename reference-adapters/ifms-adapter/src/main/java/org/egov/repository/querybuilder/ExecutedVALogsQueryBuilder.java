package org.egov.repository.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ExecutedVALogsQueryBuilder {


    private static final String FETCH_VA_EXECUTED_SELECT_QUERY = " SELECT evl.id, " +
            "evl.tenantId, " +
            "evl.hoaCode, " +
            "evl.ddoCode, " +
            "evl.granteeCode , " +
            "evl.lastExecuted, " +
            "evl.createdby, " +
            "evl.lastmodifiedby, " +
            "evl.createdtime, " +
            "evl.lastmodifiedtime " +
            "FROM jit_executed_va_logs evl ";

    public String getLastExecutedVABySearchQuery(List<Object> preparedStmtList, String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        StringBuilder query = new StringBuilder(getLastExecutedVASearchQuery(preparedStmtList, tenantId, hoaCode, ddoCode, granteeCode));
        query.append(" ORDER BY evl.lastmodifiedtime DESC ");
        return query.toString();
    }

    private String getLastExecutedVASearchQuery(List<Object> preparedStmtList, String tenantId, String hoaCode, String ddoCode, String granteeCode) {
        StringBuilder query = new StringBuilder(FETCH_VA_EXECUTED_SELECT_QUERY);

        if (tenantId != null && tenantId != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" evl.tenantId IN (").append(createQuery(Collections.singletonList(tenantId))).append(")");
            preparedStmtList.add(tenantId);
        }

        if (hoaCode != null && hoaCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" evl.hoaCode IN (").append(createQuery(Collections.singletonList(hoaCode))).append(")");
            preparedStmtList.add(hoaCode);
        }

        if (ddoCode != null && ddoCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" evl.ddoCode IN (").append(createQuery(Collections.singletonList(ddoCode))).append(")");
            preparedStmtList.add(ddoCode);
        }

        if (granteeCode != null && granteeCode != "") {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" evl.granteeCode IN (").append(createQuery(Collections.singletonList(granteeCode))).append(")");
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
