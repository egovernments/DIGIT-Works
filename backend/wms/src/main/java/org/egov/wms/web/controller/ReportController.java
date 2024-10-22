package org.egov.wms.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.wms.service.ReportService;
import org.egov.wms.util.ResponseInfoFactory;
import org.egov.wms.web.model.AggregationRequest;
import org.egov.wms.web.model.AggregationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final ResponseInfoFactory responseInfoFactory;

    public ReportController(ReportService reportService, ResponseInfoFactory responseInfoFactory) {
        this.reportService = reportService;
        this.responseInfoFactory = responseInfoFactory;
    }


    @PostMapping(value = "/payment_tracker")
    public ResponseEntity<AggregationResponse> getPaymentTracker(@Valid @RequestBody AggregationRequest aggregationRequest) {


        AggregationResponse aggregationResponse = AggregationResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(aggregationRequest.getRequestInfo(), true))
                .aggsResponse(reportService.getPaymentTracker(aggregationRequest))
                .build();
        return new ResponseEntity<>(aggregationResponse, HttpStatus.OK);
    }


}
