package org.egov.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.models.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.user.UserDetailResponse;
import org.egov.common.models.individual.Identifier;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.Skill;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ServiceCallException;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.BankAccount;
import org.egov.works.services.common.models.bankaccounts.BankAccountDetails;
import org.egov.works.services.common.models.musterroll.Status;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * MusterRollServiceUtil
 *
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * Utility class providing common methods used across muster roll service operations.
 * Contains both V1 (legacy) and V2 (intermediate billing) helper methods.
 *
 * V1 METHODS (Legacy - existing from master):
 * -------------------------------------------
 * - getAuditDetails(): Standard DIGIT audit trail
 * - populateAdditionalDetails(): Individual skill/bank details population
 * - isTenantBasedSearch(): Search mode detection
 * - fetchAttendanceRegister(): Fetch register from attendance service
 * - updateAttendanceRegister(): Update register status
 *
 * V2 METHODS (Intermediate Billing - NEW):
 * ----------------------------------------
 * - fetchBillingPeriod(): Fetch billing period from expense-calculator
 * - fetchBillingConfig(): Fetch campaign billing config
 * - checkIfCampaignHasBillingConfig(): V2 detection check
 * - findBillingPeriodForDates(): Auto-detect period from dates
 * - validateDatesAgainstPeriod(): Validate muster dates vs period
 * - getCampaignNumberFromRegister(): Extract campaign for billing
 * - isPeriodBilled(): Check if period is locked (bill generated)
 *
 * WHY V2 METHODS ARE IN THIS UTIL:
 * ---------------------------------
 * 1. SEPARATION OF CONCERNS: API call logic separated from business logic
 * 2. REUSABILITY: Multiple services need billing period lookups
 * 3. TESTABILITY: Can mock util in service tests
 * 4. CONSISTENCY: All API calls to expense-calculator go through here
 *
 * SERVICE DEPENDENCIES:
 * ---------------------
 * - Attendance Service: fetchAttendanceRegister, updateAttendanceRegister
 * - Expense Calculator: fetchBillingPeriod, fetchBillingConfig, isPeriodBilled
 * - MDMS: Skill level lookups
 *
 * ================================================================================
 */
@Component
@Slf4j
public class MusterRollServiceUtil {

	private final ObjectMapper mapper;

	private final RestTemplate restTemplate;

	private final UserUtil userUtil;

	private final MusterRollServiceConfiguration config;
	private static final String SKILL_CODE = "skillCode";

	private static final String ROLES = "roles";

	@Autowired
	public MusterRollServiceUtil(ObjectMapper mapper, RestTemplate restTemplate, UserUtil userUtil, MusterRollServiceConfiguration config) {
		this.mapper = mapper;
		this.restTemplate = restTemplate;
        this.userUtil = userUtil;
        this.config = config;
	}

