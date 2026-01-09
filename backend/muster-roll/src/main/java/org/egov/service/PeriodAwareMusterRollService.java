package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.repository.MusterRollRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.IdgenUtil;
import org.egov.util.MusterRollServiceUtil;
import org.egov.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PeriodAwareMusterRollService
 *
 * Service to handle period-specific muster roll generation for V2 intermediate billing.
 * This service creates muster rolls that are linked to specific billing periods and
 * calculates attendance aggregation within period boundaries.
 *
 * Key Features:
 * - Prevents duplicate muster rolls per register+period combination
 * - Calculates intersection between register dates and billing period dates
 * - Aggregates daily attendance entries within period boundaries
 * - Supports V1 backward compatibility (creates muster rolls without billing_period_id)
 */
@Service
@Slf4j
public class PeriodAwareMusterRollService {

    private final MusterRollRepository musterRollRepository;
    private final MusterRollServiceUtil musterRollServiceUtil;
    private final MusterRollServiceConfiguration config;
    private final IdgenUtil idgenUtil;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final EnrichmentService enrichmentService;
    private final CalculationService calculationService;
    private final org.egov.validator.PeriodAwareMusterRollValidator periodAwareMusterRollValidator;

    @Autowired
    public PeriodAwareMusterRollService(MusterRollRepository musterRollRepository,
                                        MusterRollServiceUtil musterRollServiceUtil,
                                        MusterRollServiceConfiguration config,
                                        IdgenUtil idgenUtil,
                                        ObjectMapper mapper,
                                        RestTemplate restTemplate,
                                        EnrichmentService enrichmentService,
                                        CalculationService calculationService,
                                        org.egov.validator.PeriodAwareMusterRollValidator periodAwareMusterRollValidator) {
        this.musterRollRepository = musterRollRepository;
        this.musterRollServiceUtil = musterRollServiceUtil;
        this.config = config;
        this.idgenUtil = idgenUtil;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.enrichmentService = enrichmentService;
        this.calculationService = calculationService;
        this.periodAwareMusterRollValidator = periodAwareMusterRollValidator;
    }

    /**
     * Get or create muster rolls for a billing period.
     *
     * For each register:
     * 1. Check if muster roll already exists for this register+period
     * 2. If not, create new muster roll with period-specific attendance
     * 3. If yes, return existing muster roll
     *
     * This method is called by health-expense-calculator service during V2 bill generation.
     *
     * @param requestInfo Request info with user details
     * @param tenantId Tenant ID
     * @param registerIds List of attendance register IDs
     * @param billingPeriod Billing period details (id, startDate, endDate, periodNumber)
     * @return List of muster rolls for the billing period
     */
    public List<MusterRoll> getOrCreatePeriodicMusterRolls(RequestInfo requestInfo,
                                                           String tenantId,
                                                           List<String> registerIds,
                                                           BillingPeriodDetails billingPeriod) {
        log.info("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - Processing {} registers for period {} (campaign: {})",
                registerIds != null ? registerIds.size() : 0,
                billingPeriod != null ? billingPeriod.getPeriodNumber() : "null",
                billingPeriod != null ? billingPeriod.getCampaignNumber() : "null");

        // Validate all inputs
        periodAwareMusterRollValidator.validatePeriodicMusterRollRequest(requestInfo, tenantId, registerIds, billingPeriod);

        if (CollectionUtils.isEmpty(registerIds)) {
            log.warn("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - No register IDs provided");
            return Collections.emptyList();
        }

        List<MusterRoll> periodMusterRolls = new ArrayList<>();

        for (String registerId : registerIds) {
            try {
                // Check if muster roll already exists for this register+period
                MusterRoll existingMusterRoll = findExistingMusterRollForPeriod(registerId, billingPeriod.getId(), tenantId);

                if (existingMusterRoll != null) {
                    log.info("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - Muster roll already exists: {} for register {} and period {}",
                            existingMusterRoll.getId(), registerId, billingPeriod.getPeriodNumber());
                    periodMusterRolls.add(existingMusterRoll);
                } else {
                    // Create new muster roll for this period
                    log.info("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - Creating new muster roll for register {} and period {}",
                            registerId, billingPeriod.getPeriodNumber());
                    MusterRoll newMusterRoll = createPeriodicMusterRoll(requestInfo, tenantId, registerId, billingPeriod);
                    periodMusterRolls.add(newMusterRoll);
                }
            } catch (Exception e) {
                log.error("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - Error processing register {}: {}",
                        registerId, e.getMessage(), e);
                // Continue processing other registers even if one fails
            }
        }

        log.info("PeriodAwareMusterRollService::getOrCreatePeriodicMusterRolls - Successfully processed {} muster rolls for period {}",
                periodMusterRolls.size(), billingPeriod.getPeriodNumber());

        return periodMusterRolls;
    }

