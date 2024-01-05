package org.digit.exchange.web.controllers;

import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.service.ExchangeService;
import org.digit.exchange.web.models.RequestMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange/v2")
public class ExchangeController2 {

    private ExchangeService exchangeService;

    public ExchangeController2(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping(value = "/{subpath1}/{subpath2}")
    public ResponseEntity<RequestMessage> program(@RequestBody RequestMessage messageRequest, @PathVariable("subpath1") String subpath) {
        try {
            RequestMessage response = exchangeService.processMessage(subpath, messageRequest);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
