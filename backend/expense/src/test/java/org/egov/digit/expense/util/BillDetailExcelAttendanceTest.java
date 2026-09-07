package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.egov.digit.expense.config.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Round-trip coverage for the attendance ceiling: generate a template, edit the
 * Total Attendance column, parse it back. Guards HCMPRE bug — reviewer could enter
 * any number of days in the bulk Excel and upload it successfully.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BillDetailExcelAttendanceTest {

    private static final String ZONE     = "Africa/Maputo";
    private static final String TENANT   = "mz";
    private static final String HEAD_CODE = "BASIC";
    // 2 Feb – 1 Mar 2026 inclusive
    private static final int PERIOD_DAYS = 28;

    @Mock private LocalizationUtil localizationUtil;
    @Mock private IndividualUtil individualUtil;
    @Mock private Configuration config;
    @Mock private MdmsUtil mdmsUtil;

    private BillDetailExcelGenerator generator;
    private BillDetailExcelParser parser;

    private final RequestInfo requestInfo = RequestInfo.builder().msgId("1|en_MZ").build();
    private final Set<String> reviewer = Set.of(ROLE_PAYMENT_REVIEWER);
    private final Set<String> editor   = Set.of(ROLE_PAYMENT_EDITOR);
    private final Set<String> bothRoles = Set.of(ROLE_PAYMENT_EDITOR, ROLE_PAYMENT_REVIEWER);

    @BeforeEach
    void setUp() {
        when(config.getExcelSheetProtectPassword()).thenReturn("pwd");
        when(config.getLocalizationDefaultLocale()).thenReturn("en_MZ");
        when(config.getTemplateLocalizationModule()).thenReturn("expense");
        when(config.isHealthContextEnabled()).thenReturn(false);
        when(localizationUtil.getLocalisedMessages(any(), anyString(), anyString(), anyString()))
                .thenReturn(new HashMap<>());
        when(individualUtil.getIndividualDetails(any(), anyString(), anyString())).thenReturn(null);

        ObjectMapper mapper = new ObjectMapper();
        BillPeriodUtil billPeriodUtil = new BillPeriodUtil(mapper);
        generator = new BillDetailExcelGenerator(localizationUtil, individualUtil, config,
                mapper, mdmsUtil, billPeriodUtil);
        parser = new BillDetailExcelParser(localizationUtil, config, mapper, generator, billPeriodUtil);
    }

    // ── fixtures ──────────────────────────────────────────────────────────────

    private Bill bill(Object additionalDetails, String... workerIds) {
        return bill(additionalDetails, Status.UNDER_REVIEW, workerIds);
    }

    private Bill bill(Object additionalDetails, Status status, String... workerIds) {
        List<BillDetail> details = new ArrayList<>();
        for (String workerId : workerIds) {
            details.add(BillDetail.builder()
                    .id("detail-" + workerId)
                    .tenantId(TENANT)
                    .workerId(workerId)
                    .totalAttendance(BigDecimal.valueOf(10))
                    .payee(Party.builder().tenantId(TENANT).identifier("ind-" + workerId)
                            .payeeName("Payee " + workerId).build())
                    .lineItems(new ArrayList<>(List.of(LineItem.builder()
                            .id("li-" + workerId)
                            .tenantId(TENANT)
                            .headCode(HEAD_CODE)
                            .type(LineItemType.PAYABLE)
                            .amount(BigDecimal.valueOf(1500))
                            .build())))
                    .build());
        }
        return Bill.builder().id("bill-1").tenantId(TENANT)
                .status(status)
                .additionalDetails(additionalDetails)
                .billDetails(details)
                .build();
    }

    private Map<String, Object> period() {
        ZoneId zone = ZoneId.of(ZONE);
        Map<String, Object> ad = new HashMap<>();
        ad.put(PERIOD_START_DATE_KEY,
                LocalDate.of(2026, 2, 2).atStartOfDay(zone).toInstant().toEpochMilli());
        ad.put(PERIOD_END_DATE_KEY,
                LocalDate.of(2026, 3, 1).atTime(23, 59, 59).atZone(zone).toInstant().toEpochMilli());
        return ad;
    }

    /** Overwrites the Total Attendance cell on the given data row, as a reviewer would. */
    private byte[] withAttendance(byte[] template, Map<Integer, String> valuesByRow) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template))) {
            XSSFSheet sheet = wb.getSheetAt(0);
            int attendanceCol = BillDetailExcelGenerator.STATIC_COL_COUNT + 1; // one head code
            valuesByRow.forEach((rowIdx, value) -> {
                Cell cell = sheet.getRow(rowIdx).getCell(attendanceCol);
                cell.setCellValue(Double.parseDouble(value));
            });
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    private byte[] template(Bill bill, Set<String> roles) {
        return generator.generateTemplate(bill, roles, requestInfo);
    }

    // ── the sheet itself ──────────────────────────────────────────────────────

    @Test
    void sheetCapsAttendanceAtThePeriodLength() throws Exception {
        byte[] bytes = template(bill(period(), "W1"), reviewer);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            int attendanceCol = BillDetailExcelGenerator.STATIC_COL_COUNT + 1;
            Optional<XSSFDataValidation> dv = wb.getSheetAt(0).getDataValidations().stream()
                    .filter(v -> Arrays.stream(v.getRegions().getCellRangeAddresses())
                            .anyMatch(r -> r.getFirstColumn() == attendanceCol))
                    .findFirst();

            assertTrue(dv.isPresent(), "attendance column must carry a data validation");
            assertEquals(String.valueOf(PERIOD_DAYS),
                    dv.get().getValidationConstraint().getFormula2());
            assertEquals(DataValidation.ErrorStyle.STOP, dv.get().getErrorStyle());
        }
    }

    @Test
    void attendanceColumnIsLockedForEditors() throws Exception {
        // The editor's change is stripped server-side, so the sheet must not invite it
        byte[] bytes = template(bill(period(), Status.PENDING_VERIFICATION, "W1"), editor);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            int attendanceCol = BillDetailExcelGenerator.STATIC_COL_COUNT + 1;
            Cell cell = wb.getSheetAt(0).getRow(1).getCell(attendanceCol);
            assertTrue(cell.getCellStyle().getLocked(), "attendance must be locked for PAYMENT_EDITOR");
        }
    }

    // ── upload ────────────────────────────────────────────────────────────────

    @Test
    void rejectsAttendanceBeyondThePeriod() throws Exception {
        Bill bill = bill(period(), "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "9999"));

        CustomException ex = assertThrows(CustomException.class,
                () -> parser.parse(edited, bill, reviewer, requestInfo));

        assertEquals(ERR_TEMPLATE_INVALID_ATTENDANCE, ex.getCode());
        assertTrue(ex.getMessage().contains("W1"), ex.getMessage());
        assertTrue(ex.getMessage().contains("Row 2"), ex.getMessage());
        assertTrue(ex.getMessage().contains("between 0 and 28"), ex.getMessage());
    }

    @Test
    void acceptsAttendanceExactlyAtTheCeiling() throws Exception {
        Bill bill = bill(period(), "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, String.valueOf(PERIOD_DAYS)));

        List<PartialBillDetail> parsed = parser.parse(edited, bill, reviewer, requestInfo);

        assertEquals(1, parsed.size());
        assertEquals(0, parsed.get(0).getTotalAttendance().compareTo(BigDecimal.valueOf(PERIOD_DAYS)));
    }

    @Test
    void acceptsZeroAttendanceForAbsentees() throws Exception {
        // The calculator bills absentees with totalAttendance=0 — rejecting 0 would make
        // any bill containing an absent worker un-uploadable.
        Bill bill = bill(period(), "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "0"));

        List<PartialBillDetail> parsed = parser.parse(edited, bill, reviewer, requestInfo);

        assertEquals(0, parsed.get(0).getTotalAttendance().compareTo(BigDecimal.ZERO));
    }

    @Test
    void acceptsHalfDayAttendance() throws Exception {
        // muster-roll records 0.5 per half day, so totals are legitimately fractional
        Bill bill = bill(period(), "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "17.5"));

        List<PartialBillDetail> parsed = parser.parse(edited, bill, reviewer, requestInfo);

        assertEquals(0, parsed.get(0).getTotalAttendance().compareTo(new BigDecimal("17.5")));
    }

    @Test
    void rejectsNegativeAttendance() throws Exception {
        Bill bill = bill(period(), "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "-1"));

        assertThrows(CustomException.class, () -> parser.parse(edited, bill, reviewer, requestInfo));
    }

    @Test
    void reportsEveryBadRowNotJustTheFirst() throws Exception {
        Bill bill = bill(period(), "W1", "W2", "W3");
        // rows are sorted by payee name, so W1..W3 land on rows 1..3
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "9999", 3, "500"));

        CustomException ex = assertThrows(CustomException.class,
                () -> parser.parse(edited, bill, reviewer, requestInfo));

        assertTrue(ex.getMessage().contains("W1"), ex.getMessage());
        assertTrue(ex.getMessage().contains("W3"), ex.getMessage());
    }

    // ── dual-role users act in one mode, decided by bill status ───────────────

    @Test
    void dualRoleUserOnAnEditorStageBillGetsAttendanceLocked() throws Exception {
        // BillValidator takes the editor branch here and strips attendance, so the
        // sheet must not present it as editable
        byte[] bytes = template(bill(period(), Status.PENDING_VERIFICATION, "W1"), bothRoles);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            int attendanceCol = BillDetailExcelGenerator.STATIC_COL_COUNT + 1;
            Cell cell = wb.getSheetAt(0).getRow(1).getCell(attendanceCol);
            assertTrue(cell.getCellStyle().getLocked(),
                    "dual-role user on a PENDING_VERIFICATION bill is in editor mode");
        }
    }

    @Test
    void dualRoleEditorStageUploadIsNotRejectedByAStaleAttendanceValue() throws Exception {
        // The stored attendance already exceeds the ceiling (legacy bad data — the very
        // reason this ticket exists). An editor-mode payee update must still go through,
        // since the attendance is locked, unchanged, and stripped downstream anyway.
        Bill bill = bill(period(), Status.PENDING_VERIFICATION, "W1");
        bill.getBillDetails().get(0).setTotalAttendance(BigDecimal.valueOf(500));

        byte[] template = template(bill, bothRoles);
        List<PartialBillDetail> parsed = parser.parse(template, bill, bothRoles, requestInfo);

        assertEquals(1, parsed.size());
        assertNull(parsed.get(0).getTotalAttendance(), "attendance is not a field of editor mode");
        assertNotNull(parsed.get(0).getPayee(), "the payee edit still comes through");
    }

    @Test
    void dualRoleUserOnAReviewStageBillStillGetsTheCeiling() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        byte[] edited = withAttendance(template(bill, bothRoles), Map.of(1, "9999"));

        CustomException ex = assertThrows(CustomException.class,
                () -> parser.parse(edited, bill, bothRoles, requestInfo));
        assertEquals(ERR_TEMPLATE_INVALID_ATTENDANCE, ex.getCode());
    }

    @Test
    void billWithNoPeriodKeepsWorkingWithoutACeiling() throws Exception {
        // Legacy bills carry no periodStartDate/periodEndDate — they must stay uploadable
        Bill bill = bill(null, "W1");
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "9999"));

        List<PartialBillDetail> parsed = parser.parse(edited, bill, reviewer, requestInfo);

        assertEquals(1, parsed.size());
    }
}
