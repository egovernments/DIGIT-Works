package org.egov.digit.expense.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.repository.querybuilder.BillTransactionReportQueryBuilder;
import org.egov.digit.expense.repository.rowmapper.BillTransactionReportRowMapper;
import org.egov.digit.expense.web.models.BillTransactionReport;
import org.egov.digit.expense.web.models.BillTransactionReportSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BillTransactionReportRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BillTransactionReportQueryBuilder queryBuilder;
    private final BillTransactionReportRowMapper rowMapper;

    @Autowired
    public BillTransactionReportRepository(JdbcTemplate jdbcTemplate,
                                           BillTransactionReportQueryBuilder queryBuilder,
                                           BillTransactionReportRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
    }

    public List<BillTransactionReport> search(BillTransactionReportSearchCriteria criteria) {
        log.info("BillTransactionReportRepository::search");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, preparedStatementValues);
        log.debug("Search query: {}", query);
        return jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
    }

    public Integer count(BillTransactionReportSearchCriteria criteria) {
        log.info("BillTransactionReportRepository::count");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getCountQuery(criteria, preparedStatementValues);
        log.debug("Count query: {}", query);
        return jdbcTemplate.queryForObject(query, preparedStatementValues.toArray(), Integer.class);
    }

    public BillTransactionReport getLatestReport(String billId, String tenantId, String type) {
        log.info("BillTransactionReportRepository::getLatestReport");
        List<Object> preparedStatementValues = new ArrayList<>();
        String query = queryBuilder.getLatestReportQuery(billId, tenantId, type, preparedStatementValues);
        log.debug("Latest report query: {}", query);
        List<BillTransactionReport> reports = jdbcTemplate.query(query, preparedStatementValues.toArray(), rowMapper);
        return CollectionUtils.isEmpty(reports) ? null : reports.get(0);
    }
}
