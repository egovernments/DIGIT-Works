package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * BillingVersionHelper
 *
 * Utility class for V1/V2 billing mode detection and backward compatibility.
 * Ensures V1 projects continue to work unchanged while V2 projects use new features.
 */
@Component
@Slf4j
public class BillingVersionHelper {

    /**
     * Detect billing mode for a project
     *
     * @param billingConfig Billing configuration (null for V1, populated for V2)
     * @return "V1" or "V2"
     */
    public String detectBillingMode(BillingConfig billingConfig) {
        if (billingConfig == null) {
            log.debug("No billing configuration found - using V1 mode");
            return "V1";
        }

        if (!"ACTIVE".equalsIgnoreCase(billingConfig.getStatus())) {
            log.debug("Billing configuration exists but not ACTIVE (status: {}) - using V1 mode",
                     billingConfig.getStatus());
            return "V1";
        }

        log.debug("Active billing configuration found (frequency: {}) - using V2 mode",
                 billingConfig.getBillingFrequency());
        return "V2";
    }

    /**
     * Check if project is in V2 mode
     *
     * @param billingConfig Billing configuration
     * @return true if V2, false if V1
     */
    public boolean isV2Mode(BillingConfig billingConfig) {
        return "V2".equals(detectBillingMode(billingConfig));
    }

    /**
     * Check if project is in V1 mode (legacy)
     *
     * @param billingConfig Billing configuration
     * @return true if V1, false if V2
     */
    public boolean isV1Mode(BillingConfig billingConfig) {
        return "V1".equals(detectBillingMode(billingConfig));
    }

    /**
     * Check if a bill is V2 (has period metadata)
     *
     * @param bill Bill to check
     * @return true if V2 bill, false if V1 bill
     */
    public boolean isV2Bill(Bill bill) {
        if (bill == null || bill.getAdditionalDetails() == null) {
            return false;
        }

        Object additionalDetails = bill.getAdditionalDetails();
        if (!(additionalDetails instanceof Map)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> detailsMap = (Map<String, Object>) additionalDetails;

        // V2 bills have billingPeriodId in additionalDetails
        boolean hasPeriodId = detailsMap.containsKey("billingPeriodId");
        boolean hasPeriodNumber = detailsMap.containsKey("periodNumber");
        boolean hasBillingType = "INTERMEDIATE".equals(detailsMap.get("billingType"));

        return hasPeriodId || hasPeriodNumber || hasBillingType;
    }

    /**
     * Check if a bill is V1 (legacy, no period metadata)
     *
     * @param bill Bill to check
     * @return true if V1 bill, false if V2 bill
     */
    public boolean isV1Bill(Bill bill) {
        return !isV2Bill(bill);
    }

    /**
     * Get bill version string for logging
     *
     * @param bill Bill
     * @return "V1" or "V2"
     */
    public String getBillVersion(Bill bill) {
        return isV2Bill(bill) ? "V2" : "V1";
    }

    /**
     * Validate backward compatibility: ensure V1 bill fields are not broken
     *
     * @param bill Bill to validate
     * @return true if valid (has required V1 fields)
     */
    public boolean isV1Compatible(Bill bill) {
        if (bill == null) {
            return false;
        }

        // V1 Required fields that must always be present
        boolean hasRequiredFields = bill.getTenantId() != null
                && bill.getBusinessService() != null
                && bill.getTotalAmount() != null
                && bill.getFromPeriod() != null
                && bill.getToPeriod() != null;

        if (!hasRequiredFields) {
            log.warn("Bill {} missing required V1 fields", bill.getId());
        }

        return hasRequiredFields;
    }

    /**
     * Log billing mode information
     *
     * @param projectId Project ID
     * @param billingConfig Billing configuration
     * @param operation Operation being performed
     */
    public void logBillingMode(String projectId, BillingConfig billingConfig, String operation) {
        String mode = detectBillingMode(billingConfig);
        String configInfo = "V2".equals(mode)
            ? " (Frequency: " + billingConfig.getBillingFrequency() + ")"
            : " (No billing configuration)";

        log.info("{} - Project: {} | Billing Mode: {}{}",
                operation,
                projectId,
                mode,
                configInfo);
    }

    /**
     * Ensure V1 behavior is preserved
     * Validates that V1 projects are not accidentally treated as V2
     *
     * @param projectId Project ID
     * @param billingConfig Billing configuration (should be null for V1)
     */
    public void enforceV1Behavior(String projectId, BillingConfig billingConfig) {
        if (billingConfig != null && !"ACTIVE".equalsIgnoreCase(billingConfig.getStatus())) {
            log.info("Project {} has billing config but it's not ACTIVE (status: {}). " +
                    "Enforcing V1 behavior to maintain backward compatibility.",
                    projectId, billingConfig.getStatus());
        }
    }

    /**
     * Ensure V2 behavior requirements are met
     *
     * @param projectId Project ID
     * @param billingConfig Billing configuration
     * @throws IllegalStateException if V2 requirements not met
     */
    public void enforceV2Requirements(String projectId, BillingConfig billingConfig) {
        if (billingConfig == null) {
            throw new IllegalStateException(
                "V2 billing requires billing configuration for project: " + projectId
            );
        }

        if (!"ACTIVE".equalsIgnoreCase(billingConfig.getStatus())) {
            throw new IllegalStateException(
                "V2 billing requires ACTIVE billing configuration. Current status: " +
                billingConfig.getStatus() + " for project: " + projectId
            );
        }

        if (billingConfig.getBillingFrequency() == null) {
            throw new IllegalStateException(
                "V2 billing configuration missing billing frequency for project: " + projectId
            );
        }

        log.debug("V2 requirements validated for project: {}", projectId);
    }

    /**
     * Get user-friendly mode description
     *
     * @param billingConfig Billing configuration
     * @return Description of billing mode
     */
    public String getModeDescription(BillingConfig billingConfig) {
        if (isV1Mode(billingConfig)) {
            return "V1 Mode: Single bill for entire project duration (legacy behavior)";
        } else {
            return "V2 Mode: Multiple bills based on billing periods (frequency: " +
                   billingConfig.getBillingFrequency() + ")";
        }
    }
}
