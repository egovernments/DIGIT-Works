package org.egov.digit.expense.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillTransactionReportQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillTransactionReportRowMapper;
import org.egov.digit.expense.web.models.BillTransactionReport;
import org.egov.digit.expense.web.models.BillTransactionReportSearchCriteria;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.egov.digit.expense.config.Constants.INVALID_TENANT_ID_ERR_CODE;

@Repository
@Slf4j
public class BillTransactionReportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BillTransactionReportQueryBuilder queryBuilder;
    private final BillTransactionReportRowMapper rowMapper;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public BillTransactionReportRepository(JdbcTemplate jdbcTemplate,
                                           BillTransactionReportQueryBuilder queryBuilder,
                                           BillTransactionReportRowMapper rowMapper,
                                           MultiStateInstanceUtil multiStateInstanceUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public List<BillTransactionReport> search(BillTransactionReportSearchCriteria criteria) {
        log.info("BillTransactionReportRepository::search");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, criteria.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        log.debug("Search query: {}", query);
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
    }

    public Integer count(BillTransactionReportSearchCriteria criteria) {
        log.info("BillTransactionReportRepository::count");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getCountQuery(criteria, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, criteria.getTenantId());
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        log.debug("Count query: {}", query);
        return jdbcTemplate.queryForObject(query, preparedStatementValues.toArray(), Integer.class);
    }

    public BillTransactionReport getLatestReport(String billId, String tenantId, String type) {
        log.info("BillTransactionReportRepository::getLatestReport");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getLatestReportQuery(billId, tenantId, type, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        log.debug("Latest report query: {}", query);
        List<BillTransactionReport> reports = jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
        return CollectionUtils.isEmpty(reports) ? null : reports.get(0);
    }
}
