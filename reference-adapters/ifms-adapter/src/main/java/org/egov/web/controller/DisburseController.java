package org.egov.web.controller;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.DisbursementService;
import org.egov.web.models.Disbursement;
import org.egov.web.models.DisbursementRequest;
import org.egov.web.models.DisbursementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/disburse/")
public class DisburseController {
    private final DisbursementService disbursementService;

    @Autowired
    public DisburseController(DisbursementService disbursementService) {
        this.disbursementService = disbursementService;
    }

    @RequestMapping(path = "_create", method = RequestMethod.POST)
    public DisbursementResponse request(@RequestBody @Valid DisbursementRequest disbursementRequest) {
        return disbursementService.processDisbursementRequest(disbursementRequest);
    }
}
