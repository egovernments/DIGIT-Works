package org.egov.works.enrichment;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.Configuration;
import org.egov.works.util.IdgenUtil;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class SchedulerEnrichment {
    private final IdgenUtil idgenUtil;
    private final Configuration configuration;

    @Autowired
    public SchedulerEnrichment(IdgenUtil idgenUtil, Configuration configuration) {
        this.idgenUtil = idgenUtil;
        this.configuration = configuration;
    }

    public List<ScheduledJob> enrichScheduledJobs(JobSchedulerRequest jobSchedulerRequest, Map<String, String> sorIdToSorCodeMap) {
        log.info("Enriching Scheduled Jobs");
        RequestInfo requestInfo = jobSchedulerRequest.getRequestInfo();
        JobScheduler jobScheduler = jobSchedulerRequest.getSchedule();
        // TODO: FIX this
        String tenantId = "pg.citya";
//        String tenantId = jobScheduler.getTenantId();
        AuditDetails auditDetails = getAuditDetails(requestInfo);
        List<String> jobNumbers = idgenUtil.getIdList(requestInfo, tenantId, configuration.getRateAnalysisJobNumberName(), null, 1);
        List<SorDetail> sorDetails = enrichSorDetails(jobScheduler, sorIdToSorCodeMap);

        ScheduledJob scheduledJob = ScheduledJob.builder()
                .id(UUID.randomUUID().toString())
                .jobId(jobNumbers.get(0))
                .rateEffectiveFrom(jobScheduler.getEffectiveFrom())
                .tenantId(tenantId)
                .noOfSorScheduled(jobScheduler.getSorIds().size())
                .status(StatusEnum.SCHEDULED)
                .sorDetails(sorDetails)
                .auditDetails(auditDetails)
                .build();
        return Collections.singletonList(scheduledJob);
    }

    private AuditDetails getAuditDetails(RequestInfo requestInfo) {
        return AuditDetails.builder()
                .createdBy(requestInfo.getUserInfo().getUuid())
                .createdTime(System.currentTimeMillis())
                .lastModifiedBy(requestInfo.getUserInfo().getUuid())
                .lastModifiedTime(System.currentTimeMillis())
                .build();
    }

    private List<SorDetail> enrichSorDetails(JobScheduler jobScheduler, Map<String, String> sorIdToSorCodeMap) {
        log.info("Enriching Sor Details");
        List<SorDetail> sorDetails = new ArrayList<>();
        for (String sorId : jobScheduler.getSorIds()) {
            SorDetail sorDetail;
            if (sorIdToSorCodeMap.containsKey(sorId)) {
                sorDetail = SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(sorId)
                        .sorCode(sorIdToSorCodeMap.get(sorId))
                        .status(StatusEnum.SCHEDULED)
                        .build();
            } else {
                sorDetail = SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(sorId)
                        .status(StatusEnum.FAILED)
                        .failureReason("SOR not found")
                        .build();
            }
            sorDetails.add(sorDetail);
        }
        return sorDetails;
    }

    public SorDetails getSorDetailsRequestForRateAnalysis(ScheduledJob scheduledJob) {
        return SorDetails.builder()
                .tenantId(scheduledJob.getTenantId())
                .effectiveFrom(scheduledJob.getRateEffectiveFrom())
                .build();
    }

    public void enrichScheduledJobsStatusAndEnrichAuditDetails(ScheduledJob scheduledJob) {
        for (SorDetail sorDetail : scheduledJob.getSorDetails()) {
            if (sorDetail.getStatus().equals(StatusEnum.FAILED)) {
                scheduledJob.setStatus(StatusEnum.FAILED);
                break;
            }
        }

        scheduledJob.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
    }
}
