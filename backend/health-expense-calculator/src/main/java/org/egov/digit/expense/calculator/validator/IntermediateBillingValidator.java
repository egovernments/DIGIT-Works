package org.egov.digit.expense.calculator.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.web.models.BillingConfig;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.egov.digit.expense.calculator.web.models.Criteria;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IntermediateBillingValidator
 *
 * Comprehensive validation for V2 intermediate billing operations.
 * Validates all inputs, business rules, and edge cases.
 */
@Component
@Slf4j
public class IntermediateBillingValidator {

    /**
     * Validate inputs for processIntermediateBilling
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param project Project details
     * @param billingConfig Billing configuration
     */
    public void validateIntermediateBillingRequest(RequestInfo requestInfo,
                                                   Criteria criteria,
                                                   Project project,
                                                   BillingConfig billingConfig) {
        Map<String, String> errorMap = new HashMap<>();

        // Validate RequestInfo
        if (requestInfo == null) {
            errorMap.put("REQUEST_INFO_NULL", "RequestInfo is required for intermediate billing");
        } else {
            if (requestInfo.getUserInfo() == null) {
                errorMap.put("USER_INFO_NULL", "UserInfo is required in RequestInfo");
            } else {
                if (StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
                    errorMap.put("USER_UUID_NULL", "User UUID is required in RequestInfo");
                }
            }
        }

        // Validate Criteria
        if (criteria == null) {
            errorMap.put("CRITERIA_NULL", "Criteria is required for intermediate billing");
        } else {
            if (StringUtils.isBlank(criteria.getTenantId())) {
                errorMap.put("TENANT_ID_NULL", "TenantId is required in criteria");
            }
            if (StringUtils.isBlank(criteria.getReferenceId())) {
                errorMap.put("REFERENCE_ID_NULL", "ReferenceId (Project ID) is required in criteria");
            }
            if (StringUtils.isBlank(criteria.getLocalityCode())) {
                errorMap.put("LOCALITY_CODE_NULL", "LocalityCode is required in criteria");
            }
        }

        // Validate Project
        if (project == null) {
            errorMap.put("PROJECT_NULL", "Project details are required for intermediate billing");
        } else {
            if (StringUtils.isBlank(project.getId())) {
                errorMap.put("PROJECT_ID_NULL", "Project ID is required");
            }
            if (StringUtils.isBlank(project.getTenantId())) {
                errorMap.put("PROJECT_TENANT_ID_NULL", "Project tenantId is required");
            }
            if (project.getStartDate() == null || project.getEndDate() == null) {
                errorMap.put("PROJECT_DATES_NULL", "Project start and end dates are required");
            } else if (project.getStartDate() >= project.getEndDate()) {
                errorMap.put("PROJECT_DATES_INVALID", "Project start date must be before end date");
            }
        }

        // Validate BillingConfig
        if (billingConfig == null) {
            errorMap.put("BILLING_CONFIG_NULL", "BillingConfig is required for intermediate billing");
        } else {
            if (StringUtils.isBlank(billingConfig.getId())) {
                errorMap.put("BILLING_CONFIG_ID_NULL", "BillingConfig ID is required");
            }
            if (StringUtils.isBlank(billingConfig.getTenantId())) {
                errorMap.put("BILLING_CONFIG_TENANT_ID_NULL", "BillingConfig tenantId is required");
            }
            if (StringUtils.isBlank(billingConfig.getCampaignNumber())) {
                errorMap.put("BILLING_CONFIG_CAMPAIGN_NUMBER_NULL", "BillingConfig campaignNumber is required");
            }
            if (billingConfig.getBillingFrequency() == null) {
                errorMap.put("BILLING_FREQUENCY_NULL", "BillingFrequency is required");
            }
            if (billingConfig.getProjectStartDate() == null || billingConfig.getProjectEndDate() == null) {
                errorMap.put("BILLING_CONFIG_DATES_NULL", "Campaign dates in BillingConfig are required");
            }
            if (!"ACTIVE".equalsIgnoreCase(billingConfig.getStatus())) {
                errorMap.put("BILLING_CONFIG_NOT_ACTIVE", "BillingConfig must be ACTIVE for intermediate billing. Current status: " + billingConfig.getStatus());
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validateIntermediateBillingRequest - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("IntermediateBillingValidator::validateIntermediateBillingRequest - All validations passed");
    }

    /**
     * Validate billing periods before processing
     *
     * @param periods List of billing periods
     * @param campaignNumber Campaign number
     */
    public void validateBillingPeriods(List<BillingPeriod> periods, String campaignNumber) {
        Map<String, String> errorMap = new HashMap<>();

        if (CollectionUtils.isEmpty(periods)) {
            errorMap.put("NO_BILLING_PERIODS", "No billing periods found for campaign: " + campaignNumber);
            throw new CustomException(errorMap);
        }

        // Validate each period
        for (BillingPeriod period : periods) {
            if (period == null) {
                errorMap.put("NULL_BILLING_PERIOD", "Billing period cannot be null");
                continue;
            }

            if (StringUtils.isBlank(period.getId())) {
                errorMap.put("PERIOD_ID_NULL", "Billing period ID is required");
            }
            if (period.getPeriodNumber() == null || period.getPeriodNumber() <= 0) {
                errorMap.put("PERIOD_NUMBER_INVALID", "Period number must be positive: " + period.getPeriodNumber());
            }
            if (period.getPeriodStartDate() == null || period.getPeriodEndDate() == null) {
                errorMap.put("PERIOD_DATES_NULL", "Period start and end dates are required for period: " + period.getPeriodNumber());
            } else if (period.getPeriodStartDate() >= period.getPeriodEndDate()) {
                errorMap.put("PERIOD_DATES_INVALID", "Period start date must be before end date for period: " + period.getPeriodNumber());
            }
            if (StringUtils.isBlank(period.getTenantId())) {
                errorMap.put("PERIOD_TENANT_ID_NULL", "Period tenantId is required");
            }
            if (StringUtils.isBlank(period.getCampaignNumber())) {
                errorMap.put("PERIOD_CAMPAIGN_NUMBER_NULL", "Period campaignNumber is required");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validateBillingPeriods - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("IntermediateBillingValidator::validateBillingPeriods - Validated {} periods", periods.size());
    }

    /**
     * Validate billing period for processing
     * Used during iteration through periods
     *
     * @param period Billing period
     */
    public void validatePeriodForProcessing(BillingPeriod period) {
        Map<String, String> errorMap = new HashMap<>();

        if (period == null) {
            errorMap.put("BILLING_PERIOD_NULL", "Billing period is required");
            throw new CustomException(errorMap);
        }

        // Period must be in PENDING status to be processed
        if (!"PENDING".equalsIgnoreCase(period.getStatus()) &&
            !"PROCESSING".equalsIgnoreCase(period.getStatus())) {
            // Not an error, just skip (already billed or completed)
            log.info("Period {} is in {} status, skipping processing",
                    period.getPeriodNumber(), period.getStatus());
            return;
        }

        // Additional runtime validations
        long currentTime = System.currentTimeMillis();
//        if (period.getPeriodStartDate() > currentTime) {
//            errorMap.put("PERIOD_NOT_STARTED",
//                    "Period " + period.getPeriodNumber() + " has not started yet. Start date: " + period.getPeriodStartDate());
//        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validatePeriodForProcessing - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }
    }

    /**
     * Validate sequential billing: Previous period must be billed before current period
     * This ensures bills are generated in order (period 1 → period 2 → period 3)
     *
     * @param allPeriods All billing periods (sorted by period number)
     * @param currentPeriod Current period being processed
     */
    public void validateSequentialBilling(List<BillingPeriod> allPeriods, BillingPeriod currentPeriod) {
        Map<String, String> errorMap = new HashMap<>();

        if (currentPeriod == null) {
            errorMap.put("CURRENT_PERIOD_NULL", "Current period is required for sequential validation");
            throw new CustomException(errorMap);
        }

        // Period 1 can always be processed
        if (currentPeriod.getPeriodNumber() == 1) {
            log.info("validateSequentialBilling::Period 1 - no previous period to check");
            return;
        }

        // Find previous period (periodNumber - 1)
        int previousPeriodNumber = currentPeriod.getPeriodNumber() - 1;
        BillingPeriod previousPeriod = allPeriods.stream()
                .filter(p -> p.getPeriodNumber() != null && p.getPeriodNumber() == previousPeriodNumber)
                .findFirst()
                .orElse(null);

        if (previousPeriod == null) {
            errorMap.put("PREVIOUS_PERIOD_NOT_FOUND",
                    "Cannot process period " + currentPeriod.getPeriodNumber() +
                            ": Previous period " + previousPeriodNumber + " not found");
            throw new CustomException(errorMap);
        }

        // Check if previous period is BILLED
        if (!"BILLED".equalsIgnoreCase(previousPeriod.getStatus())) {
            errorMap.put("PREVIOUS_PERIOD_NOT_BILLED",
                    String.format("Cannot process period %d: Previous period %d is not billed yet (status: %s). " +
                            "Bills must be generated sequentially.",
                            currentPeriod.getPeriodNumber(),
                            previousPeriod.getPeriodNumber(),
                            previousPeriod.getStatus()));
            throw new CustomException(errorMap);
        }

        log.info("validateSequentialBilling::Period {} validated - previous period {} is BILLED",
                currentPeriod.getPeriodNumber(), previousPeriod.getPeriodNumber());
    }

    /**
     * Validate that ALL overlapping registers have APPROVED muster rolls for the period
     * This is the V2 equivalent of V1's register approval validation
     *
     * V1: Checks register.reviewStatus = "APPROVED"
     * V2: Checks each register has an approved muster roll for this specific period
     *
     * CRITICAL V2 VALIDATION:
     * - For N registers in a period, there MUST be exactly N muster rolls
     * - ALL N muster rolls MUST be in APPROVED status
     * - Example: If 48 registers exist but only 47 muster rolls found → FAIL
     *
     * @param periodRegisters List of registers overlapping with the period
     * @param musterRolls List of muster rolls for the period
     * @param period Billing period
     */
    public void validateAllRegisterMustersApproved(List<org.egov.works.services.common.models.attendance.AttendanceRegister> periodRegisters,
                                                   List<org.egov.works.services.common.models.musterroll.MusterRoll> musterRolls,
                                                   BillingPeriod period) {
        Map<String, String> errorMap = new HashMap<>();

        if (CollectionUtils.isEmpty(periodRegisters)) {
            log.info("validateAllRegisterMustersApproved::No registers to validate for period {}", period.getPeriodNumber());
            return;
        }

        int registerCount = periodRegisters.size();
        int musterRollCount = musterRolls != null ? musterRolls.size() : 0;

        log.info("validateAllRegisterMustersApproved::Period {} - Registers: {}, Muster Rolls: {}",
                period.getPeriodNumber(), registerCount, musterRollCount);

        // CRITICAL: Count mismatch validation
        // Every register MUST have exactly one muster roll
        if (musterRollCount == 0) {
            errorMap.put("NO_MUSTER_ROLLS_FOR_REGISTERS",
                    String.format("Period %d has %d register(s) but NO muster rolls. " +
                            "All registers must have approved muster rolls before billing.",
                            period.getPeriodNumber(), registerCount));
            throw new CustomException(errorMap);
        }

        if (musterRollCount < registerCount) {
            errorMap.put("MUSTER_ROLL_COUNT_MISMATCH",
                    String.format("Period %d: Register-to-Muster count mismatch! " +
                            "Found %d register(s) but only %d muster roll(s). " +
                            "Each register MUST have exactly 1 muster roll. " +
                            "Missing: %d muster roll(s). " +
                            "Proximity supervisors must create muster rolls for ALL registers before billing.",
                            period.getPeriodNumber(), registerCount, musterRollCount, (registerCount - musterRollCount)));
            throw new CustomException(errorMap);
        }

        if (musterRollCount > registerCount) {
            log.warn("Period {}: Found MORE muster rolls ({}) than registers ({}). " +
                    "This might indicate duplicate muster rolls or incorrect period filtering.",
                    period.getPeriodNumber(), musterRollCount, registerCount);
        }

        // Create a map of registerId → musterRoll for quick lookup
        Map<String, org.egov.works.services.common.models.musterroll.MusterRoll> registerToMusterMap = new HashMap<>();
        for (org.egov.works.services.common.models.musterroll.MusterRoll musterRoll : musterRolls) {
            if (musterRoll != null && StringUtils.isNotBlank(musterRoll.getRegisterId())) {
                registerToMusterMap.put(musterRoll.getRegisterId(), musterRoll);
            }
        }

        // Check each register has an approved muster roll
        List<String> registersWithoutMuster = new ArrayList<>();
        List<String> registersWithUnapprovedMuster = new ArrayList<>();

        for (org.egov.works.services.common.models.attendance.AttendanceRegister register : periodRegisters) {
            String registerId = register.getId();

            // Check if muster roll exists for this register
            if (!registerToMusterMap.containsKey(registerId)) {
                registersWithoutMuster.add(registerId);
                continue;
            }

            // Check if muster roll is approved
            org.egov.works.services.common.models.musterroll.MusterRoll musterRoll = registerToMusterMap.get(registerId);
            String musterStatus = musterRoll.getMusterRollStatus();

            if (!"APPROVED".equalsIgnoreCase(musterStatus)) {
                registersWithUnapprovedMuster.add(String.format("%s (status: %s)", registerId, musterStatus));
            }
        }

        // Build detailed error messages
        if (!registersWithoutMuster.isEmpty()) {
            String sampleRegisters = registersWithoutMuster.size() <= 5 ?
                    String.join(", ", registersWithoutMuster) :
                    String.join(", ", registersWithoutMuster.subList(0, 5)) + "... and " + (registersWithoutMuster.size() - 5) + " more";

            errorMap.put("REGISTERS_WITHOUT_MUSTER",
                    String.format("Period %d: CRITICAL VALIDATION FAILURE - %d out of %d register(s) do NOT have muster rolls! " +
                            "Register IDs missing muster rolls: [%s]. " +
                            "Each register MUST have exactly 1 muster roll. " +
                            "Proximity supervisors must create muster rolls for these registers before billing can proceed.",
                            period.getPeriodNumber(),
                            registersWithoutMuster.size(),
                            registerCount,
                            sampleRegisters));
        }

        if (!registersWithUnapprovedMuster.isEmpty()) {
            String sampleMusters = registersWithUnapprovedMuster.size() <= 5 ?
                    String.join(", ", registersWithUnapprovedMuster) :
                    String.join(", ", registersWithUnapprovedMuster.subList(0, 5)) + "... and " + (registersWithUnapprovedMuster.size() - 5) + " more";

            errorMap.put("REGISTERS_WITH_UNAPPROVED_MUSTER",
                    String.format("Period %d: CRITICAL VALIDATION FAILURE - %d out of %d register(s) have UNAPPROVED muster rolls! " +
                            "Register IDs with unapproved musters: [%s]. " +
                            "ALL muster rolls must be in APPROVED status before billing. " +
                            "Proximity supervisors must approve these muster rolls first.",
                            period.getPeriodNumber(),
                            registersWithUnapprovedMuster.size(),
                            registerCount,
                            sampleMusters));
        }

        if (!errorMap.isEmpty()) {
            log.error("validateAllRegisterMustersApproved::Period {} validation failed: {}", period.getPeriodNumber(), errorMap);
            throw new CustomException(errorMap);
        }

        log.info("validateAllRegisterMustersApproved::Period {} - ✓ VALIDATION PASSED - " +
                "All {} register(s) have exactly {} approved muster roll(s). Ready for billing.",
                period.getPeriodNumber(), registerCount, musterRollCount);
    }

    /**
     * Validate muster rolls before bill generation
     *
     * @param musterRolls List of muster rolls
     * @param period Billing period
     */
    public void validateMusterRollsForBilling(List<org.egov.works.services.common.models.musterroll.MusterRoll> musterRolls,
                                              BillingPeriod period) {
        Map<String, String> errorMap = new HashMap<>();

        if (CollectionUtils.isEmpty(musterRolls)) {
            errorMap.put("NO_MUSTER_ROLLS",
                    "No muster rolls found for period " + period.getPeriodNumber() + ". Cannot generate bill without muster rolls.");
            throw new CustomException(errorMap);
        }

        // Validate each muster roll
        for (org.egov.works.services.common.models.musterroll.MusterRoll musterRoll : musterRolls) {
            if (musterRoll == null) {
                errorMap.put("NULL_MUSTER_ROLL", "Muster roll cannot be null");
                continue;
            }

            if (StringUtils.isBlank(musterRoll.getId())) {
                errorMap.put("MUSTER_ROLL_ID_NULL", "Muster roll ID is required");
            }
            if (StringUtils.isBlank(musterRoll.getRegisterId())) {
                errorMap.put("MUSTER_ROLL_REGISTER_ID_NULL", "Muster roll registerId is required");
            }
            if (musterRoll.getStartDate() == null || musterRoll.getEndDate() == null) {
                errorMap.put("MUSTER_ROLL_DATES_NULL", "Muster roll dates are required");
            }
            if (CollectionUtils.isEmpty(musterRoll.getIndividualEntries())) {
                errorMap.put("MUSTER_ROLL_NO_ENTRIES",
                        "Muster roll " + musterRoll.getId() + " has no individual entries. Cannot generate bill.");
            }

            // V2: Validate period linkage - Note: billingPeriodId not available in common MusterRoll model
            // Period linkage is tracked in the muster-roll service's own model
        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validateMusterRollsForBilling - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("IntermediateBillingValidator::validateMusterRollsForBilling - Validated {} muster rolls for period {}",
                musterRolls.size(), period.getPeriodNumber());
    }

    /**
     * Validate register IDs for period-aware muster roll service call
     *
     * @param registerIds List of register IDs
     * @param period Billing period
     */
    public void validateRegisterIds(List<String> registerIds, BillingPeriod period) {
        Map<String, String> errorMap = new HashMap<>();

        if (CollectionUtils.isEmpty(registerIds)) {
            errorMap.put("NO_REGISTER_IDS",
                    "No register IDs found for period " + period.getPeriodNumber() + ". Cannot generate muster rolls.");
            throw new CustomException(errorMap);
        }

        // Check for null or blank register IDs
        for (int i = 0; i < registerIds.size(); i++) {
            String registerId = registerIds.get(i);
            if (StringUtils.isBlank(registerId)) {
                errorMap.put("REGISTER_ID_BLANK", "Register ID at index " + i + " is blank");
            }
        }

        // Check for reasonable limit (prevent accidental mass processing)
        if (registerIds.size() > 1000) {
            log.warn("Large number of registers ({}) for period {}. This might take significant time.",
                    registerIds.size(), period.getPeriodNumber());
        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validateRegisterIds - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("IntermediateBillingValidator::validateRegisterIds - Validated {} register IDs for period {}",
                registerIds.size(), period.getPeriodNumber());
    }

    /**
     * Validate bill before submission
     *
     * @param bill Bill to validate
     * @param period Billing period
     */
    public void validateBillBeforeSubmission(org.egov.digit.expense.calculator.web.models.Bill bill,
                                            BillingPeriod period) {
        Map<String, String> errorMap = new HashMap<>();

        if (bill == null) {
            errorMap.put("BILL_NULL", "Bill is required for submission");
            throw new CustomException(errorMap);
        }

        if (StringUtils.isBlank(bill.getTenantId())) {
            errorMap.put("BILL_TENANT_ID_NULL", "Bill tenantId is required");
        }
        if (StringUtils.isBlank(bill.getBusinessService())) {
            errorMap.put("BILL_BUSINESS_SERVICE_NULL", "Bill businessService is required");
        }
        if (CollectionUtils.isEmpty(bill.getBillDetails())) {
            errorMap.put("BILL_NO_DETAILS",
                    "Bill has no bill details for period " + period.getPeriodNumber() + ". Cannot submit empty bill.");
        }
//        if (bill.getTotalAmount() == null || bill.getTotalAmount().doubleValue() <= 0) {
//            errorMap.put("BILL_AMOUNT_INVALID",
//                    "Bill total amount must be positive for period " + period.getPeriodNumber() +
//                            ". Current amount: " + bill.getTotalAmount());
//        }
        if (bill.getFromPeriod() == null || bill.getToPeriod() == null) {
            errorMap.put("BILL_PERIOD_DATES_NULL", "Bill period dates are required");
        } else if (bill.getFromPeriod() > bill.getToPeriod()) {
            errorMap.put("BILL_PERIOD_DATES_INVALID", "Bill fromPeriod must be before toPeriod");
        }

        // V2: Validate period linkage in additionalDetails
        if (bill.getAdditionalDetails() != null) {
            Map<String, Object> additionalDetails = (Map<String, Object>) bill.getAdditionalDetails();
            if (!additionalDetails.containsKey("billingPeriodId")) {
                errorMap.put("BILL_MISSING_PERIOD_ID", "Bill additionalDetails must contain billingPeriodId for V2");
            }
            if (!additionalDetails.containsKey("periodNumber")) {
                errorMap.put("BILL_MISSING_PERIOD_NUMBER", "Bill additionalDetails must contain periodNumber for V2");
            }
        }

        if (!errorMap.isEmpty()) {
            log.error("IntermediateBillingValidator::validateBillBeforeSubmission - Validation failed: {}", errorMap);
            throw new CustomException(errorMap);
        }

        log.info("IntermediateBillingValidator::validateBillBeforeSubmission - Bill validated for period {}",
                period.getPeriodNumber());
    }
}
