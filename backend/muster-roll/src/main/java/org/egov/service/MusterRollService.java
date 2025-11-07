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
        if(config.isMusterRollWorkflowEnabled()) {
            workflowService.updateWorkflowStatus(musterRollRequest);
        }
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
