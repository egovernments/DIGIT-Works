package org.egov.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.web.models.OrgRequest;
import org.egov.web.models.OrgResponse;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Controller
@RequestMapping("")
public class OrganisationApiController {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	public OrganisationApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/org-services/organisation/v1/_create", method = RequestMethod.POST)
	public ResponseEntity<OrgResponse> orgServicesOrganisationV1CreatePOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/org-services/organisation/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<OrgServiceResponse> orgServicesOrganisationV1SearchPOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgSearchCriteria body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/org-services/organisation/v1/_update", method = RequestMethod.POST)
	public ResponseEntity<OrgResponse> orgServicesOrganisationV1UpdatePOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgResponse>(HttpStatus.NOT_IMPLEMENTED);
	}
}
