package org.egov.digit.expense.web.controllers;

import javax.validation.Valid;

import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillResponse;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")

@Controller
@RequestMapping("expense")
public class BillController {

	@RequestMapping(value = "/bill/v1/_create", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> billV1CreatePost(@Valid @RequestBody BillRequest body) {

		return new ResponseEntity<BillResponse>(new BillResponse(), HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/bill/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> billV1SearchPost(@Valid @RequestBody BillSearchRequest body) {
		return new ResponseEntity<BillResponse>(new BillResponse(), HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/bill/v1/_update", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> billV1UpdatePost(@Valid @RequestBody BillRequest body) {
		return new ResponseEntity<BillResponse>(new BillResponse(), HttpStatus.NOT_IMPLEMENTED);
	}

}