    /**
     * Find existing muster roll for a specific register and billing period.
     * Used for duplicate prevention.
     *
     * @param registerId Attendance register ID
     * @param billingPeriodId Billing period ID
     * @param tenantId Tenant ID
     * @return Existing muster roll or null
     */
    private MusterRoll findExistingMusterRollForPeriod(String registerId, String billingPeriodId, String tenantId) {
        MusterRollSearchCriteria searchCriteria = MusterRollSearchCriteria.builder()
                .tenantId(tenantId)
                .registerId(registerId)
                .billingPeriodId(billingPeriodId)
                .build();

        List<MusterRoll> existingMusterRolls = musterRollRepository.getMusterRoll(searchCriteria, null);

        if (!CollectionUtils.isEmpty(existingMusterRolls)) {
            if (existingMusterRolls.size() > 1) {
                log.warn("PeriodAwareMusterRollService::findExistingMusterRollForPeriod - Multiple muster rolls found for register {} and period {}. Returning first one.",
                        registerId, billingPeriodId);
            }
            return existingMusterRolls.get(0);
        }

        return null;
    }

    /**
     * Create a new muster roll for a specific billing period.
     *
     * Steps:
     * 1. Fetch attendance register details
     * 2. Calculate intersection dates (register dates ∩ period dates)
     * 3. Create muster roll with period metadata
     * 4. Use existing CalculationService to aggregate attendance for intersection dates
     * 5. Link muster roll to billing period via billing_period_id
     *
     * @param requestInfo Request info with user details
     * @param tenantId Tenant ID
     * @param registerId Attendance register ID
     * @param billingPeriod Billing period details
     * @return Created muster roll
     */
    private MusterRoll createPeriodicMusterRoll(RequestInfo requestInfo,
                                                String tenantId,
                                                String registerId,
                                                BillingPeriodDetails billingPeriod) {
        log.info("PeriodAwareMusterRollService::createPeriodicMusterRoll - Creating muster roll for register {} and period {}",
                registerId, billingPeriod.getPeriodNumber());

        // Step 1: Fetch attendance register details
        AttendanceRegisterResponse registerResponse = fetchAttendanceRegister(requestInfo, tenantId, registerId);
        if (registerResponse == null || CollectionUtils.isEmpty(registerResponse.getAttendanceRegister())) {
            throw new CustomException("REGISTER_NOT_FOUND",
                    "Attendance register not found. registerId: " + registerId +
                    ", tenantId: " + tenantId +
                    ", billingPeriodId: " + billingPeriod.getId());
        }

        AttendanceRegister register = registerResponse.getAttendanceRegister().get(0);

        // Step 2: Calculate intersection between register and period dates
        DateIntersection intersection = calculateIntersection(
                register.getStartDate(),
                register.getEndDate(),
                billingPeriod.getPeriodStartDate(),
                billingPeriod.getPeriodEndDate()
        );

        if (!intersection.hasIntersection()) {
            log.warn("PeriodAwareMusterRollService::createPeriodicMusterRoll - No intersection between register {} and period {}. Skipping.",
                    registerId, billingPeriod.getPeriodNumber());
            throw new CustomException("NO_INTERSECTION",
                    "Register dates do not overlap with billing period dates. Register: " +
                            formatDate(register.getStartDate()) + " to " + formatDate(register.getEndDate()) +
                            ", Period: " + formatDate(billingPeriod.getPeriodStartDate()) + " to " + formatDate(billingPeriod.getPeriodEndDate()));
        }

        log.info("PeriodAwareMusterRollService::createPeriodicMusterRoll - Intersection for register {}: {} to {} ({} days)",
                registerId,
                formatDate(intersection.getStartDate()),
                formatDate(intersection.getEndDate()),
                intersection.getDays());

        // Validate intersection exists
        periodAwareMusterRollValidator.validateDateIntersection(registerId, billingPeriod, intersection.hasIntersection());

        // Step 3: Create muster roll object with period-specific dates
        MusterRoll musterRoll = MusterRoll.builder()
                .tenantId(tenantId)
                .registerId(registerId)
                .startDate(BigDecimal.valueOf(intersection.getStartDate()))
                .endDate(BigDecimal.valueOf(intersection.getEndDate()))
                .referenceId(register.getReferenceId())
                .serviceCode(register.getServiceCode())
                .billingPeriodId(billingPeriod.getId())  // V2: Link to billing period
                .build();

        // Step 4: Create muster roll request for processing
        MusterRollRequest musterRollRequest = MusterRollRequest.builder()
                .requestInfo(requestInfo)
                .musterRoll(musterRoll)
                .build();

        // Step 5: Enrich muster roll with IDs and audit details
        enrichmentService.enrichMusterRollOnCreate(musterRollRequest);

        // Step 6: Use existing CalculationService to compute attendance
        // The CalculationService will aggregate attendance entries within the muster roll's startDate and endDate
        // which are now set to the intersection dates
        calculationService.createAttendance(musterRollRequest, true);

        // Step 7: Set status for V2 muster rolls
        // V2 muster rolls are auto-approved for intermediate billing
        if (!config.isMusterRollWorkflowEnabled()) {
            musterRoll.setMusterRollStatus(config.getMusterRollNoWorkflowCreateStatus());
        }

        log.info("PeriodAwareMusterRollService::createPeriodicMusterRoll - Successfully created muster roll {} for register {} and period {}",
                musterRoll.getId(), registerId, billingPeriod.getPeriodNumber());

        return musterRoll;
    }

