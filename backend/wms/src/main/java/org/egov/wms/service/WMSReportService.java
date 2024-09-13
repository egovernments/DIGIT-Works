package org.egov.wms.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.producer.WMSProducer;
import org.egov.wms.repository.ReportRepository;
import org.egov.wms.util.IdgenUtil;
import org.egov.wms.web.model.Job.JobStatus;
import org.egov.wms.web.model.Job.ReportJob;
import org.egov.wms.web.model.Job.ReportRequest;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.egov.wms.web.model.WMSSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WMSReportService {
    private final IdgenUtil idgenUtil;
    private final SearchConfiguration searchConfiguration;
    private final WMSProducer wmsProducer;
    private final ReportRepository reportRepository;

    @Autowired
    public WMSReportService(IdgenUtil idgenUtil, SearchConfiguration searchConfiguration, WMSProducer wmsProducer, ReportRepository reportRepository) {
        this.idgenUtil = idgenUtil;
        this.searchConfiguration = searchConfiguration;
        this.wmsProducer = wmsProducer;
        this.reportRepository = reportRepository;
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

        reportRequest.getJobRequest().setFileStoreId("33333");
        wmsProducer.push(searchConfiguration.getReportTopic(), reportRequest);
    }

    public List<ReportJob> searchReports(ReportSearchRequest reportSearchRequest) {
        log.info("WMSReportService: searchReports");
        return reportRepository.getReportJobs(reportSearchRequest.getReportSearchCriteria());
    }
}
