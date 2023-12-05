package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class TaxIdentifierQueryBuilder {

    private static final String FETCH_TAX_IDENTIFIER_QUERY = " SELECT i.id as taxIdentifier_Id, i.org_id as taxIdentifier_orgId, " +
            "i.type as taxIdentifier_type, i.value as taxIdentifier_value, i.additional_details as taxIdentifier_additionalDetails, i.is_active as taxIdentifier_active " +
            "FROM eg_tax_identifier i";

    public String getTaxIdentifierSearchQuery(Set<String> organisationIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_TAX_IDENTIFIER_QUERY);

        if (organisationIds != null && !organisationIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" i.org_id IN (").append(createQuery(organisationIds)).append(")");
            addToPreparedStatement(preparedStmtList, organisationIds);
        }

        return queryBuilder.toString();
    }

    public String getTaxIdentifierSearchQueryBasedOnCriteria(String identifierType, String identifierValue, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_TAX_IDENTIFIER_QUERY);

        if (StringUtils.isNotBlank(identifierType)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" i.type=? ");
            preparedStmtList.add(identifierType);
        }

        if (StringUtils.isNotBlank(identifierValue)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" i.value=? ");
            preparedStmtList.add(identifierValue);
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
        preparedStmtList.addAll(ids);
    }
}
