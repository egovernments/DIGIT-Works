package org.egov.digit.expense.calculator.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.Calculation;
import org.egov.digit.expense.calculator.web.models.CalculationRequest;
import org.egov.digit.expense.calculator.web.models.CalculationResponse;
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

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
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

	@RequestMapping(value = "/works-calculator/v1/_calculate", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorV1CalculatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody CalculationRequest body) {

		return null;
	}

	@RequestMapping(value = "/works-calculator/v1/_estimate", method = RequestMethod.POST)
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

}
