package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.models.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.MusterRollProducer;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.util.MusterRollServiceUtil;
import org.egov.util.ResponseInfoCreator;
import org.egov.validator.MusterRollValidator;

import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterResponse;
import org.egov.web.models.BillingPeriod;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollResponse;
import org.egov.web.models.MusterRollSearchCriteria;
import org.egov.web.models.MusterRollStatusUpdateEvent;
import org.egov.works.services.common.models.musterroll.Status;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.egov.util.MusterRollServiceConstants.ACTION_APPROVE;
import static org.egov.util.MusterRollServiceConstants.STATUS_APPROVED;

/**
 * MusterRollService
 *
 * ================================================================================
 * PURPOSE & BUSINESS CONTEXT
 * ================================================================================
 *
 * Core service for muster roll CRUD operations. Handles both V1 (legacy) and
 * V2 (intermediate billing) flows with backward compatibility.
 *
 * WHAT IS A MUSTER ROLL?
 * ----------------------
 * A muster roll is a time-and-attendance record for workers enrolled in an
 * attendance register. It aggregates daily attendance logs into a summary
 * that can be used for payment calculation.
 *
 * V1 FLOW (Legacy):
 * -----------------
 * 1. Muster roll created for ENTIRE register date range
 * 2. One register → One muster roll → One bill (at campaign end)
 * 3. Register.reviewStatus updated when muster approved
 * 4. No billingPeriodId field
 *
 * V2 FLOW (Intermediate Billing):
 * --------------------------------
 * 1. Muster roll created for SPECIFIC billing period
 * 2. One register → MULTIPLE muster rolls → MULTIPLE bills (periodic)
 * 3. Muster status published via Kafka to attendance service
 * 4. billingPeriodId field REQUIRED
 *
 * KEY V2 ADDITIONS:
 * -----------------
 * - applyPeriodAwareDates(): Calculate register ∩ period date intersection
 * - publishMusterRollStatusUpdateEvent(): Kafka event for status sync
 * - validatePeriodNotLocked(): Check if period is locked after billing
 * - V2-aware duplicate detection (by billingPeriodId, not dates)
 *
 * KAFKA TOPICS:
 * -------------
 * - save-muster-roll: Persister topic for create
 * - update-muster-roll: Persister topic for update
 * - calculate-muster-roll: Expense calculator consumption
 * - muster-roll-status-update: V2 status sync to attendance (NEW)
 *
 * BACKWARD COMPATIBILITY:
 * -----------------------
 * All V1 flows continue to work unchanged. V2 detection is based solely on
 * presence of billingPeriodId. If null/blank, V1 flow is used.
 *
 * ================================================================================
 */
@Service
@Slf4j
public class MusterRollService {

    private final MusterRollValidator musterRollValidator;

    private final EnrichmentService enrichmentService;

    private final CalculationService calculationService;

    private final WorkflowService workflowService;

    private final NotificationService notificationService;

    private final MusterRollProducer musterRollProducer;

    private final MusterRollServiceConfiguration serviceConfiguration;

    private final MusterRollRepository musterRollRepository;

    private final ObjectMapper mapper;

    private final MdmsUtil mdmsUtils;

    private final MusterRollServiceUtil musterRollServiceUtil;

    private final MusterRollServiceConfiguration config;

    private final RestTemplate restTemplate;

    private final ResponseInfoCreator responseInfoCreator;

    private static final String COMPUTE_ATTENDENSE = "computeAttendance";

