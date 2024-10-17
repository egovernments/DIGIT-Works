package org.egov.wms.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.wms.repository.builder.ReportQueryBuilder;
import org.egov.wms.repository.rowMapper.ReportRowMapper;
import org.egov.wms.web.model.Job.ReportJob;
import org.egov.wms.web.model.Job.ReportSearchCriteria;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ReportRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ReportQueryBuilder reportQueryBuilder;
    private final ReportRowMapper reportRowMapper;

    @Autowired
    public ReportRepository(JdbcTemplate jdbcTemplate, ReportQueryBuilder reportQueryBuilder, ReportRowMapper reportRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reportQueryBuilder = reportQueryBuilder;
        this.reportRowMapper = reportRowMapper;
    }

    public List<ReportJob> getReportJobs(ReportSearchRequest reportSearchRequest){
        log.info("ReportRepository::getReportJobs");
        List<Object> preparedStmtList = new ArrayList<>();
        String query = reportQueryBuilder.getReportJobQuery(reportSearchRequest, preparedStmtList, false);
        return jdbcTemplate.query(query, reportRowMapper, preparedStmtList.toArray());
    }

    public Integer getReportJobsCount(ReportSearchRequest reportSearchRequest){
        log.info("ReportRepository::getReportJobsCount");
        List<Object> preparedStmtList = new ArrayList<>();
        String query = reportQueryBuilder.getSearchCountQueryString(reportSearchRequest, preparedStmtList);
        return jdbcTemplate.queryForObject(query, preparedStmtList.toArray(), Integer.class);
    }
}
