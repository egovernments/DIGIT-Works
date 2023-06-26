package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.SanctionDetailQueryBuilder;
import org.egov.repository.rowmapper.SanctionDetailRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.SanctionDetail;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    HelperUtil util;
    @Transactional
    public void saveSanctionDetails(List<SanctionDetail> sanctionDetails) {
        if (sanctionDetails != null && !sanctionDetails.isEmpty()) {
            List<MapSqlParameterSource> sanctionDetailsSqlParameterSources = getSqlParameterListForSanctionDetails(sanctionDetails);
            namedJdbcTemplate.batchUpdate(SANCTION_DETAILS_INSERT_QUERY, sanctionDetailsSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        }
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
