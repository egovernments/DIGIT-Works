package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.egov.works.web.models.DeathRegistrationRequest;
import org.egov.works.web.models.DeathRegistrationResponse;
import org.egov.works.web.models.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-10-05T11:05:03.529+05:30")

@Controller
@RequestMapping("/death-services/v1/registration")
public class V1ApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationResponse> v1RegistrationCreatePost(@ApiParam(value = "Details for the new Death Registration Application(s) + RequestInfo meta data.", required = true) @Valid @RequestBody DeathRegistrationRequest deathRegistrationRequest) {
        return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationRequest> v1RegistrationSearchPost(@NotNull @ApiParam(value = "Unique id for a tenant.", required = true) @Valid @RequestParam(value = "tenantId", required = true) String tenantId, @ApiParam(value = "Parameter to carry Request metadata in the request body") @Valid @RequestBody RequestInfo requestInfo, @ApiParam(value = "Search based on status.") @Valid @RequestParam(value = "status", required = false) String status, @Size(max = 50) @ApiParam(value = "Unique identifier of death registration application") @Valid @RequestParam(value = "ids", required = false) List<Long> ids, @Size(min = 2, max = 64) @ApiParam(value = "Unique application number for the Death Registration Application") @Valid @RequestParam(value = "applicationNumber", required = false) String applicationNumber) {
        return new ResponseEntity<DeathRegistrationRequest>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<DeathRegistrationResponse> v1RegistrationUpdatePost(@ApiParam(value = "Details for the new (s) + RequestInfo meta data.", required = true) @Valid @RequestBody DeathRegistrationRequest deathRegistrationRequest) {
        return new ResponseEntity<DeathRegistrationResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
