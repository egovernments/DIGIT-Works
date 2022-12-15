package org.egov.repository.querybuilder;

import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
@Component
public class RegisterQueryBuilder {

    private static final String ATTENDANCE_REGISTER_SELECT_QUERY = " SELECT reg.id, " +
            "reg.tenantid, " +
            "reg.registernumber, " +
            "reg.name , " +
            "reg.startdate, " +
            "reg.enddate, " +
            "reg.status, " +
            "reg.additionaldetails, " +
            "reg.createdby, " +
            "reg.lastmodifiedby, " +
            "reg.createdtime, " +
            "reg.lastmodifiedtime " +
            "FROM eg_wms_attendance_register reg ";
    public String getAttendanceRegisterSearchQuery(AttendanceRegisterSearchCriteria searchCriteria, List<Object> preparedStmtList) {

        StringBuilder query = new StringBuilder(ATTENDANCE_REGISTER_SELECT_QUERY);

        if(!ObjectUtils.isEmpty(searchCriteria.getTenantId())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.tenantid = ? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        if(!ObjectUtils.isEmpty(searchCriteria.getIds())){
            List<String> registerIds=searchCriteria.getIds();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.id IN (").append(createQuery(registerIds)).append(")");
            preparedStmtList.add(registerIds);
        }

        return query.toString();
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

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList){
        if(preparedStmtList.isEmpty()){
            query.append(" WHERE ");
        }else{
            query.append(" AND ");
        }
    }
}
