package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.enrichment.VirtualAllotmentEnrichment;
import org.egov.repository.AllotmentDetailsRepository;
import org.egov.repository.ExecutedVALogsRepository;
import org.egov.repository.FundsSummaryRepository;
import org.egov.repository.SanctionDetailsRepository;
import org.egov.utils.MdmsUtils;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.*;

@Service
@Slf4j
public class VirtualAllotmentService {

    @Autowired
    MdmsUtils mdmsUtils;

    @Autowired
    IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    IfmsService ifmsService;

    @Autowired
    ExecutedVALogsRepository executedVALogsRepository;

    @Autowired
    VirtualAllotmentEnrichment vaEnrichment;

    @Autowired
    SanctionDetailsRepository sanctionDetailsRepository;
    @Autowired
    FundsSummaryRepository fundsSummaryRepository;

    @Autowired
    AllotmentDetailsRepository allotmentRepository;

    public void generateVirtualAllotment(SanctionAllotmentRequest allotmentRequest) {

        RequestInfo requestInfo = allotmentRequest.getRequestInfo();

        List<String> tenants = getTenants(requestInfo);

        if (tenants != null && !tenants.isEmpty()) {
            getSsuDetailsAndFetchVA(requestInfo, tenants);
        }


    }

    private void getSsuDetailsAndFetchVA (RequestInfo requestInfo, List<String> tenants) {
        // Get MDMS data
        JSONArray hoaList = ifmsService.getHeadOfAccounts(requestInfo);
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Get SSU details for each tenants
        for (String tenantId: tenants) {
            try {
                List<String> ssuMasters = new ArrayList<>();
                ssuMasters.add(MDMS_SSU_DETAILS_MASTER);
                Map<String, Map<String, JSONArray>> ssuDetailsResponse = mdmsUtils.fetchMdmsData(requestInfo, tenantId, MDMS_IFMS_MODULE_NAME, ssuMasters);
                System.out.println("ssuDetailsResponse : "+ ssuDetailsResponse);
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

            }

        }

    }

    private void processVAForHOA(String tenantId, JsonNode hoaNode, JsonNode ssuNode, RequestInfo requestInfo) {
        try {
            ExecutedVALog executedVALog = getLastExecutedVA(tenantId, hoaNode, ssuNode);
            Long lastExecuted = null;
            if (executedVALog != null && executedVALog.getLastExecuted() != null) {
                lastExecuted = executedVALog.getLastExecuted();
            } else {
                String effectiveFrom = String.valueOf(hoaNode.get("effectiveFrom"));
                lastExecuted = Long.parseLong(effectiveFrom);
            }

            JITRequest vaRequest = vaEnrichment.constructVARequest(hoaNode, ssuNode, lastExecuted);
            JITResponse vaResponse = ifmsService.sendRequestToIFMS(vaRequest);

            // TODO: Temp response for dev remove after dev completed
//            JITResponse vaResponse = vaEnrichment.vaResponse();
            if (vaResponse.getErrorMsg() == null) {
                List<Object> vaResponseList = vaResponse.getData();
                List<Allotment> allotmentList = new ArrayList<>();
                if (vaResponseList != null && !vaResponseList.isEmpty()) {
                    for(Object va: vaResponseList) {
                        ObjectMapper objectMapper = new ObjectMapper();
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
                    if (createSanctions != null && !createSanctions.isEmpty()) {
                        sanctionDetailsRepository.saveSanctionDetails(createSanctions);
                        List<FundsSummary> fundsSummaries = vaEnrichment.getFundsSummariesFromSanctions(createSanctions);
                        fundsSummaryRepository.saveFundsSummary(fundsSummaries);
                    }
                    if (updateSanctions != null && !updateSanctions.isEmpty()) {
                        List<FundsSummary> fundsSummaries = vaEnrichment.getFundsSummariesFromSanctions(updateSanctions);
                        fundsSummaryRepository.updateFundsSummary(fundsSummaries);
                    }
                    // Get allotments to create and
                    List<Allotment> createAllotments =  vaEnrichment.getAllotmentsForCreate(updatedSanctions, allotmentList, tenantId, requestInfo);
                    if (createAllotments != null && !createAllotments.isEmpty()) {
                        allotmentRepository.saveAllotmentDetails(createAllotments);
                    }
                    System.out.println(updatedSanctions);
                }
            } else {
                log.info("Error on VA response  for hoa : "+ hoaNode.get("code").asText() +"["+vaResponse.getErrorMsg()+"]");
            }
            // Update last executed for the va
            if (executedVALog == null) {
                executedVALog = vaEnrichment.getExecutedVALogForCreate(tenantId, hoaNode, ssuNode, requestInfo);
                executedVALogsRepository.saveExecutedVALogs(Collections.singletonList(executedVALog));
            } else {
                executedVALog = vaEnrichment.enrichExecutedVaLogForUpdate(executedVALog, requestInfo);
                executedVALogsRepository.updateExecutedVALogs(Collections.singletonList(executedVALog));
            }
        } catch (Exception e) {
            System.out.println("Exception occured.");
        }
    }

    private List<String> getTenants(RequestInfo requestInfo) {
        List<String> tenantIds = new ArrayList<>();
        List<String> tenantsMasters = new ArrayList<>();
        tenantsMasters.add(MDMS_TENANTS_MASTER);
        Map<String, Map<String, JSONArray>> tenantsResponse = mdmsUtils.fetchMdmsData(requestInfo, ifmsAdapterConfig.getStateLevelTenantId(), MDMS_TENANT_MODULE_NAME, tenantsMasters);
        System.out.println(tenantsResponse);
        JSONArray tenantValues = tenantsResponse.get(MDMS_TENANT_MODULE_NAME).get(MDMS_TENANTS_MASTER);
        System.out.println(tenantValues);
        for (Object tenant: tenantValues) {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
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

}
