package org.egov.digit.expense.calculator.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.HealthBillReportGenerator;
import org.egov.digit.expense.calculator.util.ExcelGenerate;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.web.models.*;
import org.egov.digit.expense.calculator.web.models.report.BillReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-11T13:19:59.852+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("/report/v1/")
public class HealthReportApiController {
	private final ObjectMapper objectMapper;
	private final HttpServletRequest request;
	private ResponseInfoFactory responseInfoFactory;
	private HealthBillReportGenerator healthBillReportGenerator;
	private ExcelGenerate excelGenerate;

	@Autowired
	public HealthReportApiController(ObjectMapper objectMapper, HttpServletRequest request, HealthBillReportGenerator healthBillReportGenerator, ResponseInfoFactory responseInfoFactory, ExcelGenerate excelGenerate) {
		this.objectMapper = objectMapper;
		this.request = request;
		this.healthBillReportGenerator = healthBillReportGenerator;
		this.responseInfoFactory = responseInfoFactory;
		this.excelGenerate = excelGenerate;
	}


	@RequestMapping(value = "/_generate", method = RequestMethod.POST)
	public ResponseEntity<BillReportRequest> generateReport(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BillRequest billRequest) {
		BillReportRequest billReportRequest = healthBillReportGenerator.generateHealthBillReportRequest(billRequest);
//		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(billRequest.getRequestInfo(), true);
//		CalculationResponse calculationResponse = CalculationResponse.builder()
//				.responseInfo(responseInfo)
//				.calculation(new Calculation())
//				.build();
		return new ResponseEntity<BillReportRequest>(billReportRequest, HttpStatus.OK);
	}

	@RequestMapping(value = "/excel", method = RequestMethod.POST)
	public ResponseEntity<CalculationResponse> generateExcel(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BillReportRequest billReportRequest) {
		excelGenerate.generateExcel(billReportRequest.getRequestInfo(), billReportRequest.getReportBill().get(0));
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(billReportRequest.getRequestInfo(), true);
		CalculationResponse calculationResponse = CalculationResponse.builder()
				.responseInfo(responseInfo)
				.calculation(new Calculation())
				.build();
		return new ResponseEntity<CalculationResponse>(calculationResponse, HttpStatus.OK);
	}


}
