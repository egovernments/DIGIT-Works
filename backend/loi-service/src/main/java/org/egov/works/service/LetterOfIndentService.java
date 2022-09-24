package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.repository.LetterOfIndentRepository;
import org.egov.works.validator.LOIValidator;
import org.egov.works.web.models.LOISearchCriteria;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LetterOfIndentService {

    @Autowired
    private LOIConfiguration loiConfiguration;
    @Autowired
    private Producer producer;
    @Autowired
    private LOIValidator loiValidator;
    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private LetterOfIndentRepository loiRepository;

    @Autowired
    private WorkflowService workflowService;

    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request) {
        loiValidator.validateCreateLOI(request);
        enrichmentService.enrichLOI(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(loiConfiguration.getLoiSaveTopic(), request);
        return request;
    }

    public LetterOfIndentRequest updateLOI(LetterOfIndentRequest request) {
        loiValidator.validateUpdateLOI(request);
        enrichmentService.enrichLOI(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(loiConfiguration.getLoiUpdateTopic(), request);
        return request;
    }

    public List<LetterOfIndent> searchLOI(LOISearchCriteria searchCriteria) {
        loiValidator.validateSearchLOI(searchCriteria);
        enrichmentService.enrichSearchLOI(searchCriteria);
        List<LetterOfIndent> loiList = loiRepository.getEstimate(searchCriteria);
        return loiList;

    }

}
