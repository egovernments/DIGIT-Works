package org.egov.works.repository.rowmapper;

import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.LOISearchCriteria;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
public class LOIQueryBuilder {


    private static final String FETCH_LOI_QUERY = "SELECT wms_loi.* " +
            " FROM eg_wms_loi AS wms_loi ";

    public String getLOIQuery(LOISearchCriteria searchCriteria, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_LOI_QUERY);

        List<String> ids = searchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.tenantId=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getLetterOfIndentNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.loi_number=? ");
            preparedStmtList.add(searchCriteria.getLetterOfIndentNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getWorkPackageNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.work_pkg_number=? ");
            preparedStmtList.add(searchCriteria.getWorkPackageNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getLetterStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.letter_status=? ");
            preparedStmtList.add(searchCriteria.getLetterStatus());
        }

        if (StringUtils.isNotBlank(searchCriteria.getContractorid())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" wms_loi.contractor_id=? ");
            preparedStmtList.add(searchCriteria.getContractorid());
        }

        if (searchCriteria.getFromAgreementDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);

            //If user does not specify toDate, take today's date as toDate by default.
            if (searchCriteria.getToAgreementDate() == null) {
                searchCriteria.setToAgreementDate(new BigDecimal(Instant.now().toEpochMilli()));
            }

            queryBuilder.append(" est.proposal_date BETWEEN ? AND ?");
            preparedStmtList.add(searchCriteria.getFromAgreementDate());
            preparedStmtList.add(searchCriteria.getToAgreementDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (searchCriteria.getToAgreementDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toAgreementDate without a fromAgreementDate");
            }
        }

        addOrderByClause(queryBuilder, searchCriteria);

        addLimitAndOffset(queryBuilder, searchCriteria, preparedStmtList);

        return queryBuilder.toString();
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, LOISearchCriteria criteria, List<Object> preparedStmtList) {
        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        queryBuilder.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }

    private void addOrderByClause(StringBuilder queryBuilder, LOISearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY wms_loi.createdtime ");
        } else {
            switch (LOISearchCriteria.SortBy.valueOf(criteria.getSortBy().name())) {
                case defectLiabilityPeriod:
                    queryBuilder.append(" ORDER BY wms_loi.defect_liability_period ");
                    break;
                case contractPeriod:
                    queryBuilder.append(" ORDER BY wms_loi.contract_period ");
                    break;
                case emdAmount:
                    queryBuilder.append(" ORDER BY wms_loi.emd_amount ");
                    break;
                case agreementDate:
                    queryBuilder.append(" ORDER BY wms_loi.agreement_date ");
                    break;
                case letterStatus:
                    queryBuilder.append(" ORDER BY wms_loi.letter_status ");
                    break;
                case createdTime:
                    queryBuilder.append(" ORDER BY wms_loi.createdtime ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY wms_loi.createdtime ");
                    break;
            }
        }

        if (criteria.getSortOrder() == LOISearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");

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

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }
}
