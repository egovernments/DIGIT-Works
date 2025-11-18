package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.kafka.ExpenseCalculatorProducer;
import org.egov.digit.expense.calculator.repository.ExpenseCalculatorRepository;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.validator.ExpenseCalculatorServiceValidator;
import org.egov.digit.expense.calculator.validator.IntermediateBillingValidator;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

/**
 * IntermediateBillingService
 *
 * Handles HCM Payments V2 intermediate billing logic.
 * This service processes bills on a per-period basis for projects with billing configurations.
 *
 * V2 Workflow:
 * - Muster rolls are ALREADY CREATED by proximity supervisors before billing
 * - This service SEARCHES for existing approved muster rolls (does NOT create them)
 * - Bills can only be generated when ALL muster rolls for a period are APPROVED
 *
 * Key Responsibilities:
 * - Validate sequential billing (Period N requires Period N-1 billed first)
 * - Fetch registers overlapping with each period
 * - SEARCH for existing period-specific muster rolls
 * - Validate ALL muster rolls are APPROVED
 * - Generate bills per period
 * - Track bill status at project+period level
 * - Prevent duplicate bill generation
 *
 * Backward Compatible: Projects without billing config use V1 flow (ExpenseCalculatorService)
 */
@Service
@Slf4j
public class IntermediateBillingService {

    private final BillingConfigurationService billingConfigurationService;
    private final WageSeekerBillGeneratorService wageSeekerBillGeneratorService;
    private final ExpenseCalculatorRepository expenseCalculatorRepository;
    private final ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator;
    private final ExpenseCalculatorConfiguration config;
    private final AttendanceUtil attendanceUtil;
    private final BillUtils billUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final MdmsUtils mdmsUtils;
    private final ProjectUtil projectUtil;
    private final IntermediateBillingValidator intermediateBillingValidator;
    private final CommonUtil commonUtil;
    private final ExpenseCalculatorProducer expenseCalculatorProducer;

    private static final String REVIEW_STATUS_APPROVED = "APPROVED";
    private static final String REVIEW_STATUS_PENDING_FOR_APPROVAL = "PENDINGFORAPPROVAL";

    @Autowired
    public IntermediateBillingService(BillingConfigurationService billingConfigurationService,
                                      WageSeekerBillGeneratorService wageSeekerBillGeneratorService,
                                      ExpenseCalculatorRepository expenseCalculatorRepository,
                                      ExpenseCalculatorServiceValidator expenseCalculatorServiceValidator,
                                      ExpenseCalculatorConfiguration config,
                                      AttendanceUtil attendanceUtil,
                                      BillUtils billUtils,
                                      RestTemplate restTemplate,
                                      ObjectMapper mapper,
                                      MdmsUtils mdmsUtils,
                                      ProjectUtil projectUtil,
                                      IntermediateBillingValidator intermediateBillingValidator,
                                      CommonUtil commonUtil,
                                      ExpenseCalculatorProducer expenseCalculatorProducer) {
        this.billingConfigurationService = billingConfigurationService;
        this.wageSeekerBillGeneratorService = wageSeekerBillGeneratorService;
        this.expenseCalculatorRepository = expenseCalculatorRepository;
        this.expenseCalculatorServiceValidator = expenseCalculatorServiceValidator;
        this.config = config;
        this.attendanceUtil = attendanceUtil;
        this.billUtils = billUtils;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.mdmsUtils = mdmsUtils;
        this.projectUtil = projectUtil;
        this.intermediateBillingValidator = intermediateBillingValidator;
        this.commonUtil = commonUtil;
        this.expenseCalculatorProducer = expenseCalculatorProducer;
    }

    /**
     * Process intermediate billing for V2 projects
     *
     * V2 Workflow:
     * - Proximity supervisors create and approve muster rolls BEFORE billing
     * - District supervisor selects project + locality + period in UI
     * - System searches for existing APPROVED muster rolls
     * - Bill is generated only if ALL muster rolls are approved
     *
     * Steps:
     * 1. Validate request and extract campaign/project details
     * 2. For selected period (UI-driven) or all periods (batch mode):
     *    a. Validate sequential billing (Period N requires Period N-1 billed)
     *    b. Check if bill already generated for project+period (duplicate prevention)
     *    c. Fetch registers overlapping with period dates
     *    d. SEARCH for existing muster rolls (DO NOT CREATE)
     *    e. Validate ALL muster rolls for period are APPROVED
     *    f. Generate bill for period
     *    g. Submit bill to expense service
     *    h. Track bill status in bill_gen_status table
     * 3. Return list of generated bills
     *
     * @param requestInfo Request info with user details
     * @param criteria Billing criteria (includes billingPeriodId for UI-driven mode)
     * @param project Project details
     * @param isDistrictLevel Whether project is district level
     * @param billingConfig Billing configuration
     * @return List of generated bills (one per period)
     */
    public List<Bill> processIntermediateBilling(RequestInfo requestInfo,
                                                 Criteria criteria,
                                                 Project project,
                                                 boolean isDistrictLevel,
                                                 BillingConfig billingConfig) {
        log.info("IntermediateBillingService::processIntermediateBilling - Starting V2 bill generation for project {}",
                project.getId());

        // Step 0: Validate all inputs
        intermediateBillingValidator.validateIntermediateBillingRequest(requestInfo, criteria, project, billingConfig);

        // Extract project ID for status tracking
        String projectId = project.getProjectHierarchy() != null ?
                project.getProjectHierarchy() : project.getId();

        List<Bill> generatedBills = new ArrayList<>();

        // Step 1: Extract campaign number from project reference ID
        String campaignNumber = extractCampaignNumber(project);

        // Step 2: Check if aggregate mode requested
        if (BILLING_PERIOD_AGGREGATE.equalsIgnoreCase(criteria.getBillingPeriodId())) {
            log.info("Aggregate mode detected for project {}", projectId);
            return processAggregateBilling(requestInfo, criteria, project, isDistrictLevel,
                    billingConfig, projectId, campaignNumber);
        }

        // Step 3: Check if specific period requested (UI-driven mode)
        if (criteria.getBillingPeriodId() != null && !criteria.getBillingPeriodId().isEmpty()) {
            log.info("UI-driven mode: Processing specific period {} for project {}",
                    criteria.getBillingPeriodId(), projectId);

            // Fetch the specific period
            BillingPeriod selectedPeriod = billingConfigurationService.getBillingPeriodById(
                    criteria.getBillingPeriodId(),
                    criteria.getTenantId()
            );

            if (selectedPeriod == null) {
                throw new CustomException("PERIOD_NOT_FOUND",
                        "Billing period not found: " + criteria.getBillingPeriodId());
            }

            // Process only the selected period
            Bill bill = processSinglePeriod(requestInfo, criteria, selectedPeriod, project,
                    isDistrictLevel, projectId, campaignNumber);

            if (bill != null) {
                generatedBills.add(bill);
            }

            return generatedBills;
        }

        // Step 3: Check if batch processing is enabled
        if (!config.isBillingV2BatchProcessingEnabled()) {
            String errorMsg = "Batch processing is disabled for V2 intermediate billing. " +
                    "Please provide 'billingPeriodId' in the request to bill a specific period. " +
                    "Batch processing requires manual approval of all muster rolls and is disabled " +
                    "to prevent automatic billing of unapproved periods. " +
                    "To enable batch processing, set 'billing.v2.batch.processing.enabled=true' " +
                    "in application.properties (not recommended for production).";
            log.error(errorMsg);
            throw new CustomException("BATCH_PROCESSING_DISABLED", errorMsg);
        }

        log.warn("Batch processing mode enabled - Processing all unbilled periods for project {}. " +
                "This should only be used in controlled environments with pre-approved data.", projectId);

        // Step 4: Batch mode - Get all billing periods for this campaign
        List<BillingPeriod> periods = billingConfigurationService.getBillingPeriods(
                campaignNumber,
                criteria.getTenantId()
        );

        // Validate billing periods
        intermediateBillingValidator.validateBillingPeriods(periods, campaignNumber);

        log.info("Batch mode: Found {} billing periods for campaign {}", periods.size(), campaignNumber);

        // Step 4: Process each unbilled period
        for (BillingPeriod period : periods) {
            log.info("Processing billing period {}: {} to {}",
                    period.getPeriodNumber(),
                    formatDate(period.getPeriodStartDate()),
                    formatDate(period.getPeriodEndDate())
            );

            try {
                // Step 4a: Check if bill already generated for this project+period (duplicate prevention)
                // Uses comprehensive check: status table + expense service API + report completion
                if (isBillGeneratedForProjectPeriod(requestInfo, projectId, period, criteria.getTenantId())) {
                    log.info("Bill already FULLY COMPLETED for project {} period {}, skipping",
                            projectId, period.getPeriodNumber());
                    continue;
                }

                // Step 4b: Process this period
                Bill bill = processSinglePeriod(requestInfo, criteria, period, project,
                        isDistrictLevel, projectId, campaignNumber);

                if (bill != null) {
                    generatedBills.add(bill);
                }

            } catch (CustomException e) {
                log.error("IntermediateBillingService::processIntermediateBilling - Error processing period {}: {}",
                        period.getPeriodNumber(), e.getMessage(), e);
                // Record failure but continue with other periods
                recordPeriodBillingFailure(period, projectId, e);
            } catch (Exception e) {
                log.error("IntermediateBillingService::processIntermediateBilling - Unexpected error processing period {}: {}",
                        period.getPeriodNumber(), e.getMessage(), e);
                // Record failure but continue with other periods
                recordPeriodBillingFailure(period, projectId,
                        new CustomException("BILL_GENERATION_ERROR", e.getMessage()));
            }
        }

        if (generatedBills.isEmpty()) {
            log.warn("IntermediateBillingService::processIntermediateBilling - No bills generated for any period");
            throw new CustomException("NO_BILLS_GENERATED",
                    "Failed to generate bills for all periods. Please check logs for details.");
        }

        log.info("V2 bill generation completed. Generated {} bills", generatedBills.size());
        return generatedBills;
    }

