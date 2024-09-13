package org.egov.wms.enrichment;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.AuditDetails;
import org.egov.wms.config.SearchConfiguration;
import org.egov.wms.util.IdgenUtil;
import org.egov.wms.web.model.AggregationRequest;
import org.egov.wms.web.model.AggregationSearchCriteria;
import org.egov.wms.web.model.Job.JobStatus;
import org.egov.wms.web.model.Job.ReportJob;
import org.egov.wms.web.model.Job.ReportRequest;
import org.egov.wms.web.model.Job.ReportSearchRequest;
import org.egov.works.services.common.models.expense.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class WMSReportEnrichment {
    private final IdgenUtil idgenUtil;
    private final SearchConfiguration searchConfiguration;

    @Autowired
    public WMSReportEnrichment(IdgenUtil idgenUtil, SearchConfiguration searchConfiguration) {
        this.idgenUtil = idgenUtil;
        this.searchConfiguration = searchConfiguration;
    }


    public void enrichReportRequest(String reportName, ReportRequest reportRequest) {
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

    public void enrichReportRequestOnSearch(ReportSearchRequest reportSearchRequest) {
        log.info("WMSReportService: enrichReportRequestOnSearch");
        Pagination pagination = reportSearchRequest.getPagination();
        if (pagination == null) {
            pagination = Pagination.builder().limit(searchConfiguration.getReportDefaultLimit()).offSet(searchConfiguration.getReportDefaultOffset()).order(Pagination.OrderEnum.DESC).sortBy("createdtime").build();
            reportSearchRequest.setPagination(pagination);
        }
        if(pagination.getLimit() == null)
            pagination.setLimit(searchConfiguration.getReportDefaultLimit());

        if(pagination.getOffSet() == null)
            pagination.setOffSet(searchConfiguration.getReportDefaultOffset());

        if(pagination.getOrder() == null)
            pagination.setOrder(Pagination.OrderEnum.DESC);

        if(pagination.getSortBy() == null)
            pagination.setSortBy("createdtime");
    }

    public AggregationRequest enrichAggregationRequestFromReportRequest(ReportRequest reportRequest) {
        log.info("WMSReportService: enrichAggregationRequestFromReportRequest");
        ReportJob reportJob = reportRequest.getJobRequest();
        AggregationSearchCriteria aggregationSearchCriteria = AggregationSearchCriteria.builder()
                .moduleSearchCriteria((HashMap<String, Object>) reportJob.getRequestPayload())
                .tenantId(reportJob.getTenantId())
                .build();

        return AggregationRequest.builder()
                .requestInfo(reportRequest.getRequestInfo())
                .aggregationSearchCriteria(aggregationSearchCriteria)
                .build();
    }
}
