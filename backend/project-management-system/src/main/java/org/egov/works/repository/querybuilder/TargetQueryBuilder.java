package org.egov.works.repository.querybuilder;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TargetQueryBuilder {

    private static final String FETCH_TARGET_QUERY = "select t.id as target_id, t.project_id as target_projectid, t.beneficiarytype as target_beneficiarytype, t.totalno as target_totalno, t.targetno as target_targetno, " +
            "t.isdeleted as target_isdeleted, t.createdby as target_createdby, t.createdtime as target_createdtime, t.lastmodifiedby as target_lastmodifiedby, t.lastmodifiedtime as target_lastmodifiedtime " +
            " from eg_pms_target t ";

    public String getTargetSearchQuery(List<String> projectIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_TARGET_QUERY);

        if (projectIds != null && !projectIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" t.project_id IN (").append(createQuery(projectIds)).append(")");
            addToPreparedStatement(preparedStmtList, projectIds);
        }

        return queryBuilder.toString();
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
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

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }

}
