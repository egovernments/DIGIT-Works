package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.service.PeriodAwareMusterRollService;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PeriodAwareMusterRollValidator
 *
 * Validation for V2 period-aware muster roll generation.
 * Ensures all inputs are valid before processing.
 */
@Component
@Slf4j
public class PeriodAwareMusterRollValidator {

    /**
     * Validate inputs for periodic muster roll creation
     *
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @param registerIds List of register IDs
     * @param billingPeriod Billing period details
     */
    public void validatePeriodicMusterRollRequest(RequestInfo requestInfo,
                                                  String tenantId,
                                                  List<String> registerIds,
                                                  PeriodAwareMusterRollService.BillingPeriodDetails billingPeriod) {
        Map<String, String> errorMap = new HashMap<>();

        // Validate RequestInfo
        if (requestInfo == null) {
            errorMap.put("REQUEST_INFO_NULL", "RequestInfo is required for periodic muster roll creation");
        } else {
            if (requestInfo.getUserInfo() == null) {
                errorMap.put("USER_INFO_NULL", "UserInfo is required in RequestInfo");
            } else {
                if (StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
                    errorMap.put("USER_UUID_NULL", "User UUID is required in RequestInfo");
                }
            }
        }

        // Validate TenantId
        if (StringUtils.isBlank(tenantId)) {
            errorMap.put("TENANT_ID_NULL", "TenantId is required for periodic muster roll creation");
        } else {
            // Validate tenant ID format (example: mz.meghalaya)
            if (!tenantId.matches("^[a-z]{2}\\.[a-z]+$")) {
                log.warn("TenantId format may be invalid: {}. Expected format: xx.xxxxx", tenantId);
            }
        }

        // Validate Register IDs
        if (CollectionUtils.isEmpty(registerIds)) {
            errorMap.put("REGISTER_IDS_EMPTY", "At least one register ID is required for muster roll creation");
        } else {
            // Check for null or blank register IDs
            for (int i = 0; i < registerIds.size(); i++) {
                String registerId = registerIds.get(i);
                if (StringUtils.isBlank(registerId)) {
                    errorMap.put("REGISTER_ID_BLANK", "Register ID at index " + i + " is blank or null");
                }
            }

            // Check for duplicates
            long distinctCount = registerIds.stream().distinct().count();
            if (distinctCount < registerIds.size()) {
                errorMap.put("REGISTER_IDS_DUPLICATE", "Duplicate register IDs found in request");
            }

            // Check for reasonable limit
            if (registerIds.size() > 500) {
                log.warn("Large number of register IDs ({}). This may take significant time.", registerIds.size());
            }
        }

        // Validate Billing Period
        if (billingPeriod == null) {
            errorMap.put("BILLING_PERIOD_NULL", "BillingPeriod details are required for periodic muster roll creation");
        } else {
            if (StringUtils.isBlank(billingPeriod.getId())) {
                errorMap.put("BILLING_PERIOD_ID_NULL", "BillingPeriod ID is required");
            }
            if (billingPeriod.getPeriodNumber() == null || billingPeriod.getPeriodNumber() <= 0) {
                errorMap.put("PERIOD_NUMBER_INVALID", "Period number must be a positive integer. Current: " + billingPeriod.getPeriodNumber());
            }
            if (billingPeriod.getPeriodStartDate() == null) {
                errorMap.put("PERIOD_START_DATE_NULL", "Period start date is required");
            }
            if (billingPeriod.getPeriodEndDate() == null) {
                errorMap.put("PERIOD_END_DATE_NULL", "Period end date is required");
            }

            // Validate date logic
            if (billingPeriod.getPeriodStartDate() != null && billingPeriod.getPeriodEndDate() != null) {
                if (billingPeriod.getPeriodStartDate() >= billingPeriod.getPeriodEndDate()) {
                    errorMap.put("PERIOD_DATES_INVALID",
                            "Period start date must be before end date. Start: " + billingPeriod.getPeriodStartDate() +
                            ", End: " + billingPeriod.getPeriodEndDate());
                }

                // Check if period is not too far in the past or future
                long currentTime = System.currentTimeMillis();
                long oneYearMs = 365L * 24 * 60 * 60 * 1000;

                if (billingPeriod.getPeriodStartDate() < currentTime - oneYearMs) {
                    log.warn("Period start date is more than one year in the past: {}", billingPeriod.getPeriodStartDate());
                }
                if (billingPeriod.getPeriodEndDate() > currentTime + oneYearMs) {
                    log.warn("Period end date is more than one year in the future: {}", billingPeriod.getPeriodEndDate());
                }

                // Validate reasonable period duration (1 day to 90 days)
                long periodDurationMs = billingPeriod.getPeriodEndDate() - billingPeriod.getPeriodStartDate();
                long oneDayMs = 24L * 60 * 60 * 1000;
                long ninetyDaysMs = 90L * 24 * 60 * 60 * 1000;

                if (periodDurationMs < oneDayMs) {
                    errorMap.put("PERIOD_DURATION_TOO_SHORT",
                            "Period duration must be at least 1 day. Current duration: " + (periodDurationMs / oneDayMs) + " days");
                }
                if (periodDurationMs > ninetyDaysMs) {
                    log.warn("Period duration is very long: {} days", periodDurationMs / oneDayMs);
                }
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("PeriodAwareMusterRollValidator::validatePeriodicMusterRollRequest - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("PeriodAwareMusterRollValidator::validatePeriodicMusterRollRequest - All validations passed for {} registers, period {}",
                registerIds.size(), billingPeriod.getPeriodNumber());
    }

    /**
     * Validate attendance register for period-aware processing
     *
     * @param registerId Register ID
     * @param billingPeriod Billing period
     */
    public void validateRegisterForPeriod(String registerId,
                                         PeriodAwareMusterRollService.BillingPeriodDetails billingPeriod) {
        Map<String, String> errorMap = new HashMap<>();

        if (StringUtils.isBlank(registerId)) {
            errorMap.put("REGISTER_ID_NULL", "Register ID is required");
        }

        if (billingPeriod == null) {
            errorMap.put("BILLING_PERIOD_NULL", "Billing period is required");
        }

        if (!errorMap.isEmpty()) {
            log.error("PeriodAwareMusterRollValidator::validateRegisterForPeriod - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }
    }

    /**
     * Validate date intersection result
     *
     * @param registerId Register ID
     * @param billingPeriod Billing period
     * @param hasIntersection Whether intersection exists
     */
    public void validateDateIntersection(String registerId,
                                         PeriodAwareMusterRollService.BillingPeriodDetails billingPeriod,
                                         boolean hasIntersection) {
        if (!hasIntersection) {
            log.warn("No date intersection found between register {} and billing period {}",
                    registerId, billingPeriod.getPeriodNumber());
            throw new CustomException("NO_DATE_INTERSECTION",
                    "Register " + registerId + " does not overlap with billing period " + billingPeriod.getPeriodNumber() +
                            ". Cannot create muster roll without date overlap.");
        }
    }

    /**
     * Validate API controller request body
     *
     * @param requestBody Request body map
     */
    public void validatePeriodicCreateApiRequest(Map<String, Object> requestBody) {
        Map<String, String> errorMap = new HashMap<>();

        if (requestBody == null || requestBody.isEmpty()) {
            errorMap.put("REQUEST_BODY_EMPTY", "Request body is required");
            throw new CustomException(errorMap);
        }

        if (!requestBody.containsKey("RequestInfo")) {
            errorMap.put("REQUEST_INFO_MISSING", "RequestInfo is required in request body");
        }

        if (!requestBody.containsKey("tenantId")) {
            errorMap.put("TENANT_ID_MISSING", "tenantId is required in request body");
        }

        if (!requestBody.containsKey("registerIds")) {
            errorMap.put("REGISTER_IDS_MISSING", "registerIds is required in request body");
        } else {
            Object registerIdsObj = requestBody.get("registerIds");
            if (!(registerIdsObj instanceof List)) {
                errorMap.put("REGISTER_IDS_INVALID_TYPE", "registerIds must be a list");
            }
        }

        if (!requestBody.containsKey("billingPeriod")) {
            errorMap.put("BILLING_PERIOD_MISSING", "billingPeriod is required in request body");
        } else {
            Object billingPeriodObj = requestBody.get("billingPeriod");
            if (!(billingPeriodObj instanceof Map)) {
                errorMap.put("BILLING_PERIOD_INVALID_TYPE", "billingPeriod must be an object");
            } else {
                Map<String, Object> billingPeriodMap = (Map<String, Object>) billingPeriodObj;
                if (!billingPeriodMap.containsKey("id")) {
                    errorMap.put("BILLING_PERIOD_ID_MISSING", "billingPeriod.id is required");
                }
                if (!billingPeriodMap.containsKey("periodNumber")) {
                    errorMap.put("BILLING_PERIOD_NUMBER_MISSING", "billingPeriod.periodNumber is required");
                }
                if (!billingPeriodMap.containsKey("periodStartDate")) {
                    errorMap.put("BILLING_PERIOD_START_DATE_MISSING", "billingPeriod.periodStartDate is required");
                }
                if (!billingPeriodMap.containsKey("periodEndDate")) {
                    errorMap.put("BILLING_PERIOD_END_DATE_MISSING", "billingPeriod.periodEndDate is required");
                }
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("PeriodAwareMusterRollValidator::validatePeriodicCreateApiRequest - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("PeriodAwareMusterRollValidator::validatePeriodicCreateApiRequest - API request validation passed");
    }
}
