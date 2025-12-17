package org.egov.digit.expense.calculator.web.controllers;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.web.models.*;
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

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class WorksCalculatorApiController {
	@Autowired
	private final ObjectMapper objectMapper;
	@Autowired
	private final HttpServletRequest request;
	@Autowired
	private ExpenseCalculatorService expenseCalculatorService;
	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@Autowired
	public WorksCalculatorApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/v1/_calculate", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorV1CalculatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody CalculationRequest calculationRequest) {
		List<Bill> bills = expenseCalculatorService.createWageOrSupervisionBills(calculationRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(calculationRequest.getRequestInfo(), true);
		BillResponse billResponse = BillResponse.builder()
				.responseInfo(responseInfo)
				.bills(bills)
				.build();

		return new ResponseEntity<BillResponse>(billResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/_estimate", method = RequestMethod.POST)
	public ResponseEntity<CalculationResponse> worksCalculatorV1EstimatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody CalculationRequest calculationRequest) {

		Calculation calculation = expenseCalculatorService.calculateEstimates(calculationRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(calculationRequest.getRequestInfo(), true);
		CalculationResponse calculationResponse = CalculationResponse.builder()
																	 .responseInfo(responseInfo)
				                                                     .calculation(calculation)
				                                                     .build();
		return new ResponseEntity<CalculationResponse>(calculationResponse, HttpStatus.OK);
	}


	@RequestMapping(value = "/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<BillMapperSearchResponse> search(@Valid @RequestBody CalculatorSearchRequest calculatorSearchRequest) {
		RequestInfo requestInfo=calculatorSearchRequest.getRequestInfo();
		List<BillMapper> bills = expenseCalculatorService.search(calculatorSearchRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(calculatorSearchRequest.getRequestInfo(), true);
		BillMapperSearchResponse billResponse= BillMapperSearchResponse.builder().responseInfo(responseInfo).billMappers(bills)
				.pagination(calculatorSearchRequest.getPagination()).build();
		return new ResponseEntity<BillMapperSearchResponse>(billResponse, HttpStatus.OK);
	}

}
