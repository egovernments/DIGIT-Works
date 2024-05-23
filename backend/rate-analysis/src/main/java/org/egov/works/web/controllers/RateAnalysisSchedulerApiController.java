package org.egov.works.web.controllers;

import jakarta.validation.Valid;
import org.egov.works.web.models.JobSchedulerRequest;
import org.egov.works.web.models.JobSchedulerResponse;
import org.egov.works.web.models.JobSchedulerSearchCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RateAnalysisSchedulerApiController {
    @RequestMapping(value = "/rate-analysis/v1/scheduler/_create", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerCreatePost(@Valid @RequestBody JobSchedulerRequest body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/rate-analysis/v1/scheduler/_search", method = RequestMethod.POST)
    public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerSearchPost(@Valid @RequestBody JobSchedulerSearchCriteria body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
