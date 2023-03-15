package digit.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.service.BankAccountService;
import digit.util.ResponseInfoFactory;
import digit.web.models.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-14T17:30:53.139+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("/bankaccount/v1")
public class BankaccountApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<BankAccountResponse> bankaccountV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BankAccountRequest body) {
        BankAccountRequest bankAccountRequest = bankAccountService.createBankAccount(body);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
        BankAccountResponse bankAccountResponse = BankAccountResponse.builder().responseInfo(responseInfo).bankAccounts(Collections.singletonList(bankAccountRequest.getBankAccounts())).build();
        return new ResponseEntity<BankAccountResponse>(bankAccountResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<BankAccountResponse> bankaccountV1SearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BankAccountSearchRequest body) {
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
        BankAccountResponse bankAccountResponse = BankAccountResponse.builder().responseInfo(responseInfo).bankAccounts(Collections.singletonList(bankAccountRequest.getBankAccounts())).build();
        return new ResponseEntity<BankAccountResponse>(bankAccountResponse, HttpStatus.OK);
    }

}
