package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.EstimateRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstimateService {


    public EstimateRequest createEstimate(EstimateRequest request) {
        return request;
    }
}
