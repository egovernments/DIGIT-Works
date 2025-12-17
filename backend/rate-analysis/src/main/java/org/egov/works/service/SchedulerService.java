package org.egov.works.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.config.Configuration;
import org.egov.works.enrichment.SchedulerEnrichment;
import org.egov.works.kafka.RateAnalysisProducer;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.util.MdmsUtil;
import org.egov.works.validator.SchedulerValidator;
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
    private final RateAnalysisService rateAnalysisService;

    @Autowired
    public SchedulerService(SchedulerValidator schedulerValidator, MdmsUtil mdmsUtil, Configuration configuration, SchedulerEnrichment schedulerEnrichment, RateAnalysisProducer rateAnalysisProducer, ServiceRequestRepository serviceRequestRepository, RateAnalysisService rateAnalysisService) {
        this.schedulerValidator = schedulerValidator;
        this.mdmsUtil = mdmsUtil;
        this.configuration = configuration;
        this.schedulerEnrichment = schedulerEnrichment;
        this.rateAnalysisProducer = rateAnalysisProducer;
        this.serviceRequestRepository = serviceRequestRepository;
        this.rateAnalysisService = rateAnalysisService;
    }

    /**
     * Creates Scheduled Jobs
     *
     * @param jobSchedulerRequest The JobSchedulerRequest
     * @return List of Scheduled Jobs
     */
    public List<ScheduledJob> createScheduledJobs(JobSchedulerRequest jobSchedulerRequest) {
        log.info("Creating Scheduled Jobs");
        schedulerValidator.validateSchedulerRequest(jobSchedulerRequest);
        // Fetch SORs from MDMS
        Map<String, String> sorIdToSorCodeMap = getSorIdToSorCodeMapFromMdms(jobSchedulerRequest);
        List<ScheduledJob> scheduledJobs = schedulerEnrichment.enrichScheduledJobs(jobSchedulerRequest, sorIdToSorCodeMap);
        JobScheduledRequest jobScheduledRequest = JobScheduledRequest.builder()
                .requestInfo(jobSchedulerRequest.getRequestInfo())
                .scheduledJobs(scheduledJobs.get(0))
                .build();
        rateAnalysisProducer.push(configuration.getRateAnalysisJobCreateTopic(), jobScheduledRequest);
        return scheduledJobs;
    }

    /**
     *
     * @param jobSchedulerRequest
     * @return Map of SOR Id to SOR Code
     */
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

    /**
     * @param jobSchedulerRequest
     * @return List of SORs
     */
    private List<Mdms> getSorsForSorIds(JobSchedulerRequest jobSchedulerRequest) {
        log.info("SchedulerService: getSorsForSorIds");
        int limit = configuration.getDefaultLimit();
        int offset = configuration.getDefaultOffset();
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
    /**
     * Creates Scheduled Jobs from Consumer
     *
     * @param jobScheduledRequest The JobScheduledRequest
     */
    public void createScheduledJobsFromConsumer(JobScheduledRequest jobScheduledRequest) {
        log.info("SchedulerService: createScheduledJobsFromConsumer");
        jobScheduledRequest.getScheduledJobs().setStatus(StatusEnum.IN_PROGRESS);
        rateAnalysisProducer.push(configuration.getRateAnalysisJobUpdateTopic(), jobScheduledRequest);
        RequestInfo requestInfo = jobScheduledRequest.getRequestInfo();
        ScheduledJob scheduledJob = jobScheduledRequest.getScheduledJobs();

        log.info("Pushing Rate Analysis Job for processing");
        for (SorDetail sorDetail : scheduledJob.getSorDetails()) {
            if (sorDetail.getStatus().equals(StatusEnum.SCHEDULED)) {
                sorDetail.setStatus(StatusEnum.IN_PROGRESS);
                //Calling Rate Analysis Create, in case of error it will mark the status as FAILED
                try {
                    callRateAnalysisCreate(sorDetail, requestInfo, scheduledJob.getRateEffectiveFrom(), scheduledJob.getTenantId());
                    sorDetail.setStatus(StatusEnum.SUCCESSFUL);
                } catch (Exception e) {
                    log.error("Error while processing Rate Analysis Job for SOR: {}", sorDetail.getSorId());
                    log.error("Error: ", e);
                    sorDetail.setStatus(StatusEnum.FAILED);
                    sorDetail.setFailureReason(e.getMessage());
                }
            }
        }
        schedulerEnrichment.enrichScheduledJobsStatusAndEnrichAuditDetails(scheduledJob);
        log.info("Pushing Rate Analysis Job for update");
        rateAnalysisProducer.push(configuration.getRateAnalysisJobUpdateTopic(), jobScheduledRequest);
    }

    /**
     * Calls Rate Analysis Create
     *
     * @param sorDetail          The SorDetail
     * @param requestInfo        The RequestInfo
     * @param rateEffectiveFrom  The Rate Effective From
     */
    private void callRateAnalysisCreate(SorDetail sorDetail, RequestInfo requestInfo, Long rateEffectiveFrom, String tenantId) {
        log.info("SchedulerService: callRateAnalysisCreate");
        SorDetails sorDetails = SorDetails.builder()
                .tenantId(tenantId)
                .sorId(Collections.singletonList(sorDetail.getSorId()))
                .sorCodes(Collections.singletonList(sorDetail.getSorCode()))
                .effectiveFrom(String.valueOf(rateEffectiveFrom))
                .build();
        AnalysisRequest analysisRequest = AnalysisRequest.builder()
                .requestInfo(requestInfo)
                .sorDetails(sorDetails)
                .build();

        log.info("Creating Rate Analysis");
        rateAnalysisService.createRateAnalysis(analysisRequest);
    }

    /**
     * Searches Scheduled Jobs
     *
     * @param jobSchedulerSearchCriteria The JobSchedulerSearchCriteria
     * @return List of Scheduled Jobs
     */
    public List<ScheduledJob> searchScheduledJobs(JobSchedulerSearchCriteria jobSchedulerSearchCriteria) {
        log.info("SchedulerService: searchScheduledJobs");
        return serviceRequestRepository.getScheduledJobs(jobSchedulerSearchCriteria);
    }
}