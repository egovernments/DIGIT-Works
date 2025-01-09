package org.egov.digit.expense.calculator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Address;
import org.egov.common.models.individual.Identifier;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.Skill;
import org.egov.common.models.project.ProjectResponse;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.web.models.report.BillReportRequest;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.egov.works.services.common.models.expense.calculator.IndividualEntry;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.BillReportConstraints.*;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.WF_SUBMIT_ACTION_CONSTANT;

@Slf4j
@Service
public class HealthBillReportGenerator {

    private final IndividualUtil individualUtil;
    private final ExpenseCalculatorUtil expenseCalculatorUtil;
    private final BillExcelGenerate billExcelGenerate;
    private final ExpenseCalculatorConfiguration config;
    private final ProjectUtil projectUtil;
    private final LocalizationUtil localizationUtil;
    private final PDFServiceUtil pdfServiceUtil;
    private final BillUtils billUtils;


    @Autowired
    public HealthBillReportGenerator(IndividualUtil individualUtil, ExpenseCalculatorUtil expenseCalculatorUtil, BillExcelGenerate billExcelGenerate, ExpenseCalculatorConfiguration config, ProjectUtil projectUtil, LocalizationUtil localizationUtil, PDFServiceUtil pdfServiceUtil, BillUtils billUtils) {
        this.individualUtil = individualUtil;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
        this.billExcelGenerate = billExcelGenerate;
        this.config = config;
        this.projectUtil = projectUtil;
        this.localizationUtil = localizationUtil;
        this.pdfServiceUtil = pdfServiceUtil;
        this.billUtils = billUtils;
    }

