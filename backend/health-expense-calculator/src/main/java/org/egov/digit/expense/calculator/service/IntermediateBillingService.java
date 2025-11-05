package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.project.Project;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
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
 * Key Responsibilities:
 * - Iterate through billing periods
 * - Fetch registers overlapping with each period
 * - Generate/fetch period-specific muster rolls
 * - Generate bills per period
 * - Update period status after billing
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
                                      CommonUtil commonUtil) {
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
    }

    /**
     * Process intermediate billing for V2 projects
     *
     * Steps:
     * 1. Get all billing periods for project
     * 2. For each period:
     *    a. Check if bill already generated (duplicate prevention)
     *    b. Fetch registers overlapping with period dates
     *    c. Validate register approval
     *    d. Generate/fetch period-specific muster rolls
     *    e. Generate bill for period
     *    f. Submit bill to expense service
     *    g. Update period status
     * 3. Return list of generated bills
     *
     * @param requestInfo Request info with user details
     * @param criteria Billing criteria
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

        List<Bill> generatedBills = new ArrayList<>();

        // Step 1: Extract campaign number from project reference ID
        String campaignNumber = extractCampaignNumber(project);

        // Get all billing periods for this campaign
        List<BillingPeriod> periods = billingConfigurationService.getBillingPeriods(
                campaignNumber,
                criteria.getTenantId()
        );

        // Validate billing periods
        intermediateBillingValidator.validateBillingPeriods(periods, campaignNumber);

        log.info("Found {} billing periods for campaign {}", periods.size(), campaignNumber);

        // Step 2: Process each billing period
        for (BillingPeriod period : periods) {
            log.info("Processing billing period {}: {} to {}",
                    period.getPeriodNumber(),
                    formatDate(period.getPeriodStartDate()),
                    formatDate(period.getPeriodEndDate())
            );

            try {
                // Step 2a.1: Validate period before processing
                intermediateBillingValidator.validatePeriodForProcessing(period);

                // Step 2a.2: Check if bill already generated for this period (duplicate prevention)
                if (isBillGeneratedForPeriod(period)) {
                    log.info("Bill already generated for period {} (status: {}), skipping",
                            period.getPeriodNumber(), period.getStatus());
                    continue;
                }

                // Step 2b: Fetch registers overlapping with period dates
                List<AttendanceRegister> periodRegisters = getRegistersForPeriod(
                        requestInfo, criteria, isDistrictLevel, period
                );

                if (periodRegisters.isEmpty()) {
                    log.info("No registers found for period {}, skipping", period.getPeriodNumber());
                    continue;
                }

                log.info("Found {} registers for period {}", periodRegisters.size(), period.getPeriodNumber());

                // Step 2c: Validate ALL registers are approved (V1 validation preserved)
                if (config.isAttendanceApprovalRequired()) {
                    expenseCalculatorServiceValidator.validateAttendanceRegisterApproval(periodRegisters);
                }

                // Step 2d: Get register IDs
                List<String> registerIds = periodRegisters.stream()
                        .map(AttendanceRegister::getId)
                        .collect(Collectors.toList());

                // Step 2d.1: Validate register IDs
                intermediateBillingValidator.validateRegisterIds(registerIds, period);

                // Step 2e: Generate/fetch period-specific muster rolls via remote service call
                List<MusterRoll> musterRolls = callPeriodAwareMusterRollService(
                        requestInfo,
                        criteria.getTenantId(),
                        registerIds,
                        period,
                        campaignNumber
                );

                if (CollectionUtils.isEmpty(musterRolls)) {
                    log.warn("No muster rolls generated for period {}, skipping", period.getPeriodNumber());
                    continue;
                }

                log.info("Processing {} muster rolls for period {}", musterRolls.size(), period.getPeriodNumber());

                // Step 2e.1: Validate muster rolls before bill generation
                intermediateBillingValidator.validateMusterRollsForBilling(musterRolls, period);

                // Step 2f: Generate bill for this period
                Bill periodBill = generatePeriodBill(
                        requestInfo, criteria, period, musterRolls, project, periodRegisters.size(), billingConfig
                );

                // Step 2f.1: Validate bill before submission
                intermediateBillingValidator.validateBillBeforeSubmission(periodBill, period);

                // Step 2g: Submit bill to expense service
                Bill submittedBill = submitPeriodBill(requestInfo, periodBill, period, project, periodRegisters.size());

                if (submittedBill != null) {
                    generatedBills.add(submittedBill);

                    // Update period status to BILLED
                    updatePeriodStatusAfterBilling(period, submittedBill, periodRegisters.size(), musterRolls.size());
                }

            } catch (CustomException e) {
                log.error("IntermediateBillingService::processIntermediateBilling - Error processing period {}: {}",
                        period.getPeriodNumber(), e.getMessage(), e);
                // Record failure but continue with other periods
                recordPeriodBillingFailure(period, project, e);
            } catch (Exception e) {
                log.error("IntermediateBillingService::processIntermediateBilling - Unexpected error processing period {}: {}",
                        period.getPeriodNumber(), e.getMessage(), e);
                // Record failure but continue with other periods
                recordPeriodBillingFailure(period, project,
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
     * Check if bill already generated for a specific period
     * Used for duplicate prevention
     *
     * @param period Billing period
     * @return true if bill already generated, false otherwise
     */
    private boolean isBillGeneratedForPeriod(BillingPeriod period) {
        // Check period status
        if ("BILLED".equalsIgnoreCase(period.getStatus())) {
            log.info("Period {} already marked as BILLED", period.getPeriodNumber());
            return true;
        }

        // Query bill status table for this period
        List<BillStatus> statuses = expenseCalculatorRepository.getBillStatusByPeriodId(period.getId());

        boolean billExists = statuses.stream()
                .anyMatch(status -> SUCCESSFUL_STATUS.equals(status.getStatus()));

        if (billExists) {
            log.info("Bill status found for period {} with SUCCESSFUL status", period.getPeriodNumber());
        }

        return billExists;
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
     * Call PeriodAwareMusterRollService in muster-roll service
     * This is a remote service call to generate period-specific muster rolls
     *
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @param registerIds List of register IDs
     * @param period Billing period
     * @return List of muster rolls for the period
     */
    private List<MusterRoll> callPeriodAwareMusterRollService(RequestInfo requestInfo,
                                                              String tenantId,
                                                              List<String> registerIds,
                                                              BillingPeriod period,
                                                              String campaignNumber) {
        log.info("Calling PeriodAwareMusterRollService for {} registers in period {}",
                registerIds.size(), period.getPeriodNumber());

        // Build request for muster-roll service
        String uri = config.getMusterRollHost() + config.getMusterRollEndPoint() + "/_periodicCreate";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("RequestInfo", requestInfo);
        requestBody.put("tenantId", tenantId);
        requestBody.put("registerIds", registerIds);
        requestBody.put("campaignNumber", campaignNumber);

        // Map BillingPeriod to simpler DTO for service call
        Map<String, Object> periodDetails = new HashMap<>();
        periodDetails.put("id", period.getId());
        periodDetails.put("periodNumber", period.getPeriodNumber());
        periodDetails.put("periodStartDate", period.getPeriodStartDate());
        periodDetails.put("periodEndDate", period.getPeriodEndDate());
        periodDetails.put("campaignNumber", campaignNumber);

        requestBody.put("billingPeriod", periodDetails);

        try {
            // Call muster-roll service
            Map<String, Object> response = restTemplate.postForObject(
                    uri,
                    requestBody,
                    Map.class
            );

            if (response == null || !response.containsKey("musterRolls")) {
                throw new CustomException("MUSTER_ROLL_GENERATION_FAILED",
                        "Failed to generate muster rolls for period " + period.getPeriodNumber() +
                                ": Empty response from muster-roll service");
            }

            // Convert response to MusterRoll list
            List<Map<String, Object>> musterRollMaps = (List<Map<String, Object>>) response.get("musterRolls");
            List<MusterRoll> musterRolls = musterRollMaps.stream()
                    .map(map -> mapper.convertValue(map, MusterRoll.class))
                    .collect(Collectors.toList());

            log.info("Successfully retrieved {} muster rolls from PeriodAwareMusterRollService",
                    musterRolls.size());

            return musterRolls;

        } catch (Exception e) {
            log.error("Error calling PeriodAwareMusterRollService: {}", e.getMessage(), e);
            throw new CustomException("MUSTER_ROLL_SERVICE_ERROR",
                    "Failed to call muster-roll service: " + e.getMessage());
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

        additionalDetails.put("billingType", "INTERMEDIATE");
        additionalDetails.put("billingPeriodId", period.getId());
        additionalDetails.put("periodNumber", period.getPeriodNumber());
        additionalDetails.put("periodStartDate", period.getPeriodStartDate());
        additionalDetails.put("periodEndDate", period.getPeriodEndDate());
        additionalDetails.put("billingFrequency", billingConfig.getBillingFrequency() != null ?
                billingConfig.getBillingFrequency().toString() : "UNKNOWN");
        additionalDetails.put("v2Enhanced", true);
        additionalDetails.put("noOfRegisters", registerCount);
        // Campaign number can be extracted from project if needed
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
     *
     * @param requestInfo Request info
     * @param bill Bill to submit
     * @param period Billing period
     * @param project Project details
     * @param registerCount Number of registers
     * @return Submitted bill from expense service
     */
    private Bill submitPeriodBill(RequestInfo requestInfo, Bill bill, BillingPeriod period,
                                  Project project, int registerCount) {
        log.info("Submitting bill for period {}", period.getPeriodNumber());

        // Get project reference ID
        String referenceId = project.getProjectHierarchy() != null ?
                project.getProjectHierarchy() : project.getId();

        // Create bill status entry BEFORE submission
        String billStatusId = UUID.randomUUID().toString();
        expenseCalculatorRepository.createBillStatusV2(
                billStatusId,
                bill.getTenantId(),
                referenceId,
                "INTERMEDIATE",              // billing_type
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
            BillResponse response = billUtils.postCreateBill(requestInfo, bill, workflow);

            // Check if submission successful
            if (response != null && SUCCESSFUL_CONSTANT.equalsIgnoreCase(
                    response.getResponseInfo().getStatus())) {

                if (!CollectionUtils.isEmpty(response.getBills())) {
                    Bill submittedBill = response.getBills().get(0);

                    // Update status to SUCCESSFUL
                    expenseCalculatorRepository.updateBillStatusV2(
                            billStatusId,
                            SUCCESSFUL_STATUS,
                            null,
                            System.currentTimeMillis()  // processing_end_time
                    );

                    log.info("Successfully submitted bill for period {} with ID: {}",
                            period.getPeriodNumber(), submittedBill.getId());

                    return submittedBill;
                } else {
                    log.error("Bill submission returned empty bills list for period {}",
                            period.getPeriodNumber());
                    expenseCalculatorRepository.updateBillStatusV2(
                            billStatusId,
                            FAILED_STATUS,
                            "Empty bills list in response",
                            System.currentTimeMillis()
                    );
                    return null;
                }
            } else {
                String errorMsg = "Bill submission failed for period " + period.getPeriodNumber();
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
            log.error("Exception during bill submission for period {}: {}",
                    period.getPeriodNumber(), e.getMessage(), e);
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
     * Update billing period status after successful billing
     *
     * @param period Billing period
     * @param bill Submitted bill
     * @param registerCount Number of registers
     * @param musterRollCount Number of muster rolls
     */
    private void updatePeriodStatusAfterBilling(BillingPeriod period, Bill bill,
                                                int registerCount, int musterRollCount) {
        log.info("Updating period {} status to BILLED", period.getPeriodNumber());

        period.setStatus("BILLED");
        period.setBillId(bill.getId());
        period.setTotalAmount(bill.getTotalAmount());
        period.setRegisterCount(registerCount);
        period.setMusterRollCount(musterRollCount);

        billingConfigurationService.updateBillingPeriod(period);

        log.info("Successfully updated period {} status", period.getPeriodNumber());
    }

    /**
     * Record period billing failure
     *
     * @param period Billing period
     * @param project Project details
     * @param exception Exception that occurred
     */
    private void recordPeriodBillingFailure(BillingPeriod period, Project project, CustomException exception) {
        String referenceId = project.getProjectHierarchy() != null ?
                project.getProjectHierarchy() : project.getId();

        String billStatusId = UUID.randomUUID().toString();
        expenseCalculatorRepository.createBillStatusV2(
                billStatusId,
                project.getTenantId(),
                referenceId,
                "INTERMEDIATE",
                period.getId(),
                period.getPeriodNumber(),
                FAILED_STATUS,
                exception.getCode() + ": " + exception.getMessage(),
                System.currentTimeMillis(),
                0
        );

        log.info("Recorded failure for period {}", period.getPeriodNumber());
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
        bill.setStatus("ACTIVE");
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
}