    /**
     * Process a single billing period for a project
     * Extracted common logic for both UI-driven and batch modes
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param period Billing period to process
     * @param project Project details
     * @param isDistrictLevel Whether project is district level
     * @param projectId Project reference ID
     * @param campaignNumber Campaign number
     * @return Generated bill, or null if skipped
     */
    private Bill processSinglePeriod(RequestInfo requestInfo, Criteria criteria,
                                    BillingPeriod period, Project project,
                                    boolean isDistrictLevel, String projectId,
                                    String campaignNumber) {
        log.info("Processing period {} for project {}", period.getPeriodNumber(), projectId);

        // Step 1: Validate period before processing
        intermediateBillingValidator.validatePeriodForProcessing(period);

        // Step 1.1: Validate sequential billing (previous periods must be billed first)
        // Uses comprehensive check: validates actual bill existence and report completion
        validateSequentialBillingForProject(requestInfo, projectId, period, criteria.getTenantId());

        // Step 2: Fetch registers overlapping with period dates
        List<AttendanceRegister> periodRegisters = getRegistersForPeriod(
                requestInfo, criteria, isDistrictLevel, period
        );

        if (periodRegisters.isEmpty()) {
            log.info("No registers found for period {}, skipping", period.getPeriodNumber());
            return null;
        }

        log.info("Found {} registers for period {}", periodRegisters.size(), period.getPeriodNumber());

        // Step 3: Get register IDs
        List<String> registerIds = periodRegisters.stream()
                .map(AttendanceRegister::getId)
                .collect(Collectors.toList());

        // Step 3.1: Validate register IDs
        intermediateBillingValidator.validateRegisterIds(registerIds, period);

        // Step 4: SEARCH for existing period-specific muster rolls (DO NOT CREATE)
        // In V2, muster rolls are created by proximity supervisors BEFORE billing
        List<MusterRoll> musterRolls = searchExistingMusterRollsForPeriod(
                requestInfo,
                criteria.getTenantId(),
                registerIds,
                period
        );

        if (CollectionUtils.isEmpty(musterRolls)) {
            log.warn("No muster rolls found for period {}. Proximity supervisors must create muster rolls before billing.",
                    period.getPeriodNumber());
            throw new CustomException("NO_MUSTER_ROLLS_FOR_PERIOD",
                    "No muster rolls found for period " + period.getPeriodNumber() + ". " +
                    "Muster rolls must be created by proximity supervisors before bill generation.");
        }

        log.info("Found {} existing muster rolls for period {}", musterRolls.size(), period.getPeriodNumber());

        // Step 5: V2 CRITICAL VALIDATION - Ensure ALL muster rolls for this period are APPROVED
        // This replaces V1's register approval check
        intermediateBillingValidator.validateAllRegisterMustersApproved(periodRegisters, musterRolls, period);

        // Step 5.1: Additional validation for muster rolls before bill generation
        intermediateBillingValidator.validateMusterRollsForBilling(musterRolls, period);

        // Step 6: Fetch billing config for enrichment
        BillingConfig billingConfig = billingConfigurationService.getBillingConfigByCampaignNumber(
                campaignNumber, criteria.getTenantId()
        );

        // Step 7: Generate bill for this period
        Bill periodBill = generatePeriodBill(
                requestInfo, criteria, period, musterRolls, project, periodRegisters.size(), billingConfig
        );

        // Step 7.1: Validate bill before submission
        intermediateBillingValidator.validateBillBeforeSubmission(periodBill, period);

        // Step 8: Submit bill to expense service
        Bill submittedBill = submitPeriodBill(requestInfo, periodBill, period, projectId,
                periodRegisters.size(), musterRolls.size());

        return submittedBill;
    }

    /**
     * Check if bill already generated and FULLY COMPLETED for a specific project+period combination
     * Used for duplicate prevention in V2
     *
     * This is a COMPREHENSIVE check that validates:
     * 1. Status table has SUCCESSFUL entry
     * 2. Actual bill exists in expense service (via API call)
     * 3. Bill status is ACTIVE
     * 4. Report generation is COMPLETED
     *
     * Following microservices architecture principles by calling Expense service API
     * instead of directly querying its database table.
     *
     * @param requestInfo Request info for API calls
     * @param projectId Project reference ID
     * @param period Billing period
     * @param tenantId Tenant ID
     * @return true if bill is fully generated and completed, false otherwise
     */
    private boolean isBillGeneratedForProjectPeriod(RequestInfo requestInfo, String projectId,
                                                    BillingPeriod period, String tenantId) {
        // Step 1: Quick check - is there a SUCCESSFUL status entry? (fast local DB check)
        boolean statusExists = expenseCalculatorRepository.isBillGeneratedForProjectPeriod(
            projectId,
            period.getId()
        );

        if (!statusExists) {
            log.info("No status entry found for project {} period {}", projectId, period.getPeriodNumber());
            return false; // No status entry, definitely not billed
        }

        // Step 2: Comprehensive check - verify actual bill and report completion via API
        boolean isFullyCompleted = billUtils.isCompletedBillGeneratedForPeriod(
            requestInfo,
            projectId,
            period.getId(),
            tenantId
        );

        if (isFullyCompleted) {
            log.info("Bill FULLY COMPLETED for project {} period {} (status + bill + report verified)",
                    projectId, period.getPeriodNumber());
        } else {
            log.warn("Status shows SUCCESSFUL but bill/report not completed for project {} period {}. " +
                    "This could indicate a partial failure. Allowing re-generation.",
                    projectId, period.getPeriodNumber());
        }

        return isFullyCompleted;
    }

