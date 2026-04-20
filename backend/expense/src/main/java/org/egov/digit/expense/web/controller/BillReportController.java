package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.BillReportService;
import org.egov.digit.expense.web.models.BillReportRequest;
import org.egov.digit.expense.web.models.BillReportResponse;
import org.egov.digit.expense.web.models.BillReportSearchRequest;
import org.egov.digit.expense.web.models.BillReportSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bill/v1/report")
@Slf4j
public class BillReportController {

    private final BillReportService service;

    @Autowired
    public BillReportController(BillReportService service) {
        this.service = service;
    }

    @PostMapping(value = "/_generate")
    public ResponseEntity<BillReportResponse> generate(
            @Valid @RequestBody BillReportRequest request) {
        log.info("BillReportController::generate");
        BillReportResponse response = service.generate(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/_search")
    public ResponseEntity<BillReportSearchResponse> search(
            @Valid @RequestBody BillReportSearchRequest request) {
        log.info("BillReportController::search");
        BillReportSearchResponse response = service.search(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
