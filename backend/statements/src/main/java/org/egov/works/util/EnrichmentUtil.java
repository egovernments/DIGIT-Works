package org.egov.works.util;

import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class EnrichmentUtil {

    public Statement getEnrichedStatement(StatementCreateRequest statementCreateRequest, String measurementNumber,
                                          String projectId) {

       return Statement.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(statementCreateRequest.getStatementRequest().getTenantId())
                .targetId(statementCreateRequest.getStatementRequest().getId())
                .statementType(Statement.StatementTypeEnum.UTILIZATION)
               .additionalDetails(getStatementAdditionalDetails(measurementNumber, projectId))
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

    public SorDetail getEnrichedSorDetail(String statementId, String tenantId, Sor sor, Rates rates) {
        return SorDetail.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .isActive(true)
                .statementId(statementId)
                .sorId(sor.getId())
                .additionalDetails(getAdditionalDetails(sor, rates))
                .build();
    }

    public BasicSor getEnrichedLineItem(Sor sor, List<BasicSorDetails> basicSorDetails, String referenceId, Rates rates) {

        return BasicSor.builder()
                .id(UUID.randomUUID().toString())
                .sorId(sor.getId())
                .sorType(sor.getSorType())
                .basicSorDetails(basicSorDetails)
                .referenceId(referenceId)
                .additionalDetails(getAdditionalDetails(sor, rates)).build();
    }

    private Object getAdditionalDetails(Sor sor, Rates rates) {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("sorDetails", sor);
        additionalDetails.put("rateDetails", rates);
        return additionalDetails;
    }
    private Object getStatementAdditionalDetails(String measurementNumber, String projectId) {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("measurementNumber", measurementNumber);
        additionalDetails.put("projectId", projectId);
        return additionalDetails;
    }

}
