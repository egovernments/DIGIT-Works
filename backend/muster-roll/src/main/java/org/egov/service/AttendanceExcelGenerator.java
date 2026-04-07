package org.egov.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.config.MusterRollServiceConfiguration;
import org.egov.web.models.report.AttendanceReportData;
import org.egov.web.models.report.AttendanceReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static org.egov.util.AttendanceReportConstants.*;

@Slf4j
@Service
public class AttendanceExcelGenerator {

    private final MusterRollServiceConfiguration config;

    @Autowired
    public AttendanceExcelGenerator(MusterRollServiceConfiguration config) {
        this.config = config;
    }

    public byte[] generateExcel(AttendanceReportData reportData, Map<String, String> localizedMessages,
                                 Map<String, byte[]> signatureImages) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Attendance Report");
            sheet.setDefaultColumnWidth(15);

            int totalDays = reportData.getTotalDays() != null ? reportData.getTotalDays() : 0;
            int colsPerDate = reportData.getSessions() > 1 ? SIGNATURE_COLS_PER_DATE : 2;
            int totalCols = FIXED_COLUMNS_COUNT + totalDays * colsPerDate;

            writeHeaderSection(sheet, reportData, localizedMessages, totalCols);
            writeColumnHeaders(sheet, reportData, localizedMessages, colsPerDate);
            writeDataRows(sheet, reportData, signatureImages, colsPerDate);

            // Fixed column widths (same as original)
            int[] fixedColumnWidths = {8, 25, 15, 15, 12, 15, 15, 15, 12, 25, 12, 15, 15, 22};
            for (int i = 0; i < fixedColumnWidths.length; i++) {
                sheet.setColumnWidth(i, fixedColumnWidths[i] * 256);
            }

            // Signature sub-column widths per date
            for (int d = 0; d < totalDays; d++) {
                int base = FIXED_COLUMNS_COUNT + d * colsPerDate;
                sheet.setColumnWidth(base,     10 * 256); // AM Status
                sheet.setColumnWidth(base + 1, 22 * 256); // AM Signature
                if (colsPerDate > 2) {
                    sheet.setColumnWidth(base + 2, 10 * 256); // PM Status
                    sheet.setColumnWidth(base + 3, 22 * 256); // PM Signature
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Error generating Excel file: {}", e.getMessage(), e);
            throw new IOException("Failed to generate Excel file", e);
        }
    }

    // ── Header section (rows 0-2) ────────────────────────────────────────────────

