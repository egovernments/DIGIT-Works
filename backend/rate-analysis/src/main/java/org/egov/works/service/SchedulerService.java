package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.JobScheduler;
import org.egov.works.web.models.JobSchedulerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    public List<JobScheduler> createScheduler(JobSchedulerRequest jobSchedulerRequest){
        return null;
    }
}
