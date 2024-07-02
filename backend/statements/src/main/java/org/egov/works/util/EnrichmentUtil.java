package org.egov.works.util;

import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.web.models.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    public SorDetail getEnrichedSorDetail(String statementId, String tenantId, Sor sor, Rates rates,
                                          BigDecimal consumedQuantity, BigDecimal consumedAmount) {
        return SorDetail.builder()
                .id(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .isActive(true)
                .statementId(statementId)
                .sorId(sor.getId())
                .additionalDetails(getAdditionalDetails(sor, rates, consumedQuantity, consumedAmount))
                .build();
    }

    public BasicSor getEnrichedLineItem(Sor sor, List<BasicSorDetails> basicSorDetails, String referenceId, Rates rates) {

        return BasicSor.builder()
                .id(UUID.randomUUID().toString())
                .sorId(sor.getId())
                .sorType(sor.getSorType())
                .basicSorDetails(basicSorDetails)
                .referenceId(referenceId)
                .additionalDetails(getAdditionalDetails(sor, rates, null, null)).build();
    }

    private Object getAdditionalDetails(Sor sor, Rates rates, BigDecimal consumedQuantity, BigDecimal consumedAmount) {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("sorDetails", sor);
        additionalDetails.put("rateDetails", rates);
        if (consumedQuantity != null) {
            consumedAmount.setScale(4, BigDecimal.ROUND_HALF_UP);
            additionalDetails.put("consumedQuantity", consumedQuantity);
            additionalDetails.put("consumedAmount", consumedAmount);
        }
        return additionalDetails;
    }
    private Object getStatementAdditionalDetails(String measurementNumber, String projectId) {
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("measurementNumber", measurementNumber);
        additionalDetails.put("projectId", projectId);
        return additionalDetails;
    }

    public StatementSearchCriteria getSearchRequest(StatementCreateRequest statementCreateRequest) {
        return StatementSearchCriteria.builder()
                .requestInfo(statementCreateRequest.getRequestInfo())
                .searchCriteria(SearchCriteria.builder()
                        .referenceId(statementCreateRequest.getStatementRequest().getId())
                        .tenantId(statementCreateRequest.getStatementRequest().getTenantId())
                        .statementType(Statement.StatementTypeEnum.UTILIZATION)
                        .build())
                .build();
    }

    public StatementCreateRequest getStatementCreateRequest(RequestInfo requestInfo, String id, String tenantId) {
        StatementRequest statementRequest = StatementRequest.builder()
                .id(id)
                .tenantId(tenantId)
                .build();
        return StatementCreateRequest.builder()
                .requestInfo(requestInfo)
                .statementRequest(statementRequest)
                .build();
    }

    public StatementCreateRequest getAnalysisStatementCreateRequest(RequestInfo requestInfo, String id, String tenantId) {
        StatementRequest statementRequest = StatementRequest.builder()
                .id(id)
                .tenantId(tenantId)
                .build();
        return StatementCreateRequest.builder()
                .requestInfo(requestInfo)
                .statementRequest(statementRequest)
                .build();
    }

}
