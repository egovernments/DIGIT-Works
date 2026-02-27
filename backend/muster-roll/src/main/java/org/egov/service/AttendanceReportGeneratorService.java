package org.egov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.kafka.MusterRollProducer;
import org.egov.repository.MusterRollRepository;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.util.AttendanceReportConstants;
import org.egov.util.FileStoreUtil;
import org.egov.util.WorkerRegistryUtil;
import org.egov.web.models.*;
import org.egov.web.models.report.AttendanceReportData;
import org.egov.web.models.report.AttendanceReportDetail;
import org.egov.web.models.report.ReportGenerationRequest;
import org.egov.web.models.report.ReportMetadata;
import org.egov.web.models.report.MusterRollReport;
import org.egov.web.models.report.ReportType;
import org.egov.web.models.report.ReportFormat;
import org.egov.web.models.report.ReportStatus;
import org.egov.repository.MusterRollReportRepository;
import org.egov.common.contract.models.AuditDetails;
import org.egov.util.LocalizationUtil;
import org.egov.web.models.worker.IndividualWorker;
import org.egov.works.services.common.models.attendance.AttendanceRegisterSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.egov.common.contract.models.AuditDetails;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
            LocalizationUtil localizationUtil, WorkerRegistryUtil workerRegistryUtil) {
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

            // Generate Excel file
            byte[] excelContent = excelGenerator.generateExcel(reportData, messages);

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

        // Generate campaign dates
        List<Long> campaignDates = generateCampaignDates(startDate, endDate);

        LocalDate start = new Date(startDate).toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate end = new Date(endDate).toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Build attendance details (metrics are already on IndividualEntry from CalculationService)
        List<AttendanceReportDetail> details = buildAttendanceDetails(musterRoll, register, campaignDates,
                tenantId, requestInfo);

        // Build report data
        AttendanceReportData reportData = AttendanceReportData.builder()
                .musterRollId(musterRoll.getId())
                .musterRollNumber(musterRoll.getMusterRollNumber())
                .campaignName(register.getName())
                .campaignCode(register.getServiceCode())
                .startDate(start.atStartOfDay(ZoneId.of(AttendanceReportConstants.REPORT_TIMEZONE)).toInstant()
                        .toEpochMilli())
                .endDate(end.atStartOfDay(ZoneId.of(AttendanceReportConstants.REPORT_TIMEZONE)).toInstant()
                        .toEpochMilli())
                .totalAttendees(details.size())
                .totalDays(campaignDates.size())
                .attendanceDetails(details)
                .campaignDates(campaignDates)
                .build();

        return reportData;
    }

    private List<AttendanceReportDetail> buildAttendanceDetails(MusterRoll musterRoll, AttendanceRegister register,
            List<Long> campaignDates, String tenantId, RequestInfo requestInfo) {
        List<AttendanceReportDetail> details = new ArrayList<>();
        int serialNumber = 1;

        if (musterRoll.getIndividualEntries() == null) {
            log.warn("No individual entries found in muster roll: {}", musterRoll.getId());
            return details;
        }

        List<String> individualIds = Optional.of(musterRoll.getIndividualEntries()).orElse(new ArrayList<>())
                .stream().map(IndividualEntry::getIndividualId).toList();
        Map<String, IndividualWorker> individualWorkerMap = workerRegistryUtil.getWorkers(requestInfo, tenantId, individualIds);

//        Map<String, String> individualTeam

        //TODO: fetch attendees to update register report details

        for (IndividualEntry entry : musterRoll.getIndividualEntries()) {
            AttendanceReportDetail detail = buildAttendanceDetail(individualWorkerMap, entry, register, campaignDates, serialNumber++,
                    tenantId, requestInfo);
            details.add(detail);
        }

        return details;
    }

    private AttendanceReportDetail buildAttendanceDetail(Map<String, IndividualWorker> individualWorkerMap, IndividualEntry entry, AttendanceRegister register,
            List<Long> campaignDates, int serialNumber, String tenantId, RequestInfo requestInfo) {

        // Build daily attendance map
        Map<String, String> dailyAttendance = buildDailyAttendanceMap(entry, register, campaignDates);

        IndividualWorker individualWorker = individualWorkerMap.getOrDefault(entry.getIndividualId(), new IndividualWorker());

        // Count present days
        int presentDays = (int) dailyAttendance.values().stream()
                .filter(s -> AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT.equalsIgnoreCase(s))
                .count();

        // Use metrics already populated on IndividualEntry from CalculationService
        long totalRegistrations = entry.getTotalRegistrations() != null ? entry.getTotalRegistrations() : 0L;
        long totalInterventions = entry.getTotalInterventions() != null ? entry.getTotalInterventions() : 0L;

        return AttendanceReportDetail.builder()
                .serialNumber(serialNumber)
                .individualId(entry.getIndividualId())
                .name(individualWorker.getName())
                .phoneNumber(individualWorker.getPayeePhoneNumber())
                .role("") // TODO: fix the role for attendance report
                .teamCode(entry.getTag())
                .userId(individualWorker.getId())
                .enrollmentDate(null)
                .deEnrollmentDate(null)
                .attendanceMarker("")
                .presentDaysOriginal(presentDays)
                .presentDaysModified(presentDays)
                .dailyAttendance(dailyAttendance)
                .totalPerformance(totalInterventions)
                .build();
    }

    private Map<String, String> buildDailyAttendanceMap(IndividualEntry entry, AttendanceRegister register,
            List<Long> campaignDates) {
        Map<String, String> dailyAttendance = new HashMap<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(AttendanceReportConstants.REPORT_DATE_FORMAT);

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

    private List<Long> generateCampaignDates(Long startDate, Long endDate) {
        List<Long> dates = new ArrayList<>();

        if (startDate == null || endDate == null) {
            log.warn("Start date or end date is null");
            return dates;
        }

        LocalDate start = new Date(startDate).toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate end = new Date(endDate).toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;
        for (long i = 0; i < daysBetween; i++) {
            LocalDate date = start.plusDays(i);
            long dateMillis = date.atStartOfDay(ZoneId.of(AttendanceReportConstants.REPORT_TIMEZONE)).toInstant()
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
