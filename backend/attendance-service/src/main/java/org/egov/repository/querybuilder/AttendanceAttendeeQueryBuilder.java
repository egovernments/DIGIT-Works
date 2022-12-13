package org.egov.repository.querybuilder;

import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceAttendeeSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
public class AttendanceAttendeeQueryBuilder {

    private static final String ATTENDANCE_ATTENDEE_SELECT_QUERY = " SELECT att.id, " +
            "att.individual_id, " +
            "att.register_id, " +
            "att.enrollment_date , " +
            "att.deenrollment_date, " +
            "att.additionaldetails, " +
            "att.createdby, " +
            "att.lastmodifiedby, " +
            "att.createdtime, " +
            "att.lastmodifiedtime " +
            "FROM eg_wms_attendance_attendee att ";

    public String getAttendanceAttendeeSearchQuery(AttendanceAttendeeSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(ATTENDANCE_ATTENDEE_SELECT_QUERY);

        List<String> ids = criteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" log.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (!ObjectUtils.isEmpty(criteria.getIndividualId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" att.individual_id = ? ");
            preparedStmtList.add(criteria.getIndividualId());
        }
        if (!ObjectUtils.isEmpty(criteria.getRegisterId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" att.register_id = ? ");
            preparedStmtList.add(criteria.getRegisterId());
        }

        if (criteria.getEnrollmentDate() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (criteria.getDenrollmentDate() == null) {
                criteria.setDenrollmentDate(new Double(Instant.now().toEpochMilli()));
            }

            query.append(" att.enrollment_date BETWEEN ? AND ?");
            preparedStmtList.add(criteria.getEnrollmentDate());
            preparedStmtList.add(criteria.getEnrollmentDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (criteria.getDenrollmentDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify getEnrollmentDate without a getEnrollmentDate");
            }
        }

        addLimitAndOffset(query, criteria, preparedStmtList);

        return query.toString();
    }

    private void addLimitAndOffset(StringBuilder query, AttendanceAttendeeSearchCriteria criteria, List<Object> preparedStmtList) {
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
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }
}
