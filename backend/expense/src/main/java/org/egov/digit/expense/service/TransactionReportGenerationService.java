package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.util.FileStoreUtil;
import org.egov.digit.expense.util.PDFServiceUtil;
import org.egov.digit.expense.util.TransactionReportExcelGenerator;
import org.egov.digit.expense.util.TransactionReportPdfUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionReportGenerationService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final BillService billService;
    private final TransactionReportExcelGenerator transactionReportExcelGenerator;
    private final TransactionReportPdfUtil transactionReportPdfUtil;
    private final FileStoreUtil fileStoreUtil;
    private final PDFServiceUtil pdfServiceUtil;
    private final Configuration config;
    private final TaskRepository taskRepository;

    public TransactionReportGenerationService(BillService billService, TransactionReportExcelGenerator transactionReportExcelGenerator, TransactionReportPdfUtil transactionReportPdfUtil, FileStoreUtil fileStoreUtil, PDFServiceUtil pdfServiceUtil, Configuration config, TaskRepository taskRepository) {
        this.billService = billService;
        this.transactionReportExcelGenerator = transactionReportExcelGenerator;
        this.transactionReportPdfUtil = transactionReportPdfUtil;
        this.fileStoreUtil = fileStoreUtil;
        this.pdfServiceUtil = pdfServiceUtil;
        this.config = config;
        this.taskRepository = taskRepository;
    }

    public String createReportAndUploadToFileStore(BillTransactionReportRequest request)
            throws Exception {
        String tenantId = request.getBillTransactionReport().getTenantId();
        BillTransactionReport billTransactionReport = request.getBillTransactionReport();
        List<TransactionReportRow> rows =
                buildTransactionRows(request.getRequestInfo(),
                        tenantId,
                        Collections.singleton(billTransactionReport.getBillId()));
//        List<TransactionReportRow> rows = List.of(
//                TransactionReportRow.builder()
//                        .date(System.currentTimeMillis())
//                        .billNumber("BILL-001")
//                        .mtnTransactionId("MTN-TXN-123456")
//                        .description("Wage payment for worker A")
//                        .debitAmount(new BigDecimal("1500.00"))
//                        .build(),
//
//                TransactionReportRow.builder()
//                        .date(System.currentTimeMillis() - 86400000L) // yesterday
//                        .billNumber("BILL-002")
//                        .mtnTransactionId("MTN-TXN-789012")
//                        .description("Wage payment for worker B")
//                        .debitAmount(new BigDecimal("1800.50"))
//                        .build(),
//
//                TransactionReportRow.builder()
//                        .date(System.currentTimeMillis() - 2 * 86400000L)
//                        .billNumber("BILL-003")
//                        .mtnTransactionId("MTN-TXN-345678")
//                        .description("Transport allowance")
//                        .debitAmount(new BigDecimal("500.00"))
//                        .build()
//        );
//        for (int i = 0; i < rows.size(); i++) {
//            log.info("Row {} => {}", i + 1, rows.get(i));
//        }

        if (billTransactionReport.getType().equals(ReportType.PDF)) {
            TransactionReportPdfRequest pdfRequest = transactionReportPdfUtil.buildPdfRequest(request.getRequestInfo(), tenantId, rows);
            return pdfServiceUtil.createPDF(pdfRequest, tenantId, config.getPaymentPdfKey());
        } else {
            ByteArrayResource excel = transactionReportExcelGenerator.generateExcel(rows);
            return fileStoreUtil.uploadFileAndGetFileStoreId(tenantId, excel);
        }
    }

    public List<TransactionReportRow> buildTransactionRows(
            RequestInfo requestInfo,
            String tenantId,
            Set<String> billIds
    ) {

        /* ----------------------------------
         * 1. Fetch Bills using existing search
         * ---------------------------------- */
        BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                .requestInfo(requestInfo)
                .billCriteria(
                        BillCriteria.builder()
                                .tenantId(tenantId)
                                .ids(billIds)
                                .build()
                )
                .pagination(new Pagination())
                .build();

        BillResponse billResponse =
                billService.search(billSearchRequest, false);

        List<Bill> bills = billResponse.getBills();

        if (bills == null || bills.isEmpty()) {
            return List.of();
        }

        /* ----------------------------------
         * 2. Extract PAID BillDetails
         * ---------------------------------- */
        List<BillDetail> paidBillDetails =
                bills.stream()
                        .flatMap(bill -> bill.getBillDetails().stream())
                        .filter(bd -> Status.PAID.equals(bd.getStatus()))
                        .toList();

        if (paidBillDetails.isEmpty()) {
            return List.of();
        }

        List<String> billDetailIds =
                paidBillDetails.stream()
                        .map(BillDetail::getId)
                        .toList();

        /* ----------------------------------
         * 3. Fetch TaskDetails for BillDetails
         * ---------------------------------- */
        List<TaskDetails> taskDetailsList =
                taskRepository.searchByBillDetailIds(
                        tenantId, billDetailIds
                );

        if (taskDetailsList == null || taskDetailsList.isEmpty()) {
            return List.of();
        }

        /* ----------------------------------
         * 4. Index TaskDetails by BillDetailId
         * ---------------------------------- */
        Map<String, List<TaskDetails>> taskDetailsByBillDetailId =
                taskDetailsList.stream()
                        .collect(Collectors.groupingBy(TaskDetails::getBillDetailsId));

        /* ----------------------------------
         * 5. Build report Rows
         * ---------------------------------- */
        List<TransactionReportRow> rows = new ArrayList<>();

        for (Bill bill : bills) {

            for (BillDetail billDetail : bill.getBillDetails()) {

                if (!Status.PAID.equals(billDetail.getStatus())) {
                    continue;
                }

                List<TaskDetails> relatedTasks =
                        taskDetailsByBillDetailId.getOrDefault(
                                billDetail.getId(), List.of());

                TaskDetails paymentTaskDetails =
                        getSuccessfulPaymentTaskDetails(relatedTasks);

                if (paymentTaskDetails == null) {
                    continue; // No successful MTN payment
                }

                PaymentTransferResponse paymentResponse =
                        getPaymentTransferResponse(paymentTaskDetails);

                if (paymentResponse == null) {
                    continue;
                }

                rows.add(
                        TransactionReportRow.builder()
                                .date(paymentTaskDetails.getAuditDetails().getCreatedTime())
                                .billNumber(bill.getBillNumber()) // HCM Identifier
                                .mtnTransactionId(paymentResponse.getExternalId())
                                .description(paymentResponse.getPayerMessage())
                                .debitAmount(paymentResponse.getAmount())
                                .build()
                );
            }
        }


        return rows;
    }

