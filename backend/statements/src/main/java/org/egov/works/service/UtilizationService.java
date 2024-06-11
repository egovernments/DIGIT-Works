package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.validator.StatementValidator;
import org.egov.works.web.models.Statement;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtilizationService {

    private final StatementValidator statementValidator;

    @Autowired
    public UtilizationService(StatementValidator statementValidator) {
        this.statementValidator = statementValidator;
    }

    public void utilizationMethod(StatementCreateRequest statementCreateRequest) {
        log.info("UtilizationService::utilizationService");
        statementValidator.validateTenantId(statementCreateRequest.getStatementRequest(), statementCreateRequest.getRequestInfo());


    }

}
