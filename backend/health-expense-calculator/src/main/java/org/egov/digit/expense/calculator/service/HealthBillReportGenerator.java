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
import org.egov.digit.expense.calculator.web.models.boundary.BoundaryHierarchyResult;
import org.egov.digit.expense.calculator.web.models.report.BillReportRequest;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.expense.calculator.IndividualEntry;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.BillReportConstraints.*;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

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
    private final ExpenseCalculatorService expenseCalculatorService;
    private final BoundaryService boundaryService;


    @Autowired
    public HealthBillReportGenerator(IndividualUtil individualUtil, ExpenseCalculatorUtil expenseCalculatorUtil, BillExcelGenerate billExcelGenerate, ExpenseCalculatorConfiguration config, ProjectUtil projectUtil, LocalizationUtil localizationUtil, PDFServiceUtil pdfServiceUtil, BillUtils billUtils, ExpenseCalculatorService expenseCalculatorService, BoundaryService boundaryService) {
        this.individualUtil = individualUtil;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
        this.billExcelGenerate = billExcelGenerate;
        this.config = config;
        this.projectUtil = projectUtil;
        this.localizationUtil = localizationUtil;
        this.pdfServiceUtil = pdfServiceUtil;
        this.billUtils = billUtils;
        this.expenseCalculatorService = expenseCalculatorService;
        this.boundaryService = boundaryService;
    }

    /**
     * This method checks if the bill exists in the database
     * @param billRequest - contains the bill id and tenant id
     * @return true if the bill exists, false otherwise
     */
    public boolean billExists(BillRequest billRequest) {
        // Fetch the bill with the given bill id and tenant id
        List<Bill> bills = this.expenseCalculatorUtil.fetchBillsWithBillIds(billRequest.getRequestInfo(), billRequest.getBill().getTenantId(), Collections.singletonList(billRequest.getBill().getId()));
        if (bills == null || bills.isEmpty()) {
            // If the bill is not found, log an error and return false
            log.error("No bill found for bill id " + billRequest.getBill().getId());
            return false;
        }
        // If the bill is found, return true
        return true;
    }

    /**
     * Generates a report for the given bill
     * @param billRequest - contains the bill id and tenant id
     * @return a BillReportRequest object which contains the report data
     */
    public BillReportRequest generateHealthBillReportRequest(BillRequest billRequest) {
        try {
            log.info("Generating report for bill id: {}", billRequest.getBill().getId());

            ProjectResponse projectResponse = projectUtil.getProjectDetails(
                    billRequest.getRequestInfo(),
                    billRequest.getRequestInfo().getUserInfo().getTenantId(),
                    getReferenceId(billRequest),
                    billRequest.getBill().getLocalityCode()
            );

            if (projectResponse == null || projectResponse.getProject() == null) {
                log.error("Project Response null");
                throw new CustomException("PROJECT_RESPONSE_NULL", "Project response null");
            }
            if (projectResponse.getProject().isEmpty()) {
                log.error("Project not found");
                throw new CustomException("PROJECT_NOT_FOUND", "Project not found");
            }

            String eventName = null;
            if (projectResponse.getProject().get(0).getName() == null) {
                eventName = null;
            } else {
                eventName = projectResponse.getProject().get(0).getName();
            }
            // Update the report status to initiated
            updateReportStatus(billRequest, REPORT_STATUS_INITIATED, null, null, null, eventName);
            // Fetch the report bill details
            List<ReportBillDetail> reportBillDetail = getReportBillDetail(billRequest.getRequestInfo(), billRequest.getBill());
            // Enrich the report request
            BillReportRequest billReportRequest = enrichReportRequest(billRequest, reportBillDetail); //enrichReportRequest

            // Generate the excel file
            String excelFileStoreId = billExcelGenerate.generateExcel(billReportRequest.getRequestInfo(), billReportRequest.getReportBill().get(0));
            // Generate the pdf file
            String pdfFileStoreId = pdfServiceUtil.createPDF(billReportRequest, billRequest.getBill().getTenantId(), config.getPaymentPdfKey());
            log.info("PDF FileStoreId: " + pdfFileStoreId);
            log.info("Excel FileStoreId: " + excelFileStoreId);
            // Update the report status to completed
            updateReportStatus(billRequest, REPORT_STATUS_COMPLETED, excelFileStoreId, pdfFileStoreId, null, eventName);
            Map<String, Object> additionalDetails = (Map<String, Object>) billRequest.getBill().getAdditionalDetails();
            Map<String, Object> reportDetails = (Map<String, Object>) additionalDetails.get("reportDetails");
            if (reportDetails.get("status") == REPORT_STATUS_COMPLETED) {
                updateBillBusinessService(billRequest);
            }

            return billReportRequest;
        } catch (Exception e) {
            log.error("Error while generating report", e);
            updateReportStatus(billRequest, REPORT_STATUS_FAILED, null, null, e.getMessage(),null);
            return null;
        }

    }

    private void updateBillBusinessService( BillRequest billRequest){
        Bill bill = billRequest.getBill();
        if(!bill.getBusinessService().equalsIgnoreCase(PAYMENTS_BILL_BUSINESS_SERVICE)) {
            Workflow expenseWorkflow1 = Workflow.builder()
                    .action(WF_CREATE_ACTION_CONSTANT)
                    .build();
            log.info("updating business service for bill");
            billUtils.postUpdateBillDetailStatus(billRequest.getRequestInfo(), bill, expenseWorkflow1);
        }
    }

    /**
     * Enriches the report bill with campaign name and localization.
     *
     * @param billRequest  the bill request
     * @param reportBillDetail the report bill details
     * @return the enriched report bill
     */
    private BillReportRequest enrichReportRequest(BillRequest billRequest, List<ReportBillDetail> reportBillDetail) {
        String createdBy = billRequest.getRequestInfo() != null &&
                billRequest.getRequestInfo().getUserInfo() != null &&
                billRequest.getRequestInfo().getUserInfo().getName() != null
                ? billRequest.getRequestInfo().getUserInfo().getName()
                : "";

        ReportBill reportBill = ReportBill.builder()
                .totalAmount(billRequest.getBill().getTotalAmount())
                .totalFoodAmount(billRequest.getBill().getTotalFoodAmount())
                .totalTransportAmount(billRequest.getBill().getTotalTransportAmount())
                .totalWageAmount(billRequest.getBill().getTotalWageAmount())
                .reportTitle(billRequest.getBill().getLocalityCode())
                .createdBy(createdBy)
                .createdTime(System.currentTimeMillis())
                .campaignName(null)
                .numberOfIndividuals(billRequest.getBill().getBillDetails().size())
                .reportBillDetails(reportBillDetail)
                .build();

        enrichCampaignName(reportBill, billRequest);
//        enrichLocalization(reportBill, billRequest);
        billRequest.getRequestInfo().setMsgId(getMsgIdWithLocalCode(billRequest.getRequestInfo().getMsgId()));

        return BillReportRequest.builder()
                .requestInfo(billRequest.getRequestInfo())
                .reportBill(Collections.singletonList(reportBill))
                .build();

    }

    /**
     * Appends the localization code to the message id if it does not already contain it.
     *
     * @param msgId the message id
     * @return the message id with the localization code appended
     */
    private String getMsgIdWithLocalCode(String msgId) {
        if (StringUtils.hasLength(msgId) && msgId.contains("|")) {
            // Split the string by the pipe symbol
            String[] parts = msgId.split("\\|", 2); // Limit to 2 parts
            // Replace the value after the pipe with the new value
            if (parts.length > 1 &&  StringUtils.hasLength(parts[1])) {
                return msgId;
            }
        }
        return msgId + "|" + config.getReportLocalizationLocaleCode();
    }

    /**
     * This method takes a bill and its details and generates a list of report bill detail objects.
     * It does this by splitting the bill details into batches and calling the generate report method
     * on each batch. The report bill details from each batch are then added to the result list.
     * The result list is then sorted alphabetically by individual name and the slNo is set.
     *
     * @param requestInfo the request info
     * @param bill        the bill
     * @return a list of report bill detail objects
     */
    private List<ReportBillDetail> getReportBillDetail(RequestInfo requestInfo, Bill bill) {
        // Get the bill details
        List<BillDetail> billDetails = bill.getBillDetails();
        // Get the skill code to worker rate map
        Map<String, WorkerRate> skillCodeRateMap = getSkillCodeRateMap(requestInfo, bill);
        // Set the page size
        int pageSize = 10;
        // Create a list to store the report bill details
        List<ReportBillDetail> reportBillDetails = new ArrayList<>();
        // Iterate over the bill details in batches
        for (int i = 0; i < billDetails.size(); i += pageSize) {
            // Get the current batch
            List<BillDetail> batch = billDetails.subList(i, Math.min(i + pageSize, billDetails.size()));
            // Generate the report bill details for the current batch
            List<ReportBillDetail> reportBillDetail = generateReport(requestInfo, batch, skillCodeRateMap);
            // Add the report bill details to the result list
            reportBillDetails.addAll(reportBillDetail);
        }

        // Create an atomic integer to keep track of the slNo
        AtomicInteger index = new AtomicInteger(1);

        // Sort the report bill details by individual name and set the slNo
        List<ReportBillDetail> sortedReportBillDetails = reportBillDetails.stream()
                .sorted(Comparator.comparing(ReportBillDetail::getIndividualName))
                .map(reportBillDetail -> {
                    // Set the slNo
                    reportBillDetail.setSlNo(index.getAndIncrement());
                    return reportBillDetail;
                })
                .collect(Collectors.toList());

        // Return the sorted list of report bill details
        return sortedReportBillDetails;
    }

    /**
     * Method to fetch the skill code to worker rate map
     *
     * @param requestInfo the request info
     * @param bill        the bill
     * @return a map of skill code to the corresponding worker rate
     */
    private Map<String, WorkerRate> getSkillCodeRateMap(RequestInfo requestInfo, Bill bill) {
        WorkerMdms workerRateMdms = null;
        Map<String, WorkerRate> skillCodeRateMap = new HashMap<>();
        if (bill.getReferenceId() != null) {
            String parentProjectId = bill.getReferenceId().split("\\.")[0];
            workerRateMdms = expenseCalculatorService.fetchMDMSDataForWorker(requestInfo, bill.getTenantId(), parentProjectId).get(0);
        }
        if (workerRateMdms != null) {
            for (WorkerRate workerRate : workerRateMdms.getRates()) {
                skillCodeRateMap.put(workerRate.getSkillCode(), workerRate);
            }
        }
        return skillCodeRateMap;
    }

    /**
     * Generate a list of report bill details
     *
     * @param requestInfo            the request info
     * @param billDetails            the list of bill details
     * @param skillCodeRateMap       the map of skill code to the corresponding worker rate
     * @return a list of report bill details
     */
    private List<ReportBillDetail> generateReport(RequestInfo requestInfo, List<BillDetail> billDetails, Map<String, WorkerRate> skillCodeRateMap) {
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
            reportBillDetails.add(getReportBillDetail(billDetail, individualMap.get(billDetail.getPayee().getIdentifier()), individualMusterAttendanceMap, skillCodeRateMap));
        }
        return reportBillDetails;
    }
    /**
     * Method to generate a single row of the report
     *
     * @param billDetail        the bill detail of the report
     * @param individual        the individual related to the bill detail
     * @param individualMusterAttendanceMap the map of muster roll number and individual id to total days of attendance
     * @param skillCodeRateMap  the map of skill code to the corresponding worker rate
     * @return a ReportBillDetail object
     */
    private ReportBillDetail getReportBillDetail(BillDetail billDetail, Individual individual, Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap, Map<String, WorkerRate> skillCodeRateMap) {
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
            reportBillDetail.setIdNumber(individual.getName().getFamilyName());

            if (individual.getSkills() != null) {
                for (Skill skill : individual.getSkills()) {
                    if (skill != null && skill.getIsDeleted() != null && !skill.getIsDeleted()) {
                        // Found the first non-deleted skill
                        reportBillDetail.setRole(skill.getType()); // Set the role based on the skill level
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
        }
        BigDecimal totalNumberOfDays = BigDecimal.ZERO;
        if (individualMusterAttendanceMap.containsKey(billDetail.getReferenceId()) && individualMusterAttendanceMap.get(billDetail.getReferenceId()).containsKey(individual.getId())) {
            totalNumberOfDays = individualMusterAttendanceMap.get(billDetail.getReferenceId()).get(individual.getId());
            reportBillDetail.setTotalNumberOfDays(individualMusterAttendanceMap.get(billDetail.getReferenceId()).get(individual.getId()).floatValue());
        }

        String role = reportBillDetail.getRole();
        if (role != null && skillCodeRateMap.containsKey(role)) {
            reportBillDetail.setFoodAmount(skillCodeRateMap.get(role).getRateBreakup().getOrDefault(FOOD_HEAD_CODE, BigDecimal.ZERO));
            reportBillDetail.setTransportAmount(skillCodeRateMap.get(role).getRateBreakup().getOrDefault(TRANSPORT_HEAD_CODE, BigDecimal.ZERO));
            reportBillDetail.setWageAmount(skillCodeRateMap.get(role).getRateBreakup().getOrDefault(PER_DIEM_HEAD_CODE, BigDecimal.ZERO));
        }
        reportBillDetail.setTotalWages(reportBillDetail.getWageAmount().add(reportBillDetail.getTransportAmount()).add(reportBillDetail.getFoodAmount()));
        reportBillDetail.setTotalAmount(billDetail.getTotalAmount());
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

    /**
     * Enriches the report bill with campaign name.
     * @param reportBill ReportBill to be enriched
     * @param billRequest BillRequest containing the request info and bill
     */
    private void enrichCampaignName(ReportBill reportBill, BillRequest billRequest) {
        ProjectResponse projectResponse = projectUtil.getProjectDetails(
                billRequest.getRequestInfo(),
                billRequest.getBill().getTenantId(),
                getReferenceId(billRequest),
                billRequest.getBill().getLocalityCode()
        );
        if (projectResponse != null && projectResponse.getProject() != null && !projectResponse.getProject().isEmpty()) {
            reportBill.setCampaignName(projectResponse.getProject().get(0).getName());
        }
    }

    /**
     * Enriches the report bill with localized labels.
     *
     * @param reportBill the report bill to be enriched
     * @param billRequest the bill request containing the request info and bill
     */
    private void enrichLocalization(ReportBill reportBill, BillRequest billRequest) {
        String localCode = localizationUtil.getLocalCode(billRequest.getRequestInfo());
        Map<String, Map<String, String>> localizationMap = localizationUtil.getLocalisedMessages(
                billRequest.getRequestInfo(), billRequest.getBill().getTenantId(),
                localCode,
                config.getReportLocalizationBoundaryModuleName() + "," + config.getReportLocalizationModuleName());
        if (localizationMap != null && localizationMap.containsKey(localCode + "|" + billRequest.getBill().getTenantId())) {
            Map<String, String> localization = localizationMap.get(localCode + "|" + billRequest.getBill().getTenantId());
            if (localization == null) {
                return;
            }
            reportBill.setCampaignName(localization.getOrDefault(reportBill.getCampaignName(), reportBill.getCampaignName()));
            String campaignName = reportBill.getCampaignName();
            String boundaryCode = reportBill.getReportTitle();
            String localizedBoundary = localization.getOrDefault(boundaryCode, boundaryCode);
            String startingConstant = localization.getOrDefault(REPORT_FIRST_CONSTANT,REPORT_FIRST_CONSTANT);
            String middleConstant = localization.getOrDefault(REPORT_MIDDLE_CONSTANT, REPORT_MIDDLE_CONSTANT);
            String newReportTitle = startingConstant + " " + campaignName + " " + middleConstant + " " + localizedBoundary;
            reportBill.setReportTitle(newReportTitle);
            for (ReportBillDetail reportBillDetail : reportBill.getReportBillDetails()) {
                BoundaryHierarchyResult boundaryHierarchyResult = boundaryService.getBoundaryHierarchyWithLocalityCode(reportBillDetail.getLocality(),billRequest.getRequestInfo().getUserInfo().getTenantId());
                if(boundaryHierarchyResult != null) {
                    Map<String, String> boundaryMap = boundaryHierarchyResult.getBoundaryHierarchy();
                    // Check if "COUNTRY" is the only entry
                    if (boundaryMap.size() == 1 && boundaryMap.containsKey("COUNTRY")) {
                        reportBillDetail.setLocality(boundaryMap.get("COUNTRY")); // Set COUNTRY if it's the only value
                    } else {
                        // Remove COUNTRY if other values are present
                        String filteredLocality = boundaryMap.entrySet().stream()
                                .filter(entry -> !"COUNTRY".equalsIgnoreCase(entry.getKey()))  // Exclude COUNTRY if others exist
                                .map(Map.Entry::getValue)  // Get only values
                                .collect(Collectors.joining(" / ")); // Join as "/" separated string

                        reportBillDetail.setLocality(filteredLocality);
                    }
                }
                else{
                    log.warn("No boundary hierarchy found for locality code {} setting NA as fallback", reportBillDetail.getLocality());
                    reportBillDetail.setLocality("NA");
                }
                reportBillDetail.setRole(localization.getOrDefault(reportBillDetail.getRole(), reportBillDetail.getRole()));
            }
        }
    }

    /**
     * Updates the report status of the given bill request.
     *
     * @param billRequest    the bill request containing the bill to be updated
     * @param status         the status of the report
     * @param excelReportId  the id of the excel report
     * @param pdfReportId    the id of the pdf report
     * @param errorMessage   the error message if the report generation failed
     */
    private void updateReportStatus(BillRequest billRequest, String status, String excelReportId, String pdfReportId, String errorMessage, String eventName) {
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
        reportDetails.put(EVENT_NAME,eventName);

        // Add or overwrite reportDetails key
        additionalDetailsMap.put(REPORT_KEY, reportDetails);
        billRequest.getBill().setAdditionalDetails(additionalDetailsMap);
        Workflow workflow = Workflow.builder()
                .action(WF_SUBMIT_ACTION_CONSTANT)
                .build();
        billUtils.postUpdateBill(billRequest.getRequestInfo(), billRequest.getBill(), workflow);
    }

    // The reference id is concatenated as parent.child.child
    private String getReferenceId(BillRequest billRequest) {
        String referenceId = billRequest.getBill().getReferenceId();
        if (referenceId == null || referenceId.isEmpty()) {
            return null;
        }
        String[] referenceIds = referenceId.split("\\.");
        return referenceIds[referenceIds.length - 1];
    }

}
