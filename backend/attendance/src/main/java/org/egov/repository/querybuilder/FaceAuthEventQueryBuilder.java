package org.egov.repository.querybuilder;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.FaceAuthEventSearchCriteria;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@RequiredArgsConstructor
@Component
public class FaceAuthEventQueryBuilder {

    private final MultiStateInstanceUtil multiStateInstanceUtil;

    private static final String FACE_AUTH_EVENT_SELECT_QUERY = " SELECT fae.id as faeId, " +
            "fae.clientreferenceid as faeClientReferenceId, " +
            "fae.tenantid as faeTenantId, " +
            "fae.individual_id as faeIndividualId, " +
            "fae.device_id as faeDeviceId, " +
            "fae.event_type as faeEventType, " +
            "fae.outcome as faeOutcome, " +
            "fae.confidence as faeConfidence, " +
            "fae.latitude as faeLatitude, " +
            "fae.longitude as faeLongitude, " +
            "fae.location_accuracy as faeLocationAccuracy, " +
            "fae.event_timestamp as faeTimestamp, " +
            "fae.failed_attempt_count as faeFailedAttemptCount, " +
            "fae.popup_time as faePopupTime, " +
            "fae.response_time as faeResponseTime, " +
            "fae.response_type as faeResponseType, " +
            "fae.face_image as faeFaceImage, " +
            "fae.anomaly_flags as faeAnomalyFlags, " +
            "fae.project_id as faeProjectId, " +
            "fae.boundary_code as faeBoundaryCode, " +
            "fae.additionaldetails as faeAdditionalDetails, " +
            "fae.createdby as faeCreatedBy, " +
            "fae.lastmodifiedby as faeLastModifiedBy, " +
            "fae.createdtime as faeCreatedTime, " +
            "fae.lastmodifiedtime as faeLastModifiedTime, " +
            "fae.clientcreatedby as faeClientCreatedBy, " +
            "fae.clientlastmodifiedby as faeClientLastModifiedBy, " +
            "fae.clientcreatedtime as faeClientCreatedTime, " +
            "fae.clientlastmodifiedtime as faeClientLastModifiedTime " +
            "FROM %s.eg_wms_face_auth_event AS fae ";

    public String getFaceAuthEventSearchQuery(String tenantId, FaceAuthEventSearchCriteria criteria, List<Object> preparedStmtList) throws InvalidTenantIdException {
        StringBuilder query = new StringBuilder(String.format(FACE_AUTH_EVENT_SELECT_QUERY, SCHEMA_REPLACE_STRING));

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        List<String> clientReferenceIds = criteria.getClientReferenceId();
        if (clientReferenceIds != null && !clientReferenceIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.clientreferenceid IN (").append(createQuery(clientReferenceIds)).append(")");
            addToPreparedStatement(preparedStmtList, clientReferenceIds);
        }

        if (StringUtils.isNotBlank(criteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.tenantid=? ");
            preparedStmtList.add(criteria.getTenantId());
        }

        List<String> individualIds = criteria.getIndividualIds();
        if (individualIds != null && !individualIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.individual_id IN (").append(createQuery(individualIds)).append(")");
            addToPreparedStatement(preparedStmtList, individualIds);
        }

        if (StringUtils.isNotBlank(criteria.getEventType())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.event_type=? ");
            preparedStmtList.add(criteria.getEventType());
        }

        if (StringUtils.isNotBlank(criteria.getOutcome())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.outcome=? ");
            preparedStmtList.add(criteria.getOutcome());
        }

        if (StringUtils.isNotBlank(criteria.getProjectId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" fae.project_id=? ");
            preparedStmtList.add(criteria.getProjectId());
        }

        if (criteria.getFromTime() != null) {
            addClauseIfRequired(query, preparedStmtList);
            if (criteria.getToTime() == null) {
                criteria.setToTime(BigDecimal.valueOf(Instant.now().toEpochMilli()));
            }
            query.append(" fae.event_timestamp BETWEEN ? AND ?");
            preparedStmtList.add(criteria.getFromTime());
            preparedStmtList.add(criteria.getToTime());
        } else {
            if (criteria.getToTime() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toTime without a fromTime");
            }
        }

        addOrderByClause(query, criteria);
        addLimitAndOffset(query, criteria, preparedStmtList);

        return multiStateInstanceUtil.replaceSchemaPlaceholder(query.toString(), tenantId);
    }

    private void addOrderByClause(StringBuilder queryBuilder, FaceAuthEventSearchCriteria criteria) {
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY fae.lastmodifiedtime ");
        } else if (criteria.getSortBy() == FaceAuthEventSearchCriteria.SortBy.timestamp) {
            queryBuilder.append(" ORDER BY fae.event_timestamp ");
        } else {
            queryBuilder.append(" ORDER BY fae.lastmodifiedtime ");
        }

        if (criteria.getSortOrder() == FaceAuthEventSearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, FaceAuthEventSearchCriteria criteria, List<Object> preparedStmtList) {
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
        preparedStmtList.addAll(ids);
    }
}
