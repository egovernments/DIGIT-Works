package org.egov.repository.querybuilder;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceLogSearchCriteria;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@RequiredArgsConstructor
@Component
public class AttendanceLogQueryBuilder {

    // Constructor-injected dependency for resolving tenant-specific schema names
    private final MultiStateInstanceUtil multiStateInstanceUtil;

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
            "FROM %s.eg_wms_attendance_log AS log " +
            "LEFT JOIN " +
            "%s.eg_wms_attendance_document AS doc " +
            "ON (log.id=doc.attendance_log_id) ";


    /*
     * This method constructs a SQL query to fetch attendance logs based on the provided search criteria.
     *
     * @param tenantId The tenant ID for which the logs are being fetched.
     * @param criteria The search criteria containing filters for the logs.
     * @param preparedStmtList A list to hold the prepared statement parameters.
     * @return A SQL query string with placeholders for parameters.
     * @throws InvalidTenantIdException If the tenant ID is invalid.
     */
    public String getAttendanceLogSearchQuery( String tenantId, AttendanceLogSearchCriteria criteria, List<Object> preparedStmtList) throws InvalidTenantIdException {
        StringBuilder query = new StringBuilder(ATTENDANCE_LOG_SELECT_QUERY);
        // Replace static schema tokens with runtime schema for the given tenant ID
        query = new StringBuilder(String.format(String.valueOf(query), SCHEMA_REPLACE_STRING, SCHEMA_REPLACE_STRING));
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

        addOrderByClause(query, criteria);

        addLimitAndOffset(query, criteria, preparedStmtList);

        // After building full query, replace schema placeholders with actual schema using MultiStateInstanceUtil
        return multiStateInstanceUtil.replaceSchemaPlaceholder(String.valueOf(query), tenantId);
        
    }

    private void addOrderByClause(StringBuilder queryBuilder, AttendanceLogSearchCriteria criteria) {

        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
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
}
