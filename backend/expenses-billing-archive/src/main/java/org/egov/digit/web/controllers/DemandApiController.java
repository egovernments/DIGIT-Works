package org.egov.digit.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.digit.web.models.DemandRequest;
import org.egov.digit.web.models.DemandResponse;
import org.egov.digit.web.models.DemandSearchRequest;
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

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class DemandApiController {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	public DemandApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/demand/v1/_create", method = RequestMethod.POST)
	public ResponseEntity<DemandResponse> demandV1CreatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DemandRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<DemandResponse>(objectMapper.readValue(
						"{  \"pagination\" : {    \"offSet\" : 3.616076749251911,    \"limit\" : 93.01444243932576,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 2.027123023002322,    \"order\" : \"\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"demands\" : [ {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  }, {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  } ]}",
						DemandResponse.class), HttpStatus.NOT_IMPLEMENTED);
			} catch (IOException e) {
				return new ResponseEntity<DemandResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<DemandResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/demand/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<DemandResponse> demandV1SearchPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DemandSearchRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<DemandResponse>(objectMapper.readValue(
						"{  \"pagination\" : {    \"offSet\" : 3.616076749251911,    \"limit\" : 93.01444243932576,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 2.027123023002322,    \"order\" : \"\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"demands\" : [ {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  }, {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  } ]}",
						DemandResponse.class), HttpStatus.NOT_IMPLEMENTED);
			} catch (IOException e) {
				return new ResponseEntity<DemandResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<DemandResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/demand/v1/_update", method = RequestMethod.POST)
	public ResponseEntity<DemandResponse> demandV1UpdatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DemandRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<DemandResponse>(objectMapper.readValue(
						"{  \"pagination\" : {    \"offSet\" : 3.616076749251911,    \"limit\" : 93.01444243932576,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 2.027123023002322,    \"order\" : \"\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"demands\" : [ {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  }, {    \"businessService\" : \"WORKS, SALARY etc\",    \"additionalFields\" : { },    \"fromPeriod\" : 6.027456183070403,    \"referenceId\" : \"referenceId\",    \"demandType\" : \"EXPENSE\",    \"payee\" : {      \"identifier\" : \"identifier\",      \"type\" : \"XYZ Holdings\"    },    \"lineItems\" : [ {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    }, {      \"amount\" : 5.637376656633329,      \"additionalFields\" : { },      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 2      },      \"tenantId\" : \"pb.amritsar\",      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"headCode\" : \"BASICSALARY, HRA, INCOMETAX\",      \"type\" : \"PAYABLE\",      \"isActive\" : true    } ],    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1.4658129805029452,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"billGenDate\" : 5.962133916683182  } ]}",
						DemandResponse.class), HttpStatus.NOT_IMPLEMENTED);
			} catch (IOException e) {
				return new ResponseEntity<DemandResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<DemandResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

}
