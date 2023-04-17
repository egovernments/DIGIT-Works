package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DocumentQueryBuilder {

    private static final String FETCH_DOCUMENT_QUERY = "SELECT doc.id as document_Id, doc.org_id as document_orgId, " +
            "doc.org_func_id as document_orgFuncId, doc.document_type as document_documentType, doc.file_store as document_fileStore, " +
            "doc.document_uid as document_documentUid, doc.additional_details as document_additionalDetails, doc.is_active as document_active " +
            "FROM eg_org_document doc";
    public String getDocumentsSearchQuery(Set<String> organisationIds, Set<String> functionIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_DOCUMENT_QUERY);

        if (organisationIds != null && !organisationIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" doc.org_id IN (").append(createQuery(organisationIds)).append(")");
            addToPreparedStatement(preparedStmtList, organisationIds);
        }

        if (functionIds != null && !functionIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" doc.org_func_id IN (").append(createQuery(functionIds)).append(")");
            addToPreparedStatement(preparedStmtList, functionIds);
        }

        return queryBuilder.toString();
    }
    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" OR");
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
