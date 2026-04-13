package org.egov.digit.expense.calculator.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.digit.expense.calculator.repository.ServiceRequestRepository;
import org.egov.digit.expense.calculator.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.egov.digit.expense.calculator.util.ExpenseCalculatorServiceConstants.*;

@Component
@Slf4j
public class BillUtils {

    private final ServiceRequestRepository restRepo;

    private final ExpenseCalculatorConfiguration configs;

    private final ObjectMapper mapper;

    @Autowired
    public BillUtils(ServiceRequestRepository restRepo, ExpenseCalculatorConfiguration configs, ObjectMapper mapper) {
        this.restRepo = restRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public BillResponse postCreateBill(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillCreateURI();
        return postBill(requestInfo,bill,workflow,url);
    }

    public BillResponse postUpdateBill(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillUpdateURI();
        return postBill(requestInfo,bill,workflow,url);
    }

    public void postUpdateBillDetailStatus(RequestInfo requestInfo, Bill bill, Workflow workflow) {
        StringBuilder url = getBillDetailStatusUpdateURI();
        postBill(requestInfo, bill, workflow, url);
    }

    public BillResponse searchBills(CalculationRequest calculationRequest, String referenceId) {
        BillCriteria billCriteria = BillCriteria.builder()
                .tenantId(calculationRequest.getCriteria().getTenantId())
                .referenceIds(Stream.of(referenceId).collect(Collectors.toSet()))
                .localityCode(calculationRequest.getCriteria().getLocalityCode())
                .build();

        BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                .requestInfo(calculationRequest.getRequestInfo())
                .billCriteria(billCriteria)
                .pagination(Pagination.builder().build())
                .build();

        Object responseObj = restRepo.fetchResult(getBillSearchURI(), billSearchRequest);
        return mapper.convertValue(responseObj, BillResponse.class);
    }

    private BillResponse postBill(RequestInfo requestInfo, Bill bill, Workflow workflow, StringBuilder url) {
        // Update workflow object because in expense service it's using core service workflow
        Workflow expenseWorkflow1 = Workflow.builder()
                .action(workflow.getAction())
                .assignees(workflow.getAssignees())
                .documents(workflow.getDocuments())
                .comment(workflow.getComment())
                .build();
        BillCalculatorRequestInfoWrapper requestInfoWrapper = BillCalculatorRequestInfoWrapper.builder()
                .requestInfo(requestInfo)
                .bill(bill)
                .workflow(expenseWorkflow1)
                .build();
        log.info("Posting Bill to expense service");
        log.info("requestInfoWrapper",requestInfoWrapper);
        Object responseObj = restRepo.fetchResult(url, requestInfoWrapper);
        if(responseObj!=null)
        	log.info("Received Bill Response");
        return mapper.convertValue(responseObj, BillResponse.class);
    }
    
   
    private StringBuilder getBillCreateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillCreateEndPoint());
        return builder;
    }

    private StringBuilder getBillUpdateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillUpdateEndPoint());
        return builder;
    }

    private StringBuilder getBillDetailStatusUpdateURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getBillDetailStatusUpdateEndPoint());
        return builder;
    }

    private StringBuilder getBillSearchURI() {
        StringBuilder builder = new StringBuilder(configs.getBillHost());
        builder.append(configs.getExpenseBillSearchEndPoint());
        return builder;
    }

    /**
     * Helper method to parse additionalDetails object to Map.
     * Provides consistent parsing across all methods in this class.
     *
     * @param additionalDetails Object to parse (typically from Bill or nested objects)
     * @return Map representation of additionalDetails, or null if input is null
     */
    private Map<String, Object> parseToMap(Object additionalDetails) {
        if (additionalDetails == null) {
            return null;
        }
        return mapper.convertValue(
                additionalDetails,
                mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class)
        );
    }

    /**
     * Comprehensive check to verify if a bill is fully generated and completed for a period.
     * Uses Expense service search API following microservices architecture principles.
     *
     * A bill is considered fully generated when:
     * 1. Bill exists in expense service with status = 'ACTIVE'
     * 2. Bill's businessService = 'EXPENSE.WAGES'
     * 3. Bill's additionalDetails contains the billingPeriodId
     * 4. Bill's additionalDetails.reportDetails.status = 'COMPLETED'
     *
     * @param requestInfo Request info for API call
     * @param projectId Project reference ID
     * @param billingPeriodId Billing period ID
     * @param tenantId Tenant ID
     * @return true if bill is fully generated and completed, false otherwise
     */
    public boolean isCompletedBillGeneratedForPeriod(RequestInfo requestInfo, String projectId,
                                                     String billingPeriodId, String tenantId) {
        try {
            log.info("Checking bill completion for project: {}, period: {}, tenant: {}",
                    projectId, billingPeriodId, tenantId);

            // Build search criteria for Expense service API
            BillCriteria billCriteria = BillCriteria.builder()
                    .tenantId(tenantId)
                    .referenceIds(Stream.of(projectId).collect(Collectors.toSet()))
                    .businessService(EXPENSE_WAGES_BUSINESS_SERVICE)
                    .status(BILL_STATUS_ACTIVE)
                    .build();

            BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                    .requestInfo(requestInfo)
                    .billCriteria(billCriteria)
                    .pagination(Pagination.builder().limit(configs.getBillSearchDefaultLimit()).offSet(0).build())
                    .build();

            // Call Expense service search API
            log.debug("Calling Expense service search API with criteria: {}", billCriteria);
            Object responseObj = restRepo.fetchResult(getBillSearchURI(), billSearchRequest);
            BillResponse billResponse = mapper.convertValue(responseObj, BillResponse.class);

            if (billResponse == null || billResponse.getBills() == null || billResponse.getBills().isEmpty()) {
                log.info("No bills found for project: {}, period: {}", projectId, billingPeriodId);
                return false;
            }

            // Filter bills to find one matching the specific period with completed report
            for (Bill bill : billResponse.getBills()) {
                if (isBillCompletedForPeriod(bill, billingPeriodId)) {
                    log.info("Found completed bill {} for project: {}, period: {}",
                            bill.getBillNumber(), projectId, billingPeriodId);
                    return true;
                }
            }

            log.info("No completed bill found for project: {}, period: {}", projectId, billingPeriodId);
            return false;

        } catch (Exception e) {
            log.error("Error checking bill completion for project: {}, period: {}: {}",
                    projectId, billingPeriodId, e.getMessage(), e);
            // Fail-open: if we can't check, return false to allow retry
            return false;
        }
    }

    /**
     * Check if a specific bill is completed for the given period
     * Validates:
     * 1. Bill's additionalDetails contains billingPeriodId matching the target
     * 2. Bill's reportDetails.status = 'COMPLETED'
     *
     * @param bill Bill to check
     * @param billingPeriodId Target billing period ID
     * @return true if bill is for this period and report is completed
     */
    private boolean isBillCompletedForPeriod(Bill bill, String billingPeriodId) {
        try {
            if (bill.getAdditionalDetails() == null) {
                return false;
            }

            // Parse additionalDetails
            Map<String, Object> additionalDetails = parseToMap(bill.getAdditionalDetails());

            // Check 1: Verify billingPeriodId matches
            Object billPeriodId = additionalDetails.get("billingPeriodId");
            if (billPeriodId == null || !billPeriodId.toString().equals(billingPeriodId)) {
                log.debug("Bill {} does not match period {} (bill period: {})",
                        bill.getBillNumber(), billingPeriodId, billPeriodId);
                return false;
            }

            // Check 2: Verify report is completed
            Object reportDetailsObj = additionalDetails.get("reportDetails");
            if (reportDetailsObj == null) {
                log.debug("Bill {} has no reportDetails", bill.getBillNumber());
                return false;
            }

            Map<String, Object> reportDetails = parseToMap(reportDetailsObj);

            Object reportStatus = reportDetails.get("status");
            if (reportStatus == null || !REPORT_STATUS_COMPLETED.equals(reportStatus.toString())) {
                log.debug("Bill {} report not completed (status: {})",
                        bill.getBillNumber(), reportStatus);
                return false;
            }

            return true;

        } catch (Exception e) {
            log.error("Error checking bill {} for period {}: {}",
                    bill.getId(), billingPeriodId, e.getMessage());
            return false;
        }
    }

    /**
     * Get all FULLY COMPLETED period numbers for a project
     * Used for sequential billing validation.
     *
     * This method uses Expense service API to verify each period's bills are truly completed
     * (bill exists with ACTIVE status and report is COMPLETED).
     *
     * @param requestInfo Request info for API calls
     * @param projectId Project reference ID
     * @param tenantId Tenant ID
     * @return List of period numbers with fully completed bills
     */
    public List<Integer> getCompletedPeriodNumbersForProject(RequestInfo requestInfo, String projectId,
                                                             String tenantId) {
        try {
            log.info("Fetching completed period numbers for project: {}", projectId);

            List<Bill> allBills = new ArrayList<>();

            // Search EXPENSE.WAGES bills (pre-PAYMENTS transition: status=ACTIVE with completed report)
            BillCriteria wageCriteria = BillCriteria.builder()
                    .tenantId(tenantId)
                    .referenceIds(Stream.of(projectId).collect(Collectors.toSet()))
                    .businessService(EXPENSE_WAGES_BUSINESS_SERVICE)
                    .status(BILL_STATUS_ACTIVE)
                    .build();

            BillSearchRequest wageSearchRequest = BillSearchRequest.builder()
                    .requestInfo(requestInfo)
                    .billCriteria(wageCriteria)
                    .pagination(Pagination.builder().limit(configs.getBillSearchMaxLimit()).offSet(0).build())
                    .build();

            Object wageResponseObj = restRepo.fetchResult(getBillSearchURI(), wageSearchRequest);
            BillResponse wageResponse = mapper.convertValue(wageResponseObj, BillResponse.class);
            if (wageResponse != null && wageResponse.getBills() != null) {
                allBills.addAll(wageResponse.getBills());
            }

            // Search PAYMENTS.BILL bills (post-PAYMENTS transition: any status means report was already completed)
            BillCriteria paymentsCriteria = BillCriteria.builder()
                    .tenantId(tenantId)
                    .referenceIds(Stream.of(projectId).collect(Collectors.toSet()))
                    .businessService(PAYMENTS_BILL_BUSINESS_SERVICE)
                    .build();

            BillSearchRequest paymentsSearchRequest = BillSearchRequest.builder()
                    .requestInfo(requestInfo)
                    .billCriteria(paymentsCriteria)
                    .pagination(Pagination.builder().limit(configs.getBillSearchMaxLimit()).offSet(0).build())
                    .build();

            Object paymentsResponseObj = restRepo.fetchResult(getBillSearchURI(), paymentsSearchRequest);
            BillResponse paymentsResponse = mapper.convertValue(paymentsResponseObj, BillResponse.class);
            if (paymentsResponse != null && paymentsResponse.getBills() != null) {
                allBills.addAll(paymentsResponse.getBills());
            }

            if (allBills.isEmpty()) {
                log.info("No bills found for project: {}", projectId);
                return new ArrayList<>();
            }

            // Extract period numbers from bills with completed reports
            List<Integer> completedPeriods = new ArrayList<>();

            for (Bill bill : allBills) {
                Integer periodNumber = extractCompletedPeriodNumber(bill);
                if (periodNumber != null && !completedPeriods.contains(periodNumber)) {
                    completedPeriods.add(periodNumber);
                }
            }

            // Sort period numbers for easier validation
            Collections.sort(completedPeriods);

            log.info("Found {} completed periods for project {}: {}",
                    completedPeriods.size(), projectId, completedPeriods);

            return completedPeriods;

        } catch (Exception e) {
            log.error("Error fetching completed periods for project {}: {}",
                    projectId, e.getMessage(), e);
            // Fail-safe: return empty list
            return new ArrayList<>();
        }
    }

    /**
     * Extract period number from a bill if it's fully completed
     * Returns period number only if report is COMPLETED
     *
     * @param bill Bill to extract from
     * @return Period number if bill is V2 intermediate bill with completed report, null otherwise
     */
    private Integer extractCompletedPeriodNumber(Bill bill) {
        try {
            if (bill.getAdditionalDetails() == null) {
                return null;
            }

            Map<String, Object> additionalDetails = parseToMap(bill.getAdditionalDetails());

            // Check if this is a V2 intermediate bill
            Object billingType = additionalDetails.get("billingType");
            if (billingType == null || !BILLING_TYPE_INTERMEDIATE.equals(billingType.toString())) {
                return null; // Not a V2 intermediate bill
            }

            // Check if report is completed
            Object reportDetailsObj = additionalDetails.get("reportDetails");
            if (reportDetailsObj == null) {
                log.debug("Bill {} has no reportDetails", bill.getBillNumber());
                return null;
            }

            Map<String, Object> reportDetails = parseToMap(reportDetailsObj);

            Object reportStatus = reportDetails.get("status");
            if (reportStatus == null || !REPORT_STATUS_COMPLETED.equals(reportStatus.toString())) {
                log.debug("Bill {} report not completed (status: {})",
                        bill.getBillNumber(), reportStatus);
                return null;
            }

            // Extract period number
            Object periodNumberObj = additionalDetails.get("periodNumber");
            if (periodNumberObj == null) {
                log.warn("V2 bill {} missing periodNumber", bill.getBillNumber());
                return null;
            }

            return Integer.parseInt(periodNumberObj.toString());

        } catch (Exception e) {
            log.error("Error extracting period number from bill {}: {}",
                    bill.getId(), e.getMessage());
            return null;
        }
    }
}
