package org.egov.works.util;


import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.repository.StatementRepository;
import org.egov.works.web.models.SearchCriteria;
import org.egov.works.web.models.Statement;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class StatementServiceUtil {


    private final StatementConfiguration statementConfiguration;
    private final StatementRepository statementRepository;

    public StatementServiceUtil(StatementConfiguration statementConfiguration, StatementRepository statementRepository) {
        this.statementConfiguration = statementConfiguration;
        this.statementRepository = statementRepository;
    }

    public AuditDetails getAuditDetails(String by , Statement statement, Boolean isCreate){

        Long time = System.currentTimeMillis();
        if (Boolean.TRUE.equals(isCreate))
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(statement.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
                    .createdTime(statement.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();

    }

    public Boolean checkIfCreateOperation(StatementCreateRequest statementCreateRequest,List<Statement> statementList){
        String tenantId= statementCreateRequest.getStatementRequest().getTenantId();
        String referenceId= statementCreateRequest.getStatementRequest().getId();
        SearchCriteria searchCriteria =SearchCriteria.builder()
                .referenceId(referenceId)
                .tenantId(tenantId)
                .build();
        statementList.addAll(statementRepository.getStatement(searchCriteria));

        return statementList.isEmpty();



    }

}
