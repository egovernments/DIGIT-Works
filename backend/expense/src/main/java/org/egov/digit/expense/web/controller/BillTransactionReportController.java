package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.service.BillTransactionReportService;
import org.egov.digit.expense.web.models.BillTransactionReportRequest;
import org.egov.digit.expense.web.models.BillTransactionReportResponse;
import org.egov.digit.expense.web.models.BillTransactionReportSearchRequest;
import org.egov.digit.expense.web.models.BillTransactionReportSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expense/v1/transactions/report")
@Slf4j
public class BillTransactionReportController {

    private final BillTransactionReportService service;

    @Autowired
    public BillTransactionReportController(BillTransactionReportService service) {
        this.service = service;
    }

    @PostMapping(value = "/_generate")
    public ResponseEntity<BillTransactionReportResponse> generate(
            @Valid @RequestBody BillTransactionReportRequest request) {
        log.info("BillTransactionReportController::generate");
        BillTransactionReportResponse response = service.generate(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/_search")
    public ResponseEntity<BillTransactionReportSearchResponse> search(
            @Valid @RequestBody BillTransactionReportSearchRequest request) {
        log.info("BillTransactionReportController::search");
        BillTransactionReportSearchResponse response = service.search(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
