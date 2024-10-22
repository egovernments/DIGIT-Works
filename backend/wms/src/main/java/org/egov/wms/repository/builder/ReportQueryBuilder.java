package org.egov.wms.repository.builder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.web.model.Job.ReportSearchCriteria;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.egov.works.services.common.models.expense.Pagination;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class ReportQueryBuilder {
    private static final String REPORT_JOB_QUERY = "SELECT id AS reportId, tenant_id AS reportTenantId, " +
            "report_number AS reportNumber, report_name AS reportName, " +
            "no_of_projects AS noOfProjects, " +
            "status AS reportStatus, request_payload AS reportRequestPayload, " +
            "additional_details AS reportAdditionalDetails, " +
            "file_store_id AS reportFileStoreId," +
            " created_by AS reportCreatedBy, " +
            "last_modified_by AS reportLastModifiedBy, " +
            "created_time AS reportCreatedTime, " +
            "last_modified_time AS reportLastModifiedTime " +
            "FROM job_report";

    private static final String PAGINATION_WRAPPER = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY report{sortBy} {orderBy}) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";

    private static final String REPORT_JOB_COUNT_QUERY = "SELECT distinct(id) " +
            "FROM job_report";
    private final SearchConfiguration searchConfiguration;

    public ReportQueryBuilder(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    public String getReportJobQuery(ReportSearchRequest reportSearchRequest, List<Object> preparedStmtList, boolean isCountNeeded) {
        log.info("EstimateQueryBuilder::getEstimateQuery");
        ReportSearchCriteria reportSearchCriteria = reportSearchRequest.getReportSearchCriteria();
        StringBuilder query = null;
        if (isCountNeeded) {
            query = new StringBuilder(REPORT_JOB_COUNT_QUERY);
        } else {
            query = new StringBuilder(REPORT_JOB_QUERY);
        }
        if(reportSearchCriteria.getId() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" id = ?");
            preparedStmtList.add(reportSearchCriteria.getId());
        }

        if (reportSearchCriteria.getTenantId() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" tenant_id = ?");
            preparedStmtList.add(reportSearchCriteria.getTenantId());
        }

        if (reportSearchCriteria.getReportNumber() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" report_number = ?");
            preparedStmtList.add(reportSearchCriteria.getReportNumber());
        }

        if(reportSearchCriteria.getStatus() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" status = ?");
            preparedStmtList.add(reportSearchCriteria.getStatus().toString());
        }
        if(reportSearchCriteria.getScheduledFrom() != null){
            addClauseIfRequired(query, preparedStmtList);

            if(reportSearchCriteria.getScheduledTo() == null){
                reportSearchCriteria.setScheduledTo(Instant.now().toEpochMilli());
            }

            query.append(" created_time BETWEEN ? AND ?");
            preparedStmtList.add(reportSearchCriteria.getScheduledFrom());
            preparedStmtList.add(reportSearchCriteria.getScheduledTo());
        }
        if(Boolean.FALSE.equals(isCountNeeded)){
            return addPaginationWrapper(query, reportSearchRequest.getPagination(), preparedStmtList);
        }
        return query.toString();
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

    public String addPaginationWrapper(StringBuilder query, Pagination pagination, List<Object> preparedStmtList) {
        String paginatedQuery = addOrderByClause(pagination);

        int limit = pagination != null && pagination.getLimit() != null ? pagination.getLimit() : searchConfiguration.getReportDefaultLimit();
        int offset = pagination != null && pagination.getOffSet() != null? pagination.getOffSet() : searchConfiguration.getReportDefaultOffset();

        String finalQuery = paginatedQuery.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }


    private String addOrderByClause(Pagination pagination) {

        String paginationWrapper = PAGINATION_WRAPPER;
        if(pagination == null)
            return paginationWrapper.replace("{sortBy}", "CreatedTime").replace("{orderBy}", Pagination.OrderEnum.DESC.name());

        if ( pagination.getSortBy() != null && StringUtils.isNotBlank(pagination.getSortBy())) {
            paginationWrapper=paginationWrapper.replace("{sortBy}", pagination.getSortBy());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{sortBy}", "CreatedTime");
        }

        if (pagination.getOrder() != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Pagination.OrderEnum.DESC.name());
        }

        return paginationWrapper;
    }

    public String getSearchCountQueryString(ReportSearchRequest reportSearchRequest, List<Object> preparedStmtList) {
        log.info("EstimateQueryBuilder::getSearchCountQueryString");
        String query = getReportJobQuery(reportSearchRequest, preparedStmtList,true);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
