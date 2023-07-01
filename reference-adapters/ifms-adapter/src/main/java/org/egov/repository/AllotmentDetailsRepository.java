package org.egov.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.repository.querybuilder.SanctionDetailQueryBuilder;
import org.egov.repository.rowmapper.SanctionDetailRowMapper;
import org.egov.utils.HelperUtil;
import org.egov.web.models.jit.Allotment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.egov.repository.querybuilder.AllotmentDetailsQueryBuilder.ALLOTMENT_DETAILS_INSERT_QUERY;

@Repository
@Slf4j
public class AllotmentDetailsRepository {
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
    public void saveAllotmentDetails(List<Allotment> allotments) {
        List<MapSqlParameterSource> allotmentSqlParameterSources = getSqlParameterListForAllotments(allotments);
        namedJdbcTemplate.batchUpdate(ALLOTMENT_DETAILS_INSERT_QUERY, allotmentSqlParameterSources.toArray(new MapSqlParameterSource[0]));
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

}
