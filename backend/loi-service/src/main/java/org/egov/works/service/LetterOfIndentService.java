package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.repository.LOIRepository;
import org.egov.works.validator.LOIServiceValidator;
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
    private LOIConfiguration serviceConfiguration;

    @Autowired
    private Producer producer;

    @Autowired
    private LOIServiceValidator serviceValidator;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private LOIRepository loiRepository;

    @Autowired
    private WorkflowService workflowService;

    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request) {
        return request;
    }

    public List<LetterOfIndent> searchLOI(LOISearchCriteria searchCriteria) {
        serviceValidator.validateSearchLOI(searchCriteria);
        enrichmentService.enrichSearchLOI(searchCriteria);
        List<LetterOfIndent> loiList = loiRepository.getEstimate(searchCriteria);
        return loiList;

    }


}
