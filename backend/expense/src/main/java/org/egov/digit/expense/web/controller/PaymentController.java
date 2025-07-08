package org.egov.digit.expense.web.controller;

import org.egov.digit.expense.service.PaymentService;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.digit.expense.web.models.PaymentResponse;
import org.egov.digit.expense.web.models.PaymentSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@jakarta.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-02T17:49:59.877+05:30[Asia/Kolkata]")

@Controller
@RequestMapping("/payment/v1/")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping(value = "_create")
    public ResponseEntity<PaymentResponse> Create(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = service.create(paymentRequest);
        return new ResponseEntity<PaymentResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "_search")
    public ResponseEntity<PaymentResponse> search(@Valid @RequestBody PaymentSearchRequest paymentSearchRequest) {
        PaymentResponse response = service.search(paymentSearchRequest);
        return new ResponseEntity<PaymentResponse>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "_update")
    public ResponseEntity<PaymentResponse> update(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = service.update(paymentRequest);
        return new ResponseEntity<PaymentResponse>(response, HttpStatus.ACCEPTED);
    }

}
