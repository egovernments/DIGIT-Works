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
                                          ObjectMapper mapper,
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

            Map<String, Object> searchCriteria = new HashMap<>();
            searchCriteria.put("tenantId", tenantId);
            searchCriteria.put("ids", Collections.singletonList(billingPeriodId));

            requestBody.put("billingPeriodSearchCriteria", searchCriteria);

            log.info("Fetching billing period {} from expense-calculator", billingPeriodId);

            Map<String, Object> response = restTemplate.postForObject(uri, requestBody, Map.class);

            if (response != null && response.containsKey("billingPeriods")) {
                List<Map<String, Object>> periods = (List<Map<String, Object>>) response.get("billingPeriods");
                if (!periods.isEmpty()) {
                    return periods.get(0);
                }
            }

            log.warn("Billing period {} not found in response", billingPeriodId);
            return null;

        } catch (Exception e) {
            log.error("Error fetching billing period {}: {}", billingPeriodId, e.getMessage(), e);
            throw new CustomException("BILLING_PERIOD_FETCH_ERROR",
                    "Failed to fetch billing period: " + e.getMessage());
        }
    }

    /**
     * Filter registers that overlap with billing period dates
     *
     * A register overlaps if:
     * - register.startDate <= period.endDate AND
     * - register.endDate >= period.startDate
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

        return registers.stream()
                .filter(register -> {
                    if (register.getStartDate() == null || register.getEndDate() == null) {
                        log.warn("Register {} has null dates, excluding from period filter",
                                register.getId());
                        return false;
                    }

                    // Convert BigDecimal to long for comparison
                    long registerStart = register.getStartDate().longValue();
                    long registerEnd = register.getEndDate().longValue();

                    // Check overlap: register.start <= period.end && register.end >= period.start
                    boolean overlaps = registerStart <= periodEndDate && registerEnd >= periodStartDate;

                    if (!overlaps) {
                        log.debug("Register {} ({} to {}) does not overlap with period ({} to {})",
                                register.getId(), registerStart, registerEnd,
                                periodStartDate, periodEndDate);
                    }

                    return overlaps;
                })
                .collect(Collectors.toList());
    }

    /**
     * Enrich registers with muster roll status by calling muster-roll V2 search API
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

        // Extract all register IDs
        List<String> registerIds = registers.stream()
                .map(AttendanceRegister::getId)
                .collect(Collectors.toList());

        log.info("Fetching muster roll status for {} registers in period {}",
                registerIds.size(), billingPeriodId);

        // Call muster-roll V2 search API
        List<Map<String, Object>> musterRolls = searchMusterRollsForPeriod(
                registerIds, billingPeriodId, requestInfo, tenantId);

        // Build map: registerId -> muster roll status
        Map<String, String> registerToStatusMap = new HashMap<>();

        for (Map<String, Object> musterRoll : musterRolls) {
            String registerId = (String) musterRoll.get("registerId");
            String status = (String) musterRoll.get("musterRollStatus");

            if (StringUtils.isNotBlank(registerId) && StringUtils.isNotBlank(status)) {
                registerToStatusMap.put(registerId, status);
            }
        }

        log.info("Found {} muster rolls for {} registers",
                registerToStatusMap.size(), registerIds.size());

        // Enrich each register
        for (AttendanceRegister register : registers) {
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
     * Search muster rolls for specific registers and period
     *
     * @param registerIds List of register IDs
     * @param billingPeriodId Billing period ID
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @return List of muster rolls
     */
    private List<Map<String, Object>> searchMusterRollsForPeriod(
            List<String> registerIds,
            String billingPeriodId,
            RequestInfo requestInfo,
            String tenantId) {

        try {
            // Build URL to muster-roll V2 search API
            String uri = config.getMusterRollHost() + config.getMusterRollV2SearchEndpoint();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("RequestInfo", requestInfo);

            Map<String, Object> searchCriteria = new HashMap<>();
            searchCriteria.put("tenantId", tenantId);
            searchCriteria.put("registerIds", registerIds);
            searchCriteria.put("billingPeriodId", billingPeriodId);

            requestBody.put("musterRollCriteria", searchCriteria);

            log.info("Calling muster-roll V2 search for {} registers in period {}",
                    registerIds.size(), billingPeriodId);

            Map<String, Object> response = restTemplate.postForObject(uri, requestBody, Map.class);

            if (response != null && response.containsKey("musterRolls")) {
                List<Map<String, Object>> musterRolls = (List<Map<String, Object>>) response.get("musterRolls");
                log.info("Muster roll search returned {} muster rolls", musterRolls.size());
                return musterRolls;
            }

            log.warn("Muster roll search returned empty response");
            return new ArrayList<>();

        } catch (Exception e) {
            log.error("Error searching muster rolls: {}", e.getMessage(), e);
            // Don't fail the entire search if muster roll fetch fails
            // Just log the error and return empty list (all registers will get NOT_CREATED status)
            log.warn("Proceeding with register search despite muster roll fetch error");
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
