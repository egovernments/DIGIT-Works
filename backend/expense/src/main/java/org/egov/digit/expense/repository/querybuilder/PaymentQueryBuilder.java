
package org.egov.digit.expense.repository.querybuilder;

import java.util.List;
import java.util.Set;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.util.QueryBuilderUtils;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.PaymentCriteria;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class PaymentQueryBuilder {
	
	private final QueryBuilderUtils builderUtils;
	
	private final Configuration configs;

	@Autowired
	public PaymentQueryBuilder(QueryBuilderUtils builderUtils, Configuration configs) {
		this.builderUtils = builderUtils;
		this.configs = configs;
	}

	public String getPaymentQuery(PaymentSearchRequest paymentSearchRequest, List<Object> preparedStatementValues, boolean isCountRequired) {

		PaymentCriteria paymentCriteria = paymentSearchRequest.getPaymentCriteria();
		
		StringBuilder paymentSearchQuery = null;
		if (isCountRequired) {
			paymentSearchQuery = new StringBuilder(Constants.PAYMENT_COUNT_QUERY);
		} else {
			paymentSearchQuery = new StringBuilder(Constants.PAYMENT_QUERY);
		}
		builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);

		paymentSearchQuery.append(" payment.tenantId = ? ");
		preparedStatementValues.add(paymentCriteria.getTenantId());
		
		if (!StringUtils.isEmpty(paymentCriteria.getStatus())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append(" payment.status = ? ");
			preparedStatementValues.add(paymentCriteria.getStatus());
		}

		if (!StringUtils.isEmpty(paymentCriteria.getReferenceStatus())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append(" payment.referencestatus = ? ");
			preparedStatementValues.add(paymentCriteria.getReferenceStatus());
		}
		
		Set<String> paymentNums = paymentCriteria.getPaymentNumbers();
		if (!CollectionUtils.isEmpty(paymentNums)) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append("payment.paymentnumber IN (").append(builderUtils.createQuery(paymentNums)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, paymentNums);
		}

		Set<String> billIds = paymentCriteria.getBillIds();
		if (!CollectionUtils.isEmpty(billIds)) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append("paymentbill.billid IN (").append(builderUtils.createQuery(billIds)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, billIds);
		}

		Set<String> ids = paymentCriteria.getIds();
		if (!CollectionUtils.isEmpty(ids)) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append("payment.id IN (").append(builderUtils.createQuery(ids)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, ids);
		}
		return isCountRequired? paymentSearchQuery.toString() :addPaginationWrapper(paymentSearchQuery, paymentSearchRequest.getPagination(), preparedStatementValues);
	}
	

    private String addPaginationWrapper(StringBuilder query,Pagination pagination ,List<Object> preparedStmtList){

		String paginatedQuery = addOrderByClause(pagination);

		int limit = null != pagination.getLimit() ? pagination.getLimit() : configs.getDefaultLimit();
		int offset = null != pagination.getOffSet() ? pagination.getOffSet() : configs.getDefaultOffset();

        String finalQuery = paginatedQuery.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }
    
    private String addOrderByClause(Pagination pagination) {

    	String paginationWrapper = WRAPPER_QUERY;

        if (pagination.getOrder() != null && Pagination.OrderEnum.fromValue(pagination.getOrder().toString()) != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());

        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Pagination.OrderEnum.ASC.name());
        }
        
        return paginationWrapper;
    }

    
    private static final String WRAPPER_QUERY = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY p_id {orderBy}) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

	public String getSearchCountQueryString(PaymentSearchRequest paymentSearchRequest, List<Object> preparedStmtList) {
        String query = getPaymentQuery(paymentSearchRequest, preparedStmtList,true);
        if (query != null)
            return Constants.COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
