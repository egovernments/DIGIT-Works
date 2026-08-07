package org.egov.digit.expense.calculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.models.individual.Address;
import org.egov.common.models.individual.Identifier;
import org.egov.common.models.individual.Individual;
import org.egov.common.models.individual.Skill;
import org.egov.common.models.project.Project;
import org.egov.common.models.project.ProjectResponse;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.service.BillingConfigurationService;
import org.egov.digit.expense.calculator.util.*;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.web.models.BillingPeriod;
import org.egov.digit.expense.calculator.web.models.BillingPeriodSearchCriteria;
import org.egov.digit.expense.calculator.web.models.BillingPeriodSearchRequest;
import org.egov.digit.expense.calculator.web.models.BillingPeriodSearchResponse;
import org.egov.digit.expense.calculator.web.models.boundary.BoundaryHierarchyResult;
import org.egov.digit.expense.calculator.web.models.report.BillReportRequest;
import org.egov.digit.expense.calculator.web.models.report.ReportBill;
import org.egov.digit.expense.calculator.web.models.report.ReportBillDetail;
import org.egov.digit.expense.calculator.web.models.report.ReportGenerationRequest;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.expense.calculator.IndividualEntry;
import org.egov.works.services.common.models.musterroll.MusterRoll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.egov.digit.expense.calculator.util.BillReportConstraints.*;
import static org.egov.digit.expense.calculator.util.BillReportConstraints.REPORT_STATUS_COMPLETED;
import static org.egov.digit.expense.calculator.util.BillReportConstraints.REPORT_STATUS_FAILED;
import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;
import org.egov.digit.expense.calculator.util.RateFieldConfigConstants;

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
    private final ObjectMapper objectMapper;
    private final BillingConfigurationService billingConfigurationService;
    private final BoundaryService boundaryService;


    @Autowired
    public HealthBillReportGenerator(IndividualUtil individualUtil, ExpenseCalculatorUtil expenseCalculatorUtil, BillExcelGenerate billExcelGenerate, ExpenseCalculatorConfiguration config, ProjectUtil projectUtil, LocalizationUtil localizationUtil, PDFServiceUtil pdfServiceUtil, BillUtils billUtils, ExpenseCalculatorService expenseCalculatorService, ObjectMapper objectMapper, BillingConfigurationService billingConfigurationService, BoundaryService boundaryService) {
        this.individualUtil = individualUtil;
        this.expenseCalculatorUtil = expenseCalculatorUtil;
        this.billExcelGenerate = billExcelGenerate;
        this.config = config;
        this.projectUtil = projectUtil;
        this.localizationUtil = localizationUtil;
        this.pdfServiceUtil = pdfServiceUtil;
        this.billUtils = billUtils;
        this.expenseCalculatorService = expenseCalculatorService;
        this.objectMapper = objectMapper;
        this.billingConfigurationService = billingConfigurationService;
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

    public Bill generateReportApi(ReportGenerationRequest reportGenerationRequest) {
        List<Bill> bills = expenseCalculatorUtil.fetchBillsWithBillIds(reportGenerationRequest.getRequestInfo(),
                reportGenerationRequest.getTenantId(), Collections.singletonList(reportGenerationRequest.getBillId()));
        // Throwing error if billId not present in expense DB
        if (bills.isEmpty()) {
            throw new CustomException("BILL_NOT_FOUND", "Bill not found in database. If it is generated successfully, " +
                    "wait for bill to be persisted");
        }
        Bill bill = bills.get(0);
        /* If additional details is null, we don't have data of number of bill details, but assuming the api will be hit
        * periodically after bill generation, so allowing to create report.*/
        if (bill.getAdditionalDetails() == null) {
            log.warn("Total number of bill details not present in additional details. " +
                    "Generating report for number of bill details currently present in db");
        } else {
            Map<String, Object> additionalDetailsMap = (Map<String, Object>) bill.getAdditionalDetails();
            Object noOfBillDetailsObject = additionalDetailsMap.get(NO_OF_BILL_DETAILS);
            /* If noOfBillDetailsObject is null, we don't have data of number of bill details, but assuming the api will be hit
             * periodically after bill generation so, allowing to create report.*/
            if (noOfBillDetailsObject == null) {
                log.warn("Total number of bill details not present in additional details. " +
                        "Generating report for number of bill details currently present in db");
            } else if (Integer.parseInt(noOfBillDetailsObject.toString()) > bill.getBillDetails().size()) {
                throw new CustomException("ALL_BILL_DETAILS_NOT_PERSISTED",
                        Integer.parseInt(noOfBillDetailsObject.toString()) - bill.getBillDetails().size() +
                                " billDetails are yet to be persisted. Please wait for sometime and try again");
            }
        }

        BillRequest billRequest = BillRequest.builder()
                .requestInfo(reportGenerationRequest.getRequestInfo())
                .bill(bill)
                .build();

        return generateHealthBillReportRequest(billRequest);
    }

    /**
     * Generates a report for the given bill
     * @param billRequest - contains the bill id and tenant id
     * @return a BillReportRequest object which contains the report data
     */
    public Bill generateHealthBillReportRequest(BillRequest billRequest) {
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
            // Fetch WorkerMdms once — shared by both getReportBillDetail and enrichReportRequest
            WorkerMdms workerMdms = fetchWorkerMdms(billRequest.getRequestInfo(), billRequest.getBill());
            // Fetch the report bill details
            List<ReportBillDetail> reportBillDetail = getReportBillDetail(billRequest.getRequestInfo(), billRequest.getBill(), workerMdms);
            // Enrich the report request
            BillReportRequest billReportRequest = enrichReportRequest(billRequest, reportBillDetail, workerMdms);

            // Validate the report request before sending to PDF service
            log.info("Validating BillReportRequest before PDF generation:");
            log.info("  - ReportBill count: {}", billReportRequest.getReportBill().size());
            if (!billReportRequest.getReportBill().isEmpty()) {
                ReportBill reportBill = billReportRequest.getReportBill().get(0);
                log.info("  - Campaign Name: {}", reportBill.getCampaignName());
                log.info("  - Report Title: {}", reportBill.getReportTitle());
                log.info("  - Total Amount: {}", reportBill.getTotalAmount());
                log.info("  - Number of Individuals: {}", reportBill.getNumberOfIndividuals());
                log.info("  - Bill Details count: {}", reportBill.getReportBillDetails() != null ? reportBill.getReportBillDetails().size() : 0);
            }

            // Generate the excel file
            String excelFileStoreId = billExcelGenerate.generateExcel(billReportRequest.getRequestInfo(), billReportRequest.getReportBill().get(0));
            // Generate the pdf file
            String pdfFileStoreId = pdfServiceUtil.createPDF(billReportRequest, billRequest.getBill().getTenantId(), config.getPaymentPdfKey());
            log.info("PDF FileStoreId: " + pdfFileStoreId);
            log.info("Excel FileStoreId: " + excelFileStoreId);
            // Update the report status to completed
            updateReportStatus(billRequest, REPORT_STATUS_COMPLETED, excelFileStoreId, pdfFileStoreId, null, eventName);
            // Business service migration is only relevant for V1 bills reaching COMPLETED status
            updateBillBusinessService(billRequest);

            return billRequest.getBill();
        } catch (Exception e) {
            log.error("Error while generating report", e);
            updateReportStatus(billRequest, REPORT_STATUS_FAILED, null, null, e.getMessage(),null);
            throw new CustomException("REPORT_GENERATION_FAILED", "Error occurred while generating the report. Please check logs or contact support. Original error: " + e.getMessage());
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
    private BillReportRequest enrichReportRequest(BillRequest billRequest, List<ReportBillDetail> reportBillDetail, WorkerMdms workerMdms) {
        String createdBy = billRequest.getRequestInfo() != null &&
                billRequest.getRequestInfo().getUserInfo() != null &&
                billRequest.getRequestInfo().getUserInfo().getName() != null
                ? billRequest.getRequestInfo().getUserInfo().getName()
                : "";

        BillingPeriodDisplay billingPeriodDisplay = getBillingPeriodDisplay(billRequest);

        // Resolve fieldConfigs — use MDMS fieldConfig if present, else DEFAULT_FIELD_CONFIGS
        List<RateFieldConfig> rawConfigs = workerMdms != null ? workerMdms.getFieldConfig() : null;
        List<RateFieldConfig> fieldConfigs = (rawConfigs != null && !rawConfigs.isEmpty())
                ? rawConfigs.stream()
                        .sorted(Comparator.comparingInt(f -> Optional.ofNullable(f.getOrder()).orElse(99)))
                        .collect(Collectors.toList())
                : RateFieldConfigConstants.DEFAULT_FIELD_CONFIGS;

        ReportBill reportBill = ReportBill.builder()
                .totalAmount(billRequest.getBill().getTotalAmount())
                .reportTitle(billRequest.getBill().getLocalityCode())
                .createdBy(createdBy)
                .createdTime(System.currentTimeMillis())
                .campaignName(null)
                .numberOfIndividuals(billRequest.getBill().getBillDetails().size())
                .reportBillDetails(reportBillDetail)
                .billingPeriodLabel(billingPeriodDisplay.label)
                .billingPeriodDateRange(billingPeriodDisplay.dateRange)
                .billingPeriodStartDate(billingPeriodDisplay.startDate)
                .billingPeriodEndDate(billingPeriodDisplay.endDate)
                .fieldConfigs(fieldConfigs)
                .build();
        // Derive amount breakup from payableLineItems — more reliable than bill-level fields
        // because the expense service may not persist dynamic top-level fields.
        Map<String, String> headCodeMap = workerMdms != null && workerMdms.getHeadCodeMapping() != null
                ? workerMdms.getHeadCodeMapping() : Collections.emptyMap();
        Map<String, String> headCodeToBillAmountKey = fieldConfigs.stream()
                .filter(cfg -> cfg.getBillAmountKey() != null)
                .collect(Collectors.toMap(
                        cfg -> headCodeMap.getOrDefault(cfg.getFieldKey(), cfg.getFieldKey()),
                        RateFieldConfig::getBillAmountKey,
                        (a, b) -> a
                ));
        if (billRequest.getBill().getBillDetails() != null) {
            for (BillDetail billDetail : billRequest.getBill().getBillDetails()) {
                if (billDetail.getPayableLineItems() == null) continue;
                for (LineItem item : billDetail.getPayableLineItems()) {
                    String billAmountKey = headCodeToBillAmountKey.get(item.getHeadCode());
                    if (billAmountKey != null && item.getAmount() != null) {
                        reportBill.getAmountBreakup().merge(billAmountKey, item.getAmount(), BigDecimal::add);
                    }
                }
            }
        }

        Project project = enrichCampaignName(reportBill, billRequest);
        enrichLocalization(reportBill, billRequest, project);
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

    private BillingPeriodDisplay getBillingPeriodDisplay(BillRequest billRequest) {
        Bill bill = billRequest.getBill();

        // First check if this is an aggregate bill
        BillingPeriodDisplay aggregateDisplay = getAggregateBillingPeriodDisplay(bill);
        if (aggregateDisplay != null) {
            log.info("Bill {} is an aggregate bill, using aggregate period display", bill.getId());
            return aggregateDisplay;
        }

        // Regular bill flow - look for billingPeriodId
        String billingPeriodId = extractBillingPeriodId(bill);
        if (!StringUtils.hasLength(billingPeriodId)) {
            log.info("No billingPeriodId found for bill {}, skipping billing period enrichment", bill.getId());
            return BillingPeriodDisplay.empty();
        }

        BillingPeriod billingPeriod = billingConfigurationService.getBillingPeriodById(billingPeriodId, bill.getTenantId());
        if (billingPeriod == null) {
            log.warn("Billing period {} not found for bill {}, leaving period columns empty", billingPeriodId, bill.getId());
            return BillingPeriodDisplay.empty();
        }

        List<BillingPeriod> periodsForConfig = fetchBillingPeriodsForConfig(billingPeriod, billRequest);
        int displayCycleNumber = computeDisplayCycleNumber(billingPeriod, periodsForConfig);
        String label = displayCycleNumber > 0 ? "Period " + displayCycleNumber : null;
        String dateRange = formatPeriodRange(billingPeriod.getPeriodStartDate(), billingPeriod.getPeriodEndDate());

        return new BillingPeriodDisplay(label, dateRange, billingPeriod.getPeriodStartDate(), billingPeriod.getPeriodEndDate());
    }

    /**
     * Get billing period display for aggregate bills.
     * For aggregate bills: Period = "Aggregate", Date Range = first period start to last period end date
     *
     * @param bill The bill to check
     * @return BillingPeriodDisplay for aggregate bills, null if not an aggregate bill
     */
    private BillingPeriodDisplay getAggregateBillingPeriodDisplay(Bill bill) {
        try {
            if (bill.getAdditionalDetails() == null) {
                return null;
            }

            Map<String, Object> additionalDetails = objectMapper.convertValue(
                    bill.getAdditionalDetails(),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)
            );

            // Check if this is an aggregate bill
            Object isAggregateBill = additionalDetails.get("isAggregateBill");
            if (isAggregateBill == null || !Boolean.TRUE.equals(isAggregateBill)) {
                return null;
            }

            log.info("Processing aggregate bill {} for period display", bill.getId());

            // Extract period dates (first period start to last period end)
            Long periodStartDate = extractLongValue(additionalDetails.get("periodStartDate"));
            Long periodEndDate = extractLongValue(additionalDetails.get("periodEndDate"));

            // For aggregate bills: Period = "Aggregate", Date Range = full campaign date range
            String label = "Aggregate";
            String dateRange = formatPeriodRange(periodStartDate, periodEndDate);

            log.info("Aggregate bill period display - Label: {}, DateRange: {}", label, dateRange);

            return new BillingPeriodDisplay(label, dateRange, periodStartDate, periodEndDate);

        } catch (Exception e) {
            log.warn("Error extracting aggregate billing period display for bill {}: {}",
                    bill.getId(), e.getMessage());
            return null;
        }
    }

    /**
     * Extract Long value from various number types (handles Integer, Long, Double from JSON)
     */
    private Long extractLongValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<BillingPeriod> fetchBillingPeriodsForConfig(BillingPeriod billingPeriod, BillRequest billRequest) {
        try {
            BillingPeriodSearchCriteria criteria = BillingPeriodSearchCriteria.builder()
                    .tenantId(billRequest.getBill().getTenantId())
                    .billingConfigId(billingPeriod.getBillingConfigId())
                    .includeDeprecated(true)
                    .limit(200)
                    .offset(0)
                    .build();

            BillingPeriodSearchRequest searchRequest = BillingPeriodSearchRequest.builder()
                    .requestInfo(billRequest.getRequestInfo())
                    .searchCriteria(criteria)
                    .build();

            BillingPeriodSearchResponse response = billingConfigurationService.searchBillingPeriods(searchRequest);
            if (response != null && response.getBillingPeriods() != null) {
                return response.getBillingPeriods();
            }
        } catch (Exception e) {
            log.warn("Failed to fetch billing periods for config {}: {}", billingPeriod.getBillingConfigId(), e.getMessage());
        }
        return Collections.emptyList();
    }

    private int computeDisplayCycleNumber(BillingPeriod billingPeriod, List<BillingPeriod> periodsForConfig) {
        if (CollectionUtils.isEmpty(periodsForConfig)) {
            return billingPeriod.getPeriodNumber() != null ? billingPeriod.getPeriodNumber() : 0;
        }

        List<BillingPeriod> activePeriods = periodsForConfig.stream()
                .filter(period -> period.getIsDeprecated() == null || !period.getIsDeprecated())
                .sorted(Comparator.comparing(BillingPeriod::getPeriodStartDate, Comparator.nullsLast(Long::compareTo)))
                .collect(Collectors.toList());

        int index = findIndex(billingPeriod, activePeriods);
        if (index >= 0) {
            return index + 1;
        }

        List<BillingPeriod> sortedAllPeriods = periodsForConfig.stream()
                .sorted(Comparator.comparing(BillingPeriod::getPeriodStartDate, Comparator.nullsLast(Long::compareTo)))
                .collect(Collectors.toList());

        index = findIndex(billingPeriod, sortedAllPeriods);
        if (index >= 0) {
            return index + 1;
        }

        return billingPeriod.getPeriodNumber() != null ? billingPeriod.getPeriodNumber() : 0;
    }

    private int findIndex(BillingPeriod billingPeriod, List<BillingPeriod> periods) {
        for (int i = 0; i < periods.size(); i++) {
            if (billingPeriod.getId() != null && billingPeriod.getId().equals(periods.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    private String formatPeriodRange(Long startDate, Long endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    .withZone(ZoneId.of(config.getReportDateTimeZone()));
            return formatter.format(Instant.ofEpochMilli(startDate)) + " - " + formatter.format(Instant.ofEpochMilli(endDate));
        } catch (Exception e) {
            log.warn("Failed to format billing period range: {}", e.getMessage());
            return null;
        }
    }

    private String extractBillingPeriodId(Bill bill) {
        try {
            if (bill.getAdditionalDetails() == null) {
                return null;
            }
            Map<String, Object> additionalDetails = objectMapper.convertValue(
                    bill.getAdditionalDetails(),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)
            );

            Object directPeriodId = additionalDetails.get("billingPeriodId");
            if (directPeriodId instanceof String && StringUtils.hasLength((String) directPeriodId)) {
                return (String) directPeriodId;
            }

            Object nestedPeriod = additionalDetails.get("billingPeriod");
            if (nestedPeriod instanceof Map) {
                Object nestedId = ((Map<?, ?>) nestedPeriod).get("billingPeriodId");
                if (nestedId instanceof String && StringUtils.hasLength((String) nestedId)) {
                    return (String) nestedId;
                }
            }
        } catch (Exception e) {
            log.warn("Unable to extract billingPeriodId from bill {}: {}", bill.getId(), e.getMessage());
        }
        return null;
    }

    private static class BillingPeriodDisplay {
        private final String label;
        private final String dateRange;
        private final Long startDate;
        private final Long endDate;

        private BillingPeriodDisplay(String label, String dateRange, Long startDate, Long endDate) {
            this.label = label;
            this.dateRange = dateRange;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        private static BillingPeriodDisplay empty() {
            return new BillingPeriodDisplay(null, null, null, null);
        }
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
    private List<ReportBillDetail> getReportBillDetail(RequestInfo requestInfo, Bill bill, WorkerMdms workerMdms) {
        // Get the bill details
        List<BillDetail> billDetails = bill.getBillDetails();
        Map<String, WorkerRate> skillCodeRateMap = buildSkillCodeRateMap(workerMdms);
        Map<String, String> headCodeToDetailKey = buildHeadCodeToDetailKeyMap(workerMdms);
        // Set the page size
        int pageSize = 10;
        // Create a list to store the report bill details
        List<ReportBillDetail> reportBillDetails = new ArrayList<>();
        // Iterate over the bill details in batches
        for (int i = 0; i < billDetails.size(); i += pageSize) {
            // Get the current batch
            List<BillDetail> batch = billDetails.subList(i, Math.min(i + pageSize, billDetails.size()));
            // Generate the report bill details for the current batch
            List<ReportBillDetail> reportBillDetail = generateReport(requestInfo, batch, skillCodeRateMap, headCodeToDetailKey, workerMdms, bill.getTenantId());
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

    private WorkerMdms fetchWorkerMdms(RequestInfo requestInfo, Bill bill) {
        if (bill.getReferenceId() != null) {
            String parentProjectId = bill.getReferenceId().split("\\.")[0];
            List<WorkerMdms> list = expenseCalculatorService.fetchMDMSDataForWorker(requestInfo, bill.getTenantId(), parentProjectId);
            if (list != null && !list.isEmpty()) return list.get(0);
        }
        return null;
    }

    private Map<String, WorkerRate> buildSkillCodeRateMap(WorkerMdms workerMdms) {
        Map<String, WorkerRate> map = new HashMap<>();
        if (workerMdms != null && workerMdms.getRates() != null) {
            for (WorkerRate workerRate : workerMdms.getRates()) {
                map.put(workerRate.getSkillCode(), workerRate);
            }
        }
        return map;
    }

    /** Builds headCode → reportDetailKey map from fieldConfig + headCodeMapping. */
    private Map<String, String> buildHeadCodeToDetailKeyMap(WorkerMdms workerMdms) {
        Map<String, String> result = new HashMap<>();
        List<RateFieldConfig> configs;
        if (workerMdms == null || workerMdms.getFieldConfig() == null || workerMdms.getFieldConfig().isEmpty()) {
            configs = RateFieldConfigConstants.DEFAULT_FIELD_CONFIGS;
        } else {
            configs = workerMdms.getFieldConfig();
        }
        Map<String, String> headCodeMapping = (workerMdms != null && workerMdms.getHeadCodeMapping() != null)
                ? workerMdms.getHeadCodeMapping() : Collections.emptyMap();
        for (RateFieldConfig config : configs) {
            if (config.getFieldKey() == null || config.getReportDetailKey() == null) continue;
            String headCode = headCodeMapping.getOrDefault(config.getFieldKey(), config.getFieldKey());
            result.put(headCode, config.getReportDetailKey());
        }
        return result;
    }

    /**
     * Generate a list of report bill details
     *
     * @param requestInfo            the request info
     * @param billDetails            the list of bill details
     * @param skillCodeRateMap       the map of skill code to the corresponding worker rate
     * @param tenantId               the tenant id
     * @return a list of report bill details
     */
    private List<ReportBillDetail> generateReport(RequestInfo requestInfo, List<BillDetail> billDetails,
                                                   Map<String, WorkerRate> skillCodeRateMap,
                                                   Map<String, String> headCodeToDetailKey,
                                                   WorkerMdms workerMdms, String tenantId) {
        List<String> individualIds = new ArrayList<>();
        List<String> musterRollIds = new ArrayList<>();
        List<ReportBillDetail> reportBillDetails = new ArrayList<>();
        for (BillDetail billDetail : billDetails) {
            individualIds.add(billDetail.getPayee().getIdentifier());
            musterRollIds.add(billDetail.getReferenceId());
        }
        List<Individual> individuals = individualUtil.fetchIndividualDetails(individualIds, requestInfo, tenantId);
        Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap = new HashMap<>();

        Map<String, Individual> individualMap = new HashMap<>();
        for (Individual individual : individuals) {
            individualMap.put(individual.getId(), individual);
        }

        // BUGFIX: For aggregate bills, the musterRollIds point to a synthetic consolidated muster roll
        // that doesn't exist in the database. We need to extract originalMusterRollIds from the
        // consolidated muster roll's additionalDetails and fetch those instead.
        List<MusterRoll> musterRolls = expenseCalculatorUtil.fetchMusterRollByRegIdsV2(requestInfo, tenantId, musterRollIds);

        // If no muster rolls found, check if this might be an aggregate bill
        if ((musterRolls == null || musterRolls.isEmpty()) && !musterRollIds.isEmpty()) {
            log.warn("No muster rolls found for IDs: {}. This might be an aggregate bill with synthetic muster roll IDs.", musterRollIds);
            log.warn("Attendance data (number of days) will show as 0 in the report.");
            // Continue with empty attendance map - will result in 0 days
        } else if (musterRolls != null && !musterRolls.isEmpty()) {
            for (MusterRoll musterRoll : musterRolls) {
                createMusterRollIndividualWorksMap(musterRoll, individualMusterAttendanceMap);
            }
        }

        for (BillDetail billDetail : billDetails) {
            reportBillDetails.add(getReportBillDetail(billDetail, individualMap.get(billDetail.getPayee().getIdentifier()), individualMusterAttendanceMap, skillCodeRateMap, headCodeToDetailKey, workerMdms));
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
    private ReportBillDetail getReportBillDetail(BillDetail billDetail, Individual individual,
                                                  Map<String, Map<String, BigDecimal>> individualMusterAttendanceMap,
                                                  Map<String, WorkerRate> skillCodeRateMap,
                                                  Map<String, String> headCodeToDetailKey,
                                                  WorkerMdms workerMdms) {
        ReportBillDetail reportBillDetail = new ReportBillDetail().builder()
                .slNo(0)
                .individualName(null)
                .mobileNumber(null)
                .role(null)
                .locality(null)
                .idNumber(null)
                .totalNumberOfDays((float) 0)
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
                        if (skillCodeRateMap != null && skillCodeRateMap.containsKey(skill.getType())) {
                            break; // Exit the loop once the first non-deleted skill having a rate configured is found
                        }
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

        boolean billHasStoredData =
                billDetail.getTotalAttendance() != null
                && billDetail.getTotalAttendance().compareTo(BigDecimal.ZERO) > 0
                && !CollectionUtils.isEmpty(billDetail.getPayableLineItems());

        if (billHasStoredData) {
            // Reverse-calculate per-day rates from stored payableLineItems / totalAttendance
            // so re-generated reports reflect the actual amounts on the bill, not live MDMS rates.
            totalNumberOfDays = billDetail.getTotalAttendance();
            log.debug("Bill exists - using totalAttendance {} days for bill detail {}",
                    totalNumberOfDays, billDetail.getId());

            Map<String, BigDecimal> amountByHeadCode = billDetail.getPayableLineItems().stream()
                    .filter(li -> li.getHeadCode() != null && li.getAmount() != null)
                    .collect(Collectors.toMap(
                            LineItem::getHeadCode,
                            LineItem::getAmount,
                            BigDecimal::add));

            // Build lookup maps from workerMdms to identify PERCENTAGE fields and their components
            Map<String, RateFieldConfig> fieldKeyToConfig = new HashMap<>();
            Map<String, String> headCodeToFieldKey = new HashMap<>();
            if (workerMdms != null && workerMdms.getFieldConfig() != null) {
                Map<String, String> headCodeMapping = workerMdms.getHeadCodeMapping() != null
                        ? workerMdms.getHeadCodeMapping() : Collections.emptyMap();
                for (RateFieldConfig cfg : workerMdms.getFieldConfig()) {
                    if (cfg.getFieldKey() == null) continue;
                    fieldKeyToConfig.put(cfg.getFieldKey(), cfg);
                    String headCode = headCodeMapping.getOrDefault(cfg.getFieldKey(), cfg.getFieldKey());
                    headCodeToFieldKey.put(headCode, cfg.getFieldKey());
                }
            }

            // Build fieldKey → stored total map for component lookups in percentage calculations
            Map<String, BigDecimal> fieldKeyToAmount = new HashMap<>();
            for (Map.Entry<String, BigDecimal> entry : amountByHeadCode.entrySet()) {
                String fieldKey = headCodeToFieldKey.getOrDefault(entry.getKey(), entry.getKey());
                fieldKeyToAmount.put(fieldKey, entry.getValue());
            }

            reportBillDetail.setTotalNumberOfDays(totalNumberOfDays.floatValue());

            // Sum of all stored amounts used to compute actual totalWages per day
            BigDecimal sumOfAllAmounts = amountByHeadCode.values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            for (Map.Entry<String, BigDecimal> entry : amountByHeadCode.entrySet()) {
                String headCode = entry.getKey();
                BigDecimal storedTotal = entry.getValue();
                String detailKey = headCodeToDetailKey.getOrDefault(headCode, headCode + "Amount");
                String fieldKey = headCodeToFieldKey.getOrDefault(headCode, headCode);
                RateFieldConfig cfg = fieldKeyToConfig.get(fieldKey);

                // Always store the actual bill total for use in the total column
                reportBillDetail.getTotalAmountBreakup().put(detailKey, storedTotal);

                if (cfg != null && "PERCENTAGE".equals(cfg.getValueType())
                        && cfg.getComponents() != null && !cfg.getComponents().isEmpty()) {
                    // Reverse-calculate the percentage from stored bill amounts so manual bill
                    // edits are reflected without needing to re-read MDMS rates.
                    BigDecimal componentsTotalAmount = cfg.getComponents().stream()
                            .map(componentFieldKey -> fieldKeyToAmount.getOrDefault(componentFieldKey, BigDecimal.ZERO))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal reversePct = componentsTotalAmount.compareTo(BigDecimal.ZERO) == 0
                            ? BigDecimal.ZERO
                            : storedTotal.divide(componentsTotalAmount, 4, RoundingMode.HALF_UP)
                                         .multiply(BigDecimal.valueOf(100))
                                         .setScale(2, RoundingMode.HALF_UP);
                    reportBillDetail.getPerDayBreakup().put(detailKey, reversePct);
                    log.debug("PERCENTAGE field {} reverse-calculated as {}% (total={}, componentsTotal={})",
                            fieldKey, reversePct, storedTotal, componentsTotalAmount);
                } else {
                    reportBillDetail.getPerDayBreakup().put(detailKey,
                            perDayAmount(storedTotal, totalNumberOfDays));
                }
            }

            // totalWages = sum of all actual per-day dollar amounts (not the percentage display values)
            reportBillDetail.setTotalWages(perDayAmount(sumOfAllAmounts, totalNumberOfDays));
        } else if (individual != null) {
            // New bill: derive attendance from muster roll, per-day rates from MDMS.
            if (individualMusterAttendanceMap.containsKey(billDetail.getReferenceId())
                    && individualMusterAttendanceMap.get(billDetail.getReferenceId()).containsKey(individual.getId())) {
                totalNumberOfDays = individualMusterAttendanceMap.get(billDetail.getReferenceId()).get(individual.getId());
                reportBillDetail.setTotalNumberOfDays(totalNumberOfDays.floatValue());
                log.debug("Found attendance {} days for individual {} from muster roll map",
                        totalNumberOfDays, individual.getId());
            } else if (billDetail.getAdditionalDetails() != null) {
                try {
                    Map<String, Object> additionalDetails = objectMapper.convertValue(
                            billDetail.getAdditionalDetails(), Map.class);
                    Object attendanceObj = additionalDetails.get("attendance");
                    if (attendanceObj instanceof BigDecimal) {
                        totalNumberOfDays = (BigDecimal) attendanceObj;
                    } else if (attendanceObj instanceof Number) {
                        totalNumberOfDays = BigDecimal.valueOf(((Number) attendanceObj).doubleValue());
                    }
                    if (totalNumberOfDays.compareTo(BigDecimal.ZERO) > 0) {
                        reportBillDetail.setTotalNumberOfDays(totalNumberOfDays.floatValue());
                        log.debug("Found attendance {} days for individual {} from bill detail additionalDetails",
                                totalNumberOfDays, individual.getId());
                    } else {
                        log.warn("No attendance data found for individual {} in bill detail {}. Setting to 0.",
                                individual.getId(), billDetail.getId());
                    }
                } catch (Exception e) {
                    log.error("Failed to extract attendance from bill detail additionalDetails: {}", e.getMessage());
                }
            } else {
                log.warn("No attendance data found for individual {} in muster map or bill detail. Setting to 0.",
                        individual.getId());
            }

            String role = reportBillDetail.getRole();
            if (role != null && skillCodeRateMap.containsKey(role) && workerMdms != null
                    && workerMdms.getFieldConfig() != null) {
                Map<String, BigDecimal> rateBreakup = skillCodeRateMap.get(role).getRateBreakup();
                Map<String, String> headCodeMapping = workerMdms.getHeadCodeMapping() != null
                        ? workerMdms.getHeadCodeMapping() : Collections.emptyMap();
                for (RateFieldConfig config : workerMdms.getFieldConfig()) {
                    if (config.getFieldKey() == null || config.getReportDetailKey() == null) continue;
                    BigDecimal rate = rateBreakup.getOrDefault(config.getFieldKey(), BigDecimal.ZERO);
                    reportBillDetail.getPerDayBreakup().put(config.getReportDetailKey(), rate);
                }
            }
        }

        // totalWages for new-bill path: sum raw perDayBreakup values (all flat rates from MDMS).
        // For the existing-bill path, totalWages was already set above from actual stored amounts.
        if (!billHasStoredData) {
            BigDecimal totalWages = reportBillDetail.getPerDayBreakup().values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            reportBillDetail.setTotalWages(totalWages);
        }
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

        // Store attendance map keyed by muster roll ID
        individualMusterAttendanceMap.put(musterRoll.getId(), individualIdWorkDaysMap);

        // BUGFIX: For aggregate muster rolls, also store under original muster roll IDs
        // This allows report generation to lookup attendance for bills referencing aggregate muster rolls
        if (musterRoll.getAdditionalDetails() != null) {
            try {
                Map<String, Object> additionalDetails = objectMapper.convertValue(
                    musterRoll.getAdditionalDetails(), Map.class
                );
                Object originalMusterRollIds = additionalDetails.get("originalMusterRollIds");

                // If this is an aggregate muster roll, store attendance under each original ID too
                if (originalMusterRollIds instanceof List) {
                    List<String> originalIds = (List<String>) originalMusterRollIds;
                    log.info("Detected aggregate muster roll {} with {} original muster rolls",
                        musterRoll.getId(), originalIds.size());

                    // Store the same attendance data under the aggregate muster roll ID
                    // so report lookups using bill detail's referenceId (which points to aggregate ID) will work
                    for (String originalId : originalIds) {
                        // Don't overwrite if already exists
                        if (!individualMusterAttendanceMap.containsKey(originalId)) {
                            individualMusterAttendanceMap.put(originalId, individualIdWorkDaysMap);
                        }
                    }
                    log.info("Stored aggregate attendance data for {} original muster roll IDs", originalIds.size());
                }
            } catch (Exception e) {
                log.warn("Failed to process originalMusterRollIds from additionalDetails: {}", e.getMessage());
            }
        }
    }

    /**
     * Enriches the report bill with campaign name.
     * @param reportBill ReportBill to be enriched
     * @param billRequest BillRequest containing the request info and bill
     */
    private Project enrichCampaignName(ReportBill reportBill, BillRequest billRequest) {
        ProjectResponse projectResponse = projectUtil.getProjectDetails(
                billRequest.getRequestInfo(),
                billRequest.getBill().getTenantId(),
                getReferenceId(billRequest),
                billRequest.getBill().getLocalityCode()
        );
        if (projectResponse != null && projectResponse.getProject() != null && !projectResponse.getProject().isEmpty()) {
            reportBill.setCampaignName(projectResponse.getProject().get(0).getName());
            return projectResponse.getProject().get(0);
        }
        return null;
    }

    // Resolve the campaign's boundary hierarchy from the project; fall back to the static default.
    private String getHierarchyTypeFromProject(Project project) {
        try {
            if (project != null) {
                JsonNode additionalDetails = objectMapper.valueToTree(project.getAdditionalDetails());
                if (additionalDetails != null && additionalDetails.hasNonNull("hierarchyType")) {
                    String hierarchyType = additionalDetails.get("hierarchyType").asText(null);
                    if (hierarchyType != null && !hierarchyType.trim().isEmpty()) {
                        return hierarchyType.trim();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to fetch hierarchyType from project additionalDetails: {}", e.getMessage());
        }
        return config.getBoundaryHierarchyName();
    }

    /**
     * Enriches the report bill with localized labels.
     *
     * @param reportBill the report bill to be enriched
     * @param billRequest the bill request containing the request info and bill
     */
    private void enrichLocalization(ReportBill reportBill, BillRequest billRequest, Project project) {
        String hierarchyType = getHierarchyTypeFromProject(project);
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
                BoundaryHierarchyResult boundaryHierarchyResult = boundaryService.getBoundaryHierarchyWithLocalityCode(reportBillDetail.getLocality(),billRequest.getRequestInfo().getUserInfo().getTenantId(), hierarchyType);
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
     * IMPORTANT: This method preserves existing V2 metadata (billingPeriodId, periodNumber, etc.)
     * while adding/updating reportDetails.
     *
     * @param billRequest    the bill request containing the bill to be updated
     * @param status         the status of the report
     * @param excelReportId  the id of the excel report
     * @param pdfReportId    the id of the pdf report
     * @param errorMessage   the error message if the report generation failed
     */
    private void updateReportStatus(BillRequest billRequest, String status, String excelReportId, String pdfReportId, String errorMessage, String eventName) {
        Bill bill = billRequest.getBill();

        Map<String, Object> reportDetails = new HashMap<>();
        reportDetails.put(REPORT_STATUS_KEY, status);
        reportDetails.put(PDF_REPORT_ID_KEY, pdfReportId);
        reportDetails.put(EXCEL_REPORT_ID_KEY, excelReportId);
        reportDetails.put(ERROR_ERROR_MESSAGE_KEY, errorMessage);
        reportDetails.put(EVENT_NAME, eventName);

        // Always update in-memory bill state so downstream reads within this flow work correctly
        // (e.g. the post-COMPLETED check in generateHealthBillReportRequest uses in-memory additionalDetails)
        Map<String, Object> inMemoryDetails;
        try {
            inMemoryDetails = bill.getAdditionalDetails() != null
                    ? objectMapper.convertValue(bill.getAdditionalDetails(),
                        objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class))
                    : new HashMap<>();
        } catch (Exception e) {
            inMemoryDetails = new HashMap<>();
        }
        inMemoryDetails.put(REPORT_KEY, reportDetails);
        bill.setAdditionalDetails(inMemoryDetails);

        log.info("Updating bill {} with report status: {}", bill.getId(), status);

        // Persist via dedicated metadata endpoint — no workflow transition, no status change,
        // no report-regen re-trigger. Safe for all bill states including terminal ones.
        // Wrapped in try-catch so a transient expense service error does not abort file generation.
        try {
            billUtils.postUpdateReportMeta(
                    billRequest.getRequestInfo(),
                    bill.getId(),
                    bill.getTenantId(),
                    reportDetails);
            log.info("Successfully persisted report status {} for bill {}", status, bill.getId());
        } catch (Exception e) {
            log.error("Failed to persist report status {} for bill {} — in-memory state updated but DB may be stale. Error: {}",
                    status, bill.getId(), e.getMessage());
        }
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
