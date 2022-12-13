package org.egov.repository.querybuilder;

import org.egov.web.models.AttendanceStaffSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class AttendanceStaffQueryBuilder {

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
    public String getActiveAttendanceStaffSearchQuery(AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(getAttendanceStaffSearchQuery(criteria,preparedStmtList));
        addClauseIfRequired(query, preparedStmtList);
        query.append(" stf.deenrollment_date is null ");

        return query.toString();
    }

    public String getAttendanceStaffSearchQuery(AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(ATTENDANCE_STAFF_SELECT_QUERY);

        if(!ObjectUtils.isEmpty(criteria.getIndividualId())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" stf.individual_id = ? ");
            preparedStmtList.add(criteria.getIndividualId());
        }
        if(!ObjectUtils.isEmpty(criteria.getRegisterId())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" stf.register_id = ? ");
            preparedStmtList.add(criteria.getRegisterId());
        }
        return query.toString();
    }
    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList){
        if(preparedStmtList.isEmpty()){
            query.append(" WHERE ");
        }else{
            query.append(" AND ");
        }
    }

    private void addLimitAndOffset(StringBuilder query, AttendanceStaffSearchCriteria criteria, List<Object> preparedStmtList) {
        query.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        query.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }
}
