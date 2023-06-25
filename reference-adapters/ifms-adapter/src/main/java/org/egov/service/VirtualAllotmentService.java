package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.IfmsAdapterConfig;
import org.egov.repository.ExecutedVALogsRepository;
import org.egov.utils.MdmsUtils;
import org.egov.web.models.enums.JITServiceId;
import org.egov.web.models.jit.ExecutedVALog;
import org.egov.web.models.jit.JITRequest;
import org.egov.web.models.jit.SanctionAllotmentRequest;
import org.egov.web.models.jit.VARequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.egov.config.Constants.*;

@Service
public class VirtualAllotmentService {

    @Autowired
    MdmsUtils mdmsUtils;

    @Autowired
    IfmsAdapterConfig ifmsAdapterConfig;

    @Autowired
    IfmsService ifmsService;

    @Autowired
    ExecutedVALogsRepository executedVALogsRepository;

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
                        // TODO: Fetch last executed VA
                        Long lastExecuted = getLastExecutedVA(tenantId, hoaNode, ssuNode);
                        getVAFromIFMS(hoaNode, ssuNode, lastExecuted);
                    }


                }
            } catch (Exception e) {

            }

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

    public Long getLastExecutedVA(String tenantId, JsonNode hoaNode, JsonNode ssuNode) {
        System.out.println(hoaNode);
        System.out.println(ssuNode);
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeCode = ssuNode.get("granteeAgCode").asText();
        List<ExecutedVALog> executedVALogs = executedVALogsRepository.getAttendanceLogs(tenantId, hoaCode, ddoCode, granteeCode);
        Long lastExecuted = null;
        if (executedVALogs != null && !executedVALogs.isEmpty()) {
            ExecutedVALog executedVALog = executedVALogs.get(0);
            if (executedVALog.getLastExecuted() != null) {
                lastExecuted = executedVALog.getLastExecuted();
            }
        } else {
            String effectiveFrom = String.valueOf(hoaNode.get("effectiveFrom"));
            lastExecuted = Long.parseLong(effectiveFrom);
        }
        return lastExecuted;
    }

    public void getVAFromIFMS(JsonNode hoaNode, JsonNode ssuNode, Long lastExecuted) {
        String hoaCode = hoaNode.get("code").asText();
        String ddoCode = ssuNode.get("ddoCode").asText();
        String granteeCode = ssuNode.get("granteeAgCode").asText();
        String fromDate = getFormattedTimeFromTimestamp(lastExecuted, VA_REQUEST_TIME_FORMAT);

        VARequest vaRequest = VARequest.builder()
                .hoa(hoaCode)
                .ddoCode(ddoCode)
                .granteCode(granteeCode)
                .fromDate(fromDate)
                .build();

        JITRequest jitRequest = JITRequest.builder()
                .serviceId(JITServiceId.VA)
                .params(vaRequest)
                .build();


    }

    private String getFormattedTimeFromTimestamp(Long timestamp, String dateFormat) {
        // Convert timestamp to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, java.time.ZoneOffset.UTC);
        // Create a formatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        // Format the LocalDateTime to the desired format
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }
}
