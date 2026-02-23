package org.egov.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.AttendanceReportGeneratorService;
import org.egov.util.ResponseInfoCreator;
import org.egov.web.models.AttendanceReportRequest;
import org.egov.web.models.AttendanceReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/v1/attendance/report")
public class AttendanceReportApiController {

    @Autowired
    private AttendanceReportGeneratorService attendanceReportGeneratorService;

    @Autowired
    private ResponseInfoCreator responseInfoCreator;

    @Autowired
    private HttpServletRequest request;

    /**
     * Generate attendance report for a muster roll
     */
    @RequestMapping(value = "/_generate", method = RequestMethod.POST)
    public ResponseEntity<AttendanceReportResponse> generateAttendanceReport(
            @Valid @RequestBody AttendanceReportRequest reportRequest) {

        log.info("Received request to generate attendance report for muster roll: {}",
                reportRequest.getMusterRollId());

        try {
            // Initiate async report generation
            attendanceReportGeneratorService.initiateReportGeneration(
                    reportRequest.getMusterRollId(),
                    reportRequest.getTenantId(),
                    reportRequest.getRequestInfo());

            ResponseInfo responseInfo = responseInfoCreator
                    .createResponseInfoFromRequestInfo(reportRequest.getRequestInfo(), true);

            AttendanceReportResponse response = AttendanceReportResponse.builder()
                    .responseInfo(responseInfo)
                    .status("INITIATED")
                    .message("Attendance report generation initiated")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            log.error("Error generating attendance report: {}", e.getMessage(), e);

            ResponseInfo responseInfo = responseInfoCreator
                    .createResponseInfoFromRequestInfo(reportRequest.getRequestInfo(), false);

            AttendanceReportResponse response = AttendanceReportResponse.builder()
                    .responseInfo(responseInfo)
                    .status("FAILED")
                    .message("Failed to generate attendance report")
                    .errorMessage(e.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Download attendance report for a muster roll
     */
    @RequestMapping(value = "/_downloadReport", method = RequestMethod.GET)
    public ResponseEntity<AttendanceReportResponse> downloadAttendanceReport(
            @RequestParam String musterRollId,
            @RequestParam String tenantId) {

        log.info("Received request to download attendance report for muster roll: {}", musterRollId);

        try {
            // Note: This is a placeholder for the download endpoint
            // In a real implementation, this would fetch the file from FileStore
            // using the fileStoreId stored in the muster roll's additionalDetails

            AttendanceReportResponse response = AttendanceReportResponse.builder()
                    .status("PENDING")
                    .message("Report download URL generation is implemented in FileStore service")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error downloading attendance report: {}", e.getMessage(), e);

            AttendanceReportResponse response = AttendanceReportResponse.builder()
                    .status("FAILED")
                    .message("Failed to download attendance report")
                    .errorMessage(e.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
