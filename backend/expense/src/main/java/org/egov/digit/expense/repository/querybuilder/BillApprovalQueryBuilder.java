package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.web.models.BillApprovalSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@Component
public class BillApprovalQueryBuilder {

    private static final String BASE_QUERY = "SELECT id, tenant_id, bill_id, bill_number, business_service, " +
            "approver_uuid, approver_name, role, signature_method, signature_file_store_id, approval_status, " +
            "approved_time, created_by, created_time, last_modified_by, last_modified_time " +
            "FROM " + SCHEMA_REPLACE_STRING + ".eg_expense_bill_approval ";

    private static final String ORDER_BY_CLAUSE = " ORDER BY approved_time DESC ";

    public String getSearchQuery(BillApprovalSearchCriteria criteria, List<Object> preparedStatementValues) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        query.append(" WHERE 1=1 ");

        if (criteria.getBillIds() != null && !criteria.getBillIds().isEmpty()) {
            query.append(" AND bill_id IN (")
                 .append(String.join(",", criteria.getBillIds().stream().map(id -> "?").toArray(String[]::new)))
                 .append(") ");
            preparedStatementValues.addAll(criteria.getBillIds());
        }

        if (StringUtils.hasText(criteria.getTenantId())) {
            query.append(" AND tenant_id = ? ");
            preparedStatementValues.add(criteria.getTenantId());
        }

        query.append(ORDER_BY_CLAUSE);
        return query.toString();
    }
}
