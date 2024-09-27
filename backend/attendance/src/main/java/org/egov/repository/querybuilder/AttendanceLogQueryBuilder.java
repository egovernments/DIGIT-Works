package org.egov.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
public class AttendanceLogQueryBuilder {

    private final AttendanceServiceConfiguration config;

    private static final String ATTENDANCE_LOG_SELECT_QUERY = " SELECT log.id as logid, " +
            "log.individual_id as logIndividualId, " +
            "log.clientreferenceid as logClientReferenceId, " +
            "log.tenantId as logTenantId, " +
            "log.register_id as logRegisterId, " +
            "log.status as logStatus, " +
            "log.time as logTime, " +
            "log.event_type as logEventType, " +
            "log.additionaldetails as logAdditionalDetails, " +
            "log.createdby as logCreatedBy, " +
            "log.lastmodifiedby as logLastModifiedBy, " +
            "log.createdtime as logCreatedTime, " +
            "log.lastmodifiedtime as logLastModifiedTime, " +
            "log.clientcreatedby as logClientCreatedBy, " +
            "log.clientlastmodifiedby as logClientLastModifiedBy, " +
            "log.clientcreatedtime as logClientCreatedTime, " +
            "log.clientlastmodifiedtime as logClientLastModifiedTime, " +
            "doc.id as docId, " +
            "doc.filestore_id as docFileStoreId, " +
            "doc.document_type as docDocumentType, " +
            "doc.attendance_log_id as docAttendanceLogId, " +
            "doc.tenantid as docTenantId, " +
            "doc.status as docStatus, " +
            "doc.additionaldetails as docAdditionalDetails, " +
            "doc.createdby as docCreatedBy, " +
            "doc.lastmodifiedby as docLastModifiedBy, " +
            "doc.createdtime as docCreatedTime, " +
            "doc.lastmodifiedtime as docLastModifiedTime " +
            "FROM eg_wms_attendance_log AS log " +
            "LEFT JOIN " +
            "eg_wms_attendance_document AS doc " +
            "ON (log.id=doc.attendance_log_id) ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY logLastModifiedTime DESC , logid) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String ATTENDANCE_LOG_COUNT_QUERY = "SELECT distinct(log.id) "+
            "FROM eg_wms_attendance_log AS log " +
            "LEFT JOIN " +
            "eg_wms_attendance_document AS doc " +
            "ON (log.id=doc.attendance_log_id) ";

    private static final String COUNT_WRAPPER = " SELECT COUNT(*) FROM ({INTERNAL_QUERY}) AS count ";

    @Autowired
    public AttendanceLogQueryBuilder(AttendanceServiceConfiguration config) {
        this.config = config;
    }


    public String getAttendanceLogSearchQuery(AttendanceLogSearchCriteria criteria, List<Object> preparedStmtList, boolean isCount) {
        StringBuilder query = new StringBuilder(ATTENDANCE_LOG_SELECT_QUERY);

        if(isCount) {
            query = new StringBuilder(ATTENDANCE_LOG_COUNT_QUERY);
        }

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        List<String> clientReferenceIds = criteria.getClientReferenceId();
        if (clientReferenceIds != null && !clientReferenceIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.clientreferenceid IN (").append(createQuery(clientReferenceIds)).append(")");
            addToPreparedStatement(preparedStmtList, clientReferenceIds);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.tenantid=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        if (StringUtils.isNotBlank(criteria.getRegisterId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.register_id=? ");
            preparedStmtList.add(criteria.getRegisterId());
        }

        if (criteria.getFromTime() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (criteria.getToTime() == null) {
                criteria.setToTime(BigDecimal.valueOf(Instant.now().toEpochMilli()));
            }

            query.append(" log.time BETWEEN ? AND ?");
            preparedStmtList.add(criteria.getFromTime());
            preparedStmtList.add(criteria.getToTime());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (criteria.getToTime() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify getToTime without a getFromTime");
            }
        }

        List<String> individualIds = criteria.getIndividualIds();
        if (individualIds != null && !individualIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.individual_id IN (").append(createQuery(individualIds)).append(")");
            addToPreparedStatement(preparedStmtList, individualIds);
        }

        if (criteria.getStatus() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.status=? ");
            preparedStmtList.add(criteria.getStatus().toString());
        }

        if (!isCount && isPaginationRequired(criteria)) {
            addOrderByClause(query, criteria);
            return addPaginationWrapper(query.toString(), preparedStmtList, criteria);
        }


        return query.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, AttendanceLogSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY log.lastmodifiedtime ");
        }else{
            queryBuilder.append(" ORDER BY log.lastmodifiedtime ");
        }

        if (criteria.getSortOrder() == AttendanceLogSearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, AttendanceLogSearchCriteria criteria, List<Object> preparedStmtList) {
        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        queryBuilder.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

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
//        ids.forEach(id -> {
//            preparedStmtList.add(id);
//        });

        preparedStmtList.addAll(ids);
    }

    private String addPaginationWrapper(String query,List<Object> preparedStmtList,
                                        AttendanceLogSearchCriteria criteria){
        int limit = config.getAttendanceLogDefaultLimit();
        int offset = config.getAttendanceLogDefaultOffset();

        String finalQuery = paginationWrapper.replace("{}",query);

        if(criteria.getLimit()!=null && criteria.getLimit()<=config.getAttendanceLogMaxLimit())
            limit = criteria.getLimit();

        if(criteria.getLimit()!=null && criteria.getLimit()>config.getAttendanceLogMaxLimit())
            limit = config.getAttendanceLogMaxLimit();

        if(criteria.getOffset()!=null)
            offset = criteria.getOffset();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit+offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(AttendanceLogSearchCriteria criteria, List<Object> preparedStmtList) {
        String query = getAttendanceLogSearchQuery(criteria, preparedStmtList,true);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }

    private Boolean isPaginationRequired(AttendanceLogSearchCriteria criteria) {
        if((criteria.getIds() == null || criteria.getIds().isEmpty())
                && (criteria.getClientReferenceId() == null || criteria.getClientReferenceId().isEmpty())
                && StringUtils.isEmpty(criteria.getRegisterId())
                && (criteria.getIndividualIds() == null || criteria.getIndividualIds().isEmpty())
                && criteria.getFromTime() == null
                && criteria.getToTime() == null
                && criteria.getStatus() == null) {
            return true;
        }
        return false;
    }
}
