package org.egov.digit.expense.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.egov.digit.expense.repository.querybuilder.PaymentQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.PaymentRowMapper;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PaymentQueryBuilder queryBuilder;
	
	private final PaymentRowMapper paymentBillRowMapper;

	@Autowired
	public PaymentRepository(JdbcTemplate jdbcTemplate, PaymentQueryBuilder queryBuilder, PaymentRowMapper paymentBillRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryBuilder = queryBuilder;
		this.paymentBillRowMapper = paymentBillRowMapper;
	}

	public List<Payment> search(@Valid PaymentSearchRequest paymentSearchRequest) {

		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getPaymentQuery(paymentSearchRequest, preparedStatementValues);
		return jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), paymentBillRowMapper);
	}

	
}
