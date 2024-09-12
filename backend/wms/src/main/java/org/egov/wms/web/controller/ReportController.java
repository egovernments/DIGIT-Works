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

import java.io.File;
import java.io.IOException;

import static org.reflections.util.ConfigurationBuilder.build;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final ResponseInfoFactory responseInfoFactory;
    private final ObjectMapper mapper;

    public ReportController(ReportService reportService, ResponseInfoFactory responseInfoFactory, ObjectMapper mapper) {
        this.reportService = reportService;
        this.responseInfoFactory = responseInfoFactory;
        this.mapper = mapper;
    }


    @PostMapping(value = "/payment_tracker")
    public ResponseEntity<AggregationResponse> getPaymentTracker(@Valid @RequestBody AggregationRequest aggregationRequest) {


        AggregationResponse aggregationResponse = AggregationResponse.builder()
                .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(aggregationRequest.getRequestInfo(), true))
                .aggsResponse(reportService.getPaymentTracker(aggregationRequest))
                .build();
        try {
            aggregationResponse = mapper.readValue(new File("../wms/src/main/resources/payment_tracker_sample.json"), AggregationResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(aggregationResponse, HttpStatus.OK);
    }


}
