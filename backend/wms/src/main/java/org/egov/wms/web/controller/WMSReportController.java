package org.egov.wms.web.controller;

import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.tracer.config.TracerConfiguration;
import org.egov.wms.service.WMSReportService;
import org.egov.wms.util.ResponseInfoFactory;
import org.egov.wms.web.model.Job.*;
import org.egov.wms.web.model.WMSSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Slf4j
@Import({TracerConfiguration.class, MultiStateInstanceUtil.class})
public class WMSReportController {

    private final WMSReportService wmsReportService;
    private final ResponseInfoFactory responseInfoFactory;

    @Autowired
    public WMSReportController(WMSReportService wmsReportService, ResponseInfoFactory responseInfoFactory) {
        this.wmsReportService = wmsReportService;
        this.responseInfoFactory = responseInfoFactory;
    }

    @PostMapping(value = "/{REPORT_NAME}")
    public ResponseEntity<ReportResponse> generateReport(@PathVariable("REPORT_NAME") String reportName, @RequestBody ReportRequest reportRequest) {
        ReportJob reportJob = wmsReportService.processReportGeneration(reportName, reportRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(reportRequest.getRequestInfo(), true);
        ReportResponse reportResponse = ReportResponse.builder().responseInfo(responseInfo).jobResponse(reportJob).build();
        return ResponseEntity.ok(reportResponse);
    }

    @PostMapping(value = "/_search")
    public ResponseEntity<ReportSearchResponse> searchReports(@RequestBody ReportSearchRequest reportSearchRequest) {
        List<ReportJob> reportJobs = wmsReportService.searchReports(reportSearchRequest);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(reportSearchRequest.getRequestInfo(), true);
        ReportSearchResponse reportResponse = ReportSearchResponse.builder().responseInfo(responseInfo).reportJobs(reportJobs).build();
        return ResponseEntity.ok(reportResponse);
    }
}
