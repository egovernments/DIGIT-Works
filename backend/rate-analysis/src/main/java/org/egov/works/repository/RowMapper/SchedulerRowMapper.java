package org.egov.works.repository.RowMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.works.web.models.AuditDetails;
import org.egov.works.web.models.ScheduledJob;
import org.egov.works.web.models.SorDetail;
import org.egov.works.web.models.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.egov.works.config.ServiceConstants.RATE_ANALYSIS_SCHEDULE_DETAILS_ID;

@Component
public class SchedulerRowMapper implements ResultSetExtractor<List<ScheduledJob>> {
    private final ObjectMapper objectMapper;

    @Autowired
    public SchedulerRowMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ScheduledJob> extractData(ResultSet rs) throws SQLException, DataAccessException{
        Map<String, ScheduledJob> scheduledJobMap = new LinkedHashMap<>();
        Set<String> sorDetailsIds = new HashSet<>();

        while (rs.next()){
            String uuid = rs.getString("rateAnalysisScheduleId");
            ScheduledJob scheduledJob = scheduledJobMap.get(uuid);

            if(scheduledJob == null){
                scheduledJob = new ScheduledJob();
                scheduledJob.setId(uuid);
                scheduledJob.setTenantId(rs.getString("rateAnalysisScheduleTenantId"));
                scheduledJob.setJobId(rs.getString("rateAnalysisScheduleJobId"));
                scheduledJob.setRateEffectiveFrom(rs.getLong("rateAnalysisScheduleRateEffectiveFrom"));
                scheduledJob.setStatus(StatusEnum.valueOf(rs.getString("rateAnalysisScheduleJobStatus")));

                AuditDetails auditDetails = new AuditDetails();
                auditDetails.setCreatedBy(rs.getString("rateAnalysisScheduleCreatedBy"));
                auditDetails.setCreatedTime(rs.getLong("rateAnalysisScheduleCreatedTime"));
                auditDetails.setLastModifiedBy(rs.getString("rateAnalysisScheduleLastModifiedBy"));
                auditDetails.setLastModifiedTime(rs.getLong("rateAnalysisScheduleLastModifiedTime"));
                scheduledJob.setAuditDetails(auditDetails);

                scheduledJob.setSorDetails(new ArrayList<>());
                scheduledJobMap.put(uuid, scheduledJob);
            }

            // Create SorDetail object
            SorDetail sorDetail = new SorDetail();
            if(!sorDetailsIds.contains(rs.getString(RATE_ANALYSIS_SCHEDULE_DETAILS_ID))){
                sorDetailsIds.add(rs.getString(RATE_ANALYSIS_SCHEDULE_DETAILS_ID));
                sorDetail.setId(rs.getString(RATE_ANALYSIS_SCHEDULE_DETAILS_ID));
                sorDetail.setSorId(rs.getString("rateAnalysisScheduleDetailsSorId"));
                sorDetail.setSorCode(rs.getString("rateAnalysisScheduleDetailsSorCode"));
                sorDetail.setStatus(StatusEnum.valueOf(rs.getString("rateAnalysisScheduleDetailsStatus")));
                sorDetail.setFailureReason(rs.getString("rateAnalysisScheduleDetailsFailuerReason"));
                String additionalDetailsString = rs.getString("rateAnalysisScheduleDetailsAdditionalDetails");
                JsonNode additionalDetailsJson = null;
                try {
                    additionalDetailsJson = objectMapper.readTree(additionalDetailsString);
                } catch (IOException e) {
                    // Handle the JSON parsing error
                    e.printStackTrace();
                }
                sorDetail.setAdditionalDetails(additionalDetailsJson);
                scheduledJob.getSorDetails().add(sorDetail);
            }
            scheduledJob.setNoOfSorScheduled(scheduledJob.getSorDetails().size());
        }
        return new ArrayList<>(scheduledJobMap.values());
    }
}
