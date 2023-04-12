package org.egov.digit.expense.calculator.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.CalculatorService;
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
	private CalculatorService calculatorService;
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
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<BillResponse>(objectMapper.readValue(
						"{  \"pagination\" : {    \"offSet\" : 5.637376656633329,    \"limit\" : 59.621339166831824,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 2.3021358869347655,    \"order\" : \"\"  },  \"bill\" : [ {    \"billDetails\" : [ {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    }, {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    } ],    \"businessService\" : \"WORKS-MUSTERROLE\",    \"fromPeriod\" : 1680307200000,    \"dueDate\" : 1685491199000,    \"billDate\" : 0.8008281904610115,    \"netPayableAmount\" : 500,    \"additionalDetails\" : { },    \"payer\" : {      \"identifier\" : \"RURALMINISTRY-MUKTHA\",      \"type\" : \"DEPARTMENT\"    },    \"netPaidAmount\" : 6.027456183070403,    \"referenceId\" : \"MUSTERROLE-WAGE-01\",    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1682899199000,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"paymentStatus\" : \"paymentStatus\",    \"status\" : \"ACTIVE\"  }, {    \"billDetails\" : [ {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    }, {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 3,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 9        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 1.4658129805029452,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    } ],    \"businessService\" : \"WORKS-MUSTERROLE\",    \"fromPeriod\" : 1680307200000,    \"dueDate\" : 1685491199000,    \"billDate\" : 0.8008281904610115,    \"netPayableAmount\" : 500,    \"additionalDetails\" : { },    \"payer\" : {      \"identifier\" : \"RURALMINISTRY-MUKTHA\",      \"type\" : \"DEPARTMENT\"    },    \"netPaidAmount\" : 6.027456183070403,    \"referenceId\" : \"MUSTERROLE-WAGE-01\",    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1682899199000,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"paymentStatus\" : \"paymentStatus\",    \"status\" : \"ACTIVE\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}",
						BillResponse.class), HttpStatus.NOT_IMPLEMENTED);
			} catch (IOException e) {
				return new ResponseEntity<BillResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<BillResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/works-calculator/v1/_estimate", method = RequestMethod.POST)
	public ResponseEntity<CalculationResponse> worksCalculatorV1EstimatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody CalculationRequest calculationRequest) {

		Calculation calculation = calculatorService.createEstimate(calculationRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(calculationRequest.getRequestInfo(), true);
		CalculationResponse calculationResponse = CalculationResponse.builder()
																	 .responseInfo(responseInfo)
				                                                     .calculation(calculation)
				                                                     .build();

		return new ResponseEntity<CalculationResponse>(calculationResponse, HttpStatus.OK);
	}

}
