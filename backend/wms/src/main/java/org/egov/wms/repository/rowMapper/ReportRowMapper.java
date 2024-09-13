package org.egov.wms.repository.rowMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.tracer.model.CustomException;
import org.egov.wms.web.model.Job.JobStatus;
import org.egov.wms.web.model.Job.ReportJob;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ReportRowMapper implements ResultSetExtractor<List<ReportJob>> {

    private final ObjectMapper mapper;

    @Autowired
    public ReportRowMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ReportJob> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, ReportJob> reportJobMap = new LinkedHashMap<>();

        while (rs.next()){
            String reportJobId = rs.getString("reportId");

            ReportJob reportJob = reportJobMap.get(reportJobId);
            if(reportJob == null){
                reportJob = ReportJob.builder()
                        .id(rs.getString("reportId"))
                        .tenantId(rs.getString("reportTenantId"))
                        .reportNumber(rs.getString("reportNumber"))
                        .reportName(rs.getString("reportName"))
                        .status(JobStatus.fromValue(rs.getString("reportStatus")))
                        .fileStoreId(rs.getString("reportFileStoreId"))
                        .build();
                JsonNode requestPayload = getAdditionalDetail("reportRequestPayload", rs);
                reportJob.setRequestPayload(requestPayload);
                JsonNode additionalDetails = getAdditionalDetail("reportAdditionalDetails", rs);
                reportJob.setAdditionalDetails(additionalDetails);
                AuditDetails auditDetails = AuditDetails.builder().createdTime(rs.getLong("reportCreatedTime"))
                        .lastModifiedTime(rs.getLong("reportLastModifiedTime"))
                        .createdBy(rs.getString("reportCreatedBy"))
                        .lastModifiedBy(rs.getString("reportLastModifiedBy"))
                        .build();
                reportJob.setAuditDetails(auditDetails);
                reportJobMap.put(reportJobId, reportJob);
            }
        }
        return new ArrayList<>(reportJobMap.values());
    }

    private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
        JsonNode additionalDetails = null;
        try {
            PGobject obj = (PGobject) rs.getObject(columnName);
            if (obj != null) {
                additionalDetails = mapper.readTree(obj.getValue());
            }
        } catch (IOException e) {
            log.error("Failed to parse additionalDetail object");
            throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
        }
        if (additionalDetails.isEmpty()) {
            additionalDetails = null;
        }
        return additionalDetails;
    }
}
