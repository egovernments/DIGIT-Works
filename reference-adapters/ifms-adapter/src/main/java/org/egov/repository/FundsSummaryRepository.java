package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.SanctionDetailQueryBuilder;
import org.egov.repository.rowmapper.SanctionDetailRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.FundsSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.egov.repository.querybuilder.FundsSummaryQueryBuilder.FUNDS_SUMMARY_INSERT_QUERY;
import static org.egov.repository.querybuilder.FundsSummaryQueryBuilder.FUNDS_SUMMARY_UPDATE_QUERY;

@Repository
@Slf4j
public class FundsSummaryRepository {
    @Autowired
    private SanctionDetailRowMapper rowMapper;

    @Autowired
    private SanctionDetailQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    HelperUtil util;

    @Transactional
    public void saveFundsSummary(List<FundsSummary> fundsSummaries) {
        List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForFundsSummaries(fundsSummaries);
        namedJdbcTemplate.batchUpdate(FUNDS_SUMMARY_INSERT_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    @Transactional
    public void updateFundsSummary(List<FundsSummary> fundsSummaries) {
        List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForFundsSummaryUpdate(fundsSummaries);
        namedJdbcTemplate.batchUpdate(FUNDS_SUMMARY_UPDATE_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }

    private List<MapSqlParameterSource> getSqlParameterListForFundsSummaries(List<FundsSummary> fundsSummaries) {

        List<MapSqlParameterSource> fundsSummaryParamMapList = new ArrayList<>();
        for (FundsSummary fundsSummary : fundsSummaries) {

            MapSqlParameterSource fundsSummaryParamMap = new MapSqlParameterSource();
            fundsSummaryParamMap.addValue("id", fundsSummary.getId());
            fundsSummaryParamMap.addValue("tenantId", fundsSummary.getTenantId());
            fundsSummaryParamMap.addValue("sanctionId", fundsSummary.getSanctionId());
            fundsSummaryParamMap.addValue("allottedAmount", fundsSummary.getAllottedAmount());
            fundsSummaryParamMap.addValue("availableAmount", fundsSummary.getAvailableAmount());
            fundsSummaryParamMap.addValue("additionalDetails", util.getPGObject(fundsSummary.getAdditionalDetails()));
            fundsSummaryParamMap.addValue("createdby", fundsSummary.getAuditDetails().getCreatedBy());
            fundsSummaryParamMap.addValue("createdtime", fundsSummary.getAuditDetails().getCreatedTime());
            fundsSummaryParamMap.addValue("lastmodifiedby", fundsSummary.getAuditDetails().getLastModifiedBy());
            fundsSummaryParamMap.addValue("lastmodifiedtime", fundsSummary.getAuditDetails().getLastModifiedTime());
            fundsSummaryParamMapList.add(fundsSummaryParamMap);
        }
        return fundsSummaryParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForFundsSummaryUpdate(List<FundsSummary> fundsSummaries) {

        List<MapSqlParameterSource> fundsSummaryParamMapList = new ArrayList<>();
        for (FundsSummary fundsSummary : fundsSummaries) {

            MapSqlParameterSource fundsSummaryParamMap = new MapSqlParameterSource();
            fundsSummaryParamMap.addValue("allottedAmount", fundsSummary.getAllottedAmount());
            fundsSummaryParamMap.addValue("availableAmount", fundsSummary.getAvailableAmount());
            fundsSummaryParamMap.addValue("additionalDetails", util.getPGObject(fundsSummary.getAdditionalDetails()));
            fundsSummaryParamMap.addValue("lastmodifiedby", fundsSummary.getAuditDetails().getLastModifiedBy());
            fundsSummaryParamMap.addValue("lastmodifiedtime", fundsSummary.getAuditDetails().getLastModifiedTime());
            fundsSummaryParamMap.addValue("id", fundsSummary.getId());
            fundsSummaryParamMapList.add(fundsSummaryParamMap);
        }
        return fundsSummaryParamMapList;
    }

}
