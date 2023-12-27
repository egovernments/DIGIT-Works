package org.digit.exchange.controllers;

import org.digit.exchange.constants.Action;
import org.digit.exchange.models.*;
import org.digit.exchange.models.fiscal.Estimate;
import org.digit.exchange.models.fiscal.Program;
import org.digit.exchange.models.fiscal.Sanction;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Controller
@RequestMapping("/line")
public class LineDepartmentController{

    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final WebClient webClient;
    

    public LineDepartmentController() {
        this.webClient = WebClient.create();
    }

    //Send Request to Finance Department via DIGIT Exchange
    @RequestMapping(value = "/program/create", method = RequestMethod.POST)
    public ResponseEntity<String> program_create(@RequestBody Program program) {
        logger.info("Sending request for creating program : " + program.getName().toString());

        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,program,action);

        //Ask exchange to send the program
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/program")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();
        return ResponseEntity.ok("");
    }

    //on_program Create Handler
    @RequestMapping(value = "on-program/create", method = RequestMethod.POST)
    public ResponseEntity<String> onprogram_create(@RequestBody Program program) {
        logger.info("Program created with Code:" + program.getProgram());
        
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // Set the desired time zone
        ZonedDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
                                               .atZone(zoneId);
        ZonedDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59, 59)
                                               .atZone(zoneId);
    
        //Request for Budget by submitting an Estimate
        BigDecimal netAmount = new BigDecimal(100);
        BigDecimal grossAmount = new BigDecimal(100);
        Estimate estimate = new Estimate(program,startDate,endDate,netAmount,grossAmount);
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,estimate,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/estimate")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        return ResponseEntity.ok("");
    }

    //on_estimate Create Handler
    @RequestMapping(value = "on-estimate/create", method = RequestMethod.POST)
    public ResponseEntity<String> onestimate_create(@RequestBody Estimate estimate) {
        logger.info("Estimate created with Id:" + estimate.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);

        Sanction sanction = new Sanction(estimate,netAmount,grossAmount);
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,sanction,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/sanction")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        return ResponseEntity.ok("");
    }

    //on_sanction Create Handler
    @RequestMapping(value = "on-sanction/create", method = RequestMethod.POST)
    public ResponseEntity<String> onsanction_create(@RequestBody Sanction sanction) {
        logger.info("Sanction created with Id:" + sanction.getId());
        
        // BigDecimal amount = new BigDecimal(10);
        // Sanction sanction = new Sanction(estimate,amount);
        
        // String to = "finance@http://127.0.0.1:8080";
        // String from ="line@http://127.0.0.1:8080";
        // Action action = Action.create;
        // RequestMessage exchangeMessage = new RequestMessage(to,from,sanction,action);

        // //Make the request
        // Mono<String> responseMono = webClient.post()
        //             .uri("http://127.0.0.1:8080/exchange/v1/sanction")
        //             .body(Mono.just(exchangeMessage), RequestMessage.class)
        //             .retrieve()
        //             .bodyToMono(String.class);
        // responseMono.block();

        return ResponseEntity.ok("");
    }

}