    @Autowired
    public MusterRollService(CalculationService calculationService, MusterRollValidator musterRollValidator, EnrichmentService enrichmentService, WorkflowService workflowService, NotificationService notificationService, MusterRollProducer musterRollProducer, MusterRollServiceConfiguration serviceConfiguration, MusterRollRepository musterRollRepository, ObjectMapper mapper, RestTemplate restTemplate, MdmsUtil mdmsUtils, MusterRollServiceUtil musterRollServiceUtil, MusterRollServiceConfiguration config, ResponseInfoCreator responseInfoCreator) {
        this.calculationService = calculationService;
        this.musterRollValidator = musterRollValidator;
        this.enrichmentService = enrichmentService;
        this.workflowService = workflowService;
        this.notificationService = notificationService;
        this.musterRollProducer = musterRollProducer;
        this.serviceConfiguration = serviceConfiguration;
        this.musterRollRepository = musterRollRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.mdmsUtils = mdmsUtils;
        this.musterRollServiceUtil = musterRollServiceUtil;
        this.config = config;
        this.responseInfoCreator = responseInfoCreator;
    }

    /**
     * Calculates the per day attendance , attendance aggregate from startDate to endDate
     * and provides it as an estimate.
     * Note: This will NOT create muster roll and NOT store the details
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest estimateMusterRoll(MusterRollRequest musterRollRequest) {
        log.info("MusterRollService::estimateMusterRoll");

        musterRollValidator.validateEstimateMusterRoll(musterRollRequest);
        enrichmentService.enrichMusterRollOnEstimate(musterRollRequest);
        calculationService.createAttendance(musterRollRequest,false);
        return musterRollRequest;
    }


    /**
     * Calculates the per day attendance , attendance aggregate from startDate to endDate for all the
     * individuals of the provided attendance register.
     * Creates muster roll and stores the details.
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest createMusterRoll(MusterRollRequest musterRollRequest) {
        log.info("MusterRollService::createMusterRoll");

        MusterRoll musterRoll = musterRollRequest.getMusterRoll();

        // V2 Flow: If billingPeriodId is present, apply period-aware date intersection logic
        if (StringUtils.isNotBlank(musterRoll.getBillingPeriodId())) {
            log.info("MusterRollService::createMusterRoll - V2 flow detected (billingPeriodId: {})",
                musterRoll.getBillingPeriodId());
            applyPeriodAwareDates(musterRollRequest);
        } else {
            log.info("MusterRollService::createMusterRoll - V1 flow detected (no billingPeriodId)");
        }

        // Common validation and enrichment (works for both v1 and v2)
        musterRollValidator.validateCreateMusterRoll(musterRollRequest);
        checkMusterRollExists(musterRoll);
        enrichmentService.enrichMusterRollOnCreate(musterRollRequest);
        calculationService.createAttendance(musterRollRequest,true);

        // Workflow processing (same for v1 and v2)
        if(config.isMusterRollWorkflowEnabled()) {
            workflowService.updateWorkflowStatus(musterRollRequest);
        } else {
            musterRoll.setMusterRollStatus(config.getMusterRollNoWorkflowCreateStatus());
        }

        // V2 Intermediate Billing - Publish status update event for newly created muster (V2 only)
        publishMusterRollStatusUpdateEvent(musterRoll, null);

        // Save muster roll
        musterRollProducer.push(musterRoll.getTenantId(), serviceConfiguration.getSaveMusterRollTopic(), musterRollRequest);

        log.info("MusterRollService::createMusterRoll - Created muster roll {} with status {}",
            musterRoll.getId(), musterRoll.getMusterRollStatus());

        return musterRollRequest;
    }

    /**
     * Applies period-aware date intersection logic for V2 flow.
     * Calculates intersection between register dates and billing period dates.
     * Updates muster roll startDate and endDate to the intersection.
     *
     * @param musterRollRequest Muster roll request with billingPeriodId
     */
    private void applyPeriodAwareDates(MusterRollRequest musterRollRequest) {
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        String billingPeriodId = musterRoll.getBillingPeriodId();
        String tenantId = musterRoll.getTenantId();
        String registerId = musterRoll.getRegisterId();

        log.info("MusterRollService::applyPeriodAwareDates - Calculating date intersection for register {} and period {}",
            registerId, billingPeriodId);

        try {
            // Fetch billing period details
            BillingPeriod period = musterRollServiceUtil.fetchBillingPeriod(
                billingPeriodId,
                tenantId,
                musterRollRequest.getRequestInfo()
            );

            // Fetch attendance register to get its dates
            AttendanceRegisterResponse registerResponse = musterRollServiceUtil.fetchAttendanceRegister(
                musterRollRequest.getRequestInfo(),
                tenantId,
                registerId
            );

            if (registerResponse == null || CollectionUtils.isEmpty(registerResponse.getAttendanceRegister())) {
                throw new CustomException("REGISTER_NOT_FOUND",
                    "Attendance register not found for ID: " + registerId);
            }

            AttendanceRegister register = registerResponse.getAttendanceRegister().get(0);

            // Calculate intersection between register dates and period dates
            long registerStart = register.getStartDate().longValue();
            long registerEnd = register.getEndDate().longValue();
            long periodStart = period.getPeriodStartDate();
            long periodEnd = period.getPeriodEndDate();

            long intersectionStart = Math.max(registerStart, periodStart);
            long intersectionEnd = Math.min(registerEnd, periodEnd);

            // Validate intersection exists
            if (intersectionStart > intersectionEnd) {
                throw new CustomException("NO_DATE_INTERSECTION",
                    String.format("Register dates (%s to %s) do not overlap with period dates (%s to %s)",
                        formatDate(registerStart), formatDate(registerEnd),
                        formatDate(periodStart), formatDate(periodEnd)));
            }

            // Update muster roll with intersection dates
            musterRoll.setStartDate(BigDecimal.valueOf(intersectionStart));
            musterRoll.setEndDate(BigDecimal.valueOf(intersectionEnd));

            log.info("MusterRollService::applyPeriodAwareDates - Applied intersection dates: {} to {} ({} days)",
                formatDate(intersectionStart), formatDate(intersectionEnd),
                ((intersectionEnd - intersectionStart) / (24 * 60 * 60 * 1000)) + 1);

        } catch (Exception e) {
            log.error("MusterRollService::applyPeriodAwareDates - Error calculating period dates", e);
            throw new CustomException("PERIOD_DATE_CALCULATION_ERROR",
                "Failed to calculate period-aware dates: " + e.getMessage());
        }
    }

