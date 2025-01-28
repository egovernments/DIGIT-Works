package org.egov.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.service.BankAccountService;
import org.egov.util.ResponseInfoFactory;
import org.egov.web.models.*;
import org.egov.works.services.common.models.bankaccounts.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("/bankaccount/v1")
@Slf4j
public class BankaccountApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ResponseInfoFactory responseInfoFactory;

    private final BankAccountService bankAccountService;

    @Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request, ResponseInfoFactory responseInfoFactory, BankAccountService bankAccountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.responseInfoFactory = responseInfoFactory;
        this.bankAccountService = bankAccountService;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<BankAccountResponse> bankaccountV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BankAccountRequest body) {
        BankAccountRequest bankAccountRequest = bankAccountService.createBankAccount(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        BankAccountResponse bankAccountResponse = BankAccountResponse.builder().responseInfo(responseInfo).bankAccounts(bankAccountRequest.getBankAccounts()).build();
        return new ResponseEntity<BankAccountResponse>(bankAccountResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<BankAccountResponse> bankaccountV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BankAccountSearchRequest body) {
        //TODO: remove it -start
        log.info("search request : {}",body);
        log.info("search criteria : {}",body.getBankAccountDetails());
        log.info("search request info : {}",body.getRequestInfo());
        //TODO: remove it -end
        List<BankAccount> bankAccounts = bankAccountService.searchBankAccount(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        Pagination pagination = bankAccountService.countAllBankAccounts(body);
        BankAccountResponse bankAccountResponse = BankAccountResponse.builder().responseInfo(responseInfo).bankAccounts(bankAccounts).pagination(pagination).build();
        return new ResponseEntity<BankAccountResponse>(bankAccountResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<BankAccountResponse> bankaccountV1UpdatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BankAccountRequest body) {
        BankAccountRequest bankAccountRequest = bankAccountService.updateBankAccount(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        BankAccountResponse bankAccountResponse = BankAccountResponse.builder().responseInfo(responseInfo).bankAccounts(bankAccountRequest.getBankAccounts()).build();
        return new ResponseEntity<BankAccountResponse>(bankAccountResponse, HttpStatus.OK);
    }

}
