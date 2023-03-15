package org.egov.works.repository.querybuilder;

import org.egov.works.web.models.ContractCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DocumentsQueryBuilder {

    private static final String DOCUMENT_SELECT_QUERY = " SELECT document.id AS id, " +
            "document.filestore_id AS fileStoreId, " +
            "document.document_type AS documentType, " +
            "document.document_uid AS documentUid, " +
            "document.status AS status, " +
            "document.contract_id AS contractIid, " +
            "document.additional_details AS additionalDetails, " +
            "document.created_by AS createdBy, " +
            "document.last_modified_by AS lastModifiedBy, " +
            "document.created_time AS createdTime, " +
            "document.last_modified_time AS lastModifiedTime " +
            "FROM eg_wms_contract_documents AS document ";


    public String getDocumentSearchQuery(ContractCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(DOCUMENT_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" document.contract_id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }
        return query.toString();
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

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
}
