package org.egov.service;

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
import org.egov.web.models.AttendanceEntry;
import org.egov.web.models.AttendanceRegister;
import org.egov.web.models.AttendanceRegisterResponse;
import org.egov.web.models.IndividualEntry;
import org.egov.web.models.MusterRoll;
import org.egov.web.models.MusterRollSearchCriteria;
import org.egov.web.models.report.AttendanceReportData;
import org.egov.web.models.report.AttendanceReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public AttendanceReportGeneratorService(
            MusterRollRepository musterRollRepository,
            ServiceRequestRepository serviceRequestRepository,
            AttendanceExcelGenerator excelGenerator,
            FileStoreUtil fileStoreUtil,
            MusterRollProducer musterRollProducer,
            MusterRollServiceConfiguration config,
            ObjectMapper objectMapper) {
        this.musterRollRepository = musterRollRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.excelGenerator = excelGenerator;
        this.fileStoreUtil = fileStoreUtil;
        this.musterRollProducer = musterRollProducer;
        this.config = config;
        this.objectMapper = objectMapper;
    }

    public void initiateReportGeneration(String musterRollId, String tenantId, RequestInfo requestInfo) {
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
            updateReportStatus(musterRoll, AttendanceReportConstants.REPORT_STATUS_INITIATED, null, null);

            // Publish to Kafka for async processing
            publishReportGenerationRequest(musterRollId, tenantId, requestInfo);

            log.info("Report generation initiated for muster roll: {}", musterRollId);

        } catch (CustomException e) {
            log.error("Error initiating report generation: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error initiating report generation: {}", e.getMessage(), e);
            throw new CustomException("REPORT_GENERATION_FAILED", "Failed to initiate report generation");
        }
    }

    public void generateReport(String musterRollId, String tenantId, RequestInfo requestInfo) {
        MusterRoll musterRoll = null;
        try {
            log.info("Starting report generation for muster roll: {}", musterRollId);

            // Fetch muster roll
            musterRoll = fetchMusterRoll(musterRollId, tenantId);
            if (musterRoll == null) {
                log.error("Muster roll not found: {}", musterRollId);
                throw new CustomException(AttendanceReportConstants.MUSTER_NOT_FOUND,
                        "Muster roll not found with id: " + musterRollId);
            }

            // Fetch attendance data
            AttendanceReportData reportData = buildReportData(musterRoll, tenantId, requestInfo);

            // Generate Excel file
            byte[] excelContent = excelGenerator.generateExcel(reportData);

            // Upload to FileStore
            String fileName = String.format("attendance-report-%s.xlsx", musterRollId);
            String fileStoreId = fileStoreUtil.uploadFile(excelContent, fileName, tenantId,
                    AttendanceReportConstants.FILESTORE_MODULE_NAME);

            if (fileStoreId == null) {
                log.error("Failed to upload Excel file to filestore for muster roll: {}", musterRollId);
                updateReportStatus(musterRoll, AttendanceReportConstants.REPORT_STATUS_FAILED, null,
                        "Failed to upload report to filestore");
                return;
            }

            // Update muster roll with report status and file store ID
            updateReportStatus(musterRoll, AttendanceReportConstants.REPORT_STATUS_COMPLETED, fileStoreId, null);

            log.info("Report generation completed successfully for muster roll: {}", musterRollId);

        } catch (IOException e) {
            log.error("Error generating Excel file for muster roll: {}", musterRollId, e);
            if (musterRoll != null) {
                updateReportStatus(musterRoll, AttendanceReportConstants.REPORT_STATUS_FAILED, null,
                        "Error generating Excel file: " + e.getMessage());
            }
        } catch (Exception e) {
            log.error("Unexpected error during report generation for muster roll: {}", musterRollId, e);
            if (musterRoll != null) {
                updateReportStatus(musterRoll, AttendanceReportConstants.REPORT_STATUS_FAILED, null,
                        "Unexpected error: " + e.getMessage());
            }
        }
    }

    private AttendanceReportData buildReportData(MusterRoll musterRoll, String tenantId, RequestInfo requestInfo) {
        // Fetch attendance register
        AttendanceRegister register = fetchAttendanceRegister(musterRoll.getRegisterId(), tenantId, requestInfo);
        if (register == null) {
            throw new CustomException(AttendanceReportConstants.ATTENDANCE_REGISTER_NOT_FOUND,
                    "Attendance register not found with id: " + musterRoll.getRegisterId());
        }

        // Calculate date range
        Long startDate = musterRoll.getStartDate() != null ? musterRoll.getStartDate().longValue() : null;
        Long endDate = musterRoll.getEndDate() != null ? musterRoll.getEndDate().longValue() : null;

        // Generate campaign dates
        List<Long> campaignDates = generateCampaignDates(startDate, endDate);

        // Build attendance details
        List<AttendanceReportDetail> details = buildAttendanceDetails(musterRoll, register, campaignDates,
                tenantId, requestInfo);

        // Build report data
        AttendanceReportData reportData = AttendanceReportData.builder()
                .musterRollId(musterRoll.getId())
                .musterRollNumber(musterRoll.getMusterRollNumber())
                .campaignName(register.getName())
                .campaignCode(register.getServiceCode())
                .startDate(startDate)
                .endDate(endDate)
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

        for (IndividualEntry entry : musterRoll.getIndividualEntries()) {
            AttendanceReportDetail detail = buildAttendanceDetail(entry, register, campaignDates, serialNumber++,
                    tenantId, requestInfo);
            details.add(detail);
        }

        return details;
    }

    private AttendanceReportDetail buildAttendanceDetail(IndividualEntry entry, AttendanceRegister register,
            List<Long> campaignDates, int serialNumber, String tenantId, RequestInfo requestInfo) {

        // Build daily attendance map
        Map<String, String> dailyAttendance = buildDailyAttendanceMap(entry, register, campaignDates);

        // Count present days
        int presentDays = (int) dailyAttendance.values().stream()
                .filter(s -> AttendanceReportConstants.ATTENDANCE_STATUS_PRESENT.equalsIgnoreCase(s))
                .count();

        return AttendanceReportDetail.builder()
                .serialNumber(serialNumber)
                .individualId(entry.getIndividualId())
                .name("")
                .phoneNumber("")
                .role("")
                .teamCode("")
                .userId("")
                .enrollmentDate(null)
                .deEnrollmentDate(null)
                .attendanceMarker("")
                .presentDaysOriginal(presentDays)
                .presentDaysModified(presentDays)
                .dailyAttendance(dailyAttendance)
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

        LocalDate start = new Date(startDate).toInstant().atZone(ZoneId.of(AttendanceReportConstants.REPORT_TIMEZONE))
                .toLocalDate();
        LocalDate end = new Date(endDate).toInstant().atZone(ZoneId.of(AttendanceReportConstants.REPORT_TIMEZONE))
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

    private void updateReportStatus(MusterRoll musterRoll, String status, String fileStoreId, String errorMessage) {
        try {
            // Update additionalDetails with report metadata
            Map<String, Object> additionalDetails = musterRoll.getAdditionalDetails() instanceof Map
                    ? (Map<String, Object>) musterRoll.getAdditionalDetails()
                    : new HashMap<>();

            Map<String, Object> reportMetadata = new HashMap<>();
            reportMetadata.put("reportStatus", status);
            if (StringUtils.hasText(fileStoreId)) {
                reportMetadata.put("excelFileStoreId", fileStoreId);
            }
            reportMetadata.put("generatedAt", System.currentTimeMillis());
            if (StringUtils.hasText(errorMessage)) {
                reportMetadata.put("errorMessage", errorMessage);
            }

            additionalDetails.put(AttendanceReportConstants.REPORT_KEY, reportMetadata);
            musterRoll.setAdditionalDetails(additionalDetails);

            // Publish update event to Kafka
            publishMusterRollUpdate(musterRoll);

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

    private AttendanceRegister fetchAttendanceRegister(String registerId, String tenantId, RequestInfo requestInfo) {
        try {
            StringBuilder uri = new StringBuilder(config.getAttendanceLogHost())
                    .append(config.getAttendanceRegisterEndpoint());

            Map<String, Object> searchRequest = new HashMap<>();
            searchRequest.put("RequestInfo", requestInfo);
            searchRequest.put("id", registerId);
            searchRequest.put("tenantId", tenantId);

            Object response = serviceRequestRepository.fetchResult(uri, searchRequest);

            if (response instanceof Map) {
                Map<String, Object> responseMap = (Map<String, Object>) response;
                List<AttendanceRegister> registers = (List<AttendanceRegister>) responseMap.get("attendanceRegisters");
                if (!CollectionUtils.isEmpty(registers)) {
                    return registers.get(0);
                }
            }

            return null;

        } catch (Exception e) {
            log.error("Error fetching attendance register: {}", registerId, e);
            return null;
        }
    }

    private void publishReportGenerationRequest(String musterRollId, String tenantId, RequestInfo requestInfo) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("musterRollId", musterRollId);
            message.put("tenantId", tenantId);
            message.put("requestInfo", requestInfo);
            message.put("timestamp", System.currentTimeMillis());

            musterRollProducer.push(config.getAttendanceReportGenerateTopic(), message);

            log.info("Published report generation request to Kafka for muster roll: {}", musterRollId);

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
