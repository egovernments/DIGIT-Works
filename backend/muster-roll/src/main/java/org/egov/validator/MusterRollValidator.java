package org.egov.validator;

import com.jayway.jsonpath.JsonPath;
import org.egov.common.contract.models.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.util.MdmsUtil;
import org.egov.util.MusterRollServiceUtil;
import org.egov.web.models.*;
import org.egov.works.services.common.models.musterroll.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.egov.util.MusterRollServiceConstants.*;

@Component
@Slf4j
public class MusterRollValidator {

    private final MusterRollServiceConfiguration serviceConfiguration;

    private final MdmsUtil mdmsUtils;

    private final MusterRollServiceUtil musterRollServiceUtil;

    private final RestTemplate restTemplate;

    private static final String TENANT_ID = "TENANT_ID";
    private static final String MUSTER_ROLL = "MUSTER_ROLL";
    private static final String MUSTER_ROLL_IS_MANADATORY = "Muster roll is mandatory";
    private static final String TENANT_ID_IS_MANADATORY = "TenantId is mandatory";

    @Autowired
    public MusterRollValidator(MusterRollServiceConfiguration serviceConfiguration, MdmsUtil mdmsUtils, MusterRollServiceUtil musterRollServiceUtil, RestTemplate restTemplate) {
        this.serviceConfiguration = serviceConfiguration;
        this.mdmsUtils = mdmsUtils;
        this.musterRollServiceUtil = musterRollServiceUtil;
        this.restTemplate = restTemplate;
    }

    /**
     * Validate muster roll in estimate service
     * @param musterRollRequest
     */
    public void validateEstimateMusterRoll(MusterRollRequest musterRollRequest){
        log.info("MusterRollValidator::validateEstimateMusterRoll");

        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();

        validateRequestInfo(requestInfo);
        validateEstimateMusterRollRequest(musterRoll);

        // V2 Enhancement: Check for billing period and validate dates
        validateAndEnrichWithBillingPeriod(musterRoll, requestInfo, errorMap, false);

        //split the tenantId and validate tenantId
        String tenantId = musterRoll.getTenantId();
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, tenantId);
        validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * Validate muster roll in create service
     * @param musterRollRequest
     */
    public void validateCreateMusterRoll(MusterRollRequest musterRollRequest) {
        log.info("MusterRollValidator::validateCreateMusterRoll");

        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        Workflow workflow = musterRollRequest.getWorkflow();

        validateRequestInfo(requestInfo);
        validateCreateMusterRollRequest(musterRoll);

        // V2 Enhancement: Check for billing period and validate dates
        validateAndEnrichWithBillingPeriod(musterRoll, requestInfo, errorMap, true);

        if(serviceConfiguration.isValidateAttendanceRegisterEnabled()) {
            validateAndEnrichAttendance(musterRoll, requestInfo, true);
        }
        if(serviceConfiguration.isMusterRollWorkflowEnabled()) {
            validateWorkFlow(workflow, errorMap);
        }

        //split the tenantId and validate tenantId
        String tenantId = musterRoll.getTenantId();
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, tenantId);
        validateMDMSData(musterRoll, mdmsData, errorMap);

