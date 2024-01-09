package org.egov.works.mukta.adapter.repository.queryBuilder;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.mukta.adapter.web.models.PaymentSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class PaymentQueryBuilder {
    public static final String PAYMENT_SEARCH_QUERY = "SELECT id as Id, tenantid as tenantId," +
            "paymentId as paymentId, paymentStatus as paymentStatus, paymentType as paymentType," +
            "disburseData as disburseData, additionalDetails as additionalDetails, createdBy as createdBy," +
            "createdTime as createdTime, lastModifiedBy as lastModifiedBy, lastModifiedTime as lastModifiedTime " +
            "FROM eg_mukta_ifms_adapter";

    public String getPaymentSearchQuery(PaymentSearchCriteria paymentSearchCriteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(PAYMENT_SEARCH_QUERY);

        List<String> ids = paymentSearchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }
        if(paymentSearchCriteria.getTenantId() != null){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" tenantId = ? ");
            preparedStmtList.add(paymentSearchCriteria.getTenantId());
        }

        if (paymentSearchCriteria.getPaymentId() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" paymentId = ? ");
            preparedStmtList.add(paymentSearchCriteria.getPaymentId());
        }
        if (paymentSearchCriteria.getPaymentStatus() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" paymentStatus = ? ");
            preparedStmtList.add(paymentSearchCriteria.getPaymentStatus());
        }
        if (paymentSearchCriteria.getPaymentType() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" paymentType = ? ");
            preparedStmtList.add(paymentSearchCriteria.getPaymentType());
        }

        log.info("executing query ::: " + query);
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
    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }
}
