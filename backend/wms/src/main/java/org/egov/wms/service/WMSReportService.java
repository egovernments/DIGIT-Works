package org.egov.wms.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.wms.config.Constants;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.enrichment.WMSReportEnrichment;
import org.egov.wms.producer.WMSProducer;
import org.egov.wms.repository.ReportRepository;
import org.egov.wms.util.FileStoreUtil;
import org.egov.wms.util.IdgenUtil;
import org.egov.wms.web.controller.ReportController;
import org.egov.wms.web.model.*;
import org.egov.wms.web.model.Job.JobStatus;
import org.egov.wms.web.model.Job.ReportJob;
import org.egov.wms.web.model.Job.ReportRequest;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.egov.works.services.common.models.expense.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class WMSReportService {
    private final SearchConfiguration searchConfiguration;
    private final WMSProducer wmsProducer;
    private final ReportRepository reportRepository;
    private final FileStoreUtil fileStoreUtil;
    private final WMSReportEnrichment wmsReportEnrichment;
    private final ReportService reportService;
    private final RedisService redisService;


    @Autowired
    public WMSReportService(SearchConfiguration searchConfiguration, WMSProducer wmsProducer, ReportRepository reportRepository, FileStoreUtil fileStoreUtil, WMSReportEnrichment wmsReportEnrichment, ReportService reportService, RedisService redisService) {
        this.searchConfiguration = searchConfiguration;
        this.wmsProducer = wmsProducer;
        this.reportRepository = reportRepository;
        this.fileStoreUtil = fileStoreUtil;
        this.wmsReportEnrichment = wmsReportEnrichment;
        this.reportService = reportService;
        this.redisService = redisService;
    }

    public ReportJob processReportGeneration(String reportName, ReportRequest reportRequest) {
        log.info("Report generation started for reportName: " + reportName);
        wmsReportEnrichment.enrichReportRequest(reportName, reportRequest);
        log.info("Report Request Enriched: " + reportRequest.getJobRequest().getReportNumber());
        wmsProducer.push(searchConfiguration.getReportTopic(), reportRequest);
        return reportRequest.getJobRequest();
    }





    public void processReportGenerationAfterConsumptionFromTopic(ReportRequest reportRequest) {
        log.info("processReportGenerationAfterConsumptionFromTopic: " + reportRequest.getJobRequest().getId());
        RequestInfo requestInfo = reportRequest.getRequestInfo();
        ReportJob reportJob = reportRequest.getJobRequest();
        String tenantId = reportJob.getTenantId();

        try {
            AggsResponse aggregationResponse = getAggregationResponse(reportRequest);
            reportJob.setNoOfProjects(aggregationResponse.getProjectPaymentDetails().size());
            ByteArrayResource excelFile = generateExcelFromObject(aggregationResponse);
            String fileStoreId = fileStoreUtil.uploadFileAndGetFileStoreId(tenantId, excelFile);
            reportJob.setFileStoreId(fileStoreId);
            reportJob.setStatus(JobStatus.COMPLETED);
        }catch (Exception e){
            log.error("Exception while fetching data from service: " + e);
            reportJob.setStatus(JobStatus.FAILED);
        }

        reportJob.setAuditDetails(wmsReportEnrichment.getAuditDetails(requestInfo.getUserInfo().getUuid(), reportJob.getAuditDetails(), false));

        wmsProducer.push(searchConfiguration.getReportUpdateTopic(), reportRequest);
    }

    private AggsResponse getAggregationResponse(ReportRequest reportRequest) {
        AggsResponse aggsResponse = null;
        AggsResponse aggResponse = null;
        String afterKey = null;
        do {
            AggregationRequest aggregationRequest = wmsReportEnrichment.enrichAggregationRequestFromReportRequest(reportRequest, afterKey);
            aggResponse = reportService.getPaymentTracker(aggregationRequest);
            if(aggsResponse == null){
                aggsResponse = aggResponse;
                afterKey = aggResponse.getAfterKey();
            }else{
                if (aggResponse != null && !aggResponse.getProjectPaymentDetails().isEmpty()){
                    aggsResponse.getProjectPaymentDetails().addAll(aggResponse.getProjectPaymentDetails());
                    afterKey = aggResponse.getAfterKey();
                }
            }
        }while (aggResponse != null && aggResponse.getAfterKey() != null);

        return aggsResponse;
    }

    private ByteArrayResource generateExcelFromObject(AggsResponse aggsResponse) {
        log.info("WMSService: generateExcelFromObject");
        if (aggsResponse == null || aggsResponse.getProjectPaymentDetails() == null || aggsResponse.getProjectPaymentDetails().isEmpty()){
            throw new CustomException("NO_DATA_FOUND", "No data found for the given criteria");
        }
        List<String> headers = Constants.PAYMENT_REPORT_HEADERS;
        Map<String, Integer> headerIndexMap = new HashMap<>();
        headerIndexMap.put(Constants.EXPENSE_PURCHASE, 4);
        headerIndexMap.put(Constants.EXPENSE_WAGE, 2);
        headerIndexMap.put(Constants.EXPENSE_SUPERVISION, 6);
        byte[] excelBytes = null;
        // Logic to generate excel from object
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payment Report");
        // Logic to write data to excel sheet
        Row headerRow = sheet.createRow(0);
        // Logic to write header row
        for(int i = 0;i < headers.size(); i++){
            headerRow.createCell(i).setCellValue(headers.get(i));
        }

        for(ProjectPaymentDetails projectPaymentDetail: aggsResponse.getProjectPaymentDetails()){
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(projectPaymentDetail.getProjectNumber() == null ? "" : projectPaymentDetail.getProjectNumber());
            row.createCell(1).setCellValue(projectPaymentDetail.getEstimatedAmount() == null ? 0.0 : projectPaymentDetail.getEstimatedAmount());
            row.createCell(2).setCellValue(0.0); // For Wage payment successful
            row.createCell(3).setCellValue(0.0); // For Wage payment failed
            row.createCell(4).setCellValue(0.0); // For Purchase payment successful
            row.createCell(5).setCellValue(0.0); // For Purchase payment failed
            row.createCell(6).setCellValue(0.0); // For Supervision payment successful
            row.createCell(7).setCellValue(0.0); // For Supervision payment failed
            for(PaymentDetailsByBillType paymentDetailsByBillType: projectPaymentDetail.getPaymentDetails()){
                if (headerIndexMap.get(paymentDetailsByBillType.getBillType()) != null){
                    DecimalFormat df = new DecimalFormat("#.00");
                    if (paymentDetailsByBillType.getPaidAmount() != null){
                        row.getCell(headerIndexMap.get(paymentDetailsByBillType.getBillType())).setCellValue(Double.parseDouble(df.format(paymentDetailsByBillType.getPaidAmount())));
                    }
                    if (paymentDetailsByBillType.getRemainingAmount() != null){
                        row.getCell(headerIndexMap.get(paymentDetailsByBillType.getBillType()) + 1).setCellValue(Double.parseDouble(df.format(paymentDetailsByBillType.getRemainingAmount())));
                    }
//                    row.getCell(headerIndexMap.get(paymentDetailsByBillType.getBillType())).setCellValue(paymentDetailsByBillType.getPaidAmount() == null ? 0.0 : paymentDetailsByBillType.getPaidAmount());
//                    row.getCell(headerIndexMap.get(paymentDetailsByBillType.getBillType()) + 1).setCellValue(paymentDetailsByBillType.getRemainingAmount() == null ? 0.0 : paymentDetailsByBillType.getRemainingAmount());
                }
            }
//            row.createCell(5).setCellValue(projectPaymentDetail.getTotal() == null ? 0.0 : projectPaymentDetail.getTotal());
        }

        try {
             // Write the workbook to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            excelBytes = outputStream.toByteArray();
        }catch (IOException e){
            log.error("Exception while writing workbook to output stream: " + e);
            throw new CustomException("EXCEL_GENERATION_ERROR", "Exception while writing workbook to output stream");
        }
        return new ByteArrayResource(excelBytes) {
            @Override
            public String getFilename() {
                return "reportJob.xlsx";
            }
        };
    }

    public List<ReportJob> searchReports(ReportSearchRequest reportSearchRequest) {
        log.info("WMSReportService: searchReports");
        wmsReportEnrichment.enrichReportRequestOnSearch(reportSearchRequest);
        return reportRepository.getReportJobs(reportSearchRequest);
    }


    public Integer getReportSearchCount(ReportSearchRequest reportSearchRequest) {
        log.info("WMSReportService: getReportSearchCount");
        return reportRepository.getReportJobsCount(reportSearchRequest);
    }

    public Boolean validateJobScheduledRequest(ReportRequest reportRequest) {
        log.info("SchedulerValidator: validateJobScheduledRequest");
        try {
            String jobId = reportRequest.getJobRequest().getReportNumber();
        if (Boolean.TRUE.equals(redisService.isJobPresentInCache(jobId))) {
            return false;
        }
        redisService.setCacheForJob(jobId);
        return true;
        }catch (Exception e) {
            log.error("Error while calling redis service", e);
            throw new CustomException("REDIS_ERROR", "Error while calling redis service");
        }
    }
}