    private void writeHeaderSection(XSSFSheet sheet, AttendanceReportData reportData,
            Map<String, String> localizedMessages, int totalCols) {
        CellStyle headerStyle    = createHeaderStyle(sheet.getWorkbook());
        CellStyle subHeaderStyle = createSubHeaderStyle(sheet.getWorkbook());

        // Row 0: Title
        XSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(getLocalized(localizedMessages, REPORT_TITLE_KEY));
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalCols - 1));

        // Row 1: Register info
        XSSFRow infoRow1 = sheet.createRow(1);
        infoRow1.setHeightInPoints(18);
        XSSFCell infoCell1 = infoRow1.createCell(0);
        String registerPattern = getLocalized(localizedMessages, REPORT_REGISTER_INFO_KEY);
        String registerInfo = String.format(registerPattern,
                reportData.getMusterRollNumber(),
                formatDate(reportData.getStartDate()),
                formatDate(reportData.getEndDate()),
                reportData.getTotalAttendees());
        infoCell1.setCellValue(registerInfo);
        infoCell1.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, totalCols - 1));

        // Row 2: Campaign info
        XSSFRow infoRow2 = sheet.createRow(2);
        infoRow2.setHeightInPoints(18);
        XSSFCell infoCell2 = infoRow2.createCell(0);
        infoCell2.setCellValue(getLocalized(localizedMessages, REPORT_CAMPAIGN_INFO_KEY));
        infoCell2.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, totalCols - 1));
    }

    // ── 3-level column headers (rows 3, 4, 5) ───────────────────────────────────

    private void writeColumnHeaders(XSSFSheet sheet, AttendanceReportData reportData,
            Map<String, String> localizedMessages, int colsPerDate) {
        CellStyle columnHeaderStyle = createColumnHeaderStyle(sheet.getWorkbook());

        XSSFRow row3 = sheet.createRow(3);
        XSSFRow row4 = sheet.createRow(4);
        XSSFRow row5 = sheet.createRow(5);
        row3.setHeightInPoints(20);
        row4.setHeightInPoints(18);
        row5.setHeightInPoints(16);

        // Fixed columns: merge vertically rows 3-5
        for (int i = 0; i < FIXED_COLUMN_HEADER_KEYS.length; i++) {
            XSSFCell cell = row3.createCell(i);
            cell.setCellValue(getLocalized(localizedMessages, FIXED_COLUMN_HEADER_KEYS[i]));
            cell.setCellStyle(columnHeaderStyle);
            sheet.addMergedRegion(new CellRangeAddress(3, 5, i, i));
            row4.createCell(i).setCellStyle(columnHeaderStyle);
            row5.createCell(i).setCellStyle(columnHeaderStyle);
        }

        // Dynamic date columns
        if (reportData.getCampaignDates() != null) {
            for (int d = 0; d < reportData.getCampaignDates().size(); d++) {
                Long dateMillis = reportData.getCampaignDates().get(d);
                int base = FIXED_COLUMNS_COUNT + d * colsPerDate;

                // Row 3: date merged across colsPerDate cols
                XSSFCell dateCell = row3.createCell(base);
                dateCell.setCellValue(formatDate(dateMillis));
                dateCell.setCellStyle(columnHeaderStyle);
                sheet.addMergedRegion(new CellRangeAddress(3, 3, base, base + colsPerDate - 1));
                for (int k = 1; k < colsPerDate; k++) {
                    row3.createCell(base + k).setCellStyle(columnHeaderStyle);
                }

                if (colsPerDate > 2) {
                    // Row 4: Morning merged over cols 0-1, Evening merged over cols 2-3
                    XSSFCell morningCell = row4.createCell(base);
                    morningCell.setCellValue(getLocalized(localizedMessages, HEADER_MORNING));
                    morningCell.setCellStyle(columnHeaderStyle);
                    sheet.addMergedRegion(new CellRangeAddress(4, 4, base, base + 1));
                    row4.createCell(base + 1).setCellStyle(columnHeaderStyle);

                    XSSFCell eveningCell = row4.createCell(base + 2);
                    eveningCell.setCellValue(getLocalized(localizedMessages, HEADER_EVENING));
                    eveningCell.setCellStyle(columnHeaderStyle);
                    sheet.addMergedRegion(new CellRangeAddress(4, 4, base + 2, base + 3));
                    row4.createCell(base + 3).setCellStyle(columnHeaderStyle);

                    // Row 5: Status, Signature, Status, Signature
                    XSSFCell s1 = row5.createCell(base);
                    s1.setCellValue(getLocalized(localizedMessages, HEADER_STATUS));
                    s1.setCellStyle(columnHeaderStyle);

                    XSSFCell s2 = row5.createCell(base + 1);
                    s2.setCellValue(getLocalized(localizedMessages, HEADER_SIGNATURE));
                    s2.setCellStyle(columnHeaderStyle);

                    XSSFCell s3 = row5.createCell(base + 2);
                    s3.setCellValue(getLocalized(localizedMessages, HEADER_STATUS));
                    s3.setCellStyle(columnHeaderStyle);

                    XSSFCell s4 = row5.createCell(base + 3);
                    s4.setCellValue(getLocalized(localizedMessages, HEADER_SIGNATURE));
                    s4.setCellStyle(columnHeaderStyle);
                } else {
                    // Single session: Morning header merged across both cols in row 4
                    XSSFCell morningCell = row4.createCell(base);
                    morningCell.setCellValue(getLocalized(localizedMessages, HEADER_MORNING));
                    morningCell.setCellStyle(columnHeaderStyle);
                    sheet.addMergedRegion(new CellRangeAddress(4, 4, base, base + 1));
                    row4.createCell(base + 1).setCellStyle(columnHeaderStyle);

                    // Row 5: Status, Signature only
                    XSSFCell s1 = row5.createCell(base);
                    s1.setCellValue(getLocalized(localizedMessages, HEADER_STATUS));
                    s1.setCellStyle(columnHeaderStyle);

                    XSSFCell s2 = row5.createCell(base + 1);
                    s2.setCellValue(getLocalized(localizedMessages, HEADER_SIGNATURE));
                    s2.setCellStyle(columnHeaderStyle);
                }
            }
        }
    }

    // ── Data rows (starting row 6) ───────────────────────────────────────────────

    private void writeDataRows(XSSFSheet sheet, AttendanceReportData reportData,
            Map<String, byte[]> signatureImages, int colsPerDate) {
        CellStyle dataStyle     = createDataStyle(sheet.getWorkbook());
        CellStyle dateDataStyle = createDateDataStyle(sheet.getWorkbook());

        int rowIndex = 6;
        if (reportData.getAttendanceDetails() == null) return;

        for (AttendanceReportDetail detail : reportData.getAttendanceDetails()) {
            XSSFRow row = sheet.createRow(rowIndex);
            row.setHeightInPoints(75); // tall enough for 100px images

            int col = 0;
            setCellValue(row, col++, detail.getSerialNumber(), dataStyle);
            setCellValue(row, col++, detail.getName(), dataStyle);
            setCellValue(row, col++, detail.getLoginId(), dataStyle);
            setCellValue(row, col++, detail.getUserId(), dataStyle);
            setCellValue(row, col++, detail.getTeamCode(), dataStyle);
            setCellValue(row, col++, detail.getRole(), dataStyle);
            setCellValue(row, col++, detail.getPhoneNumber(), dataStyle);
            setCellValue(row, col++, formatDate(detail.getEnrollmentDate()), dateDataStyle);
            setCellValue(row, col++, formatDate(detail.getDeEnrollmentDate()), dateDataStyle);
            setCellValue(row, col++, detail.getAttendanceMarker(), dataStyle);
            setCellValue(row, col++, detail.getPresentDaysOriginal(), dataStyle);
            setCellValue(row, col++, detail.getPresentDaysModified(), dataStyle);
            setCellValue(row, col++, detail.getTotalPerformance(), dataStyle);

            // Base Signature
            byte[] baseSigImg = (detail.getBaseSignatureFileStoreId() != null)
                    ? signatureImages.get(detail.getBaseSignatureFileStoreId()) : null;
            if (baseSigImg != null) {
                row.createCell(col).setCellStyle(dataStyle);
                embedImage(sheet, baseSigImg, rowIndex, col);
            } else {
                setCellValue(row, col, SIGNATURE_NA, dataStyle);
            }
            col++;

            // Dynamic date columns
            if (reportData.getCampaignDates() != null && detail.getDailyAttendance() != null) {
                for (Long dateMillis : reportData.getCampaignDates()) {
                    String dateStr = formatDate(dateMillis);

                    String[] sigIds = detail.getDailySignatureIds() != null
                            ? detail.getDailySignatureIds().get(dateStr) : null;
                    String morningId = (sigIds != null) ? sigIds[0] : null;

                    String[] sessionStatus = (detail.getDailySessionAttendance() != null)
                            ? detail.getDailySessionAttendance().get(dateStr) : null;
                    String morningStatus = (sessionStatus != null && sessionStatus[0] != null)
                            ? sessionStatus[0] : ATTENDANCE_STATUS_ABSENT;
                    String eveningStatus = (sessionStatus != null && sessionStatus.length > 1 && sessionStatus[1] != null)
                            ? sessionStatus[1] : ATTENDANCE_STATUS_ABSENT;

                    // AM Status
                    setCellValue(row, col++, morningStatus, dataStyle);

                    // AM Signature
                    byte[] amImg = (morningId != null) ? signatureImages.get(morningId) : null;
                    if (amImg != null) {
                        row.createCell(col).setCellStyle(dataStyle);
                        embedImage(sheet, amImg, rowIndex, col);
                    } else {
                        setCellValue(row, col, SIGNATURE_NA, dataStyle);
                    }
                    col++;

                    if (colsPerDate > 2) {
                        String eveningId = (sigIds != null && sigIds.length > 1) ? sigIds[1] : null;

                        // PM Status
                        setCellValue(row, col++, eveningStatus, dataStyle);

                        // PM Signature
                        byte[] pmImg = (eveningId != null) ? signatureImages.get(eveningId) : null;
                        if (pmImg != null) {
                            row.createCell(col).setCellStyle(dataStyle);
                            embedImage(sheet, pmImg, rowIndex, col);
                        } else {
                            setCellValue(row, col, SIGNATURE_NA, dataStyle);
                        }
                        col++;
                    }
                }
            }

            rowIndex++;
        }
    }

    // ── Image embedding ──────────────────────────────────────────────────────────

    private void embedImage(XSSFSheet sheet, byte[] imageBytes, int rowIdx, int colIdx) {
        try {
            int pictureIdx = sheet.getWorkbook().addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, colIdx, rowIdx, colIdx + 1, rowIdx + 1);
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            drawing.createPicture(anchor, pictureIdx);
        } catch (Exception e) {
            log.warn("Failed to embed image at row={}, col={}: {}", rowIdx, colIdx, e.getMessage());
        }
    }

    // ── Cell helpers ─────────────────────────────────────────────────────────────

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

    // ── Cell style factories (same as original) ──────────────────────────────────

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
        style.setWrapText(true);
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
        style.setWrapText(true);
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
        style.setWrapText(true);
        return style;
    }

    // ── Formatting helpers ───────────────────────────────────────────────────────

    private String formatDate(Long milliseconds) {
        if (milliseconds == null || milliseconds == 0) return "-";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(config.getReportDateFormat());
            formatter.setTimeZone(TimeZone.getTimeZone(config.getReportTimezone()));
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
