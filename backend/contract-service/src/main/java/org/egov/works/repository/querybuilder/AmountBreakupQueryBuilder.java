package org.egov.works.repository.querybuilder;

import org.egov.works.web.models.ContractCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AmountBreakupQueryBuilder {

    private static final String AMOUNT_BREAKUP_SELECT_QUERY = " SELECT amountBreakup.id AS id, " +
            "amountBreakup.estimate_amount_breakup_id AS estimateAmountBreakupId, " +
            "amountBreakup.line_item_id AS lineItemId, " +
            "amountBreakup.amount AS amount, " +
            "amountBreakup.status AS status, " +
            "amountBreakup.additional_details AS additionalDetails, " +
            "amountBreakup.created_by AS createdBy, " +
            "amountBreakup.last_modified_by AS lastModifiedBy, " +
            "amountBreakup.created_time AS createdTime, " +
            "amountBreakup.last_modified_time AS lastModifiedTime " +
            "FROM eg_wms_contract_amountBreakups AS amountBreakup ";


    public String getAmountBreakupSearchQuery(ContractCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(AMOUNT_BREAKUP_SELECT_QUERY);
        List<String> estimateLineItemIds = criteria.getEstimateLineItemIds();
        if (estimateLineItemIds != null && !estimateLineItemIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" amountBreakup.line_item_id IN (").append(createQuery(estimateLineItemIds)).append(")");
            addToPreparedStatement(preparedStmtList, estimateLineItemIds);
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
