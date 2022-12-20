package org.egov.repository.querybuilder;

import org.egov.models.AttendanceStaffSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

@Component
public class StaffQueryBuilder {

    private static final String ATTENDANCE_STAFF_SELECT_QUERY = " SELECT stf.id, " +
            "stf.individual_id, " +
            "stf.register_id, " +
            "stf.enrollment_date , " +
            "stf.deenrollment_date, " +
            "stf.additionaldetails, " +
            "stf.createdby, " +
            "stf.lastmodifiedby, " +
            "stf.createdtime, " +
            "stf.lastmodifiedtime " +
            "FROM eg_wms_attendance_staff stf ";

    public String getActiveAttendanceStaffSearchQuery(AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(getAttendanceStaffSearchQuery(criteria, preparedStmtList));
        addClauseIfRequired(query, preparedStmtList);
        query.append(" stf.deenrollment_date is null ");

        return query.toString();
    }

    public String getAttendanceStaffSearchQuery(AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(ATTENDANCE_STAFF_SELECT_QUERY);

        if (!ObjectUtils.isEmpty(criteria.getIndividualIds())) {
            List<String> staffUserIds = criteria.getIndividualIds();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" stf.individual_id IN (").append(createQuery(staffUserIds)).append(")");
            preparedStmtList.addAll(criteria.getIndividualIds());
        }
        if (!ObjectUtils.isEmpty(criteria.getRegisterIds())) {
            List<String> registerIds = criteria.getRegisterIds();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" stf.register_id IN (").append(createQuery(registerIds)).append(")");
            preparedStmtList.addAll(criteria.getRegisterIds());
        }
        return query.toString();
    }

    public String getAttendanceStaffFromRegistersSearchQuery(AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(ATTENDANCE_STAFF_SELECT_QUERY);

        if (!ObjectUtils.isEmpty(criteria.getRegisterIds())) {
            List<String> registerIds = criteria.getRegisterIds();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" stf.register_id IN (").append(createQuery(registerIds)).append(")");
            preparedStmtList.addAll(criteria.getRegisterIds());
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

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }


    private void addLimitAndOffset(StringBuilder query, AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList) {
        query.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        query.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }
}
