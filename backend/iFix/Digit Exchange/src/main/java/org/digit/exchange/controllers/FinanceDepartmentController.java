package org.digit.exchange.controllers;

import org.digit.exchange.constants.Action;
import org.digit.exchange.models.*;
import org.digit.exchange.models.fiscal.Estimate;
import org.digit.exchange.models.fiscal.Program;
import org.digit.exchange.models.fiscal.Sanction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import java.util.UUID;


@Controller
@RequestMapping("/finance")
public class FinanceDepartmentController{
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final WebClient webClient;

    public FinanceDepartmentController() {
        this.webClient = WebClient.create();
    }

    //Request to Create a Program
    @RequestMapping(value = "/program/create", method = RequestMethod.POST)
    public ResponseEntity<String> program_create(@RequestBody Program program) {
        logger.info("Creating new program");

        Program programReply = new Program();
        programReply.copy(program);
        //Set Program Code indicating Program has been created
        programReply.setProgram(program.getName().toUpperCase());

        //Tell line that program has been created.
        RequestMessage request;
        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            Action action = Action.create;
            request = new RequestMessage(to, from, programReply, action);
        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Program Request");
        }
        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/on-program")
                    .body(Mono.just(request), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        String result = responseMono.block();

        JSONObject response = new JSONObject();
        UUID txn_uuid = UUID.randomUUID();
        response.put("transaction_id",txn_uuid);
        return ResponseEntity.ok(response.toString());
    }

    //Request to Create a Estimate
    @RequestMapping(value = "/estimate/create", method = RequestMethod.POST)
    public ResponseEntity<String> estimate_create(@RequestBody Estimate estimate) {
        logger.info("Creating new estimate");

        //Set Estimate Id indicating Estimate has been created
        UUID estimateId =  UUID.randomUUID();
        estimate.setId(estimateId.toString());

        Estimate estimateReply = new Estimate();
        estimateReply.copy(estimate);

        //Tell line that estimate has been approved.
        RequestMessage request;
        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            Action action = Action.create;
            request = new RequestMessage(to, from, estimateReply, action);
        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Program Request");
        }
        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/on-estimate")
                    .body(Mono.just(request), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        JSONObject response = new JSONObject();
        UUID txn_uuid = UUID.randomUUID();
        response.put("transaction_id",txn_uuid);
        return ResponseEntity.ok(response.toString());
    }

    //Request to Create a Sanction
    @RequestMapping(value = "/sanction/create", method = RequestMethod.POST)
    public ResponseEntity<String> sanction_create(@RequestBody Sanction sanction) {
        logger.info("Creating new sanction");

        Sanction sanctionReply= new Sanction();
        sanctionReply.copy(sanction);

        //Tell line that estimate has been approved.
        RequestMessage request;
        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            Action action = Action.create;
            request = new RequestMessage(to, from, sanctionReply, action);
        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Program Request");
        }
        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/on-sanction")
                    .body(Mono.just(request), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        String result = responseMono.block();

        return ResponseEntity.ok(result);
    }

}