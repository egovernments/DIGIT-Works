package org.egov.works.measurement.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.works.measurement.config.MBRegistryConfiguration;
import org.egov.works.measurement.web.models.MeasurementCriteria;
import org.egov.works.measurement.web.models.MeasurementSearchRequest;
import org.egov.works.measurement.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class MeasurementQueryBuilder {

    @Autowired
    private MBRegistryConfiguration config;


    private static final String BASE_MEASUREMENT_QUERY = "SELECT m.id as id, m.tenantId as tenantId, m.mbNumber as mbNumber,m.referenceId as referenceId, m.phyRefNumber as phyRefNumber,  " +
            "m.entryDate as entryDate, m.isActive as isActive, m.createdby as createdby, m.lastmodifiedby as lastmodifiedby, " +
            "m.createdtime as createdtime, m.lastmodifiedtime as lastmodifiedtime, m.additionalDetails as additionalDetails, " +

            "md.id as mdid, md.targetId as targetId, md.isActive as mdisActive, md.description as mddescription, md.referenceId as mdreferenceId , md.additionalDetails as mdadditionalDetails, " +
            "md.createdby as mdcreatedby, md.lastmodifiedby as mdlastmodifiedby, md.createdtime as mdcreatedtime, md.lastmodifiedtime as mdlastmodifiedtime, " +

            "mm.id as mmid, mm.length as mmlength, mm.breadth as mmbreadth, mm.height as mmheight, mm.numOfItems as mmnumOfItems, mm.currentValue as mmcurrentValue, mm.cumulative as mmcumulativeValue, " +
            "mm.createdby as mmcreatedby, mm.lastmodifiedby as mmlastmodifiedby, mm.createdtime as mmcreatedtime, mm.lastmodifiedtime as mmlastmodifiedtime, " +

            "dc.filestore as filestore, dc.documentType as documentType, dc.documentuuid as documentuuid, dc.additionaldetails as dcadditionaldetails, dc.id as dcid " +

            "FROM eg_mb_measurements m " +

            "INNER JOIN eg_mb_measurement_details md ON m.id = md.referenceId " +
            "INNER JOIN eg_mb_measurement_measures mm ON md.id = mm.id "+
            "LEFT JOIN eg_mb_measurement_documents dc ON m.id = dc.referenceId ";


    private static String WRAPPER_QUERY = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY {sortBy} {orderBy}) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String MEASUREMENT_COUNT_QUERY = "SELECT distinct(m.id) " +
            "FROM eg_mb_measurements m ";

    private static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";


    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }

    public String getMeasurementSearchQuery(MeasurementCriteria criteria, List<Object> preparedStmtList, MeasurementSearchRequest measurementSearchRequest, boolean isCountNeeded) {
        StringBuilder query = null;

        if(isCountNeeded) {
            query = new StringBuilder(MEASUREMENT_COUNT_QUERY);
        } else {
            query = new StringBuilder(BASE_MEASUREMENT_QUERY);
        }

        boolean tenantIdProvided = !ObjectUtils.isEmpty(criteria.getTenantId());

        if (tenantIdProvided) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.tenantId = ? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (!CollectionUtils.isEmpty(criteria.getIds())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.id IN (").append(createQuery(criteria.getIds())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getIds());
        }

        if (!CollectionUtils.isEmpty(criteria.getReferenceId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.referenceId IN (").append(createQuery(criteria.getReferenceId())).append(")");
            addToPreparedStatement(preparedStmtList, criteria.getReferenceId());
        }

        if (!ObjectUtils.isEmpty(criteria.getMeasurementNumber())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.mbNumber = ? ");
            preparedStmtList.add(criteria.getMeasurementNumber());
        }
        if (!ObjectUtils.isEmpty(criteria.getFromDate())) {
            if (criteria.getToDate() == null || ObjectUtils.isEmpty(criteria.getToDate())) {
                criteria.setToDate(System.currentTimeMillis());
            }
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.createdtime BETWEEN ? AND ? ");
            preparedStmtList.add(criteria.getFromDate());
            preparedStmtList.add(criteria.getToDate());
        }
        if (!ObjectUtils.isEmpty(criteria.getIsActive())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" m.isActive = ? ");
            preparedStmtList.add(criteria.getIsActive());
        }

        return isCountNeeded? query.toString(): addPaginationWrapper(query, measurementSearchRequest.getPagination(), preparedStmtList);
    }


    public String addPaginationWrapper(StringBuilder query, Pagination pagination, List<Object> preparedStmtList) {
        String paginatedQuery = addOrderByClause(pagination);

        int limit = null != pagination.getLimit() ? pagination.getLimit() : config.getDefaultLimit();
        int offset = null != pagination.getOffSet() ? pagination.getOffSet() : config.getDefaultOffset();

        String finalQuery = paginatedQuery.replace("{}", query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }


    private String addOrderByClause(Pagination pagination) {

        String paginationWrapper = WRAPPER_QUERY;

        // TODO: Add possible fields on which we can sort
        if ( !StringUtils.isEmpty(pagination.getSortBy())) {
            paginationWrapper=paginationWrapper.replace("{sortBy}", pagination.getSortBy());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{sortBy}", "createdtime");
        }

        if (pagination.getOrder() != null && Pagination.OrderEnum.fromValue(pagination.getOrder().toString()) != null) {
            paginationWrapper=paginationWrapper.replace("{orderBy}", pagination.getOrder().name());
        }
        else{
            paginationWrapper=paginationWrapper.replace("{orderBy}", Pagination.OrderEnum.DESC.name());
        }

        return paginationWrapper;
    }

    private String createQuery(List<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ?");
            if (i != length - 1)
                builder.append(",");
        }
        return builder.toString();
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, List<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }

    public String getSearchCountQueryString(MeasurementCriteria criteria, List<Object> preparedStmtList, MeasurementSearchRequest measurementSearchRequest) {
        log.info("EstimateQueryBuilder::getSearchCountQueryString");
        String query = getMeasurementSearchQuery(criteria, preparedStmtList, measurementSearchRequest, true);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}