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
     * Enrich registers with muster roll status by calling muster-roll V2 search API
     *
     * Edge cases handled:
     * - Empty register list: logs and returns
     * - Registers with null IDs: excluded from muster roll search
     * - Muster roll API failure: all registers get NOT_CREATED status
     * - Missing muster roll status field: treated as NOT_CREATED
     *
     * @param registers List of registers to enrich
     * @param billingPeriodId Billing period ID
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     */
    private void enrichRegistersWithMusterRollStatus(
            List<AttendanceRegister> registers,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        if (CollectionUtils.isEmpty(registers)) {
            log.info("No registers to enrich with muster roll status");
            return;
        }

        // Extract all valid register IDs
        List<String> registerIds = registers.stream()
                .map(AttendanceRegister::getId)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        if (registerIds.isEmpty()) {
            log.warn("No valid register IDs found to fetch muster roll status");
            // Set all registers to NOT_CREATED
            registers.forEach(r -> r.setRegisterPeriodStatus("NOT_CREATED"));
            return;
        }

        log.info("Fetching muster roll status for {} registers in period {}",
                registerIds.size(), billingPeriodId);

        // Call muster-roll V2 search API
        List<Map<String, Object>> musterRolls = searchMusterRollsForPeriod(
                registerIds, billingPeriodId, requestInfo, tenantId);

        // Build map: registerId -> muster roll status
        Map<String, String> registerToStatusMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(musterRolls)) {
            for (Map<String, Object> musterRoll : musterRolls) {
                if (musterRoll == null) {
                    log.warn("Encountered null muster roll entry in response");
                    continue;
                }

                String registerId = (String) musterRoll.get("registerId");
                String status = (String) musterRoll.get("musterRollStatus");

                if (StringUtils.isNotBlank(registerId) && StringUtils.isNotBlank(status)) {
                    registerToStatusMap.put(registerId, status);
                    log.debug("Mapped register {} to muster roll status: {}", registerId, status);
                } else {
                    log.warn("Muster roll entry missing registerId or status - registerId: {}, status: {}",
                            registerId, status);
                }
            }
        }

        log.info("Found {} muster rolls for {} registers",
                registerToStatusMap.size(), registerIds.size());

        // Enrich each register
        for (AttendanceRegister register : registers) {
            if (StringUtils.isBlank(register.getId())) {
                log.warn("Register has null/empty ID, setting status to NOT_CREATED");
                register.setRegisterPeriodStatus("NOT_CREATED");
                continue;
            }

            String musterStatus = registerToStatusMap.get(register.getId());

            if (musterStatus != null) {
                // Muster roll exists, set its status
                register.setRegisterPeriodStatus(musterStatus);
            } else {
                // No muster roll found for this register+period
                register.setRegisterPeriodStatus("NOT_CREATED");
            }

            log.debug("Register {} period status: {}", register.getId(), register.getRegisterPeriodStatus());
        }
    }

    /**
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
