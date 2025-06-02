package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.service.BillVerificationService;
import org.egov.digit.expense.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")

@Controller
@RequestMapping("/bill/v1/")
public class BillVerificationController {

	private final BillVerificationService service;

	@Autowired
	public BillVerificationController(BillVerificationService service) {
		this.service = service;
	}

	@PostMapping(value = "_verify")
	public ResponseEntity<BillVerificationResponse> verifyBill(@Valid @RequestBody BillVerificationRequest billVerificationRequest) {
		BillVerificationResponse response = service.verify(billVerificationRequest);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "task/_status")
	public ResponseEntity<BillVerificationStatusResponse> getBillVerificationTaskStatus(@Valid @RequestBody BillVerificationStatusRequest billVerificationStatusRequest) {

		BillVerificationStatusResponse response = service.getBillVerificationStatus(billVerificationStatusRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
