package org.egov.digit.expense.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.digit.expense.repository.querybuilder.BillReportQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillReportRowMapper;
import org.egov.digit.expense.web.models.BillReport;
import org.egov.digit.expense.web.models.BillReportSearchCriteria;
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
public class BillReportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BillReportQueryBuilder queryBuilder;
    private final BillReportRowMapper rowMapper;
    private final MultiStateInstanceUtil multiStateInstanceUtil;

    @Autowired
    public BillReportRepository(JdbcTemplate jdbcTemplate,
                                BillReportQueryBuilder queryBuilder,
                                BillReportRowMapper rowMapper,
                                MultiStateInstanceUtil multiStateInstanceUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.multiStateInstanceUtil = multiStateInstanceUtil;
    }

    public List<BillReport> search(BillReportSearchCriteria criteria) {
        log.info("BillReportRepository::search");
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

    public Integer count(BillReportSearchCriteria criteria) {
        log.info("BillReportRepository::count");
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

    public BillReport getLatestReport(String billId, String tenantId, String type) {
        log.info("BillReportRepository::getLatestReport");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getLatestReportQuery(billId, tenantId, type, preparedStatementValues);
        try {
            query = multiStateInstanceUtil.replaceSchemaPlaceholder(query, tenantId);
        } catch (InvalidTenantIdException e) {
            throw new CustomException(INVALID_TENANT_ID_ERR_CODE, e.getMessage());
        }
        log.debug("Latest report query: {}", query);
        List<BillReport> reports = jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
        return CollectionUtils.isEmpty(reports) ? null : reports.get(0);
    }
}
