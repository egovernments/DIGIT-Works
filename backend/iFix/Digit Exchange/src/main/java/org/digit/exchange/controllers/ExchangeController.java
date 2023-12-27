package org.digit.exchange.controllers;

import org.digit.exchange.config.AppConfig;
import org.digit.exchange.constants.Status;
import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.models.*;
import org.digit.exchange.models.fiscal.FiscalMessage;
import org.digit.exchange.service.ExchangeService;
import org.digit.exchange.utils.DispatcherUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;


@Controller
@RequestMapping("/exchange/v1")
public class ExchangeController{
    // private final ObjectMapper objectMapper;
    // private final HttpServletRequest request;

    private final AppConfig config;
    private final DispatcherUtil dispatcher;
    private final ExchangeService service;

    // private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    public ExchangeController(AppConfig config, DispatcherUtil dispatcher, ExchangeService service) {
        this.config = config;
        this.dispatcher = dispatcher;
        this.service = service;
    }

    public String[] parseDID(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String[] parts = input.split("@", 2); // Limit to 2 parts in case there are multiple '@' in the string
        return parts; // The first part before the '@'
    }

    private ResponseMessage prepareResponse(RequestMessage messageRequest, Status status, String senderId, int count, boolean isMsgEncrypted, String message){
        ResponseMessage response = new ResponseMessage();
        ResponseHeader header = new ResponseHeader();
        //Set MessageId same as RequestMessage
        header.setMessageId(messageRequest.getHeader().getMessageId());
        //Generate Server Timestamp
        Instant timestamp = Instant.now();
        header.setMessageTs(timestamp.toString());
        //Set Action to same as Request Message
        header.setAction(messageRequest.getHeader().getAction());
        //Set Status to as required
        header.setStatus(status);
        //Set SenderId to Current DIGIT Exchange Instance
        header.setSenderId(senderId);
        //Set ReceiverId to Request Message SenderId
        header.setReceiverId(messageRequest.getHeader().getSenderId());
        //Count
        header.setTotalCount(count);
        header.setMsgEncrypted(isMsgEncrypted);
        // header.setMeta(messageRequest.getHeader().getFiscalMessage());
        response.setHeader(header);
        //If isMsgEncrypted then encrypt message
        //Encrypt(message)
        response.setMessage(message);
        return response;
    }

    private ResponseMessage processMessage(String messageType, RequestMessage messageRequest){
        //where does the message need to be delivered to.
        String receiver = messageRequest.getHeader().getReceiverId();
        String[] recieverDid = parseDID(receiver);
        String sender = messageRequest.getHeader().getSenderId();
        String[] senderDid = parseDID(sender);
        String fiscalResponse = "";
        ResponseMessage response = null;
        String deliverToUrl = "";
        //If sender and reciever from current domain
        if(recieverDid[1].equalsIgnoreCase(senderDid[1])){
            //Get message handler based on messageType and action
            String action = messageRequest.getHeader().getAction().toString();
            deliverToUrl = config.getRoutes().get(messageType + "." + action);
            if(deliverToUrl == null)
                //No implementation available
                throw new ResourceNotFoundException("Resource not found");
            // Check if message is encrypted
            // if(messageRequest.getHeader().isMsgEncrypted())
            //Decrypt Message
            //Verify Signature
        }else{
            //Check if sender is same domain
            if(senderDid[1].equalsIgnoreCase(config.getDomain())){
                //this is being sent to an external digit exchange server
                deliverToUrl = senderDid[1] + "exchange/v1";
                // Check if message needs to be is encrypted
                // if(messageRequest.getHeader().isMsgEncrypted())
                //Encrypt Message
                //Sign Message
            }else{
                //Message received from external domain
                //Check if message needs to be is encrypted
                //if(messageRequest.getHeader().isMsgEncrypted())
                //Decrypt Message
                //Verify Signature
            }
        }
        //Save Message in Message Store
        service.createRequestMessage(messageRequest);

        FiscalMessage fiscalMessage = messageRequest.getHeader().getFiscalMessage();
        String fiscalMessageStr = messageRequest.getMessage();
        fiscalResponse = dispatcher.dispatch(deliverToUrl,fiscalMessage, fiscalMessageStr);
        //Emit Fiscal Event
        String senderId = config.getName() + "@" + config.getDomain();
        response = prepareResponse( messageRequest, Status.rcvd, senderId, 1, false, fiscalResponse);
        return response;
    }

    @RequestMapping(value = "/{subpath}", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> program(@RequestBody RequestMessage messageRequest, @PathVariable("subpath") String subpath) {
        try {
            ResponseMessage response = processMessage(subpath, messageRequest);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