	/**
	 * Method to return auditDetails for create/update flows
	 *
	 * @param by
	 * @param isCreate
	 * @return AuditDetails
	 */
	public AuditDetails getAuditDetails(String by, MusterRoll musterRoll, Boolean isCreate) {
		Long time = System.currentTimeMillis();
		if (Boolean.TRUE.equals(isCreate))
			return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time)
					.build();
		else
			return AuditDetails.builder().createdBy(musterRoll.getAuditDetails().getCreatedBy()).lastModifiedBy(by)
					.createdTime(musterRoll.getAuditDetails().getCreatedTime()).lastModifiedTime(time).build();
	}

	/**
	 * Fetch the individual skill level from MDMS
	 * 
	 * @param mdmsData
	 * @param individualEntry
	 * @param skillCode
	 *
	 */
	public void populateAdditionalDetails(Object mdmsData, IndividualEntry individualEntry, String skillCode,
			Individual matchedIndividual, BankAccount bankAccount, boolean isCreate) {
		final String jsonPathForWorksMuster = "$.MdmsRes." + "WORKS-SOR" + "."
				+ "SOR" + ".*";
		List<LinkedHashMap<String, String>> musterRes = null;

		try {
			musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::JSONPATH_ERROR",
					"Failed to parse mdms response");
		}

		String skillValue = "";
		if (skillCode != null && !CollectionUtils.isEmpty(musterRes)) {
			for (LinkedHashMap<String, String> codeValueMap : musterRes) {
				if (codeValueMap.get("id").equalsIgnoreCase(skillCode)) {
					skillValue = codeValueMap.get("name");
					break;
				}
			}
		}

		// populate individual details for estimate and create
		log.info("MusterRollServiceUtil::populateAdditionalDetails::start");
		JSONObject additionalDetails = new JSONObject();

		additionalDetails.put("userId", matchedIndividual.getIndividualId());
		additionalDetails.put("userName", matchedIndividual.getName().getGivenName());
		additionalDetails.put("fatherName", matchedIndividual.getFatherName());
		additionalDetails.put("mobileNo", matchedIndividual.getMobileNumber());
		additionalDetails.put("gender",matchedIndividual.getGender());
		Identifier aadhaar = matchedIndividual.getIdentifiers().stream()
				.filter(identifier -> identifier.getIdentifierType().contains("AADHAAR")).findFirst().orElse(null);
		if (aadhaar != null) {
			additionalDetails.put("aadharNumber", aadhaar.getIdentifierId());
		}

		// populate individual's skill details in create and update (user selected skill
		// will be set in additionalDetails)
		if (isCreate) {
			additionalDetails.put(SKILL_CODE, skillCode);
			additionalDetails.put("skillValue", skillValue);
		}

		// populate list of skills of the individual in estimate additionalDetails
		populateSkillsInEstimateDetails(isCreate,matchedIndividual,additionalDetails);

		if (bankAccount != null) {
			List<BankAccountDetails> bankAccountDetails = bankAccount.getBankAccountDetails();
			if (!CollectionUtils.isEmpty(bankAccountDetails)) {
				String accountNumber = bankAccountDetails.get(0).getAccountNumber();
				String ifscCode = bankAccountDetails.get(0).getBankBranchIdentifier().getCode();
				String accountHolderName = bankAccountDetails.get(0).getAccountHolderName();
				String accountType = bankAccountDetails.get(0).getAccountType();
				additionalDetails.put("bankDetails", accountNumber + "-" + ifscCode);
				additionalDetails.put("accountHolderName", accountHolderName);
				additionalDetails.put("accountType", accountType);
			}
		}

		if(config.isIndividualEntryRolesEnabled()) {
			UserDetailResponse userDetailResponse = userUtil.searchUsersByIndividualIds(Collections.singletonList(individualEntry.getId()));
			List<String> roles = userDetailResponse.getUser().stream().findFirst()
					.orElseThrow(() -> new CustomException(
							"MusterRollServiceUtil::populateAdditionalDetails::USER SEARCH ERROR",
							"No user found for the individual id " + individualEntry.getId())
					)
					.getRoles().stream().map(Role::getCode).toList();
			additionalDetails.put(ROLES, roles);
		}

		try {
			individualEntry.setAdditionalDetails(mapper.readValue(additionalDetails.toString(), Object.class));
		} catch (IOException e) {
			throw new CustomException("MusterRollServiceUtil::populateAdditionalDetails::PARSING ERROR",
					"Failed to set additionalDetail object");
		}

	}
	private void populateSkillsInEstimateDetails(boolean isCreate,Individual matchedIndividual,JSONObject additionalDetails){
		if (!isCreate && !CollectionUtils.isEmpty(matchedIndividual.getSkills())) {
			List<String> skillList = new ArrayList<>();
			for (Skill skill : matchedIndividual.getSkills()) {
				skillList.add(skill.getLevel());
			}
			additionalDetails.put(SKILL_CODE, skillList);
		}
	}

	public void updateAdditionalDetails(Object mdmsData, IndividualEntry individualEntry, String skillCode) {
		final String jsonPathForWorksMuster = "$.MdmsRes." + "WORKS-SOR" + "."
				+ "SOR" + ".*";
		List<LinkedHashMap<String, String>> musterRes = null;

		try {
			musterRes = JsonPath.read(mdmsData, jsonPathForWorksMuster);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new CustomException("MusterRollServiceUtil::updateAdditionalDetails::JSONPATH_ERROR",
					"Failed to parse mdms response");
		}

		String skillValue = "";
		if (skillCode != null && !CollectionUtils.isEmpty(musterRes)) {
			for (LinkedHashMap<String, String> codeValueMap : musterRes) {
				if (codeValueMap.get("id").equalsIgnoreCase(skillCode)) {
					skillValue = codeValueMap.get("name");
					break;
				}
			}
		}

		try {
			JsonNode node = mapper.readTree(mapper.writeValueAsString(individualEntry.getAdditionalDetails()));
			((ObjectNode) node).put(SKILL_CODE, skillCode);
			((ObjectNode) node).put("skillValue", skillValue);
			individualEntry.setAdditionalDetails(mapper.readValue(node.toString(), Object.class));

		} catch (IOException e) {
			log.info(
					"MusterRollServiceUtil::updateAdditionalDetails::Failed to parse additionalDetail object from request"
							+ e);
			throw new CustomException("PARSING ERROR",
					"Failed to parse additionalDetail object from request on update");
		}

	}

	/**
	 * Sets the attendanceLogId in additionalDetails of the attendanceEntry
	 * 
	 * @param attendanceEntry
	 * @param entryAttendanceLogId
	 * @param exitAttendanceLogId
	 */
	public void populateAdditionalDetailsAttendanceEntry(AttendanceEntry attendanceEntry, String entryAttendanceLogId,
			String exitAttendanceLogId) {
		JSONObject additionalDetails = new JSONObject();
		additionalDetails.put("entryAttendanceLogId", entryAttendanceLogId);
		additionalDetails.put("exitAttendanceLogId", exitAttendanceLogId);
		try {
			attendanceEntry.setAdditionalDetails(mapper.readValue(additionalDetails.toString(), Object.class));
		} catch (IOException e) {
			throw new CustomException("MusterRollServiceUtil::populateAdditionalDetailsAttendanceEntry::PARSING ERROR",
					"Failed to set additionalDetail object");
		}
	}

	/**
	 * Checks if the search is based only on tenantId
	 * 
	 * @param searchCriteria
	 * @return
	 */
	public boolean isTenantBasedSearch(MusterRollSearchCriteria searchCriteria) {
        return (searchCriteria.getIds() == null || searchCriteria.getIds().isEmpty())
                && StringUtils.isBlank(searchCriteria.getMusterRollNumber())
                && StringUtils.isBlank(searchCriteria.getRegisterId()) && searchCriteria.getFromDate() == null
                && searchCriteria.getToDate() == null && searchCriteria.getStatus() == null
                && StringUtils.isBlank(searchCriteria.getMusterRollStatus())
                && StringUtils.isNotBlank(searchCriteria.getTenantId());
    }

	public AttendanceRegisterResponse fetchAttendanceRegister(MusterRoll musterRoll, RequestInfo requestInfo) {
		log.info("MusterRollValidator::Fetching attendance register with tenantId::" + musterRoll.getTenantId()
				+ " and register ID: " + musterRoll.getRegisterId());
		String id = requestInfo.getUserInfo().getUuid();

		StringBuilder uri = new StringBuilder();
		uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
				.queryParam("tenantId", musterRoll.getTenantId()).queryParam("ids", musterRoll.getRegisterId())
				.queryParam("status", Status.ACTIVE);
		RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

		AttendanceRegisterResponse attendanceRegisterResponse = null;

		try {
			attendanceRegisterResponse = restTemplate.postForObject(uriBuilder.toUriString(), requestInfoWrapper,
					AttendanceRegisterResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
			log.error("MusterRollValidator::Error thrown from attendance register service::"
					+ httpClientOrServerExc.getStatusCode());
			throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION",
					"Error thrown from attendance register service::" + httpClientOrServerExc.getStatusCode());
		}

		if (attendanceRegisterResponse == null
				|| CollectionUtils.isEmpty(attendanceRegisterResponse.getAttendanceRegister())) {
			log.error("MusterRollValidator::User with id::" + id + " is not enrolled in the attendance register::"
					+ musterRoll.getRegisterId());
			throw new CustomException("ACCESS_EXCEPTION",
					"User is not enrolled in the attendance register and not authorized to fetch it");
		}
		return attendanceRegisterResponse;
	}

	public AttendanceRegisterResponse updateAttendanceRegister(AttendanceRegister attendanceRegister, RequestInfo requestInfo) {
		log.info("updateAttendanceRegister::Update attendance register with tenantId: {} and register ID: {}",
				attendanceRegister.getTenantId(), attendanceRegister.getId());

		StringBuilder uri = new StringBuilder();
		uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterUpdateEndpoint());

		AttendanceRegisterResponse response = null;

		AttendanceRegisterRequest attendanceRegisterRequest = AttendanceRegisterRequest.builder()
				.attendanceRegister(Collections.singletonList(attendanceRegister))
				.requestInfo(requestInfo)
				.build();
		try {
			response = restTemplate.postForObject(uri.toString(), attendanceRegisterRequest, AttendanceRegisterResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new ServiceCallException(e.getResponseBodyAsString());
		} catch (Exception e) {
			Map<String, String> map = new HashMap<>();
			String causeName = (e.getCause() != null) ? e.getCause().getClass().getName() : e.getClass().getName();
			map.put(causeName, e.getMessage());
			throw new CustomException(map);
		}

		return response;
	}

	/**
	 * Fetches billing period details from expense-calculator service.
	 *
	 * ================================================================================
	 * V2 INTERMEDIATE BILLING - BILLING PERIOD LOOKUP
	 * ================================================================================
	 *
	 * WHY THIS METHOD EXISTS:
	 * -----------------------
	 * In V2, muster roll dates must be calculated as:
	 *   musterDates = intersection(registerDates, periodDates)
	 *
	 * To calculate this intersection, we need the billing period's start/end dates.
	 * This method fetches that information from expense-calculator service.
	 *
	 * EXAMPLE:
	 * --------
	 * Register: Jan 1 - Dec 31, 2024
	 * Period 1: Jan 1 - Jan 31, 2024
	 * Muster 1: Jan 1 - Jan 31, 2024 (intersection)
	 *
	 * CALLER CONTEXT:
	 * ---------------
	 * Called by MusterRollService.applyPeriodAwareDates() during muster creation
	 * when billingPeriodId is provided (V2 flow).
	 *
	 * ERROR HANDLING:
	 * ---------------
	 * Throws CustomException if period not found - this is intentional because
	 * V2 flow REQUIRES valid billing period (unlike V1 which doesn't use periods).
	 *
	 * ================================================================================
	 *
	 * @param billingPeriodId Billing period ID
	 * @param tenantId Tenant ID
	 * @param requestInfo Request info
	 * @return BillingPeriod details
	 */
	public BillingPeriod fetchBillingPeriod(String billingPeriodId, String tenantId, RequestInfo requestInfo) {
		log.info("fetchBillingPeriod::Fetching billing period with ID: {} for tenant: {}", billingPeriodId, tenantId);

		// Build endpoint URL (without query parameters - using POST body instead)
		StringBuilder uri = new StringBuilder();
		uri.append(config.getExpenseCalculatorServiceHost())
			.append(config.getBillingPeriodSearchEndpoint());

		// Build search request with criteria
		BillingPeriodSearchCriteria criteria = BillingPeriodSearchCriteria.builder()
			.tenantId(tenantId)
			.ids(java.util.Collections.singletonList(billingPeriodId))
			.build();

		BillingPeriodSearchRequest searchRequest = BillingPeriodSearchRequest.builder()
			.requestInfo(requestInfo)
			.searchCriteria(criteria)
			.build();

		BillingPeriodResponse response = null;

		try {
			log.debug("fetchBillingPeriod::Calling endpoint: {} with criteria: {}", uri, criteria);
			response = restTemplate.postForObject(uri.toString(), searchRequest, BillingPeriodResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("fetchBillingPeriod::Error fetching billing period: {}", e.getResponseBodyAsString());
			throw new ServiceCallException(e.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("fetchBillingPeriod::Error fetching billing period", e);
			Map<String, String> map = new HashMap<>();
			String causeName = (e.getCause() != null) ? e.getCause().getClass().getName() : e.getClass().getName();
			map.put(causeName, e.getMessage());
			throw new CustomException(map);
		}

		if (response == null || CollectionUtils.isEmpty(response.getBillingPeriods())) {
			throw new CustomException("BILLING_PERIOD_NOT_FOUND",
				"Billing period not found with ID: " + billingPeriodId);
		}

		log.info("fetchBillingPeriod::Successfully fetched billing period: {}", response.getBillingPeriods().get(0).getId());
		return response.getBillingPeriods().get(0);
	}

	/**
	 * Fetches attendance register by ID.
	 * Wrapper method for consistent naming with billing period fetch.
	 *
	 * @param requestInfo Request info
	 * @param tenantId Tenant ID
	 * @param registerId Register ID
	 * @return AttendanceRegisterResponse
	 */
	public AttendanceRegisterResponse fetchAttendanceRegister(RequestInfo requestInfo, String tenantId, String registerId) {
		log.info("fetchAttendanceRegister::Fetching attendance register with tenantId: {} and register ID: {}",
			tenantId, registerId);

		StringBuilder uri = new StringBuilder();
		uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
			.queryParam("tenantId", tenantId)
			.queryParam("ids", registerId)
			.queryParam("status", Status.ACTIVE);

		RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder()
			.requestInfo(requestInfo)
			.build();

		AttendanceRegisterResponse attendanceRegisterResponse = null;

		try {
			attendanceRegisterResponse = restTemplate.postForObject(uriBuilder.toUriString(),
				requestInfoWrapper, AttendanceRegisterResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("fetchAttendanceRegister::Error thrown from attendance register service: {}",
				e.getStatusCode());
			throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION",
				"Error thrown from attendance register service: " + e.getStatusCode());
		}

		if (attendanceRegisterResponse == null
			|| CollectionUtils.isEmpty(attendanceRegisterResponse.getAttendanceRegister())) {
			log.error("fetchAttendanceRegister::Attendance register not found: {}", registerId);
			throw new CustomException("ATTENDANCE_REGISTER_NOT_FOUND",
				"Attendance register not found with ID: " + registerId);
		}

		return attendanceRegisterResponse;
	}

	/**
	 * Fetches billing configuration for a campaign from expense-calculator service.
	 * Used for V2 period-aware validation.
	 *
	 * @param campaignNumber Campaign number
	 * @param tenantId Tenant ID
	 * @param requestInfo Request info
	 * @return BillingConfig details or null if not found
	 */
	public BillingConfig fetchBillingConfig(String campaignNumber, String tenantId, RequestInfo requestInfo) {
		log.info("fetchBillingConfig::Fetching billing config for campaign: {} in tenant: {}", campaignNumber, tenantId);

		// Build endpoint URL
		StringBuilder uri = new StringBuilder();
		uri.append(config.getExpenseCalculatorServiceHost())
			.append(config.getBillingConfigSearchEndpoint());

		// Build search request
		BillingConfigSearchCriteria criteria = BillingConfigSearchCriteria.builder()
			.tenantId(tenantId)
			.campaignNumber(campaignNumber)
			.status("ACTIVE")
			.build();

		BillingConfigSearchRequest searchRequest = BillingConfigSearchRequest.builder()
			.requestInfo(requestInfo)
			.searchCriteria(criteria)
			.build();

		BillingConfigResponse response = null;

		try {
			log.debug("fetchBillingConfig::Calling endpoint: {} with criteria: {}", uri, criteria);
			response = restTemplate.postForObject(uri.toString(), searchRequest, BillingConfigResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.warn("fetchBillingConfig::Error fetching billing config (might not exist - V1 mode): {}",
				e.getResponseBodyAsString());
			return null; // Return null if not found - indicates V1 mode
		} catch (Exception e) {
			log.warn("fetchBillingConfig::Error fetching billing config: {}", e.getMessage());
			return null; // Return null on error - fallback to V1 mode
		}

		if (response == null || response.getBillingConfig() == null) {
			log.info("fetchBillingConfig::No billing config found for campaign: {} (V1 mode)", campaignNumber);
			return null;
		}

		log.info("fetchBillingConfig::Successfully fetched billing config: {} for campaign: {}",
			response.getBillingConfig().getId(), campaignNumber);
		return response.getBillingConfig();
	}

	/**
	 * Checks if a campaign has active billing configuration.
	 * Used to determine if V2 intermediate billing is enabled.
	 *
	 * @param campaignNumber Campaign number
	 * @param tenantId Tenant ID
	 * @param requestInfo Request info
	 * @return true if billing config exists and is active, false otherwise
	 */
	public boolean checkIfCampaignHasBillingConfig(String campaignNumber, String tenantId, RequestInfo requestInfo) {
		if (campaignNumber == null || campaignNumber.isEmpty()) {
			return false;
		}

		BillingConfig config = fetchBillingConfig(campaignNumber, tenantId, requestInfo);
		return config != null && "ACTIVE".equalsIgnoreCase(config.getStatus());
	}

	/**
	 * Finds the billing period that contains the given date range.
	 * Used for V2 to auto-detect which period the dates fall into.
	 *
	 * @param campaignNumber Campaign number
	 * @param tenantId Tenant ID
	 * @param startDate Start date in epoch milliseconds
	 * @param endDate End date in epoch milliseconds
	 * @param requestInfo Request info
	 * @return BillingPeriod that contains the dates, or null if not found
	 */
	public BillingPeriod findBillingPeriodForDates(String campaignNumber, String tenantId,
	                                               Long startDate, Long endDate, RequestInfo requestInfo) {
		log.info("findBillingPeriodForDates::Finding period for campaign: {}, dates: {} to {}",
			campaignNumber, startDate, endDate);

		// Build endpoint URL
		StringBuilder uri = new StringBuilder();
		uri.append(config.getExpenseCalculatorServiceHost())
			.append(config.getBillingPeriodSearchEndpoint());

		// Build search request
		BillingPeriodSearchCriteria criteria = BillingPeriodSearchCriteria.builder()
			.tenantId(tenantId)
			.campaignNumber(campaignNumber)
			.build();

		BillingPeriodSearchRequest searchRequest = BillingPeriodSearchRequest.builder()
			.requestInfo(requestInfo)
			.searchCriteria(criteria)
			.build();

		BillingPeriodResponse response = null;

		try {
			response = restTemplate.postForObject(uri.toString(), searchRequest, BillingPeriodResponse.class);
		} catch (Exception e) {
			log.error("findBillingPeriodForDates::Error fetching billing periods: {}", e.getMessage());
			return null;
		}

		if (response == null || CollectionUtils.isEmpty(response.getBillingPeriods())) {
			log.warn("findBillingPeriodForDates::No billing periods found for campaign: {}", campaignNumber);
			return null;
		}

		// Find period that contains the given dates
		for (BillingPeriod period : response.getBillingPeriods()) {
			Long periodStart = period.getPeriodStartDate();
			Long periodEnd = period.getPeriodEndDate();

			// Check if the provided dates fall completely within this period
			if (startDate >= periodStart && endDate <= periodEnd) {
				log.info("findBillingPeriodForDates::Found matching period: {} (period number: {})",
					period.getId(), period.getPeriodNumber());
				return period;
			}
		}

		log.warn("findBillingPeriodForDates::No period found that contains dates {} to {} for campaign: {}",
			startDate, endDate, campaignNumber);
		return null;
	}

	/**
	 * Validates that the given dates fall within the billing period boundaries.
	 * Throws exception if dates are outside the period.
	 *
	 * @param startDate Start date to validate
	 * @param endDate End date to validate
	 * @param period Billing period to validate against
	 * @throws CustomException if dates are outside period boundaries
	 */
	public void validateDatesAgainstPeriod(Long startDate, Long endDate, BillingPeriod period) {
		if (period == null) {
			throw new CustomException("BILLING_PERIOD_NULL", "Billing period cannot be null for validation");
		}

		Long periodStart = period.getPeriodStartDate();
		Long periodEnd = period.getPeriodEndDate();

		if (startDate < periodStart || endDate > periodEnd) {
			String message = String.format(
				"Dates (%d to %d) fall outside billing period boundaries (%d to %d). " +
				"Period number: %d, Campaign: %s",
				startDate, endDate, periodStart, periodEnd,
				period.getPeriodNumber(), period.getCampaignNumber()
			);
			log.error("validateDatesAgainstPeriod::{}", message);
			throw new CustomException("DATES_OUTSIDE_PERIOD", message);
		}

		log.info("validateDatesAgainstPeriod::Dates validated successfully against period: {}", period.getId());
	}

	/**
	 * Extracts campaign number from attendance register.
	 * Fetches the register and returns the campaign number.
	 *
	 * @param registerId Register ID
	 * @param tenantId Tenant ID
	 * @param requestInfo Request info
	 * @return Campaign number from register, or null if not found
	 */
	public String getCampaignNumberFromRegister(String registerId, String tenantId, RequestInfo requestInfo) {
		try {
			AttendanceRegisterResponse response = fetchAttendanceRegister(requestInfo, tenantId, registerId);
			if (response != null && !CollectionUtils.isEmpty(response.getAttendanceRegister())) {
				AttendanceRegister register = response.getAttendanceRegister().get(0);
				String referenceId = register.getReferenceId(); // This is the campaign number
				log.info("getCampaignNumberFromRegister::Extracted campaign number: {} from register: {}",
					referenceId, registerId);
				return referenceId;
			}
		} catch (Exception e) {
			log.error("getCampaignNumberFromRegister::Error fetching register: {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Checks if a billing period has been billed (i.e., intermediate bill generated successfully).
	 *
	 * ================================================================================
	 * V2 INTERMEDIATE BILLING - PERIOD LOCKING MECHANISM
	 * ================================================================================
	 *
	 * WHY THIS METHOD EXISTS:
	 * -----------------------
	 * Once an intermediate bill is generated for a period, the underlying data
	 * (muster rolls and attendance) must be LOCKED to maintain data consistency.
	 *
	 * If we allowed edits after billing:
	 *   1. Bill says: 100 workers × 10 days = 1000 attendance units
	 *   2. User edits attendance to: 100 workers × 5 days = 500 units
	 *   3. NOW: Bill shows 1000, but actual data shows 500 = INCONSISTENCY
	 *
	 * This method is called BEFORE any muster roll update to check if locked.
	 *
	 * FAIL-OPEN PATTERN:
	 * ------------------
	 * If the check fails (API error, endpoint doesn't exist), we ALLOW the update.
	 * This ensures system doesn't break during migration or if expense-calculator
	 * is temporarily unavailable.
	 *
	 * WHY FAIL-OPEN NOT FAIL-CLOSED:
	 * - V1 flows don't have this endpoint (would always fail)
	 * - During migration, endpoint may not be deployed yet
	 * - Better to allow occasional edit than block all updates
	 *
	 * CALLER CONTEXT:
	 * ---------------
	 * Called by MusterRollService.validatePeriodNotLocked() before update.
	 * Controlled by config: period.locking.enabled=true
	 *
	 * ================================================================================
	 *
	 * V2 Flow: Checks by billingPeriodId if provided
	 * V1 Flow: Checks by registerId + dates
	 *
	 * @param billingPeriodId Billing period ID (V2 - null for V1)
	 * @param registerId Register ID
	 * @param tenantId Tenant ID
	 * @param projectId Project ID (campaign number)
	 * @param requestInfo Request info
	 * @return true if period/register has been billed, false otherwise
	 */
	public boolean isPeriodBilled(String billingPeriodId, String registerId, String tenantId,
	                              String projectId, RequestInfo requestInfo) {
		log.info("isPeriodBilled::Checking if period is billed - periodId: {}, registerId: {}, projectId: {}",
			billingPeriodId, registerId, projectId);

		// Build endpoint URL for bill status check
		StringBuilder uri = new StringBuilder();
		uri.append(config.getExpenseCalculatorServiceHost())
			.append(config.getCheckBillStatusEndpoint());

		// Build query parameters
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("RequestInfo", requestInfo);
		requestBody.put("tenantId", tenantId);
		requestBody.put("projectId", projectId);

		if (StringUtils.isNotBlank(billingPeriodId)) {
			// V2 Flow: Check by billingPeriodId
			requestBody.put("billingPeriodId", billingPeriodId);
			log.debug("isPeriodBilled::V2 mode - checking by billingPeriodId: {}", billingPeriodId);
		} else {
			// V1 Flow: Check by registerId
			requestBody.put("registerId", registerId);
			log.debug("isPeriodBilled::V1 mode - checking by registerId: {}", registerId);
		}

		try {
			// Make REST call to check bill status
			Map<String, Object> response = restTemplate.postForObject(
				uri.toString(),
				requestBody,
				Map.class
			);

			if (response != null && response.containsKey("isBilled")) {
				Object isBilledObj = response.get("isBilled");
				boolean isBilled = false;

				// Type-safe parsing: handle Boolean, String, or other types
				if (isBilledObj instanceof Boolean) {
					isBilled = (Boolean) isBilledObj;
				} else if (isBilledObj instanceof String) {
					isBilled = "true".equalsIgnoreCase((String) isBilledObj);
				}

				log.info("isPeriodBilled::Period billed status: {}", isBilled);
				return isBilled;
			}
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			// If endpoint doesn't exist or returns error, assume not billed (fail-open)
			log.warn("isPeriodBilled::Error checking bill status (endpoint may not exist): {}",
				e.getStatusCode());
			return false;
		} catch (Exception e) {
			log.warn("isPeriodBilled::Error checking bill status: {}", e.getMessage());
			return false;
		}

		return false;
	}

	/**
	 * Check if bill generation has been initiated or completed for the given period.
	 *
	 * Calls the same _checkBillStatus endpoint as isPeriodBilled() but reads the
	 * isInitiated flag, which is true when eg_expense_bill_gen_status has an entry
	 * with status INITIATED or SUCCESSFUL for the period.
	 *
	 * Used exclusively for CAMPAIGN_SUPERVISOR edit gate — blocks as soon as bill
	 * processing starts, not just after report completion.
	 *
	 * Fail-open: returns false on any error to avoid blocking legitimate edits.
	 *
	 * @param billingPeriodId Billing period ID (V2 only — returns false if blank)
	 * @param projectId       Campaign/project reference ID
	 * @param tenantId        Tenant ID
	 * @param requestInfo     Request info for API call
	 * @return true if bill generation has been initiated or completed
	 */
	public boolean isBillInitiated(String billingPeriodId, String projectId,
	                               String tenantId, RequestInfo requestInfo) {
		if (StringUtils.isBlank(billingPeriodId)) {
			log.debug("isBillInitiated::No billingPeriodId — skipping (V1 or unknown)");
			return false;
		}

		log.info("isBillInitiated::Checking for period: {}, project: {}, tenant: {}",
				billingPeriodId, projectId, tenantId);

		StringBuilder uri = new StringBuilder();
		uri.append(config.getExpenseCalculatorServiceHost())
		   .append(config.getCheckBillStatusEndpoint());

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("RequestInfo", requestInfo);
		requestBody.put("tenantId", tenantId);
		requestBody.put("projectId", projectId);
		requestBody.put("billingPeriodId", billingPeriodId);

		try {
			Map<String, Object> response = restTemplate.postForObject(
					uri.toString(), requestBody, Map.class);

			if (response != null && response.containsKey("isInitiated")) {
				Object isInitiatedObj = response.get("isInitiated");
				boolean isInitiated = false;
				if (isInitiatedObj instanceof Boolean) {
					isInitiated = (Boolean) isInitiatedObj;
				} else if (isInitiatedObj instanceof String) {
					isInitiated = "true".equalsIgnoreCase((String) isInitiatedObj);
				}
				log.info("isBillInitiated::Result: {} for period: {}", isInitiated, billingPeriodId);
				return isInitiated;
			}
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.warn("isBillInitiated::HTTP error (fail-open): {}", e.getStatusCode());
			return false;
		} catch (Exception e) {
			log.warn("isBillInitiated::Error checking bill initiated status (fail-open): {}", e.getMessage());
			return false;
		}

		return false;
	}
}