    /**
     * Format timestamp to readable date string.
     */
    private String formatDate(long timestamp) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
    }

    /**
     * Search muster roll based on the given search criteria - tenantId, musterId, musterRollNumber, startDate, endDate, status
     * and musterRollStatus
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public MusterRollResponse searchMusterRolls(RequestInfoWrapper requestInfoWrapper, MusterRollSearchCriteria searchCriteria) {
        log.info("MusterRollService::searchMusterRolls");

        musterRollValidator.validateSearchMuster(requestInfoWrapper,searchCriteria);
        enrichmentService.enrichSearchRequest(searchCriteria);

        List<Role> roles = requestInfoWrapper.getRequestInfo().getUserInfo().getRoles();
        boolean isFilterRequired = false;
        if (config.getRestrictedSearchRoles() != null && !config.getRestrictedSearchRoles().isEmpty()) {
            List<String> restrictedRoles = Arrays.asList(config.getRestrictedSearchRoles().split(","));
            isFilterRequired = roles.stream()
                    .anyMatch(role -> restrictedRoles.contains(role.getCode()));
        }

        //Fetch the attendance registers that belong to the user and then fetch the musters that belongs to the user
        List<String> registerIds = new ArrayList<>();
        if (isFilterRequired) {
            registerIds = fetchAttendanceRegistersOfUser(requestInfoWrapper.getRequestInfo(),searchCriteria);
        }

        List<MusterRoll> musterRollList = musterRollRepository.getMusterRoll(searchCriteria,registerIds);
        int count = musterRollRepository.getMusterRollCount(searchCriteria,registerIds);
        List<MusterRoll> filteredMusterRollList = musterRollList;

        //apply the limit and offset
        if (filteredMusterRollList != null && !musterRollServiceUtil.isTenantBasedSearch(searchCriteria)) {
            filteredMusterRollList = applyLimitAndOffset(searchCriteria,filteredMusterRollList);
        }

        //populate response
        ResponseInfo responseInfo = responseInfoCreator.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true);
        return MusterRollResponse.builder().responseInfo(responseInfo).musterRolls(filteredMusterRollList)
                .count(count).build();
    }

    /**
     * Updates the totalAttendance, skill details (if modified) and re-calculates the attendance (if 'computeAttendance' is true)
     *
     * @param musterRollRequest
     * @return
     */
    public MusterRollRequest updateMusterRoll(MusterRollRequest musterRollRequest) {
        log.info("MusterRollService::updateMusterRoll");

        // V2 Flow: If billingPeriodId is present on update, recompute the period intersection
        if (StringUtils.isNotBlank(musterRollRequest.getMusterRoll().getBillingPeriodId())) {
            log.info("MusterRollService::updateMusterRoll - V2 flow detected (billingPeriodId: {}) - reapplying period intersection",
                musterRollRequest.getMusterRoll().getBillingPeriodId());
            applyPeriodAwareDates(musterRollRequest);
        }

        // CRITICAL: Check if period/register is locked due to billing (if enabled)
        // Once a bill is generated for a period, NO edits allowed to muster roll or attendance
        if (config.isPeriodLockingEnabled()) {
            validatePeriodNotLocked(musterRollRequest.getMusterRoll(), musterRollRequest.getRequestInfo());
        }

        musterRollValidator.validateUpdateMusterRoll(musterRollRequest);
        //If 'computeAttendance' flag is true, re-calculate the attendance from attendanceLogs and update
        boolean isComputeAttendance = isComputeAttendance(musterRollRequest.getMusterRoll());

        //check if the user is enrolled in the attendance register for resubmit
        MusterRoll existingMusterRoll = fetchExistingMusterRoll(musterRollRequest.getMusterRoll());
        log.info("MusterRollService::updateMusterRoll::update request for musterRollNumber::"+existingMusterRoll.getMusterRollNumber());

        //fetch MDMS data for muster - skill level
        String tenantId = existingMusterRoll.getTenantId();
        Object mdmsData = mdmsUtils.mDMSCallMuster(musterRollRequest, tenantId);
        Object mdmsV2Data = mdmsUtils.mDMSV2CallMuster(musterRollRequest, tenantId);


        //fetch the update additionalDetails from the request and persist it for verification
        if (!isComputeAttendance) {
            Object additionalDetails = musterRollRequest.getMusterRoll().getAdditionalDetails();
            existingMusterRoll.setAdditionalDetails(additionalDetails);
        }

        enrichmentService.enrichMusterRollOnUpdate(musterRollRequest,existingMusterRoll,mdmsV2Data);
        if (isComputeAttendance) {
            RequestInfo requestInfo = musterRollRequest.getRequestInfo();
            musterRollValidator.isValidUser(existingMusterRoll, requestInfo);
            calculationService.updateAttendance(musterRollRequest,mdmsData);
        }

        // V2 Intermediate Billing - Capture previous status before workflow update
        String previousStatus = musterRollRequest.getMusterRoll().getMusterRollStatus();

        if(config.isMusterRollWorkflowEnabled()) {
            workflowService.updateWorkflowStatus(musterRollRequest);
        }

        // V2 Intermediate Billing - Publish status update event (for V2 muster rolls only)
        publishMusterRollStatusUpdateEvent(musterRollRequest.getMusterRoll(), previousStatus);
        // Update attendance register status on muster approval
        // V1 Flow: Update register.reviewStatus = "APPROVED" (1:1 mapping)
        // V2 Flow: Skip register update (1:many mapping - register spans multiple periods)
        if(config.isUpdateAttendanceRegisterReviewStatusEnabled()
            && STATUS_APPROVED.equalsIgnoreCase(musterRollRequest.getMusterRoll().getMusterRollStatus())) {

            MusterRoll musterRoll = musterRollRequest.getMusterRoll();

            // V1 Flow ONLY: Update register when billingPeriodId is NULL
            if (StringUtils.isBlank(musterRoll.getBillingPeriodId())) {
                log.info("updateMusterRoll::V1 flow - updating register status to APPROVED for register: {}",
                    musterRoll.getRegisterId());

                AttendanceRegisterResponse attendanceRegisterResponse = musterRollServiceUtil
                        .fetchAttendanceRegister(musterRoll, musterRollRequest.getRequestInfo());
                List<AttendanceRegister> attendanceRegisters = attendanceRegisterResponse.getAttendanceRegister();

                if(attendanceRegisters == null || attendanceRegisters.isEmpty()) {
                    log.error("No attendance registers found to update the status for muster roll ID: {}", musterRoll.getId());
                    throw new CustomException("ATTENDANCE_REGISTER_NOT_FOUND",
                        "No attendance registers found to update the status for the given muster roll");
                }

                AttendanceRegister attendanceRegister = attendanceRegisters.get(0);
                attendanceRegister.setReviewStatus(STATUS_APPROVED);
                musterRollServiceUtil.updateAttendanceRegister(attendanceRegister, musterRollRequest.getRequestInfo());
            } else {
                // V2 Flow: Don't update register status (register spans multiple periods)
                log.info("updateMusterRoll::V2 flow - skipping register status update. " +
                    "Muster approved for period: {} register: {}",
                    musterRoll.getBillingPeriodId(), musterRoll.getRegisterId());
            }
        }
        musterRollProducer.push(tenantId, serviceConfiguration.getUpdateMusterRollTopic(), musterRollRequest);

        try {
            notificationService.sendNotificationToCBO(musterRollRequest);
        }catch (Exception e){
            log.error("Exception while sending notification: " + e);
        }

        //If the musterroll is in 'APPROVED' status, push the musterRoll to calculate topic to be processed by expense-calculator service
        if (StringUtils.isNotBlank(musterRollRequest.getMusterRoll().getMusterRollStatus()) && STATUS_APPROVED.equalsIgnoreCase(musterRollRequest.getMusterRoll().getMusterRollStatus())) {
            musterRollProducer.push(tenantId, serviceConfiguration.getCalculateMusterRollTopic(), musterRollRequest);
        }

        return musterRollRequest;
    }

    /**
     * Check if the muster roll already exists to avoid duplicate muster creation.
     *
     * V1 (billingPeriodId is NULL): Checks registerId + startDate + endDate
     * V2 (billingPeriodId is present): Checks registerId + billingPeriodId
     *
     * @param musterRoll Muster roll to check
     */
    private void checkMusterRollExists(MusterRoll musterRoll) {
        MusterRollSearchCriteria.MusterRollSearchCriteriaBuilder criteriaBuilder =
            MusterRollSearchCriteria.builder()
                .tenantId(musterRoll.getTenantId())
                .registerId(musterRoll.getRegisterId());

        // V2 Flow: Check for duplicate using registerId + billingPeriodId
        if (StringUtils.isNotBlank(musterRoll.getBillingPeriodId())) {
            log.info("checkMusterRollExists::V2 flow - checking register {} in period {}",
                musterRoll.getRegisterId(), musterRoll.getBillingPeriodId());
            criteriaBuilder.billingPeriodId(musterRoll.getBillingPeriodId());
        } else {
            // V1 Flow: Check for duplicate using registerId + startDate + endDate
            log.info("checkMusterRollExists::V1 flow - checking register {} from {} to {}",
                musterRoll.getRegisterId(), musterRoll.getStartDate(), musterRoll.getEndDate());
            criteriaBuilder.fromDate(musterRoll.getStartDate())
                          .toDate(musterRoll.getEndDate());
        }

        MusterRollSearchCriteria searchCriteria = criteriaBuilder.build();
        List<MusterRoll> musterRolls = musterRollRepository.getMusterRoll(searchCriteria, null);

        if (!CollectionUtils.isEmpty(musterRolls)) {
            StringBuilder exceptionMessage = new StringBuilder();
            exceptionMessage.append("Muster roll already exists for register: ")
                          .append(musterRoll.getRegisterId());

            if (StringUtils.isNotBlank(musterRoll.getBillingPeriodId())) {
                // V2 error message
                exceptionMessage.append(" in billing period: ")
                              .append(musterRoll.getBillingPeriodId());
            } else {
                // V1 error message
                exceptionMessage.append(" with startDate: ")
                              .append(musterRoll.getStartDate())
                              .append(" and endDate: ")
                              .append(musterRoll.getEndDate());
            }

            throw new CustomException("DUPLICATE_MUSTER_ROLL", exceptionMessage.toString());
        }
    }

    /**
     * Validates that the period/register is not locked due to billing.
     * Once a bill is generated for a period, NO edits are allowed to muster roll or attendance.
     *
     * This ensures data consistency between intermediate bills and aggregate bills.
     *
     * V2 Flow: Checks if billingPeriodId has been billed
     * V1 Flow: Checks if registerId has been billed
     *
     * @param musterRoll Muster roll being updated
     * @param requestInfo Request info
     * @throws CustomException if period/register is locked
     */
    private void validatePeriodNotLocked(MusterRoll musterRoll, RequestInfo requestInfo) {
        log.info("validatePeriodNotLocked::Checking if period is locked for muster roll: {}", musterRoll.getId());

        // Get campaign number (project ID) from register
        String campaignNumber = null;
        try {
            AttendanceRegisterResponse registerResponse = musterRollServiceUtil.fetchAttendanceRegister(
                requestInfo, musterRoll.getTenantId(), musterRoll.getRegisterId());
            if (registerResponse != null && !CollectionUtils.isEmpty(registerResponse.getAttendanceRegister())) {
                campaignNumber = registerResponse.getAttendanceRegister().get(0).getReferenceId();
            }
        } catch (Exception e) {
            log.warn("validatePeriodNotLocked::Could not fetch register to get campaign number: {}", e.getMessage());
            // If we can't fetch register, fail-open (allow update)
            return;
        }

        if (StringUtils.isBlank(campaignNumber)) {
            log.warn("validatePeriodNotLocked::No campaign number found, skipping lock check");
            return;
        }

        // Check if period is billed
        boolean isBilled = musterRollServiceUtil.isPeriodBilled(
            musterRoll.getBillingPeriodId(),  // V2: period ID, V1: null
            musterRoll.getRegisterId(),       // Register ID for V1 fallback
            musterRoll.getTenantId(),         // Tenant ID
            campaignNumber,                   // Project/Campaign ID
            requestInfo                       // Request info
        );

        if (isBilled) {
            // Period is locked - throw error
            String periodInfo;
            if (StringUtils.isNotBlank(musterRoll.getBillingPeriodId())) {
                // V2 Flow
                periodInfo = "billing period " + musterRoll.getBillingPeriodId();
            } else {
                // V1 Flow
                periodInfo = "register " + musterRoll.getRegisterId() +
                    " (dates: " + musterRoll.getStartDate() + " to " + musterRoll.getEndDate() + ")";
            }

            String errorMessage = String.format(
                "Cannot edit muster roll - intermediate bill already generated for %s. " +
                "Period is locked. All data (muster rolls and attendance) is frozen after billing. " +
                "Contact administrator to void/cancel the bill before making changes.",
                periodInfo
            );

            log.error("validatePeriodNotLocked::PERIOD_LOCKED - {}", errorMessage);
            throw new CustomException("PERIOD_LOCKED", errorMessage);
        }

        log.info("validatePeriodNotLocked::Period is not locked, update allowed");
    }

    /**
     * Fetch the existing muster roll from DB else throw error
     * @param musterRoll
     * @return
     */
    private MusterRoll fetchExistingMusterRoll(MusterRoll musterRoll) {
        List<String> ids = new ArrayList<>();
        ids.add(musterRoll.getId());
        MusterRollSearchCriteria searchCriteria = MusterRollSearchCriteria.builder().ids(ids).tenantId(musterRoll.getTenantId()).build();
        List<MusterRoll> musterRolls = musterRollRepository.getMusterRoll(searchCriteria,null);
        if (CollectionUtils.isEmpty(musterRolls)) {
            throw new CustomException("NO_MATCH_FOUND","Invalid Muster roll id - "+musterRoll.getId());
        }
        return musterRolls.get(0);
    }

    /**
     * Check if the 'computeAttendance' flag is true
     * @param musterRoll
     * @return
     */
    private boolean isComputeAttendance (MusterRoll musterRoll) {
       if (config.isRecomputeAttendanceEnabled() && musterRoll.getAdditionalDetails() != null) {
           try {
               JsonNode node = mapper.readTree(mapper.writeValueAsString(musterRoll.getAdditionalDetails()));
               if (node.findValue(COMPUTE_ATTENDENSE) != null && StringUtils.isNotBlank(node.findValue(COMPUTE_ATTENDENSE).textValue())) {
                   String value = node.findValue(COMPUTE_ATTENDENSE).textValue();
                   return BooleanUtils.toBoolean(value);
               }
           } catch (IOException e) {
               log.info("MusterRollService::isComputeAttendance::Failed to parse additionalDetail object from request"+e);
               throw new CustomException("PARSING ERROR", "Failed to parse additionalDetail object from request on update");
           }
       }
       return false;
    }

    /**
     * Fetch the registerIds that the user belongs to ( if role is org_staff or org_admin)
     * @param requestInfo
     * @return
     */
    private List<String>  fetchAttendanceRegistersOfUser(RequestInfo requestInfo, MusterRollSearchCriteria searchCriteria) {
        String id = requestInfo.getUserInfo().getUuid();

        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId",searchCriteria.getTenantId())
                .queryParam("status", Status.ACTIVE)
                .queryParam("limit", config.getAttendanceRegisterSearchLimit());
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceRegisterResponse attendanceRegisterResponse = null;
        log.info("MusterRollService::fetchAttendanceRegistersOfUser::call attendance register search with tenantId::"+searchCriteria.getTenantId()
                +"::for user::"+id);

        try {
            attendanceRegisterResponse  = restTemplate.postForObject(uriBuilder.toUriString(),requestInfoWrapper,AttendanceRegisterResponse.class);
        }  catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("MusterRollService::fetchAttendanceRegistersOfUser::Error thrown from attendance register service::"+httpClientOrServerExc.getStatusCode());
            throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION","Error thrown from attendance register service::"+httpClientOrServerExc.getStatusCode());
        }

        if (attendanceRegisterResponse == null || CollectionUtils.isEmpty(attendanceRegisterResponse.getAttendanceRegister())) {
            throw new CustomException("NO_DATA_FOUND","No Attendance registers found for the user. So no muster created for the register");
        }

        List<AttendanceRegister> attendanceRegisters = attendanceRegisterResponse.getAttendanceRegister();
        return attendanceRegisters.stream()
                .map(AttendanceRegister::getId)
                .collect(Collectors.toList());
    }

    /**
     * V2 Intermediate Billing - Publish Muster Roll Status Update Event
     *
     * ================================================================================
     * WHY THIS METHOD EXISTS - EVENT-DRIVEN DENORMALIZATION
     * ================================================================================
     *
     * PROBLEM SOLVED:
     * ---------------
     * In V2, attendance search needs to show muster roll status for each period.
     * Option A: API call to muster-roll service during search (SLOW - N API calls)
     * Option B: Store status in attendance register (FAST - 0 API calls)
     *
     * We chose Option B: Event-driven denormalization.
     *
     * HOW IT WORKS:
     * -------------
     * 1. Muster roll status changes (CREATE or UPDATE workflow action)
     * 2. This method publishes MusterRollStatusUpdateEvent to Kafka
     * 3. Attendance service MusterRollStatusUpdateConsumer receives event
     * 4. Consumer updates period_statuses JSONB in eg_wms_attendance_register
     * 5. Next attendance search returns status from local data (no API call)
     *
     * WHY KAFKA (vs synchronous API):
     * --------------------------------
     * 1. DECOUPLED: Muster-roll doesn't need to know attendance service internals
     * 2. RESILIENT: If attendance is down, events are queued (not lost)
     * 3. SCALABLE: Can add more consumers without changing producer
     * 4. PERFORMANCE: Non-blocking - muster update completes immediately
     *
     * WHY V2 ONLY:
     * ------------
     * V1 uses register.reviewStatus (1:1 mapping, updated synchronously).
     * V2 uses period_statuses array (1:many mapping, updated via events).
     * V1 events would confuse the consumer (no billingPeriodId to match).
     *
     * ERROR HANDLING:
     * ---------------
     * Catches exceptions but DOES NOT fail the main workflow.
     * Reason: Event publishing is "best effort" - if it fails, the next
     * muster update will send the correct status anyway.
     *
     * ================================================================================
     *
     * @param musterRoll The muster roll with updated status
     * @param previousStatus The previous status (for audit/logging)
     */
    private void publishMusterRollStatusUpdateEvent(MusterRoll musterRoll, String previousStatus) {
        // Only publish for V2 muster rolls (with billingPeriodId)
        if (StringUtils.isBlank(musterRoll.getBillingPeriodId())) {
            log.debug("publishMusterRollStatusUpdateEvent::Skipping event for V1 muster roll: {}", musterRoll.getId());
            return;
        }

        try {
            MusterRollStatusUpdateEvent event = MusterRollStatusUpdateEvent.builder()
                    .musterRollId(musterRoll.getId())
                    .registerId(musterRoll.getRegisterId())
                    .billingPeriodId(musterRoll.getBillingPeriodId())
                    .status(musterRoll.getMusterRollStatus())
                    .tenantId(musterRoll.getTenantId())
                    .eventTime(System.currentTimeMillis())
                    .previousStatus(previousStatus)
                    .referenceId(musterRoll.getReferenceId())
                    .additionalDetails(musterRoll.getAdditionalDetails())
                    .build();

            String topic = serviceConfiguration.getMusterRollStatusUpdateTopic();
            log.info("publishMusterRollStatusUpdateEvent::Publishing event to topic: {} for muster: {} period: {} status: {}",
                    topic, musterRoll.getId(), musterRoll.getBillingPeriodId(), musterRoll.getMusterRollStatus());

            musterRollProducer.push(musterRoll.getTenantId(), topic, event);

            log.info("publishMusterRollStatusUpdateEvent::Event published successfully for muster: {}", musterRoll.getId());
        } catch (Exception e) {
            // Log error but don't fail the main workflow
            log.error("publishMusterRollStatusUpdateEvent::Failed to publish status update event for muster: {} - Error: {}",
                    musterRoll.getId(), e.getMessage(), e);
        }
    }

    /**
     * Applies the limit and offset
     * @param searchCriteria
     * @param musterRollList
     * @return
     */
    private List<MusterRoll> applyLimitAndOffset(MusterRollSearchCriteria searchCriteria, List<MusterRoll> musterRollList) {
        return musterRollList.stream()
                        .skip(searchCriteria.getOffset())  // offset
                        .limit(searchCriteria.getLimit()) // limit
                        .collect(Collectors.toList());
    }
}
