package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;

import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.service.TransactionReportGenerationService;
import java.util.Set;

import org.egov.digit.expense.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")

@Controller
@RequestMapping("/bill/v1/")
public class BillController {
	
	private final BillService service;
	private final TransactionReportGenerationService transactionReportGenerationService;

	@Autowired
	public BillController(BillService service, TransactionReportGenerationService transactionReportGenerationService) {
		this.service = service;
        this.transactionReportGenerationService = transactionReportGenerationService;
    }

	@PostMapping(value = "_create")
	public ResponseEntity<BillResponse> create(@Valid @RequestBody BillRequest billRequest) {

		BillResponse response = service.create(billRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "_search")
	public ResponseEntity<BillResponse> search(@Valid @RequestBody BillSearchRequest billSearchRequest) throws Exception {
		BillResponse billResponse = service.search(billSearchRequest, true);
		transactionReportGenerationService.createReportAndUploadToFileStore(billSearchRequest.getRequestInfo(),
				"cg",
				Set.of("fb556d64-9b7c-46a6-ad7b-3b8d3f883529")
		);
		return new ResponseEntity<>(billResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "_update")
	public ResponseEntity<BillResponse> update(@Valid @RequestBody BillRequest billRequest) {
		BillResponse response = service.update(billRequest);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "search/_calculated")
	public ResponseEntity<BillResponse> searchCalculatedBills(@Valid @RequestBody BillSearchRequest billSearchRequest) {
		BillResponse billResponse = service.searchCalculatedBills(billSearchRequest, true);
		return new ResponseEntity<>(billResponse, HttpStatus.ACCEPTED);
	}
}
