package org.egov.digit.expense.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.repository.TaskRepository;
import org.egov.digit.expense.util.FileStoreUtil;
import org.egov.digit.expense.util.PDFServiceUtil;
import org.egov.digit.expense.util.TransactionReportExcelGenerator;
import org.egov.digit.expense.util.TransactionReportUtil;
import org.egov.digit.expense.web.models.*;
import org.egov.digit.expense.web.models.enums.ReportType;
import org.egov.digit.expense.web.models.enums.ResponseStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.egov.tracer.model.CustomException;
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
    private final TransactionReportUtil transactionReportUtil;
    private final FileStoreUtil fileStoreUtil;
    private final PDFServiceUtil pdfServiceUtil;
    private final Configuration config;
    private final TaskRepository taskRepository;

    public TransactionReportGenerationService(BillService billService, TransactionReportExcelGenerator transactionReportExcelGenerator, TransactionReportUtil transactionReportUtil, FileStoreUtil fileStoreUtil, PDFServiceUtil pdfServiceUtil, Configuration config, TaskRepository taskRepository) {
        this.billService = billService;
        this.transactionReportExcelGenerator = transactionReportExcelGenerator;
        this.transactionReportUtil = transactionReportUtil;
        this.fileStoreUtil = fileStoreUtil;
        this.pdfServiceUtil = pdfServiceUtil;
        this.config = config;
        this.taskRepository = taskRepository;
    }

    public String createReportAndUploadToFileStore(BillTransactionReportRequest request)
            throws Exception {
        try {
            String tenantId = request.getBillTransactionReport().getTenantId();
            BillTransactionReport billTransactionReport = request.getBillTransactionReport();

            BillSearchRequest billSearchRequest = BillSearchRequest.builder()
                    .requestInfo(request.getRequestInfo())
                    .billCriteria(
                            BillCriteria.builder()
                                    .tenantId(tenantId)
                                    .ids(Collections.singleton(billTransactionReport.getBillId()))
                                    .build()
                    )
                    .pagination(new Pagination())
                    .build();

            BillResponse billResponse =
                    billService.search(billSearchRequest, false);

            List<Bill> bills = billResponse.getBills();

            if (bills == null || bills.isEmpty()) {
                return null;
            }
            List<TransactionReportRow> rows =
                    buildTransactionRows(tenantId, bills);
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

            TransactionReportRequest transactionReportRequest = transactionReportUtil.buildReportRequest(request.getRequestInfo(), tenantId, bills.get(0), rows);

            transactionReportRequest.getReport().setGeneratedTime(request.getBillTransactionReport().getAuditDetails().getCreatedTime());
            transactionReportRequest.getReport().setGeneratedBy(request.getBillTransactionReport().getAuditDetails().getCreatedBy());

            if (billTransactionReport.getType().equals(ReportType.PDF)) {
                return pdfServiceUtil.createPDF(transactionReportRequest, tenantId, config.getTxnReportPdfKey());
            } else {
                ByteArrayResource excel = transactionReportExcelGenerator.generateExcel(transactionReportRequest);
                return fileStoreUtil.uploadFileAndGetFileStoreId(tenantId, excel);
            }
        }catch (CustomException e) {
            throw new CustomException(e.getCode(), e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionReportRow> buildTransactionRows(
            String tenantId,
            List<Bill> bills
    ) {
        try {

            /* ----------------------------------
             * Extract PAID BillDetails
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
             * Fetch "DONE" TaskDetails for BillDetails
             * ---------------------------------- */
            List<TaskDetails> taskDetailsList =
                    taskRepository.searchByBillDetailIds(
                            tenantId, billDetailIds
                    );

            if (taskDetailsList == null || taskDetailsList.isEmpty()) {
                return List.of();
            }

            /* ----------------------------------
             * Index TaskDetails by BillDetailId
             * ---------------------------------- */
            Map<String, List<TaskDetails>> taskDetailsByBillDetailId =
                    taskDetailsList.stream()
                            .collect(Collectors.groupingBy(TaskDetails::getBillDetailsId));

            /* ----------------------------------
             * Build report Rows
             * ---------------------------------- */
            List<TransactionReportRow> rows = new ArrayList<>();
            Integer serialNumber = 1;

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
                                    .slNo(serialNumber++)
                                    .date(
                                            paymentTaskDetails.getAuditDetails() != null
                                                    ? paymentTaskDetails.getAuditDetails().getCreatedTime()
                                                    : null
                                    )
                                    .billNumber(bill.getBillNumber())
                                    .mtnTransactionId(paymentResponse.getFinancialTransactionId())
                                    .description(paymentResponse.getPayerMessage())
                                    .debitAmount(paymentResponse.getAmount())
                                    .build()
                    );
                }
            }


            return rows;
        } catch (Exception e) {
            log.error("BILL_TRANSACTION_REPORT_EXCEPTION", e);
            throw new CustomException("BILL_TRANSACTION_REPORT_EXCEPTION", "Exception while building rows for transaction report: " + e.getMessage());
        }
    }

    private PaymentTransferResponse getPaymentTransferResponse(TaskDetails taskDetails) {
        if (taskDetails.getAdditionalDetails() == null) {
            return null;
        }

        try {
            if (taskDetails.getAdditionalDetails() instanceof String json) {
                // JSON string → deserialize
                return mapper.readValue(json, PaymentTransferResponse.class);
            }

            return mapper.convertValue(
                    taskDetails.getAdditionalDetails(),
                    PaymentTransferResponse.class
            );
        } catch (Exception e) {
            // additionalDetails is NOT a payment response
            log.warn("Failed to parse PaymentTransferResponse from additionalDetails", e);
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

        }

        return null;
    }

}
