package org.egov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RegisterPeriodEnrichmentService
 *
 * V2 Intermediate Billing - Period-based register enrichment
 *
 * Responsibilities:
 * 1. Filter registers that overlap with billing period dates
 * 2. Call muster-roll V2 search API to get muster roll status for each register
 * 3. Enrich each register with registerPeriodStatus field
 *
 * This service is only invoked when billingPeriodId is present in search criteria.
 */
@Service
@Slf4j
public class RegisterPeriodEnrichmentService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final AttendanceServiceConfiguration config;

    @Autowired
    public RegisterPeriodEnrichmentService(RestTemplate restTemplate,
                                          @org.springframework.beans.factory.annotation.Qualifier("objectMapper") ObjectMapper mapper,
                                          AttendanceServiceConfiguration config) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.config = config;
    }

    /**
     * Main enrichment method - filters and enriches registers with period status
     *
     * @param registers List of registers to enrich
     * @param billingPeriodId Billing period ID from search criteria
     * @param requestInfo Request info for service calls
     * @param tenantId Tenant ID
     * @return Filtered and enriched list of registers
     */
    public List<AttendanceRegister> filterAndEnrichRegistersForPeriod(
            List<AttendanceRegister> registers,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        if (CollectionUtils.isEmpty(registers)) {
            log.info("No registers to enrich for period {}", billingPeriodId);
            return registers;
        }

        log.info("Starting period-based enrichment for {} registers with period {}",
                registers.size(), billingPeriodId);

        // Step 1: Fetch billing period details to get date range
        Map<String, Object> billingPeriod = fetchBillingPeriod(billingPeriodId, tenantId, requestInfo);

        if (billingPeriod == null) {
            log.warn("Billing period {} not found, skipping period-based enrichment", billingPeriodId);
            // Return registers without enrichment if period not found
            return registers;
        }

        Long periodStartDate = getLongValue(billingPeriod.get("periodStartDate"));
        Long periodEndDate = getLongValue(billingPeriod.get("periodEndDate"));

        // Additional validation after conversion
        if (periodStartDate == null || periodEndDate == null) {
            log.error("Failed to parse billing period dates - startDate: {}, endDate: {}",
                    periodStartDate, periodEndDate);
            throw new CustomException("INVALID_PERIOD_DATES",
                    "Unable to parse billing period dates for period " + billingPeriodId);
        }

        if (periodEndDate < periodStartDate) {
            log.error("Invalid billing period {} - end date {} is before start date {}",
                    billingPeriodId, periodEndDate, periodStartDate);
            throw new CustomException("INVALID_PERIOD_RANGE",
                    "Billing period end date cannot be before start date");
        }

        log.info("Billing period {} dates: {} to {}", billingPeriodId, periodStartDate, periodEndDate);

        // Step 2: Filter registers that overlap with period dates
        List<AttendanceRegister> filteredRegisters = filterRegistersByPeriodDates(
                registers, periodStartDate, periodEndDate);

        log.info("Filtered {} out of {} registers that overlap with period {}",
                filteredRegisters.size(), registers.size(), billingPeriodId);

        if (filteredRegisters.isEmpty()) {
            log.info("No registers overlap with period {}", billingPeriodId);
            return filteredRegisters;
        }

        // Step 3: Enrich filtered registers with muster roll status
        enrichRegistersWithMusterRollStatus(filteredRegisters, billingPeriodId, requestInfo, tenantId);

        log.info("Successfully enriched {} registers with period status", filteredRegisters.size());

        return filteredRegisters;
    }

    /**
     * Fetch billing period details from health-expense-calculator service
     *
     * @param billingPeriodId Billing period ID
     * @param tenantId Tenant ID
     * @param requestInfo Request info
     * @return Billing period details map
     */
    private Map<String, Object> fetchBillingPeriod(String billingPeriodId,
                                                   String tenantId,
                                                   RequestInfo requestInfo) {
        try {
            // Build URL to health-expense-calculator billing period search API
            String uri = config.getExpenseCalculatorHost() +
                        config.getBillingPeriodSearchEndpoint();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("RequestInfo", requestInfo);

            // Build search criteria using correct field name as per BillingPeriodSearchRequest
            Map<String, Object> searchCriteria = new HashMap<>();
            searchCriteria.put("tenantId", tenantId);
            searchCriteria.put("ids", Collections.singletonList(billingPeriodId));

            requestBody.put("searchCriteria", searchCriteria);

            log.info("Fetching billing period {} from expense-calculator using endpoint: {}",
                    billingPeriodId, uri);

            Map<String, Object> response = restTemplate.postForObject(uri, requestBody, Map.class);

            // Validate response structure
            if (response == null) {
                log.warn("Null response received from billing period search API for period {}",
                        billingPeriodId);
                return null;
            }

            if (!response.containsKey("billingPeriods")) {
                log.warn("Response does not contain 'billingPeriods' key for period {}",
                        billingPeriodId);
                return null;
            }

            List<Map<String, Object>> periods = (List<Map<String, Object>>) response.get("billingPeriods");

            if (CollectionUtils.isEmpty(periods)) {
                log.warn("Billing period {} not found in response - empty periods list",
                        billingPeriodId);
                return null;
            }

            Map<String, Object> period = periods.get(0);

            // Validate critical fields exist
            if (period.get("periodStartDate") == null || period.get("periodEndDate") == null) {
                log.error("Billing period {} is missing required date fields - startDate: {}, endDate: {}",
                        billingPeriodId, period.get("periodStartDate"), period.get("periodEndDate"));
                throw new CustomException("INVALID_BILLING_PERIOD",
                        "Billing period " + billingPeriodId + " is missing required date fields");
            }

            log.info("Successfully fetched billing period {} with dates: {} to {}",
                    billingPeriodId, period.get("periodStartDate"), period.get("periodEndDate"));

            return period;

        } catch (CustomException e) {
            // Re-throw custom exceptions
            throw e;
        } catch (Exception e) {
            log.error("Error fetching billing period {}: {}", billingPeriodId, e.getMessage(), e);
            throw new CustomException("BILLING_PERIOD_FETCH_ERROR",
                    "Failed to fetch billing period " + billingPeriodId + ": " + e.getMessage());
        }
    }

    /**
     * Filter registers that overlap with billing period dates
     *
     * A register overlaps if:
     * - register.startDate <= period.endDate AND
     * - register.endDate >= period.startDate
     *
     * Edge cases handled:
     * - Null register dates: excluded from results with warning
     * - Invalid register ID: excluded with warning
     * - Register end before start: excluded with warning
     *
     * @param registers List of registers
     * @param periodStartDate Period start date (epoch millis)
     * @param periodEndDate Period end date (epoch millis)
     * @return Filtered list of overlapping registers
     */
    private List<AttendanceRegister> filterRegistersByPeriodDates(
            List<AttendanceRegister> registers,
            Long periodStartDate,
            Long periodEndDate) {

        if (CollectionUtils.isEmpty(registers)) {
            log.info("No registers to filter");
            return new ArrayList<>();
        }

        return registers.stream()
                .filter(register -> {
                    // Validate register ID
                    if (StringUtils.isBlank(register.getId())) {
                        log.warn("Found register with null or empty ID, excluding from period filter");
                        return false;
                    }

                    // Validate register dates exist
                    if (register.getStartDate() == null || register.getEndDate() == null) {
                        log.warn("Register {} has null dates (start: {}, end: {}), excluding from period filter",
                                register.getId(), register.getStartDate(), register.getEndDate());
                        return false;
                    }

                    try {
                        // Convert BigDecimal to long for comparison
                        long registerStart = register.getStartDate().longValue();
                        long registerEnd = register.getEndDate().longValue();

                        // Validate register date range
                        if (registerEnd < registerStart) {
                            log.warn("Register {} has invalid date range - end {} is before start {}, excluding",
                                    register.getId(), registerEnd, registerStart);
                            return false;
                        }

                        // Check overlap: register.start <= period.end && register.end >= period.start
                        boolean overlaps = registerStart <= periodEndDate && registerEnd >= periodStartDate;

                        if (!overlaps) {
                            log.debug("Register {} ({} to {}) does not overlap with period ({} to {})",
                                    register.getId(), registerStart, registerEnd,
                                    periodStartDate, periodEndDate);
                        } else {
                            log.debug("Register {} ({} to {}) overlaps with period ({} to {})",
                                    register.getId(), registerStart, registerEnd,
                                    periodStartDate, periodEndDate);
                        }

                        return overlaps;

                    } catch (Exception e) {
                        log.error("Error processing register {} dates: {}", register.getId(), e.getMessage());
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * V2 OPTIMIZED - Enrich registers with muster roll status from DATABASE (no API calls!)
     *
     * Uses the pre-computed period_statuses JSONB field that is updated asynchronously
     * via Kafka events when muster-roll status changes.
     *
     * Benefits:
     * - NO synchronous API calls to muster-roll service
     * - Scales to millions of registers
     * - O(n) complexity instead of O(n) API calls
     * - Pre-computed status for instant response
     * - Event-driven, eventually consistent
     *
     * Edge cases handled:
     * - Empty register list: logs and returns
     * - Null period_statuses field: treated as NOT_CREATED
     * - Empty period_statuses array: treated as NOT_CREATED
     * - Period not found in array: treated as NOT_CREATED
     *
     * @param registers List of registers to enrich (already loaded with period_statuses from DB)
     * @param billingPeriodId Billing period ID to search for
     * @param requestInfo Request info (UNUSED - kept for API compatibility)
     * @param tenantId Tenant ID (UNUSED - kept for API compatibility)
     */
    private void enrichRegistersWithMusterRollStatus(
            List<AttendanceRegister> registers,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        if (CollectionUtils.isEmpty(registers)) {
            log.info("enrichRegistersWithMusterRollStatus::No registers to enrich with muster roll status");
            return;
        }

        log.info("enrichRegistersWithMusterRollStatus::Enriching {} registers with period status from DATABASE (no API calls) for period {}",
                registers.size(), billingPeriodId);

        int enrichedCount = 0;
        int notCreatedCount = 0;
        int nullPeriodStatusesCount = 0;

        // Enrich each register by reading from its period_statuses field
        for (AttendanceRegister register : registers) {
            if (StringUtils.isBlank(register.getId())) {
                log.warn("enrichRegistersWithMusterRollStatus::Register has null/empty ID, setting status to NOT_CREATED");
                register.setRegisterPeriodStatus("NOT_CREATED");
                notCreatedCount++;
                continue;
            }

            // Get period_statuses array from register (already loaded from DB by rowmapper)
            List<org.egov.web.models.RegisterPeriodStatus> periodStatuses = register.getPeriodStatuses();

            if (CollectionUtils.isEmpty(periodStatuses)) {
                // No period statuses available - muster roll not yet created
                log.debug("enrichRegistersWithMusterRollStatus::Register {} has no period_statuses, setting to NOT_CREATED",
                        register.getId());
                register.setRegisterPeriodStatus("NOT_CREATED");
                nullPeriodStatusesCount++;
                continue;
            }

            // Find the status for the requested billing period
            String periodStatus = findStatusForPeriod(periodStatuses, billingPeriodId);

            if (periodStatus != null) {
                // Period status found in database
                register.setRegisterPeriodStatus(periodStatus);
                enrichedCount++;
                log.debug("enrichRegistersWithMusterRollStatus::Register {} period status from DB: {}",
                        register.getId(), periodStatus);
            } else {
                // FALLBACK: Period not found in period_statuses (Kafka event might have failed)
                // Try fetching from muster-roll API as backup
                log.warn("enrichRegistersWithMusterRollStatus::FALLBACK TRIGGERED - Register {} period {} not found in period_statuses. " +
                        "Attempting muster roll API fallback to check if muster roll exists.",
                        register.getId(), billingPeriodId);

                String fallbackStatus = searchMusterRollStatusForSingleRegister(
                        register.getId(), billingPeriodId, requestInfo, tenantId);

                if (fallbackStatus != null) {
                    // Muster roll exists but wasn't in period_statuses (Kafka sync failed)
                    register.setRegisterPeriodStatus(fallbackStatus);
                    enrichedCount++;
                    log.info("enrichRegistersWithMusterRollStatus::FALLBACK SUCCESS - Register {} period {} status retrieved from API: {}",
                            register.getId(), billingPeriodId, fallbackStatus);
                } else {
                    // No muster roll found - genuinely NOT_CREATED
                    register.setRegisterPeriodStatus("NOT_CREATED");
                    notCreatedCount++;
                    log.debug("enrichRegistersWithMusterRollStatus::FALLBACK CONFIRMED - Register {} period {} has no muster roll, status: NOT_CREATED",
                            register.getId(), billingPeriodId);
                }
            }
        }

        log.info("enrichRegistersWithMusterRollStatus::Successfully enriched {} registers - " +
                        "Found status: {}, NOT_CREATED: {}, Null period_statuses: {}",
                registers.size(), enrichedCount, notCreatedCount, nullPeriodStatusesCount);
    }

    /**
     * Find the status for a specific billing period from the period_statuses array
     *
     * @param periodStatuses List of period statuses from database
     * @param billingPeriodId Billing period ID to find
     * @return Status string if found, null otherwise
     */
    private String findStatusForPeriod(
            List<org.egov.web.models.RegisterPeriodStatus> periodStatuses,
            String billingPeriodId) {

        if (CollectionUtils.isEmpty(periodStatuses) || StringUtils.isBlank(billingPeriodId)) {
            return null;
        }

        return periodStatuses.stream()
                .filter(ps -> billingPeriodId.equals(ps.getPeriodId()))
                .map(org.egov.web.models.RegisterPeriodStatus::getStatus)
                .findFirst()
                .orElse(null);
    }

    /**
     * FALLBACK METHOD - Search muster roll for a single register and period
     *
     * Called when a period is missing from the period_statuses field (Kafka event failure scenario).
     * Makes a targeted API call to muster-roll service to check if a muster roll exists.
     *
     * Use Case:
     * - period_statuses has [p1, p2, p3, p5] but p4 is missing due to Kafka failure
     * - This method checks if p4 actually has a muster roll created
     * - Returns the actual status if found, null if not found
     *
     * Performance:
     * - Targeted query: Single register + Single period
     * - Only called when period is missing from period_statuses (rare case)
     * - Does not impact normal flow (99% cases use period_statuses field)
     *
     * @param registerId Single register ID
     * @param billingPeriodId Billing period ID
     * @param requestInfo Request info for API call
     * @param tenantId Tenant ID
     * @return Muster roll status if found, null otherwise
     */
    private String searchMusterRollStatusForSingleRegister(
            String registerId,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        // Validate input parameters
        if (StringUtils.isBlank(registerId)) {
            log.warn("searchMusterRollStatusForSingleRegister::Empty register ID provided");
            return null;
        }

        if (StringUtils.isBlank(billingPeriodId)) {
            log.warn("searchMusterRollStatusForSingleRegister::Empty billing period ID provided");
            return null;
        }

        if (StringUtils.isBlank(tenantId)) {
            log.warn("searchMusterRollStatusForSingleRegister::Empty tenant ID provided");
            return null;
        }

        try {
            // Build URL to muster-roll V2 search API with query parameters
            // Targeted query: Single register + Single period
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(config.getMusterRollHost())
                      .append(config.getMusterRollV2SearchEndpoint())
                      .append("?tenantId=").append(URLEncoder.encode(tenantId, StandardCharsets.UTF_8))
                      .append("&billingPeriodId=").append(URLEncoder.encode(billingPeriodId, StandardCharsets.UTF_8))
                      .append("&registerIds=").append(URLEncoder.encode(registerId, StandardCharsets.UTF_8));

            String uri = uriBuilder.toString();

            // Build request body with RequestInfo
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("RequestInfo", requestInfo);

            log.debug("searchMusterRollStatusForSingleRegister::Calling muster-roll API for register {} period {}: {}",
                    registerId, billingPeriodId, uri);

            Map<String, Object> response = restTemplate.postForObject(uri, requestBody, Map.class);

            // Validate response
            if (response == null) {
                log.debug("searchMusterRollStatusForSingleRegister::Null response from muster roll API");
                return null;
            }

            if (!response.containsKey("musterRolls")) {
                log.debug("searchMusterRollStatusForSingleRegister::Response missing 'musterRolls' key");
                return null;
            }

            Object musterRollsObj = response.get("musterRolls");
            if (!(musterRollsObj instanceof List)) {
                log.error("searchMusterRollStatusForSingleRegister::'musterRolls' is not a List, type: {}",
                        musterRollsObj != null ? musterRollsObj.getClass().getName() : "null");
                return null;
            }

            List<Map<String, Object>> musterRolls = (List<Map<String, Object>>) musterRollsObj;

            if (CollectionUtils.isEmpty(musterRolls)) {
                log.debug("searchMusterRollStatusForSingleRegister::No muster roll found for register {} period {}",
                        registerId, billingPeriodId);
                return null;
            }

            // Extract status from first (should be only) muster roll
            Map<String, Object> musterRoll = musterRolls.get(0);
            Object statusObj = musterRoll.get("musterRollStatus");

            if (statusObj == null) {
                log.warn("searchMusterRollStatusForSingleRegister::Muster roll found but status field is null for register {} period {}",
                        registerId, billingPeriodId);
                return null;
            }

            String status = statusObj.toString();
            log.info("searchMusterRollStatusForSingleRegister::Found muster roll for register {} period {} with status: {}",
                    registerId, billingPeriodId, status);

            return status;

        } catch (Exception e) {
            log.error("searchMusterRollStatusForSingleRegister::Error calling muster-roll API for register {} period {}: {}",
                    registerId, billingPeriodId, e.getMessage(), e);
            // Graceful degradation: Return null on error (will be treated as NOT_CREATED)
            return null;
        }
    }

    /**
     * @deprecated NO LONGER USED - V2 now uses database period_statuses field (event-driven)
     *
     * This method made synchronous API calls to muster-roll service causing scaling issues:
     * - URL length limits with 1000+ registers
     * - Timeout risks
     * - Memory overhead
     *
     * Replaced by: enrichRegistersWithMusterRollStatus() reading from period_statuses JSONB field
     *
     * Kept for reference only. Can be removed in future releases.
     *
     * ---
     *
     * OLD DOCUMENTATION (for reference):
     * Search muster rolls for specific registers and period using V1 API
     *
     * V1 API Structure:
     * - Endpoint: POST /v1/_search
     * - Request Body: RequestInfoWrapper (only RequestInfo)
     * - Search Criteria: Query parameters (tenantId, registerIds, billingPeriodId)
     *
     * Edge cases handled:
     * - Empty register IDs list: returns empty list
     * - Null/blank parameters: validates and returns empty
     * - API call failure: logs error and returns empty (graceful degradation)
     * - Malformed response: logs warning and returns empty
     *
     * @param registerIds List of register IDs
     * @param billingPeriodId Billing period ID
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @return List of muster rolls (empty list if error occurs)
     */
    private List<Map<String, Object>> searchMusterRollsForPeriod(
            List<String> registerIds,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        // Validate input parameters
        if (CollectionUtils.isEmpty(registerIds)) {
            log.warn("Empty register IDs list provided for muster roll search");
            return new ArrayList<>();
        }

        if (StringUtils.isBlank(billingPeriodId)) {
            log.warn("Blank billing period ID provided for muster roll search");
            return new ArrayList<>();
        }

        if (StringUtils.isBlank(tenantId)) {
            log.warn("Blank tenant ID provided for muster roll search");
            return new ArrayList<>();
        }

        try {
            // Build URL to muster-roll V1 search API with query parameters
            // V1 API uses @RequestBody for RequestInfo and @ModelAttribute for search criteria (query params)
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(config.getMusterRollHost())
                      .append(config.getMusterRollV2SearchEndpoint())
                      .append("?tenantId=").append(URLEncoder.encode(tenantId, StandardCharsets.UTF_8))
                      .append("&billingPeriodId=").append(URLEncoder.encode(billingPeriodId, StandardCharsets.UTF_8));

            // Add multiple registerIds as separate query parameters
            for (String registerId : registerIds) {
                uriBuilder.append("&registerIds=").append(URLEncoder.encode(registerId, StandardCharsets.UTF_8));
            }

            String uri = uriBuilder.toString();

            // V1 API expects only RequestInfo in the request body (wrapped in RequestInfoWrapper)
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("RequestInfo", requestInfo);

            log.info("Calling muster-roll V1 search API: {} for {} registers in period {}",
                    uri, registerIds.size(), billingPeriodId);

            Map<String, Object> response = restTemplate.postForObject(uri, requestBody, Map.class);

            // Validate response
            if (response == null) {
                log.warn("Null response received from muster roll search API");
                return new ArrayList<>();
            }

            if (!response.containsKey("musterRolls")) {
                log.warn("Response does not contain 'musterRolls' key");
                return new ArrayList<>();
            }

            Object musterRollsObj = response.get("musterRolls");
            if (!(musterRollsObj instanceof List)) {
                log.error("'musterRolls' field in response is not a List, type: {}",
                        musterRollsObj != null ? musterRollsObj.getClass().getName() : "null");
                return new ArrayList<>();
            }

            List<Map<String, Object>> musterRolls = (List<Map<String, Object>>) musterRollsObj;
            log.info("Muster roll search returned {} muster rolls for {} registers",
                    musterRolls.size(), registerIds.size());

            return musterRolls != null ? musterRolls : new ArrayList<>();

        } catch (Exception e) {
            log.error("Error searching muster rolls for period {}: {}", billingPeriodId, e.getMessage(), e);
            // Don't fail the entire search if muster roll fetch fails
            // Just log the error and return empty list (all registers will get NOT_CREATED status)
            log.warn("Proceeding with register search despite muster roll fetch error - " +
                    "all registers will be marked as NOT_CREATED");
            return new ArrayList<>();
        }
    }

    /**
     * Safely convert Object to Long
     */
    private Long getLongValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                log.warn("Failed to parse long value from string: {}", value);
                return null;
            }
        }
        return null;
    }
}
