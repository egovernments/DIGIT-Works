package org.egov.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SanctionDetailQueryBuilder {

    private static final String SANCTION_DETAILS_SELECT_QUERY = " SELECT jsd.id as jsdId, " +
            "jsd.tenantId as jsdTenantId, " +
            "jsd.hoaCode as jsdHoaCode, " +
            "jsd.ddoCode as jsdDdoCode, " +
            "jsd.masterAllotmentId as jsdMasterAllotmentId, " +
            "jsd.sanctionedAmount as jsdSanctionedAmount, " +
            "jsd.financialYear as jsdFinancialYear, " +
            "jsd.additionalDetails as jsdAdditionalDetails, " +
            "jsd.createdby as jsdCreatedBy, " +
            "jsd.lastmodifiedby as jsdLastModifiedBy, " +
            "jsd.createdtime as jsdCreatedTime, " +
            "jsd.lastmodifiedtime as jsdLastModifiedTime, " +
            "jfs.id as jfsId, " +
            "jfs.tenantId as jfsTenantId, " +
            "jfs.sanctionId as jfsSanctionId, " +
            "jfs.allottedAmount as jfsAllottedAmount, " +
            "jfs.availableAmount as jfsAvailableAmount, " +
            "jfs.additionalDetails as jfsAdditionalDetails, " +
            "jfs.createdby as jfsCreatedBy, " +
            "jfs.lastmodifiedby as jfsLastModifiedBy, " +
            "jfs.createdtime as jfsCreatedTime, " +
            "jfs.lastmodifiedtime as jfsLastModifiedTime " +
            "FROM jit_sanction_details AS jsd " +
            "LEFT JOIN " +
            "jit_funds_summary AS jfs " +
            "ON (jsd.id=jfs.sanctionId) ";

    public static final String SANCTION_DETAILS_INSERT_QUERY = "INSERT INTO jit_sanction_details (id, tenantId, hoaCode, ddoCode,"
            + " masterAllotmentId, sanctionedAmount, financialYear, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :hoaCode, :ddoCode, :masterAllotmentId, :sanctionedAmount, :financialYear, :additionalDetails,"
            + " :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public String getSanctionDetailsSearchQuery(SanctionDetailsSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(SANCTION_DETAILS_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.tenantId=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getHoaCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.hoaCode=? ");
            preparedStmtList.add(criteria.getHoaCode());
        }
        if (StringUtils.isNotBlank(criteria.getDdoCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.ddoCode=? ");
            preparedStmtList.add(criteria.getDdoCode());
        }
        if (StringUtils.isNotBlank(criteria.getMasterAllotmentId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" jsd.masterAllotmentId=? ");
            preparedStmtList.add(criteria.getMasterAllotmentId());
        }


        addOrderByClause(query, criteria);

        addLimitAndOffset(query, criteria, preparedStmtList);

        return query.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, SanctionDetailsSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY jsd.lastmodifiedtime ");
        }

        if (criteria.getSortOrder() == SanctionDetailsSearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, SanctionDetailsSearchCriteria criteria, List<Object> preparedStmtList) {
        if (criteria.getOffset() != null) {
            queryBuilder.append(" OFFSET ? ");
            preparedStmtList.add(criteria.getOffset());
        }

        if (criteria.getLimit() != null) {
            queryBuilder.append(" LIMIT ? ");
            preparedStmtList.add(criteria.getLimit());
        }

    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
}
