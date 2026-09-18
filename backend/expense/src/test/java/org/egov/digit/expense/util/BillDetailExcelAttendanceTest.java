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

    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    // ── rate snapshot survives an attendance edit (HCMPRE-4444) ──────────────

    /** Seeds additionalDetails.rateBreakup on every detail, as the calculator now does. */
    /** Amounts track rate x attendance — the calculator never writes a bill where they disagree. */
    private void seedRates(Bill bill, double rate) {
        for (BillDetail d : bill.getBillDetails()) {
            Map<String, Object> ad = new HashMap<>();
            ad.put(BILL_DETAIL_RATE_BREAKUP_KEY, Map.of(HEAD_CODE, BigDecimal.valueOf(rate)));
            ad.put("individualId", "ind-" + d.getWorkerId());
            ad.put("editInfo", Map.of("payeeUpdatedAtEpochMs", 1L));
            d.setAdditionalDetails(ad);
            for (LineItem li : d.getLineItems())
                li.setAmount(BigDecimal.valueOf(rate).multiply(d.getTotalAttendance()));
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> ratesOf(PartialBillDetail pd) {
        return (Map<String, Object>) adOf(pd).get(BILL_DETAIL_RATE_BREAKUP_KEY);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> adOf(PartialBillDetail pd) {
        return MAPPER.convertValue(pd.getAdditionalDetails(), Map.class);
    }

    @Test
    void sheetShowsTheSnapshotRateEvenWhenAttendanceIsZero() throws Exception {
        // Previously 0/0 rendered as 0 and the rate was gone for good
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        bill.getBillDetails().get(0).setTotalAttendance(BigDecimal.ZERO);
        bill.getBillDetails().get(0).getLineItems().get(0).setAmount(BigDecimal.ZERO);
        seedRates(bill, 10);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template(bill, reviewer)))) {
            int rateCol = BillDetailExcelGenerator.STATIC_COL_COUNT; // single head code
            assertEquals(10.0, wb.getSheetAt(0).getRow(1).getCell(rateCol).getNumericCellValue(), 0.001);
        }
    }

    @Test
    void zeroingAttendanceZeroesTheAmountButKeepsTheRate() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "0"));

        PartialBillDetail pd = parser.parse(edited, bill, reviewer, requestInfo).get(0);

        assertEquals(0, pd.getTotalAmount().compareTo(BigDecimal.ZERO), "0 days must pay 0");
        assertEquals(0, new BigDecimal(ratesOf(pd).get(HEAD_CODE).toString()).compareTo(BigDecimal.TEN),
                "the rate must survive");
    }

    @Test
    void attendanceBackFromZeroPaysTheRateAgain() throws Exception {
        // The reported bug: 0 -> 1 day still paid 0 because the rate had been overwritten
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);

        PartialBillDetail zeroed = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "0")), bill, reviewer, requestInfo).get(0);

        // apply that save back onto the bill, as the DB would hold it
        BillDetail detail = bill.getBillDetails().get(0);
        detail.setTotalAttendance(zeroed.getTotalAttendance());
        detail.setLineItems(new ArrayList<>(zeroed.getLineItems()));
        detail.setAdditionalDetails(zeroed.getAdditionalDetails());

        PartialBillDetail restored = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "1")), bill, reviewer, requestInfo).get(0);

        assertEquals(0, restored.getTotalAmount().compareTo(BigDecimal.valueOf(10)),
                "1 day at rate 10 must pay 10, not 0");
    }

    @Test
    void reviewerRateEditStillPersists() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);

        byte[] tpl = template(bill, reviewer);
        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(tpl))) {
            wb.getSheetAt(0).getRow(1).getCell(BillDetailExcelGenerator.STATIC_COL_COUNT).setCellValue(20.0);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            tpl = out.toByteArray();
        }

        PartialBillDetail pd = parser.parse(tpl, bill, reviewer, requestInfo).get(0);
        assertEquals(0, new BigDecimal(ratesOf(pd).get(HEAD_CODE).toString())
                .compareTo(BigDecimal.valueOf(20)), "a deliberate rate change must stick");
    }

    @Test
    void mergingTheRateSnapshotPreservesOtherAdditionalDetailKeys() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "2"));

        PartialBillDetail pd = parser.parse(edited, bill, reviewer, requestInfo).get(0);
        Map<String, Object> ad = adOf(pd);

        assertNotNull(ad.get("editInfo"), "EnrichmentUtil replaces wholesale — editInfo must be kept");
        assertNotNull(ad.get("individualId"));
        assertEquals(0, new BigDecimal(ad.get("noOfDaysWorked").toString())
                .compareTo(BigDecimal.valueOf(2)), "noOfDaysWorked must track attendance");
    }

    @Test
    void billsWithoutASnapshotStillDeriveTheRateFromTheAmount() throws Exception {
        // Pre-fix bills carry no rateBreakup: 1500 over 10 days -> 150/day
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        bill.getBillDetails().get(0).setTotalAttendance(BigDecimal.TEN);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template(bill, reviewer)))) {
            int rateCol = BillDetailExcelGenerator.STATIC_COL_COUNT;
            assertEquals(150.0, wb.getSheetAt(0).getRow(1).getCell(rateCol).getNumericCellValue(), 0.001);
        }
    }

    // ── the persisted amount outranks the snapshot wherever it can be divided back out ──

    /** Overwrites the first rate column of a row so a damaged cell can be round-tripped. */
    private byte[] withRateCell(byte[] tpl, int rowIdx, String text) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(tpl))) {
            Cell cell = wb.getSheetAt(0).getRow(rowIdx).getCell(BillDetailExcelGenerator.STATIC_COL_COUNT);
            if (text == null) cell.setBlank(); else cell.setCellValue(text);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        }
    }

    @Test
    void anAmountCorrectedOutsideTheSheetWinsOverTheSnapshot() throws Exception {
        // A whole-bill _update changes the amount without touching the snapshot. Showing the
        // stale rate would revert the correction the moment the sheet is uploaded again.
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);                       // 10 x 10 days = 100
        bill.getBillDetails().get(0).getLineItems().get(0).setAmount(BigDecimal.valueOf(150));

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template(bill, reviewer)))) {
            int rateCol = BillDetailExcelGenerator.STATIC_COL_COUNT;
            assertEquals(15.0, wb.getSheetAt(0).getRow(1).getCell(rateCol).getNumericCellValue(), 0.001,
                    "150 over 10 days is 15/day; the snapshot's 10 is stale");
        }
    }

    @Test
    void reUploadingAnUntouchedSheetKeepsACorrectedAmount() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        bill.getBillDetails().get(0).getLineItems().get(0).setAmount(BigDecimal.valueOf(150));

        PartialBillDetail pd = parser.parse(template(bill, reviewer), bill, reviewer, requestInfo).get(0);

        assertEquals(0, pd.getTotalAmount().compareTo(BigDecimal.valueOf(150)),
                "a no-op round trip must not pull the amount back down to 10 x 10");
    }

    @Test
    void aHeadCodeWithNoLineItemStillShowsTheSnapshotRate() throws Exception {
        // No amount to divide, so the snapshot is the only rate there is — the absentee case
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1", "W2");
        seedRates(bill, 10);
        BillDetail absent = bill.getBillDetails().stream()
                .filter(d -> "W1".equals(d.getWorkerId())).findFirst().orElseThrow();
        absent.setLineItems(new ArrayList<>());
        absent.setPayableLineItems(new ArrayList<>());

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template(bill, reviewer)))) {
            int rateCol = BillDetailExcelGenerator.STATIC_COL_COUNT;
            assertEquals(10.0, wb.getSheetAt(0).getRow(1).getCell(rateCol).getNumericCellValue(), 0.001);
        }
    }

    // ── an unreadable rate cell is a row error, not a persisted 0 ─────────────

    @Test
    void aTextRateCellIsRejectedInsteadOfPersistingAZeroRate() throws Exception {
        // Pasting a column from another sheet lands as text; it used to read as 0 and, now that
        // the rate is persisted, would zero the worker for good with no warning.
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        byte[] tpl = withRateCell(template(bill, reviewer), 1, "1,200");

        CustomException ex = assertThrows(CustomException.class,
                () -> parser.parse(tpl, bill, reviewer, requestInfo));

        assertEquals(ERR_TEMPLATE_INVALID_RATE, ex.getCode());
        assertTrue(ex.getMessage().contains(HEAD_CODE), ex.getMessage());
        assertTrue(ex.getMessage().contains("W1"), ex.getMessage());
    }

    @Test
    void aBlankRateCellIsRejectedToo() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        byte[] tpl = withRateCell(template(bill, reviewer), 1, null);

        CustomException ex = assertThrows(CustomException.class,
                () -> parser.parse(tpl, bill, reviewer, requestInfo));

        assertEquals(ERR_TEMPLATE_INVALID_RATE, ex.getCode());
    }

    @Test
    void aRateOfZeroIsStillAValidEntry() throws Exception {
        // 0 typed deliberately is not the same as a cell that could not be read
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);
        byte[] tpl = withRateCell(template(bill, reviewer), 1, "0");

        PartialBillDetail pd = parser.parse(tpl, bill, reviewer, requestInfo).get(0);

        assertEquals(0, pd.getTotalAmount().compareTo(BigDecimal.ZERO));
    }

    /**
     * The calculator omits zero-amount line items, so a worker absent at bill creation has no
     * payable row. Restoring their days must still pay them.
     */
    @Test
    void restoringDaysPaysAWorkerWhoHadNoLineItemAtBillCreation() throws Exception {
        // W2 keeps its line items so the bill still has head codes (and so a rate column);
        // W1 was absent at creation, so the calculator left it with no payable row.
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1", "W2");
        BillDetail absent = bill.getBillDetails().stream()
                .filter(d -> "W1".equals(d.getWorkerId())).findFirst().orElseThrow();
        absent.setTotalAttendance(BigDecimal.ZERO);
        absent.setLineItems(new ArrayList<>());
        absent.setPayableLineItems(new ArrayList<>());
        seedRates(bill, 10);

        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "5"));
        PartialBillDetail pd = parser.parse(edited, bill, reviewer, requestInfo).stream()
                .filter(p -> "detail-W1".equals(p.getId())).findFirst().orElseThrow();

        assertEquals(1, pd.getLineItems().size(), "a payable line item must be created");
        assertEquals(HEAD_CODE, pd.getLineItems().get(0).getHeadCode());
        assertNull(pd.getLineItems().get(0).getId(), "null id lets EnrichmentUtil assign one");
        assertEquals("detail-W1", pd.getLineItems().get(0).getBillDetailId(),
                "billdetailid is NOT NULL with an FK to eg_expense_billdetail");
        assertEquals(0, pd.getTotalAmount().compareTo(BigDecimal.valueOf(50)), "5 days at 10 = 50");
    }

    @Test
    void noLineItemIsCreatedForAZeroRate() throws Exception {
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1", "W2");
        BillDetail absent = bill.getBillDetails().stream()
                .filter(d -> "W1".equals(d.getWorkerId())).findFirst().orElseThrow();
        absent.setLineItems(new ArrayList<>());
        absent.setPayableLineItems(new ArrayList<>());
        Map<String, Object> ad = new HashMap<>();
        ad.put(BILL_DETAIL_RATE_BREAKUP_KEY, Map.of(HEAD_CODE, BigDecimal.ZERO));
        absent.setAdditionalDetails(ad);

        byte[] edited = withAttendance(template(bill, reviewer), Map.of(1, "5"));
        PartialBillDetail pd = parser.parse(edited, bill, reviewer, requestInfo).stream()
                .filter(p -> "detail-W1".equals(p.getId())).findFirst().orElseThrow();

        assertTrue(pd.getLineItems().isEmpty(), "rate 0 must not litter a zero row");
    }

    @Test
    void attendanceKeysAreBothSyncedForTheReportGenerator() throws Exception {
        // HealthBillReportGenerator reads additionalDetails.attendance, not noOfDaysWorked
        Bill bill = bill(period(), Status.UNDER_REVIEW, "W1");
        seedRates(bill, 10);

        PartialBillDetail pd = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "2")), bill, reviewer, requestInfo).get(0);
        Map<String, Object> ad = adOf(pd);

        assertEquals(0, new BigDecimal(ad.get(BILL_DETAIL_ATTENDANCE_KEY).toString())
                .compareTo(BigDecimal.valueOf(2)), "attendance is the key the report reads");
        assertEquals(0, new BigDecimal(ad.get(BILL_DETAIL_DAYS_WORKED_KEY).toString())
                .compareTo(BigDecimal.valueOf(2)));
    }

    // ── health context: the fieldConfig branch, which is what production takes ────

    /** Bill carrying a workerRatesSnapshot, so buildFieldConfigContext takes the fieldConfig path. */
    private Bill healthBill(String... workerIds) {
        Bill bill = bill(period(), Status.UNDER_REVIEW, workerIds);
        Map<String, Object> ad = new HashMap<>(MAPPER.convertValue(bill.getAdditionalDetails(), Map.class));
        ad.put(RATE_FIELD_CONFIG_SNAPSHOT_KEY, List.of(Map.of(
                "order", 1, "fieldKey", HEAD_CODE, "isPayable", true,
                "valueType", "FLAT", "paymentType", "PER_DAY",
                "columnLabelKey", "PDF_STATIC_LABEL_BILL_TABLE_WAGE")));
        bill.setAdditionalDetails(ad);
        return bill;
    }

    @Test
    void healthContextZeroAttendanceKeepsTheRate() throws Exception {
        Bill bill = healthBill("W1");
        seedRates(bill, 10);

        PartialBillDetail pd = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "0")), bill, reviewer, requestInfo).get(0);

        assertEquals(0, pd.getTotalAmount().compareTo(BigDecimal.ZERO));
        assertEquals(0, new BigDecimal(ratesOf(pd).get(HEAD_CODE).toString()).compareTo(BigDecimal.TEN),
                "fieldConfig branch must keep the rate too");
    }

    @Test
    void healthContextSheetShowsTheSnapshotRateAtZeroAttendance() throws Exception {
        Bill bill = healthBill("W1");
        bill.getBillDetails().get(0).setTotalAttendance(BigDecimal.ZERO);
        bill.getBillDetails().get(0).getLineItems().get(0).setAmount(BigDecimal.ZERO);
        seedRates(bill, 10);

        try (XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(template(bill, reviewer)))) {
            int rateCol = BillDetailExcelGenerator.STATIC_COL_COUNT;
            assertEquals(10.0, wb.getSheetAt(0).getRow(1).getCell(rateCol).getNumericCellValue(), 0.001);
        }
    }

    @Test
    void healthContextRestoringDaysFromZeroPaysAgain() throws Exception {
        Bill bill = healthBill("W1");
        seedRates(bill, 10);

        PartialBillDetail zeroed = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "0")), bill, reviewer, requestInfo).get(0);
        BillDetail detail = bill.getBillDetails().get(0);
        detail.setTotalAttendance(zeroed.getTotalAttendance());
        detail.setLineItems(new ArrayList<>(zeroed.getLineItems()));
        detail.setAdditionalDetails(zeroed.getAdditionalDetails());

        PartialBillDetail restored = parser.parse(
                withAttendance(template(bill, reviewer), Map.of(1, "1")), bill, reviewer, requestInfo).get(0);

        assertEquals(0, restored.getTotalAmount().compareTo(BigDecimal.TEN));
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
