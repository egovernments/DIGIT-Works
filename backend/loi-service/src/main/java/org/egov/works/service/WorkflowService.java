package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

    @Autowired
    private LOIConfiguration serviceConfiguration;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;


    /*
     * Should return the applicable BusinessService for the given request
     *
     */


}
