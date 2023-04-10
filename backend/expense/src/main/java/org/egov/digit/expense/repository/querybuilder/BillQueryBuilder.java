
package org.egov.digit.expense.repository.querybuilder;

import java.util.List;
import java.util.Set;

import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.util.QueryBuilderUtils;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BillQueryBuilder {
	
	@Autowired
	private QueryBuilderUtils builderUtils;

	public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStatementValues) {

		BillCriteria billCriteria = billSearchRequest.getBillcriteria();
		Pagination pagination = billSearchRequest.getPagination();
		
		StringBuilder billSearchQuery = new StringBuilder(Constants.BILL_QUERY);

		billSearchQuery.append(" bill.tenantId = ? ");
		preparedStatementValues.add(billCriteria.getTenantId());

		if (!StringUtils.isEmpty(billCriteria.getBusinessService())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append(" bill.businessservice = ? ");
			preparedStatementValues.add(billCriteria.getBusinessService());
		}
		
		if (!StringUtils.isEmpty(billCriteria.getStatus())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append(" bill.status = ? ");
			preparedStatementValues.add(billCriteria.getStatus());
		}

		Set<String> referenceIds = billCriteria.getReferenceIds();
		if (!CollectionUtils.isEmpty(billCriteria.getReferenceIds())) {

			builderUtils.addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append("bill.referenceid IN (").append(builderUtils.createQuery(referenceIds)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, referenceIds);
		}

		Set<String> ids = billCriteria.getIds();
		if (CollectionUtils.isEmpty(ids)) {

			builderUtils.addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append("bill.id IN (").append(builderUtils.createQuery(ids)).append(")");
			builderUtils.addToPreparedStatement(preparedStatementValues, ids);
		}

		String finalQuery = builderUtils.addPaginationWrapper(billSearchQuery.toString(), preparedStatementValues, pagination);

		return finalQuery;
	}
}