    /**
     * Validate sequential billing for a project
     * Ensures periods are billed in order - Period N cannot be billed unless all periods 1 to N-1 are FULLY COMPLETED
     *
     * Uses comprehensive check via Expense service API to verify:
     * - Bill exists with ACTIVE status
     * - Report generation is COMPLETED
     *
     * @param requestInfo Request info for API calls
     * @param projectId Project reference ID
     * @param period Period to be billed
     * @param tenantId Tenant ID
     * @throws CustomException if sequential validation fails
     */
    private void validateSequentialBillingForProject(RequestInfo requestInfo, String projectId,
                                                     BillingPeriod period, String tenantId) {
        Integer currentPeriodNumber = period.getPeriodNumber();

        // Period 1 can always be billed (no previous periods)
        if (currentPeriodNumber == null || currentPeriodNumber <= 1) {
            log.info("Period {} is first period, no sequential validation needed", currentPeriodNumber);
            return;
        }

        log.info("Validating sequential billing for project {} period {} (using comprehensive check)",
                projectId, currentPeriodNumber);

        // Get all FULLY COMPLETED period numbers for this project
        // This calls Expense service API to verify bills and reports are truly completed
        List<Integer> completedPeriods = billUtils.getCompletedPeriodNumbersForProject(
            requestInfo,
            projectId,
            tenantId
        );

        log.info("Project {} has {} FULLY COMPLETED periods: {}", projectId, completedPeriods.size(), completedPeriods);

        // Check if all previous periods (1 to N-1) are fully completed
        for (int i = 1; i < currentPeriodNumber; i++) {
            if (!completedPeriods.contains(i)) {
                String errorMsg = String.format(
                    "Cannot bill period %d for project %s. Period %d must be FULLY COMPLETED first " +
                    "(bill generated AND report completed). " +
                    "Periods must be billed sequentially. Currently completed periods: %s",
                    currentPeriodNumber,
                    projectId,
                    i,
                    completedPeriods.isEmpty() ? "None" : completedPeriods.toString()
                );
                log.error(errorMsg);
                throw new CustomException("SEQUENTIAL_BILLING_VIOLATION", errorMsg);
            }
        }

        log.info("Sequential billing validation passed for project {} period {} - all previous periods completed",
            projectId, currentPeriodNumber);
    }

    /**
     * Get attendance registers overlapping with billing period
     *
     * Uses existing V1 batch fetching logic, then filters by period overlap
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param isDistrictLevel Whether to fetch children registers
     * @param period Billing period
     * @return List of registers overlapping with period
     */
    private List<AttendanceRegister> getRegistersForPeriod(RequestInfo requestInfo,
                                                            Criteria criteria,
                                                            boolean isDistrictLevel,
                                                            BillingPeriod period) {
        List<AttendanceRegister> allRegisters = new ArrayList<>();
        Integer offset = 0;

        // Use existing V1 batch fetching logic
        do {
            List<AttendanceRegister> batchRegisters = attendanceUtil.fetchAttendanceRegister(
                    criteria.getReferenceId(),     // Project ID
                    criteria.getTenantId(),        // Tenant ID
                    requestInfo,                   // Request Info
                    criteria.getLocalityCode(),    // Boundary Code
                    isDistrictLevel,               // Fetch children flag
                    offset                         // Batch offset
            );

            if (batchRegisters.isEmpty()) break;

            // NEW V2: Filter by period dates using overlap detection
            List<AttendanceRegister> periodFilteredRegisters = batchRegisters.stream()
                    .filter(register -> isRegisterOverlappingWithPeriod(register, period))
                    .collect(Collectors.toList());

            allRegisters.addAll(periodFilteredRegisters);
            offset += config.getRegisterBatchSize();

        } while (true);

        log.info("Filtered {} registers overlapping with period {} from total fetched",
                allRegisters.size(), period.getPeriodNumber());

        return allRegisters;
    }

    /**
     * Check if register dates overlap with period dates
     * Uses standard interval intersection logic
     *
     * @param register Attendance register
     * @param period Billing period
     * @return true if overlap exists, false otherwise
     */
    private boolean isRegisterOverlappingWithPeriod(AttendanceRegister register, BillingPeriod period) {
        long registerStart = register.getStartDate().longValue();
        long registerEnd = register.getEndDate().longValue();
        long periodStart = period.getPeriodStartDate();
        long periodEnd = period.getPeriodEndDate();

        // Check for overlap: !(registerEnd < periodStart || registerStart > periodEnd)
        boolean hasOverlap = !(registerEnd < periodStart || registerStart > periodEnd);

        if (!hasOverlap) {
            log.debug("Register {} ({} to {}) does not overlap with period {} ({} to {})",
                    register.getId(),
                    formatDate(registerStart), formatDate(registerEnd),
                    period.getPeriodNumber(),
                    formatDate(periodStart), formatDate(periodEnd));
        }

        return hasOverlap;
    }

    /**
     * Search for existing muster rolls for a billing period
     * V2 Workflow: Muster rolls are ALREADY CREATED by proximity supervisors
     * This method only SEARCHES for existing muster rolls, does NOT create new ones
     *
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @param registerIds List of register IDs to search for
     * @param period Billing period
     * @return List of existing muster rolls for the period
     */
    private List<MusterRoll> searchExistingMusterRollsForPeriod(RequestInfo requestInfo,
                                                                String tenantId,
                                                                List<String> registerIds,
                                                                BillingPeriod period) {
        log.info("Searching for existing muster rolls for {} registers in period {} using V1 API with billingPeriodId filter",
                registerIds.size(), period.getPeriodNumber());

        // Build search request for muster-roll V1 search API
        // V1 /_search endpoint now supports billingPeriodId in query params (MusterRollSearchCriteria)
        StringBuilder uriBuilder = new StringBuilder(config.getMusterRollHost() + config.getMusterRollEndV1Point());
        uriBuilder.append("?tenantId=").append(tenantId);
        uriBuilder.append("&billingPeriodId=").append(period.getId());

        // Add registerIds as multiple query params
        for (String registerId : registerIds) {
            uriBuilder.append("&registerIds=").append(registerId);
        }

        String uri = uriBuilder.toString();
        log.debug("Muster roll search URI: {}", uri);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("RequestInfo", requestInfo);

        try {
            // Call muster-roll V1 search service with query params
            Map<String, Object> response = restTemplate.postForObject(
                    uri,
                    requestBody,
                    Map.class
            );

            if (response == null || !response.containsKey("musterRolls")) {
                log.info("No muster rolls found for period {} - this is normal for first-time billing",
                        period.getPeriodNumber());
                return new ArrayList<>();
            }

            // Convert response to MusterRoll list
            List<Map<String, Object>> musterRollMaps = (List<Map<String, Object>>) response.get("musterRolls");

            if (musterRollMaps == null || musterRollMaps.isEmpty()) {
                log.info("Empty muster roll list for period {} - will create new muster rolls",
                        period.getPeriodNumber());
                return new ArrayList<>();
            }

            List<MusterRoll> musterRolls = musterRollMaps.stream()
                    .map(map -> mapper.convertValue(map, MusterRoll.class))
                    .collect(Collectors.toList());

            log.info("Found {} existing muster rolls for period {} (server-side filtered by billingPeriodId)",
                    musterRolls.size(), period.getPeriodNumber());

            return musterRolls;

        } catch (Exception e) {
            log.error("Error searching for muster rolls using V1 API: {}", e.getMessage(), e);
            throw new CustomException("MUSTER_ROLL_SEARCH_ERROR",
                    "Failed to search for muster rolls: " + e.getMessage());
        }
    }

