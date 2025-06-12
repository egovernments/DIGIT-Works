package org.egov.digit.expense.web.controller;

import jakarta.validation.Valid;
import org.egov.digit.expense.service.MTNService;
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
@RequestMapping("/v1/")
public class MTNController {

	private final MTNService service;

	@Autowired
	public MTNController(MTNService service) {
		this.service = service;
	}

	@PostMapping(value = "bill/_verify")
	public ResponseEntity<BillTaskResponse> verifyBill(@Valid @RequestBody BillTaskRequest request) {
		BillTaskResponse response = service.verify(request);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "task/_status")
	public ResponseEntity<StatusResponse> getBillVerificationTaskStatus(@Valid @RequestBody StatusRequest request) {

		StatusResponse response = service.getTaskStatus(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value= "task/_details")
	public ResponseEntity<TaskDetailsResponse> getTaskDetails(@RequestBody TaskDetailsRequest taskDetailsRequest){
		TaskDetailsResponse response = service.getTaskDetails(taskDetailsRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@PostMapping(value = "payment/_transfer")
	public ResponseEntity<BillTaskResponse> Create(@Valid @RequestBody BillTaskRequest request) {
		BillTaskResponse response = service.transfer(request);
		return new ResponseEntity<BillTaskResponse>(response, HttpStatus.ACCEPTED);
	}

}
