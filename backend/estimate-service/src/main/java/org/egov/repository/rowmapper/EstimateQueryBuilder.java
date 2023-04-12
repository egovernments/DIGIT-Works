package org.egov.repository.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.EstimateServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class EstimateQueryBuilder {

    @Autowired
    private EstimateServiceConfiguration config;


    private static final String FETCH_ESTIMATE_QUERY = "SELECT est.*," +
            "estDetail.*,estAmtDetail.*,estAdd.*, est.id as estId,estDetail.description as estDetailDescription,est.last_modified_time as estLastModifiedTime, estDetail.id AS estDetailId, " +
            "estDetail.additional_details AS estDetailAdditional,estAmtDetail.additional_details AS estAmtDetailAdditional," +
            "estAdd.id AS estAddId,estAmtDetail.id AS estAmtDetailId,estDetail.estimate_id AS estDetailEstId," +
            "estDetail.is_active AS estDetailActive,estAmtDetail.is_active AS estAmtDetailActive,estDetail.name AS estDetailName "+
            "FROM eg_wms_estimate AS est " +
            "LEFT JOIN " +
            "eg_wms_estimate_detail AS estDetail " +
            "ON (est.id=estDetail.estimate_id) " +
            "LEFT JOIN " +
            "eg_wms_estimate_address AS estAdd " +
            "ON (est.id=estAdd.estimate_id) " +
            "LEFT JOIN " +
            "eg_wms_estimate_amount_detail AS estAmtDetail " +
            "ON (estDetail.id=estAmtDetail.estimate_detail_id) ";

    private static final String ESTIMATE_COUNT_QUERY = "SELECT distinct(est.estimate_number) " +
            "FROM eg_wms_estimate AS est " +
            "LEFT JOIN " +
            "eg_wms_estimate_detail AS estDetail " +
            "ON (est.id=estDetail.estimate_id) " +
            "LEFT JOIN " +
            "eg_wms_estimate_address AS estAdd " +
            "ON (est.id=estAdd.estimate_id) " +
            "LEFT JOIN " +
            "eg_wms_estimate_amount_detail AS estAmtDetail " +
            "ON (estDetail.id=estAmtDetail.estimate_detail_id) ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY estLastModifiedTime DESC , estId) offset_ FROM " +
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
        log.info("EstimateQueryBuilder::getEstimateQuery");
        StringBuilder queryBuilder = null;
        if (!searchCriteria.getIsCountNeeded())
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
            queryBuilder.append(" est.tenant_id=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getExecutingDepartment())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.executing_department=? ");
            preparedStmtList.add(searchCriteria.getExecutingDepartment());
        }

        if (StringUtils.isNotBlank(searchCriteria.getEstimateNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.estimate_number=? ");
            preparedStmtList.add(searchCriteria.getEstimateNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getReferenceNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.reference_number=? ");
            preparedStmtList.add(searchCriteria.getReferenceNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getWfStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.wf_status=? ");
            preparedStmtList.add(searchCriteria.getWfStatus());
        }

        if (StringUtils.isNotBlank(searchCriteria.getProjectId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" est.project_id=? ");
            preparedStmtList.add(searchCriteria.getProjectId());
        }

        //added the default as active line item and amount detail
        addClauseIfRequired(preparedStmtList, queryBuilder);
        queryBuilder.append(" estDetail.is_active=? ");
        preparedStmtList.add(Boolean.TRUE);

        addClauseIfRequired(preparedStmtList, queryBuilder);
        queryBuilder.append(" estAmtDetail.is_active=? ");
        preparedStmtList.add(Boolean.TRUE);


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
        if (!searchCriteria.getIsCountNeeded()) {
            addOrderByClause(queryBuilder, searchCriteria);
            return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, searchCriteria);
        }

        return queryBuilder.toString();
    }

    //TODO : check -> do we need to orderby estimate.totalEstimateAmount ?
    private void addOrderByClause(StringBuilder queryBuilder, EstimateSearchCriteria criteria) {
        log.info("EstimateQueryBuilder::getEstimateQuery");
        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY est.created_time ");
        } else {
            switch (EstimateSearchCriteria.SortBy.valueOf(criteria.getSortBy().name())) {
                case executingDepartment:
                    queryBuilder.append(" ORDER BY est.executing_department ");
                    break;
                case wfStatus:
                    queryBuilder.append(" ORDER BY est.wf_status ");
                    break;
                case proposalDate:
                    queryBuilder.append(" ORDER BY est.proposal_date ");
                    break;
                case createdTime:
                    queryBuilder.append(" ORDER BY est.created_time ");
                    break;
                case totalAmount:
                    queryBuilder.append(" ORDER BY estDetail.total_amount ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY est.created_time ");
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

    private String addPaginationWrapper(String query, List<Object> preparedStmtList,
                                        EstimateSearchCriteria criteria) {
        log.info("EstimateQueryBuilder::addPaginationWrapper");
        int limit = config.getDefaultLimit();
        int offset = config.getDefaultOffset();
        String finalQuery = paginationWrapper.replace("{}", query);

        if (criteria.getLimit() != null) {
            if (criteria.getLimit() <= config.getMaxLimit())
                limit = criteria.getLimit();
            else
                limit = config.getMaxLimit();
        }

        if (criteria.getOffset() != null)
            offset = criteria.getOffset();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(EstimateSearchCriteria criteria, List<Object> preparedStmtList) {
        log.info("EstimateQueryBuilder::getSearchCountQueryString");
        String query = getEstimateQuery(criteria, preparedStmtList);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
