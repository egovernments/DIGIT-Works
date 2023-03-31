package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class JurisdictionQueryBuilder {
    private static final String FETCH_JURISDICTION_QUERY = "SELECT j.id as jurisdiction_Id, j.org_id as jurisdiction_orgId, " +
            "j.code as jurisdiction_code, j.additional_details as jurisdiction_additionalDetails " +
            "FROM eg_org_jurisdiction j";

    public String getJurisdictionSearchQuery(Set<String> organisationIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_JURISDICTION_QUERY);

        if (organisationIds != null && !organisationIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" j.org_id IN (").append(createQuery(organisationIds)).append(")");
            addToPreparedStatement(preparedStmtList, organisationIds);
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
