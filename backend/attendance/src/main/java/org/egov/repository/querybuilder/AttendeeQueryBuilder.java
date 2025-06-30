package org.egov.repository.querybuilder;

import lombok.RequiredArgsConstructor;
import org.egov.common.exception.InvalidTenantIdException;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendeeSearchCriteria;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.egov.common.utils.MultiStateInstanceUtil.SCHEMA_REPLACE_STRING;

@RequiredArgsConstructor
@Component
public class AttendeeQueryBuilder {

    private final MultiStateInstanceUtil multiStateInstanceUtil;

    private static final String ATTENDANCE_ATTENDEE_SELECT_QUERY = " SELECT att.id, " +
            "att.individual_id, " +
            "att.register_id, " +
            "att.enrollment_date , " +
            "att.deenrollment_date, " +
            "att.additionaldetails, " +
            "att.createdby, " +
            "att.lastmodifiedby, " +
            "att.createdtime, " +
            "att.lastmodifiedtime, " +
            "att.tenantid " +
            "FROM %s.eg_wms_attendance_attendee att ";

    public String getAttendanceAttendeeSearchQuery(String tenantId, AttendeeSearchCriteria criteria, List<Object> preparedStmtList) throws InvalidTenantIdException {
        StringBuilder query = new StringBuilder(String.format(ATTENDANCE_ATTENDEE_SELECT_QUERY, SCHEMA_REPLACE_STRING));
        List<String> ids=criteria.getIds();
        if (ids!=null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" att.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        List<String> individualIds=criteria.getIndividualIds();
        if (individualIds!=null && !individualIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" att.individual_id IN (").append(createQuery(individualIds)).append(")");
            addToPreparedStatement(preparedStmtList, individualIds);
        }

        List<String> registerIds = criteria.getRegisterIds();
        if (registerIds != null && !registerIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" att.register_id IN (").append(createQuery(registerIds)).append(")");
            addToPreparedStatement(preparedStmtList, registerIds);
        }

        if (criteria.getEnrollmentDate() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (criteria.getDenrollmentDate() == null) {
                criteria.setDenrollmentDate(BigDecimal.valueOf(Instant.now().toEpochMilli()));
            }

            query.append(" att.enrollment_date BETWEEN ? AND ?");
            preparedStmtList.add(criteria.getEnrollmentDate());
            preparedStmtList.add(criteria.getDenrollmentDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (criteria.getDenrollmentDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify denrollmentDate without a enrollmentDate");
            }
        }
        // After building full query, replace schema placeholders with actual schema using MultiStateInstanceUtil
        return multiStateInstanceUtil.replaceSchemaPlaceholder(query.toString(), tenantId);
    }
    private void addLimitAndOffset(StringBuilder query, AttendeeSearchCriteria criteria, List<Object> preparedStmtList) {
        query.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        query.append(" LIMIT ? ");
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
