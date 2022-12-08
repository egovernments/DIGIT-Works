package org.egov.repository.rowmapper;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class StaffQueryBuilder {

    private static final String FETCH_ATTENDANCE_REGISTER_QUERY = "SELECT register.*," +
            "staff.id AS staffId,staff.individual_id AS staffIndId,staff.register_id AS staffRegId,staff.tenantid AS staffTenant,staff.enrollment_date AS staffEnroll,staff.deenrollment_date AS staffDeenroll "+
            "FROM eg_wms_attendance_register AS register " +
            "LEFT JOIN " +
            "eg_wms_attendance_staff AS staff " +
            "ON (register.id=staff.register_id) ";


    public String getStaffSearchQuery(AttendanceRegisterSearchCriteria searchCriteria, List<Object> preparedStmtList, List<String> registerIds) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_ATTENDANCE_REGISTER_QUERY);

        if (StringUtils.isNotBlank(searchCriteria.getId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.id=? ");
            preparedStmtList.add(searchCriteria.getId());
        //if the search was based on attendeeId, fetch the staffs for those registers where the attendeeId is registered
        } else if (registerIds != null && !registerIds.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" register.id IN (").append(createQuery(registerIds)).append(")");
            addToPreparedStatement(preparedStmtList, registerIds);
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
