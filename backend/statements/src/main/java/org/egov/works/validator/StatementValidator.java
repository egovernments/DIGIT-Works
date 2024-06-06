package org.egov.works.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.StatementConfiguration;
import org.egov.works.util.EstimateUtil;
import org.egov.works.util.MdmsUtil;
import org.egov.works.web.models.StatementCreateRequest;
import org.egov.works.web.models.StatementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.egov.works.config.ErrorConfiguration.*;
import static org.egov.works.config.ServiceConstants.MDMS_TENANTS_MASTER_NAME;
import static org.egov.works.config.ServiceConstants.MDMS_TENANT_MODULE_NAME;

@Component
@Slf4j
public class StatementValidator {
    private MdmsUtil mdmsUtil;
    private ObjectMapper objectMapper;
    private StatementConfiguration statementConfiguration;

    private EstimateUtil estimateUtil;

    @Autowired
    public StatementValidator(MdmsUtil mdmsUtil,ObjectMapper objectMapper,
                              StatementConfiguration statementConfiguration,EstimateUtil estimateUtil){
        this.mdmsUtil=mdmsUtil;
        this.objectMapper=objectMapper;
        this.statementConfiguration=statementConfiguration;
        this.estimateUtil=estimateUtil;
    }

    public void validateStatementOnCreate(StatementCreateRequest statementCreateRequest){
        StatementRequest statementRequest = statementCreateRequest.getStatementRequest();
        RequestInfo requestInfo=statementCreateRequest.getRequestInfo();
        validateTenantId(statementRequest,requestInfo);
        validateRequestInfo(requestInfo);
        validateStatementRequest(statementRequest);
        validateEstimate(statementRequest,requestInfo);

    }
    public void validateTenantId( StatementRequest statementRequest, RequestInfo requestInfo) {
        log.info("StatementValidator::validateTenantId");
        Set<String> validTenantSet = new HashSet<>();
        List<String> masterList = Collections.singletonList(MDMS_TENANTS_MASTER_NAME);
        Map<String, Map<String, JSONArray>> response = mdmsUtil.fetchMdmsData(requestInfo, statementConfiguration.getStateLevelTenantId(), MDMS_TENANT_MODULE_NAME, masterList);
        String node = response.get(MDMS_TENANT_MODULE_NAME).get(MDMS_TENANTS_MASTER_NAME).toString();
        try {
            JsonNode currNode = objectMapper.readTree(node);
            for (JsonNode tenantNode : currNode) {
                // Assuming each item in the array has a "code" field
                String tenantId = tenantNode.get("code").asText();
                validTenantSet.add(tenantId);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String tenantId = statementRequest.getTenantId();

        if (!validTenantSet.contains(tenantId)) {
            throw new CustomException(TENANT_ID_NOT_FOUND_CODE, tenantId + TENANT_ID_NOT_FOUND_MSG);
        }
    }

    public void validateStatementRequest(StatementRequest statementRequest){
        log.info("StatementValidator::validateStatementRequest");
        if(statementRequest==null){
            throw new CustomException(STATEMENT_REQUEST_EMPTY_CODE, STATEMENT_REQUEST_EMPTY_MSG);
        }
        if(statementRequest.getId() == null || statementRequest.getId().isEmpty()){
            throw new CustomException(ESTIMATE_ID_NOT_PASSED_CODE, ESTIMATE_ID_NOT_PASSED_MSG);
        }

    }

    public void validateEstimate(StatementRequest statementRequest, RequestInfo requestInfo) {
        log.info("StatementValidator::validateEstimate");
        Boolean isValidEstimate = estimateUtil.isValidEstimate(statementRequest,requestInfo);
        if(!isValidEstimate){
            throw new CustomException(INVALID_ESTIMATE_CODE, INVALID_ESTIMATE_MSG);

        }

    }

    private void validateRequestInfo(RequestInfo requestInfo) {
        log.info("EstimateServiceValidator::validateRequestInfo");
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }
}
