package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.web.models.BillTransactionReportSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class BillTransactionReportQueryBuilder {

    private static final String BASE_QUERY = "SELECT id, bill_id, tenant_id, type, status, file_store_id, " +
            "error_details, created_by, created_time, last_modified_by, last_modified_time " +
            "FROM eg_expense_bill_transaction_report ";

    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM eg_expense_bill_transaction_report ";

    private static final String ORDER_BY_CLAUSE = " ORDER BY last_modified_time DESC ";

    public String getSearchQuery(BillTransactionReportSearchCriteria criteria, List<Object> preparedStatementValues) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        addWhereClause(query, criteria, preparedStatementValues);
        query.append(ORDER_BY_CLAUSE);
        return query.toString();
    }

    public String getCountQuery(BillTransactionReportSearchCriteria criteria, List<Object> preparedStatementValues) {
        StringBuilder query = new StringBuilder(COUNT_QUERY);
        addWhereClause(query, criteria, preparedStatementValues);
        return query.toString();
    }

    public String getLatestReportQuery(String billId, String tenantId, String type, List<Object> preparedStatementValues) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        query.append(" WHERE bill_id = ? AND tenant_id = ? AND type = ? ");
        preparedStatementValues.add(billId);
        preparedStatementValues.add(tenantId);
        preparedStatementValues.add(type);
        query.append(ORDER_BY_CLAUSE);
        query.append(" LIMIT 1 ");
        return query.toString();
    }

    private void addWhereClause(StringBuilder query, BillTransactionReportSearchCriteria criteria,
                                List<Object> preparedStatementValues) {
        query.append(" WHERE 1=1 ");

        if (StringUtils.hasText(criteria.getBillId())) {
            query.append(" AND bill_id = ? ");
            preparedStatementValues.add(criteria.getBillId());
        }

        if (StringUtils.hasText(criteria.getTenantId())) {
            query.append(" AND tenant_id = ? ");
            preparedStatementValues.add(criteria.getTenantId());
        }

        if (StringUtils.hasText(criteria.getType())) {
            query.append(" AND type = ? ");
            preparedStatementValues.add(criteria.getType());
        }
    }
}
