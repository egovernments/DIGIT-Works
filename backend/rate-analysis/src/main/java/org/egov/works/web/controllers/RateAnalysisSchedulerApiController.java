package org.egov.works.web.controllers;

import jakarta.validation.Valid;
import org.egov.works.service.SchedulerService;
import org.egov.works.web.models.JobScheduler;
import org.egov.works.web.models.JobSchedulerRequest;
import org.egov.works.web.models.JobSchedulerResponse;
import org.egov.works.web.models.JobSchedulerSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/rate-analysis/v1/")
public class RateAnalysisSchedulerApiController {

    private final SchedulerService schedulerService;

    @Autowired
    public RateAnalysisSchedulerApiController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
    @RequestMapping(value = "scheduler/_create", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerCreatePost(@Valid @RequestBody JobSchedulerRequest jobSchedulerRequest) {
        List<JobScheduler> jobSchedulers = schedulerService.createScheduler(jobSchedulerRequest);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "scheduler/_search", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerSearchPost(@Valid @RequestBody JobSchedulerSearchCriteria body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
