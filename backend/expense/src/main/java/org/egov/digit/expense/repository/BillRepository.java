package org.egov.digit.expense.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillRowMapper;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
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
	private final BillDetailRepository billDetailRepository;

	@Autowired
	public BillRepository(JdbcTemplate jdbcTemplate,
	                       BillQueryBuilder queryBuilder,
	                       BillRowMapper searchBillRowMapper,
	                       MultiStateInstanceUtil multiStateInstanceUtil,
	                       BillDetailRepository billDetailRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryBuilder = queryBuilder;
		this.searchBillRowMapper = searchBillRowMapper;
		this.multiStateInstanceUtil = multiStateInstanceUtil;
		this.billDetailRepository = billDetailRepository;
	}

	/**
	 * Two-step search: bill-only SQL then detail SQL, then enrich.
	 * This keeps bill and detail tables decoupled at the SQL level while
	 * preserving the existing contract (bills returned with details).
	 */
	public List<Bill> search(BillSearchRequest billSearchRequest, boolean isValidationSearch) {
		String tenantId = billSearchRequest.getBillCriteria().getTenantId();

		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getBillQuery(billSearchRequest, preparedStatementValues, false, isValidationSearch);
		try {
			queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, tenantId);
		} catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
		}
		List<Bill> bills = jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), searchBillRowMapper);
		if (bills == null || bills.isEmpty()) return Collections.emptyList();

		// Step 2: fetch details for all bill IDs and enrich
		List<String> billIds = bills.stream().map(Bill::getId).collect(Collectors.toList());
		List<BillDetail> details = billDetailRepository.searchByBillIds(billIds, tenantId);
		Map<String, List<BillDetail>> detailsByBillId = details.stream()
				.collect(Collectors.groupingBy(BillDetail::getBillId));
		for (Bill bill : bills) {
			bill.setBillDetails(detailsByBillId.getOrDefault(bill.getId(), Collections.emptyList()));
		}
		return bills;
	}

	public Map<String, Integer> searchStatusCount(BillSearchRequest billSearchRequest) {
		List<Object> preparedStatementValues = new ArrayList<>();
		String queryStr = queryBuilder.getStatusCountQuery(billSearchRequest, preparedStatementValues);
		try {
			queryStr = multiStateInstanceUtil.replaceSchemaPlaceholder(queryStr, billSearchRequest.getBillCriteria().getTenantId());
		} catch (InvalidTenantIdException e) {
			throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
		}
		Map<String, Integer> statusCount = new HashMap<>();
		jdbcTemplate.query(queryStr, preparedStatementValues.toArray(), rs -> {
			statusCount.put(rs.getString("status"), rs.getInt("status_count"));
		});
		return statusCount;
	}

	public Integer searchCount(BillSearchRequest billSearchRequest) {
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
