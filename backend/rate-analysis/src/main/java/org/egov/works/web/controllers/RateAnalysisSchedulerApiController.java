package org.egov.works.web.controllers;

import jakarta.validation.Valid;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.SchedulerService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.JobSchedulerRequest;
import org.egov.works.web.models.JobSchedulerResponse;
import org.egov.works.web.models.JobSchedulerSearchCriteria;
import org.egov.works.web.models.ScheduledJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/v1/")
public class RateAnalysisSchedulerApiController {

    private final SchedulerService schedulerService;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public RateAnalysisSchedulerApiController(SchedulerService schedulerService, ResponseInfoFactory responseInfoFactory) {
        this.schedulerService = schedulerService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @RequestMapping(value = "scheduler/_create", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerCreatePost(@Valid @RequestBody JobSchedulerRequest jobSchedulerRequest) {
        List<ScheduledJob> scheduledJobs = schedulerService.createScheduledJobs(jobSchedulerRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(jobSchedulerRequest.getRequestInfo(), true);
        JobSchedulerResponse jobSchedulerResponse = JobSchedulerResponse.builder().responseInfo(responseInfo).scheduledJobs(scheduledJobs).build();
        return new ResponseEntity<>(jobSchedulerResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "scheduler/_search", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerSearchPost(@Valid @RequestBody JobSchedulerSearchCriteria jobSchedulerSearchCriteria) {
        List<ScheduledJob> scheduledJobs = schedulerService.searchScheduledJobs(jobSchedulerSearchCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(jobSchedulerSearchCriteria.getRequestInfo(), true);
        JobSchedulerResponse jobSchedulerResponse = JobSchedulerResponse.builder().responseInfo(responseInfo).scheduledJobs(scheduledJobs).build();
        return new ResponseEntity<>(jobSchedulerResponse, HttpStatus.OK);
    }
}