//    public static Map<String, Object> toMap(Object additionalDetails) {
//        if (additionalDetails == null) {
//            return Map.of();
//        }
//        return mapper.convertValue(additionalDetails, Map.class);
//    }

    private PaymentTransferResponse getPaymentTransferResponse(TaskDetails taskDetails) {
        if (taskDetails.getAdditionalDetails() == null) {
            return null;
        }

        try {
            return mapper.convertValue(
                    taskDetails.getAdditionalDetails(),
                    PaymentTransferResponse.class
            );
        } catch (IllegalArgumentException e) {
            // additionalDetails is NOT a payment response
            return null;
        }
    }

    private TaskDetails getSuccessfulPaymentTaskDetails(List<TaskDetails> taskDetailsList) {

        for (TaskDetails taskDetails : taskDetailsList) {

            PaymentTransferResponse response =
                    getPaymentTransferResponse(taskDetails);

            if (response == null) {
                continue;
            }

            if (ResponseStatus.SUCCESSFUL
                    .toString()
                    .equalsIgnoreCase(response.getStatus())) {
                return taskDetails;
            }
//
//            if (ResponseStatus.FAILED
//                    .toString()
//                    .equalsIgnoreCase(response.getStatus())) {
//                failedTask = taskDetails;
//            }
        }

        return null;
    }

}
