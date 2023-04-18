
package org.egov.digit.expense.repository.querybuilder;

import java.util.List;
import java.util.Set;

import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.util.QueryBuilderUtils;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.PaymentCriteria;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class PaymentQueryBuilder {
	
	@Autowired
	private QueryBuilderUtils builderUtils;

	public String getPaymentQuery(PaymentSearchRequest paymentSearchRequest, List<Object> preparedStatementValues) {

		PaymentCriteria paymentCriteria = paymentSearchRequest.getPaymentCriteria();
		Pagination pagination = paymentSearchRequest.getPagination();
		
		StringBuilder paymentSearchQuery = null; //new StringBuilder(Constants.BILL_QUERY); //TODO

		paymentSearchQuery.append(" payment.tenantId = ? ");
		preparedStatementValues.add(paymentCriteria.getTenantId());
		
		if (!StringUtils.isEmpty(paymentCriteria.getStatus())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append(" payment.status = ? ");
			preparedStatementValues.add(paymentCriteria.getStatus());
		}

		Set<String> referenceIds = paymentCriteria.getBillIds();
		if (!CollectionUtils.isEmpty(paymentCriteria.getBillIds())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append("billpayment.billid IN (").append(builderUtils.createQuery(referenceIds)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, referenceIds);
		}

		Set<String> ids = paymentCriteria.getIds();
		if (CollectionUtils.isEmpty(ids)) {

			builderUtils.addClauseIfRequired(preparedStatementValues, paymentSearchQuery);
			paymentSearchQuery.append("payment.id IN (").append(builderUtils.createQuery(ids)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, ids);
		}

//		String finalQuery = builderUtils.addPaginationWrapper(paymentSearchQuery.toString(), preparedStatementValues, pagination);

//		return finalQuery;

		return null;
	}
}
