package org.egov.works.util;

import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.web.models.BasicSorDetails;
import org.egov.works.web.models.SorDetail;
import org.egov.works.web.models.Statement;
import org.egov.works.web.models.StatementCreateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EnrichmentUtil {

    public Statement getEnrichedStatement(StatementCreateRequest statementCreateRequest) {

       return Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(statementCreateRequest.getStatementRequest().getTenantId())
                .targetId(statementCreateRequest.getStatementRequest().getId())
                .statementType(Statement.StatementTypeEnum.UTILIZATION)
                .auditDetails(getNewAuditDetails(statementCreateRequest.getRequestInfo()))
                .build();

    }

    private AuditDetails getNewAuditDetails(RequestInfo requestInfo) {
        return AuditDetails.builder()
                .createdBy(requestInfo.getUserInfo().getUuid())
                .lastModifiedBy(requestInfo.getUserInfo().getUuid())
                .createdTime(System.currentTimeMillis())
                .lastModifiedTime(System.currentTimeMillis())
                .build();
    }

    public BasicSorDetails getEnrichedBasicSorDetails(String type) {
        return BasicSorDetails.builder()
                .type(type)
                .build();
    }

    public SorDetail getEnrichedSorDetail() {
        return SorDetail.builder()
                .id(UUID.randomUUID().toString())
                .build();
    }

}