        //check if the user is enrolled in the attendance register
        isValidUser(musterRoll, requestInfo);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * Validate muster roll in update service
     * @param musterRollRequest
     */
    public void validateUpdateMusterRoll(MusterRollRequest musterRollRequest) {
        log.info("MusterRollValidator::validateUpdateMusterRoll");

        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        RequestInfo requestInfo = musterRollRequest.getRequestInfo();
        Workflow workflow = musterRollRequest.getWorkflow();

        validateRequestInfo(requestInfo);

        // V2: Validate period is not frozen (check if period is already billed)
        validatePeriodNotFrozen(musterRoll, requestInfo, errorMap);

        if(serviceConfiguration.isValidateAttendanceRegisterEnabled()) {
            validateAndEnrichAttendance(musterRoll, requestInfo, false);
        }
        // CAMPAIGN_SUPERVISOR editing an already-approved muster roll does not require
        // a workflow action — they update attendance values directly without a transition
        boolean isCampaignSupervisorEdit = isCampaignSupervisorEditingApproved(
                musterRoll.getMusterRollStatus(), requestInfo);
        if (!isCampaignSupervisorEdit && serviceConfiguration.isMusterRollWorkflowEnabled()) {
            validateWorkFlow(workflow, errorMap);
        }
        validateUpdateMusterRollRequest(musterRoll);

        //split the tenantId and validate tenantId
        String tenantId = musterRoll.getTenantId();
        Object mdmsData = mdmsUtils.mDMSCall(musterRollRequest, tenantId);
        validateMDMSData(musterRoll, mdmsData, errorMap);

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }

    }

    /**
     * V2: Validate that period is not frozen (not already billed)
     * This prevents muster roll edits after a period has been billed
     *
     * V1 Flow (billingPeriodId is NULL): Skip this validation
     * V2 Flow (billingPeriodId is present): Check period status is not "BILLED"
     *
     * @param musterRoll Muster roll to validate
     * @param requestInfo Request info
     * @param errorMap Error map to accumulate errors
     */
    private void validatePeriodNotFrozen(MusterRoll musterRoll, RequestInfo requestInfo, Map<String, String> errorMap) {
        // V1 Flow: Skip validation (no period association)
        if (StringUtils.isBlank(musterRoll.getBillingPeriodId())) {
            log.debug("validatePeriodNotFrozen::V1 flow - skipping period freeze check");
            return;
        }

        // V2 Flow: Check period status
        log.info("validatePeriodNotFrozen::V2 flow - checking period freeze status for period: {}",
                musterRoll.getBillingPeriodId());

        try {
            // Fetch billing period from expense-calculator service
            BillingPeriod period = musterRollServiceUtil.fetchBillingPeriod(
                    musterRoll.getBillingPeriodId(),
                    musterRoll.getTenantId(),
                    requestInfo
            );

            if (period == null) {
                errorMap.put("BILLING_PERIOD_NOT_FOUND",
                        "Billing period not found with ID: " + musterRoll.getBillingPeriodId());
                return;
            }

            // Check if period is BILLED (frozen)
            if ("BILLED".equalsIgnoreCase(period.getStatus())) {
                errorMap.put("PERIOD_FROZEN",
                        String.format("Cannot edit muster roll: Billing period %d is already BILLED (frozen). " +
                                "Muster rolls cannot be modified after bill generation.",
                                period.getPeriodNumber()));
                log.error("validatePeriodNotFrozen::Period {} is BILLED - cannot edit muster roll {}",
                        period.getPeriodNumber(), musterRoll.getId());
            } else {
                log.info("validatePeriodNotFrozen::Period {} status is {} - edit allowed",
                        period.getPeriodNumber(), period.getStatus());
            }
        } catch (Exception e) {
            log.error("validatePeriodNotFrozen::Error fetching billing period {}: {}",
                    musterRoll.getBillingPeriodId(), e.getMessage(), e);
            errorMap.put("BILLING_PERIOD_FETCH_ERROR",
                    "Error fetching billing period: " + e.getMessage());
        }
    }

    /**
     * Validate muster roll in search service
     * @param requestInfoWrapper
     * @param searchCriteria
     */
    public void validateSearchMuster(RequestInfoWrapper requestInfoWrapper, MusterRollSearchCriteria searchCriteria) {
        log.info("MusterRollValidator::validateSearchMuster");

        if (searchCriteria == null || requestInfoWrapper == null || requestInfoWrapper.getRequestInfo() == null) {
            throw new CustomException("MUSTER_ROLL_SEARCH_CRITERIA_REQUEST", "Muster roll search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException(TENANT_ID, "Tenant is mandatory");
        }
    }


    private void validateRequestInfo(RequestInfo requestInfo) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateCreateMusterRollRequest(MusterRoll musterRoll) {
        if (musterRoll == null) {
            throw new CustomException(MUSTER_ROLL,MUSTER_ROLL_IS_MANADATORY);
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException(TENANT_ID,TENANT_ID_IS_MANADATORY);
        }
        if (musterRoll.getRegisterId() == null) {
            throw new CustomException("REGISTER_ID","RegisterId is mandatory");
        }
        if (musterRoll.getStartDate() == null) {
            throw new CustomException("START_DATE_EMPTY","StartDate is mandatory");
        }

        //Check if the startDate is Monday - UI sends the epoch time in IST
        LocalDate startDate = Instant.ofEpochMilli(musterRoll.getStartDate().longValue()).atZone(ZoneId.of(serviceConfiguration.getTimeZone())).toLocalDate();
        if (serviceConfiguration.isValidateStartDateMondayEnabled() && startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new CustomException("START_DATE_MONDAY","StartDate should be Monday");
        }
        musterRoll.setStartDate(new BigDecimal(startDate.atStartOfDay(ZoneId.of(serviceConfiguration.getTimeZone())).toInstant().toEpochMilli()));

        log.info("MusterRollValidator::validateCreateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateCreateMusterRoll::startDate::"+startDate);
        log.info("MusterRollValidator::validateCreateMusterRoll::endDate in epoch from request::"+musterRoll.getEndDate());

        //Override the endDate as SUNDAY
        if (serviceConfiguration.isMusterRollSetDefaultDurationEnabled()) {
            LocalDate endDate = startDate.plusDays(serviceConfiguration.getMusterRollDefaultDuration());
            log.info("MusterRollValidator::validateCreateMusterRoll:: calculated endDate::"+endDate);
            musterRoll.setEndDate(new BigDecimal(endDate.atStartOfDay(ZoneId.of(serviceConfiguration.getTimeZone())).toInstant().toEpochMilli()));
        }
    }

    private void validateUpdateMusterRollRequest(MusterRoll musterRoll) {
        if (musterRoll == null) {
            throw new CustomException(MUSTER_ROLL,MUSTER_ROLL_IS_MANADATORY);
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException(TENANT_ID,TENANT_ID_IS_MANADATORY);
        }
        if (musterRoll.getId() == null) {
            throw new CustomException("MUSTER_ROLL_ID","MusterRollId is mandatory");
        }

    }

    private void validateEstimateMusterRollRequest(MusterRoll musterRoll) {
        if (musterRoll == null) {
            throw new CustomException(MUSTER_ROLL,MUSTER_ROLL_IS_MANADATORY);
        }
        if (musterRoll.getTenantId() == null) {
            throw new CustomException(TENANT_ID,TENANT_ID_IS_MANADATORY);
        }
        if (musterRoll.getRegisterId() == null) {
            throw new CustomException("REGISTER_ID","RegisterId is mandatory");
        }
        if (musterRoll.getStartDate() == null) {
            throw new CustomException("START_DATE_EMPTY","StartDate is mandatory");
        }
        //endDate is required for /_estimate musterroll - The estimate will be shown for last few days in the week in view/edit screen
        if (musterRoll.getEndDate() == null) {
            throw new CustomException("END_DATE_EMPTY","EndDate is mandatory");
        }

        log.info("MusterRollValidator::validateEstimateMusterRoll::startDate in epoch::"+musterRoll.getStartDate());
        log.info("MusterRollValidator::validateEstimateMusterRoll::endDate in epoch::"+musterRoll.getEndDate());

    }

    private boolean isCampaignSupervisorEditingApproved(String musterRollStatus, RequestInfo requestInfo) {
        if (!STATUS_APPROVED.equalsIgnoreCase(musterRollStatus)) return false;
        String campaignRole = serviceConfiguration.getCampaignSupervisorRole();
        return requestInfo.getUserInfo().getRoles().stream()
                .anyMatch(role -> campaignRole.equalsIgnoreCase(role.getCode()));
    }

    private void validateWorkFlow(Workflow workflow, Map<String, String> errorMap) {
        if (workflow == null) {
            throw new CustomException("WORK_FLOW", "Work flow is mandatory");
        }
        if (StringUtils.isBlank(workflow.getAction())) {
            errorMap.put("WORK_FLOW.ACTION", "Work flow's action is mandatory");
        }
    }

    private void validateMDMSData(MusterRoll musterRoll, Object mdmsData, Map<String, String> errorMap) {

        final String jsonPathForTenants = "$.MdmsRes." + MDMS_TENANT_MODULE_NAME + "." + MASTER_TENANTS + ".*";
        List<Object> tenantRes = null;

        try {
            tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);

        } catch (Exception e) {
            log.error("MusterRollValidator::validateMDMSData::"+e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }

        if (CollectionUtils.isEmpty(tenantRes)) {
            log.error("The tenant: " + musterRoll.getTenantId() + " is not present in MDMS");
            errorMap.put("INVALID_TENANT", "The tenant: " + musterRoll.getTenantId() + " is not present in MDMS");
        }

    }

    /**
     * Check if the user is valid. User should be enrolled as a staff in the attendance register
     * @param musterRoll
     * @param requestInfo
     */
    public void isValidUser(MusterRoll musterRoll, RequestInfo requestInfo) {
        String id = requestInfo.getUserInfo().getUuid();

        StringBuilder uri = new StringBuilder();
        uri.append(serviceConfiguration.getAttendanceLogHost()).append(serviceConfiguration.getAttendanceRegisterEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId",musterRoll.getTenantId())
                .queryParam("ids",musterRoll.getRegisterId())
                .queryParam("status", Status.ACTIVE);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceRegisterResponse attendanceRegisterResponse = null;
        log.info("MusterRollValidator::isValidUser::call attendance register search with tenantId::"+musterRoll.getTenantId()
                +"::for user::"+id);

        try {
            attendanceRegisterResponse  = restTemplate.postForObject(uriBuilder.toUriString(),requestInfoWrapper,AttendanceRegisterResponse.class);
        }  catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("MusterRollValidator::isValidUser::Error thrown from attendance register service::"+httpClientOrServerExc.getStatusCode());
            throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION","Error thrown from attendance register service::"+httpClientOrServerExc.getStatusCode());
        }

        if (attendanceRegisterResponse == null || CollectionUtils.isEmpty(attendanceRegisterResponse.getAttendanceRegister())) {
            log.error("MusterRollValidator::isValidUser::User with id::" + id + " is not enrolled in the attendance register::"+musterRoll.getRegisterId());
            throw new CustomException("INVALID_USER",
                    "User is not enrolled in the attendance register. userId: " + id +
                    ", registerId: " + musterRoll.getRegisterId() +
                    ", tenantId: " + musterRoll.getTenantId());
        }

    }

    public void validateAndEnrichAttendance(MusterRoll musterRoll, RequestInfo requestInfo, boolean isCreate) {
        log.info("MusterRollValidator::validateAndEnrichAttendance");
        AttendanceRegisterResponse attendanceRegisterResponse = musterRollServiceUtil
                .fetchAttendanceRegister(musterRoll, requestInfo);
        List<AttendanceRegister> attendanceRegisters = attendanceRegisterResponse.getAttendanceRegister();
        if(attendanceRegisters == null || attendanceRegisters.isEmpty()) {
            log.error("No attendance registers found for the muster roll");
            throw new CustomException("ATTENDANCE_REGISTER_NOT_FOUND",
                    "No attendance registers found for the muster roll. registerId: " + musterRoll.getRegisterId() +
                    ", tenantId: " + musterRoll.getTenantId());
        }
        AttendanceRegister attendanceRegister = attendanceRegisters.get(0);
        LocalDate startDate = Instant.ofEpochMilli(attendanceRegister.getStartDate().longValue()).atZone(ZoneId.of(serviceConfiguration.getTimeZone())).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(attendanceRegister.getEndDate().longValue()).atZone(ZoneId.of(serviceConfiguration.getTimeZone())).toLocalDate();

        // Calculate inclusive difference in days
        long inclusiveDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        log.info("Total number of days in attendance register: " + inclusiveDays);
        if (!isCreate && musterRoll.getIndividualEntries().stream()
                .anyMatch(entry ->
                        ((entry.getModifiedTotalAttendance() != null
                                && inclusiveDays < entry.getModifiedTotalAttendance().longValue()))
                || (entry.getActualTotalAttendance() != null
                && inclusiveDays < entry.getActualTotalAttendance().longValue())))
        {
            throw new CustomException("MusterRollValidator::validateAndEnrichAttendance::", "Attendance days can't be more than register days");
        }
        musterRoll.setStartDate(attendanceRegister.getStartDate());
        musterRoll.setEndDate(attendanceRegister.getEndDate());
    }

    /**
     * V2 Enhancement: Validates and enriches muster roll with billing period information.
     * Implements backward-compatible V1/V2 mode detection:
     *
     * - If billingPeriodId provided: Fetch period and override dates (V2 mode)
     * - If billing config exists for campaign: Auto-detect period from dates (V2 mode)
     * - If no billing config exists: Skip validation (V1 mode - backward compatible)
     *
     * @param musterRoll Muster roll to validate and enrich
     * @param requestInfo Request info
     * @param errorMap Error map to accumulate validation errors
     * @param isCreate True if called from create flow, false if from estimate
     */
    private void validateAndEnrichWithBillingPeriod(MusterRoll musterRoll, RequestInfo requestInfo,
                                                    Map<String, String> errorMap, boolean isCreate) {
        log.info("validateAndEnrichWithBillingPeriod::Checking for V2 billing period (isCreate: {})", isCreate);

        String tenantId = musterRoll.getTenantId();
        String registerId = musterRoll.getRegisterId();
        String billingPeriodId = musterRoll.getBillingPeriodId();

        try {
            // Step 1: Get campaign number from register
            String campaignNumber = musterRollServiceUtil.getCampaignNumberFromRegister(registerId, tenantId, requestInfo);
            if (campaignNumber == null || campaignNumber.isEmpty()) {
                log.info("validateAndEnrichWithBillingPeriod::No campaign number found - V1 mode (backward compatible)");
                return; // V1 mode - no validation needed
            }

            log.info("validateAndEnrichWithBillingPeriod::Campaign number: {}", campaignNumber);

            // Step 2: Check if campaign has billing configuration
            boolean hasBillingConfig = musterRollServiceUtil.checkIfCampaignHasBillingConfig(
                campaignNumber, tenantId, requestInfo);

            if (!hasBillingConfig) {
                log.info("validateAndEnrichWithBillingPeriod::No billing config for campaign {} - V1 mode (backward compatible)",
                    campaignNumber);
                return; // V1 mode - no validation needed
            }

            log.info("validateAndEnrichWithBillingPeriod::Campaign {} has billing config - V2 mode enabled", campaignNumber);

            BillingPeriod billingPeriod = null;

            // Step 3: Fetch billing period
            if (billingPeriodId != null && !billingPeriodId.isEmpty()) {
                // Case 1: billingPeriodId provided explicitly (UI selected period)
                log.info("validateAndEnrichWithBillingPeriod::Fetching period by ID: {}", billingPeriodId);
                billingPeriod = musterRollServiceUtil.fetchBillingPeriod(billingPeriodId, tenantId, requestInfo);

                if (billingPeriod == null) {
                    errorMap.put("BILLING_PERIOD_NOT_FOUND",
                        "Billing period not found with ID: " + billingPeriodId);
                    return;
                }

                // Override dates with period dates (Period takes precedence)
                log.info("validateAndEnrichWithBillingPeriod::Overriding dates with period dates: {} to {}",
                    billingPeriod.getPeriodStartDate(), billingPeriod.getPeriodEndDate());
                musterRoll.setStartDate(new java.math.BigDecimal(billingPeriod.getPeriodStartDate()));
                musterRoll.setEndDate(new java.math.BigDecimal(billingPeriod.getPeriodEndDate()));

            } else {
                // Case 2: No billingPeriodId - auto-detect from dates
                log.info("validateAndEnrichWithBillingPeriod::No billingPeriodId provided - auto-detecting from dates");

                Long startDate = musterRoll.getStartDate() != null ? musterRoll.getStartDate().longValue() : null;
                Long endDate = musterRoll.getEndDate() != null ? musterRoll.getEndDate().longValue() : null;

                if (startDate == null || endDate == null) {
                    errorMap.put("DATES_REQUIRED",
                        "Start and end dates are required when billingPeriodId is not provided for campaign with V2 billing");
                    return;
                }

                // Find which period these dates fall into
                billingPeriod = musterRollServiceUtil.findBillingPeriodForDates(
                    campaignNumber, tenantId, startDate, endDate, requestInfo);

                if (billingPeriod == null) {
                    String message = String.format(
                        "No billing period found for dates %d to %d in campaign %s. " +
                        "Dates must fall within a billing period for V2 intermediate billing.",
                        startDate, endDate, campaignNumber
                    );
                    log.error("validateAndEnrichWithBillingPeriod::{}", message);
                    errorMap.put("DATES_OUTSIDE_ALL_PERIODS", message);
                    return;
                }

                log.info("validateAndEnrichWithBillingPeriod::Auto-detected period: {} (period number: {})",
                    billingPeriod.getId(), billingPeriod.getPeriodNumber());

                // Enrich with detected period ID
                musterRoll.setBillingPeriodId(billingPeriod.getId());
            }

            // Step 4: Additional validation for create flow
            if (isCreate && billingPeriod != null) {
                // Check if period is already billed
                if ("BILLED".equalsIgnoreCase(billingPeriod.getStatus())) {
                    log.warn("validateAndEnrichWithBillingPeriod::Period {} is already billed (Bill ID: {})",
                        billingPeriod.getId(), billingPeriod.getBillId());
                    // Note: This is a warning, not blocking - muster roll can be created but won't be billed again
                }
            }

            log.info("validateAndEnrichWithBillingPeriod::V2 validation successful - Period: {}, Campaign: {}",
                billingPeriod.getId(), campaignNumber);

        } catch (CustomException e) {
            // Re-throw custom exceptions (these are validation errors)
            throw e;
        } catch (Exception e) {
            // Log and continue for unexpected errors - fallback to V1 mode for resilience
            log.error("validateAndEnrichWithBillingPeriod::Unexpected error during V2 validation - falling back to V1 mode: {}",
                e.getMessage(), e);
            // Don't fail the request - V1 mode will continue
        }
    }

}