    /**
     * Calculate intersection between register dates and period dates.
     *
     * Intersection logic:
     * - Start date = MAX(register start, period start)
     * - End date = MIN(register end, period end)
     * - If start > end, no intersection exists
     *
     * @param registerStart Register start timestamp (milliseconds)
     * @param registerEnd Register end timestamp (milliseconds)
     * @param periodStart Period start timestamp (milliseconds)
     * @param periodEnd Period end timestamp (milliseconds)
     * @return DateIntersection with intersection details
     */
    private DateIntersection calculateIntersection(BigDecimal registerStart, BigDecimal registerEnd,
                                                   Long periodStart, Long periodEnd) {
        long regStart = registerStart.longValue();
        long regEnd = registerEnd.longValue();

        // Calculate intersection start (later of the two starts)
        long intersectionStart = Math.max(regStart, periodStart);

        // Calculate intersection end (earlier of the two ends)
        long intersectionEnd = Math.min(regEnd, periodEnd);

        // Check if intersection exists
        boolean hasIntersection = intersectionStart <= intersectionEnd;

        if (!hasIntersection) {
            return DateIntersection.builder()
                    .hasIntersection(false)
                    .startDate(0L)
                    .endDate(0L)
                    .durationMs(0L)
                    .days(0)
                    .build();
        }

        // Calculate duration
        long durationMs = intersectionEnd - intersectionStart + 1;
        int days = (int) Math.ceil(durationMs / (24.0 * 60 * 60 * 1000));

        return DateIntersection.builder()
                .hasIntersection(true)
                .startDate(intersectionStart)
                .endDate(intersectionEnd)
                .durationMs(durationMs)
                .days(days)
                .build();
    }

