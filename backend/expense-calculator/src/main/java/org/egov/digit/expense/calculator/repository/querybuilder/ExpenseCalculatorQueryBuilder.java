package org.egov.digit.expense.calculator.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ExpenseCalculatorQueryBuilder {


    private static final String FETCH_CALCULATE_QUERY = "SELECT musterroll_id FROM eg_works_calculation ";


    public String getMusterRollsOfContract(String contractId, String billType, List<String> billIds, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_CALCULATE_QUERY);

        if (StringUtils.isNotBlank(contractId)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append("contract_id=? ");
            preparedStmtList.add(contractId);
        }

        if (StringUtils.isNotBlank(billType)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append("bill_type=? ");
            preparedStmtList.add(billType);
        }

        if (billIds != null && !billIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.id IN (").append(createQuery(billIds)).append(")");
            addToPreparedStatement(preparedStmtList, billIds);
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
