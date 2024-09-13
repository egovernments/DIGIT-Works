package org.egov.wms.repository.builder;

import lombok.extern.slf4j.Slf4j;
import org.egov.wms.web.model.Job.ReportSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class ReportQueryBuilder {
    private static final String REPORT_JOB_QUERY = "SELECT id AS reportId, tenant_id AS reportTenantId, " +
            "report_number AS reportNumber, report_name AS reportName, " +
            "status AS reportStatus, request_payload AS reportRequestPayload, " +
            "additional_details AS reportAdditionalDetails, " +
            "file_store_id AS reportFileStoreId," +
            " created_by AS reportCreatedBy, " +
            "last_modified_by AS reportLastModifiedBy, " +
            "created_time AS reportCreatedTime, " +
            "last_modified_time AS reportLastModifiedTime " +
            "FROM job_report";

    public String getReportJobQuery(ReportSearchCriteria reportSearchCriteria, List<Object> preparedStmtList) {
        StringBuilder query = new StringBuilder(REPORT_JOB_QUERY);
        if(reportSearchCriteria.getId() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" id = ?");
            preparedStmtList.add(reportSearchCriteria.getId());
        }

        if (reportSearchCriteria.getTenantId() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" tenant_id = ?");
            preparedStmtList.add(reportSearchCriteria.getTenantId());
        }

        if (reportSearchCriteria.getReportNumber() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" report_number = ?");
            preparedStmtList.add(reportSearchCriteria.getReportNumber());
        }

        if(reportSearchCriteria.getStatus() != null) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" status = ?");
            preparedStmtList.add(reportSearchCriteria.getStatus());
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

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        preparedStmtList.addAll(ids);
    }
}
