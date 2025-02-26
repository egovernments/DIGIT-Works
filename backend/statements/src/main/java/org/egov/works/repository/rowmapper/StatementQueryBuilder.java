package org.egov.works.repository.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.works.web.models.SearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class StatementQueryBuilder {

    private static final String LEFT_JOIN = "LEFT JOIN ";

    private static final String FETCH_STATEMENT_QUERY = "SELECT statement.id AS id," +
            " statement.tenantid AS tenantId, " +
            "statement.target_id AS targetId, statement.statement_type AS statementType, statement.basic_sor_details AS basicSorDetails, " +
            "statement.additional_details AS additionalDetails, statement.createdtime AS createdTime, statement.createdby AS createdBy, " +
            "statement.lastmodifiedtime AS lastModifiedTime, statement.lastmodifiedby AS lastModifiedBy, sorDetails.id AS sorDetailsId, " +
            "sorDetails.tenantid AS sorDetailsTenantId, sorDetails.statement_id AS statementId, sorDetails.sorid AS sorId, " +
            "sorDetails.basic_sor_details AS sorDetailsBasicSorDetails, sorDetails.additional_details as sorAdditionalDetails, sorDetails.is_active as isActive, lineItems.id AS lineItemId, lineItems.tenantid AS lineItemsTenantId, " +
            "lineItems.sorid AS lineItemsSorId, lineItems.sortype AS lineItemSorType, lineItems.reference_id AS lineItemIdReferenceId, " +
            "lineItems.basic_sor_details AS lineItemsBasicSorDetails, lineItems.additional_details as lineItemAdditionalDetails " +
            "FROM eg_statement AS statement " +
            LEFT_JOIN +
            "eg_statement_sor_details AS sorDetails " +
            "ON (statement.id = sorDetails.statement_id) " +
            LEFT_JOIN +
            "eg_statement_sor_line_items AS lineItems " +
            "ON (sorDetails.id = lineItems.reference_id)";

    private static final String ORDER_BY_STATEMENT_CREATED_TIME = " ORDER BY statement.createdtime ";



    public String getStatementQuery(SearchCriteria searchCriteria, List<Object> preparedStmtList) {
        log.info("StatementQueryBuilder::getStatementQuery");
        StringBuilder queryBuilder = new StringBuilder(FETCH_STATEMENT_QUERY);


        List<String> statementIds = searchCriteria.getStatementId();
        if (statementIds != null && !statementIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" statement.id IN (").append(createQuery(statementIds)).append(")");
            addToPreparedStatement(preparedStmtList, statementIds);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" statement.tenantid=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }
        if (searchCriteria.getStatementType() != null ) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" statement.statement_type=? ");
            preparedStmtList.add(searchCriteria.getStatementType().toString());
        }
        if (StringUtils.isNotBlank(searchCriteria.getReferenceId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" statement.target_id=? ");
            preparedStmtList.add(searchCriteria.getReferenceId());
        }
        //added the default as active
        addClauseIfRequired(preparedStmtList, queryBuilder);
        queryBuilder.append(" sorDetails.is_active=? ");
        preparedStmtList.add(Boolean.TRUE);

        addOrderByClause(queryBuilder, searchCriteria);


        return queryBuilder.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, SearchCriteria searchCriteria) {
        log.info("StatementQueryBuilder::addOrderByClause");
        //default
       // if (searchCriteria.getSortBy() == null || StringUtils.isEmpty(searchCriteria.getSortBy().name())) {
            queryBuilder.append(ORDER_BY_STATEMENT_CREATED_TIME);


         queryBuilder.append("DESC");

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

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }
    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
}