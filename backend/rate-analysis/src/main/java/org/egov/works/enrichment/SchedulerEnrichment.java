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

    /**
     * Enriches Scheduled Jobs
     *
     * @param jobSchedulerRequest The JobSchedulerRequest
     * @param sorIdToSorCodeMap   The Map of SOR Id to SOR Code
     * @return List of Scheduled Jobs
     */
    public List<ScheduledJob> enrichScheduledJobs(JobSchedulerRequest jobSchedulerRequest, Map<String, String> sorIdToSorCodeMap) {
        log.info("SchedulerEnrichment:enrichScheduledJobs");
        RequestInfo requestInfo = jobSchedulerRequest.getRequestInfo();
        JobScheduler jobScheduler = jobSchedulerRequest.getSchedule();
        String tenantId = jobScheduler.getTenantId();
        AuditDetails auditDetails = getAuditDetails(requestInfo);
        List<String> jobNumbers = idgenUtil.getIdList(requestInfo, tenantId, configuration.getRateAnalysisJobNumberName(), null, 1);
        List<SorDetail> sorDetails = enrichSorDetails(jobScheduler, sorIdToSorCodeMap);

        ScheduledJob scheduledJob = ScheduledJob.builder()
                .id(UUID.randomUUID().toString())
                .jobId(jobNumbers.get(0))
                .rateEffectiveFrom(jobScheduler.getEffectiveFrom())
                .tenantId(tenantId)
                .noOfSorScheduled(sorDetails.size())
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

    /**
     * Enriches SOR Details
     *
     * @param jobScheduler       The JobScheduler
     * @param sorIdToSorCodeMap The Map of SOR Id to SOR Code
     * @return List of SOR Details
     */
    private List<SorDetail> enrichSorDetails(JobScheduler jobScheduler, Map<String, String> sorIdToSorCodeMap) {
        log.info("SchedulerEnrichment:enrichSorDetails");
        List<SorDetail> sorDetails = new ArrayList<>();
        if(jobScheduler.getSorIds() != null) {
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
        }else{
            for(Map.Entry<String, String> entry: sorIdToSorCodeMap.entrySet()){
                SorDetail sorDetail = SorDetail.builder()
                        .id(UUID.randomUUID().toString())
                        .sorId(entry.getKey())
                        .sorCode(sorIdToSorCodeMap.get(entry.getKey()))
                        .status(StatusEnum.SCHEDULED)
                        .build();
                sorDetails.add(sorDetail);
            }
        }
        return sorDetails;
    }

    /**
     * Enriches Scheduled Jobs Status and Enriches Audit Details
     *
     * @param scheduledJob The ScheduledJob
     */
    public void enrichScheduledJobsStatusAndEnrichAuditDetails(ScheduledJob scheduledJob) {
        log.info("SchedulerEnrichment:enrichScheduledJobsStatusAndEnrichAuditDetails");
//        for (SorDetail sorDetail : scheduledJob.getSorDetails()) {
//            if (sorDetail.getStatus().equals(StatusEnum.FAILED)) {
//                scheduledJob.setStatus(StatusEnum.FAILED);
//                break;
//            }
//        }
        scheduledJob.setStatus(StatusEnum.COMPLETED);
        scheduledJob.getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
    }
}
