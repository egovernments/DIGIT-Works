package org.egov.digit.expense.repository.querybuilder;

import org.egov.digit.expense.config.Constants;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BillDetailQueryBuilder {

    public String getQueryByBillIds(List<String> billIds, String tenantId, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(Constants.BILL_DETAIL_QUERY);
        query.append(" WHERE bd.tenantid = ?");
        preparedStmtList.add(tenantId);
        query.append(" AND bd.billid IN (").append(createQuery(billIds)).append(")");
        preparedStmtList.addAll(billIds);
        return query.toString();
    }

    public String getQueryByDetailIds(List<String> detailIds, String tenantId, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(Constants.BILL_DETAIL_QUERY);
        query.append(" WHERE bd.tenantid = ?");
        preparedStmtList.add(tenantId);
        query.append(" AND bd.id IN (").append(createQuery(detailIds)).append(")");
        preparedStmtList.addAll(detailIds);
        return query.toString();
    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        int i = 0;
        for (String ignored : ids) {
            builder.append(" ?");
            if (i < length - 1) builder.append(",");
            i++;
        }
        return builder.toString();
    }
}
