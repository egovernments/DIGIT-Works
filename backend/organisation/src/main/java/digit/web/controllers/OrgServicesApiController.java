package digit.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import digit.web.models.OrgSearchCriteria;
import digit.web.models.OrgServiceRequest;
import digit.web.models.OrgServiceResponse;
import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-15T14:49:42.141+05:30")

@Controller
@RequestMapping("")
public class OrgServicesApiController {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	public OrgServicesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/org-services/v1/_create", method = RequestMethod.POST)
	public ResponseEntity<OrgServiceResponse> orgServicesV1CreatePOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgServiceRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/org-services/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<OrgServiceResponse> orgServicesV1SearchPOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgSearchCriteria body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/org-services/v1/_update", method = RequestMethod.POST)
	public ResponseEntity<OrgServiceResponse> orgServicesV1UpdatePOST(
			@ApiParam(value = "", allowableValues = "application/json") @RequestHeader(value = "Content-Type", required = false) String contentType,
			@ApiParam(value = "") @Valid @RequestBody OrgServiceRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<OrgServiceResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

}
