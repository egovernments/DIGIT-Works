package org.egov.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.jit.SanctionDetailsSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SanctionDetailQueryBuilder {

    private static final String SANCTION_DETAILS_SELECT_QUERY = " SELECT snacDetails.id as snacDetailsId, " +
            "snacDetails.tenantId as snacDetailsTenantId, " +
            "snacDetails.programCode as snacDetailsProgramCode, "+
            "snacDetails.hoaCode as snacDetailsHoaCode, " +
            "snacDetails.ddoCode as snacDetailsDdoCode, " +
            "snacDetails.masterAllotmentId as snacDetailsMasterAllotmentId, " +
            "snacDetails.sanctionedAmount as snacDetailsSanctionedAmount, " +
            "snacDetails.financialYear as snacDetailsFinancialYear, " +
            "snacDetails.additionalDetails as snacDetailsAdditionalDetails, " +
            "snacDetails.createdby as snacDetailsCreatedBy, " +
            "snacDetails.lastmodifiedby as snacDetailsLastModifiedBy, " +
            "snacDetails.createdtime as snacDetailsCreatedTime, " +
            "snacDetails.lastmodifiedtime as snacDetailsLastModifiedTime, " +
            "fundsSummary.id as fundsSummaryId, " +
            "fundsSummary.tenantId as fundsSummaryTenantId, " +
            "fundsSummary.sanctionId as fundsSummarySanctionId, " +
            "fundsSummary.allottedAmount as fundsSummaryAllottedAmount, " +
            "fundsSummary.availableAmount as fundsSummaryAvailableAmount, " +
            "fundsSummary.additionalDetails as fundsSummaryAdditionalDetails, " +
            "fundsSummary.createdby as fundsSummaryCreatedBy, " +
            "fundsSummary.lastmodifiedby as fundsSummaryLastModifiedBy, " +
            "fundsSummary.createdtime as fundsSummaryCreatedTime, " +
            "fundsSummary.lastmodifiedtime as fundsSummaryLastModifiedTime, " +
            "allotmentDetails.id as allotmentDetailsId, " +
            "allotmentDetails.tenantId as allotmentDetailsTenantId, " +
            "allotmentDetails.sanctionId as allotmentDetailsSanctionId, " +
            "allotmentDetails.allotmentSerialNo as allotmentDetailsAllotmentSerialNo, " +
            "allotmentDetails.ssuAllotmentId as allotmentDetailsSsuAllotmentId, " +
            "allotmentDetails.allotmentAmount as allotmentDetailsAllotmentAmount, " +
            "allotmentDetails.allotmentTransactionType as allotmentDetailsAllotmentTransactionType, " +
            "allotmentDetails.sanctionBalance as allotmentDetailsSanctionBalance, " +
            "allotmentDetails.allotmentDate as allotmentDetailsAllotmentDate, " +
            "allotmentDetails.additionalDetails as allotmentDetailsAdditionalDetails, " +
            "allotmentDetails.createdtime as allotmentDetailsCreatedtime, " +
            "allotmentDetails.createdby as allotmentDetailsCreatedby, " +
            "allotmentDetails.lastmodifiedtime as allotmentDetailsLastmodifiedtime, " +
            "allotmentDetails.lastmodifiedby as allotmentDetailsLastmodifiedby " +
            "FROM jit_sanction_details AS snacDetails " +
            "LEFT JOIN " +
            "jit_funds_summary AS fundsSummary " +
            "ON (snacDetails.id=fundsSummary.sanctionId) " +
            "LEFT JOIN " +
            "jit_allotment_details AS allotmentDetails " +
            "ON (snacDetails.id=allotmentDetails.sanctionId)";

    public static final String SANCTION_DETAILS_INSERT_QUERY = "INSERT INTO jit_sanction_details (id, tenantId, hoaCode, ddoCode, programCode,"
            + " masterAllotmentId, sanctionedAmount, financialYear, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :hoaCode, :ddoCode, :programCode, :masterAllotmentId, :sanctionedAmount, :financialYear, :additionalDetails,"
            + " :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public static final String ALLOTMENT_DETAILS_INSERT_QUERY = "INSERT INTO jit_allotment_details (id, tenantId, sanctionId, allotmentSerialNo, ssuAllotmentId,"
            + " allotmentAmount, allotmentTransactionType, sanctionBalance, allotmentDate, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :sanctionId, :allotmentSerialNo, :ssuAllotmentId, :allotmentAmount, :allotmentTransactionType, :sanctionBalance, :allotmentDate,"
            + " :additionalDetails, :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public static final String FUNDS_SUMMARY_INSERT_QUERY = "INSERT INTO jit_funds_summary (id, tenantId, sanctionId,"
            + " allottedAmount, availableAmount, additionalDetails, createdby, createdtime, lastmodifiedby, lastmodifiedtime) "
            + "	VALUES (:id, :tenantId, :sanctionId, :allottedAmount, :availableAmount, :additionalDetails,"
            + " :createdby, :createdtime, :lastmodifiedby, :lastmodifiedtime);";

    public static final String FUNDS_SUMMARY_UPDATE_QUERY = "UPDATE jit_funds_summary SET allottedAmount=:allottedAmount, availableAmount=:availableAmount,"
            + "additionalDetails=:additionalDetails, lastmodifiedby=:lastmodifiedby,lastmodifiedtime=:lastmodifiedtime "
            + " WHERE id=:id";



    public String getSanctionDetailsSearchQuery(SanctionDetailsSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(SANCTION_DETAILS_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" snacDetails.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" snacDetails.tenantId=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getHoaCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" snacDetails.hoaCode=? ");
            preparedStmtList.add(criteria.getHoaCode());
        }
        if (StringUtils.isNotBlank(criteria.getDdoCode())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" snacDetails.ddoCode=? ");
            preparedStmtList.add(criteria.getDdoCode());
        }
        if (StringUtils.isNotBlank(criteria.getMasterAllotmentId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" snacDetails.masterAllotmentId=? ");
            preparedStmtList.add(criteria.getMasterAllotmentId());
        }


        addOrderByClause(query, criteria);

        addLimitAndOffset(query, criteria, preparedStmtList);

        return query.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, SanctionDetailsSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY snacDetails.lastmodifiedtime ");
        }else{
            queryBuilder.append(" ORDER BY snacDetails.").append(criteria.getSortBy().name());
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
