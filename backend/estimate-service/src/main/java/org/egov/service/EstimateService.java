package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.web.models.Estimate;
import org.egov.web.models.EstimateRequest;
import org.egov.web.models.EstimateSearchCriteria;
import org.egov.web.models.RequestInfoWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EstimateService {
    
    
    public EstimateRequest createEstimate(EstimateRequest estimateRequest) {
        
        return estimateRequest;
    }

    public List<Estimate> searchEstimate(RequestInfoWrapper requestInfoWrapper, EstimateSearchCriteria searchCriteria) {

        return Collections.emptyList();
    }

    public Integer countAllEstimateApplications(EstimateSearchCriteria searchCriteria) {

        return 0;
    }

    public EstimateRequest updateEstimate(EstimateRequest estimateRequest) {

        return estimateRequest;
    }
}
