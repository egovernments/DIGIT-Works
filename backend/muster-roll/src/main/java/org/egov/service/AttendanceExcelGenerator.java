package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.util.AttendanceReportConstants;
import org.egov.web.models.report.AttendanceReportData;
import org.egov.web.models.report.AttendanceReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AttendanceExcelGenerator {

    private final MusterRollServiceConfiguration config;

    @Autowired
    public AttendanceExcelGenerator(MusterRollServiceConfiguration config) {
        this.config = config;
    }

    public byte[] generateExcel(AttendanceReportData reportData, Map<String, String> localizedMessages)
            throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Attendance Report");

            // Set default column widths
            sheet.setDefaultColumnWidth(15);

            // Create workbook
            writeHeaderSection(sheet, reportData, localizedMessages);
            writeColumnHeaders(sheet, reportData, localizedMessages);
            writeDataRows(sheet, reportData);

            // Set fixed column widths (headless-safe approach)
            // First 11 columns are fixed (S.No, Name, Phone, Role, Team Code, User ID, Enroll, De-enroll, Marker, Present, Modified)
            int[] fixedColumnWidths = {8, 25, 15, 15, 12, 15, 15, 15, 12, 12, 12, 15};
            for (int i = 0; i < fixedColumnWidths.length; i++) {
                sheet.setColumnWidth(i, fixedColumnWidths[i] * 256);
            }

            // Dynamic date columns - use consistent width
            int dateColumnWidth = 3072;
            for (int i = AttendanceReportConstants.FIXED_COLUMNS_COUNT;
                 i < AttendanceReportConstants.FIXED_COLUMNS_COUNT + reportData.getTotalDays(); i++) {
                sheet.setColumnWidth(i, dateColumnWidth);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Error generating Excel file: {}", e.getMessage(), e);
            throw new IOException("Failed to generate Excel file", e);
        }
    }

    private void writeHeaderSection(XSSFSheet sheet, AttendanceReportData reportData,
            Map<String, String> localizedMessages) {
        CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
        CellStyle subHeaderStyle = createSubHeaderStyle(sheet.getWorkbook());

        // Row 0: Title
        XSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(getLocalized(localizedMessages,
                AttendanceReportConstants.REPORT_TITLE_KEY));
        titleCell.setCellStyle(headerStyle);

        // Merge cells for title
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
            AttendanceReportConstants.FIXED_COLUMNS_COUNT + reportData.getTotalDays() - 1));

        // Row 1: Register info
        XSSFRow infoRow1 = sheet.createRow(1);
        infoRow1.setHeightInPoints(18);
        XSSFCell infoCell1 = infoRow1.createCell(0);

        String registerPattern = getLocalized(localizedMessages,
                AttendanceReportConstants.REPORT_REGISTER_INFO_KEY);
        String registerInfo = String.format(registerPattern,
                reportData.getMusterRollNumber(),
                formatDate(reportData.getStartDate()),
                formatDate(reportData.getEndDate()),
                reportData.getTotalAttendees());
        infoCell1.setCellValue(registerInfo);
        infoCell1.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
            AttendanceReportConstants.FIXED_COLUMNS_COUNT + reportData.getTotalDays() - 1));

        // Row 2: Campaign info
        XSSFRow infoRow2 = sheet.createRow(2);
        infoRow2.setHeightInPoints(18);
        XSSFCell infoCell2 = infoRow2.createCell(0);

        String campaignPattern = getLocalized(localizedMessages,
                AttendanceReportConstants.REPORT_CAMPAIGN_INFO_KEY);
        String campaignInfo = String.format(campaignPattern,
                reportData.getCampaignName(),
                reportData.getMusterRollId());
        infoCell2.setCellValue(campaignInfo);
        infoCell2.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0,
            AttendanceReportConstants.FIXED_COLUMNS_COUNT + reportData.getTotalDays() - 1));
    }

    private void writeColumnHeaders(XSSFSheet sheet, AttendanceReportData reportData,
            Map<String, String> localizedMessages) {
        XSSFRow headerRow = sheet.createRow(3);
        headerRow.setHeightInPoints(20);
        CellStyle columnHeaderStyle = createColumnHeaderStyle(sheet.getWorkbook());

        // Fixed columns
        int columnIndex = 0;
        for (String headerKey : AttendanceReportConstants.FIXED_COLUMN_HEADER_KEYS) {
            XSSFCell cell = headerRow.createCell(columnIndex);
            cell.setCellValue(getLocalized(localizedMessages, headerKey));
            cell.setCellStyle(columnHeaderStyle);
            columnIndex++;
        }

        // Dynamic date columns
        if (reportData.getCampaignDates() != null) {
            for (Long dateMillis : reportData.getCampaignDates()) {
                XSSFCell cell = headerRow.createCell(columnIndex);
                cell.setCellValue(formatDate(dateMillis));
                cell.setCellStyle(columnHeaderStyle);
                columnIndex++;
            }
        }
    }

    private void writeDataRows(XSSFSheet sheet, AttendanceReportData reportData) {
        CellStyle dataStyle = createDataStyle(sheet.getWorkbook());
        CellStyle dateDataStyle = createDateDataStyle(sheet.getWorkbook());

        int rowIndex = 4;
        if (reportData.getAttendanceDetails() != null) {
            for (AttendanceReportDetail detail : reportData.getAttendanceDetails()) {
                XSSFRow row = sheet.createRow(rowIndex);
                row.setHeightInPoints(18);

                int columnIndex = 0;

                // Fixed columns
                setCellValue(row, columnIndex++, detail.getSerialNumber(), dataStyle);
                setCellValue(row, columnIndex++, detail.getName(), dataStyle);
                setCellValue(row, columnIndex++, detail.getPhoneNumber(), dataStyle);
                setCellValue(row, columnIndex++, detail.getRole(), dataStyle);
                setCellValue(row, columnIndex++, detail.getTeamCode(), dataStyle);
                setCellValue(row, columnIndex++, detail.getUserId(), dataStyle);
                setCellValue(row, columnIndex++, formatDate(detail.getEnrollmentDate()), dateDataStyle);
                setCellValue(row, columnIndex++, formatDate(detail.getDeEnrollmentDate()), dateDataStyle);
                setCellValue(row, columnIndex++, detail.getAttendanceMarker(), dataStyle);
                setCellValue(row, columnIndex++, detail.getPresentDaysOriginal(), dataStyle);
                setCellValue(row, columnIndex++, detail.getPresentDaysModified(), dataStyle);
                setCellValue(row, columnIndex++, detail.getTotalPerformance(), dataStyle);

                // Dynamic attendance columns
                if (reportData.getCampaignDates() != null && detail.getDailyAttendance() != null) {
                    for (Long dateMillis : reportData.getCampaignDates()) {
                        String dateStr = formatDate(dateMillis);
                        String attendanceStatus = detail.getDailyAttendance().getOrDefault(dateStr, "");
                        setCellValue(row, columnIndex++, attendanceStatus, dataStyle);
                    }
                }

                rowIndex++;
            }
        }
    }

    private void setCellValue(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        if (value != null) {
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
        cell.setCellStyle(style);
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createSubHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createColumnHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createDataStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createDateDataStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private String formatDate(Long milliseconds) {
        if (milliseconds == null || milliseconds == 0) {
            return "-";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(config.getReportDateFormat());
            return formatter.format(new Date(milliseconds));
        } catch (Exception e) {
            log.warn("Error formatting date: {}", e.getMessage());
            return "-";
        }
    }

    private String getLocalized(Map<String, String> messages, String key) {
        return messages != null ? messages.getOrDefault(key, key) : key;
    }
}
