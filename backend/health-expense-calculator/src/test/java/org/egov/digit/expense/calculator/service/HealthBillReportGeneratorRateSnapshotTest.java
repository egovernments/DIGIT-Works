package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.Name;
import org.egov.common.models.individual.Skill;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.web.models.BillDetail;
import org.egov.digit.expense.calculator.web.models.LineItem;
import org.egov.digit.expense.calculator.web.models.RateFieldConfig;
import org.egov.digit.expense.calculator.web.models.WorkerMdms;
import org.egov.digit.expense.calculator.web.models.WorkerRate;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Rate reporting at zero attendance. Dividing the stored amount by 0 days has no inverse, so the
 * row falls back to the stored rate snapshot — and, for bills written before that snapshot existed,
 * to the live MDMS rate rather than reporting 0.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HealthBillReportGeneratorRateSnapshotTest {

    private static final String SKILL     = "WORKER";
    private static final String FIELD_KEY = "PER_DAY";
    private static final String HEAD_CODE = "BASIC";
    private static final String DETAIL_KEY = "wage";

    @Mock private ExpenseCalculatorConfiguration config;

    @Spy private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks private HealthBillReportGenerator reportGenerator;

    private BillDetail billDetail(BigDecimal attendance, BigDecimal amount, Object additionalDetails) {
        return BillDetail.builder()
                .id("d1")
                .referenceId("mr1")
                .totalAttendance(attendance)
                .totalAmount(amount)
                .additionalDetails(additionalDetails)
                .payableLineItems(new ArrayList<>(List.of(
                        LineItem.builder().headCode(HEAD_CODE).amount(amount).build())))
                .build();
    }

    private Individual worker() {
        return Individual.builder()
                .id("ind1")
                .name(Name.builder().givenName("A").familyName("B").build())
                .skills(new ArrayList<>(List.of(
                        Skill.builder().type(SKILL).isDeleted(false).build())))
                .build();
    }

    private WorkerMdms workerMdms() {
        RateFieldConfig fc = new RateFieldConfig();
        fc.setFieldKey(FIELD_KEY);
        fc.setValueType("FLAT");
        fc.setPaymentType("PER_DAY");
        fc.setReportDetailKey(DETAIL_KEY);
        fc.setIsPayable(true);
        fc.setOrder(1);
        return WorkerMdms.builder()
                .fieldConfig(new ArrayList<>(List.of(fc)))
                .headCodeMapping(Map.of(FIELD_KEY, HEAD_CODE))
                .build();
    }

    private Map<String, WorkerRate> mdmsRates(double rate) {
        return Map.of(SKILL, WorkerRate.builder()
                .skillCode(SKILL)
                .rateBreakup(Map.of(FIELD_KEY, BigDecimal.valueOf(rate)))
                .build());
    }

    private Map<String, Object> snapshot(double rate) {
        Map<String, Object> ad = new HashMap<>();
        ad.put("rateBreakup", Map.of(FIELD_KEY, BigDecimal.valueOf(rate)));
        return ad;
    }

    private ReportBillDetail report(BillDetail detail, double mdmsRate) {
        return (ReportBillDetail) ReflectionTestUtils.invokeMethod(
                reportGenerator, "getReportBillDetail",
                detail, worker(), Collections.<String, Map<String, BigDecimal>>emptyMap(),
                mdmsRates(mdmsRate), Map.of(HEAD_CODE, DETAIL_KEY), workerMdms());
    }

    @Test
    @DisplayName("a bill written before the snapshot existed reports the MDMS rate, not 0")
    void preSnapshotBillAtZeroDaysFallsBackToTheMdmsRate() {
        BillDetail detail = billDetail(BigDecimal.ZERO, BigDecimal.ZERO, null);

        ReportBillDetail report = report(detail, 150);

        assertEquals(0, report.getPerDayBreakup().get(DETAIL_KEY).compareTo(BigDecimal.valueOf(150)));
        assertEquals(0, report.getTotalWages().compareTo(BigDecimal.valueOf(150)));
    }

    @Test
    @DisplayName("a stored snapshot still outranks the live MDMS rate at zero days")
    void aStoredSnapshotWinsOverMdmsAtZeroDays() {
        BillDetail detail = billDetail(BigDecimal.ZERO, BigDecimal.ZERO, snapshot(120));

        ReportBillDetail report = report(detail, 150);

        assertEquals(0, report.getPerDayBreakup().get(DETAIL_KEY).compareTo(BigDecimal.valueOf(120)));
    }

    @Test
    @DisplayName("with days on the bill the stored amount is still what gets divided")
    void nonZeroDaysStillDividesTheStoredAmount() {
        // guards the fallback against leaking into the ordinary path
        BillDetail detail = billDetail(BigDecimal.valueOf(2), BigDecimal.valueOf(300), snapshot(120));

        ReportBillDetail report = report(detail, 999);

        assertEquals(0, report.getPerDayBreakup().get(DETAIL_KEY).compareTo(BigDecimal.valueOf(150)));
    }
}
