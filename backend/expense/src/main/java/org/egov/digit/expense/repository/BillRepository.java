package org.egov.digit.expense.repository;

import java.util.ArrayList;
import java.util.List;

import org.egov.digit.expense.repository.querybuilder.BillQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillRowMapper;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final BillQueryBuilder queryBuilder;
	
	private final BillRowMapper searchBillRowMapper;

	@Autowired
	public BillRepository(JdbcTemplate jdbcTemplate, BillQueryBuilder queryBuilder, BillRowMapper searchBillRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryBuilder = queryBuilder;
		this.searchBillRowMapper = searchBillRowMapper;
	}

	public List<Bill> search(BillSearchRequest billSearchRequest){
		
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getBillQuery(billSearchRequest, preparedStatementValues, false);
        return jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), searchBillRowMapper);
	}

	public Integer searchCount(BillSearchRequest billSearchRequest){
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getSearchCountQueryString(billSearchRequest, preparedStatementValues);
		return jdbcTemplate.queryForObject(queryStr, preparedStatementValues.toArray(), Integer.class);
	}
}
