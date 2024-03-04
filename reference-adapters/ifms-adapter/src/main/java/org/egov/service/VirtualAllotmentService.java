package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.enrichment.VirtualAllotmentEnrichment;
import org.egov.repository.ExecutedVALogsRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.tracer.model.CustomException;
import org.egov.utils.MdmsUtils;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.*;

@Service
@Slf4j
public class VirtualAllotmentService {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MdmsUtils mdmsUtils;

    @Autowired
    private IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    private IfmsService ifmsService;

    @Autowired
    private ExecutedVALogsRepository executedVALogsRepository;

    @Autowired
    private VirtualAllotmentEnrichment vaEnrichment;

    @Autowired
    private SanctionDetailsRepository sanctionDetailsRepository;

    public void generateVirtualAllotment(RequestInfo requestInfo) {
        log.info("Start executing VA service.");

        List<String> tenants = getTenants(requestInfo);
        log.info("Fetched tenant details");

        if (tenants != null && !tenants.isEmpty()) {
            getSsuDetailsAndFetchVA(requestInfo, tenants);
        }


    }

    private void getSsuDetailsAndFetchVA (RequestInfo requestInfo, List<String> tenants) {
        log.info("Fetching HOA list from MDMS");
        // Get MDMS data
        JSONArray hoaList = ifmsService.getHeadOfAccounts(requestInfo);
        // Create ObjectMapper instance

        // Get SSU details for each tenants
        for (String tenantId: tenants) {
            log.info("Started fetching VA for tenant : " + tenantId);
            try {
                List<String> ssuMasters = new ArrayList<>();
                ssuMasters.add(MDMS_SSU_DETAILS_MASTER);
                log.info("Fetching SSU list from MDMS");
                Map<String, Map<String, JSONArray>> ssuDetailsResponse = mdmsUtils.fetchMdmsData(requestInfo, tenantId, MDMS_IFMS_MODULE_NAME, ssuMasters);
                log.info("SSU list from MDMS : " + ssuDetailsResponse);
                JSONArray ssuList = ssuDetailsResponse.get(MDMS_IFMS_MODULE_NAME).get(MDMS_SSU_DETAILS_MASTER);

                for (Object ssu: ssuList) {
                    for (Object hoa: hoaList) {
                        // Convert object to JsonNode
                        JsonNode hoaNode = objectMapper.valueToTree(hoa);
                        JsonNode ssuNode = objectMapper.valueToTree(ssu);
                        processVAForHOA(tenantId, hoaNode, ssuNode, requestInfo);
                    }

                }
            } catch (Exception e) {
                log.info("Exception occurred while executing VirtualAllotmentService:getSsuDetailsAndFetchVA." + e);
            }

        }

    }

