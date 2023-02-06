package org.egov.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.web.models.MusterRollSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MusterRollQueryBuilder {

    private static final String FETCH_MUSTER_ROLL_QUERY = "SELECT muster.id,muster.tenant_id,muster.musterroll_number,muster.attendance_register_id,muster.status,muster.musterroll_status,muster.start_date,muster.end_date,muster.createdby,muster.lastmodifiedby,muster.createdtime,muster.lastmodifiedtime,muster.additionaldetails,"+
            "ind.id AS summaryId,ind.muster_roll_id AS indMusterId,ind.individual_id AS IndividualId,ind.total_attendance AS totalAttendance,ind.additionaldetails AS indAddlDetails,ind.createdby AS indCreatedBy,ind.lastmodifiedby AS indModifiedBy,ind.createdtime AS indCreatedTime,ind.lastmodifiedtime AS indModifiedTime,"+
            "attn.id AS attendanceId,attn.attendance_summary_id AS attnSummaryId,attn.date_of_attendance AS AttnDate,attn.attendance_value AS attendance,attn.additionaldetails AS attnAddlDetails,attn.createdby AS attnCreatedBy,attn.lastmodifiedby AS attnModifiedBy,attn.createdtime AS attnCreatedTime,attn.lastmodifiedtime AS attnModifiedTime "+
            "FROM eg_wms_muster_roll AS muster " +
            "LEFT JOIN " +
            "eg_wms_attendance_summary AS ind " +
            "ON (muster.id=ind.muster_roll_id) " +
            "LEFT JOIN " +
            "eg_wms_attendance_entries AS attn " +
            "ON (attn.attendance_summary_id=ind.id) ";


    public String getMusterSearchQuery(MusterRollSearchCriteria searchCriteria, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = new StringBuilder(FETCH_MUSTER_ROLL_QUERY);

        List<String> ids = searchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.tenant_id LIKE ? ");
            preparedStmtList.add(searchCriteria.getTenantId()+"%");
        }

        if (StringUtils.isNotBlank(searchCriteria.getMusterRollNumber())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.musterroll_number=? ");
            preparedStmtList.add(searchCriteria.getMusterRollNumber());
        }

        if (StringUtils.isNotBlank(searchCriteria.getRegisterId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.attendance_register_id=? ");
            preparedStmtList.add(searchCriteria.getRegisterId());
        }

        if (searchCriteria.getFromDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.start_date=? ");
            preparedStmtList.add(searchCriteria.getFromDate());
        }

        if (searchCriteria.getToDate() != null) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.end_date=? ");
            preparedStmtList.add(searchCriteria.getToDate());
        }

        if (searchCriteria.getStatus() != null && StringUtils.isNotBlank(searchCriteria.getStatus().toString())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.status=? ");
            preparedStmtList.add(searchCriteria.getStatus().toString());
        }

        if (StringUtils.isNotBlank(searchCriteria.getMusterRollStatus())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" muster.musterroll_status=? ");
            preparedStmtList.add(searchCriteria.getMusterRollStatus());
        }

        addLimitAndOffset(queryBuilder, searchCriteria, preparedStmtList);

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

    private void addLimitAndOffset(StringBuilder queryBuilder, MusterRollSearchCriteria criteria, List<Object> preparedStmtList) {
        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        queryBuilder.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }

}
