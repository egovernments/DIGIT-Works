package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.validator.LOIValidator;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request) {
        loiValidator.validateCreateLOI(request);
        enrichmentService.enrichLOI(request);
        // TODO: Workflow status update
        producer.push(loiConfiguration.getLoiSaveTopic(), request);
        return request;
    }

}
