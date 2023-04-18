package org.egov.digit.expense.util;

import java.util.List;
import java.util.Set;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryBuilderUtils {

	@Autowired
	private Configuration configs;
	
	/*
	 * Utility methods for query builders
	 */
	
	public void addToPreparedStatement(List<Object> preparedStmtList, Set<String> ids) {
		ids.forEach(id -> {
			preparedStmtList.add(id);
		});
	}
	
	public String createQuery(Set<String> ids) {
		StringBuilder builder = new StringBuilder();
		int length = ids.size();
		for (int i = 0; i < length; i++) {
			builder.append("?");
			if (i != length - 1)
				builder.append(",");
		}
		return builder.toString();
	}
	
	public void addClauseIfRequired(List<Object> values,StringBuilder queryString) {
		if (values.isEmpty())
			queryString.append(" WHERE ");
		else {
			queryString.append(" AND ");
		}
	}
	
	public static final String PAGINATION_WRAPPER = "SELECT * FROM "
			+ "(SELECT *, DENSE_RANK() OVER (ORDER BY b_lastmodifiedtime DESC, b_id) offset_ FROM " + "({})" + " result) result_offset "
			+ "WHERE offset_ > ? AND offset_ <= ?";

/*	public String addPaginationWrapper(String query, List<Object> preparedStmtList, Pagination pagination) {


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
	}*/

}