    public boolean billExists(BillRequest billRequest) {
        List<Bill> bills = this.expenseCalculatorUtil.fetchBillsWithBillIds(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), Collections.singletonList(billRequest.getBill().getId()));
        if (bills == null || bills.isEmpty()) {
            log.error("No bill found for bill id " + billRequest.getBill().getId());
            return false;
        }
        return true;
    }

    public BillReportRequest generateHealthBillReportRequest(BillRequest billRequest) {
        try {
            updateReportStatus(billRequest, REPORT_STATUS_INITIATED, null, null, null);
            List<ReportBillDetail> reportBillDetail = getReportBillDetail(billRequest.getRequestInfo(), billRequest.getBill());
            BillReportRequest billReportRequest = enrichReportRequest(billRequest, reportBillDetail); //enrichReportRequest

            String excelFileStoreId = billExcelGenerate.generateExcel(billReportRequest.getRequestInfo(), billReportRequest.getReportBill().get(0));
            String pdfFileStoreId = pdfServiceUtil.createPDF(billReportRequest, billRequest.getBill().getTenantId(), config.getPaymentPdfKey());
            log.info("PDF FileStoreId: " + pdfFileStoreId);
            log.info("Excel FileStoreId: " + excelFileStoreId);
            updateReportStatus(billRequest, REPORT_STATUS_COMPLETED, excelFileStoreId, pdfFileStoreId, null);
            return billReportRequest;
        } catch (Exception e) {
            log.error("Error while generating report", e);
            updateReportStatus(billRequest, REPORT_STATUS_FAILED, null, null, e.getMessage());
            return null;
        }

    }

    private BillReportRequest enrichReportRequest(BillRequest billRequest, List<ReportBillDetail> reportBillDetail) {
        String createdBy = billRequest.getRequestInfo() != null &&
                billRequest.getRequestInfo().getUserInfo() != null &&
                billRequest.getRequestInfo().getUserInfo().getName() != null
                ? billRequest.getRequestInfo().getUserInfo().getName()
                : "";

        ReportBill reportBill = ReportBill.builder()
                .totalAmount(billRequest.getBill().getTotalAmount())
                .reportTitle(config.getReportHeaderTitle())
                .createdBy(createdBy)
                .createdTime(System.currentTimeMillis())
                .campaignName(null)
                .numberOfIndividuals(billRequest.getBill().getBillDetails().size())
                .reportBillDetails(reportBillDetail)
                .build();

        enrichCampaignName(reportBill, billRequest);
        enrichLocalization(reportBill, billRequest);
        billRequest.getRequestInfo().setMsgId(getMsgIdWithLocalCode(billRequest.getRequestInfo().getMsgId()));

        return BillReportRequest.builder()
                .requestInfo(billRequest.getRequestInfo())
                .reportBill(Collections.singletonList(reportBill))
                .build();

    }

    private String getMsgIdWithLocalCode(String msgId) {
        String updatedMsgId = null;
        if (msgId.contains("|")) {
            // Split the string by the pipe symbol
            String[] parts = msgId.split("\\|", 2); // Limit to 2 parts
            // Replace the value after the pipe with the new value
            updatedMsgId = parts[0] + "|" + config.getReportLocalizationLocaleCode();
        } else {
            updatedMsgId = msgId + "|" + config.getReportLocalizationLocaleCode();
        }
        return updatedMsgId;
    }

    private List<ReportBillDetail> getReportBillDetail(RequestInfo requestInfo, Bill bill) {

        List<BillDetail> billDetails = bill.getBillDetails();
        int pageSize = 10;
        List<ReportBillDetail> reportBillDetails = new ArrayList<>();
        for (int i = 0; i < billDetails.size(); i += pageSize) {
            List<BillDetail> batch = billDetails.subList(i, Math.min(i + pageSize, billDetails.size()));
            List<ReportBillDetail> reportBillDetail = generateReport(requestInfo, batch);
            reportBillDetails.addAll(reportBillDetail);
        }


        AtomicInteger index = new AtomicInteger(1);
        List<ReportBillDetail> sortedReportBillDetails = reportBillDetails.stream()
                .sorted(Comparator.comparing(ReportBillDetail::getIndividualName))
                .map(reportBillDetail -> {
                    reportBillDetail.setSlNo(index.getAndIncrement());
                    return reportBillDetail;
                })
                .collect(Collectors.toList());

        return sortedReportBillDetails;
    }

    private List<ReportBillDetail> generateReport(RequestInfo requestInfo, List<BillDetail> billDetails) {
        List<String> individualIds = new ArrayList<>();
        List<String> musterRollIds = new ArrayList<>();
        List<ReportBillDetail> reportBillDetails = new ArrayList<>();
        for (BillDetail billDetail : billDetails) {
            individualIds.add(billDetail.getPayee().getIdentifier());
            musterRollIds.add(billDetail.getReferenceId());
        }
        List<Individual> individuals = individualUtil.fetchIndividualDetails(individualIds, requestInfo, billDetails.get(0).getTenantId());
        Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap = new HashMap<>();

        Map<String, Individual> individualMap = new HashMap<>();
        for (Individual individual : individuals) {
            individualMap.put(individual.getId(), individual);
        }

        List<MusterRoll> musterRolls = expenseCalculatorUtil.fetchMusterRollByRegIdsV2(requestInfo, billDetails.get(0).getTenantId(), musterRollIds);
        if (musterRolls != null && !musterRolls.isEmpty()) {
            for (MusterRoll musterRoll : musterRolls) {
                createMusterRollIndividualWorksMap(musterRoll, individualMusterAttendanceMap);
            }
        }

        for (BillDetail billDetail : billDetails) {
            reportBillDetails.add(getReportBillDetail(billDetail, individualMap.get(billDetail.getPayee().getIdentifier()), individualMusterAttendanceMap));
        }
        return reportBillDetails;
    }

    private ReportBillDetail getReportBillDetail(BillDetail billDetail, Individual individual, Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap) {
        ReportBillDetail reportBillDetail = new ReportBillDetail().builder()
                .slNo(0)
                .individualName(null)
                .mobileNumber(null)
                .role(null)
                .locality(null)
                .idNumber(null)
                .totalNumberOfDays((float) 0)
                .foodAmount(BigDecimal.ZERO)
                .transportAmount(BigDecimal.ZERO)
                .wageAmount(BigDecimal.ZERO)
                .totalWages(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .build();

        if (individual != null) {
            reportBillDetail.setIndividualName(individual.getName().getGivenName());
            reportBillDetail.setMobileNumber(individual.getMobileNumber());

            if (individual.getSkills() != null) {
                for (Skill skill : individual.getSkills()) {
                    if (skill != null && skill.getIsDeleted() != null && !skill.getIsDeleted()) {
                        // Found the first non-deleted skill
                        reportBillDetail.setRole(skill.getLevel()); // Set the role based on the skill level
                        break; // Exit the loop once the first non-deleted skill is found
                    }
                }
            }
            // Find the first address that is not deleted
            if (individual.getAddress() != null) {
                for (Address address : individual.getAddress()) {
                    if (address != null && address.getIsDeleted() != null && !address.getIsDeleted() && address.getLocality() != null) {
                        // Found the first non-deleted address
                        reportBillDetail.setLocality(address.getLocality().getCode()); // Set locality code
                        break; // Exit the loop once the first non-deleted address is found
                    }
                }
            }
            if (individual.getIdentifiers() != null) {
                for (Identifier identifier : individual.getIdentifiers()) {
                    if (identifier != null && identifier.getIsDeleted() != null && !identifier.getIsDeleted() && identifier.getIdentifierType().equals(config.getReportBeneficiaryIdentifierType())) {
                        reportBillDetail.setIdNumber(identifier.getIdentifierId());
                        break;
                    }
                }
            }
        }
        BigDecimal totalNumberOfDays = BigDecimal.ZERO;
        if (individualMusterAttendanceMap.containsKey(billDetail.getReferenceId()) && individualMusterAttendanceMap.get(billDetail.getReferenceId()).containsKey(individual.getId())) {
            totalNumberOfDays = individualMusterAttendanceMap.get(billDetail.getReferenceId()).get(individual.getId());
            reportBillDetail.setTotalNumberOfDays(individualMusterAttendanceMap.get(billDetail.getReferenceId()).get(individual.getId()).floatValue());
        }
        for (LineItem lineItem : billDetail.getPayableLineItems()) {
            switch (lineItem.getHeadCode()) {
                case FOOD_HEAD_CODE:
                    reportBillDetail.setFoodAmount(perDayAmount(lineItem.getAmount(), totalNumberOfDays));
                    break;
                case TRANSPORT_HEAD_CODE:
                    reportBillDetail.setTransportAmount(perDayAmount(lineItem.getAmount(), totalNumberOfDays));
                    break;
                case PER_DIEM_HEAD_CODE:
                    reportBillDetail.setWageAmount(perDayAmount(lineItem.getAmount(), totalNumberOfDays));
                    break;
            }
        }

        reportBillDetail.setTotalWages(reportBillDetail.getWageAmount().add(reportBillDetail.getTransportAmount()).add(reportBillDetail.getFoodAmount()));
        reportBillDetail.setTotalAmount(reportBillDetail.getTotalWages().multiply(totalNumberOfDays));
        return reportBillDetail;
    }

    private BigDecimal perDayAmount(BigDecimal amount, BigDecimal totalNumberOfDays) {
        if (totalNumberOfDays.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return amount.divide(totalNumberOfDays, 2, RoundingMode.HALF_UP);
    }

    private void createMusterRollIndividualWorksMap(MusterRoll musterRoll, Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap) {
        if (individualMusterAttendanceMap.containsKey(musterRoll.getMusterRollNumber())) {
            return;
        }
        Map<String, BigDecimal> individualIdWorkDaysMap = new HashMap<>();
        for (IndividualEntry individualEntry : musterRoll.getIndividualEntries()) {
            String individualId = individualEntry.getIndividualId();
            if (individualEntry.getModifiedTotalAttendance() != null) {
                individualIdWorkDaysMap.put(individualId, individualEntry.getModifiedTotalAttendance());
            } else {
                individualIdWorkDaysMap.put(individualId, individualEntry.getActualTotalAttendance());
            }
        }
        individualMusterAttendanceMap.put(musterRoll.getId(), individualIdWorkDaysMap);

    }

    private void enrichCampaignName(ReportBill reportBill, BillRequest billRequest) {
        ProjectResponse projectResponse = projectUtil.getProjectDetails(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), billRequest.getBill().getReferenceId(), billRequest.getBill().getLocalityCode());
        if (projectResponse != null && projectResponse.getProject() != null && projectResponse.getProject().size() > 0) {
            reportBill.setCampaignName(projectResponse.getProject().get(0).getName());
        }
    }

    private void enrichLocalization(ReportBill reportBill, BillRequest billRequest) {
        Map<String, Map<String, String>>  localizationMap = localizationUtil.getLocalisedMessages(
                billRequest.getRequestInfo(), billRequest.getBill().getTenantId(),
                config.getReportLocalizationLocaleCode(),
                config.getReportLocalizationBoundaryModuleName() + "," + config.getReportLocalizationModuleName());
        if (localizationMap != null && localizationMap.containsKey(config.getReportLocalizationLocaleCode() + "|" + billRequest.getBill().getTenantId())) {
            Map<String, String> localization = localizationMap.get(config.getReportLocalizationLocaleCode() + "|" + billRequest.getBill().getTenantId());
            if (localization == null) {
                return;
            }
            for (ReportBillDetail reportBillDetail : reportBill.getReportBillDetails()) {
                reportBillDetail.setLocality(localization.getOrDefault(reportBillDetail.getLocality(), reportBillDetail.getLocality()));
                reportBillDetail.setRole(localization.getOrDefault(reportBillDetail.getRole(), reportBillDetail.getRole()));
            }
        }

    }

    private void updateReportStatus(BillRequest billRequest, String status, String excelReportId, String pdfReportId, String errorMessage) {
        // Ensure additionalDetails is initialized
        Object additionalDetails = billRequest.getBill().getAdditionalDetails();
        if (additionalDetails == null) {
            additionalDetails = new HashMap<String, Object>();
        }
        // Cast additionalDetails to Map
        Map<String, Object> additionalDetailsMap = (Map<String, Object>) additionalDetails;
        // Add reportDetails key with nested values
        Map<String, Object> reportDetails = new HashMap<>();
        reportDetails.put(REPORT_STATUS_KEY, status);
        reportDetails.put(PDF_REPORT_ID_KEY, pdfReportId);
        reportDetails.put(EXCEL_REPORT_ID_KEY, excelReportId);
        reportDetails.put(ERROR_ERROR_MESSAGE_KEY, errorMessage);

        // Add or overwrite reportDetails key
        additionalDetailsMap.put(REPORT_KEY, reportDetails);
        billRequest.getBill().setAdditionalDetails(additionalDetailsMap);
        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();
        billUtils.postUpdateBill(billRequest.getRequestInfo(), billRequest.getBill(), workflow);
    }
}
