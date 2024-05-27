package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.Configuration;
import org.egov.works.enrichment.SchedulerEnrichment;
import org.egov.works.kafka.RateAnalysisProducer;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.MdmsUtil;
import org.egov.works.validators.SchedulerValidator;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.egov.works.config.ServiceConstants.*;


@Service
@Slf4j
public class SchedulerService {
    private final SchedulerValidator schedulerValidator;
    private final MdmsUtil mdmsUtil;
    private final Configuration configuration;
    private final SchedulerEnrichment schedulerEnrichment;
    private final RateAnalysisProducer rateAnalysisProducer;
    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public SchedulerService(SchedulerValidator schedulerValidator, MdmsUtil mdmsUtil, Configuration configuration, SchedulerEnrichment schedulerEnrichment, RateAnalysisProducer rateAnalysisProducer, ServiceRequestRepository serviceRequestRepository) {
        this.schedulerValidator = schedulerValidator;
        this.mdmsUtil = mdmsUtil;
        this.configuration = configuration;
        this.schedulerEnrichment = schedulerEnrichment;
        this.rateAnalysisProducer = rateAnalysisProducer;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<ScheduledJob> createScheduledJobs(JobSchedulerRequest jobSchedulerRequest) {
        log.info("Creating Scheduled Jobs");
        schedulerValidator.validateSchedulerRequest(jobSchedulerRequest);
        Map<String, String> sorIdToSorCodeMap = getSorIdToSorCodeMapFromMdms(jobSchedulerRequest);
        List<ScheduledJob> scheduledJobs = schedulerEnrichment.enrichScheduledJobs(jobSchedulerRequest, sorIdToSorCodeMap);
        JobScheduledRequest jobScheduledRequest = JobScheduledRequest.builder()
                .requestInfo(jobSchedulerRequest.getRequestInfo())
                .scheduledJobs(scheduledJobs.get(0))
                .build();
        rateAnalysisProducer.push(configuration.getRateAnalysisJobCreateTopic(), jobScheduledRequest);
        return scheduledJobs;
    }

    private Map<String, String> getSorIdToSorCodeMapFromMdms(JobSchedulerRequest jobSchedulerRequest) {
        log.info("Fetching SORs from MDMS");
        Map<String, String> sorIdToSorCodeMap = new HashMap<>();
        List<Mdms> mdmsList = getSorsForSorIds(jobSchedulerRequest);
        for (Mdms mdms : mdmsList) {
            String id = mdms.getId();
            JsonNode data = mdms.getData();
            String sorCode = data.get(SOR_CODE_JSON_KEY).asText();
            String sorType = data.get(SOR_TYPE_JSON_KEY).asText();
            if (sorType.equals(configuration.getWorksSorType())) {
                sorIdToSorCodeMap.put(id, sorCode);
            }
        }
        return sorIdToSorCodeMap;
    }

    private List<Mdms> getSorsForSorIds(JobSchedulerRequest jobSchedulerRequest) {
        int limit = 20;
        int offset = 0;
        List<Mdms> mdmsList = new ArrayList<>();
        while (true) {
            MdmsCriteriaV2 mdmsCriteria = MdmsCriteriaV2.builder()
                    .tenantId(jobSchedulerRequest.getSchedule().getTenantId())
                    .schemaCode(WORKS_SOR_SCHEMA_CODE)
                    .offset(offset)
                    .limit(limit)
                    .build();
            if (jobSchedulerRequest.getSchedule().getSorIds() != null && !jobSchedulerRequest.getSchedule().getSorIds().isEmpty()) {
                mdmsCriteria.setIds(jobSchedulerRequest.getSchedule().getSorIds());
            }
            MdmsSearchCriteriaV2 mdmsSearchCriteria = MdmsSearchCriteriaV2.builder()
                    .requestInfo(jobSchedulerRequest.getRequestInfo())
                    .mdmsCriteria(mdmsCriteria)
                    .build();
            MdmsResponseV2 mdmsResponse = mdmsUtil.fetchSorsFromMdms(mdmsSearchCriteria);
            if (mdmsResponse.getMdms() != null && !mdmsResponse.getMdms().isEmpty()) {
                mdmsList.addAll(mdmsResponse.getMdms());
                offset += limit;
            } else {
                break;
            }
        }
        return mdmsList;
    }

    public void createScheduledJobsFromConsumer(JobScheduledRequest jobScheduledRequest) {
        log.info("Creating Scheduled Jobs from Consumer");
        jobScheduledRequest.getScheduledJobs().setStatus(StatusEnum.IN_PROGRESS);
        RequestInfo requestInfo = jobScheduledRequest.getRequestInfo();
        ScheduledJob scheduledJob = jobScheduledRequest.getScheduledJobs();
        SorDetails sorDetails = schedulerEnrichment.getSorDetailsRequestForRateAnalysis(scheduledJob);

        log.info("Pushing Rate Analysis Job for processing");
        for (SorDetail sorDetail : scheduledJob.getSorDetails()) {
            if (sorDetail.getStatus().equals(StatusEnum.SCHEDULED)) {
                sorDetail.setStatus(StatusEnum.IN_PROGRESS);
                try {
                    callRateAnalysisCreate(sorDetail, requestInfo, sorDetails);
                    sorDetail.setStatus(StatusEnum.SUCCESSFUL);
                } catch (Exception e) {
                    log.error("Error while processing Rate Analysis Job for SOR: " + sorDetail.getSorId());
                    log.error("Error: ", e);
                    sorDetail.setStatus(StatusEnum.FAILED);
                    sorDetail.setFailureReason(e.getMessage());
                }
            }
        }
        schedulerEnrichment.enrichScheduledJobsStatusAndEnrichAuditDetails(scheduledJob);
        rateAnalysisProducer.push(configuration.getRateAnalysisJobUpdateTopic(), jobScheduledRequest);
    }

    private void callRateAnalysisCreate(SorDetail sorDetail, RequestInfo requestInfo, SorDetails sorDetails) {
        log.info("Calling Rate Analysis Create for SOR: " + sorDetail.getSorId());
        sorDetails.setSorId(Collections.singletonList(sorDetail.getSorId()));
        AnalysisRequest analysisRequest = AnalysisRequest.builder()
                .requestInfo(requestInfo)
                .sorDetails(sorDetails)
                .build();

        //TODO: Call rate analysis create
    }

    public List<ScheduledJob> searchScheduledJobs(JobSchedulerSearchCriteria jobSchedulerSearchCriteria) {
        log.info("Searching Scheduled Jobs");
        return serviceRequestRepository.getScheduledJobs(jobSchedulerSearchCriteria);
    }
}
