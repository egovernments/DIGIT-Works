package org.egov.works.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.works.web.models.ContractCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class LineItemsQueryBuilder {

    private static final String LINE_ITEMS_SELECT_QUERY = " SELECT lineItems.id AS id, " +
            "lineItems.estimate_id AS estimateId, " +
            "lineItems.estimate_line_item_id AS estimateLineItemId, " +
            "lineItems.contract_id AS contractId, " +
            "lineItems.tenant_id AS tenantId, " +
            "lineItems.unit_rate AS unitRate, " +
            "lineItems.no_of_unit AS noOfUnit, " +
            "lineItems.status AS status, " +
            "lineItems.additional_details AS additionalDetails, " +
            "lineItems.created_by AS createdBy, " +
            "lineItems.last_modified_by AS lastModifiedBy, " +
            "lineItems.created_time AS createdTime, " +
            "lineItems.last_modified_time AS lastModifiedTime, " +

            "amountBreakups.id AS amtId, " +
            "amountBreakups.estimate_amount_breakup_id AS amtEstimateAmountBreakupId, " +
            "amountBreakups.line_item_id AS amtLineItemId, " +
            "amountBreakups.amount AS amtAmount, " +
            "amountBreakups.status AS amtStatus, " +
            "amountBreakups.additional_details AS amtAdditionalDetails, " +
            "amountBreakups.created_by AS amtCreatedBy, " +
            "amountBreakups.last_modified_by AS amtLastModifiedBy, " +
            "amountBreakups.created_time AS amtCreatedTime, " +
            "amountBreakups.last_modified_time AS amtLastModifiedTime " +
            "FROM eg_wms_contract_line_items AS lineItems " +
            "LEFT JOIN " +
            "eg_wms_contract_amount_breakups as amountBreakups " +
            "ON (lineItems.id=amountBreakups.line_item_id) ";

    public String getLineItemsSearchQuery(ContractCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(LINE_ITEMS_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" lineItems.contract_id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        List<String> estimateIds = criteria.getEstimateIds();
        if (estimateIds != null && !estimateIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" lineItems.estimate_id IN (").append(createQuery(estimateIds)).append(")");
            addToPreparedStatement(preparedStmtList, estimateIds);
        }

        List<String> lineItemIds = criteria.getEstimateLineItemIds();
        if (lineItemIds != null && !lineItemIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" lineItems.estimate_line_item_id IN (").append(createQuery(lineItemIds)).append(")");
            addToPreparedStatement(preparedStmtList, lineItemIds);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" lineItems.tenant_id=? ");
            preparedStmtList.add(criteria.getTenantId());
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