    /**
     * Generate bill for a specific billing period
     *
     * Uses existing V1 bill generation logic, but overrides dates and adds V2 metadata
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param period Billing period
     * @param musterRolls Muster rolls for the period
     * @param project Project details
     * @param registerCount Number of registers in this period
     * @return Generated bill for the period
     */
    private Bill generatePeriodBill(RequestInfo requestInfo, Criteria criteria,
                                    BillingPeriod period, List<MusterRoll> musterRolls,
                                    Project project, int registerCount, BillingConfig billingConfig) {
        log.info("Generating bill for period {}", period.getPeriodNumber());

        // Create empty bill (V1 logic)
        Bill bill = Bill.builder()
                .totalAmount(BigDecimal.ZERO)
                .billDetails(new ArrayList<>())
                .build();

        // Fetch MDMS data for worker skills (V1 logic)
        String parentProjectId = project.getProjectHierarchy() != null ?
                project.getProjectHierarchy().split("\\.")[0] : project.getId();
        List<WorkerMdms> workerMdms = fetchMDMSDataForWorker(requestInfo, criteria.getTenantId(), parentProjectId);

        // Generate bill details from muster rolls (V1 logic preserved)
        wageSeekerBillGeneratorService.createWageSeekerBillsHealth(
                requestInfo, musterRolls, workerMdms, bill
        );

        // Enrich with V1 bill fields
        enrichBill(bill, criteria, project);

        // NEW V2: Override dates with period boundaries
        bill.setFromPeriod(period.getPeriodStartDate());
        bill.setToPeriod(period.getPeriodEndDate());

        // Add V2 metadata to additionalDetails
        Map<String, Object> additionalDetails = bill.getAdditionalDetails() != null ?
                (Map<String, Object>) bill.getAdditionalDetails() : new HashMap<>();

        // V2 Metadata
        additionalDetails.put("billingType", BILLING_TYPE_INTERMEDIATE);
        additionalDetails.put("billingPeriodId", period.getId());
        additionalDetails.put("periodNumber", period.getPeriodNumber());
        additionalDetails.put("periodStartDate", period.getPeriodStartDate());
        additionalDetails.put("periodEndDate", period.getPeriodEndDate());
        additionalDetails.put("billingFrequency", billingConfig.getBillingFrequency() != null ?
                billingConfig.getBillingFrequency().toString() : "UNKNOWN");
        additionalDetails.put("v2Enhanced", true);

        // Bill counts (required for report generation)
        additionalDetails.put(NO_OF_REGISTERS, registerCount);
        additionalDetails.put(NO_OF_BILL_DETAILS, bill.getBillDetails().size());

        // Campaign number (for MDMS lookup)
        String campaignNumber = extractCampaignNumber(project);
        if (campaignNumber != null) {
            additionalDetails.put("campaignNumber", campaignNumber);
        }

        bill.setAdditionalDetails(additionalDetails);

        log.info("Generated bill for period {} with total amount: {}",
                period.getPeriodNumber(), bill.getTotalAmount());

        return bill;
    }

    /**
     * Submit bill to expense service and track status
     * CORRECTED: Uses project-specific status tracking in bill_gen_status table
     *
     * @param requestInfo Request info
     * @param bill Bill to submit
     * @param period Billing period
     * @param projectId Project reference ID
     * @param registerCount Number of registers
     * @param musterRollCount Number of muster rolls
     * @return Submitted bill from expense service
     */
    private Bill submitPeriodBill(RequestInfo requestInfo, Bill bill, BillingPeriod period,
                                  String projectId, int registerCount, int musterRollCount) {
        log.info("Submitting bill for project {} period {} (bill has {} details)",
                projectId, period.getPeriodNumber(),
                bill.getBillDetails() != null ? bill.getBillDetails().size() : 0);

        // Create bill status entry BEFORE submission
        String billStatusId = UUID.randomUUID().toString();
        log.info("Creating bill status entry with ID: {} for project {} period {}",
                billStatusId, projectId, period.getPeriodNumber());

        expenseCalculatorRepository.createBillStatusV2(
                billStatusId,
                bill.getTenantId(),
                projectId,                   // Project-specific tracking
                BILLING_TYPE_INTERMEDIATE,   // billing_type
                period.getId(),              // period_id
                period.getPeriodNumber(),    // period_number
                INITIATED_STATUS,
                null,
                System.currentTimeMillis(),  // processing_start_time
                registerCount
        );

        // Submit bill using V1 workflow
        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();

        try {
            log.info("Calling expense service to create bill for project {} period {}", projectId, period.getPeriodNumber());
            BillResponse response = billUtils.postCreateBill(requestInfo, bill, workflow);
            log.info("Received response from expense service for project {} period {}", projectId, period.getPeriodNumber());

            // Check if submission successful
            if (response != null && SUCCESSFUL_CONSTANT.equalsIgnoreCase(
                    response.getResponseInfo().getStatus())) {

                if (!CollectionUtils.isEmpty(response.getBills())) {
                    Bill submittedBill = response.getBills().get(0);

                    log.info("Bill successfully created in expense service with ID: {}, billNumber: {}, totalAmount: {}, billDetails: {}",
                            submittedBill.getId(),
                            submittedBill.getBillNumber(),
                            submittedBill.getTotalAmount(),
                            submittedBill.getBillDetails() != null ? submittedBill.getBillDetails().size() : 0);

                    // Update status to SUCCESSFUL with bill details
                    expenseCalculatorRepository.updateBillStatusWithDetails(
                            billStatusId,
                            SUCCESSFUL_STATUS,
                            null,
                            System.currentTimeMillis(),  // processing_end_time
                            submittedBill.getId(),       // billId
                            submittedBill.getBillNumber() != null ? submittedBill.getBillNumber() : "N/A",
                            submittedBill.getTotalAmount() != null ? submittedBill.getTotalAmount().toString() : "0",
                            musterRollCount
                    );

                    log.info("Successfully submitted bill for project {} period {} with ID: {} and updated status entry {}",
                            projectId, period.getPeriodNumber(), submittedBill.getId(), billStatusId);

                    // V2: Trigger report generation (same as V1 flow)
                    if (config.isReportGenerationAuto()) {
                        log.info("Triggering report generation for V2 bill: {} (billNumber: {}) period: {} with {} billDetails",
                                submittedBill.getId(),
                                submittedBill.getBillNumber(),
                                period.getPeriodNumber(),
                                submittedBill.getBillDetails().size());

                        ReportGenerationTrigger reportGenerationTrigger = ReportGenerationTrigger.builder()
                                .requestInfo(requestInfo)
                                .billId(submittedBill.getId())
                                .tenantId(submittedBill.getTenantId())
                                .createdTime(System.currentTimeMillis())
                                .numberOfBillDetails(submittedBill.getBillDetails().size())
                                .build();

                        expenseCalculatorProducer.push(config.getReportGenerationTriggerTopic(), reportGenerationTrigger);
                        log.info("Report generation trigger successfully pushed to Kafka for V2 bill: {} with {} expected billDetails",
                                submittedBill.getId(), submittedBill.getBillDetails().size());
                    }

                    // V2 Enhancement: Update register reviewStatus after successful period bill generation
                    // - If LAST period → reviewStatus = "APPROVED" (ready for aggregate billing)
                    // - If OTHER period → reviewStatus = "PENDINGFORAPPROVAL" (bill generated, more periods pending)
                    // This is done AFTER report trigger to ensure full workflow is initiated
                    // IMPORTANT: Use bill.getReferenceId() (actual project ID) not projectId (which could be hierarchy)
                    boolean isLast = isLastPeriod(period, bill.getTenantId());
                    updateRegisterReviewStatusAfterPeriodBill(requestInfo, bill.getReferenceId(), period, bill.getTenantId(), isLast);

                    return submittedBill;
                } else {
                    log.error("Bill submission returned empty bills list for project {} period {}",
                            projectId, period.getPeriodNumber());
                    expenseCalculatorRepository.updateBillStatusV2(
                            billStatusId,
                            FAILED_STATUS,
                            "Empty bills list in response",
                            System.currentTimeMillis()
                    );
                    return null;
                }
            } else {
                String errorMsg = "Bill submission failed for project " + projectId +
                        " period " + period.getPeriodNumber();
                log.error(errorMsg);
                expenseCalculatorRepository.updateBillStatusV2(
                        billStatusId,
                        FAILED_STATUS,
                        errorMsg,
                        System.currentTimeMillis()
                );
                return null;
            }
        } catch (Exception e) {
            log.error("Exception during bill submission for project {} period {}: {}",
                    projectId, period.getPeriodNumber(), e.getMessage(), e);
            expenseCalculatorRepository.updateBillStatusV2(
                    billStatusId,
                    FAILED_STATUS,
                    "Exception: " + e.getMessage(),
                    System.currentTimeMillis()
            );
            throw e;
        }
    }

