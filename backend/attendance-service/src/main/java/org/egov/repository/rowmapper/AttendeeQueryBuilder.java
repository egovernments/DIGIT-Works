package org.egov.repository.rowmapper;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AttendeeQueryBuilder {

    private static final String FETCH_ATTENDANCE_REGISTER_QUERY = "SELECT register.*," +
            "attendee.id AS attendeeId,attendee.register_id AS attendeeRegId,attendee.individual_id AS attendeeIndividualId, attendee.enrollment_date AS attendeeEnroll, attendee.tenantid AS attendeeTenant " +
            "FROM eg_wms_attendance_register AS register " +
            "LEFT JOIN " +
            "eg_wms_attendance_attendee AS attendee " +
            "ON (register.id=attendee.register_id) " ;


    public String getAttendeeSearchQuery(AttendanceRegisterSearchCriteria searchCriteria, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_ATTENDANCE_REGISTER_QUERY);

        List<String> ids = searchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.tenantid=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if (StringUtils.isNotBlank(searchCriteria.getRegisterNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.registernumber=? ");
            preparedStmtList.add(searchCriteria.getRegisterNumber());
        }
        if (StringUtils.isNotBlank(searchCriteria.getName())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.name=? ");
            preparedStmtList.add(searchCriteria.getName());
        }

        if (searchCriteria.getStatus() != null && StringUtils.isNotBlank(searchCriteria.getStatus().toString())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.status=? ");
            preparedStmtList.add(searchCriteria.getStatus().toString());
        }

        if (searchCriteria.getFromDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.startdate=? ");
            preparedStmtList.add(searchCriteria.getFromDate());
        }

        if (searchCriteria.getToDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.enddate=? ");
            preparedStmtList.add(searchCriteria.getToDate());
        }

        //Get all the registers where the given Individual is registered.
        if (StringUtils.isNotBlank(searchCriteria.getAttendeeId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" attendee.individual_id=? ");
            preparedStmtList.add(searchCriteria.getAttendeeId());
        }

        queryBuilder.append(" ORDER BY register.name ASC ");

        return queryBuilder.toString();
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
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
}
