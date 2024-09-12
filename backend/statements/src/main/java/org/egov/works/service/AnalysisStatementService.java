package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.repository.StatementRepository;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.util.StatementServiceUtil;
import org.egov.works.validator.StatementValidator;
import org.egov.works.web.models.Statement;
import org.egov.works.web.models.StatementCreateRequest;
import org.egov.works.web.models.StatementPushRequest;
import org.egov.works.web.models.StatementSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private StatementServiceUtil statementServiceUtil;

    public StatementPushRequest createAnalysisStatement(StatementCreateRequest statementCreateRequest, Estimate estimate) {
        log.info("AnalysisStatementService::createAnalysisStatement");
        statementValidator.validateStatementOnCreate(statementCreateRequest);
        StatementPushRequest statementPushRequest ;
        List<Statement> statementList = new ArrayList<>();
        Boolean isCreate = statementServiceUtil.checkIfCreateOperation(statementCreateRequest, statementList);
            if (isCreate) {
                statementPushRequest = enrichmentService.enrichStatementPushRequest(statementCreateRequest,estimate,Boolean.TRUE);
                producer.push(statementConfiguration.getSaveAnalysisStatementTopic(), statementPushRequest);
            } else {
                statementPushRequest = enrichmentService.enrichStatementPushRequestForUpdate(statementCreateRequest, statementList.get(0),estimate,Boolean.FALSE);
                producer.push(statementConfiguration.getUpdateAnalysisStatementTopic(), statementPushRequest);
            }
            if(statementPushRequest.getStatement()==null){
            log.error("Statement Push Request Not Created Successfully");
            throw new CustomException("STATEMENT_PUSH_REQUEST_ERROR", "Statement Push Request Not Created Successfully");
        }
            return statementPushRequest;

        }


        public List<Statement> searchStatement (StatementSearchCriteria statementSearchCriteria){
            statementValidator.validateStatementSearchCriteria(statementSearchCriteria);
            log.info("Get statement from db");
            List<Statement> statementList=statementRepository.getStatement(statementSearchCriteria.getSearchCriteria());
            return statementList;

        }

    }
