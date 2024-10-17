package org.egov.digit.expense.calculator.web.controllers;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-17T16:59:23.221+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("/purchase")
public class PurchaseBillApiController {

	@Autowired
	private final ObjectMapper objectMapper;

	@Autowired
	private final HttpServletRequest request;

	@Autowired
	private ExpenseCalculatorService expenseCalculatorService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@Autowired
	public PurchaseBillApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/v1/_createbill", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorPurchaseV1CreatebillPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PurchaseBillRequest purchaseBillRequest) {
		List<Bill> purchaseBills = expenseCalculatorService.createPurchaseBill(purchaseBillRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(purchaseBillRequest.getRequestInfo(), true);
		BillResponse billResponse = BillResponse.builder()
				.responseInfo(responseInfo)
				.bills(purchaseBills)
				.build();

		return new ResponseEntity<BillResponse>(billResponse, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/v1/_updatebill", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorPurchaseV1UpdatebillPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PurchaseBillRequest purchaseBillRequest) {
		List<Bill> purchaseBills = expenseCalculatorService.updatePurchaseBill(purchaseBillRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(purchaseBillRequest.getRequestInfo(), true);
		BillResponse billResponse = BillResponse.builder()
				.responseInfo(responseInfo)
				.bills(purchaseBills)
				.build();

		return new ResponseEntity<BillResponse>(billResponse,HttpStatus.ACCEPTED);
	}

}
