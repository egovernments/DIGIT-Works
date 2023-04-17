package org.egov.works.repository.rowmapper;

import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
public class EstimateQueryBuilder {

    @Autowired
    private EstimateServiceConfiguration config;


    private static final String FETCH_ESTIMATE_QUERY = "SELECT est.*," +
            "estDetail.*, est.id as est_id, est.lastModifiedTime as est_lastModifiedTime, estDetail.id AS estDetailId, estDetail.additionaldetails AS estDetailAdditional " +
            "FROM eg_wms_estimate AS est " +
            "LEFT JOIN " +
            "eg_wms_estimate_detail AS estDetail " +
            "ON (est.id=estDetail.estimate_id) ";

    private static final String ESTIMATE_COUNT_QUERY = "SELECT distinct(est.estimate_number) " +
            "FROM eg_wms_estimate AS est " +
            "LEFT JOIN " +
            "eg_wms_estimate_detail AS estDetail " +
            "ON (est.id=estDetail.estimate_id) ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY est_lastModifiedTime DESC , est_id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }

    public String getEstimateQuery(EstimateSearchCriteria searchCriteria, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        if(!searchCriteria.getIsCountNeeded())
            queryBuilder = new StringBuilder(FETCH_ESTIMATE_QUERY);
        else
            queryBuilder = new StringBuilder(ESTIMATE_COUNT_QUERY);

        List<String> ids = searchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.tenantId=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getDepartment())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.department=? ");
            preparedStmtList.add(searchCriteria.getDepartment());
        }

        if (StringUtils.isNotBlank(searchCriteria.getAdminSanctionNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.admin_sanction_number=? ");
            preparedStmtList.add(searchCriteria.getAdminSanctionNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getEstimateNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.estimate_number=? ");
            preparedStmtList.add(searchCriteria.getEstimateNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getEstimateStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.estimate_status=? ");
            preparedStmtList.add(searchCriteria.getEstimateStatus());
        }

        if (StringUtils.isNotBlank(searchCriteria.getEstimateDetailNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" estDetail.estimate_detail_number=? ");
            preparedStmtList.add(searchCriteria.getEstimateDetailNumber());
        }

        if (searchCriteria.getFromProposalDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);

            //If user does not specify toDate, take today's date as toDate by default.
            if (searchCriteria.getToProposalDate() == null) {
                searchCriteria.setToProposalDate(new BigDecimal(Instant.now().toEpochMilli()));
            }

            queryBuilder.append(" est.proposal_date BETWEEN ? AND ?");
            preparedStmtList.add(searchCriteria.getFromProposalDate());
            preparedStmtList.add(searchCriteria.getToProposalDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (searchCriteria.getToProposalDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toProposalDate without a fromProposalDate");
            }
        }
        if (StringUtils.isNotBlank(searchCriteria.getTypeOfWork())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.type_of_work=? ");
            preparedStmtList.add(searchCriteria.getTypeOfWork());
        }

        //TODO -estimateType

        //addLimitAndOffset(queryBuilder, searchCriteria, preparedStmtList);
        if(!searchCriteria.getIsCountNeeded()){
            addOrderByClause(queryBuilder, searchCriteria);
            return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, searchCriteria);
        }

        return queryBuilder.toString();
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, EstimateSearchCriteria criteria, List<Object> preparedStmtList) {
        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        queryBuilder.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }

    private void addOrderByClause(StringBuilder queryBuilder, EstimateSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY est.createdtime ");
        } else {
            switch (EstimateSearchCriteria.SortBy.valueOf(criteria.getSortBy().name())) {
                case department:
                    queryBuilder.append(" ORDER BY est.department ");
                    break;
                case estimateStatus:
                    queryBuilder.append(" ORDER BY est.estimate_status ");
                    break;
                case proposalDate:
                    queryBuilder.append(" ORDER BY est.proposal_date ");
                    break;
                case createdTime:
                    queryBuilder.append(" ORDER BY est.createdtime ");
                    break;
                case totalAmount:
                    queryBuilder.append(" ORDER BY est.total_amount ");
                    break;
                case typeOfWork:
                    queryBuilder.append(" ORDER BY est.type_of_work ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY est.createdtime ");
                    break;
            }
        }

        if (criteria.getSortOrder() == EstimateSearchCriteria.SortOrder.ASC)
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

    private String addPaginationWrapper(String query,List<Object> preparedStmtList,
                                        EstimateSearchCriteria criteria){
        int limit = config.getDefaultLimit();
        int offset = config.getDefaultOffset();
        String finalQuery = paginationWrapper.replace("{}",query);

        if(criteria.getLimit()!=null && criteria.getLimit()<=config.getMaxLimit())
            limit = criteria.getLimit();

        if(criteria.getLimit()!=null && criteria.getLimit()>config.getMaxLimit())
            limit = config.getMaxLimit();

        if(criteria.getOffset()!=null)
            offset = criteria.getOffset();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit+offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(EstimateSearchCriteria criteria, List<Object> preparedStmtList) {
        String query = getEstimateQuery(criteria, preparedStmtList);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
