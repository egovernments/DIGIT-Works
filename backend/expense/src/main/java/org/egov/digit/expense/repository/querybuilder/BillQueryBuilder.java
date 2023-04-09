
package org.egov.digit.expense.repository.querybuilder;

import java.util.List;
import java.util.Set;

import org.egov.digit.expense.config.BillConstants;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.BillCriteria;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BillQueryBuilder {
	
	@Autowired
	private Configuration configs;

	public String getBillQuery(BillSearchRequest billSearchRequest, List<Object> preparedStatementValues) {

		BillCriteria billCriteria = billSearchRequest.getBillcriteria();
		Pagination pagination = billSearchRequest.getPagination();
		
		StringBuilder billSearchQuery = new StringBuilder(BillConstants.BILL_QUERY);

		billSearchQuery.append(" property.tenantId = ? ");
		preparedStatementValues.add(billCriteria.getTenantId());

		if (!StringUtils.isEmpty(billCriteria.getBusinessService())) {

			addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append(" property.businessservice = ? ");
			preparedStatementValues.add(billCriteria.getBusinessService());
		}

		Set<String> referenceIds = billCriteria.getReferenceIds();
		if (!CollectionUtils.isEmpty(billCriteria.getReferenceIds())) {

			addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append("property.tenantid IN (").append(createQuery(referenceIds)).append(")");
			addToPreparedStatement(preparedStatementValues, referenceIds);
		}

		Set<String> ids = billCriteria.getIds();
		if (CollectionUtils.isEmpty(ids)) {

			addClauseIfRequired(preparedStatementValues, billSearchQuery);
			billSearchQuery.append("property.tenantid IN (").append(createQuery(ids)).append(")");
			addToPreparedStatement(preparedStatementValues, ids);
		}

		String finalQuery = addPaginationWrapper(billSearchQuery.toString(), preparedStatementValues, pagination);

		return finalQuery;
	}
	
	/*
	 * Utility methods for query builders
	 */
	
	private void addToPreparedStatement(List<Object> preparedStmtList, Set<String> ids) {
		ids.forEach(id -> {
			preparedStmtList.add(id);
		});
	}
	
	private String createQuery(Set<String> ids) {
		StringBuilder builder = new StringBuilder();
		int length = ids.size();
		for (int i = 0; i < length; i++) {
			builder.append(" ?");
			if (i != length - 1)
				builder.append(",");
		}
		return builder.toString();
	}
	
	private static void addClauseIfRequired(List<Object> values,StringBuilder queryString) {
		if (values.isEmpty())
			queryString.append(" WHERE ");
		else {
			queryString.append(" AND ");
		}
	}
	
	private final String PAGINATION_WRAPPER = "SELECT * FROM "
			+ "(SELECT *, DENSE_RANK() OVER (ORDER BY b_lastmodifiedtime DESC, b_id) offset_ FROM " + "({})" + " result) result_offset "
			+ "WHERE offset_ > ? AND offset_ <= ?";

	private String addPaginationWrapper(String query, List<Object> preparedStmtList, Pagination pagination) {
		
		
		Long limit = configs.getDefaultLimit();
		Long offset = configs.getDefaultOffset();
		String finalQuery = PAGINATION_WRAPPER.replace("{}", query);

		if (pagination.getLimit() != null && pagination.getLimit() <= configs.getMaxSearchLimit())
			limit = pagination.getLimit();

		if (pagination.getLimit() != null && pagination.getLimit() > configs.getMaxSearchLimit())
			limit = configs.getMaxSearchLimit();

		if (pagination.getOffSet() != null)
			offset = pagination.getOffSet();

		preparedStmtList.add(offset);
		preparedStmtList.add(limit + offset);

		return finalQuery;
	}

}
