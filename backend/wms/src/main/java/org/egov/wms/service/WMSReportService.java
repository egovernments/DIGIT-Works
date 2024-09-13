package org.egov.wms.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.producer.WMSProducer;
import org.egov.wms.repository.ReportRepository;
import org.egov.wms.util.FileStoreUtil;
import org.egov.wms.util.IdgenUtil;
import org.egov.wms.web.model.Job.JobStatus;
import org.egov.wms.web.model.Job.ReportJob;
import org.egov.wms.web.model.Job.ReportRequest;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.egov.wms.web.model.WMSSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WMSReportService {
    private final IdgenUtil idgenUtil;
    private final SearchConfiguration searchConfiguration;
    private final WMSProducer wmsProducer;
    private final ReportRepository reportRepository;
    private final FileStoreUtil fileStoreUtil;

    @Autowired
    public WMSReportService(IdgenUtil idgenUtil, SearchConfiguration searchConfiguration, WMSProducer wmsProducer, ReportRepository reportRepository, FileStoreUtil fileStoreUtil) {
        this.idgenUtil = idgenUtil;
        this.searchConfiguration = searchConfiguration;
        this.wmsProducer = wmsProducer;
        this.reportRepository = reportRepository;
        this.fileStoreUtil = fileStoreUtil;
    }

    public ReportJob processReportGeneration(String reportName, ReportRequest reportRequest) {
        log.info("Report generation started for reportName: " + reportName);
        enrichReportRequest(reportName, reportRequest);
        log.info("Report Request Enriched: " + reportRequest.getJobRequest().getReportNumber());
        wmsProducer.push(searchConfiguration.getReportTopic(), reportRequest);
        return reportRequest.getJobRequest();
    }

    private void enrichReportRequest(String reportName, ReportRequest reportRequest) {
        log.info("Enriching reportRequest for reportName: " + reportName);
        List<String> reportIds = idgenUtil.getIdList(reportRequest.getRequestInfo(), reportRequest.getJobRequest().getTenantId(), searchConfiguration.getReportIdName(), null, 1);
        reportRequest.getJobRequest().setId(UUID.randomUUID().toString());
        reportRequest.getJobRequest().setReportName(reportName);
        reportRequest.getJobRequest().setReportNumber(reportIds.get(0));
        reportRequest.getJobRequest().setStatus(JobStatus.INITIATED);
        String userId = reportRequest.getRequestInfo().getUserInfo().getUuid();
        reportRequest.getJobRequest().setAuditDetails(getAuditDetails(userId, reportRequest.getJobRequest().getAuditDetails(), true));
    }

     public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (Boolean.TRUE.equals(isCreate))
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).lastModifiedBy(by)
                    .createdTime(auditDetails.getCreatedTime()).lastModifiedTime(time).build();
    }

    public void processReportGenerationAfterConsumptionFromTopic(ReportRequest reportRequest) {
        log.info("processReportGenerationAfterConsumptionFromTopic: " + reportRequest.getJobRequest().getId());
        RequestInfo requestInfo = reportRequest.getRequestInfo();
        ReportJob reportJob = reportRequest.getJobRequest();
        String tenantId = reportJob.getTenantId();

        ByteArrayResource excelFile = generateExcelFromObject(reportJob);
        String fileStoreId = fileStoreUtil.uploadFileAndGetFileStoreId(tenantId, excelFile);
        reportJob.setFileStoreId(fileStoreId);
        reportJob.setStatus(JobStatus.COMPLETED);
        reportJob.setAuditDetails(getAuditDetails(requestInfo.getUserInfo().getUuid(), reportJob.getAuditDetails(), false));

        wmsProducer.push(searchConfiguration.getReportTopic(), reportRequest);
    }

    private ByteArrayResource generateExcelFromObject(ReportJob reportJob) {
        log.info("WMSService: generateExcelFromObject");
        byte[] excelBytes = null;
        // Logic to generate excel from object
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("reportJobSheet");
        // Logic to write data to excel sheet
        Row headerRow = sheet.createRow(0);
        // Logic to write header row
        headerRow.createCell(0).setCellValue("Report Number");
        headerRow.createCell(1).setCellValue("Report Name");

        Row dataRow = sheet.createRow(1);
        // Logic to write data row
        dataRow.createCell(0).setCellValue(reportJob.getReportNumber());
        dataRow.createCell(1).setCellValue(reportJob.getReportName());

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
        return reportRepository.getReportJobs(reportSearchRequest);
    }

    public Integer getReportSearchCount(ReportSearchRequest reportSearchRequest) {
        log.info("WMSReportService: getReportSearchCount");
        return reportRepository.getReportJobsCount(reportSearchRequest);
    }
}