    /**
     * Record period billing failure for specific project
     * CORRECTED: Uses project-specific tracking, not config table updates
     *
     * @param period Billing period
     * @param projectId Project reference ID
     * @param exception Exception that occurred
     */
    private void recordPeriodBillingFailure(BillingPeriod period, String projectId, CustomException exception) {
        String billStatusId = UUID.randomUUID().toString();
        expenseCalculatorRepository.createBillStatusV2(
                billStatusId,
                period.getTenantId(),
                projectId,                      // Project-specific tracking
                BILLING_TYPE_INTERMEDIATE,
                period.getId(),
                period.getPeriodNumber(),
                FAILED_STATUS,
                exception.getCode() + ": " + exception.getMessage(),
                System.currentTimeMillis(),
                0
        );

        log.info("Recorded failure for project {} period {}", projectId, period.getPeriodNumber());
    }

    /**
     * Fetch MDMS data for worker skills
     * Reuses existing V1 logic
     */
    private List<WorkerMdms> fetchMDMSDataForWorker(RequestInfo requestInfo, String tenantId, String campaignId) {
        log.info("Fetch worker MDMS");
        Object mdmsData = mdmsUtils.getWorkerRateFromMDMSV2(requestInfo, tenantId, campaignId);
        List<Object> workerListJson = commonUtil.readJSONPathValue(mdmsData, JSON_PATH_FOR_HCM);
        List<WorkerMdms> workerMdmsList = new ArrayList<>();
        for (Object obj : workerListJson) {
            WorkerMdms workerMdms = mapper.convertValue(obj, WorkerMdms.class);
            workerMdmsList.add(workerMdms);
        }
        if (workerMdmsList.isEmpty()) {
            throw new CustomException("RATES_NOT_CONFIGURED_IN_MDMS", "rates are not configured in mdms for campaign id:: " + campaignId);
        }
        return workerMdmsList;
    }

    /**
     * Enrich bill with common fields
     * Reuses existing V1 logic
     */
    private void enrichBill(Bill bill, Criteria criteria, Project project) {
        bill.setTenantId(criteria.getTenantId());
        bill.setBusinessService(config.getWageBusinessService());
        bill.setStatus(BILL_STATUS_ACTIVE);
        bill.setReferenceId(project.getProjectHierarchy() != null ?
                project.getProjectHierarchy() : project.getId());
        bill.setLocalityCode(criteria.getLocalityCode());
        bill.setBillDate(System.currentTimeMillis());

        // Set payer from project
        bill.setPayer(Party.builder()
                .identifier(UUID.randomUUID().toString())
                .tenantId(criteria.getTenantId())
                .type("ORG")
                .build());
    }

