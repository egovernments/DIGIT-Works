package org.egov.digit.expense.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.PaymentQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.PaymentRowMapper;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
public class PaymentRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PaymentQueryBuilder queryBuilder;
	
	private final PaymentRowMapper paymentBillRowMapper;

	private final MultiStateInstanceUtil multiStateInstanceUtil;

	@Autowired
	public PaymentRepository(JdbcTemplate jdbcTemplate, PaymentQueryBuilder queryBuilder, PaymentRowMapper paymentBillRowMapper, MultiStateInstanceUtil multiStateInstanceUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryBuilder = queryBuilder;
		this.paymentBillRowMapper = paymentBillRowMapper;
		this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

	public List<Payment> search(@Valid PaymentSearchRequest paymentSearchRequest) {

		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getPaymentQuery(paymentSearchRequest, preparedStatementValues, false);
        try {
			// Applies schema replacement to the query string based on tenant ID
            queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, paymentSearchRequest.getPaymentCriteria().getTenantId());
        } catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        return jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), paymentBillRowMapper);
	}

	public Integer count(@Valid PaymentSearchRequest paymentSearchRequest) {
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getSearchCountQueryString(paymentSearchRequest, preparedStatementValues);
		try {
			// Applies schema replacement to the query string based on tenant ID
			queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, paymentSearchRequest.getPaymentCriteria().getTenantId());
		} catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
		}
		return jdbcTemplate.queryForObject(queryStr, preparedStatementValues.toArray(), Integer.class);
	}
}
