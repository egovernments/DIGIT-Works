package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.egov.digit.expense.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.mdms.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MdmsUtil {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final Configuration configs;

	@Autowired
	public MdmsUtil(RestTemplate restTemplate, ObjectMapper mapper, Configuration configs) {
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.configs = configs;
	}

    public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, BillRequest billRequest) {
        StringBuilder uri = new StringBuilder();
        uri.append(configs.getMdmsV2Host()).append(configs.getMdmsV2EndPoint());
        MdmsCriteriaReq mdmsCriteriaReq = prepareMdMsRequest(requestInfo, tenantId);
        Object response = new HashMap<>();
        MdmsResponse mdmsResponse = new MdmsResponse();
        try {
            response = restTemplate.postForObject(uri.toString(), mdmsCriteriaReq, Map.class);
            mdmsResponse = mapper.convertValue(response, MdmsResponse.class);
        }catch(Exception e) {
            log.error("Exception occurred while fetching category lists from mdms: ",e);
        }

		Long createdTime = billRequest.getBill().getAuditDetails() != null ? billRequest.getBill().getAuditDetails().getCreatedTime() : Instant.now().toEpochMilli();
		Map<String, Map<String, JSONArray>> mdmsRes = filterMdmsResponseByDate(createdTime, mdmsResponse.getMdmsRes());
		log.info(mdmsRes.toString());
        return mdmsRes;
    }
	public Map<String, Map<String, JSONArray>> filterMdmsResponseByDate(Long createdTime, Map<String, Map<String, JSONArray>> mdmsRes) {
		Map<String, Map<String, JSONArray>> filteredMdmsRes = new HashMap<>();
		for (Map.Entry<String, Map<String, JSONArray>> entry : mdmsRes.entrySet()) {
			Map<String, JSONArray> moduleMap = entry.getValue();
			Map<String, JSONArray> filteredModuleMap = new HashMap<>();
			for (Map.Entry<String, JSONArray> moduleEntry : moduleMap.entrySet()) {
				String moduleName = moduleEntry.getKey();
				JSONArray filteredModuleArray = filterMdmsArrayByDate(createdTime, moduleEntry.getValue());
				filteredModuleMap.put(moduleName, filteredModuleArray);
			}
			filteredMdmsRes.put(entry.getKey(), filteredModuleMap);
		}
		return filteredMdmsRes;
	}

	private JSONArray filterMdmsArrayByDate(Long createdTime, JSONArray moduleArray) {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONArray filteredModuleArray = new JSONArray();
		for (Object o : moduleArray) {
			JsonNode moduleJsonNode = objectMapper.valueToTree(o);
			if (moduleJsonNode.has(Constants.EFFECTIVE_FROM_FIELD_MDMS) && moduleJsonNode.has(Constants.EFFECTIVE_TO_FIELD_MDMS)) {
				Long effectiveFrom = moduleJsonNode.get(Constants.EFFECTIVE_FROM_FIELD_MDMS).asLong();
				Long effectiveTo = moduleJsonNode.get(Constants.EFFECTIVE_TO_FIELD_MDMS).asLong();
				Boolean isActive = moduleJsonNode.get(Constants.ACTIVE_FIELD_MDMS).asBoolean();
				if ((moduleJsonNode.get(Constants.EFFECTIVE_TO_FIELD_MDMS).isNull() && effectiveFrom <= createdTime && isActive) ||
						(!moduleJsonNode.get(Constants.EFFECTIVE_TO_FIELD_MDMS).isNull() && effectiveFrom <= createdTime && effectiveTo >= createdTime)) {
					filteredModuleArray.add(o);
				}
			} else {
				filteredModuleArray.add(o);
			}
		}
		return filteredModuleArray;
	}
	/**
	 * prepares Master Data request
	 * 
	 * @param tenantId
	 * @param requestInfo
	 * @return
	 */
	public MdmsCriteriaReq prepareMdMsRequest(RequestInfo requestInfo, String tenantId) {

		// Criteria for tenant module
		List<MasterDetail> tenantMasterDetails = new ArrayList<>();
		Constants.TENANT_MDMS_MASTER_NAMES.forEach(name ->
			tenantMasterDetails.add(MasterDetail.builder().name(name).build())
		);

		ModuleDetail tenantModuleDetail = ModuleDetail.builder()
				.moduleName(Constants.TENANT_MODULE_NAME)
				.masterDetails(tenantMasterDetails)
				.build();

		// Criteria for Expense module
		List<MasterDetail> expenseMasterDetails = new ArrayList<>();
		Constants.EXPENSE_MDMS_MASTER_NAMES.forEach(name ->
			expenseMasterDetails.add(MasterDetail.builder().name(name).build())
		);
		ModuleDetail expenseModuleDetail = ModuleDetail.builder()
				.moduleName(Constants.EXPENSE_MODULE_NAME)
				.masterDetails(expenseMasterDetails)
				.build();

		List<ModuleDetail> moduleDetails = new ArrayList<>();
		moduleDetails.add(tenantModuleDetail);
		moduleDetails.add(expenseModuleDetail);
		
		MdmsCriteria mdmsCriteria = MdmsCriteria.builder()
				.tenantId(tenantId)
				.moduleDetails(moduleDetails)
				.build();

		return MdmsCriteriaReq.builder()
				.requestInfo(requestInfo)
				.mdmsCriteria(mdmsCriteria)
				.build();
	}
	
}
