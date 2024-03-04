package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.enrichment.VirtualAllotmentEnrichment;
import org.egov.repository.querybuilder.SanctionDetailQueryBuilder;
import org.egov.repository.rowmapper.SanctionDetailRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.Allotment;
import org.egov.web.models.jit.FundsSummary;
import org.egov.web.models.jit.SanctionDetail;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.egov.repository.querybuilder.SanctionDetailQueryBuilder.FUNDS_SUMMARY_INSERT_QUERY;
import static org.egov.repository.querybuilder.SanctionDetailQueryBuilder.FUNDS_SUMMARY_UPDATE_QUERY;
import static org.egov.repository.querybuilder.SanctionDetailQueryBuilder.ALLOTMENT_DETAILS_INSERT_QUERY;
import static org.egov.repository.querybuilder.SanctionDetailQueryBuilder.SANCTION_DETAILS_INSERT_QUERY;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class SanctionDetailsRepository {
    @Autowired
    private SanctionDetailRowMapper rowMapper;

    @Autowired
    private SanctionDetailQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private HelperUtil util;
    @Autowired
    private VirtualAllotmentEnrichment vaEnrichment;

    @Transactional
    public void createUpdateSanctionFunds(List<SanctionDetail> createSanctions, List<SanctionDetail> updateSanctions, List<Allotment> createAllotments) {
        try {
            // Create new sanctions with fund summaries
            if (createSanctions != null && !createSanctions.isEmpty()) {
                List<MapSqlParameterSource> sanctionDetailsSqlParameterSources = getSqlParameterListForSanctionDetails(createSanctions);
                namedJdbcTemplate.batchUpdate(SANCTION_DETAILS_INSERT_QUERY, sanctionDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));

                List<FundsSummary> createFundsSummaries = vaEnrichment.getFundsSummariesFromSanctions(createSanctions);
                if (createFundsSummaries != null && !createFundsSummaries.isEmpty()) {
                    List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForFundsSummaries(createFundsSummaries);
                    namedJdbcTemplate.batchUpdate(FUNDS_SUMMARY_INSERT_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
                }
            }

            // Update existing sanction details
            if (updateSanctions != null && !updateSanctions.isEmpty()) {
                List<FundsSummary> updateFundsSummaries = vaEnrichment.getFundsSummariesFromSanctions(updateSanctions);
                List<MapSqlParameterSource> sqlParameterSources = getSqlParameterListForFundsSummaryUpdate(updateFundsSummaries);
                namedJdbcTemplate.batchUpdate(FUNDS_SUMMARY_UPDATE_QUERY, sqlParameterSources.toArray(new MapSqlParameterSource[0]));
            }

            if (createAllotments != null && !createAllotments.isEmpty()) {
                List<MapSqlParameterSource> allotmentSqlParameterSources = getSqlParameterListForAllotments(createAllotments);
                namedJdbcTemplate.batchUpdate(ALLOTMENT_DETAILS_INSERT_QUERY, allotmentSqlParameterSources.toArray(new MapSqlParameterSource[0]));
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in createUpdateSanctionFunds while creating/updating sanction details  "+ e);
        }
    }
    @Transactional
    public void saveSanctionDetails(List<SanctionDetail> sanctionDetails) {
        if (sanctionDetails != null && !sanctionDetails.isEmpty()) {
            List<MapSqlParameterSource> sanctionDetailsSqlParameterSources = getSqlParameterListForSanctionDetails(sanctionDetails);
            namedJdbcTemplate.batchUpdate(SANCTION_DETAILS_INSERT_QUERY, sanctionDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        }
    }

    @Transactional
    public void saveAllotmentDetails(List<Allotment> allotments) {
        List<MapSqlParameterSource> allotmentSqlParameterSources = getSqlParameterListForAllotments(allotments);
        namedJdbcTemplate.batchUpdate(ALLOTMENT_DETAILS_INSERT_QUERY, allotmentSqlParameterSources.toArray(new MapSqlParameterSource[0]));
    }


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

    private List<MapSqlParameterSource> getSqlParameterListForSanctionDetails(List<SanctionDetail> sanctionDetails) {

        List<MapSqlParameterSource> sanctionDetailParamMapList = new ArrayList<>();
        for (SanctionDetail sanctionDetail : sanctionDetails) {

            MapSqlParameterSource sanctionDetailParamMap = new MapSqlParameterSource();
            sanctionDetailParamMap.addValue("id", sanctionDetail.getId());
            sanctionDetailParamMap.addValue("tenantId", sanctionDetail.getTenantId());
            sanctionDetailParamMap.addValue("hoaCode", sanctionDetail.getHoaCode());
            sanctionDetailParamMap.addValue("ddoCode", sanctionDetail.getDdoCode());
            sanctionDetailParamMap.addValue("masterAllotmentId", sanctionDetail.getMasterAllotmentId());
            sanctionDetailParamMap.addValue("sanctionedAmount", sanctionDetail.getSanctionedAmount());
            sanctionDetailParamMap.addValue("financialYear", sanctionDetail.getFinancialYear());
            sanctionDetailParamMap.addValue("additionalDetails", util.getPGObject(sanctionDetail.getAdditionalDetails()));
            sanctionDetailParamMap.addValue("createdby", sanctionDetail.getAuditDetails().getCreatedBy());
            sanctionDetailParamMap.addValue("createdtime", sanctionDetail.getAuditDetails().getCreatedTime());
            sanctionDetailParamMap.addValue("lastmodifiedby", sanctionDetail.getAuditDetails().getLastModifiedBy());
            sanctionDetailParamMap.addValue("lastmodifiedtime", sanctionDetail.getAuditDetails().getLastModifiedTime());
            sanctionDetailParamMapList.add(sanctionDetailParamMap);
        }
        return sanctionDetailParamMapList;
    }

    private List<MapSqlParameterSource> getSqlParameterListForAllotments(List<Allotment> allotments) {

        List<MapSqlParameterSource> allotmentParamMapList = new ArrayList<>();
        for (Allotment allotment : allotments) {

            MapSqlParameterSource allotmentParamMap = new MapSqlParameterSource();
            allotmentParamMap.addValue("id", allotment.getId());
            allotmentParamMap.addValue("tenantId", allotment.getTenantId());
            allotmentParamMap.addValue("sanctionId", allotment.getSanctionId());
            allotmentParamMap.addValue("allotmentSerialNo", allotment.getAllotmentSerialNo());
            allotmentParamMap.addValue("ssuAllotmentId", allotment.getSsuAllotmentId());
            allotmentParamMap.addValue("allotmentTransactionType", allotment.getAllotmentTxnType());
            allotmentParamMap.addValue("allotmentAmount", allotment.getDecimalAllottedAmount());
            allotmentParamMap.addValue("sanctionBalance", allotment.getDecimalSanctionBalance());
            allotmentParamMap.addValue("allotmentDate", allotment.getAllotmentDateTimeStamp());
            allotmentParamMap.addValue("additionalDetails", util.getPGObject(allotment.getAdditionalDetails()));
            allotmentParamMap.addValue("createdby", allotment.getAuditDetails().getCreatedBy());
            allotmentParamMap.addValue("createdtime", allotment.getAuditDetails().getCreatedTime());
            allotmentParamMap.addValue("lastmodifiedby", allotment.getAuditDetails().getLastModifiedBy());
            allotmentParamMap.addValue("lastmodifiedtime", allotment.getAuditDetails().getLastModifiedTime());
            allotmentParamMapList.add(allotmentParamMap);
        }
        return allotmentParamMapList;
    }

    public List<SanctionDetail> getSanctionDetails(SanctionDetailsSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        log.info("Fetching Executed sanction. tenantId ["+searchCriteria.getTenantId()+"]");
        String query = queryBuilder.getSanctionDetailsSearchQuery(searchCriteria, preparedStmtList);
        log.info("Query build successfully. tenantId ["+searchCriteria.getTenantId()+"]");
        List<SanctionDetail> sanctionDetailList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        log.info("Fetched Executed sanction. tenantId ["+searchCriteria.getTenantId()+"]");
        return sanctionDetailList;
    }
}