    private void processVAForHOA(String tenantId, JsonNode hoaNode, JsonNode ssuNode, RequestInfo requestInfo) {
        log.info("Fetching allotments for HOA");
        try {
            ExecutedVALog executedVALog = getLastExecutedVA(tenantId, hoaNode, ssuNode);
            Long lastExecuted = null;
            if (executedVALog != null && executedVALog.getLastExecuted() != null) {
                lastExecuted = executedVALog.getLastExecuted();
            } else {
                String effectiveFrom = String.valueOf(hoaNode.get("effectiveFrom"));
                lastExecuted = Long.parseLong(effectiveFrom);
            }

            log.info("Calling ifms service.");
            JITRequest vaRequest = vaEnrichment.constructVARequest(hoaNode, ssuNode, lastExecuted);
            JITResponse vaResponse = ifmsService.sendRequestToIFMS(vaRequest);

            if (vaResponse.getErrorMsg() == null) {
                List<Object> vaResponseList = vaResponse.getData();
                List<Allotment> allotmentList = new ArrayList<>();
                if (vaResponseList != null && !vaResponseList.isEmpty()) {
                    for(Object va: vaResponseList) {
                        Allotment allotment = objectMapper.convertValue(va, Allotment.class);
                        allotmentList.add(allotment);
                    }
                    SanctionDetailsSearchCriteria searchCriteria = vaEnrichment.getSanctionDetailsSearchCriteriaForVA(tenantId, hoaNode, ssuNode);
                    List<SanctionDetail> existingSanctionDetailList = sanctionDetailsRepository.getSanctionDetails(searchCriteria);
                    List<SanctionDetail> updatedSanctions = vaEnrichment.getCreateAndUpdateSanctionDetails(existingSanctionDetailList, allotmentList);
                    vaEnrichment.enrichAndUpdateSanctions(updatedSanctions, tenantId, hoaNode, ssuNode, requestInfo);
                    Map<String, List<SanctionDetail>> createUpdateSanctions = vaEnrichment.getCreateUpdateSanctionMap(existingSanctionDetailList, updatedSanctions);
                    List<SanctionDetail> createSanctions =  createUpdateSanctions.get("create");
                    List<SanctionDetail> updateSanctions =  createUpdateSanctions.get("update");
                    // Get allotments to create and
                    List<Allotment> createAllotments =  vaEnrichment.getAllotmentsForCreate(updatedSanctions, allotmentList, tenantId, requestInfo);
                    sanctionDetailsRepository.createUpdateSanctionFunds(createSanctions, updateSanctions, createAllotments);
                    System.out.println(updatedSanctions);
                }
                // Update last executed for the va
                if (executedVALog == null) {
                    executedVALog = vaEnrichment.getExecutedVALogForCreate(tenantId, hoaNode, ssuNode, requestInfo);
                    executedVALogsRepository.saveExecutedVALogs(Collections.singletonList(executedVALog));
                } else {
                    executedVALog = vaEnrichment.enrichExecutedVaLogForUpdate(executedVALog, requestInfo);
                    executedVALogsRepository.updateExecutedVALogs(Collections.singletonList(executedVALog));
                }
            } else {
                log.info("Error on VA response  for hoa : "+ hoaNode.get("code").asText() +"["+vaResponse.getErrorMsg()+"]");
            }
        } catch (Exception e) {
            log.info("Exception occurred while executing VirtualAllotmentService:processVAForHOA." + e);
        }
    }

    public List<String> getTenants(RequestInfo requestInfo) {
        List<String> tenantIds = new ArrayList<>();
        List<String> tenantsMasters = new ArrayList<>();
        tenantsMasters.add(MDMS_TENANTS_MASTER);
        Map<String, Map<String, JSONArray>> tenantsResponse = mdmsUtils.fetchMdmsData(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_TENANT_MODULE_NAME, tenantsMasters);
        System.out.println(tenantsResponse);
        JSONArray tenantValues = tenantsResponse.get(MDMS_TENANT_MODULE_NAME).get(MDMS_TENANTS_MASTER);
        System.out.println(tenantValues);
        for (Object tenant: tenantValues) {
            // Create ObjectMapper instance
            // Convert object to JsonNode
            JsonNode tenantNode = objectMapper.valueToTree(tenant);
            String tenantId = tenantNode.get("code").textValue();
            if (tenantId != null && !tenantId.equals(ifmsAdapterConfig.getStateLevelTenantId())) {
                tenantIds.add(tenantId);
            }
        }
        return tenantIds;
    }

    private ExecutedVALog getLastExecutedVA(String tenantId, JsonNode hoaNode, JsonNode ssuNode) {
        log.info("Executing VirtualAllotmentService:getLastExecutedVA");

        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeCode = ssuNode.get("granteeAgCode").asText();
        List<ExecutedVALog> executedVALogs = executedVALogsRepository.getExecutedVALogs(tenantId, hoaCode, ddoCode, granteeCode);
        ExecutedVALog executedVALog = null;
        if (executedVALogs != null && !executedVALogs.isEmpty()) {
            executedVALog = executedVALogs.get(0);
        }
        return executedVALog;
    }

    public List<SanctionDetail> searchSanctions(FundsSearchRequest fundsSearchRequest){
        searchValidator(fundsSearchRequest.getSearchCriteria());
        List<SanctionDetail> sanctionDetails = sanctionDetailsRepository.getSanctionDetails(fundsSearchRequest.getSearchCriteria());

        log.info("Sending search response");
        return sanctionDetails;
    }

    private void searchValidator(SanctionDetailsSearchCriteria searchCriteria){

        if(CollectionUtils.isEmpty(searchCriteria.getIds()) && StringUtils.isEmpty(searchCriteria.getHoaCode())
                && StringUtils.isEmpty(searchCriteria.getDdoCode()) && StringUtils.isEmpty(searchCriteria.getMasterAllotmentId())
                && StringUtils.isEmpty(searchCriteria.getTenantId()))
            throw new CustomException("SEARCH_CRITERIA_MANDATORY", "Atleast one search parameter should be provided");
    }

}
