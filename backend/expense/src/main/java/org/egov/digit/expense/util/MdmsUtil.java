package org.egov.digit.expense.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.egov.digit.expense.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.RateFieldConfig;
import org.egov.mdms.model.*;

import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.egov.digit.expense.config.Constants.*;

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
			throw new CustomException("ERROR_FETCHING_MASTER_DATA", "Exception while fetching tenant.tenants, expense.HeadCodes, expense.BusinessServices from mdms: " + e.getMessage());
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
	 * Fetches WorkerMdms from MDMS V2 for the given campaignId and returns the resolved,
	 * ordered fieldConfig list. Returns DEFAULT_FIELD_CONFIGS when no fieldConfig is defined
	 * in MDMS. Returns empty list on any error (caller should handle gracefully).
	 */
	public List<RateFieldConfig> fetchWorkerRateFieldConfig(RequestInfo requestInfo,
	                                                         String tenantId,
	                                                         String campaignId) {
		try {
			String filter = WORKER_RATES_CAMPAIGN_ID_FILTER_PREFIX + campaignId
					+ WORKER_RATES_CAMPAIGN_ID_FILTER_SUFFIX;

			MdmsCriteria criteria = MdmsCriteria.builder()
					.tenantId(tenantId.contains(".") ? tenantId.split("\\.")[0] : tenantId)
					.moduleDetails(Collections.singletonList(ModuleDetail.builder()
							.moduleName(HCM_MODULE_NAME)
							.masterDetails(Collections.singletonList(MasterDetail.builder()
									.name(WORKER_RATES_MASTER)
									.filter(filter)
									.build()))
							.build()))
					.build();

			String url = configs.getMdmsV2Host() + configs.getMdmsV2EndPoint();
			Object response = restTemplate.postForObject(url,
					MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(criteria).build(),
					Object.class);

			List<Object> workerRatesList = JsonPath.read(mapper.writeValueAsString(response),
					HCM_WORKER_RATES_JSON_PATH);

			if (workerRatesList == null || workerRatesList.isEmpty()) {
				log.warn("No WORKER_RATES MDMS entry for campaignId={}", campaignId);
				return RateFieldConfig.DEFAULT_FIELD_CONFIGS;
			}

			// Use first matching entry
			Object workerMdmsRaw = workerRatesList.get(0);
			Map<String, Object> workerMdmsMap = mapper.convertValue(workerMdmsRaw,
					new TypeReference<Map<String, Object>>() {});

			Object fcRaw = workerMdmsMap.get("fieldConfig");
			if (fcRaw == null) {
				log.info("No fieldConfig in MDMS for campaignId={} — using defaults", campaignId);
				return RateFieldConfig.DEFAULT_FIELD_CONFIGS;
			}

			List<RateFieldConfig> configs = mapper.convertValue(fcRaw,
					new TypeReference<List<RateFieldConfig>>() {});
			if (configs == null || configs.isEmpty()) {
				return RateFieldConfig.DEFAULT_FIELD_CONFIGS;
			}

			configs.sort(Comparator.comparingInt(f -> Optional.ofNullable(f.getOrder()).orElse(99)));
			log.info("Fetched {} fieldConfig entries from MDMS for campaignId={}", configs.size(), campaignId);
			return configs;

		} catch (Exception e) {
			log.warn("Failed to fetch worker rate fieldConfig from MDMS for campaignId={}: {}",
					campaignId, e.getMessage());
			return Collections.emptyList();
		}
	}

	/**
	 * Fetches rateMaxLimitSchema for a given campaignType (projectType) from MDMS master
	 * HCM-BILLING-CONFIG-PAYMENT-SETUP/CampaignTypeSkills.
	 *
	 * Returns Map&lt;fieldKey, maxDisplayValue&gt; — keys are fieldKeys (e.g. PER_DAY, FOOD, TRAVEL).
	 * Callers resolve fieldKey → headCode via bill.additionalDetails.workerRatesSnapshot.
	 *
	 * Returns an empty map when projectType is not found in MDMS or on any error.
	 */
	public Map<String, BigDecimal> fetchCampaignRateLimits(RequestInfo requestInfo,
	                                                        String tenantId,
	                                                        String projectType) {
		try {
			String filter = CAMPAIGN_TYPE_SKILLS_FILTER_PREFIX + projectType + CAMPAIGN_TYPE_SKILLS_FILTER_SUFFIX;
			String rootTenantId = tenantId.contains(".") ? tenantId.split("\\.")[0] : tenantId;

			MdmsCriteria criteria = MdmsCriteria.builder()
					.tenantId(rootTenantId)
					.moduleDetails(Collections.singletonList(ModuleDetail.builder()
							.moduleName(HCM_BILLING_CONFIG_MODULE)
							.masterDetails(Collections.singletonList(MasterDetail.builder()
									.name(CAMPAIGN_TYPE_SKILLS_MASTER)
									.filter(filter)
									.build()))
							.build()))
					.build();

			String url = configs.getMdmsV2Host() + configs.getMdmsV2EndPoint();
			Object response = restTemplate.postForObject(url,
					MdmsCriteriaReq.builder().requestInfo(requestInfo).mdmsCriteria(criteria).build(),
					Object.class);

			String responseJson = mapper.writeValueAsString(response);
			String jsonPath = "$.MdmsRes." + HCM_BILLING_CONFIG_MODULE + "." + CAMPAIGN_TYPE_SKILLS_MASTER;
			List<Object> skillsList = JsonPath.read(responseJson, jsonPath);

			if (skillsList == null || skillsList.isEmpty()) {
				log.info("No CampaignTypeSkills MDMS entry for projectType={}", projectType);
				return Collections.emptyMap();
			}

			Map<String, Object> skillsEntry = mapper.convertValue(skillsList.get(0),
					new TypeReference<Map<String, Object>>() {});

			Object limitsRaw = skillsEntry.get(RATE_MAX_LIMIT_SCHEMA_KEY);
			if (limitsRaw == null) return Collections.emptyMap();

			Map<String, Object> rateMaxLimitSchema = mapper.convertValue(limitsRaw,
					new TypeReference<Map<String, Object>>() {});

			// Keys in rateMaxLimitSchema are fieldKeys (e.g. "FOOD", "TRAVEL", "PER_DAY")
			// — resolved to actual headCodes at validation/generation time via bill.additionalDetails
			Map<String, BigDecimal> fieldKeyToMaxLimit = new HashMap<>();
			for (Map.Entry<String, Object> entry : rateMaxLimitSchema.entrySet()) {
				if (entry.getValue() != null) {
					fieldKeyToMaxLimit.put(entry.getKey(), new BigDecimal(entry.getValue().toString()));
				}
			}
			log.info("Fetched rate limits for projectType={}: {}", projectType, fieldKeyToMaxLimit);
			return fieldKeyToMaxLimit;

		} catch (Exception e) {
			log.warn("Failed to fetch campaign rate limits for projectType={}: {}", projectType, e.getMessage());
			return Collections.emptyMap();
		}
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