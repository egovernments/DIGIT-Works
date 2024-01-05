package org.digit.exchange.web.controllers;

import org.digit.exchange.config.AppConfig;
import org.digit.exchange.constants.Status;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.kafka.ExchangeProducer;
import org.digit.exchange.web.models.RequestMessage;
import org.digit.exchange.service.ExchangeService;
import org.digit.exchange.utils.DispatcherUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/exchange/v1")
public class ExchangeController{
    // private final ObjectMapper objectMapper;
    // private final HttpServletRequest request;

    private final AppConfig config;
    private final DispatcherUtil dispatcher;
    private final ExchangeService service;

    private final ExchangeProducer producer;

    // private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    public ExchangeController(AppConfig config, DispatcherUtil dispatcher, ExchangeService service, ExchangeProducer producer) {
        this.config = config;
        this.dispatcher = dispatcher;
        this.service = service;
        this.producer = producer;
    }

    public String[] parseDID(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        // Limit to 2 parts in case there are multiple '@' in the string // The first part before the '@'
        return input.split("@", 2);
    }


//    private RequestMessage processMessage(String messageType, RequestMessage messageRequest){
//        Map<String, RequestMessage> messageTypeRequestMap = new HashMap<>();
//        //where does the message need to be delivered to.
//        String receiver = messageRequest.getHeader().getReceiverId();
//        String[] recieverDid = parseDID(receiver);
//        String sender = messageRequest.getHeader().getSenderId();
//        String[] senderDid = parseDID(sender);
//        String fiscalResponse = "";
//        RequestMessage response = null;
//        String deliverToUrl = "";
//        //If sender and reciever from current domain
//        if(recieverDid[1].equalsIgnoreCase(senderDid[1])){
//            //Get message handler based on messageType and action
//            String action = messageRequest.getHeader().toString();
//            deliverToUrl = config.getRoutes().get(messageType + "." + action);
//            if(deliverToUrl == null)
//                //No implementation available
//                throw new ResourceNotFoundException("Resource not found");
//            // Check if message is encrypted
//            // if(messageRequest.getHeader().isMsgEncrypted())
//            //Decrypt Message
//            //Verify Signature
//        }else{
//            //Check if sender is same domain
//            if(senderDid[1].equalsIgnoreCase(config.getDomain())){
//                //this is being sent to an external digit exchange server
//                deliverToUrl = senderDid[1] + "exchange/v1";
//                // Check if message needs to be is encrypted
//                // if(messageRequest.getHeader().isMsgEncrypted())
//                //Encrypt Message
//                //Sign Message
//            }else{
//                //Message received from external domain
//                //Check if message needs to be is encrypted
//                //if(messageRequest.getHeader().isMsgEncrypted())
//                //Decrypt Message
//                //Verify Signature
//            }
//        }
//        //Save Message in Message Store
//        service.createRequestMessage(messageRequest);
//
//        // Send to kafka topic
//        messageTypeRequestMap.put(messageType, messageRequest);
//        producer.push(config.getExchangeKafkaTopic(), messageTypeRequestMap);
//
//        FiscalMessage fiscalMessage = messageRequest.getHeader().getFiscalMessage();
//        String fiscalMessageStr = messageRequest.getMessage();
//        fiscalResponse = dispatcher.dispatch(deliverToUrl,fiscalMessage, fiscalMessageStr);
//        //Emit Fiscal Event
//        String senderId = config.getName() + "@" + config.getDomain();
//        response = prepareResponse( messageRequest, Status.rcvd, senderId, 1, false, fiscalResponse);
//        return response;
//    }

//    @PostMapping(value = "/{subpath}")
//    public ResponseEntity<RequestMessage> program(@RequestBody RequestMessage messageRequest, @PathVariable("subpath") String subpath) {
//        try {
//            ResponseMessage response = processMessage(subpath, messageRequest);
//            return ResponseEntity.ok(response);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
