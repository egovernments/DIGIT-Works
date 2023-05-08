package org.egov.digit.expense.web.controller;

import javax.validation.Valid;

import org.egov.digit.expense.service.BillService;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillResponse;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")

@Controller
@RequestMapping("/bill/v1/")
public class BillController {
	
	@Autowired
	private BillService service;

	@PostMapping(value = "_create")
	public ResponseEntity<BillResponse> Create(@Valid @RequestBody BillRequest billRequest) {

		BillResponse response = service.create(billRequest);
		return new ResponseEntity<BillResponse>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "_search")
	public ResponseEntity<BillResponse> search(@Valid @RequestBody BillSearchRequest billSearchRequest) {
		BillResponse billResponse = service.search(billSearchRequest, true);
		return new ResponseEntity<BillResponse>(billResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "_update")
	public ResponseEntity<BillResponse> update(@Valid @RequestBody BillRequest billRequest) {
		BillResponse response = service.update(billRequest);
		return new ResponseEntity<BillResponse>(response, HttpStatus.ACCEPTED);
	}

}