    /**
     * Fetch attendance register by ID.
     *
     * @param requestInfo Request info
     * @param tenantId Tenant ID
     * @param registerId Register ID
     * @return Attendance register response
     */
    private AttendanceRegisterResponse fetchAttendanceRegister(RequestInfo requestInfo, String tenantId, String registerId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("RequestInfo", requestInfo);

        AttendanceRegisterSearchCriteria searchCriteria = new AttendanceRegisterSearchCriteria();
        searchCriteria.setTenantId(tenantId);
        searchCriteria.setIds(Collections.singletonList(registerId));

        requestBody.put("searchCriteria", searchCriteria);

        try {
            AttendanceRegisterResponse response = restTemplate.postForObject(
                    uri.toString(),
                    requestBody,
                    AttendanceRegisterResponse.class
            );

            if (response == null || CollectionUtils.isEmpty(response.getAttendanceRegister())) {
                log.error("PeriodAwareMusterRollService::fetchAttendanceRegister - No attendance register found for ID: {}", registerId);
            }

            return response;
        } catch (Exception e) {
            log.error("PeriodAwareMusterRollService::fetchAttendanceRegister - Error fetching register {}: {}", registerId, e.getMessage(), e);
            throw new CustomException("ATTENDANCE_REGISTER_FETCH_ERROR",
                    "Failed to fetch attendance register: " + e.getMessage());
        }
    }

    /**
     * Format timestamp to readable date string.
     *
     * @param timestamp Timestamp in milliseconds
     * @return Formatted date string (yyyy-MM-dd)
     */
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp));
    }

    /**
     * Format BigDecimal timestamp to readable date string.
     *
     * @param timestamp Timestamp as BigDecimal
     * @return Formatted date string (yyyy-MM-dd)
     */
    private String formatDate(BigDecimal timestamp) {
        if (timestamp == null) {
            return "null";
        }
        return formatDate(timestamp.longValue());
    }

    /**
     * BillingPeriodDetails
     *
     * DTO to hold billing period information passed from health-expense-calculator service.
     * This avoids tight coupling between services.
     */
    public static class BillingPeriodDetails {
        private String id;
        private Integer periodNumber;
        private Long periodStartDate;
        private Long periodEndDate;
        private String campaignNumber;

        public BillingPeriodDetails() {
        }

        public BillingPeriodDetails(String id, Integer periodNumber, Long periodStartDate, Long periodEndDate, String campaignNumber) {
            this.id = id;
            this.periodNumber = periodNumber;
            this.periodStartDate = periodStartDate;
            this.periodEndDate = periodEndDate;
            this.campaignNumber = campaignNumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getPeriodNumber() {
            return periodNumber;
        }

        public void setPeriodNumber(Integer periodNumber) {
            this.periodNumber = periodNumber;
        }

        public Long getPeriodStartDate() {
            return periodStartDate;
        }

        public void setPeriodStartDate(Long periodStartDate) {
            this.periodStartDate = periodStartDate;
        }

        public Long getPeriodEndDate() {
            return periodEndDate;
        }

        public void setPeriodEndDate(Long periodEndDate) {
            this.periodEndDate = periodEndDate;
        }

        public String getCampaignNumber() {
            return campaignNumber;
        }

        public void setCampaignNumber(String campaignNumber) {
            this.campaignNumber = campaignNumber;
        }
    }

    /**
     * DateIntersection
     *
     * Helper class to hold date intersection calculation results.
     */
    private static class DateIntersection {
        private boolean hasIntersection;
        private long startDate;
        private long endDate;
        private long durationMs;
        private int days;

        private DateIntersection(Builder builder) {
            this.hasIntersection = builder.hasIntersection;
            this.startDate = builder.startDate;
            this.endDate = builder.endDate;
            this.durationMs = builder.durationMs;
            this.days = builder.days;
        }

        public boolean hasIntersection() {
            return hasIntersection;
        }

        public long getStartDate() {
            return startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public long getDurationMs() {
            return durationMs;
        }

        public int getDays() {
            return days;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private boolean hasIntersection;
            private long startDate;
            private long endDate;
            private long durationMs;
            private int days;

            public Builder hasIntersection(boolean hasIntersection) {
                this.hasIntersection = hasIntersection;
                return this;
            }

            public Builder startDate(long startDate) {
                this.startDate = startDate;
                return this;
            }

            public Builder endDate(long endDate) {
                this.endDate = endDate;
                return this;
            }

            public Builder durationMs(long durationMs) {
                this.durationMs = durationMs;
                return this;
            }

            public Builder days(int days) {
                this.days = days;
                return this;
            }

            public DateIntersection build() {
                return new DateIntersection(this);
            }
        }
    }

    /**
     * AttendanceRegisterSearchCriteria
     *
     * Helper class for attendance register search.
     */
    private static class AttendanceRegisterSearchCriteria {
        private String tenantId;
        private List<String> ids;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }
    }
}
