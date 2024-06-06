package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.validator.StatementValidator;
import org.egov.works.web.models.StatementCreateRequest;
import org.egov.works.web.models.StatementPushRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalysisStatementService {

    @Autowired
    private StatementValidator statementValidator;
    @Autowired
    private EnrichmentService enrichmentService;
    @Autowired
    private Producer producer;
    @Autowired
    private StatementConfiguration statementConfiguration;

public StatementPushRequest createAnalysisStatement(StatementCreateRequest statementCreateRequest){
    log.info("AnalysisStatementService::createAnalysisStatement");
    statementValidator.validateStatementOnCreate(statementCreateRequest);
    StatementPushRequest statementPushRequest= enrichmentService.enrichStatementPushRequest(statementCreateRequest);
    if(statementPushRequest!=null){
        producer.push(statementConfiguration.getSaveAnalysisStatementTopic(),statementPushRequest);

    }
    return statementPushRequest;

}

/*public List<StatementResponse> searchStatement(StatementSearchCriteria statementSearchCriteria){


}*/

}
