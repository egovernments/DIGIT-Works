package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.kafka.Producer;
import org.egov.works.repository.StatementRepository;
import org.egov.works.services.common.models.contract.Contract;
import org.egov.works.services.common.models.estimate.Estimate;
import org.egov.works.services.common.models.measurement.Measurement;
import org.egov.works.util.EnrichmentUtil;
import org.egov.works.validator.StatementValidator;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UtilizationService {

    private final StatementValidator statementValidator;
    private final FetcherService fetcherService;
    private final UtilizationEnrichmentService utilizationEnrichmentService;
    private final Producer producer;
    private final StatementConfiguration statementConfiguration;
    private final StatementRepository statementRepository;
    private final EnrichmentUtil enrichmentUtil;

    @Autowired
    public UtilizationService(StatementValidator statementValidator, FetcherService fetcherService, UtilizationEnrichmentService utilizationEnrichmentService, Producer producer, StatementConfiguration statementConfiguration, StatementRepository statementRepository, EnrichmentUtil enrichmentUtil) {
        this.statementValidator = statementValidator;
        this.fetcherService = fetcherService;
        this.utilizationEnrichmentService = utilizationEnrichmentService;
        this.producer = producer;
        this.statementConfiguration = statementConfiguration;
        this.statementRepository = statementRepository;
        this.enrichmentUtil = enrichmentUtil;
    }

    public Statement utilizationCreate(StatementCreateRequest statementCreateRequest, Measurement measurement) {
        log.info("UtilizationService::utilizationService");
        statementValidator.validateTenantId(statementCreateRequest.getStatementRequest(), statementCreateRequest.getRequestInfo());
        String tenantId = statementCreateRequest.getStatementRequest().getTenantId();
        if (measurement == null)
            measurement = fetcherService.fetchAndValidateMeasurements(statementCreateRequest
                    .getStatementRequest().getId(), tenantId, statementCreateRequest.getRequestInfo());
        Contract contract = fetcherService.fetchAndValidateContracts(measurement.getReferenceId(),
                tenantId, statementCreateRequest.getRequestInfo());
        Estimate estimate = fetcherService.fetchAndValidateEstimates(contract.getLineItems().get(0).getEstimateId(),
                tenantId, statementCreateRequest.getRequestInfo(), Statement.StatementTypeEnum.UTILIZATION.toString());
        Statement statement = utilizationEnrichmentService.createUtilizationStatement(statementCreateRequest,
                measurement, contract, estimate);

        List<Statement> previousStatements = searchStatement(enrichmentUtil.getSearchRequest(statementCreateRequest));
        StatementPushRequest pushRequest = StatementPushRequest.builder().requestInfo(statementCreateRequest.getRequestInfo())
                .statement(statement).build();
        if (previousStatements.isEmpty()) {
            producer.push(statementConfiguration.getSaveAnalysisStatementTopic(), pushRequest);
        }
        else {
            utilizationEnrichmentService.enrichPreviousIds(previousStatements.get(0), statement);
            producer.push(statementConfiguration.getUpdateAnalysisStatementTopic(), pushRequest);
        }
        return statement;
    }


    public List<Statement> searchStatement(StatementSearchCriteria statementSearchCriteria){
//        statementValidator.validateStatementSearchCriteria(statementSearchCriteria);
        log.info("get statement from db");
        return statementRepository.getStatement(statementSearchCriteria.getSearchCriteria());

    }

}
