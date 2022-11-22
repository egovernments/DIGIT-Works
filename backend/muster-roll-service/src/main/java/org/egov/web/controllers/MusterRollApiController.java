package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import io.swagger.annotations.ApiParam;
import org.egov.web.models.MusterRollRequest;
import org.egov.web.models.MusterRollResponse;
import org.egov.web.models.MusterRollSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Controller
@RequestMapping("/v1")
public class MusterRollApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1CreatePost(@ApiParam(value = "", required = true) @Valid @RequestBody MusterRollRequest musterRollRequest) {
        return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> attendanceV1SearchPOST(@Valid @RequestBody RequestInfoWrapper body, @Valid @ModelAttribute MusterRollSearchCriteria searchCriteria) {
        return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<MusterRollResponse> musterRollV1UpdatePost(@ApiParam(value = "", required = true) @Valid @RequestBody MusterRollRequest musterRollRequest) {
        return new ResponseEntity<MusterRollResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
