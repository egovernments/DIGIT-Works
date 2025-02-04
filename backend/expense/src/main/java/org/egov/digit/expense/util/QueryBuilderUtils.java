package org.egov.digit.expense.util;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class QueryBuilderUtils {
	/*
	 * Utility methods for query builders
	 */
	
	public void addToPreparedStatement(List<Object> preparedStmtList, Set<String> ids) {
		preparedStmtList.addAll(ids);
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

}
