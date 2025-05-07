package org.egov.digit.expense.repository;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillRowMapper;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
public class BillRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final BillQueryBuilder queryBuilder;
	
	private final BillRowMapper searchBillRowMapper;

	private final MultiStateInstanceUtil multiStateInstanceUtil;

	@Autowired
	public BillRepository(JdbcTemplate jdbcTemplate, BillQueryBuilder queryBuilder, BillRowMapper searchBillRowMapper, MultiStateInstanceUtil multiStateInstanceUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryBuilder = queryBuilder;
		this.searchBillRowMapper = searchBillRowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

	public List<Bill> search(BillSearchRequest billSearchRequest, boolean isValidationSearch){
		
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getBillQuery(billSearchRequest, preparedStatementValues, false, isValidationSearch);
		try {
			queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, billSearchRequest.getBillCriteria().getTenantId());
		} catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
		}
        return jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), searchBillRowMapper);
	}

	public Integer searchCount(BillSearchRequest billSearchRequest){
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getSearchCountQueryString(billSearchRequest, preparedStatementValues);
		try {
			queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, billSearchRequest.getBillCriteria().getTenantId());
		} catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
		}
		return jdbcTemplate.queryForObject(queryStr, preparedStatementValues.toArray(), Integer.class);
	}
}