    /**
     * Format timestamp to readable date
     */
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp));
    }

    /**
     * Extract campaign number from project reference ID
     * The campaign number is stored in project.referenceID field
     *
     * @param project Project details
     * @return Campaign number if found, null otherwise
     */
    private String extractCampaignNumber(Project project) {
        if (project == null) {
            log.warn("Project is null, cannot extract campaign number");
            return null;
        }

        // Extract from project.referenceID (primary source for campaign number)
        if (StringUtils.isNotBlank(project.getReferenceID())) {
            log.info("Extracted campaign number from project.referenceID: {}", project.getReferenceID());
            return project.getReferenceID();
        }

        // Fallback: Try to get from project hierarchy (first part before dot)
        if (project.getProjectHierarchy() != null && !project.getProjectHierarchy().isEmpty()) {
            String hierarchy = project.getProjectHierarchy();
            if (hierarchy.contains(".")) {
                String campaignNum = hierarchy.split("\\.")[0];
                log.info("Extracted campaign number from project hierarchy: {}", campaignNum);
                return campaignNum;
            }
            log.info("Using project hierarchy as campaign number: {}", hierarchy);
            return hierarchy;
        }

        // Last fallback to project ID
        log.warn("No referenceID or hierarchy found, using project ID as campaign number: {}", project.getId());
        return project.getId();
    }

    // ==================== AGGREGATE BILLING METHODS ====================

    /**
     * Process aggregate billing by fetching ALL muster rolls from ALL periods
     * and passing them to the existing bill generation logic
     *
     * Key Insight: The existing createWageSeekerBillsHealth() already aggregates
     * multiple muster rolls. We just pass muster rolls from ALL periods instead of one period.
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param project Project details
     * @param isDistrictLevel Whether project is district level
     * @param billingConfig Billing configuration
     * @param projectId Project reference ID
     * @param campaignNumber Campaign number
     * @return List containing single aggregate bill
     */
    private List<Bill> processAggregateBilling(RequestInfo requestInfo,
                                               Criteria criteria,
                                               Project project,
                                               boolean isDistrictLevel,
                                               BillingConfig billingConfig,
                                               String projectId,
                                               String campaignNumber) {
        log.info("=== Starting Aggregate Bill Generation ===");
        log.info("Project: {}, Campaign: {}, Tenant: {}", projectId, campaignNumber, criteria.getTenantId());

        // STEP 1: Get ALL billing periods for this campaign
        List<BillingPeriod> allPeriods = billingConfigurationService.getBillingPeriods(
                campaignNumber,
                criteria.getTenantId()
        );

        if (CollectionUtils.isEmpty(allPeriods)) {
            throw new CustomException("NO_BILLING_PERIODS",
                    "No billing periods found for campaign: " + campaignNumber);
        }

        // Sort periods by period number
        allPeriods.sort(Comparator.comparing(BillingPeriod::getPeriodNumber));
        log.info("Found {} billing periods for aggregate bill", allPeriods.size());

        // STEP 2: Validate all periods have successful intermediate bills
        log.info("Validating all periods have successful bills...");
        validateAllPeriodsSuccessful(projectId, allPeriods, criteria.getTenantId());
        log.info("✓ Validation passed - all {} periods billed successfully", allPeriods.size());

        // STEP 3: Check aggregate bill doesn't already exist
        log.info("Checking if aggregate bill already exists...");
        if (aggregateBillAlreadyExists(projectId, criteria.getTenantId())) {
            throw new CustomException("AGGREGATE_BILL_EXISTS",
                    "Aggregate bill already generated for project: " + projectId +
                            ". Cannot create duplicate aggregate bills.");
        }
        log.info("✓ No existing aggregate bill found");

        // STEP 4: Fetch ALL muster rolls from ALL periods
        // This is the KEY - we reuse existing fetching logic!
        log.info("Fetching ALL muster rolls from ALL periods...");
        List<MusterRoll> allMusterRolls = fetchAllMusterRollsForAllPeriods(
                requestInfo,
                criteria,
                isDistrictLevel,
                allPeriods
        );
        log.info("✓ Fetched {} muster rolls across {} periods", allMusterRolls.size(), allPeriods.size());

        // STEP 5: Create virtual aggregate period spanning all periods
        BillingPeriod firstPeriod = allPeriods.get(0);
        BillingPeriod lastPeriod = allPeriods.get(allPeriods.size() - 1);
        BillingPeriod aggregatePeriod = createAggregatePeriod(firstPeriod, lastPeriod, allPeriods);

        log.info("Created virtual aggregate period: {} to {}",
                formatDate(aggregatePeriod.getPeriodStartDate()),
                formatDate(aggregatePeriod.getPeriodEndDate()));

        // STEP 6: Generate aggregate bill using EXISTING logic!
        // The same generatePeriodBill() function used for intermediate bills
        log.info("Generating aggregate bill using existing bill generator...");
        int totalRegisterCount = getTotalRegisterCount(allPeriods);

        Bill aggregateBill = generatePeriodBill(
                requestInfo,
                criteria,
                aggregatePeriod,      // Virtual period with full date range
                allMusterRolls,       // ALL muster rolls from ALL periods
                project,
                totalRegisterCount,
                billingConfig
        );

        log.info("✓ Bill generated with {} details, total amount: {}",
                aggregateBill.getBillDetails() != null ? aggregateBill.getBillDetails().size() : 0,
                aggregateBill.getTotalAmount());

        // STEP 7: Override metadata to mark as FINAL_AGGREGATE
        enrichAggregateMetadata(aggregateBill, allPeriods, campaignNumber);
        log.info("✓ Enriched aggregate metadata");

        // STEP 8: Submit aggregate bill
        log.info("Submitting aggregate bill to expense service...");
        Bill submittedBill = submitAggregateBill(
                requestInfo,
                aggregateBill,
                aggregatePeriod,
                projectId,
                allPeriods,
                allMusterRolls.size()
        );

        log.info("=== Aggregate Bill Generation Complete ===");
        log.info("Bill ID: {}, Bill Number: {}, Total Amount: {}",
                submittedBill.getId(),
                submittedBill.getBillNumber(),
                submittedBill.getTotalAmount());

        return Collections.singletonList(submittedBill);
    }

    /**
     * Fetch ALL muster rolls from ALL periods by reusing existing logic
     *
     * For each period:
     * 1. Get registers overlapping with period (existing getRegistersForPeriod)
     * 2. Search muster rolls for those registers (existing searchExistingMusterRollsForPeriod)
     * 3. Accumulate all muster rolls
     *
     * @param requestInfo Request info
     * @param criteria Billing criteria
     * @param isDistrictLevel Whether to fetch children registers
     * @param allPeriods All billing periods
     * @return List of ALL muster rolls from ALL periods
     */
    private List<MusterRoll> fetchAllMusterRollsForAllPeriods(RequestInfo requestInfo,
                                                               Criteria criteria,
                                                               boolean isDistrictLevel,
                                                               List<BillingPeriod> allPeriods) {
        List<MusterRoll> allMusterRolls = new ArrayList<>();

        for (BillingPeriod period : allPeriods) {
            log.info("Fetching muster rolls for period {} ({} to {})",
                    period.getPeriodNumber(),
                    formatDate(period.getPeriodStartDate()),
                    formatDate(period.getPeriodEndDate()));

            try {
                // REUSE: Get registers for this period (existing logic)
                List<AttendanceRegister> periodRegisters = getRegistersForPeriod(
                        requestInfo,
                        criteria,
                        isDistrictLevel,
                        period
                );

                if (periodRegisters.isEmpty()) {
                    log.warn("No registers found for period {}, skipping", period.getPeriodNumber());
                    continue;
                }

                log.debug("Found {} registers for period {}", periodRegisters.size(), period.getPeriodNumber());

                // Get register IDs
                List<String> registerIds = periodRegisters.stream()
                        .map(AttendanceRegister::getId)
                        .collect(Collectors.toList());

                // REUSE: Search muster rolls (existing logic)
                List<MusterRoll> periodMusterRolls = searchExistingMusterRollsForPeriod(
                        requestInfo,
                        criteria.getTenantId(),
                        registerIds,
                        period
                );

                if (!CollectionUtils.isEmpty(periodMusterRolls)) {
                    allMusterRolls.addAll(periodMusterRolls);
                    log.info("Added {} muster rolls from period {}",
                            periodMusterRolls.size(), period.getPeriodNumber());
                } else {
                    log.warn("No muster rolls found for period {}", period.getPeriodNumber());
                }

            } catch (Exception e) {
                log.error("Error fetching muster rolls for period {}: {}",
                        period.getPeriodNumber(), e.getMessage(), e);
                throw new CustomException("MUSTER_ROLL_FETCH_ERROR",
                        String.format("Failed to fetch muster rolls for period %d: %s",
                                period.getPeriodNumber(), e.getMessage()));
            }
        }

        if (allMusterRolls.isEmpty()) {
            throw new CustomException("NO_MUSTER_ROLLS_FOR_AGGREGATE",
                    "No muster rolls found across any period for aggregate billing. " +
                            "Ensure all intermediate bills have been generated successfully.");
        }

        log.info("Successfully fetched total of {} muster rolls from {} periods",
                allMusterRolls.size(), allPeriods.size());
        return allMusterRolls;
    }

    /**
     * Create a virtual billing period spanning all periods
     * Used to set fromPeriod and toPeriod in aggregate bill
     *
     * @param firstPeriod First billing period
     * @param lastPeriod Last billing period
     * @param allPeriods All billing periods
     * @return Virtual aggregate period
     */
    private BillingPeriod createAggregatePeriod(BillingPeriod firstPeriod,
                                                BillingPeriod lastPeriod,
                                                List<BillingPeriod> allPeriods) {
        BillingPeriod aggregatePeriod = new BillingPeriod();

        // Use first period's start date and last period's end date
        aggregatePeriod.setPeriodStartDate(firstPeriod.getPeriodStartDate());
        aggregatePeriod.setPeriodEndDate(lastPeriod.getPeriodEndDate());

        // Set period number to 0 to indicate aggregate
        aggregatePeriod.setPeriodNumber(0);

        // Copy metadata
        aggregatePeriod.setId(UUID.randomUUID().toString());
        aggregatePeriod.setTenantId(firstPeriod.getTenantId());
        aggregatePeriod.setCampaignNumber(firstPeriod.getCampaignNumber());
        aggregatePeriod.setBillingFrequency(firstPeriod.getBillingFrequency());

        // Add additional details
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("isAggregate", true);
        additionalDetails.put("aggregatedPeriodCount", allPeriods.size());
        additionalDetails.put("periodNumbers", allPeriods.stream()
                .map(BillingPeriod::getPeriodNumber)
                .collect(Collectors.toList()));
        aggregatePeriod.setAdditionalDetails(additionalDetails);

        return aggregatePeriod;
    }

    /**
     * Enrich bill metadata to mark as FINAL_AGGREGATE
     * Overrides billingType and adds aggregate-specific metadata
     *
     * @param bill Bill to enrich
     * @param allPeriods All billing periods
     * @param campaignNumber Campaign number
     */
    private void enrichAggregateMetadata(Bill bill,
                                        List<BillingPeriod> allPeriods,
                                        String campaignNumber) {
        Map<String, Object> additionalDetails = bill.getAdditionalDetails() != null ?
                (Map<String, Object>) bill.getAdditionalDetails() : new HashMap<>();

        // Override billing type to FINAL_AGGREGATE
        additionalDetails.put("billingType", BILLING_TYPE_FINAL_AGGREGATE);

        // Remove individual period metadata
        additionalDetails.remove("billingPeriodId");
        additionalDetails.remove("periodNumber");

        // Add aggregate-specific metadata
        additionalDetails.put("aggregatedPeriodCount", allPeriods.size());
        additionalDetails.put("periodNumbers", allPeriods.stream()
                .map(BillingPeriod::getPeriodNumber)
                .collect(Collectors.toList()));
        additionalDetails.put("campaignNumber", campaignNumber);
        additionalDetails.put("periodStartDate", allPeriods.get(0).getPeriodStartDate());
        additionalDetails.put("periodEndDate", allPeriods.get(allPeriods.size() - 1).getPeriodEndDate());

        bill.setAdditionalDetails(additionalDetails);

        log.debug("Enriched bill with aggregate metadata for {} periods", allPeriods.size());
    }

    /**
     * Submit aggregate bill to expense service
     * Reuses existing submission logic but marks status as FINAL_AGGREGATE
     *
     * @param requestInfo Request info
     * @param bill Bill to submit
     * @param aggregatePeriod Virtual aggregate period
     * @param projectId Project reference ID
     * @param allPeriods All billing periods
     * @param totalMusterRollCount Total muster roll count
     * @return Submitted bill from expense service
     */
    private Bill submitAggregateBill(RequestInfo requestInfo,
                                     Bill bill,
                                     BillingPeriod aggregatePeriod,
                                     String projectId,
                                     List<BillingPeriod> allPeriods,
                                     int totalMusterRollCount) {
        log.info("Submitting aggregate bill for project {}", projectId);

        // Create bill status entry BEFORE submission
        String billStatusId = UUID.randomUUID().toString();
        int totalRegisterCount = getTotalRegisterCount(allPeriods);

        log.info("Creating aggregate bill status entry: {}", billStatusId);

        expenseCalculatorRepository.createBillStatusV2(
                billStatusId,
                bill.getTenantId(),
                projectId,
                BILLING_TYPE_FINAL_AGGREGATE, // billing_type
                null,                        // period_id (null for aggregate)
                null,                        // period_number kept null to satisfy positive check constraint
                INITIATED_STATUS,
                null,
                System.currentTimeMillis(),
                totalRegisterCount
        );

        // Submit bill using existing workflow
        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();

        try {
            log.info("Calling expense service to create aggregate bill");
            BillResponse response = billUtils.postCreateBill(requestInfo, bill, workflow);

            if (response != null && SUCCESSFUL_CONSTANT.equalsIgnoreCase(
                    response.getResponseInfo().getStatus())) {

                if (!CollectionUtils.isEmpty(response.getBills())) {
                    Bill submittedBill = response.getBills().get(0);

                    log.info("Aggregate bill successfully created: ID={}, Number={}, Amount={}, Details={}",
                            submittedBill.getId(),
                            submittedBill.getBillNumber(),
                            submittedBill.getTotalAmount(),
                            submittedBill.getBillDetails() != null ? submittedBill.getBillDetails().size() : 0);

                    // Update status to SUCCESSFUL
                    expenseCalculatorRepository.updateBillStatusWithDetails(
                            billStatusId,
                            SUCCESSFUL_STATUS,
                            null,
                            System.currentTimeMillis(),
                            submittedBill.getId(),
                            submittedBill.getBillNumber() != null ? submittedBill.getBillNumber() : "N/A",
                            submittedBill.getTotalAmount() != null ? submittedBill.getTotalAmount().toString() : "0",
                            totalMusterRollCount
                    );

                    log.info("Updated bill status to SUCCESSFUL for aggregate bill: {}", billStatusId);

                    // Trigger report generation if auto-generation is enabled
                    if (config.isReportGenerationAuto()) {
                        log.info("Triggering report generation for aggregate bill: {}",
                                submittedBill.getId());

                        ReportGenerationTrigger reportTrigger = ReportGenerationTrigger.builder()
                                .requestInfo(requestInfo)
                                .billId(submittedBill.getId())
                                .tenantId(submittedBill.getTenantId())
                                .createdTime(System.currentTimeMillis())
                                .numberOfBillDetails(submittedBill.getBillDetails() != null ?
                                        submittedBill.getBillDetails().size() : 0)
                                .build();

                        expenseCalculatorProducer.push(
                                config.getReportGenerationTriggerTopic(),
                                reportTrigger
                        );

                        log.info("Report generation trigger pushed to Kafka for aggregate bill");
                    }

                    return submittedBill;

                } else {
                    log.error("Aggregate bill submission returned empty bills list");
                    expenseCalculatorRepository.updateBillStatusV2(
                            billStatusId,
                            FAILED_STATUS,
                            "Empty bills list in response from expense service",
                            System.currentTimeMillis()
                    );
                    throw new CustomException("AGGREGATE_BILL_SUBMISSION_FAILED",
                            "Empty bills list returned from expense service");
                }
            } else {
                String errorMsg = "Aggregate bill submission failed for project " + projectId;
                log.error(errorMsg);
                expenseCalculatorRepository.updateBillStatusV2(
                        billStatusId,
                        FAILED_STATUS,
                        errorMsg,
                        System.currentTimeMillis()
                );
                throw new CustomException("AGGREGATE_BILL_SUBMISSION_FAILED", errorMsg);
            }

        } catch (CustomException e) {
            log.error("CustomException during aggregate bill submission: {}", e.getMessage(), e);
            expenseCalculatorRepository.updateBillStatusV2(
                    billStatusId,
                    FAILED_STATUS,
                    e.getMessage(),
                    System.currentTimeMillis()
            );
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during aggregate bill submission: {}", e.getMessage(), e);
            expenseCalculatorRepository.updateBillStatusV2(
                    billStatusId,
                    FAILED_STATUS,
                    "Unexpected error: " + e.getMessage(),
                    System.currentTimeMillis()
            );
            throw new CustomException("AGGREGATE_BILL_SUBMISSION_ERROR",
                    "Failed to submit aggregate bill: " + e.getMessage());
        }
    }

    /**
     * Validate all periods have successful intermediate bills
     * Checks bill_gen_status table for INTERMEDIATE bills for all periods
     *
     * @param projectId Project reference ID
     * @param allPeriods All billing periods
     * @param tenantId Tenant ID
     * @throws CustomException if any period is missing or not successful
     */
    private void validateAllPeriodsSuccessful(String projectId,
                                             List<BillingPeriod> allPeriods,
                                             String tenantId) {
        log.info("Validating all {} periods have successful bills", allPeriods.size());

        // Get all billed period numbers for this project
        List<Integer> billedPeriods = expenseCalculatorRepository.getBilledPeriodNumbersForProject(
                projectId,
                tenantId
        );

        log.debug("Project {} has {} billed periods: {}", projectId, billedPeriods.size(), billedPeriods);

        // Check for missing periods
        List<Integer> missingPeriods = new ArrayList<>();
        for (BillingPeriod period : allPeriods) {
            if (!billedPeriods.contains(period.getPeriodNumber())) {
                missingPeriods.add(period.getPeriodNumber());
            }
        }

        if (!missingPeriods.isEmpty()) {
            String errorMsg = String.format(
                    "Cannot create aggregate bill for project %s. " +
                            "Missing intermediate bills for periods: %s. " +
                            "All intermediate bills must be generated successfully before creating aggregate bill. " +
                            "Currently billed periods: %s",
                    projectId,
                    missingPeriods,
                    billedPeriods.isEmpty() ? "None" : billedPeriods.toString()
            );
            log.error(errorMsg);
            throw new CustomException("INCOMPLETE_INTERMEDIATE_BILLS", errorMsg);
        }

        log.info("✓ Validation passed: All {} periods have successful intermediate bills", allPeriods.size());
    }

    /**
     * Check if aggregate bill already exists for project
     * Queries bill_gen_status table for FINAL_AGGREGATE billing_type
     *
     * @param projectId Project reference ID
     * @param tenantId Tenant ID
     * @return true if aggregate bill exists, false otherwise
     */
    private boolean aggregateBillAlreadyExists(String projectId, String tenantId) {
        return expenseCalculatorRepository.checkAggregateBillExists(projectId, tenantId);
    }

    /**
     * Calculate total register count across all periods
     * Reads from period additionalDetails
     *
     * @param allPeriods All billing periods
     * @return Total register count
     */
    private int getTotalRegisterCount(List<BillingPeriod> allPeriods) {
        return allPeriods.stream()
                .mapToInt(period -> {
                    try {
                        if (period.getAdditionalDetails() == null) {
                            return 0;
                        }
                        Map<String, Object> details = mapper.convertValue(
                                period.getAdditionalDetails(), Map.class
                        );
                        Object registerCount = details.get("registerCount");
                        if (registerCount instanceof Integer) {
                            return (Integer) registerCount;
                        }
                        return 0;
                    } catch (Exception e) {
                        log.warn("Failed to extract registerCount from period {}: {}",
                                period.getPeriodNumber(), e.getMessage());
                        return 0;
                    }
                })
                .sum();
    }

    /**
     * Check if the given period is the last period for the billing configuration.
     * Used to determine when to update attendance register reviewStatus to APPROVED.
     *
     * @param period Current billing period
     * @param tenantId Tenant ID
     * @return true if this is the last period, false otherwise
     */
    private boolean isLastPeriod(BillingPeriod period, String tenantId) {
        try {
            // Get all periods for this billing configuration
            BillingPeriodSearchCriteria criteria = BillingPeriodSearchCriteria.builder()
                    .billingConfigId(period.getBillingConfigId())
                    .tenantId(tenantId)
                    .build();

            BillingPeriodSearchResponse response = billingConfigurationService.searchBillingPeriods(
                    BillingPeriodSearchRequest.builder()
                            .requestInfo(new RequestInfo())
                            .searchCriteria(criteria)
                            .build()
            );

            if (response == null || CollectionUtils.isEmpty(response.getBillingPeriods())) {
                log.warn("No periods found for billingConfigId: {}", period.getBillingConfigId());
                return false;
            }

            // Find the maximum period number
            Integer maxPeriodNumber = response.getBillingPeriods().stream()
                    .map(BillingPeriod::getPeriodNumber)
                    .filter(num -> num != null)
                    .max(Integer::compareTo)
                    .orElse(null);

            if (maxPeriodNumber == null) {
                log.warn("No valid period numbers found for billingConfigId: {}", period.getBillingConfigId());
                return false;
            }

            boolean isLast = period.getPeriodNumber().equals(maxPeriodNumber);
            log.info("Period {} check: maxPeriodNumber={}, isLast={}",
                    period.getPeriodNumber(), maxPeriodNumber, isLast);

            return isLast;

        } catch (Exception e) {
            log.error("Error checking if period {} is last period: {}",
                    period.getPeriodNumber(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * Update attendance register reviewStatus after period bill generation.
     *
     * V2 Enhancement: Updates reviewStatus based on period type:
     * - LAST period → reviewStatus = "APPROVED" (ready for aggregate billing)
     * - OTHER periods → reviewStatus = "PENDINGFORAPPROVAL" (bill generated, more periods pending)
     *
     * @param requestInfo Request info
     * @param projectId Project reference ID
     * @param period Billing period
     * @param tenantId Tenant ID
     * @param isLastPeriod Whether this is the last period
     */
    private void updateRegisterReviewStatusAfterPeriodBill(RequestInfo requestInfo, String projectId,
                                                           BillingPeriod period, String tenantId,
                                                           boolean isLastPeriod) {
        try {
            String reviewStatus = isLastPeriod ? REVIEW_STATUS_APPROVED : REVIEW_STATUS_PENDING_FOR_APPROVAL;

            log.info("Period {} ({}) billed successfully for project {}. Updating register reviewStatus to '{}'",
                    period.getPeriodNumber(),
                    isLastPeriod ? "LAST" : "INTERMEDIATE",
                    projectId,
                    reviewStatus);

            attendanceUtil.updateRegisterReviewStatus(requestInfo, projectId, tenantId, reviewStatus);

            log.info("Successfully updated attendance register reviewStatus to '{}' for project {} after period {}",
                    reviewStatus, projectId, period.getPeriodNumber());

        } catch (Exception e) {
            // Log error but don't fail the bill generation
            log.error("Failed to update register reviewStatus for project {} after period {}: {}",
                    projectId, period.getPeriodNumber(), e.getMessage(), e);
            log.warn("Bill generation was successful, but register reviewStatus update failed. " +
                    "This may need manual intervention.");
        }
    }
}
