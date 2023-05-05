package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.works.service.ContractService;
import org.egov.works.web.models.ContractCriteria;
import org.egov.works.web.models.ContractRequest;
import org.egov.works.web.models.ContractResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Controller
@RequestMapping("/v1")
public class ContractApiController {

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final HttpServletRequest request;
    @Autowired
    private ContractService contractService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    public ContractApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> createContractV1(@ApiParam(value = "Details for the new contract.", required = true) @Valid @RequestBody ContractRequest contractRequest) {
        ContractResponse contractResponse = contractService.createContract(contractRequest);
        return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> contractV1SearchPost(@ApiParam(value = "") @Valid @RequestBody ContractCriteria contractCriteria) {
        RequestInfo requestInfo=contractCriteria.getRequestInfo();
        List<Contract> contracts = contractService.searchContracts(contractCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        ContractResponse contractResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(contracts).pagination(contractCriteria.getPagination()).build();
        return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> contractV1UpdatePost(@ApiParam(value = "", required = true) @Valid @RequestBody ContractRequest contractRequest) {
        ContractResponse contractResponse = contractService.updateContract(contractRequest);
        return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);
    }

}
