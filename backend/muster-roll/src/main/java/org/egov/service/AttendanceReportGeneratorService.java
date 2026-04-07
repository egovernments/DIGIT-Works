package org.egov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.Name;
import org.egov.common.models.individual.Skill;
import org.egov.common.models.individual.UserDetails;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.MusterRollProducer;
import org.egov.repository.MusterRollRepository;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.*;
import org.egov.web.models.*;
import org.egov.web.models.report.AttendanceReportData;
import org.egov.web.models.report.AttendanceReportDetail;
import org.egov.web.models.report.ReportGenerationRequest;
import org.egov.web.models.report.MusterRollReport;
import org.egov.web.models.report.ReportType;
import org.egov.web.models.report.ReportFormat;
import org.egov.web.models.report.ReportStatus;
import org.egov.repository.MusterRollReportRepository;
import org.egov.common.contract.models.AuditDetails;
import org.egov.web.models.worker.IndividualWorker;
import org.egov.works.services.common.models.attendance.AttendanceRegisterSearchCriteria;
import org.egov.works.services.common.models.attendance.StaffPermission;
import org.egov.works.services.common.models.attendance.StaffType;
import org.egov.works.services.common.models.musterroll.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttendanceReportGeneratorService {

    private final MusterRollRepository musterRollRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final AttendanceExcelGenerator excelGenerator;
    private final FileStoreUtil fileStoreUtil;
    private final MusterRollProducer musterRollProducer;
    private final MusterRollServiceConfiguration config;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final MusterRollReportRepository musterRollReportRepository;
    private final LocalizationUtil localizationUtil;
    private final WorkerRegistryUtil workerRegistryUtil;
    private final IndividualUtil individualUtil;

    @Autowired
    public AttendanceReportGeneratorService(
            MusterRollRepository musterRollRepository,
            ServiceRequestRepository serviceRequestRepository,
            AttendanceExcelGenerator excelGenerator,
            FileStoreUtil fileStoreUtil,
            MusterRollProducer musterRollProducer,
            MusterRollServiceConfiguration config,
            ObjectMapper objectMapper, RestTemplate restTemplate,
            MusterRollReportRepository musterRollReportRepository,
            LocalizationUtil localizationUtil, WorkerRegistryUtil workerRegistryUtil, IndividualUtil individualUtil) {
        this.musterRollRepository = musterRollRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.excelGenerator = excelGenerator;
        this.fileStoreUtil = fileStoreUtil;
        this.musterRollProducer = musterRollProducer;
        this.config = config;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.musterRollReportRepository = musterRollReportRepository;
        this.localizationUtil = localizationUtil;
        this.workerRegistryUtil = workerRegistryUtil;
        this.individualUtil = individualUtil;
    }

    public void initiateReportGeneration(String musterRollId, String tenantId,
                                          String reportType, String reportFormat,
                                          RequestInfo requestInfo) {
        try {
            // Fetch muster roll
            MusterRoll musterRoll = fetchMusterRoll(musterRollId, tenantId);
            if (musterRoll == null) {
                log.error("Muster roll not found: {}", musterRollId);
                throw new CustomException(AttendanceReportConstants.MUSTER_NOT_FOUND,
                        "Muster roll not found with id: " + musterRollId);
            }

            // Validate muster is approved
            if (!AttendanceReportConstants.REPORT_STATUS_COMPLETED.equalsIgnoreCase(musterRoll.getMusterRollStatus())
                    && !"APPROVED".equalsIgnoreCase(musterRoll.getMusterRollStatus())) {
                log.error("Muster roll not approved: {}", musterRollId);
                throw new CustomException(AttendanceReportConstants.MUSTER_NOT_APPROVED,
                        "Report can only be generated for approved muster rolls");
            }

            // Update status to INITIATED
            updateReportStatus(musterRoll, reportType, reportFormat,
                    ReportStatus.INITIATED.getValue(), null, null, requestInfo.getUserInfo().getUuid());

            // Publish to Kafka for async processing
            publishReportGenerationRequest(musterRollId, tenantId, reportType, reportFormat, requestInfo);

            log.info("Report generation initiated for muster roll: {} ({} {})",
                    musterRollId, reportType, reportFormat);

        } catch (CustomException e) {
            log.error("Error initiating report generation: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error initiating report generation: {}", e.getMessage(), e);
            throw new CustomException("REPORT_GENERATION_FAILED", "Failed to initiate report generation");
        }
    }

    public void generateReport(ReportGenerationRequest request) {
        MusterRoll musterRoll = null;
        String musterRollId = request.getMusterRollId();
        String tenantId = request.getTenantId();
        String reportType = request.getReportType();
        String reportFormat = request.getReportFormat();
        RequestInfo requestInfo = request.getRequestInfo();

        try {
            log.info("Starting report generation for muster roll: {} ({} {})",
                    musterRollId, reportType, reportFormat);

            // Fetch muster roll
            musterRoll = fetchMusterRoll(musterRollId, tenantId);
            if (musterRoll == null) {
                log.error("Muster roll not found: {}", musterRollId);
                return;
            }

            // Dispatch to type-specific generator using enums
            try {
                ReportType type = ReportType.fromValue(reportType);
                ReportFormat format = ReportFormat.fromValue(reportFormat);

                if (ReportType.ATTENDANCE_REPORT == type) {
                    if (ReportFormat.EXCEL == format) {
                        generateAttendanceExcelReport(musterRoll, musterRollId, tenantId, requestInfo,
                                reportType, reportFormat);
                    } else if (ReportFormat.PDF == format) {
                        log.info("PDF format for ATTENDANCE_REPORT not yet implemented for muster roll: {}",
                                musterRollId);
                        updateReportStatus(musterRoll, reportType, reportFormat,
                                ReportStatus.FAILED.getValue(), null,
                                "PDF format not yet implemented", requestInfo.getUserInfo().getUuid());
                    }
                } else {
                    log.info("Report type {} not yet implemented for muster roll: {}", reportType, musterRollId);
                    updateReportStatus(musterRoll, reportType, reportFormat,
                            ReportStatus.FAILED.getValue(), null,
                            "Report type not yet implemented", requestInfo.getUserInfo().getUuid());
                }
            } catch (IllegalArgumentException e) {
                log.error("Invalid report type or format: type={}, format={}", reportType, reportFormat, e);
                updateReportStatus(musterRoll, reportType, reportFormat,
                        ReportStatus.FAILED.getValue(), null,
                        "Invalid report type or format", requestInfo.getUserInfo().getUuid());
            }

        } catch (Exception e) {
            log.error("Unexpected error during report generation for muster roll: {} ({} {})",
                    musterRollId, reportType, reportFormat, e);
            if (musterRoll != null) {
                updateReportStatus(musterRoll, reportType, reportFormat,
                        AttendanceReportConstants.REPORT_STATUS_FAILED, null,
                        "Unexpected error: " + e.getMessage(), requestInfo.getUserInfo().getUuid());
            }
        }
    }

    private void generateAttendanceExcelReport(MusterRoll musterRoll, String musterRollId, String tenantId,
                                                RequestInfo requestInfo, String reportType, String reportFormat) {
        try {
            // Extract locale from requestInfo
            String locale = AttendanceReportConstants.LOCALIZATION_DEFAULT_LOCALE;
            if (requestInfo != null && requestInfo.getMsgId() != null && requestInfo.getMsgId().split("\\|").length > 1) {
                locale = requestInfo.getMsgId().split("\\|")[1];
            }

            // Extract root tenant (strip sub-tenant suffix)
            String rootTenantId = tenantId.split("\\.")[0];

            // Fetch localized messages
            Map<String, Map<String, String>> allMessages = localizationUtil.getLocalisedMessages(
                    requestInfo, rootTenantId, locale, config.getReportLocalizationModule());
            Map<String, String> messages = allMessages.getOrDefault(locale + "|" + rootTenantId,
                    Collections.emptyMap());

            // Fetch attendance data
            AttendanceReportData reportData = buildReportData(musterRoll, tenantId, requestInfo);

            // Collect all unique fileStoreIds from signature data and bulk-download images
            Map<String, byte[]> signatureImages = downloadSignatureImages(reportData, tenantId);

            // Generate Excel file
            byte[] excelContent = excelGenerator.generateExcel(reportData, messages, signatureImages);

            // Upload to FileStore
            String fileName = String.format("attendance-report-%s.xlsx", musterRollId);
            String fileStoreId = fileStoreUtil.uploadFile(excelContent, fileName, tenantId,
                    config.getReportFilestoreModule());

            if (fileStoreId == null) {
                log.error("Failed to upload Excel file to filestore for muster roll: {}", musterRollId);
                updateReportStatus(musterRoll, reportType, reportFormat,
                        ReportStatus.FAILED.getValue(), null,
                        "Failed to upload report to filestore", requestInfo.getUserInfo().getUuid());
                return;
            }

            // Update muster roll with report status and file store ID
            updateReportStatus(musterRoll, reportType, reportFormat,
                    ReportStatus.COMPLETED.getValue(), fileStoreId, null, requestInfo.getUserInfo().getUuid());

            log.info("Report generation completed successfully for muster roll: {} ({} {})",
                    musterRollId, reportType, reportFormat);

        } catch (IOException e) {
            log.error("Error generating Excel file for muster roll: {}", musterRollId, e);
            updateReportStatus(musterRoll, reportType, reportFormat,
                    ReportStatus.FAILED.getValue(), null,
                    "Error generating Excel file: " + e.getMessage(), requestInfo.getUserInfo().getUuid());
        }
    }

    private AttendanceReportData buildReportData(MusterRoll musterRoll, String tenantId, RequestInfo requestInfo) {
        // Fetch attendance register
        AttendanceRegister register = fetchAttendanceRegister(requestInfo, tenantId, musterRoll.getRegisterId());
        if (register == null) {
            throw new CustomException(AttendanceReportConstants.ATTENDANCE_REGISTER_NOT_FOUND,
                    "Attendance register not found with id: " + musterRoll.getRegisterId());
        }

        Long startDate;
        Long endDate;

        try {
            if (musterRoll.getAdditionalDetails() != null) {
                JsonNode rootNode = objectMapper.valueToTree(musterRoll.getAdditionalDetails());
                JsonNode billingNode = rootNode.get("billingPeriod");
                if (billingNode != null && !billingNode.isNull()) {
                    BillingPeriod period = objectMapper.convertValue(
                            billingNode,
                            BillingPeriod.class
                    );
                    startDate = period.getPeriodStartDate();
                    endDate = period.getPeriodEndDate();

                } else {
                    throw new IllegalStateException("billingPeriod not present in additionalDetails");
                }
            } else {
                throw new IllegalStateException("additionalDetails is null");
            }
        } catch (Exception e) {
            log.error("failed to parse billing period for the muster roll. falling back to muster roll startdate, enddate", e);
            startDate = musterRoll.getStartDate() != null ? musterRoll.getStartDate().longValue() : null;
            endDate = musterRoll.getEndDate() != null ? musterRoll.getEndDate().longValue() : null;
        }

    // Read sessions count from register additionalDetails (default 2)
        int sessions = 2;
        try {
            if (register.getAdditionalDetails() != null) {
                JsonNode regNode = objectMapper.valueToTree(register.getAdditionalDetails());
                JsonNode sessionsNode = regNode.get("sessions");
                if (sessionsNode != null && !sessionsNode.isNull()) {
                    sessions = sessionsNode.asInt(2);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to read sessions from register additionalDetails, defaulting to 2: {}", e.getMessage());
        }

        // Fetch ENTRY attendance logs and build signature lookup map
        List<AttendanceLog> entryLogs = fetchEntryAttendanceLogs(musterRoll, startDate, endDate, tenantId, requestInfo);
        Map<String, Map<String, List<AttendanceLog>>> signatureLogMap = buildSignatureLogMap(entryLogs);

        // Generate campaign dates
        List<Long> campaignDates = generateCampaignDates(startDate, endDate);

        // Build attendance details (metrics are already on IndividualEntry from CalculationService)
        List<AttendanceReportDetail> details = buildAttendanceDetails(musterRoll, register, campaignDates,
                tenantId, requestInfo, signatureLogMap, sessions);

        return AttendanceReportData.builder()
                .musterRollId(musterRoll.getId())
                .registerNumber(register.getRegisterNumber())
                .musterRollNumber(musterRoll.getMusterRollNumber())
                .campaignName(register.getName())
                .campaignCode(register.getServiceCode())
                .startDate(startDate)
                .endDate(endDate)
                .totalAttendees(details.size())
                .totalDays(campaignDates.size())
                .attendanceDetails(details)
                .campaignDates(campaignDates)
                .sessions(sessions)
                .build();
    }

    private List<AttendanceReportDetail> buildAttendanceDetails(MusterRoll musterRoll, AttendanceRegister register,
            List<Long> campaignDates, String tenantId, RequestInfo requestInfo,
            Map<String, Map<String, List<AttendanceLog>>> signatureLogMap, int sessions) {
        List<AttendanceReportDetail> details = new ArrayList<>();
        int serialNumber = 1;

        if (musterRoll.getIndividualEntries() == null) {
            log.warn("No individual entries found in muster roll: {}", musterRoll.getId());
            return details;
        }

        List<String> individualIds = new ArrayList<>(Optional.of(musterRoll.getIndividualEntries()).orElse(new ArrayList<>())
                .stream().map(IndividualEntry::getIndividualId).toList());
        Map<String, IndividualWorker> individualWorkerMap = workerRegistryUtil.getWorkers(requestInfo, tenantId, individualIds);
        if (register != null && !CollectionUtils.isEmpty(register.getStaff())) {
            register.getStaff().forEach(staffPermission -> {
                individualIds.add(staffPermission.getUserId());
            });
        }
        Map<String, Individual> individualMap = individualUtil.fetchIndividualDetailsAsMap(individualIds, requestInfo, tenantId);

        for (IndividualEntry entry : musterRoll.getIndividualEntries()) {
            AttendanceReportDetail detail = buildAttendanceDetail(individualWorkerMap, individualMap, entry, register,
                    campaignDates, serialNumber++, tenantId, requestInfo, signatureLogMap, sessions);
            details.add(detail);
        }

        return details;
    }

    private AttendanceReportDetail buildAttendanceDetail(Map<String, IndividualWorker> individualWorkerMap,
            Map<String, Individual> individualMap, IndividualEntry entry, AttendanceRegister register,
            List<Long> campaignDates, int serialNumber, String tenantId, RequestInfo requestInfo,
            Map<String, Map<String, List<AttendanceLog>>> signatureLogMap, int sessions) {

        // Build daily attendance map
        Map<String, String> dailyAttendance = buildDailyAttendanceMap(entry, register, campaignDates);
        String attendanceMarkers = null;
        if (register != null && !CollectionUtils.isEmpty(register.getStaff())) {
            attendanceMarkers = register.getStaff().stream()
                    .filter(staffPermission -> StaffType.OWNER.equals(staffPermission.getStaffType()))
                    .map(StaffPermission::getUserId)
                    .filter(individualMap::containsKey)
                    .map(individualId -> Optional.ofNullable(individualMap
                            .get(individualId))
                            .map(Individual::getName)
                            .map(Name::getGivenName)
                            .orElse(null)
                    )
                    .collect(Collectors.joining(","));
            ;
        }

        IndividualWorker individualWorker = individualWorkerMap.getOrDefault(entry.getIndividualId(), new IndividualWorker());
        Individual individual = individualMap.get(entry.getIndividualId());
        String rolecode = null;
        if(individual != null && !CollectionUtils.isEmpty(individual.getSkills())) {
            rolecode = individual.getSkills().stream().map(Skill::getType)
                    .filter(config.getAllowedAttendanceReportSkills()::contains).findFirst()
                    .orElse(null);
        }

        // Count present days
        int presentDays = (int) dailyAttendance.values().stream()
                .filter(AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT::equalsIgnoreCase)
                .count();

        // Use metrics already populated on IndividualEntry from CalculationService
        long totalRegistrations = entry.getTotalRegistrations() != null ? entry.getTotalRegistrations() : 0L;
        long totalInterventions = entry.getTotalInterventions() != null ? entry.getTotalInterventions() : 0L;

        // Build daily signature IDs and session attendance maps
        SimpleDateFormat sigDateFormatter = new SimpleDateFormat(config.getReportDateFormat());
        sigDateFormatter.setTimeZone(TimeZone.getTimeZone(config.getReportTimezone()));
        Map<String, String[]> dailySignatureIds = new HashMap<>();
        Map<String, String[]> dailySessionAttendance = new HashMap<>();
        Map<String, List<AttendanceLog>> logsForIndividual =
                signatureLogMap.getOrDefault(entry.getIndividualId(), Collections.emptyMap());
        for (Long dateMillis : campaignDates) {
            String dateStr = sigDateFormatter.format(new Date(dateMillis));
            List<AttendanceLog> logsForDay = logsForIndividual.getOrDefault(dateStr, Collections.emptyList());

            long boundaryMillis = computeSessionBoundaryMillis(dateMillis);
            AttendanceLog morningLog = findSessionLog(logsForDay, true, sessions, boundaryMillis);
            AttendanceLog eveningLog = findSessionLog(logsForDay, false, sessions, boundaryMillis);

            String morningId = extractSignatureFileStoreId(
                    morningLog != null ? Collections.singletonList(morningLog) : Collections.emptyList(), 0);
            String eveningId = extractSignatureFileStoreId(
                    eveningLog != null ? Collections.singletonList(eveningLog) : Collections.emptyList(), 0);
            dailySignatureIds.put(dateStr, new String[]{morningId, eveningId});

            String morningStatus = morningLog != null
                    ? AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT
                    : AttendanceReportConstants.ATTENDANCE_STATUS_ABSENT;
            String eveningStatus = eveningLog != null
                    ? AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT
                    : AttendanceReportConstants.ATTENDANCE_STATUS_ABSENT;
            dailySessionAttendance.put(dateStr, new String[]{morningStatus, eveningStatus});
        }

        AttendanceReportDetail attendanceReportDetail = AttendanceReportDetail.builder()
                .serialNumber(serialNumber)
                .registerName(register.getName())
                .registerNumber(register.getRegisterNumber())
                .individualId(entry.getIndividualId())
                .name(Optional.ofNullable(individual).map(Individual::getName).map(Name::getGivenName).orElse(null))
                .phoneNumber(Optional.ofNullable(individual).map(Individual::getMobileNumber).orElse(null))
                .role(rolecode)
                .teamCode(entry.getTag())
                .userId(individualWorker.getId())
                .loginId(Optional.ofNullable(individual).map(Individual::getUserDetails).map(UserDetails::getUsername).orElse(null))
                .attendanceMarker(attendanceMarkers)
                .presentDaysOriginal(presentDays)
                .presentDaysModified(entry.getModifiedTotalAttendance() != null ? entry.getModifiedTotalAttendance().intValue() : presentDays)
                .dailyAttendance(dailyAttendance)
                .dailySignatureIds(dailySignatureIds)
                .dailySessionAttendance(dailySessionAttendance)
                .baseSignatureFileStoreId(individualWorker.getSignatureId())
                .totalPerformance(totalInterventions)
                .build();
        if (!CollectionUtils.isEmpty(register.getAttendees())) {
            IndividualEntry attendee = register.getAttendees()
                    .stream().filter(ind -> ind.getIndividualId().equals(entry.getIndividualId())).findFirst().orElse(null);
            if (attendee != null) {
                attendanceReportDetail.setEnrollmentDate(Optional.ofNullable(attendee.getEnrollmentDate())
                        .map(BigDecimal::longValue)
                        .orElse(null));
                attendanceReportDetail.setDeEnrollmentDate(Optional.ofNullable(attendee.getDenrollmentDate())
                        .map(BigDecimal::longValue)
                        .orElse(null));
            }
        }
        return attendanceReportDetail;
    }

    private Map<String, String> buildDailyAttendanceMap(IndividualEntry entry, AttendanceRegister register,
            List<Long> campaignDates) {
        Map<String, String> dailyAttendance = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(config.getReportDateFormat());
        dateFormatter.setTimeZone(TimeZone.getTimeZone(config.getReportTimezone()));

        // Initialize all days as ABSENT
        for (Long dateMillis : campaignDates) {
            String dateStr = dateFormatter.format(new Date(dateMillis));
            dailyAttendance.put(dateStr, AttendanceReportConstants.ATTENDANCE_STATUS_ABSENT);
        }

        // Mark PRESENT days based on attendance entries
        // AttendanceEntry has 'time' (Long timestamp) and 'attendance' (BigDecimal value)
        if (entry.getAttendanceEntries() != null) {
            for (AttendanceEntry attendanceEntry : entry.getAttendanceEntries()) {
                if (attendanceEntry.getTime() != null) {
                    Long timeMillis = attendanceEntry.getTime().longValue();
                    String dateStr = dateFormatter.format(new Date(timeMillis));

                    // If attendance value is non-zero or not null, consider it PRESENT
                    if (attendanceEntry.getAttendance() != null && attendanceEntry.getAttendance().compareTo(java.math.BigDecimal.ZERO) > 0) {
                        dailyAttendance.put(dateStr, AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT);
                    }
                }
            }
        }

        return dailyAttendance;
    }

    /**
     * Fetches all ENTRY-type attendance logs for the muster roll's register within the billing period window.
     */
    private List<AttendanceLog> fetchEntryAttendanceLogs(MusterRoll musterRoll, Long startDate, Long endDate,
            String tenantId, RequestInfo requestInfo) {
        try {
            long windowStart = startDate != null ? startDate : musterRoll.getStartDate().longValue();
            long windowEnd = endDate != null ? endDate
                    : musterRoll.getEndDate().longValue() + (24 * 60 * 60 * 1000L) - 1;

            StringBuilder uri = new StringBuilder();
            uri.append(config.getAttendanceLogHost()).append(config.getAttendanceLogEndpoint());
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                    .queryParam("tenantId", tenantId)
                    .queryParam("registerId", musterRoll.getRegisterId())
                    .queryParam("fromTime", BigDecimal.valueOf(windowStart))
                    .queryParam("toTime", BigDecimal.valueOf(windowEnd))
                    .queryParam("status", Status.ACTIVE);

            RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();
            AttendanceLogResponse response = restTemplate.postForObject(
                    uriBuilder.toUriString(), requestInfoWrapper, AttendanceLogResponse.class);

            if (response == null || CollectionUtils.isEmpty(response.getAttendance())) {
                log.info("No attendance logs found for register: {}", musterRoll.getRegisterId());
                return Collections.emptyList();
            }

            return response.getAttendance().stream()
                    .filter(log -> "ENTRY".equals(log.getType()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.warn("Failed to fetch entry attendance logs for register {}: {}", musterRoll.getRegisterId(), e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Builds a lookup map: individualId → dateStr → List of ENTRY logs sorted by time ASC.
     */
    private Map<String, Map<String, List<AttendanceLog>>> buildSignatureLogMap(List<AttendanceLog> entryLogs) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(config.getReportDateFormat());
        dateFormatter.setTimeZone(TimeZone.getTimeZone(config.getReportTimezone()));

        Map<String, Map<String, List<AttendanceLog>>> result = new HashMap<>();
        for (AttendanceLog log : entryLogs) {
            if (log.getIndividualId() == null || log.getTime() == null) continue;
            String dateStr = dateFormatter.format(new Date(log.getTime().longValue()));
            result.computeIfAbsent(log.getIndividualId(), k -> new HashMap<>())
                  .computeIfAbsent(dateStr, k -> new ArrayList<>())
                  .add(log);
        }
        // Sort each day's list by time ASC
        result.values().forEach(dayMap ->
                dayMap.values().forEach(logs ->
                        logs.sort(Comparator.comparing(l -> l.getTime().longValue()))));
        return result;
    }

    /**
     * Returns the ENTRY log that belongs to the given session (morning/evening) for a day.
     * For 1-session registers, always uses the first log as morning (boundary ignored).
     * For 2-session registers, splits by boundary: morning = time < boundary, evening = time >= boundary.
     */
    private AttendanceLog findSessionLog(List<AttendanceLog> logsForDay,
            boolean isMorning, int sessions, long boundaryMillis) {
        if (logsForDay == null || logsForDay.isEmpty()) return null;
        if (sessions == 1) {
            return isMorning ? logsForDay.get(0) : null;
        }
        if (isMorning) {
            return logsForDay.stream()
                    .filter(l -> l.getTime().longValue() < boundaryMillis)
                    .findFirst().orElse(null);
        } else {
            return logsForDay.stream()
                    .filter(l -> l.getTime().longValue() >= boundaryMillis)
                    .findFirst().orElse(null);
        }
    }

    /**
     * Computes the epoch-millis for the session boundary time (e.g. "12:00") on the given date,
     * using the configured report timezone. Falls back to noon (12:00) if config is malformed.
     */
    private long computeSessionBoundaryMillis(Long dateMillis) {
        ZoneId zone = ZoneId.of(config.getReportTimezone());
        LocalDate date = new Date(dateMillis).toInstant().atZone(zone).toLocalDate();
        int hour = 12;
        int minute = 0;
        try {
            String[] parts = config.getSessionBoundaryTime().split(":");
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            log.warn("Invalid sessionBoundaryTime config '{}', defaulting to 12:00: {}",
                    config.getSessionBoundaryTime(), e.getMessage());
        }
        return date.atTime(hour, minute).atZone(zone).toInstant().toEpochMilli();
    }

    /**
     * Extracts signatureFileStoreId from the log at the given index in the list.
     * Returns null if the index is out of bounds or the field is absent.
     */
    private String extractSignatureFileStoreId(List<AttendanceLog> logs, int index) {
        if (logs == null || index >= logs.size()) return null;
        try {
            AttendanceLog attendanceLog = logs.get(index);
            if (attendanceLog.getAdditionalDetails() == null) return null;
            JsonNode node = objectMapper.valueToTree(attendanceLog.getAdditionalDetails());
            JsonNode idNode = node.get("signatureFileStoreId");
            if (idNode != null && !idNode.isNull() && !idNode.asText().isBlank()) {
                return idNode.asText();
            }
        } catch (Exception e) {
            log.warn("Failed to extract signatureFileStoreId from attendance log: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Collects all unique fileStoreIds from report details and downloads the images in bulk.
     * Returns a map of fileStoreId → image bytes. Failed downloads are absent from the map.
     */
    private Map<String, byte[]> downloadSignatureImages(AttendanceReportData reportData, String tenantId) {
        Map<String, byte[]> signatureImages = new HashMap<>();
        if (reportData.getAttendanceDetails() == null) return signatureImages;

        Set<String> fileStoreIds = new HashSet<>();
        for (AttendanceReportDetail detail : reportData.getAttendanceDetails()) {
            if (detail.getBaseSignatureFileStoreId() != null)
                fileStoreIds.add(detail.getBaseSignatureFileStoreId());
            if (detail.getDailySignatureIds() == null) continue;
            for (String[] ids : detail.getDailySignatureIds().values()) {
                if (ids[0] != null) fileStoreIds.add(ids[0]);
                if (ids[1] != null) fileStoreIds.add(ids[1]);
            }
        }

        for (String fileStoreId : fileStoreIds) {
            byte[] bytes = fileStoreUtil.downloadFileBytes(fileStoreId, tenantId);
            if (bytes != null) {
                signatureImages.put(fileStoreId, bytes);
            }
        }
        log.info("Downloaded {}/{} signature images for report", signatureImages.size(), fileStoreIds.size());
        return signatureImages;
    }

    private List<Long> generateCampaignDates(Long startDate, Long endDate) {
        List<Long> dates = new ArrayList<>();

        if (startDate == null || endDate == null) {
            log.warn("Start date or end date is null");
            return dates;
        }

        ZoneId reportZone = ZoneId.of(config.getReportTimezone());
        LocalDate start = new Date(startDate).toInstant().atZone(reportZone)
                .toLocalDate();
        LocalDate end = new Date(endDate).toInstant().atZone(reportZone)
                .toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;
        for (long i = 0; i < daysBetween; i++) {
            LocalDate date = start.plusDays(i);
            long dateMillis = date.atStartOfDay(reportZone)
                    .toInstant()
                    .toEpochMilli();
            dates.add(dateMillis);
        }

        return dates;
    }

    private void updateReportStatus(MusterRoll musterRoll, String reportType, String reportFormat,
                                    String status, String fileStoreId, String errorMessage, String userUuid) {
        try {
            // Create or update report in database with multi-tenant support
            MusterRollReport report = musterRollReportRepository
                    .findByMusterRollAndTypeAndFormat(
                            musterRoll.getId(),
                            reportType,
                            reportFormat,
                            musterRoll.getTenantId())
                    .orElse(MusterRollReport.builder()
                            .musterRollId(musterRoll.getId())
                            .tenantId(musterRoll.getTenantId())
                            .reportType(reportType)
                            .reportFormat(reportFormat)
                            .build());

            report.setReportStatus(status);
            report.setFileStoreId(fileStoreId);
            report.setGeneratedAt(System.currentTimeMillis());
            report.setErrorMessage(errorMessage);
            AuditDetails auditDetails = Optional.ofNullable(report.getAuditDetails())
                    .orElse(new AuditDetails());
            if(ObjectUtils.isEmpty(auditDetails.getCreatedBy())) {
                auditDetails.setCreatedBy(userUuid);
                auditDetails.setCreatedTime(System.currentTimeMillis());
            }
            auditDetails.setLastModifiedBy(userUuid);
            auditDetails.setLastModifiedTime(System.currentTimeMillis());

            report.setAuditDetails(auditDetails);

            // Save or update the report
            if (report.getId() == null) {
                musterRollReportRepository.save(report);
            } else {
                musterRollReportRepository.update(report);
            }

            log.info("Updated report status in database: musterRoll={} ({} {} = {})",
                    musterRoll.getId(), reportType, reportFormat, status);

        } catch (Exception e) {
            log.error("Error updating report status for muster roll: {}", musterRoll.getId(), e);
        }
    }

    private MusterRoll fetchMusterRoll(String musterRollId, String tenantId) {
        try {
            MusterRollSearchCriteria criteria = MusterRollSearchCriteria.builder()
                    .tenantId(tenantId)
                    .musterRollNumber(null)
                    .ids(new ArrayList<>(List.of(musterRollId)))
                    .build();

            List<MusterRoll> musterRolls = musterRollRepository.getMusterRoll(criteria, null);

            if (!CollectionUtils.isEmpty(musterRolls)) {
                return musterRolls.get(0);
            }

            return null;

        } catch (Exception e) {
            log.error("Error fetching muster roll: {}", musterRollId, e);
            return null;
        }
    }

    private AttendanceRegister fetchAttendanceRegister(RequestInfo requestInfo, String tenantId, String registerId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
        uri.append("?tenantId=").append(tenantId);

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
                log.error("No attendance register found for ID: {}", registerId);
                throw new CustomException("ATTENDANCE_REGISTER_FETCH_ERROR", "Attendance Register is empty");
            }
            return response.getAttendanceRegister().get(0);
        } catch (Exception e) {
            log.error("Error fetching register {}: {}", registerId, e.getMessage(), e);
            throw new CustomException("ATTENDANCE_REGISTER_FETCH_ERROR",
                    "Failed to fetch attendance register: " + e.getMessage());
        }
    }

    private void publishReportGenerationRequest(String musterRollId, String tenantId,
                                                 String reportType, String reportFormat,
                                                 RequestInfo requestInfo) {
        try {
            ReportGenerationRequest reportRequest = ReportGenerationRequest.builder()
                    .musterRollId(musterRollId)
                    .tenantId(tenantId)
                    .reportType(reportType)
                    .reportFormat(reportFormat)
                    .requestInfo(requestInfo)
                    .timestamp(System.currentTimeMillis())
                    .build();

            musterRollProducer.push(config.getAttendanceReportGenerateTopic(), reportRequest);

            log.info("Published report generation request to Kafka for muster roll: {} ({} {})",
                    musterRollId, reportType, reportFormat);

        } catch (Exception e) {
            log.error("Error publishing report generation request to Kafka: {}", e.getMessage(), e);
            throw new CustomException("KAFKA_PUBLISH_FAILED", "Failed to publish report generation request");
        }
    }

    private void publishMusterRollUpdate(MusterRoll musterRoll) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("musterRoll", musterRoll);

            musterRollProducer.push(config.getAttendanceReportUpdateTopic(), message);

            log.info("Published muster roll update to Kafka for muster roll: {}", musterRoll.getId());

        } catch (Exception e) {
            log.error("Error publishing muster roll update to Kafka: {}", e.getMessage(), e);
        }
    }
}
